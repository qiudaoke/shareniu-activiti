package com.shareniu.chapter2.configurator;

import java.io.InputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
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
		@Before
		public void init() {
			
			InputStream in = App.class.getClassLoader().getResourceAsStream("com/shareniu/chapter2/configurator/activiti.cfg.xml");
			ProcessEngineConfiguration pcf = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(in);
			 processEngine = pcf.buildProcessEngine();
			 repositoryService = processEngine.getRepositoryService();
				identityService = processEngine.getIdentityService();
				 runtimeService = processEngine.getRuntimeService();
				  taskService = processEngine.getTaskService();
				  ProcessEngineConfigurationImpl pc = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
		}
		
		@Test
		public void a(){
			
		}
		@Test
		public void buildProcessEngine(){
			ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();			
					//数据库驱动信息
					processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
					//连接的数据库连接字符串
				processEngineConfiguration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti");
					//连接的数据库用户名
					processEngineConfiguration.setJdbcUsername("root");
					//连接的数据库密码
					processEngineConfiguration.setJdbcPassword("");
					//工作流的核心对象，ProcessEnginee对象
			ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
			System.out.println(processEngine);
					}
}
