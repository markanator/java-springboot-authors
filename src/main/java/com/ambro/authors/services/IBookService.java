package com.ambro.authors.services;

import com.ambro.authors.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    BookEntity createUpdateBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);
}
