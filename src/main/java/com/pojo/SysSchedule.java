package com.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @Author Cheng fu
 * @Date 2025/4/3 21:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysSchedule {
    private Integer sid;
    private Integer uid;
    private String title;
    private Integer completed;
}
