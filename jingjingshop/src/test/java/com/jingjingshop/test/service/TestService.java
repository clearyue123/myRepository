package com.jingjingshop.test.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.service.user.impl.UserServiceImpl;

public class TestService {

	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		UserServiceImpl userService = (UserServiceImpl)app.getBean("userServiceImpl");
		TbUser findOne = userService.findOne(1L);
		System.out.println(findOne.getUsername());
	}

}
