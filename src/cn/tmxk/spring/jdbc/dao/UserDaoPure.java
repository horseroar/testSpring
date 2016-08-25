package cn.tmxk.spring.jdbc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 测试在父类Dao中使用注解将dataSource注入进来，<BR>
 * 子类继承后是否可以正常拿到jdbcTemplate(YES!)
 * 
 * @author Administrator
 */
@Service
public class UserDaoPure extends BaseDao {

	public void queryUserList() {
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList("select * from user");
		System.out.println(queryForList.size());
	}
}
