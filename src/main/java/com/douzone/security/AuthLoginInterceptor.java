package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	@Autowired
	private SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		//UserService userService = ac.getBean(UserService.class);
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = userService.login(email, password);
		
		if(userVo == null) {
			/* 인증실패 */
			response.sendRedirect(request.getContextPath()+"/user/login?result=fail");
			return false;
		}
		
		//로그인 처리
		HttpSession session = request.getSession(true);
		//HttpSession session = request.getSession();
		session.setAttribute("authuser", userVo);
		//session.setAttribute("siteVo", siteService.get());
		response.sendRedirect(request.getContextPath()+"/");
		return false;
	}

}
