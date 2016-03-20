package test;

import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class TestHistory extends BaseTest {
	// 1 历史流程实例查看（查找按照某个规则一共执行了多少次流程）
	@Test
	public void queryHistoricProcessInstance() throws Exception {
		// 获取历史流程实例，返回历史流程实例的集合
		List<HistoricProcessInstance> hpiList = processEngine
				.getHistoryService()//
				.createHistoricProcessInstanceQuery()// 创建历史流程实例查询
				.processDefinitionKey("myProcess")// 按照流程定义的key查询
				.orderByProcessInstanceStartTime().desc()// 按照流程开始时间降序排列
				.list();// 返回结果集
		// 遍历查看结果
		for (HistoricProcessInstance hpi : hpiList) {
			System.out.print("pid:" + hpi.getId() + ",");
			System.out.print("pdid:" + hpi.getProcessDefinitionId() + ",");
			System.out.print("startTime:" + hpi.getStartTime() + ",");
			System.out.print("endTime:" + hpi.getEndTime() + ",");
			System.out.print("duration:" + hpi.getDurationInMillis() + ",");
			System.out.println("------------------------");
		}
	}

	/**
	 * 某一次流程的执行一共经历的多少步
	 * 
	 * @throws Exception
	 */
	@Test
	public void queryHistoricActivitiInstance() throws Exception {
		String processInstanceId = "2401";
		List<HistoricActivityInstance> haiList = processEngine
				.getHistoryService().createHistoricActivityInstanceQuery() // 创建历史活动实例查询
				.processInstanceId(processInstanceId)// 使用流程实例id查询
				// .listPage(firstResult, maxResults)// 分页条件
				.orderByHistoricActivityInstanceEndTime().asc()// 排序条件
				.list();// 执行查询
		for (HistoricActivityInstance hai : haiList) {
			System.out.print("activitiId:" + hai.getActivityId() + "，");
			System.out.print("name:" + hai.getActivityName() + "，");
			System.out.print("type:" + hai.getActivityType() + "，");
			System.out.print("pid:" + hai.getProcessInstanceId() + "，");
			System.out.print("assignee:" + hai.getAssignee() + "，");
			System.out.print("startTime:" + hai.getStartTime() + "，");
			System.out.print("endTime:" + hai.getEndTime() + "，");
			System.out.println("duration:" + hai.getDurationInMillis());
		}
	}

	// 3 历史任务查看(某一次流程的执行经历的多少任务节点)
	@Test
	public void queryHistoricTask() throws Exception {
		String processInstanceId = "2401";
		List<HistoricTaskInstance> htiList = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()// 创建历史任务的查询
				.processInstanceId(processInstanceId)// 使用流程实例id查询
				// .listPage(firstResult, maxResults)// 分页条件
				.orderByHistoricTaskInstanceStartTime().asc()// 排序条件
				.list();// 执行查询
		for (HistoricTaskInstance hti : htiList) {
			System.out.print("taskId:" + hti.getId() + "，");
			System.out.print("name:" + hti.getName() + "，");
			System.out.print("pdId:" + hti.getProcessDefinitionId() + "，");
			System.out.print("pid:" + hti.getProcessInstanceId() + "，");
			System.out.print("assignee:" + hti.getAssignee() + "，");
			System.out.print("startTime:" + hti.getStartTime() + "，");
			System.out.print("endTime:" + hti.getEndTime() + "，");
			System.out.println("duration:" + hti.getDurationInMillis());
		}
	}

	// 某一次流程的执行时设置的流程变量
	@Test
	public void queryHistoricVariables() {
		String processInstanceId = "2401";
		List<HistoricVariableInstance> hviList = processEngine
				.getHistoryService()//
				.createHistoricVariableInstanceQuery()// 创建历史流程变量查询
				.processInstanceId(processInstanceId) // 按照流程实例ID查询
				.orderByVariableName().asc() // 排序条件
				.list();
		if (hviList != null && hviList.size() > 0) {
			for (HistoricVariableInstance hvi : hviList) {
				System.out.print("piId:" + hvi.getProcessInstanceId() + "，");
				System.out
						.print("variablesName:" + hvi.getVariableName() + "，");
				System.out.print("variablesValue:" + hvi.getValue() + "，");
			}
		}
	}

}
