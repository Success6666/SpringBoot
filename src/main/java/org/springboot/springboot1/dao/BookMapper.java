package org.springboot.springboot1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springboot.springboot1.entity.Book;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> getBooks();
    int addBook(Book book);
    Book getBookById(int bookId);
    int updateBook(Book book);
    int deleteBook(int bookId);
    List<Book> getBooksByCategoryId(int categoryId);

}
