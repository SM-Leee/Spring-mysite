package com.douzone.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value="/join",method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			model.addAttribute(result.getModel());
			
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authuser, Model model) {
		
		model.addAttribute("vo", userService.modifyselect(authuser));
		return "user/modify";
	}
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authuser ,@ModelAttribute UserVo userVo) {
		userVo.setNo(authuser.getNo());
		userService.modify(userVo);
		
		authuser.setName(userVo.getName());
		
		return "redirect:/main?result=success";
	}

	//	@ExceptionHandler(UserDaoException.class)
	//	public String handleUserDaoException() {
	//		// 1. 로깅 작업
	//		
	//		// 2. 페이지 전환( 사과 페이지 )
	//		return "error/exception";
	//	}

}
