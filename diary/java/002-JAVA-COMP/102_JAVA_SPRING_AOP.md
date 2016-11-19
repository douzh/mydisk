# 专用词

切面（Aspect）

切面（Aspect）其实就是共有功能的实现。如日志切面、权限切面、事务切面等。在实际应用中通常是一个存放共有功能实现的普通Java类，之所以能被AOP容器识别成切面，是在配置中指定的。

通知（Advice）

通知（Advice）是切面的具体实现。以目标方法为参照点，根据放置的地方不同，可分为前置通知（Before）、后置通知（AfterReturning）、异常通知（AfterThrowing）、最终通知（After）与环绕通知（Around）5种。在实际应用中通常是切面类中的一个方法，具体属于哪类通知，同样是在配置中指定的。

连接点（Joinpoint）

连接点（Joinpoint）就是程序在运行过程中能够插入切面的地点。例如，方法调用、异常抛出或字段修改等，但Spring只支持方法级的连接点。

切入点（Pointcut）

切入点（Pointcut）用于定义哪些通知应该切入到哪些连接点上。不同的通知通常需要切入到不同的连接点上，这种精准的匹配是由切入点的正则表达式来定义的。

目标对象（Target）

目标对象（Target）就是那些即将切入切面的对象，也就是那些被通知的对象。这些对象中已经只剩下干干净净的核心业务逻辑代码了，所有的共有功能代码等待AOP容器的切入。

代理对象（Proxy）

代理对象（Proxy）将通知应用到目标对象之后被动态创建的对象。可以简单地理解为，代理对象的功能等于目标对象的核心业务逻辑功能加上共有功能。代理对象对于使用者而言是透明的，是程序运行过程中的产物。

植入（Weaving）

植入（Weaving）将切面应用到目标对象从而创建一个新的代理对象的过程。这个过程可以发生在编译期、类装载期及运行期，当然不同的发生点有着不同的前提条件。譬如发生在编译期的话，就要求有一个支持这种AOP实现的特殊编译器；发生在类装载期，就要求有一个支持AOP实现的特殊类装载器；只有发生在运行期，则可直接通过Java语言的反射机制与动态代理机制来动态实现。

# AOP注解

    <!--启用AspectJ对Annotation的支持-->
    <aop:aspectj-autoproxy/>

## 声明一个切面

切面是个bean，切面类要有@Aspect注释

    <bean id="securityHandler" class="com.iteedu.spring.SecurityHandler"/>  
    
    @Aspect
    public class SecurityHandler {
    
        @Pointcut("execution(* add*(..)) || execution(* del*(..))")
        private void allAddMethod(){};
    
        @Before("allAddMethod()")
        private void checkSecurity() {
    	    System.out.println("----checkSecurity()----------");
        }
    }

## 声明一个切入点

allAddMethod()方法就是一个切入点，名称就是allAddMethod。此方法不能有返回值和参数，该方法只是一个标识。

切入点定义了作用于哪些连接点，即做用于哪些方法。

方法标识只是为了让程序能获取@Pointcut注释的内容。

## 声明通知

在通知方法上添加通知

    @Before("allAddMethod()")

如果使用一个in-place 的切入点表达式，我们可以把上面的例子换个写法：

    @Before（"execution(* add*(..)) || execution(* del*(..))"）

# 配制

    <!--配制切入点和关联通知-->
    <aop:config>
        <aop:aspect id="security" ref="securityHandler">
    	<aop:pointcut id="allAddMethod" expression="execution(*add*(..))"/>
    	<aop:before method="checkSecurity" pointcut-ref="allAddMethod"/>
        </aop:aspect>
    </aop:config>

# 通知类型

可以在相同的切面里定义多个通知，或者其他成员。 



正常返回通知[After returning advice]：在连接点正常执行完成后执行，如果连接点抛出异常，则不会执行。 

异常返回通知[After throwing advice]：在连接点抛出异常后执行。
 
返回通知[After (finally) advice]：在连接点执行完成后执行，不管是正常执行完成，还是抛出异常，都会执行返回通知中的内容。 

