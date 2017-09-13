package com.shareniu.chapter16.png;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.image.ProcessDiagramGenerator;
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
	public void testPng() throws IOException {

		BpmnModel bpmnModel = repositoryService.getBpmnModel("extensionOperationProcess:1:57504");
		Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration());
		ProcessDiagramGenerator pdg = processEngine
				.getProcessEngineConfiguration().getProcessDiagramGenerator();
		List<String> list=new ArrayList<>();
		list.add("usertask3");
		List<String> list1=new ArrayList<>();
		list1.add("flow1");
		list1.add("flow8");
		InputStream is = pdg.generateDiagram(bpmnModel, "png",list,list1);
		FileOutputStream fos = new FileOutputStream("E:/a/1.png");
		byte[] b = new byte[1024];
		while((is.read(b)) != -1){
		fos.write(b);
		}
		is.close();
		fos.close();
	}

}
