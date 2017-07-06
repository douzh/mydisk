package com.iteedu.demo.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/**
 * 对文件里的单词记数示例
 *
 */
object WordCountDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark init test");
    //如果在本地测试，设置master为本地
    //方法一：在运行配制里添加JVM参数
    //-Dspark.master=local
    //方法二：在代码里指定
    conf.setMaster("local");
    val sc = new SparkContext(conf);

    val textFile = sc.textFile("file:///D:/generatorConfig.xml")
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    counts.saveAsTextFile("file:///D:/out")
  }
}