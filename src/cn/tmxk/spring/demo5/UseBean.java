package cn.tmxk.spring.demo5;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class UseBean {

	@Resource(name = "zhHelloBean")
	HelloBean helloBean;

	public void show() {
		System.out.println("œ‘ æhelloœ˚œ¢");
		helloBean.sayHello();
	}

	public HelloBean getHelloBean() {
		return helloBean;
	}

	public void setHelloBean(HelloBean helloBean) {
		this.helloBean = helloBean;
	}

}
