package com.iteedu.datacenter.stock.xueqiu.task;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.client.MongoCollection;

public class UpdateCompanyInfoTask extends AbsTask implements Runnable {

	public static final String API_INFO = "http://xueqiu.com/stock/f10/compinfo.json?symbol=";

	private TaskParam param;

	public UpdateCompanyInfoTask(TaskParam param) {
		super();
		this.param = param;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			MongoCollection<Document> collection = param.getDb().getCollection(
					"compinfo");
			if (collection.count(new Document().append("_id",
					param.getSymbol())) > 0) {
				return;
			}
			String sJson = XueqiuApi.getRs(API_INFO + param.getSymbol());
			Document bson = Document.parse(sJson);
			Document info = (Document) bson.get("tqCompInfo");

			info.put("_id", param.getSymbol());
			info.put("name", param.getName());
			List<Document> industryList=(List<Document>) info.get("tqCompIndustryList");
			if(CollectionUtils.isNotEmpty(industryList)){
				String industry="";
				for(Document doc:industryList){
					industry+=doc.getString("level2name");
				}
				info.put("industry", industry);
			}
			List<Document> mapList=(List<Document>) info.get("tqCompBoardmapList");
			if(CollectionUtils.isNotEmpty(mapList)){
				info.put("province", mapList.get(0).getString("keyname"));
				if(mapList.size()>1){
				info.put("city", mapList.get(1).getString("keyname"));
				}
			}
			info.put("createtime", now);
			DbUtils.upsertById(collection, info);
			System.out.println(Calendar.getInstance().getTime()
					+ " zycwzb finish:" + param.getSymbol());
		} catch (Exception e) {
			return;
		}
	}
}
