package com.ambro.authors.repositories;

import com.ambro.authors.TestDataUtil;
import com.ambro.authors.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {

    private IAuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTest(IAuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

//    @Test
//    public void testThatFindManyReturnsAllAuthors(){
//        Author authorA = TestDataUtil.createTestAuthorA();
//        underTest.create(authorA);
//        Author authorb = TestDataUtil.createTestAuthorB();
//        underTest.create(authorb);
//        Author authorc = TestDataUtil.createTestAuthorC();
//        underTest.create(authorc);
//
//        List<Author> result = underTest.findMany();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(authorA, authorb, authorc);
//    }
//
//    @Test
//    public void testThatUpdateAuthorUpdatesCorrectly(){
//        Author authorA = TestDataUtil.createTestAuthorA();
//        underTest.create(authorA);
//
//        authorA.setName("<NAME>");
//        underTest.update(authorA.getId(), authorA);
//
//        Optional<Author> result = underTest.findOne(authorA.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorA);
//    }
//
//    @Test
//    public void testThatDeleteAuthorDeletesCorrectly(){
//        // Arrange
//        Author authorA = TestDataUtil.createTestAuthorA();
//        underTest.create(authorA);
//        // Act
//        underTest.delete(authorA.getId());
//        // Assert
//        Optional<Author> result = underTest.findOne(authorA.getId());
//        assertThat(result).isEmpty();
//    }
}
