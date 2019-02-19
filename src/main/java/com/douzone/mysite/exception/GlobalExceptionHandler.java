package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice		//AOP가 모든 컨트롤러에 몰린다.
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(
			HttpServletRequest request,
			Exception e) {
		
		// 1. 로깅 작업
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		// 2. 시스템 오류 안내페이지 전환
		ModelAndView mav = new ModelAndView();
		mav.addObject("errors",	errors.toString());
		mav.setViewName("error/exception");
		
		return mav;
	}
}
