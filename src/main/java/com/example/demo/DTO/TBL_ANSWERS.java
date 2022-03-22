package com.example.demo.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TBL_ANSWERS {
	private LocalDateTime instDate;
	private String instUser;
	private LocalDateTime updtDate;
	private String updtUser;
	private int delFgl;
	private int answerNo;
	private String answerTitle;
	private String answerText;
}
