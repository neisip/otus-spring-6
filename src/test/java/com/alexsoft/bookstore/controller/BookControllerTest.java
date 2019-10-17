package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.utils.BookMappingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookApiController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

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
    void itReturnsBooksOnListCall() throws Exception {

        given(bookRepository.findAll()).willReturn(books);

        //when
        ResultActions ra = mvc.perform(get("/api/book/list"));

        //then
        ra.andExpect(status()
                .isOk())
                .andExpect(jsonPath("$[0].title", is(b.getTitle())))
                .andExpect(jsonPath("$[0].id", is(b.getId())));
    }

    @Test
    void itAddsBooks() throws Exception {

        //given
        String jsonString = new ObjectMapper().writeValueAsString(BookMappingUtil.mapBookToBookInfoDto(b).get());
        given(bookRepository.save(argThat((book) ->
                book.getTitle().equals(b.getTitle()) &&
                book.getGenre().equals(b.getGenre()) &&
                book.getAuthor().getName().equals(b.getAuthor().getName())))
        ).willReturn(b);

        //when
        val res = mvc.perform(post("/api/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        //then
        res.andExpect(status().isOk()).andExpect(content().string(b.getId()));
        verify(bookRepository).save(any());

    }

    @Test
    void itDeletesBook() throws Exception {
        //given

        given(bookRepository.findById("1")).willReturn(Optional.of(b));
        doNothing().when(bookRepository).delete(b);

        //when
        val res = mvc.perform(delete("/api/book/1"));

        //then
        res.andExpect(status().isOk());
        verify(bookRepository).findById(any());
        verify(bookRepository).delete(b);
    }

    @Test
    void itReturnsNotFoundWhenObjectToDeleteIsAbsent() throws Exception {
        //given
        given(bookRepository.findById("1")).willReturn(Optional.empty());

        //when
        val res =  mvc.perform(delete("/api/book/1"));

        //then
        res.andExpect(status().is4xxClientError());
        verify(bookRepository).findById(any());
        verifyNoMoreInteractions(bookRepository);
    }
}
