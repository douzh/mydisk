package test;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TestHello extends BaseTest {

	// 2. 启动流程
	@Test
	public void startProcess() throws Exception {
		// 启动流程
		// 使用流程定义的key启动流程实例，默认会按照最新版本启动流程实例
		ProcessInstance pi = runtimeService
				.startProcessInstanceByKey("myProcess");
		System.out.println("pid:" + pi.getId() + ",activitiId:"
				+ pi.getActivityId());
	}

	// 3. 查看任务
	@Test
	public void queryMyTask() throws Exception {
		runtimeService.startProcessInstanceByKey("myProcess");
		// 指定任务办理者
		String assignee = "hruser";
		// 查询任务的列表
		List<Task> tasks = taskService.createTaskQuery()// 创建任务查询对象
				.taskAssignee(assignee)// 指定个人任务办理人
				.list();
		// 遍历结合查看内容
		for (Task task : tasks) {
			System.out.println("taskId:" + task.getId() + ",taskName:"
					+ task.getName());
			System.out.println("*************************");
		}
	}

	// 4. 办理任务
	@Test
	public void completeTask() throws Exception {
		String taskId = "104";
		// 完成任务
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成任务！");
	}

}
