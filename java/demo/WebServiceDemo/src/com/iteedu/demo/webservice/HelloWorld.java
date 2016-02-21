package com.iteedu.demo.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorld {
	String sayHi(@WebParam(name = "text") String text);

	String sayHiToUser(User user);

	String[] SayHiToUserList(List<User> userList);
}