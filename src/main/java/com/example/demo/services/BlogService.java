package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.DAO.AnswerDAO;
import com.example.demo.DAO.PostDAO;
import com.example.demo.DAO.SignUpDAO;
import com.example.demo.DTO.TBL_ANSWERS;
import com.example.demo.DTO.TBL_POST;
import com.example.demo.DTO.USER;
import com.example.demo.forms.AnswerForm;
import com.example.demo.forms.BlogForm;
import com.example.demo.forms.LoginUserForm;
import com.example.demo.forms.PostForm;
import com.example.demo.forms.SignUpForm;

@Service
public class BlogService {

	@Autowired // 依存性の注入
	private PostDAO postDAO;

	@Autowired // 依存性の注入
	private SignUpDAO signUpDAO;

	@Autowired
	private AnswerDAO answerDAO;

	//serch
	public List<TBL_POST> getData (Model model, BlogForm form) {
		TBL_POST param = new TBL_POST();
		//検索ワードをセット
		param.setSearchText(form.getSearchText());
        List<TBL_POST> list = postDAO.search(param);

        return list;
	}

	//post
	public void insertData (Model model, PostForm form) {
		TBL_POST param = new TBL_POST();
		param.setInstUser(form.getName());
		param.setPostTitle(form.getTitle());
		param.setPostText(form.getText());
		postDAO.insert(param);
	}

	//user registation check
	public boolean uniqueChecker (SignUpForm form, int itemFlg) {
		USER param = new USER();
		boolean result = false;

		if (itemFlg == 0) {
			param.setUserId(form.getUserId());
			result = signUpDAO.uniqueChecker(param);
		}
		if (itemFlg == 1) {
			param.setUserPWD(form.getUserPWD());
			result = signUpDAO.uniqueChecker(param);
		}
		return result;
	}

	//ユーザー登録
	public void registUser (Model model, SignUpForm form) {
		USER param = new USER();
		param.setUserId(form.getUserId());
		param.setUserPWD(form.getUserPWD());
		signUpDAO.regist(param);
	}

	//Login Validation check
	public boolean loginChecker (LoginUserForm loginForm) {
		USER param = new USER();
		boolean result = false;

		param.setUserId(loginForm.getUserId());
		param.setUserPWD(loginForm.getUserPWD());
		result = signUpDAO.uniqueChecker(param);
		return result;
	}

	//Insert answers
	public void insertAnswer(Model model, AnswerForm answerForm) {
		TBL_ANSWERS param = new TBL_ANSWERS();
		param.setAnswerNo(answerForm.getAnswerNo());
		param.setInstUser(answerForm.getName());
		param.setAnswerTitle(answerForm.getTitle());
		param.setAnswerText(answerForm.getText());
		answerDAO.insert(param);
	}
	//Get answers
	public List<TBL_ANSWERS> getAnswers(AnswerForm answerForm) {
		TBL_ANSWERS param = new TBL_ANSWERS();
		param.setAnswerNo(answerForm.getAnswerNo());
		List<TBL_ANSWERS> list = answerDAO.getAnswers(param);
		return list;
	}




}
