package com.example.test.service;

import com.example.test.BookRepository;
import com.example.test.entity.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void remove(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book with id = %s does not exist"));
        bookRepository.delete(book);
    }

    @Override
    public void updateBook(Book bookSrc) {
        if (Objects.isNull(bookSrc.getId())) {
            throw new NullPointerException("Book have id = null");
        }
        Book bookUpd = bookRepository.findById(bookSrc.getId())
                .orElseThrow(() -> new RuntimeException("Book with id = %s does not exist"));
        bookUpd.setIsbn(bookSrc.getIsbn());
        bookUpd.setName(bookSrc.getName());
        bookRepository.save(bookUpd);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookId(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
    }
}
