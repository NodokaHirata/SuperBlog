package com.example.demo.forms;

import java.io.Serializable;

import lombok.Data;

//ユーザー登録
@Data
public class SignUpForm implements Serializable{
	private String userId;
	private String userPWD;
	private String url;
}
