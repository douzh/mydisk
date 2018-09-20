package com.iteedu.demo.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.{ SaveMode, SparkSession }
import org.apache.spark.sql.Column
import java.util.UUID
import com.alibaba.fastjson.JSON
import org.apache.hadoop.hive.ql.exec.spark.SparkUtilities

/**
 * csv文件读取与操作
 *
 */
object TxtFileDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark init test");
    conf.setMaster("local");
    val spark = SparkSession.builder.config(conf).getOrCreate();
    var frdd = spark.read.textFile("D:\\data\\inv_contract.csv").rdd.map(f => (f.split("\001", -1), f))
    val numE = frdd.map(f => (f._1.length, f._2)).filter(n => 41 != n._1)
    val numR = frdd.filter(n => 41 == n._1.length)

    val numRs = numE.map(f => ("f0001",s"column number error,need ${41}, find ${f._1}",f._2))
    val lstJsonErr = List(34, 33).map(i =>
    numR.map(n => isJson(n._1(i), i)).filter(n => n._1 == false))
    val jsonErr = lstJsonErr.reduce((r1, r2) => r1.union(r2))
    val jsonRs=jsonErr.map(f=>("f0002",s"json column type error, column position ${f._2}",f._3))
    
    val rs=numRs.union(jsonRs)
    rs.foreach(n => println(n.toString()))
  }
  def isJson(s: String, index: Int): (Boolean, Int, String) = {
    try {
      JSON.parseObject(s)
      return (true, index, s)
    } catch {
      case ex: Exception => {
        return (false, index, s)
      }
    }
  }
}