

package com.example.demo.forms;

import lombok.Data;

//Blog 投稿データインサート用form

@Data
public class PostForm {
	private String name;
	private String title;
	private String text;
}
