package com.schedule.controller;


import com.schedule.pojo.User;
import com.schedule.service.UserService;
import com.schedule.service.impi.UserServiceImpi;
import com.schedule.service.userservice.UserLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: TODO
 * @date 2025/4/29 17:54
 */
@WebServlet("/user/*")
public class UserController extends BasicController{
	private final UserLogin userLogin = new UserLogin();

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userLogin.loginAndGetUser(username, password);
		if(user != null){
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+"/index.html");
		}else {
			response.sendRedirect(request.getContextPath()+"/login.html");
		}
	}


}
