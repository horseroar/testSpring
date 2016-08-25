package cn.tmxk.spring.jdbc.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.tmxk.spring.jdbc.service.CommonService;

/**
 * 本类的目的是测试commservice中方法的事务控制是否能起作用
 * 
 * @author Administrator
 * 
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestCommonService {

	@Resource
	CommonService commonService;

	/**
	 * 调用其中带有事务控制的方法，看能否正常进行rollback
	 */
	@Test
	public void testtransactTestFunction() {
		commonService.transactTestFunction();
	}
}
