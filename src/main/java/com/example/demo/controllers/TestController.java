package com.example.demo.controllers;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.DAO.PostDAO;
import com.example.demo.DTO.TBL_POST;

@Controller
public class TestController {

	@Autowired // 依存性の注入
	private PostDAO postDAO;

	@GetMapping("/hello")
	public String getData(HttpServletRequest request, Model model) {
		List<TBL_POST> list = postDAO.search(new TBL_POST()); // postDAOのfindPostを実行
		TBL_POST data = list.get(1);
		model.addAttribute("data", data);
		model.addAttribute("list", list);
		return "hello";
	}

}
