package demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DeploymentBuilder;

public class ActivitiHelloWorld {
	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine
				.getRepositoryService();

		DeploymentBuilder builder = repositoryService.createDeployment();

		builder.addClasspathResource("diagrams/HelloWorld.bpmn");

		builder.deploy();
		// select * from `ACT_GE_PROPERTY`;这时这个表中会多条数据

		RuntimeService runtimeService = processEngine.getRuntimeService();

		runtimeService

		.startProcessInstanceByKey("myProcess");// 启动流程，ID必须与你配置的一致

		System.out.println("ok......");
	}
}
