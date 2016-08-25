package cn.tmxk.spring.aop_demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.tmxk.spring.aop_demo.IMoveable;
import cn.tmxk.spring.aop_demo.imp.Person;

@Repository("userinfo")
public class User {

	@Autowired
	private IMoveable person;

	public void personAction() {
		person.run();
	}

	public void sayHello() {
		System.out.println("user:say hello");
	}

	public void run() {
		System.out.println("user:run");
	}

	public void run1() {
		System.out.println("user:run1");
	}
	

	public IMoveable getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
