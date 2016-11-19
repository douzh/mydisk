# 基础

中央仓库查询  
http://search.maven.org/

另一个查询网站  
http://mvnrepository.com/

中央仓库地址  
http://repo1.maven.org/

## mvn

常用命令为 ：

    mvn archetype:create ：创建 Maven 项目

    mvn compile ：编译源代码

    mvn test-compile ：编译测试代码

    mvn test ： 运行应用程序中的单元测试

    mvn site ： 生成项目相关信息的网站

    mvn clean ：清除目标目录中的生成结果

    mvn package ： 依据项目生成 jar 文件

    mvn install ：在本地 Repository 中安装 jar

    mvn deploy：将jar包发布到远程仓库

    mvn eclipse:eclipse ：生成 Eclipse 项目文件

    mvn dependency:resolve：打印项目依赖列表

    mvn dependency:tree ：以树形结构展现依赖列表


# Project Object Model(pom.xml)

    <project>
      <modelVersion>4.0.0</modelVersion>
    </project>

## 项目坐标

顶级元素，构成maven的唯一坐标

      <groupId>org.sonatype.mavenbook</groupId>
      <artifactId>my-project</artifactId>
      <version>1.0</version>
      <packaging>war</packaging>


## 属性 properties

顶级元素，在properties中定义，用${属性名称}引用

    <properties>
    	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    	<java.home>${env.JAVA_HOME}/jre</java.home>
    </properties>

### 隐式变量

Maven提供了三个隐式的变量，可以用来访问环境变量，POM信息，和Maven Settings：

#### env

env变量 暴露了你操作系统或者shell的环境变量。

#### project

project变量暴露了POM。你可以使用点标记（.）的路径来引用POM元素的值。
例如，在本节中我们使用过groupId和artifactId来设置构建配置中的finalName元素。这个属性引用的语法是：org.sonatype.mavenbook-${project.artifactId}。

#### settings

settings变量暴露了Maven settings信息。可以使用点标记（.）的路径来引用settings.xml文件中元素的值。例如，${settings.offline}会引用~/.m2/settings.xml文件中offline元素的值。

除了这三个隐式的变量，你还可以引用系统属性，以及任何在Maven POM中和构建profile中自定义的属性组。

除了这三个隐式的变量，你还可以引用系统属性，以及任何在Maven POM中和构建profile中自定义的属性组。


## dependencyManagement

顶级元素，管理引用的版本。
   
    <dependencyManagement>
    	<dependencies>
    
    	</dependencies>
    </dependencyManagement>

## dependencys

顶级元素，管理引用。

    <dependencies>
        <dependency>
    	    <groupId>org.springframework</groupId>
    	    <artifactId>spring-core</artifactId>
    	    <version>2.5.6</version>
        </dependency>
    </dependencies>

## Element : build

    Information required to build the project.
    
    Content Model : all(sourceDirectory?, scriptSourceDirectory?, testSourceDirectory?, 
     outputDirectory?, testOutputDirectory?, extensions?, defaultGoal?, resources?, 
     testResources?, directory?, finalName?, filters?, pluginManagement?, plugins?)

# setting.xml

    <settings>
        <localRepository/>
        <interactiveMode/>
        <usePluginRegistry/>
        <offline/>
        <pluginGroups/>
        <servers/>
        <mirrors/>
        <proxies/>
        <profiles/>
        <activeProfiles/>
    </settings>

## 简单值

localRepository  
该值表示构建系统本地仓库的路径。其默认值为.m2/repository。

interactiveMode  
如果Maven需要和用户交互以获得输入，则设置成true，反之则应为false。默认为true。

usePluginRegistry  
如果需要让Maven使用文件/.m2/plugin-registry.xml来管理插件版本，则设为true。默认为false。

offline  
如果构建系统需要在离线模式下运行，则为true，默认为false。当由于网络设置原因或者安全因素，构建服务器不能连接远程仓库的时候，该配置就十分有用。

