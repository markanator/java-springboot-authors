package com.ambro.authors.services;

import com.ambro.authors.domain.dto.BookDto;
import com.ambro.authors.domain.entities.BookEntity;

import java.util.List;

public interface IBookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
