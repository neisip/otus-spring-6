package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    private List<Book> books = makeMockBooks();

    private List<Book> makeMockBooks() {

        val b = new Book();
        b.setTitle("123");
        b.setAuthor(new Author("123"));
        b.setGenre("123");
        return Arrays.asList(b);
    }

    @Test
    void itReturnsBooksOnListCall() throws Exception {
        //given

        given(bookRepository.findAll()).willReturn(books);

        //when
        ResultActions ra = mvc.perform(get("/book/list"));

        //then
        ra.andExpect(status().isOk())
                .andExpect(model(). attribute("books", books));


    }

    @Test
    void itAddsBooks() throws Exception {
        val b = books.get(0);

        val params = new LinkedMultiValueMap<String, String>();
        params.set("title", b.getTitle());
        params.set("genre", b.getGenre());
        params.set("author.name", b.getAuthor().getName());

        //given
        given(bookRepository.save(argThat((book) ->

                book.getTitle().equals(b.getTitle()) &&
                book.getGenre().equals(b.getGenre()) &&
                book.getAuthor().getName().equals(b.getAuthor().getName())))
        ).willReturn(b);


        //when
        val res = mvc.perform(post("/book/add").params(params));

        //then
        res.andExpect(status().is3xxRedirection());
        verify(bookRepository).save(any());

    }

    @Test
    void itDeletesBook() throws Exception {
        //given
        val b = books.get(0);
        given(bookRepository.findById("1")).willReturn(Optional.of(b));
        doNothing().when(bookRepository).delete(b);

        //when
        val res = mvc.perform(delete("/book/delete/1"));

        //then
        res.andExpect(status().is3xxRedirection());
        verify(bookRepository).findById(any());
        verify(bookRepository).delete(b);
    }

    @Test
    void itReturnsNotFoundWhenObjectToDeleteIsAbsent() throws Exception {
        //given
        given(bookRepository.findById("1")).willReturn(Optional.empty());

        //when
        val res =  mvc.perform(delete("/book/delete/1"));

        //then
        res.andExpect(status().is4xxClientError());
        verify(bookRepository).findById(any());
        verifyNoMoreInteractions(bookRepository);
    }
}
