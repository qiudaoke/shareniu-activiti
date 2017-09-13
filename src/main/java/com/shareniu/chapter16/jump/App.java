package com.shareniu.chapter16.jump;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.junit.Before;
import org.junit.Test;

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
		InputStream in = App.class.getClassLoader().getResourceAsStream("com/shareniu/chapter16/activiti.cfg.xml");
		ProcessEngineConfiguration pcf = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(in);
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
	public void testShareniuCommonJumpTaskCmd() throws IOException {
		ReadOnlyProcessDefinition processDefinitionEntity = (ReadOnlyProcessDefinition) repositoryService
				.getProcessDefinition("parallel:1:4");
		// 目标节点
		ActivityImpl destinationActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask4");
		String executionId = "10002";
		String parentId = "2501";
		// 当前节点
		ActivityImpl currentActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask1");
		processEngine.getManagementService().executeCommand(
				new ShareniuCommonJumpTaskCmd(executionId, parentId, destinationActivity, null, currentActivity));
	}

	@Test
	public void testShareniuParallelJumpTaskCmd() throws IOException {
		ReadOnlyProcessDefinition processDefinitionEntity = (ReadOnlyProcessDefinition) repositoryService
				.getProcessDefinition("parallel:1:4");
		// 目标节点
		ActivityImpl destinationActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask1");
		String executionId = "10002";
		String parentId = "2501";
		// 当前节点
		ActivityImpl currentActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask2");
		processEngine.getManagementService().executeCommand(
				new ShareniuParallelJumpTaskCmd(executionId, parentId, destinationActivity, null, currentActivity));
	}

	@Test
	public void startProcessInstanceById() throws IOException {

		ReadOnlyProcessDefinition processDefinitionEntity = (ReadOnlyProcessDefinition) repositoryService
				.getProcessDefinition("multiInstance:1:7504");
		// 目标节点
		ActivityImpl destinationActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask2");
		String executionId = "10008";
		String parent = "10001";
		// 当前节点
		ActivityImpl currentActivity = (ActivityImpl) processDefinitionEntity.findActivity("usertask1");
		processEngine.getManagementService().executeCommand(
				new ShareniuMultiInstanceJumpTaskCmd(executionId, parent, destinationActivity, null, currentActivity));
	}

	@Test
	public void testComplete() throws IOException {
		taskService.complete("5022");

	}

	@Test
	public void createTask() throws IOException {
		// Task newTask = taskService.newTask("x");
		// taskService.saveTask(newTask);
		// ActivityExecution execution = null;
		// TaskEntity.createAndInsert(execution);
		processEngine.getManagementService().executeCommand(new FindParentCmd());

	}

	@Test
	public void testShareniuMinusSignCmd() throws IOException {
		// Task newTask = taskService.newTask("x");
		// taskService.saveTask(newTask);
		// ActivityExecution execution = null;
		// TaskEntity.createAndInsert(execution);
		processEngine.getManagementService().executeCommand(new ShareniuMinusSignCmd("5011"));

	}
}
