package com.example.test.service;

import com.example.test.AbstractIntegrationTest;
import com.example.test.BookRepository;
import com.example.test.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql("/scripts/book/clear.sql")
    @Sql("/scripts/book/init.sql")
    @DisplayName("Book save")
    void save() {
        Book book = new Book();
        book.setName("Four book");
        book.setIsbn("09876-1234-56789-1234");
        bookService.save(book);
        Book fourBook = bookRepository.findBookByName("Four book");
        assertThat(fourBook).isNotNull();
    }

    @Test
    @Sql("/scripts/book/clear.sql")
    @Sql("/scripts/book/init.sql")
    @DisplayName("Book remove")
    void remove() {
        Book book = bookRepository.findBookByName("First book");
        assertThat(book).isNotNull();
        bookRepository.delete(book);
        book = bookRepository.findBookByName("First book");
        assertThat(book).isNull();
    }

    @Test
    @Sql("/scripts/book/clear.sql")
    @Sql("/scripts/book/init.sql")
    @DisplayName("Book update")
    void updateBook() {
        Book book = bookRepository.findBookByName("First book");
        Long bookId = book.getId();
        book.setName("First book");
        bookRepository.saveAndFlush(book);
        Book bookUpdate = bookRepository.findById(bookId).orElse(new Book());
        assertThat("First book").isEqualTo(bookUpdate.getName());
    }

    @Test
    @Sql("/scripts/book/clear.sql")
    @Sql("/scripts/book/init.sql")
    @DisplayName("Book find all")
    void findAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(1).isEqualTo(books.size());
    }

    @Test
    @Sql("/scripts/book/clear.sql")
    @Sql("/scripts/book/init.sql")
    @DisplayName("Book find book id")
    void findBookId() {
        Book book = bookRepository.findBookByName("First book");
        Book bookId = bookRepository.findById(book.getId()).orElse(new Book());
        assertThat(book.getId()).isEqualTo(bookId.getId());
    }
}