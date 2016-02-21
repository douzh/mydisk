/*
 * @(#)Zycwzb.java 2015-4-23 下午5:09:24 HtmlUnit Copyright 2015 Thuisoft, Inc.
 * All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com.iteedu.stock.spider.xueqiu.bean;

import java.util.Date;

import net.sf.json.JSONObject;

import com.iteedu.stock.spider.common.utils.DateUtils;
import com.iteedu.stock.spider.common.utils.NvlUtil;

/**
 * 主要财务指标
 * Zycwzb
 * @author douzh
 * @time 2015-4-23下午5:09:24
 */
public class TZycwzb {

    private String cid;
    private String symbol;
    private String code;
    private String reportDateStr;
    /**
     * 报表日期
     */
    private Date reportDate;

    /**
     * 基本每股收益
     */
    private Double basicEps;

    /**
     * 每股收益(摊薄)
     */
    private Double epsDiluted;

    /**
     * 每股收益(加权)
     */
    private Double epsWeighted;

    /**
     * 每股净资产
     */
    private Double naps;

    /**
     * 每股现金流
     */
    private Double operCashPerShare;

    /**
     * 每股经营性现金流
     */
    private Double perOpeCashPerShare;

    /**
     * 净资产增长率(%)
     */
    private Double netassGrowRate;

    /**
     * 净资产收益率(摊薄)(%)
     */
    private Double dilutedRoe;

    /**
     * 净资产收益率(加权)(%)
     */
    private Double weightedRoe;

    /**
     * 主营业务收入增长率(%)
     */
    private Double mainBusincGrowRate;

    /**
     * 净利润增长率(%)
     */
    private Double netincGrowRate;

    /**
     * 总资产增长率(%)
     */
    private Double totassGrowRate;

    /**
     * 销售毛利率(%)
     */
    private Double saleGrossProfitrto;

    /**
     * 主营业务收入
     */
    private Double mainBusiIncome;

    /**
     * 主营业务利润
     */
    private Double mainBusiProfit;

    /**
     * 利润总额
     */
    private Double totProfit;

    /**
     * 净利润
     */
    private Double netProfit;

    /**
     * 资产总额
     */
    private Double totalAssets;

    /**
     * 负债总额
     */
    private Double totalLiab;

    /**
     * 股东权益合计
     */
    private Double totShareQui;

    /**
     * 经营活动产生的现金流量净额
     */
    private Double operRevenue;

    /**
     * 投资活动产生的现金流量净额
     */
    private Double invnetCashflow;

    /**
     * 筹资活动产生的现金流量净额
     */
    private Double finnetCflow;

    /**
     * 汇率变动对现金及现金等价物的影响
     */
    private Double chgExchgChgs;

    /**
     * 现金及现金等价物净增加额
     */
    private Double cashNetr;

    /**
     * 期末现金及现金等价物余额
     */
    private Double cashEqufinbal;

    /**
     * 原始数据
     */
    private JSONObject jsonData;
    /**
     * 原始数据
     */
    private String orgData;
    public TZycwzb() {

    }

