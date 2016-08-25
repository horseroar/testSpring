package cn.tmxk.spring.jdbc.test;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import cn.tmxk.spring.jdbc.model.User;

public class TestBase {
	@Test
	public void testGetObjectFields() throws IllegalArgumentException, IllegalAccessException {
		User user = new User("maxaio", "asda");
		user.setId(11);
		Field[] fields = user.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			// 获取属性的值
			System.out.println(MessageFormat.format("属性的名为{0},属性的值为:{1},属性的类型为:{2}", field.getName(), field.get(user),
					field.getType().getName()));
		}
	}
	
	@Test
	public void testArrayAppendObject() {
		Object obj[]= {
				1,2,3
		};
		List list=new ArrayList();
		Collections.addAll(list, obj);
		list.add(4);
		list.add(5);
		Object resultArr[]=list.toArray();
		for (Object item : resultArr) {
			System.out.println(item);
		}
	}
}
