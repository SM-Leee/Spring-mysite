package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor01 implements HandlerInterceptor {
	
	//컨트롤러가 호출되기 전에 실행된다. (Hnadler 호출 되기 전에 실행된다 )(요청전)
	//Handler 호출 여부를 결정 (boolean의 반환값에 따라서 호출이 일어난다 - false가 일어나면 다음으로 넘어가지 않는다 )
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("MyIntercetor01 : preHandle");
		return false;
	}
	
	// 컨트롤러가 실행된 후에 호출된다. ( Handler가 호출 된 후에 실행된다. )(응답후)
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("MyIntercetor01 : postHandle");
	}
	
	// 뷰에서 최종결과가 생성하는 일을 포함한 모든 일이 완료되었을떄 실행된다. ( 뷰가 끝나고 실행된다 )
	//View의 randering 작업까지 완전히 완료(응답 후)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("MyIntercetor01 : afterCompletion");
		
	}

}
