package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.forms.BlogForm;

@Controller
public class LogoutController {

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model, @ModelAttribute("BlogForm") BlogForm form) {
		//引数に"false"を指定した場合、セッションが存在しない場合にはnullが帰ってくる
		HttpSession session = request.getSession(false);
		if (session != null) {
			//sessionを終了
			session.invalidate();
		}
		return "redirect:index";
	}

}
