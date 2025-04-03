package test.dao;

import com.utils.JdbcUtilsByDruid;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author Cheng fu
 * @Date 2025/4/3 19:07
 */
public class JdbcUtilsTest {
    
    @Mock
    private DataSource dataSource;
    
    @Mock
    private Statement statement;
    
    @Mock
    private ResultSet resultSet;
    @Mock
    private Connection connection;
    
    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }
    
    @AfterEach
    public void tearDown() {
        // 清理操作
    }
    
    @Test
    public void testGetConnection() throws SQLException {
        Connection conn = JdbcUtilsByDruid.getConnection();
        assertNotNull(conn);
        verify(dataSource).getConnection();
    }
    
    @Test
    public void testClose() throws SQLException {
        JdbcUtilsByDruid.close(resultSet, statement, connection);
        verify(resultSet).close();
        verify(statement).close();
        verify(connection).close();
    }
    
    @Test
    public void testCloseWithNulls() throws SQLException {
        JdbcUtilsByDruid.close(null, null, null);
        // 预期不会抛出异常
    }
    
    @Test
    public void testGetConnectionWithException() {
        try {
            when(dataSource.getConnection()).thenThrow(new SQLException("Connection failed"));
            assertThrows(RuntimeException.class, () -> JdbcUtilsByDruid.getConnection());
        } catch (SQLException e) {
            // 不应该发生
        }
    }
}
