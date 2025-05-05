package com.schedule.dao;


import com.schedule.pojo.Schedule;

import java.util.List;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 日程管理的数据访问层，提供对日程表的基本CRUD操作
 * @date 2025/5/2 23:20
 */
public class ScheduleDao extends BasicDao<Schedule> {

    /**
     * 添加新的日程安排
     *
     * @param schedule 日程对象
     * @return 受影响的行数
     */
    public int addSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedules (title, description, start_time, end_time, user_id) VALUES (?, ?, ?, ?, ?)";
        return update(sql, schedule.getTitle(), schedule.getDescription(),
                schedule.getStart_time(), schedule.getEnd_time(), schedule.getUser_id());
    }

    /**
     * 根据日程ID删除日程
     *
     * @param scheduleId 日程ID
     * @return 受影响的行数
     */
    public int deleteSchedule(int scheduleId) {
        String sql = "DELETE FROM schedules WHERE schedule_id = ?";
        return update(sql, scheduleId);
    }

    /**
     * 更新日程信息
     *
     * @param schedule 日程对象
     * @return 受影响的行数
     */
    public int updateSchedule(Schedule schedule) {
        String sql = "UPDATE schedules SET title = ?, description = ?, start_time = ?, end_time = ? WHERE schedule_id = ?";
        return update(sql, schedule.getTitle(), schedule.getDescription(),
                schedule.getStart_time(), schedule.getEnd_time(), schedule.getSchedule_id());
    }

    /**
     * 根据日程ID查询单个日程
     *
     * @param scheduleId 日程ID
     * @return 日程对象
     */
    public Schedule getScheduleById(int scheduleId) {
        String sql = "SELECT * FROM schedules WHERE schedule_id = ?";
        return singleQuery(sql, Schedule.class, scheduleId);
    }

    /**
     * 获取指定用户的所有日程
     *
     * @param userId 用户ID
     * @return 日程列表
     */
    public List<Schedule> getSchedulesByUserId(int userId) {
        String sql = "SELECT * FROM schedules WHERE user_id = ? ORDER BY start_time";
        return multiQuery(sql, Schedule.class, userId);
    }

    /**
     * 获取指定用户在某个时间范围内的日程
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 日程列表
     */
    public List<Schedule> getSchedulesByTimeRange(int userId, String startTime, String endTime) {
        String sql = "SELECT * FROM schedules WHERE user_id = ? AND start_time >= ? AND end_time <= ? ORDER BY start_time";
        return multiQuery(sql, Schedule.class, userId, startTime, endTime);
    }
}
