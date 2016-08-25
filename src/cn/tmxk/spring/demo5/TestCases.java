package cn.tmxk.spring.demo5;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCases {
	public static final String APPLICATION_CONTEXT_XML = "applicationContext.xml";

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
		UseBean useBean = (UseBean) ac.getBean("useBean");
		useBean.show();
		HelloBean obj = (HelloBean) ac.getBean("enhellobean1");
		obj.sayHello();
	}

	/**
	 * 测试自定义一个没有接口的类，是不是能正常调用
	 */
	@Test
	public void testMyBean() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
		MyBean bean = ac.getBean("mybean", MyBean.class);
		bean.sayMyBean();
		MyBean bean2 = ac.getBean("mybean", MyBean.class);
		Assert.assertEquals(bean, bean2);
	}
}
