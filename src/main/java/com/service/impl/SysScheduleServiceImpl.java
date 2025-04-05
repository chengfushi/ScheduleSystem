package com.service.impl;

import com.dao.SysScheduleDao;
import com.pojo.SysSchedule;
import com.service.SysScheduleServe;
import java.util.List;

/**
 * @Author Cheng fu
 * @Date 2025/4/5 21:51
 */
public class SysScheduleServiceImpl implements SysScheduleServe {
    private SysScheduleDao scheduleDao = new SysScheduleDao();
    
    @Override
    public int addSchedule(SysSchedule schedule) {
        String sql = "INSERT INTO sys_schedule (uid, title, completed) VALUES (?, ?, ?)";
        return scheduleDao.update(sql, schedule.getUid(), schedule.getTitle(), schedule.getCompleted());
    }
    
    @Override
    public int removeSchedule(Integer sid) {
        String sql = "DELETE FROM sys_schedule WHERE sid = ?";
        return scheduleDao.update(sql, sid);
    }
    
    @Override
    public int updateSchedule(SysSchedule schedule) {
        String sql = "UPDATE sys_schedule SET title = ?, completed = ? WHERE sid = ?";
        return scheduleDao.update(sql, schedule.getTitle(), schedule.getCompleted(), schedule.getSid());
    }
    
    @Override
    public SysSchedule findScheduleById(Integer sid) {
        String sql = "SELECT * FROM sys_schedule WHERE sid = ?";
        return scheduleDao.querySingle(sql, SysSchedule.class, sid);
    }
    
    @Override
    public List<SysSchedule> findSchedulesByUid(Integer uid) {
        String sql = "SELECT * FROM sys_schedule WHERE uid = ?";
        return scheduleDao.queryMulty(sql, SysSchedule.class, uid);
    }
}
