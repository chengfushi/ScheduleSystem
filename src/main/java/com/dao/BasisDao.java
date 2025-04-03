package com.dao;

import com.utils.JdbcUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 这是一个用于操作数据库的一个基类，所有的dao类都需要继承这个类
 * @Author Cheng fu
 * @Date 2025/4/3 18:38
 *
 */
public class BasisDao<T> {
    private QueryRunner queryRunner = new QueryRunner();
    
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JdbcUtilsByDruid.getConnection();
            return queryRunner.update(connection, sql, parameters);
        } catch (SQLException e) {
            // 记录异常日志（推荐使用日志框架）
            System.err.println("SQL执行失败: " + e.getMessage());
            throw new RuntimeException("数据库更新操作失败", e);
        } finally {
            // 确保connection不为null时才关闭
            if (connection != null) {
                JdbcUtilsByDruid.close(null, null, connection);
            }
        }
    }
    
    public List<T> queryMulty(String sql, Class clazz, Object... parameters) {
        Connection connection = null;
        
        try {
            connection = JdbcUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtilsByDruid.close(null, null, connection);
        }
    }
    
    public T querySingle(String sql, Class clazz,Object... parameters) {
        Connection connection = null;
        
        try {
            connection = JdbcUtilsByDruid.getConnection();
            return queryRunner.query(connection,sql,new BeanHandler<T>(clazz),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JdbcUtilsByDruid.close(null,null,connection);
        }
    }
    
    public Object queryScalar(String sql,Object... parameters) {
        Connection connection = null;
        
        try {
            connection = JdbcUtilsByDruid.getConnection();
            return queryRunner.query(connection,sql,new ScalarHandler<>(),parameters);
        } catch (SQLException e) {
            throw new RuntimeException();
        }finally {
            JdbcUtilsByDruid.close(null,null,connection);
        }
    }
}

