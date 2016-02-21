package com.iteedu.demo.webservice;

import javax.jws.WebParam;

import javax.jws.WebService;

import javax.jws.soap.SOAPBinding;

import javax.jws.soap.SOAPBinding.Style;

/**
 * <b>function:</b>定制客户端请求WebService所需要的接口
 */

@WebService
@SOAPBinding(style = Style.RPC)
public interface IService {
	public User getUserByName(@WebParam(name = "name") String name);

	public void setUser(User user);
}
