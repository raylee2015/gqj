package com.esemip.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esemip.service.TestService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring_mybatis_config.xml" })

public class TestMyBatis {
	@Resource
	private TestService testService = null;

	@Test
	public void test1() throws Exception {
		System.out.println(testService.testQuery());
	}
}
