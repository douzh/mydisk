package com.iteedu.datacenter;

import java.util.Date;
import java.util.List;

import com.iteedu.datacenter.stock.xueqiu.bean.TStock;
import com.iteedu.mongodb.api.DbUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Hello world!
 *
 */
public class UpdateMain {
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
//				 TaskExecutor.submitTask(new UpdateKLineDayTask(new
//				 TaskParam(s, db)));
				// TaskExecutor.submitTask(new UpdateBalSheetTask(new
				// TaskParam(s.getSymbol(),db)));
//				 TaskExecutor.submitTask(new UpdateZycwzbTask(new
//				 TaskParam(s.getSymbol(),db)));
//				 TaskExecutor.submitTask(new UpdateCompanyInfoTask(new
//				 TaskParam(s, db)));
			}
			System.out.println("end:" + new Date());
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
