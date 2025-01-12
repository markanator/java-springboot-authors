package com.ambro.authors.services.impl;

import com.ambro.authors.domain.entities.AuthorEntity;
import com.ambro.authors.repositories.IAuthorRepository;
import com.ambro.authors.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final IAuthorRepository authorRepository;

    public AuthorServiceImpl(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity author) {
        return authorRepository.save(author);
    }
}
