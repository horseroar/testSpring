package cn.tmxk.spring.demo1;

public class HelloBeanFactory {
	public static HelloBean getHelloBean() {
		return new ZhHelloBean();
	}
}
