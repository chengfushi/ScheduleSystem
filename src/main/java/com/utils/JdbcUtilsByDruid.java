package com.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 这是一个工具类，用于连接数据库
 * @Author Cheng fu
 * @Date 2025/4/3 18:53
 */
public class JdbcUtilsByDruid {
    private static DataSource dataSource;
    
    //创建静态方法连接数据库
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\main\\resources\\mysql.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    //关闭连接
    public static void close(ResultSet resultSet,Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
