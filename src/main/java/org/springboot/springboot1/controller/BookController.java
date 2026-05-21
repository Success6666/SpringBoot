package org.springboot.springboot1.controller;

import org.springboot.springboot1.entity.Book;
import org.springboot.springboot1.entity.Category;
import org.springboot.springboot1.service.IBookService;
import org.springboot.springboot1.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    IBookService bookService;
    @Autowired
    ICategoryService categoryService;

    private void addAuthInfo(org.springframework.ui.Model model, Principal principal) {
        if (principal instanceof Authentication auth) {
            model.addAttribute("isAdmin", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_admin")));
        }
    }

    @GetMapping("/bookList")
    public String tobookList(org.springframework.ui.Model model, Principal principal) {
        List<Book> books = bookService.getBooks();
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("books", books);
        model.addAttribute("categoryList", categoryList);
        addAuthInfo(model, principal);
        return "bookList";
    }

    @GetMapping("/bookList/category/{categoryId}")
    public String tobookListByCategory(@PathVariable Integer categoryId, org.springframework.ui.Model model, Principal principal) {
        List<Book> books = bookService.getBooksByCategoryId(categoryId);
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("books", books);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("activeCategoryId", categoryId);
        addAuthInfo(model, principal);
        return "bookList";
    }

    @GetMapping("/addbook")
    @PreAuthorize("hasRole('admin')")
    public String toAddBook(@ModelAttribute Book book, org.springframework.ui.Model model) {
        model.addAttribute("categoryList", categoryService.getCategories());
        return "addBook";
    }

    @PostMapping("/addbook")
    @PreAuthorize("hasRole('admin')")
    public String addBook(@ModelAttribute Book book) {
        bookService.addbook(book);
        return "redirect:/bookList";
    }

    @GetMapping("/bookDetail/{bookId}")
    public String bookDetail(@PathVariable Integer bookId, org.springframework.ui.Model model) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);
            return "bookDetail";
        }
        return "redirect:/bookList";
    }

    @GetMapping("/editBook/{bookId}")
    @PreAuthorize("hasRole('admin')")
    public String toEditBook(@PathVariable Integer bookId, org.springframework.ui.Model model) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("categoryList", categoryService.getCategories());
            return "editBook";
        }
        return "redirect:/bookList";
    }

    @PostMapping("/editBook")
    @PreAuthorize("hasRole('admin')")
    public String editBook(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return "redirect:/bookList";
    }

    @GetMapping("/deleteBook/{bookId}")
    @PreAuthorize("hasRole('admin')")
    public String deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/bookList";
    }
}
