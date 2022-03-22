package com.example.demo.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import com.example.demo.forms.SignUpForm;
import com.example.demo.services.BlogService;

@Component
public class SignUpValidator {

	private BlogService blogService;
	@Autowired
	public SignUpValidator(BlogService blogService){
		this.blogService = blogService;
	}

	public void validator(SignUpForm form, BindingResult result, Model model) {
		//メールアドレス正規表現
		String regularExpressionForEmail = "/^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$/";
		Pattern patternForEmail = Pattern.compile(regularExpressionForEmail);
		Matcher matcherForEmail = patternForEmail.matcher(form.getUserId());
		//パスワード
		String regularExpressionForPwd = "/^[a-zA-Z0-9.!#$%&'*+\\\\/=?^_`{|}~-]*$/";
		Pattern patternForPwd = Pattern.compile(regularExpressionForPwd);
		Matcher matcherForPwd = patternForPwd.matcher(form.getUserId());

		//User_Id checks
		if (StringUtils.isEmpty(form.getUserId())) {
			result.rejectValue("userId", null,"ユーザーIDは必須項目です。");
		}else if (matcherForEmail.find()) {
			result.rejectValue("userId", null, "無効なメールアドレスです。");
		}
		if (form.getUserId().length() > 45) {
			result.rejectValue("userId", null, "ユーザーIDは45文字以内に設定してください。");
		}
		if (!StringUtils.isEmpty(form.getUserId())) {
			int itemFlg = 0;
			boolean isUnique = blogService.uniqueChecker(form, itemFlg);
			if (isUnique == false) {
				result.rejectValue("userId", null, "このユーザーIDはすでに使用されています。");
			}
		}

		//Password checks
		if (StringUtils.isEmpty(form.getUserPWD())) {
			result.rejectValue("userPWD", null, "Passwordは必須項目です。");
		}else if (matcherForPwd.find()) {
			result.rejectValue("userPWD", null, "無効なパスワードです。");
		}
		if (form.getUserPWD().length() > 45 || form.getUserPWD().length() < 6) {
			result.rejectValue("userPWD", null, "Passwordは6文字以上45文字以内に設定してください。");
		}
		//セキュリティー的によくないため削除
//		if (!form.getUserPWD().isEmpty()) {
//			int itemFlg = 1;
//			boolean isUnique = blogService.uniqueChecker(form, itemFlg);
//			if (isUnique == false) {
//				result.rejectValue("userPWD", null, "このパスワードはすでに使用されています。");
//			}
//		}


	}

}
