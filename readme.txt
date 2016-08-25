1.以下包中的源码主要是了学习spring 的ioc和aop的一些练习代码，使用applicationContext.xml作为配置
文件，有基于XML的配置，也有基于注解的扫描，代码稍微有点混乱，可配合淘宝上买的《达内spring》PDF
教材查看复习。

demo1
demo2
demo5
aop_demo
aop_demo2

2.jdbc包下的是对spring jdbc的学习尝试代码
	基于配置文件spring.xml和spring-jdbc.xml进行测试
	使用了注解形式来跑测试类：
	@RunWith(value = SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = { "classpath:spring.xml" })
	为了让其能顺利跑起来 引入了在intellij搭建springMybatis环境的两个jar包
	具体见jdbc包下的readme.txt说明文件
	

