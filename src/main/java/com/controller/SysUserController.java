package com.controller;

import com.pojo.SysUser;
import com.service.SysUserService;
import com.service.impl.SysUserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller for handling user-related requests
 */
@WebServlet("/User/*")
public class SysUserController extends BaseController {
    private SysUserService userService = new SysUserServiceImpl();
    
    /**
     * Handle user registration
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        
        // Validate input
        if (username == null || password == null || password2 == null ||
            username.trim().isEmpty() || password.trim().isEmpty() || !password.equals(password2)) {
            resp.sendRedirect("/regist.html");
            return;
        }
        
        // Attempt registration
        boolean success = userService.register(username, password);
        if (success) {
            resp.sendRedirect("/login.html");
        } else {
            resp.sendRedirect("/regist.html");
        }
    }
    
    /**
     * Handle user login
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        // Validate input
        if (username == null || password == null ||
            username.trim().isEmpty() || password.trim().isEmpty()) {
            resp.sendRedirect("/login.html");
            return;
        }
        
        // Attempt login
        SysUser user = userService.login(username, password);
        if (user != null) {
            // Store user info in session
            HttpSession session = req.getSession();
            session.setAttribute("uid", user.getUid());
            session.setAttribute("username", user.getUsername());
            
            // Redirect to dashboard
            resp.sendRedirect("/dashboard.html");
        } else {
            resp.sendRedirect("/login.html");
        }
    }
}
