package cn.tmxk.spring.jdbc.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
/**
 * 注解形式将dataSource注入 
 * @author Administrator
 */
public class BaseDao {
	JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
