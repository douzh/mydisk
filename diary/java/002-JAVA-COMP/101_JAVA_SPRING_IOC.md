*  集成

** 导入依赖库


** WEB启动方式

*** ContextLoaderServlet

    <servlet>
        <servlet-name>context</servlet-name>
        <servlet-class>org.springframework.web.context.ContextLoaderServlet</servlet-class>
        <load-on-startup>2</load-on-startup>
    </servlet>

*** ContextLoaderListener

ContextLoaderListener的作用就是启动Web容器时，自动装配ApplicationContext的配置信息。因为它实现了ServletContextListener这个接口，在web.xml配置这个监听器，启动容器时，就会默认执行它实现的方法。

    public class ContextLoaderListener implements ServletContextListener

     <listener>  
     <listener-class>org.springframework.web.context.ContextLoaderListener </listener-class>
     </listener>

如果在web.xml中不写任何参数配置信息，默认的路径是"/WEB-INF/applicationContext.xml，在WEB-INF目录下创建的xml文件的名称必须是applicationContext.xml。如果是要自定义文件名可以在web.xml里加入contextConfigLocation这个context参数：

    <context-param>  
        <param-name>contextConfigLocation</param-name>  
        <param-value>/WEB-INF/applicationContext-*.xml,classpath*:applicationContext-*.xml</param-value>  
    </context-param>

*** ContextLoader

ContextLoader创建的是 XmlWebApplicationContext这样一个类，它实现的接口是WebApplicationContext->ConfigurableWebApplicationContext->ApplicationContext->
BeanFactory这样一来spring中的所有bean都由这个类来创建

向ServletContext设置了一个WebApplicationContext，可以用于获取bean。

    servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);

*** WebApplicationContextUtils

    WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);

ContextLoader向ServletContext注入了WebApplicationContext，WebApplicationContextUtils只是从ServletContext取出来而已。

*** struts配制

**** ContextLoaderPlugIn

     <plug-in className="org.springframework.web.struts.ContextLoaderPlugIn">
        <set-property property="contextConfigLocation" value="/WEB-INF/applicationContext.xml,/WEB-INF/action-servlet.xml" />
      </plug-in>

    protected WebApplicationContext initWebApplicationContext()
    String attrName = getServletContextAttributeName();
    getServletContext().setAttribute(attrName, wac);

注意：ContextLoaderPlugIn没有用ContextLoader加载，并且注入的属性值key也不一样。

**** DelegatingActionProxy

根据action的name和bean的name调用action实例。

    <action
      attribute="loginForm"
      input="/login.jsp"
      name="loginForm"
      path="/login"
      scope="request"
      type="org.springframework.web.struts.DelegatingActionProxy">
      <forward name="failed" path="/fail.jsp" />
      <forward name="success" path="/success.jsp" />
    </action>

      <bean name="/login" 
        class="com.qdu.sun.struts.action.LoginAction">   
         <property name="loginBean">
            <ref bean="login"/>
         </property>
      </bean>

**** DelegatingRequestProcessor

和DelegatingActionProxy相比，省去了重复的type属性。

    <controller processorClass="org.springframework.web.struts.DelegatingRequestProcessor" />

action配置则无需配置type属性，即使配置了type属性也不起任何作用。
拦截到的请求转发到Spring context下的bean，根据bean的name属性来匹配。
Action bean不再需要id属性，而要用name替代id属性，这时name属性的值应和Action的path属性的值相同


** 启动方式

*** BeanFactory

    Object getBean(String name)
    boolean containsBean(String name);


*** XMLBeanFactory

利用FileSystemResource，则配置文件必须放在project直接目录下，或者写明绝对路径

    Resource resource = new FileSystemResource("beans.xml");

    ClassPathResource resource2 = new ClassPathResource("beans.xml");
    
    BeanFactory factory2 = new XmlBeanFactory(resource2);

