package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.MongoCollection;

public class UpdateBalSheetTask extends AbsTask implements Runnable{

	 /**
     * 资产负债表
     */
    public static final String API_BALSHEET="http://xueqiu.com/stock/f10/balsheet.json?page=1&size=40&symbol=";
    
	
	private TaskParam param;
	
	public UpdateBalSheetTask(TaskParam param) {
		super();
		this.param = param;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		  try {
	            String sJson = XueqiuApi.getRs(API_BALSHEET + param.getSymbol());
	            Document doc = Document.parse(sJson);
	            List<Document> lst=(List<Document>) doc.get("list");
	            MongoCollection<Document> collection = param.getDb().getCollection("balsheet");
	            for(Document sheet:lst){
	            	String key=param.getSymbol()+"-"+sheet.getString("reportdate");
	            	sheet.put("_id", key);
	            	doc.put("symbol", param.getSymbol());
	            	doc.put("createtime", now);
	            	DbUtils.upsertById(collection, sheet);
	            }
	            System.out.println(Calendar.getInstance().getTime()+" balsheet finish:"+param.getSymbol());
	            return ;
	        } catch (Exception e) {
	            return;
	        }
	}

}
