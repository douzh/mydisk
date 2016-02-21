/*
 * @(#)BaseManager.java	2015-4-29 下午2:56:41
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.base.dao;

import com.iteedu.base.utils.SpringUtil;

/**
 * BaseManager
 * @author douzh
 * @time 2015-4-29下午2:56:41
 */
public class BaseManager {

    private static BaseDao dao=(BaseDao) SpringUtil.getBean("baseDao");

    /**
     * @return the dao
     */
    public static BaseDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public static void setDao(BaseDao dao) {
        BaseManager.dao = dao;
    }
    
}
