package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;

import com.iteedu.base.SMath;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class CalcAveRoeTask extends AbsTask implements Runnable {

	private TaskParam param;

	public CalcAveRoeTask(TaskParam param) {
		super();
		this.param = param;
	}

	public void run() {
		try {
			String listdate = getListdate();
			MongoCollection<Document> collection = param.getDb().getCollection(
					"zycwzb");
			FindIterable<Document> ite = getRoelist(collection);
			int count=0;
			List<Double> lstRoe = new ArrayList<Double>();
			for (Document doc : ite) {
				if (listdate != null
						&& doc.getString("reportdate").compareTo(listdate) < 0)
					continue;
				if (doc.getString("reportdate").compareTo("20071231") < 0)
					continue;
				count++;
				Double d = doc.getDouble("weightedroe");
				if (d == null || d > 70)
					continue;
				lstRoe.add(d);
			}
			if (CollectionUtils.isEmpty(lstRoe)) {
				return;
			}
			Double[] arrRoe = new Double[lstRoe.size()];
			lstRoe.toArray(arrRoe);
			MongoCollection<Document> calc = param.getDb().getCollection(
					"stockroe");
			Document ave = new Document();
			ave.put("_id", param.getSymbol());
			ave.put("name", param.getName());
			ave.put("listdate", listdate);
			Double roeave = SMath.dformat(SMath.getAverage(arrRoe));
			ave.put("roeave", roeave);
			Double roemin = roeave > lstRoe.get(0) ? lstRoe.get(0) : roeave;
			ave.put("roemin", SMath.dformat(roeave));
			Double roediv = SMath.dformat(SMath.getStandardDiviation(arrRoe));
			ave.put("roesdiv", roediv);
			Double roecv = roediv < roeave ? SMath.dformat(roediv / roeave) : 1;
			if (lstRoe.size() == 1) {
				roecv = 1d;
			}
			ave.put("roecv", roecv);
			// Double
			// score=SMath.dformat((lstRoe.get(0)+2*roeave)+30*(1-roecv)+3*lstRoe.size());
			if (roeave > 0) {
				Double tmp = Math.abs(roeave - roemin) / roeave;
				double tmp2 = (1 - roecv + lstRoe.size() / 10 + (1 - (tmp > 1 ? 1
						: tmp))+(lstRoe.size()>2?lstRoe.size():0)/count);
				Double score = SMath.dformat((lstRoe.get(0) + roeave) / 2
						+ roeave * tmp2);
				ave.put("roescore", score);
				ave.put("roefinal", SMath.dformat(score / 5));
			} else {
				ave.put("roescore", roeave);
				ave.put("roefinal", roeave);
			}
			ave.put("reportcount", count);
			ave.put("roecount", lstRoe.size());
			ave.put("roelast", lstRoe.get(0));
			ave.put("roelastsub", SMath.dformat(lstRoe.get(0) - roeave));
			ave.put("roelist", lstRoe);
			setLevel(ave);
			DbUtils.upsertById(calc, ave);
			System.out.println(Calendar.getInstance().getTime()
					+ " CalcAveRoeTask finish:" + param.getSymbol());
		} catch (Exception e) {
			System.out.println(" CalcAveRoeTask error:" + param.getSymbol());
			e.printStackTrace();
			return;
		}
	}

	private void setLevel(Document ave){
		Double roefinal=ave.getDouble("roefinal");
		Double roelast=ave.getDouble("roelast");
		String level=null;
		if(roefinal>=20&&roelast>=20){
			level="A";
		}else if(roefinal>=15&&roelast>=15){
			level="B";
		}else if(roefinal>=10&&roelast>=10){
			level="C";
		}else if(roefinal>=5&&roelast>=5){
			level="D";
		}else if(roefinal>=0&&roelast>=0){
			level="E";
		}else {
			level="F";
		}
		ave.put("roelevel", level);
	}
	private FindIterable<Document> getRoelist(
			MongoCollection<Document> collection) {
		Document condition = new Document();
		condition.put("symbol", param.getSymbol());
		Pattern pattern = Pattern.compile("\\d\\d\\d\\d1231",
				Pattern.CASE_INSENSITIVE);
		condition.put("reportdate", pattern);
		// String listdate=getListdate();
		// if(StringUtils.isNotBlank(listdate)){
		// condition.put("reportdate", new Document().put("$gte", listdate));
		// }
		Document keys = new Document();
		keys.put("_id", 1);
		keys.put("reportdate", 1);
		keys.put("weightedroe", 1);
		FindIterable<Document> ite = collection.find(condition)
				.projection(keys).sort(new Document().append("reportdate", -1));
		return ite;
	}

	private String getListdate() {
		MongoCollection<Document> stocklist = param.getDb().getCollection(
				"listdate");
		Document condition = new Document();
		condition.put("_id", param.getSymbol());
		FindIterable<Document> ite = stocklist.find(condition);
		Document doc = ite.first();
		return doc.getString("listdate");
	}
}
