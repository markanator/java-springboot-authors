package com.ambro.authors.repositories;

import com.ambro.authors.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository  extends CrudRepository<Book, String> {
}
