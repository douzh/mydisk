package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.MongoCollection;

public class UpdatePageTask extends AbsTask implements Runnable {

	/**
	 * 资产负债表
	 */
	public static final String API_PAGE = "https://xueqiu.com/stock/screener/screen.json?category=SH&orderby=symbol&order=desc&current=ALL&pb=ALL&page=";

	private TaskParam param;

	public UpdatePageTask(TaskParam param) {
		super();
		this.param = param;
	}

	public void run() {
		try {
			MongoCollection<Document> collection = param.getDb().getCollection(
					"stockpage");
			String sJson = XueqiuApi.getRs(API_PAGE + 1);
			Document page = Document.parse(sJson);
			dealPage(page,collection);
			int count = page.getInteger("count");
			for(int i=2;i<count/20+1;i++){
				sJson = XueqiuApi.getRs(API_PAGE + i);
				page = Document.parse(sJson);
				dealPage(page,collection);
			}
			System.out.println(Calendar.getInstance().getTime()
					+ " UpdatePageTask finish:" + param.getSymbol());
			return;
		} catch (Exception e) {
			return;
		}
	}
	
	private void dealPage(Document page,MongoCollection<Document> collection ){
		List<Document> lst = (List<Document>) page.get("list");
		for (Document s : lst) {
			s.remove("id");
			String key = s.getString("symbol");
			collection.deleteOne(new Document("_id", key));
			s.put("_id", key);
			s.put("createtime", now);
			DbUtils.upsertById(collection, s);
		}
	}

}
