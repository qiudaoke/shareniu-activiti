package com.shareniu.chapter2;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineInfo;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.ReflectUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.UrlResource;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ProcessEnginesTest {
	@Test
	public void getDefaultProcessEngine() {
		ProcessEngine  processEngine = ProcessEngines.getDefaultProcessEngine();
		ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService2 = processEngine.getRepositoryService();
		System.out.println(repositoryService2);
		List<ProcessEngineInfo> processEngineInfos = ProcessEngines.getProcessEngineInfos();
		for (ProcessEngineInfo processEngineInfo : processEngineInfos) {
			System.out.println(processEngineInfo.getResourceUrl()+"-->>"+processEngineInfo.getName());
		}
	}
	@Test
	public void genericXmlApplicationContext() throws Exception {
		 ClassLoader classLoader = ReflectUtil.getClassLoader();
		 Enumeration<URL> resources = null;
		  resources = classLoader.getResources("activiti-context.xml");
		  while (resources.hasMoreElements()) {
		        URL resource = resources.nextElement();
		        ApplicationContext applicationContext = new GenericXmlApplicationContext(new UrlResource(resource));
		      }
		
	}
	@Test
	public void testShareniuLifecycleListener() throws Exception {
		
		ProcessEngineConfiguration pc = ProcessEngineConfiguration.
				createProcessEngineConfigurationFromResource("com/shareniu/chapter2/lifecyclelistener/activiti.cfg.xml");
		ProcessEngine buildProcessEngine = pc.buildProcessEngine();
		System.err.println("---->>>>"+buildProcessEngine);
	} 
}
