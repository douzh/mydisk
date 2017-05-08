package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.MongoCollection;

public class UpdateZycwzbTask extends AbsTask implements Runnable {

	/**
	 * 主要财务指标
	 */
	public static final String API_ZYCWZB = "http://xueqiu.com/stock/f10/finmainindex.json?page=1&size=100&symbol=";

	private TaskParam param;

	public UpdateZycwzbTask(TaskParam param) {
		super();
		this.param = param;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			MongoCollection<Document> collection = param.getDb().getCollection(
					"zycwzb");
			if (collection.count(new Document().append("symbol",
					param.getSymbol()).append("createtime", now)) > 0) {
				return;
			}
			String sJson = XueqiuApi.getRs(API_ZYCWZB + param.getSymbol());
			Document doc = Document.parse(sJson);
			List<Document> lst = (List<Document>) doc.get("list");
			for (Document sheet : lst) {
				String key = param.getSymbol() + "-"
						+ sheet.getString("reportdate");
				sheet.put("_id", key);
				sheet.put("symbol", param.getSymbol());
				sheet.put("createtime", now);
				DbUtils.upsertById(collection, sheet);
			}
			System.out.println(Calendar.getInstance().getTime()
					+ " zycwzb finish:" + param.getSymbol());
		} catch (Exception e) {
			return;
		}
	}
}
