package com.schedule.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 这是数据库操作的一个工具类，为dao的操作提供一些方法
 * @date 2025/4/28 11:18
 */
public class DbUtilsByDruid {
	private static DataSource dataSource;

	static {
		Properties properties = new Properties();

		try {
			// 尝试加载配置文件
			System.out.println("正在加载druid.properties配置文件...");
			properties.load(DbUtilsByDruid.class.getClassLoader().getResourceAsStream("druid.properties"));
			if (properties.isEmpty()) {
				throw new RuntimeException("无法加载druid.properties配置文件，请确保文件存在且内容正确");
			}
			System.out.println("成功加载配置文件，数据库URL: " + properties.getProperty("url"));
			
			// 创建数据源
			System.out.println("正在初始化数据库连接池...");
			dataSource = DruidDataSourceFactory.createDataSource(properties);
			System.out.println("数据库连接池初始化成功");
		} catch (Exception e) {
			System.err.println("初始化数据库连接池失败：" + e.getMessage());
			if (properties != null) {
				System.err.println("当前配置信息：");
				System.err.println("url=" + properties.getProperty("url"));
				System.err.println("username=" + properties.getProperty("username"));
				System.err.println("driverClassName=" + properties.getProperty("driverClassName"));
			}
			e.printStackTrace();
			throw new RuntimeException("数据库连接池初始化失败", e);
		}
	}

	public static Connection getConnection() throws Exception {
		try {
			System.out.println("正在获取数据库连接...");
			Connection connection = dataSource.getConnection();
			if (connection == null) {
				throw new RuntimeException("无法获取数据库连接，请检查数据库配置和连接状态");
			}
			System.out.println("成功获取数据库连接");
			return connection;
		} catch (Exception e) {
			System.err.println("获取数据库连接失败：" + e.getMessage());
			throw new RuntimeException("获取数据库连接失败，请检查数据库配置和连接状态", e);
		}
	}

	public static void close(Connection connection, ResultSet resultSet, Statement statement) {
		try {
			if (connection != null) {
				connection.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			System.err.println("关闭数据库资源失败：" + e.getMessage());
			e.printStackTrace();
		}
	}
}
