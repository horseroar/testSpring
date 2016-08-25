搭建环境步骤
1.配置基本的spring.xml和spring-jdbc.xml
	spring里配置启用注解 扫描包
	spring-jdbc里面配置数据源dataSource
	
2.编写操作数据库使用的dao类
	重点是将dataSource注入到jdbcTemplate属性中,然后就可以使用jdbcTemplate欢乐地进行数据库的操作了
	
	使用dataSource实现方式分为两种：
		第一种： 继承JdbcDaoSupport类后xml配置
			继承后，在配置文件里面配置xxxxDao引用dataSource
			<bean id="userDao" class="cn.tmxk.spring.jdbc.UserDao">
				<property name="dataSource" ref="dataSource">
			</bean>
		第二种：继承JdbcDaoSupport类后注解配置
			由于JdbcDaoSupoort中的setDataSource是final类型，故子类无法进行覆盖改写
			可以写一个setMyDataSource方法，在其中调用父类的this.setDataSource方法
			JdbcTemplate jdbcTemplate;
			@Resource(name = "dataSource")
			public void setDataSource(DataSource dataSource) {
				this.jdbcTemplate = new JdbcTemplate(dataSource);
			}
	PS:试了使用注解方式，将set的代码转移到自己定义的父类中，然后子类继承的情况下，仍能得到Template，
	可见继承后的子类，spring仍可以处理其注解。XML方式没有试，估计也是没有问题的。
	
	之前帮助毛童经常用到的jdbcTemplate(其用的是NamedParameterJdbcTemplate)的方法：
		queryForList (Map<String,Object>)
		queryForInt
		queryForObject(最后传入一个BeanPropertyRowMapper可实现map与对象之间的转换)
		batchUpdate
		query 然后传入一个new ResultSetExtractor()进去，通过操作记录集进行一些元数据的操作等
		
PS:经测试之后的结果来看，在获取List<T>方面的功能很弱，只能获取简单类型的包装类，不知道网友是否有现成的解决方案
在获取单独对象的时候，是可以传入一个BeanPropertyRowMapper从而实现map自动向对象的转换


3.事务控制（Spring整合JDBC）
	以对cn.tmxk.spring.jdbc.service.CommonService.java类中的方法进行事务控制为例：
	3.1XML方式实现
		大致步骤：
			① spring配置文件中配置事务管理器，springJDBC和spring整合hibernate后使用的事务管理器不一样。
				<bean id="txManager"
					class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
					<property name="dataSource" ref="dataSource" />
				</bean>
			② 声明tx:advice,指明使用的事务管理器，指明各方法的事务传播属性
				<tx:advice id="txAdvice" transaction-manager="txManager">
				    <tx:attributes>
				      <tx:method name="save*" propagation="REQUIRED"/>
				      <tx:method name="del*" propagation="REQUIRED"/>
				      <tx:method name="update*" propagation="REQUIRED"/>
				      <tx:method name="add*" propagation="REQUIRED"/>
				      <tx:method name="find*" propagation="REQUIRED"/>
				      <tx:method name="get*" propagation="REQUIRED"/>
				      <tx:method name="apply*" propagation="REQUIRED"/>
				      <tx:method name="*" propagation="REQUIRED"/>
				    </tx:attributes>
				</tx:advice>
			③ 配置AOP和参与事务的类
				<aop:config>
					<aop:pointcut id="commServiceMethod" expression="execution(* cn.tmxk.spring.jdbc.service.CommonService.transact*(..))"/>
					<aop:advisor pointcut-ref="commServiceMethod" advice-ref="txAdvice" />
				</aop:config>
			
			PS:感觉配置稍微麻烦一些，特别是最后两步
	
	3.2注解方式实现
		大致步骤：
			① XML中配置
							<!-- 配置事务管理器 -->
							<bean id="txManager"
								class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
								<property name="dataSource" ref="dataSource" />
							</bean>
							
							<!-- 配置相关的事务控制器 -->
							<bean id="txManager"
								class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
								<property name="dataSource" ref="dataSource" />
							</bean>
							
							<!-- 启用注解形式事务 -->
							<tx:annotation-driven transaction-manager="txManager"/>
				
			② 代码中书写：
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
									User user = new User("admin", "pass");
									userDaoNP.addUser(user);
									//故意抛出异常使事务回滚
									if (true)
										throw new RuntimeException("...");
									List<User> userList = new ArrayList<User>();
									for (int i = 0; i < 100; i++) {
										userList.add(new User("admin" + i, "pass" + i));
									}
									userDaoNP.batchAddUser1(userList);
								}
							}
		   ③ 实测抛异常的情况下 由于运行时异常的上抛导致了事务的回滚
		   PS 注解的优势
		   		a.XML中省去了<tx:advice结点的配置
		   		b.XML中省去了<aop结点的配置，不用使用execute或with表达式，直接在方法或类上使用注解，更容易实现
		   		
		   
参考资料：
达内sping教程PDF第3章(XML配置及事务)
达内sping教程PDF第5章(基本XML配置及注解事务)
http://www.tuicool.com/articles/niERn2
https://segmentfault.com/a/1190000002870283
