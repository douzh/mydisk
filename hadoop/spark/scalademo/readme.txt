sbt没有新建project生成基本目录的功能  没有类似 sbt init的功能

sbt支持的目录结构，可以手动创建，这不费什么事，也可采用从其他标准sbt项目拷贝

. 
|-- build.sbt         主构建文件 
|—lib                   第三方jar包 
|—project            下有另一个可编程的Build.Scala构建文件可实现高级复杂功能 
|-- src 
| |-- main 
| | |-- Java 
| | |-- resources 
| | |-- scala 
| |-- test 
| |-- java 
| |-- resources 
| |-- scala 
|—target           可选

也可以使用这个工具 Giter8

惯例：build.sbt定义主要简单的配置，对于构建逻辑放到project下的.scala文件中，用于更复杂定义和共享代码

sbt eclipse 为项目加入eclipse工程信息