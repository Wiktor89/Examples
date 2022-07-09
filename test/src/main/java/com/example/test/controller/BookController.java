package com.example.test.controller;

import com.example.test.entity.Book;
import com.example.test.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book findBookId(@PathVariable("id") Long id) {
        return bookService.findBookId(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id) {
        bookService.remove(id);
    }

    @PostMapping("/")
    public void add(@RequestBody Book book) {
        bookService.save(book);
    }
}
