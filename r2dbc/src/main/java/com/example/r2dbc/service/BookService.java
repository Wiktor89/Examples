package com.example.r2dbc.service;

import com.example.r2dbc.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Flux<Book> findAll();

    Mono<Book> findByName(String name);

    Mono<Book> findByIsbn(String isbn);

    Mono<Book> save(Book book);

    void delete(Long id);

    Mono<Book> update(Long id, Book book);
}
