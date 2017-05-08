package com.iteedu.mongodb.api;

import com.mongodb.MongoClient;

public class MdbClient {

	MongoClient mongoClient =null;

	public MdbClient(String ip,int port) {
		super();
		this.mongoClient =  new MongoClient(ip, port);
	}
	
	
}
