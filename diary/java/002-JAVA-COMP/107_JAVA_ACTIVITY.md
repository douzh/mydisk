http://blog.csdn.net/u012613903/article/category/2820693

# 工作流概念

## candidate 候选人

## claim 要求，声明

# 框架

## 数据库表命名

Activiti 只存储流程实例执行期间的运行时数据，当流程实例结束时，将删除这些记录。这就使这些运行时的表保持的小且快。

Activiti数据库中表的命名都是以ACT_开头的。第二部分是一个两个字符用例表的标识。此用例大体与服务API 是匹配的。 
 
ACT_RE_*：repository。带此前缀的表包含的是静态信息，如，流程定义、流程的资源（图片、规则，等）。

ACT_RU_*：runtime。就是这个运行时的表存储着流程变量、用户任务、变量、作业，等中的运行时的数据。

ACT_ID_*：identity。这些表包含着标识的信息，如用户、用户组、等等。 

ACT_HI_*：history。就是这些表包含着历史的相关数据，如结束的流程实例、变量、任务、等等。 

ACT_GE_*：普通数据，各种情况都使用的数据。

## ProcessEngine

### ProcessEngineConfiguration

#### 通过activiti.cfg.xml配置

ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine() 
会查找类路径下的activiti.cfg.xml 文件，然后根据文件中的配置构建引擎。

activiti.cfg.xml文件内必须包含一个 id 为’ processEngineConfiguration’的 bean。 

接下来使用这个bean构造ProcessEngine。存在多个用于定义processEngineConfiguration的类。这些类代表着不同的环境，有相应的默认设置。

    <bean id="processEngineConfiguration" class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
     
        <property name="jdbcUrl" value="jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000" /> 
        <property name="jdbcDriver" value="org.h2.Driver" /> 
        <property name="jdbcUsername" value="sa" /> 
        <property name="jdbcPassword" value="" /> 
         
        <property name="databaseSchemaUpdate" value="true" /> 
         
        <property name="jobExecutorActivate" value="false" /> 
         
        <property name="mailServerHost" value="mail.my-corp.com" />   
        <property name="mailServerPort" value="5025" />      
    </bean> 

#### databaseSchemaUpdate

允许在流程引擎启动和关闭时设置处理数据库模式的策略。 
false（默认）：创建流程引擎时检查数据库模式的版本是否与函数要求的匹配，如果版本不匹配就会抛出异
常。 
true：构建流程引擎时，执行检查，如果有必要会更新数据库模式。如果数据库模式不存在，就创建一个。 
create-drop：创建流程引擎时创建数据库模式，关闭流程引擎时删除数据库模式。

#### 编程方式

    ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault(); 
    ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(String resource); 
    ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(String resource, String beanName); 
    ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(InputStream inputStream); 
    ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(InputStream inputStream, String beanName); 

不用配制文件

    ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration(); 
    ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration(); 
    
    ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration() 
       .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE) 
       .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000") 
       .setJobExecutorActivate(true) 
       .buildProcessEngine(); 

### 通过Spring构建

可以作为普通的 Spring bean 对 ProcessEngine 进行配置。集成的出发点是类 org.activiti.spring.ProcessEngineFactoryBean。

    <bean id="processEngineConfiguration" class="org.activiti.spring.SpringProcessEngineConfiguration"> 
         ... 
    </bean> 
        
    <bean id="processEngine" class="org.activiti.spring.ProcessEngineFactoryBean"> 
       <property name="processEngineConfiguration" ref="processEngineConfiguration" /> 
    </bean> 

一定要注意 processEngineConfiguration bean 现在使用的是 org.activiti.spring.SpringProcessEngineConfiguration 类。


## TaskService

### TaskQuery

## RuntimeService

## RepositoryService

## FormService

## HistoryService

## IdentityService

## ManagementService

# 工作流对象

## ProcessDefinition

bpmn编译发布后对应一个ProcessDefinition，每个bpmn修改后重新发布，会新增版本，不会删除原来的流程定义。

    List<ProcessDefinition> pdList = RepositoryService().createProcessDefinitionQuery().list();

### Activity Definition 流动定义

业务描述单位
业务步骤
分为任务活动（要人工干预完成）和自由活动（系统自动执行）

### Transition 转移

Incoming Transition 流入转移

Outgoing Transition 流出转移

## Deployment   部署对象

