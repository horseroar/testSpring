package cn.tmxk.spring.demo2;

public class UseBean {
	HelloBean helloBean;

	public void show() {
		System.out.println("��ʾhello��Ϣ");
		helloBean.sayHello();
	}

	public HelloBean getHelloBean() {
		return helloBean;
	}

	public void setHelloBean(HelloBean helloBean) {
		this.helloBean = helloBean;
	}

}
