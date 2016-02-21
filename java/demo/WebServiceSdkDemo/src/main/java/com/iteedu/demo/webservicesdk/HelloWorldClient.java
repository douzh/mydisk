package com.iteedu.demo.webservicesdk;

import java.util.Arrays;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class HelloWorldClient {
	public static void main(String[] args) {
		JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
		svr.setServiceClass(HelloWorld.class);
		svr.setAddress("http://localhost:8080/helloWorld");
		HelloWorld hw = (HelloWorld) svr.create();
		System.out.println(hw.sayHi("abc"));
		User user = new User();
		user.setName("Tony");
		System.out.println(hw.sayHiToUser(user));
		String[] rs2=hw.SayHiToUserList(Arrays.asList(new User[]{user,user}));
		System.out.println(Arrays.toString(rs2));
	}
}