package com.example.r2dbc.service;

import com.example.r2dbc.model.Book;
import com.example.r2dbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public Mono<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Mono<Book> update(Long id, Book book) {
        return bookRepository.findById(id)
                .flatMap(s -> {
                    book.setId(s.getId());
                    return bookRepository.save(s);
                });
    }
}
