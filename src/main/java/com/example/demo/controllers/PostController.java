package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.loginUserInfo;
import com.example.demo.forms.BlogForm;
import com.example.demo.forms.LoginUserForm;
import com.example.demo.forms.PostForm;
import com.example.demo.forms.SignUpForm;
import com.example.demo.services.BlogService;
import com.example.demo.validators.PostValidator;

@Controller
public class PostController {
	private BlogService blogService;
	@Autowired
	public PostController(BlogService blogService){
		this.blogService = blogService;
	}

	@Autowired
	private PostValidator postValidator;

	@ModelAttribute
	BlogForm setUpForm() {
		return new BlogForm();
	}

	@RequestMapping("/post")
	//Blog投稿画面
	public String PostBlog(HttpServletRequest request, Model model, @ModelAttribute("PostForm") PostForm form, BindingResult result, @ModelAttribute("LoginUserForm") LoginUserForm loginForm, SignUpForm signUpForm) {
		//html側でログインしているかの判断のためにセッション作成
		HttpSession session = request.getSession();
		//sessionを取り出して変数に代入 getAttributeの戻り値はobject型のためstringにキャスト
		loginUserInfo loginId = (loginUserInfo)session.getAttribute("loginUserInfo");
		//loginForm.setUrl("post");
		//modelに詰めてviewに渡す
		model.addAttribute("loginId",loginId);
		model.addAttribute("PostForm", form); // 投稿フォーム用インスタンス
		model.addAttribute("LoginUserForm", loginForm);
		model.addAttribute("SignUpForm", signUpForm);
		return "post";
	}

	@RequestMapping(value = "/insert")
	//インサート
	public String insert(HttpServletRequest request, Model model, @ModelAttribute("PostForm") PostForm form, BindingResult result, @ModelAttribute("LoginUserForm") LoginUserForm loginForm, SignUpForm signUpForm) {
		//Validation check
		postValidator.validator(form, result, model);
		if (result.hasErrors()) {
			model.addAttribute("PostForm", form);
			return "post";
		}
		blogService.insertData(model,form);
		//以下エラーがない場合confirm画面作成して変更
        return "redirect:post";
	}
}
