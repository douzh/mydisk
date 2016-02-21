package com.iteedu.demo.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/*")  // 父request请求url
public class TestController {

    @RequestMapping("login.do")  // 子request请求url，拼接后等价于/test/login.do
    public String testLogin(String username, String password) {
        if (!"admin".equals(username) || !"admin".equals(password)) {
            return "loginError";
        }
        return "loginSuccess";
    }
}