/*
 * @(#)SpringUtil.java	2015-4-29 下午2:58:23
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.base.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringUtil
 * @author douzh
 * @time 2015-4-29下午2:58:23
 */
public class SpringUtil {

    public static Object getBean(String name){
        BeanFactory fac=new ClassPathXmlApplicationContext("applicationContext.xml");
        Object bean=fac.getBean(name);
        return bean;
    }
}
