package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.SiteService;

@Controller
public class MainController {
	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping({"","/main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.get());
	
		//return "/WEB-INF/views/main/index.jsp";
		return "main/index";
	}
}
