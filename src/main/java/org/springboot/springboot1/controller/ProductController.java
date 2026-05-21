package org.springboot.springboot1.controller;

import org.springboot.springboot1.entity.Category;
import org.springboot.springboot1.entity.Product;
import org.springboot.springboot1.service.ICategoryService;
import org.springboot.springboot1.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    private void addAuthInfo(Model model, Principal principal) {
        if (principal instanceof Authentication auth) {
            model.addAttribute("isAdmin", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_admin")));
        }
    }

    @GetMapping("/list")
    public String list(Model model, Principal principal) {
        List<Product> productList = productService.findAll();
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);
        addAuthInfo(model, principal);
        return "product_list";
    }

    @GetMapping("/findByCategoryId/{categoryId}")
    public String findByCategoryId(@PathVariable Integer categoryId, Model model, Principal principal) {
        List<Product> productList = productService.findByCategoryId(categoryId);
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);
        addAuthInfo(model, principal);
        return "product_list";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('admin')")
    public String toAdd(@ModelAttribute Product product, Model model) {
        model.addAttribute("categoryList", categoryService.getCategories());
        return "addProduct";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('admin')")
    public String add(@ModelAttribute Product product) {
        productService.add(product);
        return "redirect:/product/list";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "productDetail";
        }
        return "redirect:/product/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('admin')")
    public String toEdit(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("categoryList", categoryService.getCategories());
            return "editProduct";
        }
        return "redirect:/product/list";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('admin')")
    public String edit(@ModelAttribute Product product) {
        productService.update(product);
        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product/list";
    }
}
