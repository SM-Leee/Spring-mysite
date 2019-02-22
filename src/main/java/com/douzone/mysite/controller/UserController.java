package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

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
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/loginform";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, @ModelAttribute UserVo userVo) {
		UserVo authuser = userService.login(userVo.getEmail(), userVo.getPassword());
		if(authuser == null) {
			/* 인증실패 */
			session.setAttribute("result", "fail");
			return "user/loginform";
		}

		session.setAttribute("authuser", authuser);
		return "redirect:/";
	}

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		UserVo authuser = (UserVo)session.getAttribute("authuser");
		if(session != null && authuser != null) {
			session.removeAttribute("authuser");
			session.invalidate();
		}
		return "redirect:/";
	}

	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(HttpSession session, Model model) {
		/* 접근제어 */
		UserVo authuser = (UserVo) session.getAttribute("authuser");
		if(authuser == null) {
			return "redirect:/";
		}
		model.addAttribute("vo", userService.modifyselect(authuser));
		return "user/modifyform";
	}
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute UserVo userVo) {
		UserVo authuser = (UserVo)session.getAttribute("authuser");
		if(authuser == null) {
			session.removeAttribute("authuser");
			session.invalidate();
			return "redirect:/";
		}
		
		userVo.setNo(authuser.getNo());
		userService.modify(userVo);
		
		return "redirect:/";
	}

	//	@ExceptionHandler(UserDaoException.class)
	//	public String handleUserDaoException() {
	//		// 1. 로깅 작업
	//		
	//		// 2. 페이지 전환( 사과 페이지 )
	//		return "error/exception";
	//	}

}
