package org.springboot.springboot1.service;

import org.springboot.springboot1.entity.Book;

import java.util.List;

public interface IBookService {
    List<Book> getBooks();
    int addbook(Book book);
    Book getBookById(int bookId);
    int updateBook(Book book);
    int deleteBook(int bookId);
    List<Book> getBooksByCategoryId(int categoryId);
}
