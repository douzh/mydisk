package com.iteedu.demo.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.{ SaveMode, SparkSession }

/**
 * csv文件读取与操作
 *
 */
object CsvFileGroupByDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark init test");
    conf.setMaster("local");
    val spark = SparkSession.builder.config(conf).getOrCreate();
    val proinfo = spark.read.format("csv").option("header","true").option("delimiter","^").load("D:/data/biz_pro_info.csv");
     val rplan = spark.read.format("csv").option("header","true").option("delimiter","^").load("D:/data/biz_pro_repay_plan.csv");
    //创建临时表，用于spark sql查询用
    proinfo.createOrReplaceTempView("proinfo");
    rplan.createOrReplaceTempView("rplan");
    val rs=spark.sql("select proinfo.project_no,count(1) from proinfo,rplan where proinfo.project_no=rplan.project_no group by proinfo.project_no");
    rs.show();
  }
}