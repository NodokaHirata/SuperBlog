package com.example.demo.validators;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import com.example.demo.forms.PostForm;

@Component


public class PostValidator {

	public void validator(PostForm form, BindingResult result, Model model) {
		if (StringUtils.isEmpty(form.getName())) {
			result.rejectValue("name", null,"名前は必須項目です。");
		} else if (form.getName().length() > 45) {
			result.rejectValue("name", null, "名前は45文字以内に設定してください。");
		}
		if (StringUtils.isEmpty(form.getTitle())) {
			result.rejectValue("title", null, "タイトルは必須項目です。");
		} else if (form.getTitle().length() > 45) {
			result.rejectValue("title", null, "タイトルは45文字以内に設定してください。");
		}
		if (StringUtils.isEmpty(form.getText())) {
			result.rejectValue("text", null, "本文は必須項目です。");
		} else if (form.getText().length() > 65535) {
			result.rejectValue("text", null, "本文が長すぎます。65,535文字以内に設定してください。");
		}
	}
}
