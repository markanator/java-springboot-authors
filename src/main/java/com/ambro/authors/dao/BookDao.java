package com.ambro.authors.dao;

import com.ambro.authors.domain.Book;

import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);
}
