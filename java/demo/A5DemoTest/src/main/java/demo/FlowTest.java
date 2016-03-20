package demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;

public class FlowTest {
	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine
				.getRepositoryService();

		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("diagrams/Flow1.bpmn");
		builder.deploy();
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance pi1=runtimeService.startProcessInstanceByKey("FlowTest","1");
		ProcessInstance pi2=runtimeService.startProcessInstanceByKey("FlowTest","2");
		System.out.println(pi1.getBusinessKey());
		System.out.println(pi2.getBusinessKey());
		TaskService taskServer=processEngine.getTaskService();
//		taskServer.createTaskQuery().
	}
}