1、一次部署的多个文件的信息。对于不需要的流程可以删除和修改。

2、对应的表：

- act_re_deployment：部署对象表
- act_re_procdef：流程定义表
- act_ge_bytearray：资源文件表
- act_ge_property：主键生成策略表

## ProcessInstance  流程实例

特指流程从开始到结束的那个最大的执行分支，一个执行的流程中，流程实例只有1个。

注意

- 如果是单例流程，执行对象ID就是流程实例ID
- 如果一个流程有分支和聚合，那么执行对象ID和流程实例ID就不相同
- 一个流程中，流程实例只有1个，执行对象可以存在多个。

生成

runtimeService

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

删除

runtimeService

    void deleteProcessInstance(String processInstanceId, String deleteReason);

HistoryService

    void deleteHistoricProcessInstance(String processInstanceId);
    void deleteHistoricTaskInstance(String taskId);

### Activity Instance 活动实例

由活动定义产生的活动实例

### Task 任务

活动定义中的任务活动产生的活动实例。

## Execution   执行对象

 按流程定义的规则执行一次的过程.
 
 对应的表：
 
- act_ru_execution： 正在执行的信息
- act_hi_procinst：已经执行完的历史流程实例信息
- act_hi_actinst：存放历史所有完成的活动

    Execution exe=runtimeService.createExecutionQuery().singleResult();

## Task 任务

 执行到某任务环节时生成的任务信息。
 
 对应的表：
 
- act_ru_task：正在执行的任务信息
- act_hi_taskinst：已经执行完的历史任务信息
- 
## 流程变量

在流程执行或者任务执行的过程中，用于设置和获取变量，使用流程变量在流程传递的过程中传递业务参数。对应的表：

- act_ru_variable：正在执行的流程变量表
- act_hi_varinst：流程变量历史表

    RuntimeService runtimeService = processEngine.getRuntimeService();
    TaskService taskService = processEngine.getTaskService();
    // ========================================
    // 设置变量的方法
    // 通过Execution设置一个变量
    runtimeService.setVariable(executionId, name, value);
    // 通过Execution设置多个变量
    runtimeService.setVariables(executionId, variablesMap);
    // 通过Task设置一个变量
    taskService.setVariable(taskId, variableName, value);
    // 通过Task设置多个变量
    taskService.setVariables(taskId, variablesMap);
    // 在启动流程实例时，同时也设置一些流程变量
    runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    // 在完成任务时，同时也设置一些流程变量
    taskService.complete(taskId, variables);
    // ========================================
    // 获取变量的方法
    // 通过Execution获取一个变量
    runtimeService.getVariable(executionId, variableName);
    // 通过Execution获取所有变量的信息，存放到Map集合中
    runtimeService.getVariables(executionId);
    // 通过Execution获取指定流程变量名称的变量值的信息，存放到Map集合中
    runtimeService.getVariables(executionId, variableNames);
    
    // 通过Task获取一个变量
    taskService.getVariable(taskId, variableName);
    // 通过Task获取所有变量的信息，存放到Map集合中
    taskService.getVariables(taskId);
    // 通过Task获取指定流程变量名称的变量值的信息，存放到Map集合中
    taskService.getVariables(taskId, variableNames);

## Comment 


注意：添加批注的时候，由于Activiti底层代码是使用：

    String userId = Authentication.getAuthenticatedUserId();
     CommentEntity comment = new CommentEntity();
     comment.setUserId(userId);
 
所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人

    Authentication.setAuthenticatedUserId(SessionContext.get().getName());
    taskService.addComment(taskId, processInstanceId, message);

## Attachment

## 组、用户

表结构介绍

- act_id_group：角色组表
- act_id_user：用户表：
- act_id_membership：用户角色表

    IdentityService identityService = processEngine.getIdentityService();//认证：保存组和用户信息
    identityService.saveGroup(new GroupEntity("部门经理"));//建立组
    identityService.saveGroup(new GroupEntity("总经理"));//建立组
    identityService.saveUser(new UserEntity(“小张”));//建立用户
    identityService.saveUser(new UserEntity("小李")); //建立用户
    identityService.saveUser(new UserEntity("小王")); //建立用户
    identityService.createMembership("小张", "部门经理");//建立组和用户关系
    identityService.createMembership("小李", "部门经理");//建立组和用户关系
    identityService.createMembership(“小王”, “总经理”);//建立组和用户关系

## PersonalTask

