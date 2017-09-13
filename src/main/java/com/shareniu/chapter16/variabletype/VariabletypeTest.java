package com.shareniu.chapter16.variabletype;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import com.shareniu.activiti.learing.ch3.DeploymentBuilderTest;
import com.shareniu.chapter15.App;

public class VariabletypeTest {
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
				"com/shareniu/chapter16/variabletype/activiti.cfg.xml");
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
	public void addInputStreamTest(){
		//定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader().getResourceAsStream("com/shareniu/activiti/learing/ch4/variabletype/activiti.cfg.xml");
		//流程定义的分类
		String category="variabletypeTest";
		//构造DeploymentBuilder对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().category(category).addInputStream("variabletype.bpmn", inputStream);
		//部署
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy);
		
	}
	@Test  
	public void complete() {  
	    // 与任务相关的service,正在执行的service  
	    TaskService taskService = processEngine.getTaskService();  
	  
	    // 任务ID  
	    String taskId = "7547";  
	    Map<String, Object> variables=new HashMap<>();
	    variables.put("a", false);
	  taskService.complete(taskId,variables);
	  
	}  
	@Test  
	public void setVariables() {  
	    // 与任务相关的service,正在执行的service  
	    TaskService taskService = processEngine.getTaskService();  
	  
	    // 任务ID  
	    String taskId = "37510";  
	  
	    Map<String, Object> variables=new HashMap<>();
	    variables.put("a", "a");
	    //variables.put("b", "b");
		// 1.设置流程变量，使用基本数据类型  
	    taskService.setVariables(taskId, variables);
	   //taskService.setVariableLocal(taskId, "a", "1");  
	      
	    System.out.println("设置流程变量成功！");  
	  
	}  
	@Test
	public void startProcessInstanceById(){
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("shareniu", new Shareniu("1", "shareniu1",18));
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById("subprocess:1:4",variables);
		System.out.println(startProcessInstanceById);
		
	}
	@Test
	public void fixSerializedVariables(){
		 List<Task> tasks = taskService.createTaskQuery().list();
		 for (Task task : tasks) {
	            Map<String, Object> vars = taskService.getVariables(task.getId());
	            for (Map.Entry<String, Object> entry : vars.entrySet()) {
	            	 Object value = entry.getValue();
	            	 if (value instanceof Shareniu ) {
	            		/* taskService.removeVariable(task.getId(), entry.getKey());
	                     taskService.setVariable(task.getId(), entry.getKey(), value);*/
	            		 Shareniu s=(Shareniu) value;
	            		 System.out.println(s);
	                }
	            }
		 }
	}
	
	
}
