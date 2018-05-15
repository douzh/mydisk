package com.iteedu.demo.spark

import scalikejdbc.config.DBs
import scalikejdbc._
import scalikejdbc.config.DBsWithEnv

object DBConfigTest {
  
  def main(args: Array[String]): Unit = {
    //http://scalikejdbc.org/documentation/configuration.html
     DBs.setupAll()
     val result = NamedDB(Symbol("single")) readOnly { implicit session =>
            sql"select * from run_batch_task ".map((rs: WrappedResultSet) => rs.string("batch_date")).list.apply
        }
     println(result)
     DBs.closeAll()
  }
}