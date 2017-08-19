package com.shareniu.chapter6;

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
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
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
		InputStream in = App.class.getClassLoader().getResourceAsStream("com/shareniu/chapter6/activiti.cfg.xml");
		ProcessEngineConfiguration pcf = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(in);
		 processEngine = pcf.buildProcessEngine();
		 repositoryService = processEngine.getRepositoryService();
			identityService = processEngine.getIdentityService();
			 runtimeService = processEngine.getRuntimeService();
			  taskService = processEngine.getTaskService();
			  ProcessEngineConfigurationImpl pc = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
			   eventDispatcher = pc.getEventDispatcher();
	}

	@Test
	public void addInputStreamTest() {
		// 定义的文件信息的流读取 
		InputStream inputStream = DeploymentBuilderTest.class
				.getClassLoader()
				.getResourceAsStream(
						"com/shareniu/chapter6/ActivitiEventListener.bpmn");
		// 流程定义的分类
		String category = "shareniu_addInputStream";
		// 构造DeploymentBuilder对象
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment().category(category)
				.addInputStream(resourceName, inputStream);
		// 部署
		Deployment deploy = deploymentBuilder.deploy();
	}
	@Test
	public void startProcessInstanceById() {
		Map<String, Object> map=new HashMap<String, Object>();
		//map.put("bean", new ShareniuExpress());
	//	map.put("a", new ShareniuDelegateExpression());
		ShareniuEventListener listener = new ShareniuEventListener();
		eventDispatcher.addEventListener(listener);
		//移除事件监听器
		//eventDispatcher.removeEventListener(listener1);
		runtimeService.startProcessInstanceById("myProcess:1:4",map);
	}
	@Test
	public void complete() {
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("s", "s");
		variables.put("s1", "s1");
		String taskId="2504";
		taskService.complete(taskId, variables);
	}
	
}
	
	
	
