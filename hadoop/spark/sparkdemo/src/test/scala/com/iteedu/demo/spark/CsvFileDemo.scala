package com.iteedu.demo.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.{ SaveMode, SparkSession }

/**
 * csv文件读取与操作
 *
 */
object CsvFileDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark init test");
    conf.setMaster("local");
    val spark = SparkSession.builder.config(conf).getOrCreate();
    val csv = spark.read.format("csv").option("header","true").option("delimiter","^").load("D:/data/biz_pro_info.csv");
    //创建临时表，用于spark sql查询用
    csv.createOrReplaceTempView("proinfo");
    val rs=spark.sql("select * from proinfo limit 10");
    rs.show();
  }
}