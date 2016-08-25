package cn.tmxk.spring.jdbc.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.tmxk.spring.jdbc.dao.UserDaoNP;
import cn.tmxk.spring.jdbc.model.User;

/**
 * 将userDao等类注入到此测试类进行测试
 * 
 * @author Administrator
 * 
 * 这里配置的@RunWith时需要注意，直接使用Myeclipse自带的junit是无法通过编译的<br>
 * 需要换成junit-4.12.jar和hamcrest-core-1.3.jar包才可以（从dq推荐的博客中提取）
 * https://segmentfault.com/a/1190000002870283
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestUserDaoNamedParameter {
	// @Resource
	// UserDao userDao;
	/**
	 * 这里的注解可以用:
	 * 
	 * @Resource(name="userDao")
	 * @Autowired
	 * @Resource <BR>
	 *           均能够达到注入的目的
	 */

	@Autowired
	UserDaoNP nameDao;

	@Test
	public void testAddUser() {
		User user = new User("skyhorse", "test111");
		nameDao.addUser(user);
	}

	@Test
	public void testDeleteUser() {
		/*
		 * 查询了下刚插入的skyhorse用户的id为199
		 */
		int result = nameDao.delUser(190);
		Assert.assertEquals(1, result);
	}

	@Test
	public void testUpdateUser() {
		User user = new User("maxaio", "pass");
		user.setId(164);
		int result = nameDao.updateUser(user);
		Assert.assertEquals(1, result);
	}

	@Test
	public void testUpdateUser1() {
		User user = new User("maxaio111", "pass111");
		user.setId(164);
		int result = nameDao.updateUser1(user);
		Assert.assertEquals(1, result);
	}

	@Test
	public void testBatchAddUser() {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			userList.add(new User("dq" + i, "pass" + i));
		}
		nameDao.batchAddUser1(userList);
	}

	@Test
	public void testBatchAddUser2() {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			userList.add(new User("dd" + i, "pass" + i));
		}
		nameDao.batchAddUser2(userList);
	}

	/**
	 * 测试去获取一个object
	 * 参考链接：http://www.tuicool.com/articles/niERn2
	 */
	@Test
	public void testQuerySingleUser() {
		User user = nameDao.queryForSingleUser(1);
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
	}
	
	
	
}
