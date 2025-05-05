package com.schedule.service;

import com.schedule.dao.ScheduleDao;
import com.schedule.pojo.Schedule;
import java.util.List;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 日程管理的业务逻辑层，处理日程相关的业务操作
 * @date 2025/5/2 23:21
 */
public class ScheduleService {
    private final ScheduleDao scheduleDao = new ScheduleDao();

    /**
     * 创建新的日程
     *
     * @param schedule 日程对象
     * @return 是否创建成功
     */
    public boolean createSchedule(Schedule schedule) {
        if (schedule == null || schedule.getTitle() == null || schedule.getTitle().trim().isEmpty()) {
            return false;
        }
        return scheduleDao.addSchedule(schedule) > 0;
    }

    /**
     * 删除日程
     *
     * @param scheduleId 日程ID
     * @param userId     用户ID（用于权限验证）
     * @return 是否删除成功
     */
    public boolean deleteSchedule(int scheduleId, int userId) {
        Schedule schedule = scheduleDao.getScheduleById(scheduleId);
        if (schedule == null || schedule.getUser_id() != userId) {
            return false;
        }
        return scheduleDao.deleteSchedule(scheduleId) > 0;
    }

    /**
     * 更新日程信息
     *
     * @param schedule 日程对象
     * @param userId   用户ID（用于权限验证）
     * @return 是否更新成功
     */
    public boolean updateSchedule(Schedule schedule, int userId) {
        if (schedule == null || schedule.getSchedule_id() <= 0) {
            return false;
        }
        Schedule existingSchedule = scheduleDao.getScheduleById(schedule.getSchedule_id());
        if (existingSchedule == null || existingSchedule.getUser_id() != userId) {
            return false;
        }
        return scheduleDao.updateSchedule(schedule) > 0;
    }

    /**
     * 获取单个日程详情
     *
     * @param scheduleId 日程ID
     * @param userId     用户ID（用于权限验证）
     * @return 日程对象
     */
    public Schedule getScheduleDetail(int scheduleId, int userId) {
        Schedule schedule = scheduleDao.getScheduleById(scheduleId);
        if (schedule != null && schedule.getUser_id() == userId) {
            return schedule;
        }
        return null;
    }

    /**
     * 获取用户的所有日程
     *
     * @param userId 用户ID
     * @return 日程列表
     */
    public List<Schedule> getUserSchedules(int userId) {
        return scheduleDao.getSchedulesByUserId(userId);
    }

    /**
     * 获取用户在指定时间范围内的日程
     *
     * @param userId    用户ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 日程列表
     */
    public List<Schedule> getSchedulesByTimeRange(int userId, String startTime, String endTime) {
        return scheduleDao.getSchedulesByTimeRange(userId, startTime, endTime);
    }
}
