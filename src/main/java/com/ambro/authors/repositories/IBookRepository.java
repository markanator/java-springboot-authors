package com.ambro.authors.repositories;

import com.ambro.authors.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends CrudRepository<BookEntity, String> {
}