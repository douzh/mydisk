package test;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestProcessDefinition extends BaseTest {

	// 查询所有
	@Test
	public void queryProcessDefinition() throws Exception {
		// 获取仓库服务对象,使用版本的升序排列，查询列表
		List<ProcessDefinition> pdList = repositoryService
				.createProcessDefinitionQuery()
				// 添加查询条件
				// .processDefinitionName(processDefinitionName)
				// .processDefinitionId(processDefinitionId)
				// .processDefinitionKey(processDefinitionKey)
				// 排序
				.orderByProcessDefinitionVersion().asc()
				// 查询的结果集
				// .count()//返回结果集的数量
				// .listPage(firstResult, maxResults)//分页查询
				// .singleResult()//惟一结果集
				.list();// 总的结果集数量
		// 遍历集合，查看内容
		for (ProcessDefinition pd : pdList) {
			System.out.println("id:" + pd.getId());
			System.out.println("name:" + pd.getName());
			System.out.println("key:" + pd.getKey());
			System.out.println("version:" + pd.getVersion());
			System.out.println("resourceName:" + pd.getDiagramResourceName());
			System.out.println("***************************************");
		}
	}

	// 删除（使用流程定义ID）
	@Test
	public void deleteDeployment() throws Exception {
		// 删除发布信息
		String deploymentId = "1";
		// 普通删除，如果当前规则下有正在执行的流程，则抛异常
		// repositoryService.deleteDeployment(deploymentId);
		// 级联删除,会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
		// 相当于：repositoryService.deleteDeploymentCascade(deploymentId);
		repositoryService.deleteDeployment(deploymentId, true);
	}

	// 查看流程图（xxx.png）
	@Test
	public void showView() throws Exception {
		// 从仓库中找需要展示的文件
		String deploymentId = "12501";
		List<String> names = repositoryService
				.getDeploymentResourceNames(deploymentId);
		String imageName = "";
		for (String name : names) {
			System.out.println("name:" + name);
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		System.out.println("imageName:" + imageName);
		if (imageName != null) {
			// System.out.println(imageName);
			File f = new File("e:/" + imageName);
			// 通过部署ID和文件名称得到文件的输入流
			InputStream in = repositoryService.getResourceAsStream(
					deploymentId, imageName);
			FileUtils.copyInputStreamToFile(in, f);
		}
	}

	@Test
	public void queryAllLatestVersions() throws Exception {
		// 查询，把最大的版本都排到后面
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery()//
				.orderByProcessDefinitionVersion().asc()//
				.list();
		// 过滤出最新的版本
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : list) {
			map.put(pd.getKey(), pd);
		}

		// 显示
		for (ProcessDefinition pd : map.values()) {
			System.out.println("id:" + pd.getId()// 格式：{key}-{version}
					+ ", name:" + pd.getName()// .jpdl.xml根元素的name属性的值
					+ ", key:" + pd.getKey()// .jpdl.xml根元素的key属性的值，如果不写，默认为name属性的值
					+ ", version:" + pd.getVersion()// 默认自动维护，第1个是1，以后相同key的都会自动加1
					+ ", deploymentId:" + pd.getDeploymentId()); // 所属的某个Deployment的对象
		}
	}

	@Test
	public void deleteByKey() throws Exception {
		// 1，查询指定key的所有版本的流程定义
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery()//
				.processDefinitionKey("myProcess")// 指定流程定义的key查询
				.list();
		// 2，循环删除
		for (ProcessDefinition pd : list) {
			processEngine.getRepositoryService()//
					.deleteDeployment(pd.getDeploymentId(), true);
		}
		System.out.println("删除成功！");
	}

}
