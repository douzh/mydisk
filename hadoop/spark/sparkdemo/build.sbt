name := "SparkDemo"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "2.1.0.cloudera1" % "provided",
    "com.typesafe" % "config" % "1.3.1",
    "org.scalikejdbc" %% "scalikejdbc" % "3.0.1",
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.0.1",
    "mysql" % "mysql-connector-java" % "6.0.6",
    "org.apache.spark" %% "spark-sql" % "2.1.0.cloudera1" % "provided"
    )