package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.repository.author.AuthorDao;
import com.alexsoft.bookstore.repository.book.BookDao;
import com.alexsoft.bookstore.repository.genre.GenreDao;
import org.junit.Test;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookstoreShellControllerTest {

    private BookDao bookDao = mock(BookDao.class);
    private AuthorDao authorDao = mock(AuthorDao.class);
    private GenreDao genreDao = mock(GenreDao.class);
    private PrintStream output = mock(PrintStream.class);

    private BookstoreShellController sut = new BookstoreShellControllerImpl(authorDao, bookDao, genreDao, output);

    private List<BookDO> makeStubBooks() {

        BookDO b1 = new BookDO(1L, "Stub1", 1L, 1L);
        BookDO b2 = new BookDO(2L, "Stub2", 2L, 2L);

        LinkedList<BookDO> l = new LinkedList<BookDO>();
        l.add(b1);
        l.add(b2);
        return l;
    }

    private List<BookDO> books = makeStubBooks();

    @Test
    public void outputsBooksToConsole() {
        //given
        when(bookDao.getAll()).thenReturn(books);

        //when
        sut.showBooks();

        //then
        verify(bookDao).getAll();
        verify(output).println(books.get(0));
        verify(output).println(books.get(1));
    }

    @Test
    public void outputsGenresToConsole() {
        //given
        String mockTitle = "1";
        when(bookDao.getBooksByGenreTitle(mockTitle)).thenReturn(books.subList(0,1));

        //when
        sut.showBooksByGenreTitle(mockTitle);

        //then
        verify(bookDao).getBooksByGenreTitle(mockTitle);
        verify(output).println(books.get(0));
    }

    @Test
    public void outputsBooksByAuthorToConsole() {
        //given
        String mockName = "1";
        when(bookDao.getBooksByAuthorName(mockName)).thenReturn(books.subList(0,1));

        //when
        sut.showBooksByAuthorName(mockName);

        //then
        verify(bookDao).getBooksByAuthorName(mockName);
        verify(output).println(books.get(0));
    }

    @Test
    public void outputsBooksByGenreToConsole() {
        //given
        String mockTitle = "1";
        when(bookDao.getBooksByGenreTitle(mockTitle)).thenReturn(books.subList(0,1));

        //when
        sut.showBooksByGenreTitle(mockTitle);

        //then
        verify(bookDao).getBooksByGenreTitle(mockTitle);
        verify(output).println(books.get(0));
    }

    @Test
    public void addsBook() {
        //given
        Long mockedId = 3L;
        when(bookDao.insert(any())).thenReturn(true);

        //when
        sut.addBook(mockedId, "3L", 3L, 3L);

        //then
        verify(bookDao).insert(argThat((it)-> it.getId().equals(mockedId)));
    }

    @Test
    public void addsGenre() {
        //given
        Long mockedId = 3L;
        when(genreDao.insert(any())).thenReturn(true);

        //when
        sut.addGenre(mockedId, "3L");

        //then
        verify(genreDao).insert(argThat((it)-> it.getId().equals(mockedId)));
    }

    @Test
    public void addsAuthor() {
        //given
        Long mockedId = 3L;
        when(authorDao.insert(any())).thenReturn(true);

        //when
        sut.addAuthor(mockedId, "3L");

        //then
        verify(authorDao).insert(argThat((it)-> it.getId().equals(mockedId)));

    }
    @Test
    public void removesGenreByTitle() {
        //given
        String mockedTitle = "Stub";
        when(genreDao.deleteByTitle(mockedTitle)).thenReturn(true);

        //when
        sut.removeGenreByTitle(mockedTitle);

        //then
        verify(genreDao).deleteByTitle(mockedTitle);
    }

    @Test
    public void removesAuthorByName() {
        //given
        String mockedName = "Stub";
        when(authorDao.deleteByName(mockedName)).thenReturn(true);

        //when
        sut.removeAuthorByName(mockedName);

        //then
        verify(authorDao).deleteByName(mockedName);
    }

    @Test
    public void removesBookByTitle() {
        //given
        String mockedTitle = "Stub";
        when(bookDao.deleteByTitle(mockedTitle)).thenReturn(true);

        //when
        sut.removeBookByTitle(mockedTitle);

        //then
        verify(bookDao).deleteByTitle(mockedTitle);

    }

}
