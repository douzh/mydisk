package com.iteedu.mongodb.api;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

public class DbUtils {

	public static UpdateResult upsertById(MongoCollection<Document> col,Document doc){
		return col.updateOne(Filters.eq("_id", doc.get("_id")), new Document().append("$set", doc), new UpdateOptions().upsert(true));
	}
}
