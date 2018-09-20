package com.iteedu.demo.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.{ SaveMode, SparkSession }
import org.apache.spark.sql.Column
import java.util.UUID

/**
 * csv文件读取与操作
 *
 */
object JsonToDfDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark init test");
    conf.setMaster("local");
    val spark = SparkSession.builder.config(conf).getOrCreate();
     val json="""[{"date":"2017-04-15","principal":"6000.00","interest":"100.00"},{"date":"2017-08-15","principal":"6000.00","interest":"100.00"},{"date":"2018-01-15","principal":"6000.00","interest":"100.00"}]"""
     val jdf=spark.read.json(spark.sparkContext.makeRDD(json:: Nil))
     jdf.show()
  }
  val uuid=()=>{
    UUID.randomUUID()
  }
}