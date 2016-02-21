package com.iteedu.stock.spider.common.utils;
import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/*
 * @(#)HttpUtil.java	2015-4-23 下午4:40:51
 * HtmlUnit
 * Copyright 2015 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * HttpUtil
 * @author douzh
 * @time 2015-4-23下午4:40:51
 */
public class HttpUtil {
    /**
     * @return
     * @author douzh
     * @time 2015-4-23下午4:36:05
     */
    public static WebClient getClient() {
        WebClient client = new WebClient(BrowserVersion.FIREFOX_24);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setRedirectEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setGeolocationEnabled(true);
//        client.addWebWindowListener(new WebWindowListener() {
//            public void webWindowOpened(WebWindowEvent event) {
//                System.out.println("Web Window Openning");
//            }
//
//            public void webWindowContentChanged(WebWindowEvent event) {
//                System.out.println("Web Content Changed");
//            }
//
//            public void webWindowClosed(WebWindowEvent event) {
//                System.out.println("Web Window Closed");
//            }
//        });
        client.setAjaxController(new NicelyResynchronizingAjaxController() {
            public boolean processSynchron(HtmlPage page, WebRequest settings,
                    boolean async) {
                System.out.println(settings.getUrl());
                return super.processSynchron(page, settings, async);
            }
        });
        return client;
    }
}
