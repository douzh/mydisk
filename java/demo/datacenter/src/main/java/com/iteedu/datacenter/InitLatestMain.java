package com.iteedu.datacenter;

import java.util.Date;

import com.iteedu.datacenter.stock.xueqiu.task.UpdatePageTask;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class InitLatestMain {

	public static void main(String[] args) {

		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 9080);
			// 连接到数据库
			MongoDatabase db = mongoClient.getDatabase("stock");
			System.out.println("Connect to database successfully");
			new UpdatePageTask(new TaskParam(db)).run();
			System.out.println("end:" + new Date());
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
