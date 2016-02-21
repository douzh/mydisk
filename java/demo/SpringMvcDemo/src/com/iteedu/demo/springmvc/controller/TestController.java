package com.iteedu.demo.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/*")  // ��request����url
public class TestController {

    @RequestMapping("login.do")  // ��request����url��ƴ�Ӻ�ȼ���/test/login.do
    public String testLogin(String username, String password) {
        if (!"admin".equals(username) || !"admin".equals(password)) {
            return "loginError";
        }
        return "loginSuccess";
    }
}