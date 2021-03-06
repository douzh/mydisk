## 集成Spring

### SqlMapClientFactoryBean 

用来

### SqlMapClientDaoSupport 

### 配制

    <bean id="sqlMapClient" class="org.springframework.orm.ibatis.SqlMapClientFactoryBean">
      <property name="configLocation">
         <value>sqlMapConfig.xml</value>
      </property>
    </bean>
    
    <bean id="DaoImp" class="com.tmall.dao.DaoImp">
       <property name="dataSource">
       <ref bean="dataSource"/>
     </property>
      <property name="sqlMapClient">
        <ref bean="sqlMapClient"/>
      </property>
    </bean>

## SqlMapConfig

### <sqlMapConfig>

    <sqlMapConfig>
        <properties resource="jdbc.properties" />
        <transactionManager type="JDBC">
        ...
        </transactionManager>
        <sqlMap resource="ibatis/resources/User.xml" />
    </sqlMapConfig>

### <properties>

引用属性文件，引用后用EL表达式取属性文件中的值

    <properties resource="jdbc.properties" />

### <transactionManager>

JDBC 普通的JDBC事务管理器
JTA 基于容器的事务管理器，企业级的容器中使用
EXTERNAL外部的事务管理器，管理事务的是应用程序，而不是iBATIS

    <transactionManager type="JDBC">
        <dataSource type="SIMPLE">
        </dataSource>
    </transactionManager>

### <dataSource>

- SIMPLE 简单的数据源工厂，配制简单数据连接池的数据源
- DBCP Apache的DBCP连接池
- JNDI JNDI目录服务器，基于容器级的开发

    <dataSource type="SIMPLE">
    	<property name="JDBC.Driver" value="${jdbc.driverClassName}" />
    	<property name="JDBC.ConnectionURL" value="${jdbc.url}" />
    	<property name="JDBC.Username" value="${jdbc.username}" />
    	<property name="JDBC.Password" value="${jdbc.password}" />
    </dataSource>

### <setings>

- maxRequests 最大请求数，不能小于maxSession和maxTransaction，512
- maxSession 最大的会话数，128
- maxTransactions最大事务，16
- cacheModelsEnabled 启用缓存，true
- lazyLoadingEnable 懒加载,true
- enhancementEnable 增强模式，false
- useStatementNamesapces 使用命名空间，false
- defaultStatementTimeout 设置访问超时

### <sqlMap>

- resource 用于指定本地的映射文件
- url 指定远程分布式系统中的映射文件

## SqlMap

### <typeAlias>

    <typeAlias alias="Account" type="com.mydomain.domain.Account"/>

用别名取代包含包名的类命名。

默认字义了一些常用数据类型的别名，所以使用String、Map等是不需要全名就可以了。

还定义了几个事务和数据源的一些别名,如<dataSource>和<transactionManager>的type。

### <resultMap>

    <!-- SQL字段与类属性映射关系，省略在SQL语句中用as转换字段别名 -->
    <resultMap id="AccountResult" class="Account">
      <result property="id" column="ACC_ID"/>
      <result property="firstName" column="ACC_FIRST_NAME"/>
      <result property="lastName" column="ACC_LAST_NAME"/>
      <result property="emailAddress" column="ACC_EMAIL"/>
    </resultMap>

### <parametMap>

    <!-- SQL字段类型与类属性映射关系，在SQL语句中用?占位符就可以了 -->
    <parametertMap id="AccountMap" class="Account">
      <parameter property="id" jdbcType="INT"/>
      <parameter property="firstName" jdbcType="VARCHAR"/>
      <parameter property="lastName" jdbcType="VARCHAR"/>
      <parameter property="emailAddress" jdbcType="VARCHAR"/>
    </parameterMap>

### <selectKey>

- Oracle： select seq.nextVal as id from dual
- MS-SQL： select @@IDENTITY as id
- MySQL： select LAST_INSERT_ID()或select @@IDENTITY

