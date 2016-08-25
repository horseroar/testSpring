package cn.tmxk.spring.aop_demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Aspect
public class PersonAop {
	@Pointcut("execution (* cn.tmxk.spring.aop_demo.imp.Person.*(..))")
	private void anyMethod() {
	}// ����һ�������

	@Around("anyMethod()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("���뷽��");
		Object obj = pjp.proceed();
		System.out.println("�˳�����");
		return obj;
	}

	@Before("anyMethod()")
	public void log() {
		System.out.println("loging before running...");
	}

	@After("anyMethod()")
	public void logAfter() {
		System.out.println("loging after runing...");
	}

	@Before("execution (* cn.tmxk.spring.aop_demo.imp.Person.*(..))")
	public void log1() {
		System.out.println("log1");
	}
}
