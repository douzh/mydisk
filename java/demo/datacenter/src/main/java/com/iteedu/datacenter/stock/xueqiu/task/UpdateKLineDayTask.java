package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.iteedu.base.DateUtils;
import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class UpdateKLineDayTask extends AbsTask implements Runnable {

	/**
	 * 日K线
	 */
	public static final String url = "https://xueqiu.com/stock/forchartk/stocklist.json?period=1day&type=normal";

	private TaskParam param;

	public UpdateKLineDayTask(TaskParam param) {
		super();
		this.param = param;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		try {
			MongoCollection<Document> klineday = param.getDb().getCollection("klineday");
			FindIterable<Document> iteklineday = klineday.find(new Document().append("symbol", param.getSymbol()))
					.sort(new Document().append("date", -1)).limit(1);
			Document first = iteklineday.first();
			long begin=0;
			String endDate=DateUtils.getYesterdayYmd();
			long end=DateUtils.getLongTime(endDate);
			if (first != null) {
				String d=first.getString("date");
				begin=DateUtils.getLongTime(d);
				if(begin>=end){
					return;
				}
			}
			// db.klineday.aggregate([{$group:{_id:'$symbol','maxdate':{'$max':'$createtime'}}}]))
			String sJson = XueqiuApi.getRs(url+"&end="+end+"&begin="+begin+"&symbol=" + param.getSymbol());
			Document rs = Document.parse(sJson);
			List<Document> lst = (List<Document>) rs.get("chartlist");
			for (Document doc : lst) {
				Date d = new Date(doc.getString("time"));
				String date = sdf.format(d);
				String key = param.getSymbol() + "-" + sdf.format(d);
				doc.put("_id", key);
				doc.put("symbol", param.getSymbol());
				doc.put("date", date);
				doc.put("createtime", now);
				DbUtils.upsertById(klineday, doc);
			}
			System.out.println(Calendar.getInstance().getTime() + " klineday finish:" + param.getSymbol());
		} catch (Exception e) {
			return;
		}
	}

}
