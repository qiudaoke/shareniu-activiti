package com.shareniu.chapter14;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import com.shareniu.chapter3.DeploymentBuilderTest;
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
				"com/shareniu/chapter14/activiti.cfg.xml");
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
	public void addInputStreamTest() throws IOException {
		// 定义的文件信息的流读取
		/*InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResource("com/shareniu/chapter14/activitybehavior.bpmn20.xml").openStream();*/
		//usertaskactivitybehavior.bpmn20.xml
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResource("com/shareniu/chapter14/callactivity.bpmn").openStream();
		// 流程定义的分类
		String category = "shareniu_addInputStream";
		// 构造DeploymentBuilder对象
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment().category(category)
				.addInputStream(resourceName, inputStream);
		// 部署
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy);

	}
	@Test
	public void startProcessInstanceById() {
		runtimeService.startProcessInstanceById("extensionUserTask:2:62504");
	}
	@Test
	public void startProcessInstanceByIdskipExpression() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", false);
		runtimeService.startProcessInstanceById("skipExpression:1:4", map);
	}
	@Test
	public void startsubprocess() throws IOException {
		Authentication.setAuthenticatedUserId("aaaaaaa");
		runtimeService.startProcessInstanceById("subprocess:1:4", "shareniu");
		//runtimeService.startProcessInstanceById("subprocess:2:15004", "shareniu");
		//taskService.claim(taskId, userId);
	}
	@Test
	public void testtaskService() throws IOException {
		taskService.complete("333");
		
	}
	
}