pluginGroups  
该元素包含一个pluginGroup元素列表，每个子元素包含了一个groupId。当我们使用某个插件，并且没有在命令行为其提供groupId的时候，Maven就会使用该列表。默认情况下该列表包含了org.apache.maven.plugins。

## servers

    <server>
        <id>server001</id>
        <username>my_login</username>
        <password>my_password</password>
        <privateKey>${usr.home}/.ssh/id_dsa</privateKey>
        <passphrase>some_passphrase</passphrase>
        <filePermissions>664</filePermissions>
        <directoryPermissions>775</directoryPermissions>
        <configuration></configuration>
    </server>
    </servers>

id  
这是server的id（注意不是用户登陆的id），该id与distributionManagement中repository元素的id相匹配。

username, password  
这对元素表示服务器认证所需要的登录名和密码。

privateKey, passphrase  
和前两个元素类似，这一对元素指定了一个私钥的路径（默认是/home/hudson/.ssh/id_dsa）以及如果需要的话，一个密语。将来passphrase和password元素可能会被提取到外部，但目前它们必须在settings.xml文件以纯文本的形式声明。

filePermissions, directoryPermissions  
如果在部署的时候会创建一个仓库文件或者目录，这时候就可以使用权限（permission）。这两个元素合法的值是一个三位数字，其对应了#nix文件系统的权限，如664，或者775。

## mirror

    <mirror>
        <id>planetmirror.com</id>
        <name>PlanetMirror Australia</name>
        <url>http://downloads.planetmirror.com/pub/maven2</url>
        <mirrorOf>central</mirrorOf>
    </mirror>

id, name  
该镜像的唯一定义符。id用来区分不同的mirror元素。

url  
该镜像的URL。构建系统会优先考虑使用该URL，而非使用默认的服务器URL。

mirrorOf  
被镜像的服务器的id。例如，如果我们要设置了一个Maven中央仓库（http://repo1.maven.org/maven2）的镜像，就需要将该元素设置成central。这必须和中央仓库的id central完全一致。

## Profiles

settings.xml中的profile元素是pom.xml中profile元素的裁剪版本。它包含了activation, repositories, pluginRepositories 和 properties元素。这里的profile元素只包含这四个子元素是因为这里只关心构建系统这个整体（这正是settings.xml文件的角色定位），而非单独的项目对象模型设置。
如果一个settings中的profile被激活，它的值会覆盖任何其它定义在POM中或者profile.xml中的带有相同id的profile。

## Activation

Activation是profile的开启钥匙。如POM中的profile一样，profile的力量来自于它能够在某些特定的环境中自动使用某些特定的值；这些环境通过activation元素指定。

## Repositories

仓库是Maven用来填充构建系统本地仓库所使用的一组远程项目。而Maven是从本地仓库中使用其插件和依赖。不同的远程仓库可能含有不同的项目，而在某个激活的profile下，可能定义了一些仓库来搜索需要的发布版或快照版构件。

# 插件

## help插件

    $ mvn help:describe -Dplugin=help
    $ mvn help:describe -Dplugin=archetype

## archetype插件

建一个 JAVA 项目 ：

    mvn archetype:generate -DgroupId=com.test -DartifactId=App

建一个 web 项目 ：

     mvn archetype:generate  -DgroupId=com.test -DartifactId=web-app -DarchetypeArtifactId=maven-archetype-webapp -Dversion=1.0

## eclipse插件

Eclipse导入已存在的maven项目 ：

在导入之前需要在项目根目录下面执行如下命令：

普通 Eclipse 项目执行 ： 

    mvn eclipse:eclipse Eclipse

web 项目执行 ： 

    mvn eclipse:eclipse –Dwtpversion=1.0

然后通过Eclipse的maven插件中选择导入已存在的maven项目即可将其导入。

在用eclipse导入web项目时 如果eclipse安装了wtp插件在会自动将其转化为eclipse认得到的web项目，在项目上面点击右键run as 的run on server可以用，但是当eclipse没有安装wtp插件时run on server则不用，此时的解决方式为用命令行切换到项目的根目录然后执行 mvn eclipse:eclipse –Dwtpversion=1.0即可解决。
