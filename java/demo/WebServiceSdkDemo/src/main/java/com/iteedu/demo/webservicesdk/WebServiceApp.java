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
		sfb.setAddress(ADDRESS);// webService��Ӧ�ķ��ʵ�ַ
		sfb.setServiceClass(HelloWorld.class);// ָ��webService��������
		sfb.setServiceBean(hw);// webService��Ӧ��bean����
		sfb.create();// �����ʹ�����һ��webService�ķ����

	}
}