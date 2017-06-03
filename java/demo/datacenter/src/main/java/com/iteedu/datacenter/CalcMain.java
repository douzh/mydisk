package com.iteedu.datacenter;

import java.util.Date;
import java.util.List;

import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.iteedu.datacenter.stock.xueqiu.task.BuildStackFinalTask;
import com.iteedu.datacenter.stock.xueqiu.task.CalcAveRoeTask;
import com.iteedu.datacenter.stock.xueqiu.task.bean.TaskParam;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Hello world!
 *
 */
public class CalcMain {
	public static void main(String[] args) {

		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("localhost", 9080);
			// 连接到数据库
			MongoDatabase db = mongoClient.getDatabase("stock");
			System.out.println("Connect to database successfully");
			// List<TStock> lstStock=XueqiuApi.initStocklist(db);
			List<TStock> lstStock = DbUtils.getStocklist(db);
			for (TStock s : lstStock) {
//				 TaskExecutor.submitCalcTask(new CalcKLineDayPBTask(new
//				 TaskParam(s, db)));
//				 TaskExecutor.submitCalcTask(new CalcPbDistTask(new
//				 TaskParam(s, db)));
//				 TaskExecutor.submitCalcTask(new CalcListdateTask(new
//				 TaskParam(s, db)));
//				 TaskExecutor.submitCalcTask(new CalcAveRoeTask(new
//				 TaskParam(s,db)));
				 TaskExecutor.submitCalcTask(new BuildStackFinalTask(new
				 TaskParam(s,db)));
			}
			System.out.println("end:" + new Date());
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
