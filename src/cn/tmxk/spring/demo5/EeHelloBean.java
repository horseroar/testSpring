package cn.tmxk.spring.demo5;

import org.springframework.stereotype.Repository;

@Repository(value = "eehello")
public class EeHelloBean implements HelloBean {

	public void sayHello() {
		System.out.println("EE HELLO!");
	}

}
