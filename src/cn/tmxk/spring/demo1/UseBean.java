package cn.tmxk.spring.demo1;

public class UseBean {
	public void show() {
		System.out.println("œ‘ æhelloœ˚œ¢");
		HelloBean helloBean = HelloBeanFactory.getHelloBean();
		helloBean.sayHello();
	}
}
