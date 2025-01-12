package com.ambro.authors.services.impl;

import com.ambro.authors.domain.entities.BookEntity;
import com.ambro.authors.repositories.IBookRepository;
import com.ambro.authors.services.IBookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements IBookService {
    private final IBookRepository bookRepository;

    public BookServiceImpl(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        // more certain of ISBN as id
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
