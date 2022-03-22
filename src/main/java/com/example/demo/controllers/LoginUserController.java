package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.loginUserInfo;
import com.example.demo.forms.LoginUserForm;
import com.example.demo.forms.PostForm;
import com.example.demo.validators.LoginUserValidator;

@Controller
public class LoginUserController {

	@Autowired
	private LoginUserValidator loginUserValidator;

	@RequestMapping("/loginUser")
	//ユーザーログイン画面表示
	public String showLoginPage(HttpServletRequest request,HttpServletResponse response, Model model, @ModelAttribute("LoginUserForm") LoginUserForm loginForm, BindingResult result){
		//sessionがある（ログイン済）場合はブログに遷移（URLに直打ちされた場合ログイン画面に遷移しないよう制御）
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserInfo") != null) {
			return "redirect:index";
		}
		model.addAttribute(loginForm);
		return "loginUser";
	}

	@RequestMapping(value = "/login")
	//Login
	public String login(HttpServletRequest request, Model model, @ModelAttribute("LoginUserForm") LoginUserForm loginForm, BindingResult result, @ModelAttribute("PostForm") PostForm form){
		//ログインできるかのチェック
		loginUserValidator.validator(loginForm,result,model);
		//エラーがあればログイン画面に遷移
		if(result.hasErrors()) {
			model.addAttribute("LoginUserForm",loginForm);
			return "loginUser";
		}
		//validation 突破したらセッションに登録処理
		//sessionインスタンス作成(引数なしor tureの場合セッションがなければ新規開始、セッション開始されてればそれ使う)
		HttpSession session = request.getSession();
		//session登録用のDTOインスタンス作成
		loginUserInfo loginUserInfo = new loginUserInfo();
		//session登録用DTOインスタンスに登録したいデータが入っているformをセット
		loginUserInfo.setUserId(loginForm.getUserId());
		//register for a session
		session.setAttribute("loginUserInfo",loginUserInfo);
		//index以外の画面から遷移してきた場合
		if (!StringUtils.isEmpty(loginForm.getUrl())) {
			return "redirect:" + loginForm.getUrl();
		}
		//indexからのログインの場合マイページへ（暫定では一覧画面）
		return "redirect:index";
	}
}
