package com.example.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DAO.PostDAO;
import com.example.demo.DTO.TBL_ANSWERS;
import com.example.demo.DTO.TBL_POST;
import com.example.demo.forms.AnswerForm;
import com.example.demo.forms.BlogForm;
import com.example.demo.services.BlogService;


@Controller
public class SinglePostController {
	@Autowired // 依存性の注入
	private PostDAO postDAO;

	private BlogService blogService;
	@Autowired
	public SinglePostController(BlogService blogService){
		this.blogService = blogService;
	}

	@ModelAttribute
	BlogForm setUpForm() {
		return new BlogForm();
	}

	@ModelAttribute
	AnswerForm setAnswerForm() {
		return new AnswerForm();
	}

	@RequestMapping(value = "/single")
	//Blog単一ページ
	public String getItem(HttpServletRequest request, @RequestParam(name = "postNo", required = false) int postNo, Model model, AnswerForm answerForm) {
		TBL_POST param = new TBL_POST();
		param.setPostNo(postNo);
		answerForm.setAnswerNo(postNo);
		List<TBL_POST> data = postDAO.search(param); // postDAOのfindPostを実行
		model.addAttribute("data", data.get(0));
		model.addAttribute("AnswerForm", answerForm);
		//解答を取得
		List<TBL_ANSWERS> answers = blogService.getAnswers(answerForm);
		model.addAttribute("answers", answers);
		return "single-post";
	}

	@RequestMapping(value = "/answer")
	//解答インサート
	public String insertAnswer(HttpServletRequest request, Model model, AnswerForm answerForm) {
		answerForm.setAnswerNo(Integer.parseInt(request.getParameter("answerNo")));
		blogService.insertAnswer(model, answerForm);
		return "hello";
	}

}

