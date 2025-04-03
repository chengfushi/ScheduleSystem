package test.dao;

import com.dao.BasisDao;
import com.utils.JdbcUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BasisDaoTest {

    @Mock
    private JdbcUtilsByDruid jdbcUtilsByDruid;

    @Mock
    private QueryRunner queryRunner;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BasisDao<Object> basisDao;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(jdbcUtilsByDruid.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    public void testUpdate() throws SQLException {
        when(queryRunner.update(any(Connection.class), anyString(), any(Object[].class))).thenReturn(1);

        int result = basisDao.update("UPDATE table SET column = ? WHERE id = ?", "value", 1);

        assertEquals(1, result);
        verify(queryRunner).update(connection, "UPDATE table SET column = ? WHERE id = ?", "value", 1);
    }

    @Test
    public void testUpdateWithException() throws SQLException {
        when(queryRunner.update(any(Connection.class), anyString(), any(Object[].class))).thenThrow(new SQLException("Update failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            basisDao.update("UPDATE table SET column = ? WHERE id = ?", "value", 1);
        });

        assertEquals("数据库更新操作失败", exception.getMessage());
        verify(queryRunner).update(connection, "UPDATE table SET column = ? WHERE id = ?", "value", 1);
    }

    @Test
    public void testQueryMulty() throws SQLException {
        List<Object> expectedList = Arrays.asList(new Object(), new Object());
        when(queryRunner.query(any(Connection.class), anyString(), any(BeanListHandler.class), any(Object[].class))).thenReturn(expectedList);

        List<Object> result = basisDao.queryMulty("SELECT * FROM table", Object.class);

        assertEquals(expectedList, result);
        verify(queryRunner).query(connection, "SELECT * FROM table", new BeanListHandler<>(Object.class));
    }

    @Test
    public void testQueryMultyWithException() throws SQLException {
        when(queryRunner.query(any(Connection.class), anyString(), any(BeanListHandler.class), any(Object[].class))).thenThrow(new SQLException("Query failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            basisDao.queryMulty("SELECT * FROM table", Object.class);
        });

        assertNotNull(exception);
        verify(queryRunner).query(connection, "SELECT * FROM table", new BeanListHandler<>(Object.class));
    }

    @Test
    public void testQuerySingle() throws SQLException {
        Object expectedObject = new Object();
        when(queryRunner.query(any(Connection.class), anyString(), any(BeanHandler.class), any(Object[].class))).thenReturn(expectedObject);

        Object result = basisDao.querySingle("SELECT * FROM table WHERE id = ?", Object.class, 1);

        assertEquals(expectedObject, result);
        verify(queryRunner).query(connection, "SELECT * FROM table WHERE id = ?", new BeanHandler<>(Object.class), 1);
    }

    @Test
    public void testQuerySingleWithException() throws SQLException {
        when(queryRunner.query(any(Connection.class), anyString(), any(BeanHandler.class), any(Object[].class))).thenThrow(new SQLException("Query failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            basisDao.querySingle("SELECT * FROM table WHERE id = ?", Object.class, 1);
        });

        assertNotNull(exception);
        verify(queryRunner).query(connection, "SELECT * FROM table WHERE id = ?", new BeanHandler<>(Object.class), 1);
    }

    @Test
    public void testQueryScalar() throws SQLException {
        Object expectedScalar = "scalarValue";
        when(queryRunner.query(any(Connection.class), anyString(), any(ScalarHandler.class), any(Object[].class))).thenReturn(expectedScalar);

        Object result = basisDao.queryScalar("SELECT COUNT(*) FROM table");

        assertEquals(expectedScalar, result);
        verify(queryRunner).query(connection, "SELECT COUNT(*) FROM table", new ScalarHandler<>());
    }

    @Test
    public void testQueryScalarWithException() throws SQLException {
        when(queryRunner.query(any(Connection.class), anyString(), any(ScalarHandler.class), any(Object[].class))).thenThrow(new SQLException("Query failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            basisDao.queryScalar("SELECT COUNT(*) FROM table");
        });

        assertNotNull(exception);
        verify(queryRunner).query(connection, "SELECT COUNT(*) FROM table", new ScalarHandler<>());
    }
}

