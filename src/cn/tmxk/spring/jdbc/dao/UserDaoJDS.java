package cn.tmxk.spring.jdbc.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

/**
 * 测试使用继承的方式，注解将dataSource设置进JdbcTemplate
 * 
 * @author Administrator
 * 
 */
@Service
public class UserDaoJDS extends JdbcDaoSupport {

	@Resource(name = "dataSource")
	public void setMyDataSource(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public void getUserList() {
		// 获取jdbcTemplate执行一个用户列表查询
		List<Map<String, Object>> userList = this.getJdbcTemplate().queryForList("select * from user");
		for (Map<String, Object> map : userList) {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				System.out.println(entry.getKey() + "," + entry.getValue());
			}
		}
	}
}
