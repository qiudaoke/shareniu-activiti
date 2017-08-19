package com.shareniu.chapter9;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.converter.util.InputStreamProvider;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import com.shareniu.activiti.learing.ch3.DeploymentBuilderTest;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class App {
	// 获取到Activiti ProcessEngine
	ProcessEngine processEngine = null;
	// 获取RepositoryService 实例对象
	RepositoryService repositoryService = null;
	// 资源名称
	String resourceName = "shareniu_addInputStream.bpmn";
	IdentityService identityService;
	RuntimeService runtimeService;
	TaskService taskService;
	ActivitiEventDispatcher eventDispatcher;

	@Before
	public void init() {
		InputStream in = App.class.getClassLoader().getResourceAsStream(
				"com/shareniu/chapter9/activiti.cfg.xml");
		ProcessEngineConfiguration pcf = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromInputStream(in);
		processEngine = pcf.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		identityService = processEngine.getIdentityService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
		ProcessEngineConfigurationImpl pc = (ProcessEngineConfigurationImpl) processEngine
				.getProcessEngineConfiguration();
		eventDispatcher = pc.getEventDispatcher();
	}

	@Test
	public void addInputStreamTest() throws Exception {
		// 定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResourceAsStream(
						"com/shareniu/chapter9/timer-transition.bpmn");
		// 流程定义的分类
		String category = "variabletypeTest";
		// 构造DeploymentBuilder对象
		Date date = new Date();
		Date addDate = addDate(date, 5);
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment().category(category)
				.addInputStream("variabletype.bpmn", inputStream);
		// 部署
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy);
		// Thread.sleep(50000);

	}

	public static Date addDate(Date date, long day) throws Exception {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	@Test
	public void startProcessInstanceById() throws Exception {
		Map<String, Object> variables = new HashMap<String, Object>();
		// variables.put("a", new Shareniu("1", "shareniu1",18));
		variables.put("shareniu", 20);
		// variables.put("", new ShareniuDelegateExpression())
		ProcessInstance startProcessInstanceById = runtimeService
				.startProcessInstanceById("timer-transition:4:45004", variables);
		System.out.println(startProcessInstanceById);
		Thread.sleep(3000);
	}

	@Test
	public void a() throws Exception {

		taskService.complete("17505");
		// taskService.complete("60005");
		/*
		 * AtomicBoolean isWaiting = new AtomicBoolean(false); boolean
		 * compareAndSet = isWaiting.compareAndSet(true, false);
		 * System.out.println(compareAndSet+"----?????????"+isWaiting );
		 */
	}

	@Test
	public void autoStartProcessCmd() throws Exception {
		// Date now = new Date();//当前时间
		// ServiceImpl serviceImpl =(ServiceImpl)
		// repositoryService;//获取ServiceImpl实例对象
		// serviceImpl.getCommandExecutor().execute(new
		// AutoStartProcessCmd(now,"shareniu"));
		Thread.sleep(1000);

	}

	@Test
	public void xxx() throws Exception {
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		InputStream inputStream = DeploymentBuilderTest.class
				.getClassLoader()
				.getResourceAsStream(
						"com/shareniu/activiti/learing/ch14/job/timer-intermediate-transition.xml");

		InputStreamProvider in = new InputStreamSource(inputStream);
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(in, false,
				false);
		System.out.println(bpmnModel);
		Thread.sleep(2000);
		// repositoryService.suspendProcessDefinitionById("", false, new
		// Date());
		// repositoryService.activateProcessDefinitionById("timer-start-event:1:75006");

	}

	@Test
	public void pvm1() {
		String processDefinitionId = "async:2:95004";
		// 获取流程定义实体对象
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		// 节点的名称
		String taskDefKey = "operationTask1";
		// 获得当前流程定义模型的所有任务节点
		List<ActivityImpl> activitilist = processDefinition.getActivities();
		ActivityImpl currActiviti = null;// 当前活动节点
		for (ActivityImpl activityImpl : activitilist) {
			// 确定当前活动activiti节点
			if (taskDefKey.equals(activityImpl.getId())) {
				currActiviti = activityImpl; // 如果查询到，跳出循环
				break;
			}
		}

	}
}
