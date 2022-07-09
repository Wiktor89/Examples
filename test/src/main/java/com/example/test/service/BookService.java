package com.example.test.service;

import com.example.test.entity.Book;

import java.util.List;

public interface BookService {

    void save(Book book);

    void remove(Long id);

    void updateBook(Book book);

    List<Book> findAll();

    Book findBookId(Long id);
}
