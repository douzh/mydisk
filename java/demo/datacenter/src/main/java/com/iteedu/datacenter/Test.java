package com.iteedu.datacenter;

import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.iteedu.datacenter.stock.xueqiu.task.CalcAveRoeTask;
import com.iteedu.datacenter.stock.xueqiu.task.CalcKLineDayPBTask;
import com.iteedu.datacenter.stock.xueqiu.task.CalcPbDistTask;
import com.iteedu.datacenter.stock.xueqiu.task.UpdateKLineDayTask;
import com.iteedu.datacenter.stock.xueqiu.task.BuildStackFinalTask;
import com.iteedu.datacenter.stock.xueqiu.task.UpdateZycwzbTask;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

/**
 * Hello world!
 *
 */
public class Test {
	public static void main(String[] args) {

		try {
			MongoClient mongoClient = new MongoClient("localhost", 9080);
			// 连接到数据库
			MongoDatabase db = mongoClient.getDatabase("stock");
			System.out.println("Connect to database successfully");
			 TStock s=new TStock();
			 s.setSymbol("SZ002673");
			 new CalcAveRoeTask(new TaskParam(s,db)).run();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
