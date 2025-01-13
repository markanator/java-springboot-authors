package com.ambro.authors.services.impl;

import com.ambro.authors.domain.entities.AuthorEntity;
import com.ambro.authors.repositories.IAuthorRepository;
import com.ambro.authors.services.IAuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements IAuthorService {
    private final IAuthorRepository authorRepository;

    public AuthorServiceImpl(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(
                authorRepository.findAll()
                                .spliterator(),
                false)
                .collect(
                        Collectors.toList()
                );
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
