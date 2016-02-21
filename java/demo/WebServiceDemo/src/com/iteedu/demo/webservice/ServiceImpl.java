package com.iteedu.demo.webservice;
 
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
/**
 * <b>function:</b> WebService传递复杂对象，如JavaBean、Array、List、Map等
 */
@WebService
@SOAPBinding(style = Style.RPC)
public class ServiceImpl implements IService {
    public User getUserByName(@WebParam(name = "name") String name) {
        User user = new User();
        user.setName(name);
        return user;
    }
    public void setUser(User user) {
        System.out.println("############Server setUser###########");
        System.out.println("setUser:" + user);
    }
}

