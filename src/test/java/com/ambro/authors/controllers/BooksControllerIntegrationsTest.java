package com.ambro.authors.controllers;


import com.ambro.authors.TestDataUtil;
import com.ambro.authors.domain.dto.BookDto;
import com.ambro.authors.domain.entities.BookEntity;
import com.ambro.authors.services.IBookService;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BooksControllerIntegrationsTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final IBookService bookService;

    @Autowired
    public BooksControllerIntegrationsTest(MockMvc mockMvc, IBookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttp201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttp200Ok() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBookA = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateBookReturnsTheCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatUpdateBookReturnsTheUpdatedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBookA = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBookA.getIsbn());
        testBookA.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookA.getTitle())
        );
    }

    @Test
    public void testThatFindAllBooksReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatFindAllBooksReturnsListOfBooks() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(book.getIsbn(), book);

        // Act
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle())
                );
    }
    @Test
    public void testThatFindOneBookReturnsHttp200WhenBookExists() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(book.getIsbn(), book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatFindOneBookReturnsHttp404WhenNoBookExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/99999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

}
