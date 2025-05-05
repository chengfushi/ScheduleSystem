package com.schedule.dao;


import com.schedule.utils.DbUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDao<T> {
    private final QueryRunner queryRunner = new QueryRunner();

    public int update(String sql, Object... parameters) {
        Connection connection = null;
        int rows = 0;
        try {
            connection = DbUtilsByDruid.getConnection();
            rows = queryRunner.update(connection, sql, parameters);
        } catch (SQLException e) {
            System.err.println("执行更新操作失败：" + e.getMessage());
            System.err.println("SQL语句：" + sql);
            e.printStackTrace();
            throw new RuntimeException("数据库更新操作失败", e);
        } catch (Exception e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("数据库操作异常", e);
        } finally {
            DbUtilsByDruid.close(connection, null, null);
        }
        return rows;
    }

    public List<T> multiQuery(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        List<T> list = null;
        try {
            connection = DbUtilsByDruid.getConnection();
            list = queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            System.err.println("执行多行查询操作失败：" + e.getMessage());
            System.err.println("SQL语句：" + sql);
            e.printStackTrace();
            throw new RuntimeException("数据库查询操作失败", e);
        } catch (Exception e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("数据库操作异常", e);
        } finally {
            DbUtilsByDruid.close(connection, null, null);
        }
        return list;
    }

    public T singleQuery(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        T t = null;
        try {
            connection = DbUtilsByDruid.getConnection();
            t = queryRunner.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            System.err.println("执行单行查询操作失败：" + e.getMessage());
            System.err.println("SQL语句：" + sql);
            e.printStackTrace();
            throw new RuntimeException("数据库查询操作失败", e);
        } catch (Exception e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("数据库操作异常", e);
        } finally {
            DbUtilsByDruid.close(connection, null, null);
        }
        return t;
    }

    public Object scalarQuery(String sql, Object... parameters) {
        Connection connection = null;
        Object result = null;
        try {
            connection = DbUtilsByDruid.getConnection();
            result = queryRunner.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            System.err.println("执行标量查询操作失败：" + e.getMessage());
            System.err.println("SQL语句：" + sql);
            e.printStackTrace();
            throw new RuntimeException("数据库查询操作失败", e);
        } catch (Exception e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("数据库操作异常", e);
        } finally {
            DbUtilsByDruid.close(connection, null, null);
        }
        return result;
    }
}
