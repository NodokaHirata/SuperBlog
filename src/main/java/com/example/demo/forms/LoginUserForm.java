package com.example.demo.forms;

import lombok.Data;

//ユーザーログイン
@Data
public class LoginUserForm {
	private String userId;
	private String userPWD;
	private String url;
}

