package com.service;

import com.pojo.SysUser;

/**
 * 该接口定义了以SysUser表格为核心的业务
 * @Author Cheng fu
 * @Date 2025/4/5 21:49
 */
public interface SysUserService {
    boolean register(String username, String password);
    SysUser login(String username, String password); // 新增方法
    int addUser(SysUser user);
    int removeUser(Integer uid);
    int updateUser(SysUser user);
    SysUser findUserById(Integer uid);
}