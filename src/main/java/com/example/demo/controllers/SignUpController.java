package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.loginUserInfo;
import com.example.demo.forms.SignUpForm;
import com.example.demo.services.BlogService;
import com.example.demo.validators.SignUpValidator;

@Controller

public class SignUpController {

	private BlogService blogService;
	@Autowired
	public SignUpController(BlogService blogService){
		this.blogService = blogService;
	}
	@Autowired
	private SignUpValidator signUpValidator;

	@RequestMapping("/signUp")
	//ユーザー登録画面表示
	public String showSignUpPage (HttpServletRequest request, Model model, @ModelAttribute("SignUpForm") SignUpForm form, BindingResult result) {
		HttpSession session = request.getSession();
		//requestからパラメタ取得
		String url = request.getParameter("signUp");
		model.addAttribute("SignUpForm", form);
		return "signUp";
	}

	@RequestMapping(value = "/regist")
	//ユーザー登録
	public String regist (HttpServletRequest request, Model model, @ModelAttribute("SignUpForm") SignUpForm form, BindingResult result) {
		//入力チェック
		signUpValidator.validator(form, result, model);
		//エラーの場合stay
		if (result.hasErrors()) {
			model.addAttribute("SignUpForm", form);
			return "signUp";
		}
		//エラーない場合登録処理
		blogService.registUser(model,form);
		//登録後セッションに登録処理
		//sessionインスタンス作成
		HttpSession session = request.getSession();
		//session登録用のDTOインスタンス作成
		loginUserInfo loginUserInfo = new loginUserInfo();
		//session登録用DTOインスタンスに登録したいデータが入っているformをセット
		loginUserInfo.setUserId(form.getUserId());
		//register for a session
		session.setAttribute("loginUserInfo",loginUserInfo);
		//index以外の画面から遷移してきた場合
		if(!StringUtils.isEmpty(form.getUrl())) {
			return "redirect:" + form.getUrl();
		}
		//indexからの場合マイページへ（暫定では一覧画面）
		return "redirect:index";
	}

}
