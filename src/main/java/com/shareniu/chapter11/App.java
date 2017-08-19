package com.shareniu.chapter11;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.UrlResource;

import com.shareniu.activiti.learing.ch11.ShareniuDelegateExpression;
import com.shareniu.activiti.learing.ch3.DeploymentBuilderTest;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class App {
	ApplicationContext applicationContext;
	ProcessEngine processEngine;
	RepositoryService repositoryService ;
	TaskService taskService;
	RuntimeService runtimeService;
	@Before
	public void genericXmlApplicationContext() throws Exception {
		 ClassLoader classLoader = ReflectUtil.getClassLoader();
		 Enumeration<URL> resources = null;
		  resources = classLoader.getResources("com/shareniu/activiti/learing/ch14/listener/applicationContext.xml");
		  while (resources.hasMoreElements()) {
		        URL resource = resources.nextElement();
		         applicationContext = new GenericXmlApplicationContext(new UrlResource(resource));
		          processEngine = applicationContext.getBean(ProcessEngine.class);
		          repositoryService = processEngine.getRepositoryService();
		 		 taskService = processEngine.getTaskService();
		 		 runtimeService = processEngine.getRuntimeService();
		 		 
		      }
	}
	@Test
	public void addInputStreamTest(){
		//定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader().getResourceAsStream("com/shareniu/activiti/learing/ch14/listener/2.bpmn");
		//流程定义的分类
		String category="variabletypeTest";
		//构造DeploymentBuilder对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().category(category).addInputStream("variabletype.bpmn", inputStream);
		//部署
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy);
		
	}
	@Test
	public void startProcessInstanceById() throws Exception{
		Map<String, Object> variables=new HashMap<String, Object>();
		//variables.put("a", new Shareniu("1", "shareniu1",18));
		variables.put("shareniu", 20);
		//variables.put("", new ShareniuDelegateExpression())
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById("myProcess:1:4",variables);
		System.out.println(startProcessInstanceById);
		//Thread.sleep(1000);
	}
	@Test
	public void a() throws Exception{
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("shareniu1111", 20);
		runtimeService.setVariableLocal("17504","loopCounter", 22222222);
		
		
		Thread.sleep(50000);
	}
}
