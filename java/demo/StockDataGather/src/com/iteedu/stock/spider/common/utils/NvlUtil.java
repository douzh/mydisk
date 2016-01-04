/*
 * @(#)NvlUtil.java	2015-4-24 下午2:07:51
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.stock.spider.common.utils;

import java.math.BigDecimal;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;


/**
 * NvlUtil
 * @author douzh
 * @time 2015-4-24下午2:07:51
 */
public class NvlUtil {

    public static String getString(Object o){
        if(o!=null){
            return o.toString();
        }
        return null;
    }
    
    public static Double getDouble(Object o){
        if(o instanceof String&&NumberUtils.isNumber(o.toString())){
            return Double.parseDouble(o.toString());
        }else if (o instanceof Double){
            return (Double) o;
        }
        return null;
    }
    public static Double getDouble(JSONObject json,String key){
        return getDouble(json.get(key));
    }
    public static BigDecimal getDecimal(Object o){
        if(o instanceof String&&NumberUtils.isNumber(o.toString())){
            return new BigDecimal(o.toString());
        }
        return null;
    }
    public static BigDecimal getDecimal(JSONObject json,String key){
        return getDecimal(json.get(key));
    }
}
