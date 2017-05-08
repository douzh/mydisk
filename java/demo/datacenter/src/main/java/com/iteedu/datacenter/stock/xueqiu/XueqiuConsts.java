/*
 * @(#)XueQiuConsts.java	2015-4-29 下午3:50:30
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.datacenter.stock.xueqiu;

/**
 * XueQiuConsts
 * @author douzh
 * @time 2015-4-29下午3:50:30
 */
public interface XueqiuConsts {

    /**
     * 上证股票列表
     */
    public static final String API_SHA="http://xueqiu.com/stock/quote_order.json?page=1&size=2000" +
    		"&order=asc&exchange=CN&stockType=sha&orderBy=symbol&column=symbol%2Cname";
    /**
     * 深证股票列表
     */
    public static final String API_SZA="http://xueqiu.com/stock/quote_order.json?page=1&size=2000" +
    		"&order=asc&orderBy=symbol&exchange=CN&stockType=sza&column=symbol%2Cname";
   
}
