package com.ambro.authors.services;

import com.ambro.authors.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);
}
