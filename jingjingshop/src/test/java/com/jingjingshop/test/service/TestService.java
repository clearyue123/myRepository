package com.jingjingshop.test.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.sellergoods.BrandService;

public class TestService {

	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		BrandService brandService = (BrandService)app.getBean("brandServiceImpl");
		List<TbBrand> findAll = brandService.findAll();
		System.out.println(findAll.size());
	}

}
