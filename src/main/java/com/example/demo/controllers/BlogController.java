package com.example.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.TBL_POST;
import com.example.demo.DTO.loginUserInfo;
import com.example.demo.forms.BlogForm;
import com.example.demo.services.BlogService;

@Controller
public class BlogController {

	private BlogService blogService;
	@Autowired
	public BlogController(BlogService blogService){
		this.blogService = blogService;
	}

	@RequestMapping("/index")
	//Blog一覧
	public String getData(HttpServletRequest request, Model model, @ModelAttribute("BlogForm")BlogForm form) {
		//ログイン状態かをセッションで確認
		//引数なし or falseで登録済みのセッションがあれば使うsessionを作成
		HttpSession session = request.getSession();
		if (session != null) {
			//sessionを取り出して変数に代入 getAttributeの戻り値はobject型のためstringにキャスト
			loginUserInfo loginId = (loginUserInfo)session.getAttribute("loginUserInfo");
			//modelに詰めてviewに渡す
			model.addAttribute("loginId",loginId);
		}
		//全件取得
		List<TBL_POST> list = blogService.getData(model,form);
		model.addAttribute("BlogForm", form);
		model.addAttribute("list", list);
		return "index";
	}

	@RequestMapping(value = "/search")
	//Blog検索
	public String goSearch(HttpServletRequest request, Model model, BlogForm form) {
		//検索ワードを引数に渡す
		List<TBL_POST> list = blogService.getData(model,form);
        model.addAttribute("BlogForm", form); // 検索フォーム用インスタンス
        model.addAttribute("list",list);
        return "index";

    }
}
