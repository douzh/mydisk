package test;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TestVariable extends BaseTest {
	/** 设置流程变量 */
	@Test
	public void setVariables() {
		// 获取执行的Service
		TaskService taskService = processEngine.getTaskService();
		// 指定办理人
		String assigneeUser = "张三";
		// 流程实例ID
		String processInstanceId = "1701";
		Task task = taskService.createTaskQuery()// 创建任务查询
				.taskAssignee(assigneeUser)// 指定办理人
				.processInstanceId(processInstanceId)// 指定流程实例ID
				.singleResult();
		/** 一：变量中存放基本数据类型 */
		// taskService.setVariable(task.getId(), "请假人",
		// "张无忌");//使用流程变量的名称和流程变量的值设置流程变量，一次只能设置一个值
		// taskService.setVariable(task.getId(), "请假天数", 3);
		// taskService.setVariable(task.getId(), "请假日期", new Date());
		/** 二：变量中存放javabean对象，前提：让javabean对象实现implements java.io.Serializable */
		Person p = new Person();
		p.setId(1L);
		p.setName("翠花");
		taskService.setVariable(task.getId(), "人员信息", p);
	}

	/** 获取流程变量 */
	@Test
	public void getVariables() {
		// 获取执行的Service
		TaskService taskService = processEngine.getTaskService();
		// 指定办理人
		String assigneeUser = "张三";
		// 流程实例ID
		String processInstanceId = "1701";
		Task task = taskService.createTaskQuery()// 创建任务查询
				.taskAssignee(assigneeUser)// 指定办理人
				.processInstanceId(processInstanceId)// 指定流程实例ID
				.singleResult();
		/** 一：变量中存放基本数据类型 */
		// String stringValue = (String) taskService.getVariable(task.getId(),
		// "请假人");
		// Integer integerValue = (Integer)
		// taskService.getVariable(task.getId(), "请假天数");
		// Date dateValue = (Date) taskService.getVariable(task.getId(),
		// "请假日期");
		// System.out.println(stringValue+"     "+integerValue+"     "+dateValue);
		/** 二：变量中存放javabean对象，前提：让javabean对象实现implements java.io.Serializable */
		/**
		 * 获取流程变量时注意：流程变量如果是javabean对象，除了要求实现Serializable之外，
		 * 同时要求流程变量对象的属性不能发生发生变化，否则抛出异常 解决方案：在设置流程变量的时候，在javabean的对象中使用： private
		 * static final long serialVersionUID = -8065294171680448312L;
		 */
		Person p = (Person) taskService.getVariable(task.getId(), "人员信息");
		System.out.println(p.getId());
		System.out.println(p.getName());
	}

	/**
	 * 获取历史流程变量值
	 */
	@Test
	public void getHisVariables() {
		List<HistoricVariableInstance> list = processEngine.getHistoryService()//
				.createHistoricVariableInstanceQuery()//
				.variableName("请假天数")// 指定流程变量名称查询
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricVariableInstance hvi : list) {
				System.out.println(hvi.getVariableName() + "     "
						+ hvi.getValue());
			}
		}
	}
}
