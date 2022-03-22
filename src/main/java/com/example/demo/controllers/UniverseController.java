package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UniverseController {
	@RequestMapping("/universe")
	public String universe() {
		return "index";
	}
}