个人任务及三种分配方式：

- 在taskProcess.bpmn中直接写 assignee=“张三丰"
- 在taskProcess.bpmn中写 assignee=“#{userID}”，变量的值要是String的。
使用流程变量指定办理人
- 使用TaskListener接口，要使类实现该接口，在类中定义：

    delegateTask.setAssignee(assignee);// 指定个人任务的办理人

使用任务ID和办理人重新指定办理人：

    processEngine.getTaskService().setAssignee(taskId, userId);

## GroupTask

组任务及三种分配方式：

1：在taskProcess.bpmn中直接写 candidate-users=“小A,小B,小C,小D"

2：在taskProcess.bpmn中写 candidate-users=“#{userIDs}”，变量的值要是String的。
使用流程变量指定办理人

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("userIDs", "大大,小小,中中");

3，使用TaskListener接口，使用类实现该接口，在类中定义：

    //添加组任务的用户
    delegateTask.addCandidateUser(userId1);
    delegateTask.addCandidateUser(userId2);
    组任务分配给个人任务（认领任务）：
    processEngine.getTaskService().claim(taskId, userId);
    个人任务分配给组任务：
    processEngine.getTaskService(). setAssignee(taskId, null);
    向组任务添加人员：
    processEngine.getTaskService().addCandidateUser(taskId, userId);
    向组任务删除人员：
    processEngine.getTaskService().deleteCandidateUser(taskId, userId);

组任务对应的表：

#存放组任务的办理人：act_ru_identitylink


# BPMN2.0

## Start事件

## End事件 

## 定时器事件

定时器的定义只能有一个下面的元素
timeData：该格式以 ISO 8601格式指定了触发事件的确定时间（译注，即，在确定时刻触发定时器事件） 。

    <timerEventDefinition> 
         <timeDate>2011-03-11T12:13:14</timeDate> 
    </timerEventDefinition> 

timeDuration：指定定时器事件在触发前运行多长时间，timeDuration 可以作为 timeEventDuration 的子元素来指定。

使用的格式是 ISO 8601 格式（这是 BPMN 2.0 规范所要求的）。示例（间隔10 天 ）： 

    <timeDuration>P10D</timeDuration> 

timeCycle：指定循环的时间间隔（译注，即，每隔多长时间执行一次循环） ，这对于周期性的启动流程、或者给过期
的用户任务发送提示是很有帮助的。时间循环元素可以使用两种格式来指定。首先是循环次数的格式，这是 ISO 8601
所规定的。示例（循环3 次，每次循环持续 10 小 时 ）：

    <timeCycle>R3/PT10H</timeCycle>

此外，你也可以使用cron表达式来指定循环次数，下面的示例展示了每5 分钟触发一次 
0 0/5 # # # ? 
## 流

### 顺序流（sequence flow） 

    <sequenceFlow id="flow1" sourceRef="theStart" targetRef="theTask" /> 

### 带条件的顺序流（conditional sequence flow） 

可以在顺序流上定义条件。当顺序流程左侧是 BPMN 2.0 的活动时，就会计算其输出顺序流上的条件。选取条件成立的输
出顺序流来执行。如果选取了多个顺序流，就会创建多个执行路径，并且流程会以并行的方式来执行。 
 
注意：以上适用于 BPMN 2.0 的活动（以及事件），但是不适用于 gateways。根据 gateway 的类型，其会以其特有的方式
来处理带有条件的顺序流。

    <sequenceFlow id="flow" sourceRef="theStart" targetRef="theTask"> 
      <conditionExpression xsi:type="tFormalExpression"> 
        <![CDATA[${order.price > 100 && order.price < 250}]]> 
      </conditionExpression> 
    </sequenceFlow> 

### 默认顺序流

所有 BPMN 2.0 任务以及 gateways都可以有一个默认的顺序流。只有当没有其它顺序流被选取的情况下，才选取该顺序流
作为活动的输出顺序流。默认顺序流上的条件总是被忽略掉。 

    <exclusiveGateway id="exclusiveGw" name="Exclusive Gateway" default="flow2" /> 

## 分支（Gateways） 

### 单一分支（Exclusive Gateway）

exclusive gateway（也称为XOR gateway，或更专业点，exclusive data-based gateway）用来对流程中的决定进行建模。流程执行到这种 gateway 时，按照输出流定义的顺序对它们进行计算。条件为 true 的顺序流（或没有设置条件，概念上顺序流上定义为’true’）被选取继续执行流程。 
 
