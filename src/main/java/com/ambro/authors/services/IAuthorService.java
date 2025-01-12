package com.ambro.authors.services;

import com.ambro.authors.domain.entities.AuthorEntity;

public interface IAuthorService {
    AuthorEntity createAuthor(AuthorEntity author);
}
