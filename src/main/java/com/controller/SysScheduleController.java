package com.controller;

import com.pojo.SysSchedule;
import com.service.SysScheduleServe;
import com.service.impl.SysScheduleServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author Cheng fu
 * @Date 2025/4/3 21:54
 */
@WebServlet("/Schedule/*")
public class SysScheduleController extends BaseController {
    private SysScheduleServe scheduleService = new SysScheduleServiceImpl();

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get parameters from request
        String title = req.getParameter("title");
        HttpSession session = req.getSession();
        Integer uid = (Integer) session.getAttribute("uid");

        // Create new schedule
        SysSchedule schedule = new SysSchedule();
        schedule.setUid(uid);
        schedule.setTitle(title);
        schedule.setCompleted(0); // Default to not completed

        // Add schedule and send response
        int result = scheduleService.addSchedule(schedule);
        if (result > 0) {
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("fail");
        }
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get schedule ID from request
        Integer sid = Integer.parseInt(req.getParameter("sid"));

        // Delete schedule and send response
        int result = scheduleService.removeSchedule(sid);
        if (result > 0) {
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("fail");
        }
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get parameters from request
        Integer sid = Integer.parseInt(req.getParameter("sid"));
        String title = req.getParameter("title");
        Integer completed = Integer.parseInt(req.getParameter("completed"));

        // Create schedule object with updated values
        SysSchedule schedule = new SysSchedule();
        schedule.setSid(sid);
        schedule.setTitle(title);
        schedule.setCompleted(completed);

        // Update schedule and send response
        int result = scheduleService.updateSchedule(schedule);
        if (result > 0) {
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("fail");
        }
    }

    public void find(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user ID from session
        HttpSession session = req.getSession();
        Integer uid = (Integer) session.getAttribute("uid");

        // Get all schedules for the user
        List<SysSchedule> schedules = scheduleService.findSchedulesByUid(uid);
        
    }
}
