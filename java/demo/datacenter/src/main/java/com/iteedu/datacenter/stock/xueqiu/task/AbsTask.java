package com.iteedu.datacenter.stock.xueqiu.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbsTask {
	
	protected  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	protected  String now =sdf.format(new Date());

}
