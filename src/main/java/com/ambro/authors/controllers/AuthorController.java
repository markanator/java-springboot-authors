package com.ambro.authors.controllers;

import com.ambro.authors.domain.dto.AuthorDto;
import com.ambro.authors.domain.entities.AuthorEntity;
import com.ambro.authors.mappers.Mapper;
import com.ambro.authors.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    @Autowired
    public AuthorController(AuthorService _authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = _authorService;
        this.authorMapper = authorMapper;
    }


    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
}
