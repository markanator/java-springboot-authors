package com.ambro.authors.controllers;

import com.ambro.authors.domain.dto.BookDto;
import com.ambro.authors.domain.entities.BookEntity;
import com.ambro.authors.mappers.Mapper;
import com.ambro.authors.services.IBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BooksController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final IBookService bookService;

    public BooksController(Mapper<BookEntity, BookDto> bookMapper, IBookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        BookEntity book = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, book);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto,
                bookExists
                        ? HttpStatus.OK
                        : HttpStatus.CREATED
        );
    }
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookEntity> books = bookService.findAll();
        return new ResponseEntity<>(
                books.stream()
                        .map(bookMapper::mapTo)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> maybeBook = bookService.findOne(isbn);
        return maybeBook.map(
                book -> new ResponseEntity<>(bookMapper.mapTo(book), HttpStatus.OK)
        ).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }
}
