package test;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class TestTask extends BaseTest {
	@Test
	public void deployZIP() throws Exception {
		// 获得上传文件的输入流程
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("diagrams/leave.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		// 获取仓库服务，从类路径下完成部署
		Deployment deployment = processEngine.getRepositoryService()//
				.createDeployment()//
				.name("请假流程")// 添加部署规则的显示别名
				.addZipInputStream(zipInputStream)// 使用zip的输入流完成部署
				.deploy();// 完成发布
		System.out.println(deployment.getId() + "        "
				+ deployment.getName());
	}

	@Test
	public void queryProcessState() throws Exception {
		String processInstanceId = "1201";
		// 通过流程实例ID查询流程实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.createProcessInstanceQuery()// 创建流程实例查询，查询正在执行的流程实例
				.processInstanceId(processInstanceId)// 按照流程实例ID查询
				.singleResult();// 返回惟一的结果集
		if (pi != null) {
			System.out.println("当前流程在：" + pi.getActivityId());
		} else {
			System.out.println("流程已结束!!");
		}
	}

	@Test
	public void queryHistoryTask() throws Exception {
		// 历史任务办理人
		String taskAssignee = "张三";
		// 使用办理人查询流程实例
		List<HistoricTaskInstance> list = processEngine.getHistoryService()//
				.createHistoricTaskInstanceQuery()// 创建历史任务查询
				.taskAssignee(taskAssignee)// 指定办理人查询历史任务
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance task : list) {
				System.out.println("任务ID：" + task.getId());
				System.out.println("流程实例ID：" + task.getProcessInstanceId());
				System.out.println("任务的办理人：" + task.getAssignee());
				System.out.println("执行对象ID：" + task.getExecutionId());
				System.out.println(task.getStartTime() + "　"
						+ task.getEndTime() + "　" + task.getDurationInMillis());
			}
		}
	}

	@Test
	public void queryHistoryProcessInstance() throws Exception {
		String processInstanceId = "1201";
		HistoricProcessInstance hpi = processEngine.getHistoryService()//
				.createHistoricProcessInstanceQuery()// 创建历史流程实例查询
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		System.out.println("流程定义ID：" + hpi.getProcessDefinitionId());
		System.out.println("流程实例ID：" + hpi.getId());
		System.out.println(hpi.getStartTime() + "　　　　" + hpi.getEndTime()
				+ "        " + hpi.getDurationInMillis());
	}

}
