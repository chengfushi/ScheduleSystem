package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Cheng fu
 * @Date 2025/4/3 21:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysUser {
    private Integer uid;
    private String username;
    private String userPwd;
}