    public TZycwzb(JSONObject jZb, String symbol) {
        jsonData = jZb;
        orgData=jZb.toString();
        try {
            reportDateStr=jZb.getString("reportdate");
            cid=symbol+":"+reportDateStr;
            this.symbol=symbol;
            code=symbol.substring(2);
            reportDate = DateUtils.parseDateEN2(reportDateStr);
            basicEps = NvlUtil.getDouble(jZb,"basiceps");
            epsDiluted = NvlUtil.getDouble(jZb,"epsdiluted");
            epsWeighted = NvlUtil.getDouble(jZb,"epsweighted");
            naps = NvlUtil.getDouble(jZb,"naps");
            operCashPerShare = NvlUtil.getDouble(jZb,"opercashpershare");
            perOpeCashPerShare = NvlUtil.getDouble(jZb,"peropecashpershare");
            netassGrowRate = NvlUtil.getDouble(jZb,"netassgrowrate");
            dilutedRoe = NvlUtil.getDouble(jZb,"dilutedroe");
            weightedRoe = NvlUtil.getDouble(jZb,"weightedroe");
            mainBusincGrowRate = NvlUtil.getDouble(jZb,"mainbusincgrowrate");
            netincGrowRate = NvlUtil.getDouble(jZb,"netincgrowrate");
            totassGrowRate = NvlUtil.getDouble(jZb,"totassgrowrate");
            saleGrossProfitrto = NvlUtil.getDouble(jZb,"salegrossprofitrto");
            mainBusiIncome = NvlUtil.getDouble(jZb,"mainbusiincome");
            mainBusiProfit = NvlUtil.getDouble(jZb,"mainbusiprofit");
            totProfit = NvlUtil.getDouble(jZb,"totprofit");
            netProfit = NvlUtil.getDouble(jZb,"netprofit");
            totalAssets = NvlUtil.getDouble(jZb,"totalassets");
            totalLiab = NvlUtil.getDouble(jZb,"totalliab");
            totShareQui = NvlUtil.getDouble(jZb,"totsharequi");
            operRevenue = NvlUtil.getDouble(jZb,"operrevenue");
            invnetCashflow = NvlUtil.getDouble(jZb,"invnetcashflow");
            finnetCflow = NvlUtil.getDouble(jZb,"finnetcflow");
            chgExchgChgs = NvlUtil.getDouble(jZb,"chgexchgchgs");
            cashNetr = NvlUtil.getDouble(jZb,"cashnetr");
            cashEqufinbal = NvlUtil.getDouble(jZb,"cashequfinbal");
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
     * @return the 报表日期
     */
    public Date getReportDate() {
        return reportDate;
    }

    /**
     * @param 报表日期 the reportDate to set
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * @return the basicEps
     */
    public Double getBasicEps() {
        return basicEps;
    }

    /**
     * @param basicEps the basicEps to set
     */
    public void setBasicEps(Double basicEps) {
        this.basicEps = basicEps;
    }

    /**
     * @return the epsDiluted
     */
    public Double getEpsDiluted() {
        return epsDiluted;
    }

    /**
     * @param epsDiluted the epsDiluted to set
     */
    public void setEpsDiluted(Double epsDiluted) {
        this.epsDiluted = epsDiluted;
    }

    /**
     * @return the epsWeighted
     */
    public Double getEpsWeighted() {
        return epsWeighted;
    }

    /**
     * @param epsWeighted the epsWeighted to set
     */
    public void setEpsWeighted(Double epsWeighted) {
        this.epsWeighted = epsWeighted;
    }

    /**
     * @return the naps
     */
    public Double getNaps() {
        return naps;
    }

    /**
     * @param naps the naps to set
     */
    public void setNaps(Double naps) {
        this.naps = naps;
    }

    /**
     * @return the operCashPerShare
     */
    public Double getOperCashPerShare() {
        return operCashPerShare;
    }

    /**
     * @param operCashPerShare the operCashPerShare to set
     */
    public void setOperCashPerShare(Double operCashPerShare) {
        this.operCashPerShare = operCashPerShare;
    }

    /**
     * @return the perOpeCashPerShare
     */
    public Double getPerOpeCashPerShare() {
        return perOpeCashPerShare;
    }

    /**
     * @param perOpeCashPerShare the perOpeCashPerShare to set
     */
    public void setPerOpeCashPerShare(Double perOpeCashPerShare) {
        this.perOpeCashPerShare = perOpeCashPerShare;
    }

    /**
     * @return the netassGrowRate
     */
    public Double getNetassGrowRate() {
        return netassGrowRate;
    }

    /**
     * @param netassGrowRate the netassGrowRate to set
     */
    public void setNetassGrowRate(Double netassGrowRate) {
        this.netassGrowRate = netassGrowRate;
    }

    /**
     * @return the dilutedRoe
     */
    public Double getDilutedRoe() {
        return dilutedRoe;
    }

    /**
     * @param dilutedRoe the dilutedRoe to set
     */
    public void setDilutedRoe(Double dilutedRoe) {
        this.dilutedRoe = dilutedRoe;
    }

    /**
     * @return the weightedRoe
     */
    public Double getWeightedRoe() {
        return weightedRoe;
    }

    /**
     * @param weightedRoe the weightedRoe to set
     */
    public void setWeightedRoe(Double weightedRoe) {
        this.weightedRoe = weightedRoe;
    }

    /**
     * @return the mainBusincGrowRate
     */
    public Double getMainBusincGrowRate() {
        return mainBusincGrowRate;
    }

    /**
     * @param mainBusincGrowRate the mainBusincGrowRate to set
     */
    public void setMainBusincGrowRate(Double mainBusincGrowRate) {
        this.mainBusincGrowRate = mainBusincGrowRate;
    }

    /**
     * @return the netincGrowRate
     */
    public Double getNetincGrowRate() {
        return netincGrowRate;
    }

    /**
     * @param netincGrowRate the netincGrowRate to set
     */
    public void setNetincGrowRate(Double netincGrowRate) {
        this.netincGrowRate = netincGrowRate;
    }

    /**
     * @return the totassGrowRate
     */
    public Double getTotassGrowRate() {
        return totassGrowRate;
    }

    /**
     * @param totassGrowRate the totassGrowRate to set
     */
    public void setTotassGrowRate(Double totassGrowRate) {
        this.totassGrowRate = totassGrowRate;
    }

    /**
     * @return the saleGrossProfitrto
     */
    public Double getSaleGrossProfitrto() {
        return saleGrossProfitrto;
    }

    /**
     * @param saleGrossProfitrto the saleGrossProfitrto to set
     */
    public void setSaleGrossProfitrto(Double saleGrossProfitrto) {
        this.saleGrossProfitrto = saleGrossProfitrto;
    }

    /**
     * @return the mainBusiIncome
     */
    public Double getMainBusiIncome() {
        return mainBusiIncome;
    }

    /**
     * @param mainBusiIncome the mainBusiIncome to set
     */
    public void setMainBusiIncome(Double mainBusiIncome) {
        this.mainBusiIncome = mainBusiIncome;
    }

    /**
     * @return the mainBusiProfit
     */
    public Double getMainBusiProfit() {
        return mainBusiProfit;
    }

    /**
     * @param mainBusiProfit the mainBusiProfit to set
     */
    public void setMainBusiProfit(Double mainBusiProfit) {
        this.mainBusiProfit = mainBusiProfit;
    }

    /**
     * @return the totProfit
     */
    public Double getTotProfit() {
        return totProfit;
    }

    /**
     * @param totProfit the totProfit to set
     */
    public void setTotProfit(Double totProfit) {
        this.totProfit = totProfit;
    }

    /**
     * @return the netProfit
     */
    public Double getNetProfit() {
        return netProfit;
    }

    /**
     * @param netProfit the netProfit to set
     */
    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    /**
     * @return the totalAssets
     */
    public Double getTotalAssets() {
        return totalAssets;
    }

    /**
     * @param totalAssets the totalAssets to set
     */
    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    /**
     * @return the totalLiab
     */
    public Double getTotalLiab() {
        return totalLiab;
    }

    /**
     * @param totalLiab the totalLiab to set
     */
    public void setTotalLiab(Double totalLiab) {
        this.totalLiab = totalLiab;
    }

    /**
     * @return the totShareQui
     */
    public Double getTotShareQui() {
        return totShareQui;
    }

    /**
     * @param totShareQui the totShareQui to set
     */
    public void setTotShareQui(Double totShareQui) {
        this.totShareQui = totShareQui;
    }

    /**
     * @return the operRevenue
     */
    public Double getOperRevenue() {
        return operRevenue;
    }

    /**
     * @param operRevenue the operRevenue to set
     */
    public void setOperRevenue(Double operRevenue) {
        this.operRevenue = operRevenue;
    }

    /**
     * @return the invnetCashflow
     */
    public Double getInvnetCashflow() {
        return invnetCashflow;
    }

    /**
     * @param invnetCashflow the invnetCashflow to set
     */
    public void setInvnetCashflow(Double invnetCashflow) {
        this.invnetCashflow = invnetCashflow;
    }

    /**
     * @return the finnetCflow
     */
    public Double getFinnetCflow() {
        return finnetCflow;
    }

    /**
     * @param finnetCflow the finnetCflow to set
     */
    public void setFinnetCflow(Double finnetCflow) {
        this.finnetCflow = finnetCflow;
    }

    /**
     * @return the chgExchgChgs
     */
    public Double getChgExchgChgs() {
        return chgExchgChgs;
    }

    /**
     * @param chgExchgChgs the chgExchgChgs to set
     */
    public void setChgExchgChgs(Double chgExchgChgs) {
        this.chgExchgChgs = chgExchgChgs;
    }

    /**
     * @return the cashNetr
     */
    public Double getCashNetr() {
        return cashNetr;
    }

    /**
     * @param cashNetr the cashNetr to set
     */
    public void setCashNetr(Double cashNetr) {
        this.cashNetr = cashNetr;
    }

    /**
     * @return the cashEqufinbal
     */
    public Double getCashEqufinbal() {
        return cashEqufinbal;
    }

    /**
     * @param cashEqufinbal the cashEqufinbal to set
     */
    public void setCashEqufinbal(Double cashEqufinbal) {
        this.cashEqufinbal = cashEqufinbal;
    }

    /**
     * @return the jsonData
     */
    public JSONObject getJsonData() {
        return jsonData;
    }

    /**
     * @param jsonData the jsonData to set
     */
    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
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
