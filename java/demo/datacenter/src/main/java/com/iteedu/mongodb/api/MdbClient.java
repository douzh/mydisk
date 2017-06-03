package com.iteedu.mongodb.api;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MdbClient {

	MongoClient mongoClient =null;

	public MdbClient(String ip,int port) {
		super();
		this.mongoClient =  new MongoClient(ip, port);
	}
}
