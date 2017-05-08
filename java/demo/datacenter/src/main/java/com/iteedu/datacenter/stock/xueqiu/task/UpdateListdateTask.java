package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class UpdateListdateTask extends AbsTask implements Runnable {

	private TaskParam param;

	public UpdateListdateTask(TaskParam param) {
		super();
		this.param = param;
	}

	public void run() {
		try {
			MongoCollection<Document> stocklist = param.getDb().getCollection(
					"listdate");
			Document condition = new Document();
			condition.put("_id", param.getSymbol());
			FindIterable<Document> ite = stocklist.find(condition);
			Document stock = null;
			for (Document doc : ite) {
				stock = doc;
			}
			if (stock == null) {
				stock = new Document();
				stock.put("_id", param.getSymbol());
				stock.put("symbol", param.getSymbol());
				stock.put("name", param.getName());
			}
			MongoCollection<Document> klineday = param.getDb().getCollection(
					"klineday");
			Document keys = new Document();
			keys.put("_id", 1);
			keys.put("date", 1);
			keys.put("symbol", 1);
			FindIterable<Document> iteklineday = klineday
					.find(new Document().append("symbol", param.getSymbol()))
					.projection(keys).sort(new Document().append("_id", 1)).limit(1);
			for (Document doc : iteklineday) {
				stock.put("listdate", doc.get("date"));
			}
			DbUtils.upsertById(stocklist, stock);
			 System.out.println(Calendar.getInstance().getTime()
			 + " UpdateStackFinalTask finish:" + param.getSymbol());
		} catch (Exception e) {
			System.out.println("UpdateStackFinalTask error:"
					+ param.getSymbol());
			e.printStackTrace();
			return;
		}
	}
}
