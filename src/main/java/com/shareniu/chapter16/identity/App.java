package com.shareniu.chapter16.identity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

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
					"com/shareniu/chapter16/identity/activiti.cfg.xml");
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
			//taskJuel.bpmn        subProcess.bpmn   activitybehavior.bpmn20.xml    /activiti/src/main/java/com/shareniu/chapter16/jump/jump.bpmn
			//  sequential.bpmn    multiInstance.bpmn   collectionmultiInstance.bpmn  collectionmultiInstance.bpmn
			InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
					.getResource("com/shareniu/chapter16/identity/usertaskactivitybehavior.bpmn20.xml").openStream();
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
			runtimeService.startProcessInstanceById("dep:5:47504");
		}
		@Test
		public void saveUser() {
			ShareniuUserEntity user=new ShareniuUserEntity();
			user.setId(UUID.randomUUID().toString());
			user.setLoginId("a");
			user.setPassword("1");
			user.setName("shareniu");
			identityService.saveUser(user);
		}
		@Test
		public void deleteUser() {
			identityService.deleteUser("920ed436-b36b-47e7-8f93-f361df7ba437");
		}
		@Test
		public void list() {
			ShareniuUserQueryImpl userQuery = (ShareniuUserQueryImpl) identityService.createUserQuery();
			List<User> list = userQuery.loginId("b").list();
			System.err.println(list.size());
		}
		@Test
		public void executeCommand() {
			processEngine.getManagementService().executeCommand(new AddIdentitylinkCmd("shareniu1",null));
		}
		@Test
		public void executeShareniuFindIdentityLinksByTaskId() {
			
			processEngine.getManagementService().executeCommand(new ShareniuFindIdentityLinksByTaskId("62504"));
		}
}
