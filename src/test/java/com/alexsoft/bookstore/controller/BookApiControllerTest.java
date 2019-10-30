package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.config.AppConfig;
import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.service.BookPropertiesSyncService;
import com.github.cloudyrock.mongock.Mongock;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebFluxTest(controllers = BookApiController.class,
        excludeFilters= @ComponentScan.Filter(type = FilterType.REGEX,pattern = "com.alexsoft.bookstore.config.*"))
public class BookApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookPropertiesSyncService bookPropertiesSyncService;

    private List<Book> books = Arrays.asList(makeMockBook());
    private Book b = makeMockBook();
    private Book makeMockBook() {
        val b = new Book();
        b.setId("1");
        b.setTitle("123");
        b.setAuthor(new Author("123"));
        b.setGenre("123");
        return b;
    }

    @Test
    void itListsBooks() {
        //given
        given(bookRepository.findAll()).willReturn(Flux.just(b));

        //when
        //then
        webTestClient
                .get()
                .uri("/api/book/list")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("[{\"id\":\"1\",\"title\":\"123\",\"genre\":\"123\",\"authorName\":\"123\",\"comments\":null}]");
    }
}
