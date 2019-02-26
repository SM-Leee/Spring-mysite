package com.douzone.mysite.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class GlobalExceptionHandler {
	//예외가 발생했을때 발생한다. (execution(* *..*.*.*(..)) : 모든 Exception은 여기에 떨어진다 )
		@AfterThrowing(
				value = "execution(* *..*.*.*(..))",
				throwing="ex")
		public void afterThrowingAdvice(Throwable ex) {
			System.out.println("call [afterThrowing advice] : "+ ex);
		}
}
