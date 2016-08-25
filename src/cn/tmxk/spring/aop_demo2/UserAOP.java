package cn.tmxk.spring.aop_demo2;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Repository;

@Repository
@Aspect
public class UserAOP {

	// 声明一个切点，定义其切入点表达式
	@Pointcut("execution (* cn.tmxk.spring.aop_demo2.User.run*(..))")
	private void runMethod() {
	}

	@Before("runMethod()")
	public void before() {
		System.out.println("切点之前执行");
	}

	@After("runMethod()")
	public void after() {
		System.out.println("切点之后执行");
	}
}
