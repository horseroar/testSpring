package cn.tmxk.spring.jdbc.dao;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import cn.tmxk.spring.jdbc.model.User;

/**
 * 测试使用namedParameterDao操作数据库
 * 
 * @author Administrator
 * 
 */
@Service("nameDao")
public class UserDaoNP {
	NamedParameterJdbcTemplate jdbcTemplate;
	JdbcOperations jdbcOperations;

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		/*
		 * 获取jdbcOperations ,比jdbcTemplate的功能略强一些<br> (虽然jdbcTemplate已经能够适用很多场景)
		 * 毛童的公司使用的是jdbcOperations来操作数据库
		 */
		this.jdbcOperations = jdbcTemplate.getJdbcOperations();
	}

	/**
	 * 获取元数据信息
	 */
	public List<String> queryMetaData() {
		return jdbcTemplate.query("select * from user", new HashMap(), new ResultSetExtractor<List<String>>() {
			List<String> columnList = new ArrayList<String>();

			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				ResultSetMetaData metaData = rs.getMetaData();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					columnList.add(metaData.getColumnName(i + 1));
				}
				return columnList;
			}
		});
	}

	/**
	 * 批量增加
	 * 
	 * @param userList
	 */
	public void batchAddUser(List<Object[]> userList) {
		jdbcOperations.batchUpdate("insert into user(username,password) values(?,?)", userList);
	}

	/**
	 * 批量增加1(使用batchUpdate(sql,batchpreparedStatementSetter)方式)
	 * 
	 * @param userList
	 */
	public void batchAddUser1(final List<User> userList) {
		String sqlText = "insert into user(username,password) values(?,?)";
		jdbcOperations.batchUpdate(sqlText, new BatchPreparedStatementSetter() {

			public int getBatchSize() {
				if (userList != null) {
					return userList.size();
				}
				return 0;
			}

			public void setValues(PreparedStatement ps, int userIndex) throws SQLException {
				// 第二个参数猜测应该是第几个用户的意思
				User user = userList.get(userIndex);
				// 进行用户bean与ps之间的绑定设置
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
			}

		});
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(User user) {
		return jdbcOperations.update("insert into user(username,password) values(?,?)", user.getUsername(), user
				.getPassword());
	}

	public int delUser(int userId) {
		return jdbcOperations.update("delete from user where id=?", userId);
	}

	/**
	 * 使用preparedStatementSetter方式对值进行设置
	 */
	public int updateUser(final User user) {
		return jdbcOperations.update("update user set username=?,password=? where id=?", new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setInt(3, user.getId());
			}
		});
	}

	/**
	 * 使用jdbcTemplate的update 传入map的形式，让数据进行更新
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser1(User user) {
		String sql = "update user set username=:username,password=:password where id=:id";
		Map paramMap = new HashMap();
		paramMap.put("username", user.getUsername());
		paramMap.put("password", user.getPassword());
		paramMap.put("id", user.getId());
		return jdbcTemplate.update(sql, paramMap);
	}

	/**
	 * 批量新增用户，看能否用map形式来进行实现
	 * 
	 * @param userList
	 */
	public void batchAddUser2(List<User> userList) {
		String sqlbatch = "insert into user(username,password) values(:username,:password)";
		// 初始化一个hashMap数组(这里可以考虑先用list里面放置hashmap,然后再toArray的形式来进行)
		Map<String, Object>[] paramMapArry = new HashMap[userList.size()];
		// 初始化一个含有map的list
		List<Map<String, Object>> userListExt = new ArrayList<Map<String, Object>>();
		// 遍历userList,将属性取出，放在map中后，再放入userListExt集合
		for (User user : userList) {
			// 此处的值设置还有优化的空间，可以考虑使用反射获取到所有属性，然后设置到map中去
			// map算是一种灵活的变相存储对象的形式，可以考虑多使用这种略带扩展性质的形式
			Map<String, Object> resultMap = convertObject2Map(user);
			userListExt.add(resultMap);
		}
		// 将userListExt集合转换成数组
		userListExt.toArray(paramMapArry);
		jdbcTemplate.batchUpdate(sqlbatch, paramMapArry);
	}

	/**
	 * 通过反射获取到对象声明的区域，然后通过beanUtils将值设置到map中去 ,<BR>
	 * 上面通过名称来设置map的 均可以通过此种方式来进行改进
	 * 
	 * @param user
	 * @return
	 */
	private Map<String, Object> convertObject2Map(Object obj) {
		Map resultMap = new HashMap<String, Object>();
		// 获得区域
		Field[] fields = obj.getClass().getDeclaredFields();
		// 遍历属性，将属性名和对象属性值放入map
		for (Field field : fields) {
			field.setAccessible(true); // 设置私有属性可访问
			try {
				resultMap.put(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	/**
	 * 尝试根据单个用户ID去获取用户的信息
	 * 
	 * @param userId
	 */
	public User queryForSingleUser(int userId) {
		//这里需要给自己定义的javabean类进行赋值，所以需要使用到BeanPropertyRowMapper
		//如果是系统自定义的基本类型的包装类的话，则最后一个参数直接传入User.class即可
		User user = jdbcTemplate.queryForObject("select id,username,password from user where id=1", new HashMap(),
				new BeanPropertyRowMapper<User>(User.class));
		return user;
		
	}
	
}
