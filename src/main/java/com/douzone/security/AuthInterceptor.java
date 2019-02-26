package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth.Role;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1. Handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. Casting
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Method에 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 3-1. Method 에 @Auth가 안붙어 있으면 class(type)의 @Auth 받아오기
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		// 4. Method와 class 에 @Auth가 안붙어 있으면 
		if(auth == null) {
			return true;
		}
		
		// 5. @Auth 가 불어 있기 때문에 로그인 여부 ( 인증 여부 ) 를 확인해야한다.
		HttpSession session = request.getSession();
		UserVo authuser = null;
		if(session != null) {
			authuser = (UserVo)session.getAttribute("authuser");
		}
		
		if(authuser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//5-1. role 비교작업 
		Role role = auth.value();
		
		
		// User Role 접근이면 인증만 되어 있으면 허용
		if(role == Role.USER) {
			return true;
		}
		
		
		// ADMIN Role 접근
		//ADMIN 권한이 없는 사욪자이면 메인으로 갑니다
		if("ADMIN".equals(authuser.getRole()) == false) {
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		
		
		// admin 접근 허용
		return true;
		
		
//		if(authuser.getRole().equals(role.toString())) {
//			//접근허용
//			return true;
//		} else {
//			//접근거절
//			response.sendRedirect(request.getContextPath()+"/");
//			return false;
//		}
		
	}

}
