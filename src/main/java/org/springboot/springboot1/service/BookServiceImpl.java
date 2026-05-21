package org.springboot.springboot1.service;

import org.springboot.springboot1.dao.BookMapper;
import org.springboot.springboot1.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    BookMapper bookMapper;
    @Override
    public List<org.springboot.springboot1.entity.Book> getBooks() {
        return bookMapper.getBooks();
    }

    @Override
    public int addbook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public Book getBookById(int bookId) {
        return bookMapper.getBookById(bookId);
    }

    @Override
    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public int deleteBook(int bookId) {
        return bookMapper.deleteBook(bookId);
    }

    @Override
    public List<Book> getBooksByCategoryId(int categoryId) {
        return bookMapper.getBooksByCategoryId(categoryId);
    }
}
