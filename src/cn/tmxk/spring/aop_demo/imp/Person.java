package cn.tmxk.spring.aop_demo.imp;

import org.springframework.stereotype.Repository;

import cn.tmxk.spring.aop_demo.IMoveable;

@Repository("person")
public class Person implements IMoveable {
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		System.out.println("run....");
	}

}
