package cn.tmxk.spring.demo2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {
	public static void main(String[] args) {
		User user = (User) new ClassPathXmlApplicationContext(
				"applicationContext.xml").getBean("user");
		System.out.println(user.getUsername() + "," + user.getPassword());
	}
}
