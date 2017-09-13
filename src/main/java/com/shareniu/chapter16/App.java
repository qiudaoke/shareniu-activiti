package com.shareniu.chapter16;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.junit.Before;
import org.junit.Test;

import com.shareniu.chapter10.ExtensionBpmnConstants;
import com.shareniu.chapter10.ExtensionOperation;
import com.shareniu.chapter3.DeploymentBuilderTest;

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
				"com/shareniu/chapter16/activiti.cfg.xml");
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
		List<Model> list = repositoryService.createModelQuery().list();
		//taskJuel.bpmn        subProcess.bpmn   activitybehavior.bpmn20.xml    /activiti/src/main/java/com/shareniu/chapter16/jump/jump.bpmn
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResource("com/shareniu/chapter16/jump/multiInstance.bpmn").openStream();
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
	public void startProcessInstanceById() throws IOException {
		//runtimeService.startProcessInstanceById("subprocess:1:4", "shareniu");
		
		Map<String, Object> var=new HashMap<String, Object>();
		var.put("a", "a");
		runtimeService.startProcessInstanceById("multiInstance:1:4");
		
	}
	@Test
	public void findActivities() {
		//   extensionOperationProcess:2:62504
		String processDefinitionId = "multiInstance:1:4";
		ProcessDefinitionEntity pdf = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		List<ActivityImpl> activities = pdf.getActivities();
		for (ActivityImpl activityImpl : activities) {
			Object object = activityImpl.getProperties().get(
					ExtensionBpmnConstants.PROPERTY_OPERATIONS_DEFINITION);
			if (object instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) object;
				for (Object obj : map.keySet()) {
					ExtensionOperation value = (ExtensionOperation) map
							.get(obj);
				}
			}
			if (activityImpl.getId().equals("operationTask")) {
				// 获取operationTask节点的入线
				List<PvmTransition> incomingTransitions = activityImpl
						.getIncomingTransitions();
				System.out.println(incomingTransitions.get(0).getId());
				// 获取operationTask节点的出线
				List<PvmTransition> outgoingTransitions = activityImpl
						.getOutgoingTransitions();
				for (PvmTransition pvmTransition : outgoingTransitions) {
				}
			}
		}
	}
	

}
