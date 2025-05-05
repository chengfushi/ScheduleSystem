package com.schedule.controller;


import com.google.gson.Gson;
import com.schedule.pojo.Schedule;
import com.schedule.service.ScheduleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 日程管理的Web接口层，处理前端的HTTP请求
 * @date 2025/5/2 23:19
 */
@WebServlet("/schedule/*")
public class ScheduleController extends BasicController {
    private final ScheduleService scheduleService = new ScheduleService();
    private final Gson gson = new Gson();

    public void create(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Schedule schedule = gson.fromJson(request.getReader(), Schedule.class);
        int userId = getUserIdFromSession(request);
        schedule.setUser_id(userId);

        boolean success = scheduleService.createSchedule(schedule);
        sendJsonResponse(response, success ? "{\"success\": true}" : "{\"success\": false}");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Schedule schedule = gson.fromJson(request.getReader(), Schedule.class);
        int userId = getUserIdFromSession(request);

        boolean success = scheduleService.updateSchedule(schedule, userId);
        sendJsonResponse(response, success ? "{\"success\": true}" : "{\"success\": false}");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        int userId = getUserIdFromSession(request);

        boolean success = scheduleService.deleteSchedule(scheduleId, userId);
        sendJsonResponse(response, success ? "{\"success\": true}" : "{\"success\": false}");
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        int userId = getUserIdFromSession(request);

        Schedule schedule = scheduleService.getScheduleDetail(scheduleId, userId);
        sendJsonResponse(response, gson.toJson(schedule));
    }

    public void list(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int userId = getUserIdFromSession(request);
        List<Schedule> schedules = scheduleService.getUserSchedules(userId);
        sendJsonResponse(response, gson.toJson(schedules));
    }

    public void range(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int userId = getUserIdFromSession(request);
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        List<Schedule> schedules = scheduleService.getSchedulesByTimeRange(userId, startTime, endTime);
        sendJsonResponse(response, gson.toJson(schedules));
    }

    private void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private int getUserIdFromSession(HttpServletRequest request) {
        // 从Session中获取用户ID，这里简单实现，实际项目中需要完善用户认证逻辑
        Object userId = request.getSession().getAttribute("userId");
        return userId != null ? (Integer) userId : 0;
    }
}
