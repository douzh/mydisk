package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.iteedu.base.SMath;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class CalcKLineDayPBTask extends AbsTask implements Runnable {

	private TaskParam param;

	public CalcKLineDayPBTask(TaskParam param) {
		super();
		this.param = param;
	}

	public void run() {
		try {
			MongoCollection<Document> cwzb = param.getDb().getCollection(
					"zycwzb");
			// 取出账务数据
			FindIterable<Document> pbite = cwzb
					.find(new Document().append("symbol", param.getSymbol()))
					.projection(
							new Document().append("symbol", 1)
									.append("naps", 1).append("reportdate", 1))
					.sort(new Document().append("_id", -1));
			List<Document> pblist = new ArrayList<Document>();
			for (Document doc : pbite) {
				pblist.add(doc);
			}

			MongoCollection<Document> klineday = param.getDb().getCollection(
					"klineday");
			Document filter = new Document()
					.append("symbol", param.getSymbol());
			filter.append("pb", null);
			FindIterable<Document> iteklineday = klineday
					.find(filter)
					.projection(
							new Document().append("_id", 1).append("close", 1)
									.append("pb", 1).append("date", 1))
					.sort(new Document().append("_id", 1));
			Double preClose = null;
			Double prePb = null;
			Double sub = null;
			for (Document doc : iteklineday) {
				String date = doc.getString("date");
				Document naps = getNaps(pblist, date);
				if (naps == null || naps.getDouble("naps") == null)
					continue;
				doc.put("naps", naps.getDouble("naps"));
				doc.put("reportdate", naps.getString("reportdate"));
				Double close = doc.getDouble("close");
				Double pb = close / naps.getDouble("naps");
				if (preClose != null
						&& Math.abs((preClose - close) / preClose) > 0.11) {
					// 拆股后股价变化
					sub = prePb - pb;
				}
				if (prePb != null && Math.abs((prePb - pb) / prePb) <= 0.11) {
					sub = null;
				}
				if (sub != null) {
					pb += sub;
				}
				doc.put("pb", SMath.dformat(pb));
				prePb = pb;
				preClose = close;
				doc.put("createtime", now);
				DbUtils.upsertById(klineday, doc);
			}
			System.out.println(Calendar.getInstance().getTime()
					+ " UpdateKLineDayPBTask finish:" + param.getSymbol());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private Document getNaps(List<Document> pblist, String date) {
		Document naps = null;
		for (Document doc : pblist) {
			if (doc.getDouble("naps") == null) {
				continue;
			}
			if (doc.getDouble("naps") == 0) {
				continue;
			}
			naps = doc;
			if (date.compareTo(doc.getString("reportdate")) <= 0) {
				continue;
			}
			return naps;
		}
		return naps;
	}

}
