package com.schedule.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 控制器基类
 * @date 2025/4/28 10:57
 */
public class BasicController extends HttpServlet {

	/**
	 * @description: 这是一个基类，通过反射来调用子类中的方法
	 * @author Chrng Fu
	 * @date 2025/4/28 11:00
	 * @version 1.0
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String url = req.getRequestURI();
		String[] split = url.split("/");
		String methodName = split[split.length - 1];

		try {

			Class<? extends BasicController> clazz = this.getClass();

			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, req, resp);

		}catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误");
		}

	}
}