注意此处输出顺序流的含义与 BPMN 2.0中一般情形下的顺序流是不一样的。虽然通常所有那些条件为 true的顺序流都会被选取以并行的方式继续流程的执行，但在使用 exclusive gateway 时，只有一个顺序流被选取。在多个顺序流条件为true的情况下，XML 中最先定义的那个被选取来继续流程的执行（仅有那个会被选中） 。如果没有选取到任何顺序流，就会抛出异常。 

    <exclusiveGateway id="exclusiveGw" name="Exclusive Gateway" /> 
     
    <sequenceFlow id="flow2" sourceRef="exclusiveGw" targetRef="theTask1"> 
       <conditionExpression xsi:type="tFormalExpression">${input == 1}</conditionExpression> 
    </sequenceFlow> 

### 并行分支（parallel gateway） 

Parallel Gateway，它能拆分出多个执行路径，或多个输入执行路径进行合并。

拆分（fork） ：并行执行所有的输出顺序流，为每一个顺序流创建一个并行执行路径。
合并（join） ：所有到达 parallel gataway 的并发性的执行路径都等待于此，直到每个输入流都执行到。然后，流程经由 joining gateway继续向下执行。 

与其它 gateway 一个重要的不同点是 parallel gateway 不会计算条件。如果在连接 parallel gateway上定义了条件，那么那
些条件会被简单的忽略掉。 

## Activity 活动

### UserTask

会签

要把一个节点设置为多实例，节点xml元素必须设置一个multiInstanceLoopCharacteristics子元素。 

    <multiInstanceLoopCharacteristics isSequential="false|true">
    ...
    </multiInstanceLoopCharacteristics>

isSequential属性表示节点是进行 顺序执行还是并行执行。
 
实例的数量会在进入节点时计算一次。 有一些方法配置它。

一种方法是使用loopCardinality子元素直接指定一个数字。 

    <multiInstanceLoopCharacteristics isSequential="false|true">
    <loopCardinality>5</loopCardinality>
    </multiInstanceLoopCharacteristics>

也可以使用结果为整数的表达式： 

    <loopCardinality>${nrOfOrders-nrOfCancellations}</loopCardinality>  

另一个定义实例数目的方法是，通过loopDataInputRef子元素，设置一个类型为集合的流程变量名。 对于集合中的每个元素，都会创建一个实例。 也可以通过inputDataItem子元素指定集合。 下面的代码演示了这些配置： 

    <userTask id="miTasks" name="My Task ${loopCounter}" activiti:assignee="${assignee}">
    <multiInstanceLoopCharacteristics isSequential="false">
    <loopDataInputRef>assigneeList</loopDataInputRef>
    <inputDataItem name="assignee" />
    </multiInstanceLoopCharacteristics>
    </userTask>

假设assigneeList变量包含这些值[kermit, gonzo, foziee]。 在上面代码中，三个用户任务会同时创建。每个分支都会拥有一个用名为assignee的流程变量， 这个变量会包含集合中的对应元素，在例子中会用来设置用户任务的分配者。 

loopDataInputRef和inputDataItem的缺点是

1）名字不好记。

2）根据BPMN 2.0格式定义，它们不能包含表达式。

activiti通过在 multiInstanceCharacteristics中设置 collection和 elementVariable属性解决了这个问题： 

    <userTask id="miTasks" name="My Task" activiti:assignee="${assignee}">
    <multiInstanceLoopCharacteristics isSequential="true"
    activiti:collection="${myService.resolveUsersForTask()}" 
    activiti:elementVariable="assignee" >
    </multiInstanceLoopCharacteristics>
    </userTask>

多实例节点在所有实例都完成时才会结束。
也可以指定一个表达式在每个实例结束时执行。 如果表达式返回true，所有其他的实例都会销毁，多实例节点也会结束，流程会继续执行。 这个表达式必须定义在completionCondition子元素中。 

    <userTask id="miTasks" name="My Task" activiti:assignee="${assignee}">
    <multiInstanceLoopCharacteristics isSequential="false"
    activiti:collection="assigneeList" activiti:elementVariable="assignee" >
    <completionCondition>${nrOfCompletedInstances/nrOfInstances >= 0.6 }</completionCondition>
    </multiInstanceLoopCharacteristics>
    </userTask>
