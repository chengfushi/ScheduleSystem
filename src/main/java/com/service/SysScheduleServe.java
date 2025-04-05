package com.service;

import com.pojo.SysSchedule;
import java.util.List;

/**
 * @Author Cheng fu
 * @Date 2025/4/5 21:51
 */
public interface SysScheduleServe {
    // Add a new schedule
    int addSchedule(SysSchedule schedule);
    
    // Delete a schedule by id
    int removeSchedule(Integer sid);
    
    // Update a schedule
    int updateSchedule(SysSchedule schedule);
    
    // Find schedule by id
    SysSchedule findScheduleById(Integer sid);
    
    // Find all schedules for a user
    List<SysSchedule> findSchedulesByUid(Integer uid);
}