*** ClassPathXMLApplicationContext 

    // src目录下
    ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "applicationContext-part2.xml"});
    
    // src/conf 目录下的
    ApplicationContext ac=new ClassPathXMLApplicationContext("conf/appcontext.XML");
    
    //加file:前缀可以加载绝对路径
    ApplicationContext ac=new ClassPathXMLApplicationContext("file:G:/Test/src/appcontext.XML");

*** FileSystemXMLApplicationContext

    ApplicationContext ac=new FileSystemXMLApplicationContext("G:/Test/src/appcontext.XML");
    
    ApplicationContext ac = new FileSystemXmlApplicationContext (new String[] {"applicationContext.xml", " applicationContext_task.xml "});
    
    //用通配符加载一批配制文件
    String path=" spring/applicationContext*.xml";
    ApplicationContext context = new FileSystemXmlApplicationContext(path);
    
    ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:地址");


*** 文件路径总结

“classpath:”在classpath的配置中搜索，不管用什么对象加载。

“file:” 在文件系统中搜索，不管用什么对象加载。

“classpath*:” 所有与给定名称匹配的classpath资源都应该被获取。


* 注解

<context:annotation-config /> 

他的作用是式地向 Spring 容器注册

    AutowiredAnnotationBeanPostProcessor
    CommonAnnotationBeanPostProcessor
    PersistenceAnnotationBeanPostProcessor 
    RequiredAnnotationBeanPostProcessor

注册这4个 BeanPostProcessor的作用，就是为了你的系统能够识别相应的注解。

    <context:component-scan base-package=”com.iteedu.spring”> 

该配置项其实也包含了自动注入上述processor的功能，因此当使用
<context:component-scan/> 后，就可以将 <context:annotation-config/> 移除了。


*** @Autowired @Qualifier

    <!-- 该 BeanPostProcessor 将自动对标注 @Autowired 的 Bean 进行注入 -->
    <bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor"/>

@Autowired是通过byType装配bean的，候选 Bean 数目必须有且仅有一个。
如果想改成byName，可以添加@Qualifier("XXX") 

