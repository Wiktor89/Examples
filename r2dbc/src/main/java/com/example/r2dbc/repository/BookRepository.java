package com.example.r2dbc.repository;

import com.example.r2dbc.model.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface BookRepository extends R2dbcRepository<Book, Long> {

    Mono<Book> findByIsbn(String isbn);

    Mono<Book> findByName(String name);
}
