package cn.tmxk.spring.demo5;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("mybean")
@Scope("")
public class MyBean {
	public String beanName;

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void sayMyBean() {
		System.out.println("hello,mybean!");
	}

}
