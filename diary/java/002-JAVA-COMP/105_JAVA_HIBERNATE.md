# 集成Spring

## HibernateDaoSupport

最终是给HibernateDaoSupport注入sessionFactory，使用HibernateTemplate做数据操作。

    @Autowired
    public void setSessionFactory0(
    	@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }


## sessionFactory

    <bean id="sessionFactory"
    	class="org.springframework.orm.hibernate3.LocalSessionFactoryBean">
    ....
    </bean>

### configLocation

    <property name="configLocation">
    	<value>classpath:hibernate.cfg.xml</value>
    </property>
    
    public void setConfigLocation(Resource configLocation) {
    	this.configLocations = new Resource[] {configLocation};
    }

直接导入原来的配制。

### hibernateProperties

    <property name="hibernateProperties">
        <props>
    	<prop key="hibernate.dialect">${hibernate.dialect}</prop>
    	<prop key="hibernate.connection.autocommit">${hibernate.connection.autocommit}</prop>
    	<prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
        </props>
    </property>


    public void setHibernateProperties(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

### 映射文件导入

添加映射文件有多种方式，可以混合使用

映射目录：

    <property name="mappingDirectoryLocations">
        <list>
    	<value>classpath:/hibernate/</value>
        </list>
    </property>

    public void setMappingDirectoryLocations(Resource[] mappingDirectoryLocations) {
    	this.mappingDirectoryLocations = mappingDirectoryLocations;
    }

映射一批文件：

    <property name="mappingLocations">
        <list>
    	<value>classpath#:hibernate/right/#.hbm.xml</value>
    	<value>classpath#:hibernate/message/#.hbm.xml</value>
        </list>
    </property>

    public void setMappingLocations(Resource[] mappingLocations) {
    	this.mappingLocations = mappingLocations;
    }

映身单个文件：

    <property name="mappingResources">
        <list>
    	<value>com/iteedu/hibernate/User.hbm.xml</value>
    	<value>com/iteedu/hibernate/Role.hbm.xml</value>
        </list>
    </property>
    
    public void setMappingResources(String[] mappingResources) {
    	this.mappingResources = mappingResources;
    }

## HibernateTemplate

    get()
    load()
    delete()
    save...()
    update()
    find...()
    execute...(HibernateCallback action)
    bulkUpdate()

## HibernateCallback

    public interface HibernateCallback {
        Object doInHibernate(Session session);
    }

## 托管session

防止了忘记关闭session导致的内存泄露

    getHibernateTemplate().executeFind(new HibernateCallback() {
        public Object doInHibernate(Session session)
    	throws HibernateException, SQLException {
    
        }
    });

# 分页

    Query query = session.createQuery("select art from Article art where art.username = ?");
    //设置参数
    query.setParameter(0, username);
    //设置每页显示多少个，设置多大结果。
    query.setMaxResults(page.getEveryPage());
    //设置起点
    query.setFirstResult(page.getBeginIndex());
    return query.list();


# load和get的区别

区别：

get在调用时会生成对象实体，加载数据库中的数据。没有对应数据时返回null。

load调用时只会生成一个代理对象，只有在使用对象时才会从数据库中加载数据。如果没有对应id的数据就会抛出异常。

load方法实现了懒加载(lazy-load)。在加载数据量大，耗时长时用load可以加快速度，毕竟只是创建代理对象没有从数据库加载数据。
如果加载时还要读对象属性，load就不行了，还不如直接创建对象加载数据呢。

# 生命周期

## 瞬时态（transient）的特征

- 在数据库中没有与之匹配的数据
- 没有纳入session的管理

两种来源：

- 新建的POJO(简单JAVA对象)。
- 持久态bean删除操作后，数据库中没有对应数据，不在session管理范围，但bean还是存在的。

## 持久态（persistent）的特征

- persistent状态的对象在数据库中有与之匹配的数据
- 纳入了session的管理
- 在清理缓存（脏数据检查）的时候,会和数据库同步。即在commit()前所有对bean的修改都会在commit()时用update语句更新到数据库。

两种来源：

- 瞬时态的bean通过session保存到数据库。
- 通过session直接获取的实体bean。

## 游离态(detached)的特征

- 在数据库中有与之匹配的数据
- 没有纳入session的管理

来源：

- 清除缓存或关闭session会造成持久态bean没有sessin管理，变成游离态。
- Session对游离态的bean进行更新操作可以使之再纳入session管理范围，成为持久bean。

# 映射文件

## 基本结构

    <hibernate-mapping>
    	<class>
    		<id>
    			<generator></generator>
    		</id>
    		<property></property>
    		<property></property>
            ……
    	</class>
        ……
    </hibernate-mapping>

## class

lass元素来定义一个持久化类。

    <class name="com.iteedu.hibernate.User" table="table_user">

<id>标签必须是第一位的，是数据库中的主键。

## id

<id>必须要指定一个生成器用来生成主键值。如果这个生成器实例需要某些配置值或者初始化参数， 用<param>元素来传递。

    <generator class="org.hibernate.id.TableHiLoGenerator">
          <param name="table">uid_table</param>
          <param name="column">next_hi_value_column</param>
    </generator>

increment

用于为long, short或者int类型生成 唯一标识。只有在没有其他进程往同一张表中插入数据时才能使用。 在集群下不要使用。

identity

对DB2,MySQL, MS SQL Server, Sybase和HypersonicSQL的内置标识字段提供支持。 返回的标识符是long, short 或者int类型的。

sequence

在DB2,PostgreSQL, Oracle, SAP DB, McKoi中使用序列（sequence)， 而在Interbase中使用生成器(generator)。返回的标识符是long, short或者 int类型的。

hilo

使用一个高/低位算法高效的生成long, short 或者 int类型的标识符。给定一个表和字段（默认分别是 hibernate_unique_key 和next_hi）作为高位值的来源。 高/低位算法生成的标识符只在一个特定的数据库中是唯一的。

seqhilo

使用一个高/低位算法来高效的生成long, short 或者 int类型的标识符，给定一个数据库序列（sequence)的名字。

uuid

用一个128-bit的UUID算法生成字符串类型的标识符， 这在一个网络中是唯一的（使用了IP地址）。UUID被编码为一个32位16进制数字的字符串。

guid

在MS SQL Server 和 MySQL 中使用数据库生成的GUID字符串。

native

根据底层数据库的能力选择identity, sequence 或者hilo中的一个。

assigned

让应用程序在save()之前为对象分配一个标示符。这是 <generator>元素没有指定时的默认生成策略。
