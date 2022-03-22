package com.example.demo.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TBL_POST {
	private LocalDateTime instDate;
	private String instUser;
	private LocalDateTime updtDate;
	private String updtUser;
	private int delFgl;
	private int postNo;
	private String postTitle;
	private String postText;


	private String searchText;
}
