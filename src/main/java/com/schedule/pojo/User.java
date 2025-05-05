package com.schedule.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 这是一个用户类
 * @date 2025/4/28 11:22
 */
@Data
@Builder
@AllArgsConstructor
public class User {
	private int id;
	private String username;
	private String password;
}