@Autowired标注对象：成员变量、方法、构造函数
@Qualifier标注对象：成员变量、方法入参、构造函数入参。

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    
    @Autowired
    public void setSessionFactory0(
    	@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

*** @Component、@Repository、@Service、@Controller

@Service用于标注业务层组件

@Controller用于标注控制层组件（如struts中的action）

@Repository用于标注数据访问组件，即DAO组件

@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。

* bean

** 命名

*** id:

一个bean的唯一标识  ， 命名格式必须符合XML ID属性的命名规范 

*** name

可以用特殊字符，并且一个bean可以用多个名称：name=“bean1,bean2,bean3” ,用逗号或者分号或者空格隔开。

如果没有id，则name的第一个名称默认是id，其它的为别名。

id和name是可以同时存在的。

*** 同名bean

同一个spring配置文件中，bean的 id、name是不能够重复的，否则spring容器启动时会报错。 

如果一个spring容器从多个配置文件中加载配置信息，则多个配置文件中是允许有同名bean的，并且后面加载的配置文件的中的bean定义会覆盖前面加载的同名bean。 

*** 无名bean

如果 一个 <bean> 标签未指定 id、name 属性，则 spring容器会给其一个默认的id，值为其类全名。

如果同一个类有多个<bean> 标签未指定 id、name 属性，则spring容器会按照其出现的次序，分别给其指定 id 值为 "类全名#1", "类全名#2" 

** 实例化

*** 构造器


*** 静态工厂

静态方法在类里

    <bean id="exampleBean" class="examples.ExampleBean"
          factory-method="createInstance"/>


*** 实例工厂

有一专门的工厂BEAN

    <!—包含createInstance() 方法的工厂bean-->
    <bean id="myFactoryBean" class="...">  ...</bean>
      <!—用来调用工厂的bean -->
    <bean id="exampleBean"
          factory-bean="myFactoryBean"
          factory-method="createInstance"/>


*** depends-on

    depends-on="manager,accountDao"

*** lazy-init

    <bean id="lazy" class="..." lazy-init="true">
        <!-- various properties here... -->
    </bean>
    
    <beans default-lazy-init="true">
        ...
    </beans>

** 依赖注入

*** Setter注入

    <bean id="exampleBean" class="examples.ExampleBean">
      <property name="beanOne">
         <ref bean="anotherExampleBean"/>
      </property>
      <property name="beanTwo" ref="yetAnotherBean"/>
      <property name="integerProperty" value="1"/>
    </bean>

*** 构造器注入

bean可以用静态工厂或实例工厂实例化

    <bean id="exampleBean" class="examples.ExampleBean">
      <constructor-arg>
           <ref bean="anotherExampleBean"/>
      </constructor-arg>
      <constructor-arg ref="yetAnotherBean"/>
      <!—对于JAVA基础类型要指定type -->
      <constructor-arg type="int" value="1"/>
    </bean>

通过使用index属性可以显式的指定构造器参数出现顺序。使用index属性除了可以解决多个简单类型构造参数造成的模棱两可的问题之外，还可以用来解决两个构造参数类型相同造成的麻烦。注意：index属性值从0开始。

    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" ref="yetAnotherBean"/>

*** idref

将bean的id字符串传给传给<constructor-arg/> 或 <property/>元素

    <bean id="theTargetBean" class="..."/>
    <bean id="theClientBean" class="...">
        <property name="targetName">
            <idref bean="theTargetBean" />
        </property>
    </bean>

上述bean定义片段完全地等同于（在运行时）以下的片段

    <bean id="theTargetBean" class="..." />
    <bean id="client" class="...">
        <property name="targetName" value="theTargetBean" />
    </bean> 

用idref不用value是因为，idref会验证bean是否存在。

*** ref

ref有三个属性：local、parent、bean

local只能指定与当前配置的对象在同一个配置文件的对象定义的名称；
parent则只能指定位于当前容器的父容器中定义的对象引用；
bean则基本上通吃，所以，通吃情况下，直接使用bean来指定对象引用就可以了。

*** 内部bean

    <bean id="outer" class="...">
      <property name="target">
        <bean class="com.mycompany.Person"> <!-- this is the inner bean -->
          <property name="name" value="Fiona Apple"/>
          <property name="age" value="25"/>
        </bean>
      </property>
    </bean>

不需要有id或name属性，总是prototype，singleton被忽略

*** 集合

**** java.util.Properties

      <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@somecompany.org</prop>
            <prop key="support">support@somecompany.org</prop>
            <prop key="development">development@somecompany.org</prop>
        </props>
      </property>

**** java.util.List

      <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
      </property>

**** java.util.Map

      <property name="someMap">
        <map>
            <entry>
                <key>
                    <value>yup an entry</value>
                </key>
                <value>just some string</value>
            </entry>
    	<entry key="myKey" value="hello"/>
            <entry key-ref="myKeyBean" value-ref="myValueBean"/>
        </map>
      </property>

**** java.util.Set

      <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
      </property>


*** autowire

autowire="no/byName/byType/constructor/autodetect"

    <beans default-autowire="byName"           >
    	<bean id="bean2" class="..." autowire-candidate="false"/>
    </beans>

全局是指一个配制文件中的所有bean默认都自动装配，不想自动装配的可以<bean/>元素的 autowire-candidate属性可被设为false

    <bean id="bean2" class="..." autowire="byName"/>

** scope

    scope="singleton/prototype/request/session/globalSession"

** abstract和parent

    <bean id="beanAbstract"  abstract="true">
    	<property name="id" value="1000"/>
    	<property name="name" value="Jack"/>
    </bean>         
    
    <bean id="bean3" class="com.bjsxt.spring.Bean3" parent="beanAbstract">
    	<property name="name" value="Tom"/>
    	<property name="password" value="123"/>
    </bean>        
    
    <bean id="bean4" class="com.bjsxt.spring.Bean4" parent="beanAbstract"/>
