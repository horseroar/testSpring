package cn.tmxk.spring.jdbc.test;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.tmxk.spring.jdbc.model.User;

/**
 * 本类主要是 测试一下除了UserDaoNp里没有测到的其他的功能(主要是对与象相关的列表和单独的对象等)
 */
/**
 * PS：从目前的情况来看 想要列表也转成相应的对象，只能自己手工转换一下了
 * 或者利用java本身的反射机制封装出一个公共方法，然后调用公共方法实现list<Map<String,Object>>到List<T>的转换
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestJdbcOperations {
	/*
	 * 将jdbcTemplate注入进来
	 */
	JdbcOperations jdbcOperations;
	NamedParameterJdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcOperations = jdbcTemplate.getJdbcOperations();
	}

	@Test
	// JdbcOperations只能传普通类型进来
	public void testGetObjectList() {
		String sql = "select username from user";
		List<String> userList = jdbcOperations.queryForList(sql, String.class);
		for (String username : userList) {
			System.out.println(username);
		}
	}

	@Test
	// jdbcTemplate也只能传普通类型进来（下面的代码仍行不通）
	public void testGetObjectList1() {
		String sql = "select * from user";
		List<User> userList = jdbcTemplate.queryForList(sql, new HashMap(), User.class);
		for (User user : userList) {
			System.out.println(user.getUsername());
		}
	}

	/**
	 * 16:41 2016-8-30 added by horseroar
	 * 测试使用spring jdbcTemplate来获取一个对象列表
	 */
	@Test
	public void testGetObjectListBySpringJdbcTemplate() {
		List<User> userList = jdbcTemplate.query("select * from user", new HashMap(), new BeanPropertyRowMapper<User>(User.class));
		for (User user : userList) {
			System.out.println(user.getUsername()+","+user.getPassword());
		}
	}

	/**
	 * 16:41 2016-8-30 added by horseroar
	 * 测试使用spring jdbcOperations来获取一个对象列表
	 */
	@Test
	public void testGetObjectListBySpringJdbcOperations() {
		List<User> userList = jdbcTemplate.getJdbcOperations().query("select * from user", new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
		for (User user : userList) {
			System.out.println(user.getUsername()+","+user.getPassword());
		}
	}

}
