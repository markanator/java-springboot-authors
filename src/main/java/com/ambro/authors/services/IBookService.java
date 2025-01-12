package com.ambro.authors.services;

import com.ambro.authors.domain.entities.BookEntity;

public interface IBookService {
    BookEntity createBook(String isbn, BookEntity book);
}
