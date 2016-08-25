package cn.tmxk.spring.jdbc.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tmxk.spring.jdbc.dao.UserDaoNP;
import cn.tmxk.spring.jdbc.model.User;

/**
 * 通用业务类
 * 
 * @author Administrator
 * 
 */
@Service
public class CommonService {

	@Resource(name = "nameDao")
	UserDaoNP userDaoNP;

	/**
	 * 测试一下此需要进行事务控制的方法
	 * 
	 * 先尝试插入一条记录，然后在中间抛异常，再插入第二条记录
	 * 
	 */
	@Transactional
	public void transactTestFunction() {
		User user = new User("maxiao", "pass");
		userDaoNP.addUser(user);
//		if (true)
//			throw new RuntimeException("...");
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			userList.add(new User("admin" + i, "pass" + i));
		}
		userDaoNP.batchAddUser1(userList);
	}
}
