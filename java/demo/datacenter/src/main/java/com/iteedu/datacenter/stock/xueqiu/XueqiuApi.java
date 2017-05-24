package com.iteedu.datacenter.stock.xueqiu;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.bson.Document;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.iteedu.base.HttpUtil;
import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class XueqiuApi {

	private static WebClient client = null;
	static {
		client = HttpUtil.getClient();
		try {
			loginXueqiu(client);
		} catch (Exception e) {
		}
	}

	public static List<TStock> initStocklist(MongoDatabase db) {
		List<TStock> lstStock= XueqiuApi.getStockList();
		MongoCollection<Document> collection = db.getCollection("stocklist");
		collection.drop();
		collection = db.getCollection("stocklist");
		for (TStock s : lstStock) {
			Document d = new Document();
			d.put("_id", s.getCode());
			d.put("symbol", s.getSymbol());
			d.put("name", s.getName());
			collection.insertOne(d);
		}
		return lstStock;
	}
	
	public static List<TStock> getStocklist(MongoDatabase db) {
		List<TStock> lstStock= new ArrayList<TStock>();
		MongoCollection<Document> slist = db.getCollection("stockpage");
	
		FindIterable<Document> ite =slist.find().sort(new Document().append("_id", 1));
		for (Document doc : ite) {
			TStock s=new TStock();
			s.setName(doc.getString("name"));
			s.setSymbol(doc.getString("symbol"));
			lstStock.add(s);
		}
		return lstStock;
	}
	
	public static String getRs(String url){
		try {
			Page p= client.getPage(url);
			WebResponse wp = p.getWebResponse();
            String rs = wp.getContentAsString();
            return rs;
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @param client
	 * @return
	 */
	public static List<TStock> getStockList() {
		List<TStock> lstStock = getStockList(XueqiuConsts.API_SHA);
		lstStock.addAll(getStockList(XueqiuConsts.API_SZA));
		return lstStock;
	}

	@SuppressWarnings("unchecked")
	private static List<TStock> getStockList(String url) {
		try {
			Page p = client.getPage(url);
			WebResponse wp = p.getWebResponse();
			String sJson = wp.getContentAsString();
			Document doc = Document.parse(sJson);
			List<List<String>> lstData = (List<List<String>>) doc.get("data");
			List<TStock> lstStock = new ArrayList<TStock>();
			for (List<String> stock : lstData) {
				TStock s = new TStock();
				s.setSymbol(stock.get(0));
				s.setName(stock.get(1));
				lstStock.add(s);
			}
			return lstStock;
		} catch (Exception e) {
			return new ArrayList<TStock>();
		}
	}

	private static HtmlPage loginXueqiu(WebClient client) throws Exception {
		WebRequest webRequest = new WebRequest(
				new URL(
						"https://xueqiu.com/snowman/login?username=xueqiuclient@126.com&password=xueqiu"));
		byte[] rs = sendRequest(client, webRequest);
		String strRs = new String(rs);
		return null;
	}

	// 底层请求
	private static byte[] sendRequest(WebClient webClient, WebRequest webRequest)
			throws Exception {
		byte[] responseContent = null;
		Page page = webClient.getPage(webRequest);

		WebResponse webResponse = page.getWebResponse();

		int status = webResponse.getStatusCode();

		System.out.println("Charset : " + webResponse.getContentCharset());

		System.out.println("ContentType : " + webResponse.getContentType());

		// 读取数据内容
		if (status == 200) {
			if (page.isHtmlPage()) {
				// 等待JS执行完成，包括远程JS文件请求，Dom处理
				webClient.waitForBackgroundJavaScript(10000); // 使用JS还原网页
				responseContent = ((HtmlPage) page).asXml().getBytes();
			} else {
				InputStream bodyStream = webResponse.getContentAsStream();
				responseContent = IOUtils.toByteArray(bodyStream);
				bodyStream.close();
			}
		}
		// 关闭响应流
		webResponse.cleanUp();
		return responseContent;
	}

	public static String getJsonReslut(WebClient client, String url) {
		try {
			Page p = client.getPage(url);
			WebResponse wp = p.getWebResponse();
			String sJson = wp.getContentAsString();
			return sJson;
		} catch (Exception e) {
			return null;
		}
	}
}
