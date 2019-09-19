package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.utils.mappers.AuthorMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorDaoImplTest {

    private NamedParameterJdbcOperations operations = mock(NamedParameterJdbcOperations.class);
    private AuthorDao sut = new AuthorDaoImpl(operations);

    private AuthorDO ma1 = new AuthorDO(1L, "1");
    private AuthorDO ma2 = new AuthorDO(2L, "2");

    private List<AuthorDO> makeMockAuthorList() {
        List n = new LinkedList();
        n.add(ma1);
        n.add(ma2);
        return n;
    }

    private List<AuthorDO> ml = makeMockAuthorList();
    private Map<String, ?> authorMap = new AuthorMapper().getMapFor(ma1);

    @Test
    public void insertUpdatesDB() {
        //given
        when(operations.update(any(), any(Map.class))).thenReturn(1);
        when(operations.queryForObject(any(), any(Map.class) ,any(Class.class))).thenReturn(true);

        //when
        boolean e = sut.insert(ma1);

        //then
        verify(operations).update(any(), anyMap());
        Assert.assertTrue(e);
    }

    @Test
    public void insertCatchesException() {
        //given
        when(operations.update(any(), any(Map.class))).thenThrow(new RuntimeException());
        when(operations.queryForObject(any(), any(Map.class) ,any(Class.class))).thenReturn(true);

        //when
        boolean e = sut.insert(ma1);

        //then
        verify(operations).update(any(), any(Map.class));
        Assert.assertFalse(e);
    }

    @Test
    public void getAllReturnsBooks() {
        //given
        when(operations.query(anyString(), any(RowMapper.class))).thenReturn(ml);

        //when
        List<AuthorDO> authors = sut.getAll();

        //then
        verify(operations).query(anyString(), any(RowMapper.class));
        Assert.assertEquals(ml, authors);
    }

    @Test
    public void getAllCatchesException() {
        //given
        when(operations.query(anyString(), any(RowMapper.class))).thenThrow(new RuntimeException());

        //when
        List<AuthorDO> authors = sut.getAll();

        //then
        verify(operations).query(anyString(), any(RowMapper.class));
        Assert.assertEquals(Collections.emptyList(), authors);
    }

    @Test
    public void contains() {
        //given
        long mockId = 1L;
        when(operations.queryForObject(anyString(), any(Map.class), any(Class.class))).thenReturn(true);

        //when
        boolean r = sut.contains(mockId);

        //then
        verify(operations).queryForObject(anyString(), any(Map.class), any(Class.class));
        Assert.assertTrue(r);
    }

    @Test
    public void containsCatchesException() {
        //given
        long mockId = 1L;
        when(operations.queryForObject(anyString(), any(Map.class), any(Class.class))).thenThrow(new RuntimeException());

        //when
        boolean r = sut.contains(mockId);

        //then
        verify(operations).queryForObject(anyString(), any(Map.class), any(Class.class));
        Assert.assertFalse(r);
    }

    @Test
    public void deleteByName() {
        //given
        String name = "Mock name";
        when(operations.update(anyString(), any(Map.class))).thenReturn(1);

        //when
        boolean r = sut.deleteByName(name);

        //then
        verify(operations).update(anyString(), any(Map.class));
        Assert.assertTrue(r);

    }

    @Test
    public void deleteByNameCatchesException() {
        //given
        String name = "Mock name";
        when(operations.update(anyString(), any(Map.class))).thenThrow(new RuntimeException());

        //when
        boolean r = sut.deleteByName(name);

        //then
        verify(operations).update(anyString(), any(Map.class));
        Assert.assertFalse(r);

    }

}