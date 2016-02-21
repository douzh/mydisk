package com.iteedu.demo.webservicesdk;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class WebServiceApp {
	private final static String ADDRESS = "http://localhost:8080/helloWorld";

	public static void main(String[] args) {
//		create();
		publish();
	}

	private static void publish() {
		System.out.println("web service start");
		HelloWorldImpl implementor = new HelloWorldImpl();
		Endpoint.publish(ADDRESS, implementor);
		System.out.println("web service started");

	}

	private static void create() {
		HelloWorld hw = new HelloWorldImpl();
		JaxWsServerFactoryBean sfb = new JaxWsServerFactoryBean();
		sfb.setAddress(ADDRESS);// webService对应的访问地址
		sfb.setServiceClass(HelloWorld.class);// 指定webService服务类型
		sfb.setServiceBean(hw);// webService对应的bean对象
		sfb.create();// 这样就创建了一个webService的服务端

	}
}