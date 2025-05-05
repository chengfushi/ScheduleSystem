package com.schedule.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: TODO
 * @date 2025/5/2 23:20
 */
@Data
@Builder
@AllArgsConstructor
public class Schedule {
	private int schedule_id;
	private String title;
	private String description;
	private LocalDate start_time;
	private LocalDate end_time;
	private int user_id;
}
