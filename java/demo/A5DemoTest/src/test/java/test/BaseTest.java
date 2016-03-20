package test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BaseTest {
	ProcessEngine processEngine = null;
	RepositoryService repositoryService = null;
	TaskService taskService = null;
	RuntimeService runtimeService = null;

	@Before
	public void setUp() {
		// 获取流程引擎
		processEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		taskService = processEngine.getTaskService();
		runtimeService = processEngine.getRuntimeService();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void deploy() throws Exception {
		repositoryService.createDeployment()//
				.addClasspathResource("diagrams/HelloWorld.bpmn")//
				.addClasspathResource("diagrams/HelloWorld.png")//
				.deploy();
		repositoryService.createDeployment()//
				.addClasspathResource("diagrams/Account.bpmn")//
				.addClasspathResource("diagrams/Account.png")//
				.deploy();
		repositoryService.createDeployment()//
		.addClasspathResource("diagrams/PersonTask.bpmn")//
		.addClasspathResource("diagrams/PersonTask.png")//
		.deploy();
		System.out.println("deploy finished");
	}
	/**
	 * 删除部署定义
	 * @throws Exception
	 */
	@Test
	public void deleteAllDeploy() throws Exception {
		// 1，查询指定key的所有版本的流程定义
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery()//
//				.processDefinitionKey("myProcess")// 指定流程定义的key查询
				.list();
		// 2，循环删除
		for (ProcessDefinition pd : list) {
			processEngine.getRepositoryService()//
					.deleteDeployment(pd.getDeploymentId(), true);
			System.out.println(pd.getKey()+"删除成功！");
		}
	}
}
