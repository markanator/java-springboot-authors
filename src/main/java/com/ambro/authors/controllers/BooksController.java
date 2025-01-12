package com.ambro.authors.controllers;

import com.ambro.authors.domain.dto.BookDto;
import com.ambro.authors.domain.entities.BookEntity;
import com.ambro.authors.mappers.Mapper;
import com.ambro.authors.services.IBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final IBookService bookService;

    public BooksController(Mapper<BookEntity, BookDto> bookMapper, IBookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        BookEntity book = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(isbn, book);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }
}
