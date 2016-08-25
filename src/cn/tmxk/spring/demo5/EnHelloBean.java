package cn.tmxk.spring.demo5;

import org.springframework.stereotype.Service;

@Service("enhellobean1")
public class EnHelloBean implements HelloBean {

	public void sayHello() {
		System.out.println("Hello,the world!");
	}

}
