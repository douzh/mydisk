package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;

import org.bson.Document;

import com.iteedu.base.SMath;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class BuildStackFinalTask extends AbsTask implements Runnable {

	private TaskParam param;

	public BuildStackFinalTask(TaskParam param) {
		super();
		this.param = param;
	}

	public void run() {
		try {
			MongoCollection<Document> stockfinal = param.getDb().getCollection(
					"stockfinal");
			Document condition = new Document();
			condition.put("_id", param.getSymbol());
			FindIterable<Document> ite = stockfinal.find(condition);
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
			MongoCollection<Document> roe = param.getDb().getCollection(
					"stockroe");
			FindIterable<Document> iteroe = roe
					.find(new Document().append("_id", param.getSymbol()));
			for (Document doc : iteroe) {
				stock.putAll(doc);
			}
			MongoCollection<Document> page = param.getDb().getCollection(
					"stockpage");
			FindIterable<Document> itepage = page
					.find(new Document().append("symbol", param.getSymbol()));
			for (Document doc : itepage) {
				stock.putAll(doc);
			}
			stock.put("roeyear", SMath.dformat(getRoeyear(stock)));
			DbUtils.upsertById(stockfinal, stock);
			 System.out.println(Calendar.getInstance().getTime()
			 + " UpdateStackFinalTask finish:" + param.getSymbol());
		} catch (Exception e) {
			System.out.println("UpdateStackFinalTask error:"
					+ param.getSymbol());
			e.printStackTrace();
			return;
		}
	}
	
	private double getRoeyear(Document stock){
		Double pb=stock.getDouble("pb");
		if(pb==null||pb<0){
			return 1000d;
		}
		if(pb<1){
			return 0d;
		}
		Double roe=stock.getDouble("roefinal");
		if(roe==null||roe<0){
			return 1000d;
		}
		return SMath.log(pb, 1+roe/100);
	}
}
