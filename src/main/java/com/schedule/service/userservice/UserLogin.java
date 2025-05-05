package com.schedule.service.userservice;


import com.schedule.dao.UserDao;
import com.schedule.pojo.User;
import com.schedule.service.impi.UserServiceImpi;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: TODO
 * @date 2025/5/2 18:51
 */
public class UserLogin extends UserServiceImpi {
	private final UserDao userDao = new UserDao();

	@Override
	public boolean login(String username, String password) {
		return loginAndGetUser(username, password) != null;
	}

	/**
	 * 用户登录并获取用户信息
	 * 如果用户不存在则自动注册
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户对象，如果登录失败返回null
	 */
	public User loginAndGetUser(String username, String password) {
		// 先查询用户是否存在
		String querySql = "select * from user where username=? and password=?";
		User user = (User) userDao.singleQuery(querySql, User.class, username, password);
		
		if (user == null) {
			// 用户不存在，执行注册
			String insertSql = "insert into user(username, password) values(?,?)";
			userDao.update(insertSql, username, password);
			
			// 重新查询获取完整的用户信息
			user = (User) userDao.singleQuery(querySql, User.class, username, password);
		}
		return user;
	}
}
