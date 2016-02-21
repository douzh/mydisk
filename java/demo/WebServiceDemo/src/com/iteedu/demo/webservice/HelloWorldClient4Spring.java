package com.iteedu.demo.webservice;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldClient4Spring {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/src/applicationContext.xml");
		HelloWorld hw = (HelloWorld)context.getBean("client");
		System.out.println(hw.sayHi("abc"));
		User user = new User();
		user.setName("Tony");
		System.out.println(hw.sayHiToUser(user));
		String[] rs2=hw.SayHiToUserList(Arrays.asList(new User[]{user,user}));
		System.out.println(Arrays.toString(rs2));
	}
}