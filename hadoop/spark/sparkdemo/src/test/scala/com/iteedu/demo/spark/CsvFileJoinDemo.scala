package com.iteedu.demo.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.{ SaveMode, SparkSession }

/**
 * csv文件读取与操作
 *
 */
object CsvFileJoinDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark init test");
    conf.setMaster("local");
    val spark = SparkSession.builder.config(conf).getOrCreate();
    val proinfo = spark.read.format("csv").option("header","true").option("delimiter","^").load("D:/data/biz_pro_info.csv");
     val continfo = spark.read.format("csv").option("header","true").option("delimiter","^").load("D:/data/biz_inv_contract.csv");
    //创建临时表，用于spark sql查询用
    proinfo.createOrReplaceTempView("proinfo");
    continfo.createOrReplaceTempView("continfo");
    val rs=spark.sql("select * from proinfo,continfo where continfo.project_no=proinfo.project_no limit 10");
    rs.show();
  }
}