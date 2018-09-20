package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.MongoCollection;

public class UpdateZhsySheetTask extends AbsTask implements Runnable{

	 /**
     * 综合损益表
     */
    public static final String API="https://xueqiu.com/stock/f10/incstatement.json?page=1&size=200&symbol=";
    
	
	private TaskParam param;
	
	public UpdateZhsySheetTask(TaskParam param) {
		super();
		this.param = param;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		  try {
	            String sJson = XueqiuApi.getRs(API + param.getSymbol());
	            Document doc = Document.parse(sJson);
	            List<Document> lst=(List<Document>) doc.get("list");
	            MongoCollection<Document> collection = param.getDb().getCollection("zhsysheet");
	            for(Document sheet:lst){
	            	String key=param.getSymbol()+"-"+sheet.getString("enddate");
	            	sheet.put("_id", key);
	            	doc.put("symbol", param.getSymbol());
	            	doc.put("createtime", now);
	            	DbUtils.upsertById(collection, sheet);
	            }
	            System.out.println(Calendar.getInstance().getTime()+" zhsysheet finish:"+param.getSymbol());
	            return ;
	        } catch (Exception e) {
	            return;
	        }
	}

}
