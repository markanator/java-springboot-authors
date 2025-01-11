package com.ambro.authors;

import com.ambro.authors.domain.Author;
import com.ambro.authors.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("L.A. Rain")
                .age(32)
                .build();
    }
    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Mark Ambro")
                .age(33)
                .build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Amelia Casey")
                .age(44)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }
    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-1")
                .title("Harry Potter and the Philosopher's Stone")
                .author(author)
                .build();
    }
    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-2")
                .title("Dune")
                .author(author)
                .build();
    }
}
