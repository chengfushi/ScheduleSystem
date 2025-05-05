package com.schedule.service;


import com.schedule.dao.UserDao;
import com.schedule.pojo.User;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: TODO
 * @date 2025/4/29 18:05
 */
public interface UserService {
	public boolean login(String username,String password);


}
