package com.shareniu.chapter15;

import java.io.InputStream;
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
		InputStream in = App.class.getClassLoader().getResourceAsStream(
				"com/shareniu/chapter15/activiti.cfg.xml");
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
	public void testCustomSqlExecution() {
		ManagementService managementService = processEngine
				.getManagementService();
		// 实例化CustomSqlExecution对象，这里使用匿名内部类
		CustomSqlExecution<ShareniuMapper, List<Map<String, Object>>> customSqlExecution = new AbstractCustomSqlExecution<ShareniuMapper, List<Map<String, Object>>>(
				ShareniuMapper.class) {
			public List<Map<String, Object>> execute(ShareniuMapper customMapper) {
				// 查询数据库操作
				return customMapper.selectTasks();
			}
		};
		// 执行executeCustomSql函数
		List<Map<String, Object>> results = managementService
				.executeCustomSql(customSqlExecution);
		System.out.println(results);// 输出从数据库中查询到的值
	}

}
