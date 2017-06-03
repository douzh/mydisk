package com.iteedu.datacenter;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {

	//初始线程池是3各线程 
	//如果一下子任务过多，就创建线程但是不能超过5各 
	//也就是说最多可以一次处理5各任务 
	//如果一次来30各任务，第一次只能处理5个任务 
	//剩下的任务放到队列里面 
	private static ThreadPoolExecutor te = new ThreadPoolExecutor(1, 1, 5, 
	         TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), 
	         new ThreadPoolExecutor.DiscardOldestPolicy());
	
	
	public static void submitTask(Runnable task){
		te.submit(task);
	}
	
	private static ThreadPoolExecutor calcpool = new ThreadPoolExecutor(50, 50, 5, 
	         TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), 
	         new ThreadPoolExecutor.DiscardOldestPolicy());
	
	
	public static void submitCalcTask(Runnable task){
		calcpool.submit(task);
	}
}
