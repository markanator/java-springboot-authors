package com.ambro.authors.controllers;

import com.ambro.authors.domain.dto.AuthorDto;
import com.ambro.authors.domain.entities.AuthorEntity;
import com.ambro.authors.mappers.Mapper;
import com.ambro.authors.services.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final IAuthorService IAuthorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    @Autowired
    public AuthorController(IAuthorService _I_authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.IAuthorService = _I_authorService;
        this.authorMapper = authorMapper;
    }


    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = IAuthorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<AuthorEntity> authors = IAuthorService.findAll();
        return new ResponseEntity<>(
                authors.stream()
                        .map(authorMapper::mapTo)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        Optional<AuthorEntity> maybeAuthor = IAuthorService.findOne(id);

        return maybeAuthor.map(author -> new ResponseEntity<>(authorMapper.mapTo(author), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
