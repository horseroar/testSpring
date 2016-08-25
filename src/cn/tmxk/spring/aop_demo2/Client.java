package cn.tmxk.spring.aop_demo2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
	public static void main(String[] args) {
		ApplicationContext atx = new ClassPathXmlApplicationContext("applicationContext.xml");
		User person = atx.getBean("userinfo", User.class);
		person.run1();
	}
}
