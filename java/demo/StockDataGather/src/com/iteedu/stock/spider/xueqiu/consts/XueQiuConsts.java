/*
 * @(#)XueQiuConsts.java	2015-4-29 下午3:50:30
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.stock.spider.xueqiu.consts;

/**
 * XueQiuConsts
 * @author douzh
 * @time 2015-4-29下午3:50:30
 */
public interface XueQiuConsts {

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
    /**
     * 主要财务指标
     */
    public static final String API_ZYCWZB="http://xueqiu.com/stock/f10/finmainindex.json?page=1&size=40&symbol=";
    /**
     * 资产负债表
     */
    public static final String API_BALSHEET="http://xueqiu.com/stock/f10/balsheet.json?page=1&size=40&symbol=";
}
