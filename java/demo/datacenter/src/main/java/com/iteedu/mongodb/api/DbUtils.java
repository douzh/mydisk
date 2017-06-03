package com.iteedu.mongodb.api;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

public class DbUtils {

	public static UpdateResult upsertById(MongoCollection<Document> col,Document doc){
		return col.updateOne(Filters.eq("_id", doc.get("_id")), new Document().append("$set", doc), new UpdateOptions().upsert(true));
	}
	

	public static List<TStock> getStocklist(MongoDatabase db) {
		List<TStock> lstStock= new ArrayList<TStock>();
		MongoCollection<Document> slist = db.getCollection("stockpage");
	
		FindIterable<Document> ite =slist.find().sort(new Document().append("_id", 1));
		for (Document doc : ite) {
			TStock s=new TStock();
			s.setName(doc.getString("name"));
			s.setSymbol(doc.getString("symbol"));
			lstStock.add(s);
		}
		return lstStock;
	}
}