• Oracle系列值

    <insert id="insertAccount" parameterClass="Account">
      <selectKey resultClass="int" keyProperty="id">
        select sequence.nextVal from dual
      </selectKey>
      insert into ACCOUNT (
        ACC_ID,
        ACC_FIRST_NAME,
        ACC_LAST_NAME,
        ACC_EMAIL)
      values (#id#, #firstName#, #lastName#, #emailAddress#)
    </insert>

• MySQL自动增长值

    <insert id="insertAccount" parameterClass="Account">
      insert into ACCOUNT (ACC_FIRST_NAME,ACC_LAST_NAME,ACC_EMAIL)
      values (#firstName#, #lastName#, #emailAddress#)
      <selectKey>
        select @@IDENTITY as id
      </selectKey>
    </insert>

### <select>

- id 指定名称
- parameterClass 参数类型，类的别名或全名
- parameterMap 传入参数的显示映射
- resultClass 返回类型，类的别名或全名
- resultMap 返回类型的显示映射
- cacheModel 缓存模式
- resultSetType 结果集类型，如游标的类型只能往前
- fetchSize 预取回指定大小的数据
- xmlResultName 当resulClass值为xml时在此指定XML文件的根标签
- remapResults 如果返回的字段不确定，将此属性设为true

如：select # from  $table$ 或 select $fields$ from table

- timeout 超时

    <!-- 根据id获得用户对象 -->
    <select id="getUsersById" resultMap="ibatisTest">
        select # from Ibatis where id=#value#
    </select> 

### <insert> <update> <delete>

- id 指定名称
- parameterClass 参数类型，类的别名或全名
- parameterMap 传入参数的显示映射

    <!-- 新增用户对象 -->
    <insert id="insertUsers" parameterClass="user">
        insert into ibatis (id,name) values (#id#,#name#)
    </insert>
    
    <!-- 更新用户对象 -->
    <delete id="updateUsers" parameterClass="user">
        update ibatis set name=#name# where id=#id#
    </delete>

### <procedure>

调用存储过程

- id 指定名称
- parameterClass 参数类型，类的别名或全名
- parameterMap 传入参数的显示映射
- resultClass 返回类型，类的别名或全名
- resultMap 返回类型的显示映射

    <!-- 参数映射 -->
    <parameterMap id="procedureMap" class="map">
      <parameter property="ACC_FIRST_NAME" jdbcType="VARCHAR" 
        javaType="java.lang.String" mode="IN"/>
      <parameter property="ACC_LAST_NAME" jdbcType="VARCHAR" 
        javaType="java.lang.String" mode="OUT"/>
      <parameter property="ACC_EMAIL" jdbcType="VARCHAR" 
        javaType="java.lang.String" mode="INOUT"/>
    </parameterMap>
    <!-- 调用存储过程 -->
    <procedure id="callProcedure" parameterMap="procedureMap" 
      resultClass="AccountResult">
      {call procedureName(?,?,?)}
    </procedure>

### <statement>

执行 DDL语句

- id 指定名称
- parameterClass 参数类型，类的别名或全名
- parameterMap 传入参数的显示映射

    <statement id="dropTable" parameterClass="String">
      drop table #tableName#
    </statement>

### <sql> <include>

    <sql id="sqlStatement">
      select ACC_ID, ACC_FIRST_NAME, ACC_LAST_NAME, ACC_EMAIL
    </sql>
    <select id="sqlAndInclude">
      <include refid="sqlStatement"/> from ACCOUNT
    </select>

## SQL参数

### #

相当于JDBC中上的？号，以对象的方式传递参数，可以防止SQL注入攻击

    <select id="select" resultMap="AccountResult" parameterClass="String">
      select # from ACCOUNT
      where ACC_ID    ＝ ＃id＃
    </select>

### $

以拼接字符串的方式传递参数，经典用场是like语句，如：

    <select id="select" resultMap="AccountResult" parameterClass="String">
      select # from ACCOUNT
      where ACC_FIRST_NAME like '%$name$%'
    </select>

使用$符号做占位符时应特别注意防止SQL注入攻击。

### 自动参数映射

    <insert id="insertAccount" parameterClass="com.mydomain.domain.Account">
      insert into ACCOUNT (ACC_ID, ACC_FIRST_NAME, ACC_LAST_NAME, ACC_EMAIL)
      values (#id#, #firstName#, #lastName#, #emailAddress#)
    </insert>

占位符中填写类的属性名或Map的key值，iBATIS就可以自动获取参数值

### 内联参数映射

JavaBean中的数据类型与数据库中类型的映射，如：

    <insert id="insertAccount" parameterClass="com.mydomain.domain.Account">
      insert into ACCOUNT (ACC_ID, ACC_FIRST_NAME, ACC_LAST_NAME, ACC_EMAIL)
      values (#id:INT#, #firstName:VARCHAR#, #lastName:VARCHAR#, 
    #emailAddress:VARCHAR#)
    </insert>

### 外联参数映射

用定义好的映射关系指定参数类型，如：

    <insert id="insertAccount" parameterMap="AccountMap">
      insert into ACCOUNT (ACC_ID, ACC_FIRST_NAME, ACC_LAST_NAME, ACC_EMAIL)
      values (?, ?, ?, ?)
    </insert>

## 动态SQL

共同属性

- prepend 前缀
- open 开始
- close 结束

### <dynamic>标签

    <select id="selectAccount" resultMap="AccountResult" parameterClass="Account">
      select # from ACCOUNT
      <dynamic prepend="where">
        <isNotNull property="id"  prepend="and" open="(" close=")">
          id = #id#
        </isNotNull>
        <isNotEmpty property="name" prepend="and">
          name like '%$name$%'
        </isNotEmpty>
      </dynamic>
    </select>

当id和name不为空是生成的SQL语句是：

    select # from ACCOUNT where (id=id的值) and name like '%name的值%'

### 一元标签

- <isPropertyAvailable>  
检查是否存在该属性（存在parameter bean的属性）
- <isNotPropertyAvailable>  
检查是否不存在该属性（不存在parameter bean的属性）
- <isNull>  
检查属性是否为null 
- <isNotNull>  
检查属性是否不为null 
- <isEmpty>  
检查Collection.size()的值，属性的String或String.valueOf()值,是否为null或
空（“”或size() < 1）
- <isNotEmpty>  
检查Collection.size()的值，属性的String或String.valueOf()值,是否不为null或不为空（“”或size() > 0）

### 二元标签

- <isEqual property=“age” compareValue=“20”>  
比较属性值和静态值或另一个属性值是否相等
- <isNotEqual>  
比较属性值和静态值或另一个属性值是否不相等
- <isGreaterThan>  
比较属性值是否大于静态值或另一个属性值
- <isGreaterEqual>  
比较属性值是否大于等于静态值或另一个属性值
- <isLessThan>  
比较属性值是否小于静态值或另一个属性值
- <isLessEqual>  
比较属性值是否小于等于静态值或另一个属性值

### <iterate>标签

假如有参数：

    Map<String,String[]> map = new HashMap<String,String[]>();
    map.put("keyWords", new String[]{"1", "3", "4" });

动态语句： 

    <select id="selectAccount" resultMap="AccountResult" 
    parameterClass="Map">
      select # from ACCOUNT
      <dynamic>
        <isNotNull prepend="keyWords">
        <iterate prepend="where ACC_ID in" property="keyWords" open="(" 
    conjunction="," close=")" removeFirstPrepend="false">
    #keyWords[]#
        </iterate>
        </isNotNull>
      </dynamic>
    </select>

生成的SQL语句为：

    select # from ACCOUNT where ACC_ID in (1, 3, 4)

### 其它

- <isParameterPresent>  
检查是否存在参数对象（不为null）
- <isNotParameterPresent>  
检查是否不存在参数对象（参数对象为null）
