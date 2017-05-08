package com.iteedu.datacenter;

import java.util.Date;
import java.util.List;

import com.iteedu.datacenter.stock.xueqiu.XueqiuApi;
import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.iteedu.datacenter.stock.xueqiu.task.UpdateKLineDayPBTask;
import com.iteedu.datacenter.stock.xueqiu.task.UpdateZycwzbTask;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			// 连接到数据库
			MongoDatabase db = mongoClient.getDatabase("stock");
			System.out.println("Connect to database successfully");
			// List<TStock> lstStock=XueqiuApi.initStocklist(db);
			List<TStock> lstStock = XueqiuApi.getStocklist(db);
			for (TStock s : lstStock) {
				TaskExecutor.submitTask(new UpdateKLineDayPBTask(new TaskParam(
						s, db)));
				// TaskExecutor.submitTask(new UpdateStackFinalTask(new
				// TaskParam(s,db)));
				// TaskExecutor.submitTask(new UpdateStackFinalTask(new
				// TaskParam(s,db)));
				// TaskExecutor.submitTask(new CalcAveRoeTask(new
				// TaskParam(s,db)));
				// // TaskExecutor.submitTask(new UpdateBalSheetTask(new
				// TaskParam(s.getSymbol(),db)));
//				 TaskExecutor.submitTask(new UpdateZycwzbTask(new
//				 TaskParam(s.getSymbol(),db)));
				// TaskExecutor.submitTask(new UpdateKLineDayTask(new
				// TaskParam(s.getSymbol(),db)));
			}
			System.out.println("end:" + new Date());
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
