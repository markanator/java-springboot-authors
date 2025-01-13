package com.ambro.authors.controllers;

import com.ambro.authors.TestDataUtil;
import com.ambro.authors.domain.dto.AuthorDto;
import com.ambro.authors.domain.entities.AuthorEntity;
import com.ambro.authors.services.IAuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorsControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final IAuthorService authorService;

    @Autowired
    public AuthorsControllerIntegrationTest(MockMvc mockMvc, IAuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        // ARRANGE
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        // Act
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        )
        .andExpect(
                // ASSERT
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        // ARRANGE
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        // Act
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        // ASSERT
                        MockMvcResultMatchers.status().isOk()
                );
    }
    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorA);

        // Act
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value(testAuthorA.getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].age").value(testAuthorA.getAge())
                );
    }
    @Test
    public void testThatFindOneAuthorSuccessfullyReturnsHttp200OkWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorA);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/"+testAuthorA.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatFindOneAuthorSuccessfullyReturnsHttp404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/999999")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFindOneAuthorSuccessfullyReturnsDetailsWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorA);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/"+testAuthorA.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(1)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
                );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp404WhenNoAuthorExists() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        String res = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/999999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(res)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorA = authorService.save(testAuthorA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String res = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/"+savedAuthorA.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(res)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatFullUpdateAuthorUpdatesExistingUser() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorA = authorService.save(testAuthorA);

        AuthorEntity authorBDto = TestDataUtil.createTestAuthorB();
        authorBDto.setId(savedAuthorA.getId());

        String res = objectMapper.writeValueAsString(authorBDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/"+savedAuthorA.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(res)
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(testAuthorA.getId())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value(authorBDto.getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(authorBDto.getAge())
                );

    }
    @Test
    public void testThatDeleteAuthorReturns404WhenNoAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorA = authorService.save(testAuthorA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String res = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/authors/999999")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void testThatDeleteAuthorReturns404WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthorA = authorService.save(testAuthorA);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/authors/999999" + savedAuthorA.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
