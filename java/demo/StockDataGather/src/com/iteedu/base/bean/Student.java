/*
 * @(#)Student.java	2015-4-29 下午2:54:03
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.base.bean;

/**
 * Student
 * @author douzh
 * @time 2015-4-29下午2:54:03
 */
public class Student {

    private String sno;
    private String sname;
    private Integer sex;
    /**
     * @return the sno
     */
    public String getSno() {
        return sno;
    }
    /**
     * @param sno the sno to set
     */
    public void setSno(String sno) {
        this.sno = sno;
    }
    /**
     * @return the sname
     */
    public String getSname() {
        return sname;
    }
    /**
     * @param sname the sname to set
     */
    public void setSname(String sname) {
        this.sname = sname;
    }
    /**
     * @return the sex
     */
    public Integer getSex() {
        return sex;
    }
    /**
     * @param sex the sex to set
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
}
