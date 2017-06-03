package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * PB分布计算
 *
 */
public class CalcPbDistTask  extends AbsTask implements Runnable {

	private TaskParam param;

	public CalcPbDistTask(TaskParam param) {
		this.param = param;
	}
	
	public void run() {
		//获取历史排序PB
		List<Double> lstPb=new ArrayList<Double>();
		List<Double> lstPbDate=new ArrayList<Double>();
		getSortPbList(lstPb,lstPbDate);
		if(CollectionUtils.isEmpty(lstPb)){
			return;
		}
		//用当前PB比较出参数
		MongoCollection<Document> colPb = param.getDb().getCollection("pbdist");
		Document item = new Document();
		item.put("_id", param.getSymbol());
		item.put("symbol", param.getSymbol());
		item.put("name", param.getName());
		for(int i=0;i<=100;i+=5){
			item.put("pb"+i, getPbN(lstPb,i));
		}
		for(int i=0;i<=100;i+=5){
			item.put("pbdate"+i, getPbN(lstPbDate,i));
		}
		calcPbx(lstPb,lstPbDate, item);

		item.put("createtime", now);
		DbUtils.upsertById(colPb, item);
		System.out.println("update symbol:"+param.getSymbol());
	}

	private void calcPbx(List<Double> lstPb,List<Double> lstPbDate, Document item) {
		MongoCollection<Document> col = param.getDb().getCollection(
				"stockpage");
		FindIterable<Document> ite = col
				.find(new Document().append("_id", param.getSymbol()));
		Double pb=ite.first().getDouble("pb");
		item.put("pbx", 100);
		for(int i=0;i<lstPb.size();i++){
			if(lstPb.get(i)<pb){
				continue;
			}
			int pbx=100*i/lstPb.size();
			item.put("pbx", pbx);
			item.put("pbscore", 100-pbx);
			break;
		}
		item.put("pbxdate", 100);
		for(int i=0;i<lstPbDate.size();i++){
			if(lstPbDate.get(i)<pb){
				continue;
			}
			int pbx=100*i/lstPbDate.size();
			item.put("pbxdate", pbx);
			break;
		}
	}

	private Double getPbN(List<Double> lstPb,int i){
		if(i==0){
			return lstPb.get(0);
		}
		if(i==100){
			return lstPb.get(lstPb.size()-1);
		}
		int p=Long.valueOf(lstPb.size()*i/100).intValue();
		return lstPb.get(p);
	}
	
	private void getSortPbList(List<Double> lstPb,List<Double> lstPbDate) {
		MongoCollection<Document> klineday = param.getDb().getCollection("klineday");
		FindIterable<Document> iteklineday = klineday
				.find(new Document().append("symbol", param.getSymbol()))
				.projection(new Document().append("_id", 1).append("pb", 1).append("date", 1))
				.sort(new Document().append("pb", 1));
		for (Document doc : iteklineday) {
			Double pb = doc.getDouble("pb");
			if(pb==null){
				continue;
			}
			lstPb.add(pb);
			String date=doc.getString("date");
			if(date.compareTo("20120101")>=0){
				lstPbDate.add(pb);
			}
		}
	}
}
