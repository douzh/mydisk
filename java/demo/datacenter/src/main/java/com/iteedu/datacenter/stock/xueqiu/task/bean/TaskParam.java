package com.iteedu.datacenter.stock.xueqiu.task.bean;

import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.mongodb.client.MongoDatabase;

public class TaskParam {

	private String symbol;
	private String name;
	private MongoDatabase db;
	public TaskParam(MongoDatabase db) {
		super();
		this.db = db;
	}
	public TaskParam(TStock s, MongoDatabase db) {
		super();
		this.symbol = s.getSymbol();
		name=s.getName();
		this.db = db;
	}
	public TaskParam(String symbol, MongoDatabase db) {
		super();
		this.symbol = symbol;
		this.db = db;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public MongoDatabase getDb() {
		return db;
	}
	public void setDb(MongoDatabase db) {
		this.db = db;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
