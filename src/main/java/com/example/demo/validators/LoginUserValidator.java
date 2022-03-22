package com.example.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import com.example.demo.forms.LoginUserForm;
import com.example.demo.services.BlogService;

@Component
public class LoginUserValidator {

	private BlogService blogService;
	@Autowired
	public LoginUserValidator(BlogService blogService){
		this.blogService = blogService;
	}

	public void validator(LoginUserForm form, BindingResult result, Model model) {
			boolean isUnique = blogService.loginChecker(form);
			if (StringUtils.isEmpty(form.getUserId()) || StringUtils.isEmpty(form.getUserPWD())) {
				result.rejectValue("userPWD", null,"Pleace enter your UserID and Password.");
			}
			if (isUnique == true) {
				result.rejectValue("userId", null, "You have entered an invalid UserID or Password.");
			}
	}
}
