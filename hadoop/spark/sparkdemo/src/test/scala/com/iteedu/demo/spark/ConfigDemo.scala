package com.iteedu.demo.spark

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions.asScalaSet
import scala.tools.scalap.Main

object ConfigDemo {

  private val appConf = ConfigFactory.load()

  def main(args: Array[String]): Unit = {
    val cset = appConf.getConfig("db.single")
    println(cset)
    val configMap = asScalaSet(cset.entrySet()).
      filter(entry => Array("driver", "url", "user", "password").contains(entry.getKey)).
      map(entry => (entry.getKey, entry.getValue.unwrapped().toString())).
      toMap
      println(configMap)
  }
}