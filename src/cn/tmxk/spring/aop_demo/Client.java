package cn.tmxk.spring.aop_demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tmxk.spring.aop_demo.imp.Person;

public class Client {
	public static void main(String[] args) {
		ApplicationContext atx = new ClassPathXmlApplicationContext("applicationContext.xml");
		IMoveable person = atx.getBean("person", IMoveable.class);
		person.run();
	}
}
