/*
 * @(#)TStock.java	2015-4-29 下午3:24:16
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.stock.spider.xueqiu.bean;

import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.iteedu.stock.spider.common.utils.NvlUtil;

/**
 * TStock
 * @author douzh
 * @time 2015-4-29下午3:24:16
 */
public class TStock {

    private String symbol;
    private String code;
    private String name;
    private Integer valid=1;
    private Date createtime=Calendar.getInstance().getTime();
    
    /**
     * 
     */
    public TStock() {
        super();
    }
    /**
     * @param jstock
     */
    public TStock(JSONArray jstock) {
        try {

            setSymbol(NvlUtil.getString(jstock.get(0)));
            setName(NvlUtil.getString(jstock.get(1)));
        } catch (Exception e) {
        }
    }
    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
        if(StringUtils.isNotBlank(symbol)){
            code=symbol.substring(2);
        }
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the valid
     */
    public Integer getValid() {
        return valid;
    }
    /**
     * @param valid the valid to set
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }
    /**
     * @return the createtime
     */
    public Date getCreatetime() {
        return createtime;
    }
    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
