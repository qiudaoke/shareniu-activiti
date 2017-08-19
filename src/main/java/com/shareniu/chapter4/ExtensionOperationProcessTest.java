package com.shareniu.chapter4;

import java.io.InputStream;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.impl.util.io.ResourceStreamSource;
import org.activiti.engine.impl.util.io.StreamSource;
import org.junit.Before;
import org.junit.Test;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ExtensionOperationProcessTest {
	// 获取到Activiti ProcessEngine
	ProcessEngine processEngine = null;
	// 获取RepositoryService 实例对象
	RepositoryService repositoryService = null;
	// 资源名称
	String resourceName = "shareniu_addInputStream.bpmn";
	RuntimeService runtimeService = null;
	IdentityService identityService = null;
	TaskService taskService = null;
	HistoryService historyService;

	@Before
	public void init() {
		processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"com/shareniu/chapter4/activiti.cfg.xml")
				.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		identityService = processEngine.getIdentityService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
	}

	@Test
	public void convertToBpmnModel() {
		// 获取流程文档数据流
		InputStream xmlStream = this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"com/shareniu/chapter4/oneTaskProcess.bpmn20.xml");
		StreamSource xmlSource = new InputStreamSource(xmlStream);
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSource,
				false, false, "UTF-8");
	}
	@Test
	public void testResourceStreamSource() {
		StreamSource xmlSource = new ResourceStreamSource("com/shareniu/chapter4/oneTaskProcess.bpmn20.xml");
		InputStream inputStream = xmlSource.getInputStream();
		System.out.println(inputStream);
	}

}
