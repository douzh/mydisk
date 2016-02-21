package com.iteedu.stock.spider.xueqiu.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.iteedu.base.dao.BaseManager;
import com.iteedu.stock.spider.xueqiu.bean.TBalSheet;
import com.iteedu.stock.spider.xueqiu.bean.TStock;
import com.iteedu.stock.spider.xueqiu.bean.TZycwzb;
import com.iteedu.stock.spider.xueqiu.consts.XueQiuConsts;

/*
 * @(#)HttpUtil.java 2015-4-23 下午4:40:51 HtmlUnit Copyright 2015 Thuisoft, Inc.
 * All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */

/**
 * HttpUtil
 * @author douzh
 * @time 2015-4-23下午4:40:51
 */
public class XueQiuApi implements XueQiuConsts {
    private static Log log = LogFactory.getLog(XueQiuApi.class);

    /**
     * @param loginUrl
     * @param client
     * @throws IOException
     * @throws MalformedURLException
     * @author douzh
     * @time 2015-4-23下午4:37:49
     */
    public static HtmlPage loginXueqiu(WebClient client) throws IOException, MalformedURLException {
        HtmlPage page = (HtmlPage) client.getPage("http://xueqiu.com");
        HtmlTextInput account = null;
        HtmlForm loginForm = (HtmlForm) page.getElementById("form-login-index");
        account = (HtmlTextInput) loginForm.getInputByName("username");
        HtmlPasswordInput password = (HtmlPasswordInput) loginForm
                .getInputByName("password");
        DomNodeList<HtmlElement> btns = loginForm
                .getElementsByTagName("button");
        HtmlButton submit = (HtmlButton) btns.get(0);
        account.setValueAttribute("xueqiuclient@126.com");
        password.setValueAttribute("xueqiu");
        return (HtmlPage) submit.click();
    }

    /**
     * 同步所有股票代码
     * @param client
     */
    public static void syncStockList(WebClient client) {
        List<TStock> lstStock = getStockList(client);
        BaseManager.getDao().getHibernateTemplate().bulkUpdate("delete TStock");
        BaseManager.getDao().getHibernateTemplate().flush();
        BaseManager.getDao().saveOrUpdateAll(lstStock);
    }

    /**
     * @param client
     * @return
     */
    public static List<TStock> getStockList(WebClient client) {
        List<TStock> lstStock = getShaList(client);
        lstStock.addAll(getSzaList(client));
        return lstStock;
    }

    /**
     * 上证股票列表
     * @param client
     * @return
     * @author douzh
     * @time 2015-4-29下午3:48:17
     */
    private static List<TStock> getShaList(WebClient client) {
        return getStockList(client, API_SHA);
    }

    /**
     * 深证股票列表
     * @param client
     * @return
     * @author douzh
     * @time 2015-4-29下午3:48:17
     */
    private static List<TStock> getSzaList(WebClient client) {
        return getStockList(client, API_SZA);
    }

    /**
     * 股票列表
     * @param client
     * @return
     * @author douzh
     * @time 2015-4-29下午3:48:17
     */
    private static List<TStock> getStockList(WebClient client, String url) {
        try {
            Page p = client.getPage(url);
            WebResponse wp = p.getWebResponse();
            String sJson = wp.getContentAsString();
            JSONObject.fromObject(sJson);
            JSONObject json = JSONObject.fromObject(sJson);
            JSONArray jarr = json.getJSONArray("data");
            @SuppressWarnings("unchecked")
            Iterator<JSONArray> ite = jarr.iterator();
            List<TStock> lstStock = new ArrayList<TStock>();
            while (ite.hasNext()) {
                try {
                    JSONArray jstock = ite.next();
                    TStock stock = new TStock(jstock);
                    lstStock.add(stock);
                } catch (Exception e) {
                    continue;
                }
            }
            return lstStock;
        } catch (Exception e) {
            log.error("获取上证列表出错", e);
            return new ArrayList<TStock>();
        }
    }

    /**
     * 主要财务指标数据
     * @param client
     * @return
     * @throws IOException
     * @throws MalformedURLException
     * @author douzh
     */
    public static List<TZycwzb> getZycwzbList(WebClient client, String symbol) {
        try {
            Page p = client.getPage(API_ZYCWZB + symbol);
            WebResponse wp = p.getWebResponse();
            String sJson = wp.getContentAsString();
            return parseZycwzb(sJson,symbol);
        } catch (Exception e) {
            log.error("获取主要财务指标出错", e);
            return null;
        }
    }
    /**
     * 资产负债表数据
     * @param client
     * @return
     * @throws IOException
     * @throws MalformedURLException
     * @author douzh
     */
    public static List<TBalSheet> getBalSheetList(WebClient client, String symbol) {
        try {
            Page p = client.getPage(API_BALSHEET + symbol);
            WebResponse wp = p.getWebResponse();
            String sJson = wp.getContentAsString();
            return parseBalSheet(sJson,symbol);
        } catch (Exception e) {
            log.error("获取主要财务指标出错", e);
            return null;
        }
    }

    /**
     * @param sJson
     * @return
     * @author douzh
     */
    private static List<TBalSheet> parseBalSheet(String sJson, String symbol) {
        List<TBalSheet> lst = new ArrayList<TBalSheet>();
        JSONObject.fromObject(sJson);
        JSONObject json = JSONObject.fromObject(sJson);
        String comptype=json.getString("comptype");
        JSONArray arr = json.getJSONArray("list");
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> ite = arr.iterator();
        while (ite.hasNext()) {
            try {
                JSONObject jZb = ite.next();
                TBalSheet zb = new TBalSheet(jZb,symbol,comptype);
                lst.add(zb);
            } catch (Exception e) {
                continue;
            }
        }
        return lst;
    }
    /**
     * @param sJson
     * @return
     * @author douzh
     */
    private static List<TZycwzb> parseZycwzb(String sJson, String symbol) {
        List<TZycwzb> lstZb = new ArrayList<TZycwzb>();
        JSONObject.fromObject(sJson);
        JSONObject json = JSONObject.fromObject(sJson);
        JSONArray arr = json.getJSONArray("list");
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> ite = arr.iterator();
        while (ite.hasNext()) {
            try {
                JSONObject jZb = ite.next();
                TZycwzb zb = new TZycwzb(jZb,symbol);
                lstZb.add(zb);
            } catch (Exception e) {
                continue;
            }
        }
        return lstZb;
    }
    /**
     * 需要获取主要财务指标的股票
     * @return
     * @author douzh
     */
    @SuppressWarnings("unchecked")
    public static List<TStock> getStockNeedZycwzb(){
        StringBuilder hql=new StringBuilder();
        hql.append("select stock from TStock stock where not exists (");
        hql.append("select zb.symbol from TZycwzb zb where zb.symbol=stock.symbol");
        hql.append(")");
        return (List<TStock>) BaseManager.getDao().getList(hql.toString(), null);
    }
    /**
     * 需要获取资产负债表的股票
     * @return
     * @author douzh
     */
    @SuppressWarnings("unchecked")
    public static List<TStock> getStockNeedBalSheet(){
        StringBuilder hql=new StringBuilder();
        hql.append("select stock from TStock stock where not exists (");
        hql.append("select zb.symbol from TBalSheet zb where zb.symbol=stock.symbol");
        hql.append(")");
        return (List<TStock>) BaseManager.getDao().getList(hql.toString(), null);
    }
}
