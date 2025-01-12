package com.ambro.authors.services;

import com.ambro.authors.domain.entities.AuthorEntity;

import java.util.List;

public interface IAuthorService {
    AuthorEntity createAuthor(AuthorEntity author);

    List<AuthorEntity> findAll();
}
