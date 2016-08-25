package cn.tmxk.spring.demo5;

import org.springframework.stereotype.Service;

@Service
public class ZhHelloBean implements HelloBean {

	public void sayHello() {
		System.out.println("ÄãºÃ£¬ÊÀ½ç!");
	}

}
