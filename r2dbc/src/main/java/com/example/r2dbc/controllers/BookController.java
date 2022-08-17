package com.example.r2dbc.controllers;

import com.example.r2dbc.model.Book;
import com.example.r2dbc.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public Flux<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping("/add")
    public Mono<Book> save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping("/isbn/{isbn}")
    public Mono<Book> findIsbn(@PathVariable("isbn") String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @GetMapping("/name/{name}")
    public Mono<Book> findName(@PathVariable("name") String name) {
        return bookService.findByName(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Mono<Book> update(@RequestBody Book book,
                             @PathVariable("id") Long id) {
        return bookService.update(id, book);
    }
}
