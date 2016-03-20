package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class PersonTask extends BaseTest {

	@Test
	public void testPersonTask() throws Exception {
		deleteAllDeploy();
		deploy();
		startProcess();
		searchProcess();
		execTask();
	}

	@Test
	public void deploy() throws Exception {
		repositoryService.createDeployment()//
				.addClasspathResource("diagrams/PersonTask.bpmn")//
				.addClasspathResource("diagrams/PersonTask.png")//
				.deploy();
		System.out.println("PersonTask deploy finished");
	}

	/**
	 * 启动流程
	 * 
	 * @throws Exception
	 */
	@Test
	public void startProcess() throws Exception {
		System.out.println("------------startProcess");
		// 使用流程定义的key启动流程实例，默认会按照最新版本启动流程实例
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("var1", "this var 1");
		mp.put("var2", "this var 2");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(
				"persontask", "buskey1", mp);
		System.out.println("ProcessInstanceId:" + pi.getId());
		System.out.println("ActivityId:"+ pi.getActivityId());
		System.out.println("------------startProcess end");
	}

	/**
	 * 
	 */
	@Test
	public void searchProcess() {
		System.out.println("------------searchProcess");
		//同一业务ID可以启动相同的多个流程，所以可以是列表
		List<ProcessInstance> lstpi = runtimeService
				.createProcessInstanceQuery().//
				processInstanceBusinessKey("buskey1").list();
		for (Iterator<ProcessInstance> iterator = lstpi.iterator(); iterator
				.hasNext();) {
			ProcessInstance pi = (ProcessInstance) iterator.next();
			System.out.println("ProcessInstanceId:" + pi.getId());
			System.out.println("DeploymentId:" +pi.getDeploymentId());
		}
		System.out.println("------------searchProcess end");
	}
	@Test
	public void execTask() {
		System.out.println("------------execTask");
		List<Execution> lstExe=runtimeService.createExecutionQuery().list();
		for (Iterator<Execution> iterator = lstExe.iterator(); iterator.hasNext();) {
			Execution exe = (Execution) iterator.next();
			System.out.println("ActivityId:"+exe.getActivityId());
			System.out.println("Name:"+exe.getName());
			System.out.println("ProcessInstanceId:"+exe.getProcessInstanceId());
		}
		List<Task> lstTask=taskService.createTaskQuery().list();
		for (Iterator<Task> iterator = lstTask.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			System.out.println("taskid:"+task.getId());
			System.out.println("taskName:"+task.getName());
			System.out.println("Assignee:"+task.getAssignee());
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("var1", "this var 1 of taskid "+task.getId());
			mp.put("var2", "this var 2 of taskid "+task.getId());
			taskService.complete(task.getId(), mp);
		}
		lstTask=taskService.createTaskQuery().list();
		for (Iterator<Task> iterator = lstTask.iterator(); iterator.hasNext();) {
			Task task = (Task) iterator.next();
			System.out.println("taskid:"+task.getId());
			System.out.println("taskName:"+task.getName());
			System.out.println("Assignee:"+task.getAssignee());
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("var1", "this var 1 of taskid "+task.getId());
			mp.put("var2", "this var 2 of taskid "+task.getId());
			taskService.complete(task.getId(), mp);
		}
		System.out.println("------------execTask end");
	}
}
