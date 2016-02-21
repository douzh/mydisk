/*
 * @(#)BalSheet.java	2015-5-6 下午2:00:45
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iteedu.stock.spider.xueqiu.bean;

import java.util.Date;

import com.iteedu.stock.spider.common.utils.DateUtils;

import net.sf.json.JSONObject;

/**
 * BalSheet
 * @author douzh
 * @time 2015-5-6下午2:00:45
 */
public class TBalSheet {
    private String cid;
    private String symbol;
    private String code;
    private String reportDateStr;
    /**
     * 报表日期
     */
    private Date reportDate;
    private String comptype;
    /**
     * 原始数据
     */
    private JSONObject orgJson;
    /**
     * 原始数据
     */
    private String orgData;
    /**
     * 
     */
    public TBalSheet() {
    }
    public TBalSheet(JSONObject jZb, String symbol,String comptype) {
        orgJson = jZb;
        orgData=jZb.toString();
        try {
            reportDateStr=jZb.getString("reportdate");
            cid=symbol+":"+reportDateStr;
            this.symbol=symbol;
            code=symbol.substring(2);
            reportDate = DateUtils.parseDateEN2(reportDateStr);
            this.comptype=comptype;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return the cid
     */
    public String getCid() {
        return cid;
    }
    /**
     * @param cid the cid to set
     */
    public void setCid(String cid) {
        this.cid = cid;
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
     * @return the reportDateStr
     */
    public String getReportDateStr() {
        return reportDateStr;
    }
    /**
     * @param reportDateStr the reportDateStr to set
     */
    public void setReportDateStr(String reportDateStr) {
        this.reportDateStr = reportDateStr;
    }
    /**
     * @return the reportDate
     */
    public Date getReportDate() {
        return reportDate;
    }
    /**
     * @param reportDate the reportDate to set
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
    /**
     * @return the comptype
     */
    public String getComptype() {
        return comptype;
    }
    /**
     * @param comptype the comptype to set
     */
    public void setComptype(String comptype) {
        this.comptype = comptype;
    }
    /**
     * @return the orgJson
     */
    public JSONObject getOrgJson() {
        return orgJson;
    }
    /**
     * @param orgJson the orgJson to set
     */
    public void setOrgJson(JSONObject orgJson) {
        this.orgJson = orgJson;
    }
    /**
     * @return the orgData
     */
    public String getOrgData() {
        return orgData;
    }
    /**
     * @param orgData the orgData to set
     */
    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }
    
}
