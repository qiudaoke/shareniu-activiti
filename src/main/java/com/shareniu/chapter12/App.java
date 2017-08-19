package com.shareniu.chapter12;

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
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager;
import org.junit.Before;
import org.junit.Test;
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
		InputStream in = App.class.getClassLoader().getResourceAsStream("com/shareniu/chapter12/activiti.cfg.xml");
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
		ServiceImpl service=	(ServiceImpl)taskService;
		CommandExecutor commandExecutor = service.getCommandExecutor();
		commandExecutor.execute(new Command<Object>() {
			public Object execute(CommandContext commandContext) {
				ProcessDefinitionEntityManager pdem = commandContext.getProcessDefinitionEntityManager();
				ProcessDefinitionEntity pde = pdem.findProcessDefinitionById("extensionOperationProcess:2:62504");
				System.out.println(pde);
				return null;
			}
		});
		
	}
	@Test
	public void startProcessInstanceById() {
		Map<String, Object> map=new HashMap<String, Object>();
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
	
	
	