环绕通知[Around advice]：环绕通知围绕在连接点前后，比如一个方法调用的前后。这是最强大的通知类型，能在方法调用前后自定义一些操作。环绕通知还需要负责决定是继续处理join point(调用ProceedingJoinPoint的proceed方法)还是中断执行。

五种通知的执行顺序为： 前置通知→环绕通知→正常返回通知/异常返回通知→返回通知

## 前置通知

前置通知[Before advice]：在连接点前面执行，前置通知不会影响连接点的执行，除非此处抛出异常。 

    @Aspect
    public class BeforeExample {
    
      @Before（"com.xyz.myapp.SystemArchitecture.dataAccessOperation（）"）
      public void doAccessCheck（） {
        // ...
      }
    
    }

## 后置通知

      @AfterReturning（
        pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation（）",
        returning="retVal"）
      public void doAccessCheck（Object retVal） {
        // ...
      }

## 异常通知

      @AfterThrowing（
        pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation（）",
        throwing="ex"）
      public void doRecoveryActions（DataAccessException ex） {
        // ...
      }

## 最终通知

      @After（"com.xyz.myapp.SystemArchitecture.dataAccessOperation（）"）
      public void doReleaseLock（） {
        // ...
      }

最终通知必须准备处理正常返回和异常返回两种情况。通常用它来释放资源。

## 环绕通知

可以用来计算方法执行时间。

      @Around（"com.xyz.myapp.SystemArchitecture.businessService（）"）
      public Object doBasicProfiling（ProceedingJoinPoint pjp） throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed（）;
        // stop stopwatch
        return retVal;
      }

通知的第一个参数必须是 ProceedingJoinPoint类型。在通知体内，调用 ProceedingJoinPoint的proceed()方法会导致 后台的连接点方法执行。proceed 方法也可能会被调用并且传入一个 Object[]对象-该数组中的值将被作为方法执行时的参数。

## 获取参数

任何通知方法可以将第一个参数定义为 org.aspectj.lang.JoinPoint 类型，环绕通知需要定义为 ProceedingJoinPoint 类型的， 它是 JoinPoint 的一个子类。

JoinPoint 接口提供了一系列有用的方法

- getArgs()（返回方法参数）
- getThis()（返回代理对象）
- getTarget()（返回目标）
- getSignature()（返回正在被通知的方法相关信息）
- toString()（打印出正在被通知的方法的有用信息）。

# 事务

    <!-- 支持注解的声明式事务 -->
    <tx:annotation-driven transaction-manager="transactionManager" />

事务语义封装在<tx:advice/>定义中，用<aop:advisor>标签在</aop:config>中施加到指定切入点(<aop:pointcut>)。

<aop:advisor>标签是</aop:config>的直接子标签，所以<aop:pointcut>也要定义在</aop:config>中。

    <!-- 配置事务的传播特性 -->
    <tx:advice id="txAdvice" transaction-manager=" txManager">
    	<tx:attributes>
    		<tx:method name="add*" propagation="REQUIRED" />
    		<tx:method name="del*" propagation="REQUIRED" />
    		<tx:method name="modify*" propagation="REQUIRED" />
    		<tx:method name="*" read-only="true" />
    	</tx:attributes>
    </tx:advice>

    <!-- 那些类的哪些方法参与事务 -->
    <aop:config>
    	<aop:pointcut id="allManagerMethod"
    		expression="execution(* com.iteedu.manager.*.*(..))" />
    	<aop:advisor pointcut-ref="allManagerMethod" advice-ref="txAdvice" />
    </aop:config>

如果 PlatformTransactionManager bean的名字是 ‘transactionManager’ 的话，事务通知（<tx:advice/>）中的 ‘transaction-manager’ 属性可以忽略。否则你则需要像上例那样明确指定。

## transaction-manager

每种框架都有自己的transaction-manager，创建方法也各不相同。

    <bean id=" txManager "
    	class="org.springframework.orm.hibernate3.HibernateTransactionManager">
    	<property name="sessionFactory">
    		<ref bean="sessionFactory" />
    	</property>
    </bean>
    
    <bean id="txManager"
    	class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    	<property name="dataSource" ref="dataSource" />
    </bean>
