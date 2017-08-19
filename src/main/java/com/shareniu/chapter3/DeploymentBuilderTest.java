package com.shareniu.chapter3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class DeploymentBuilderTest {
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
						"com/shareniu/chapter3/activiti.cfg.xml")
				.buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		identityService = processEngine.getIdentityService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
	}

	@Test
	public void taskService() {
	}

	@Test
	public void zip() {
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResourceAsStream("1.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deploy = repositoryService.createDeployment()
				.addZipInputStream(zipInputStream).deploy();
		identityService.setAuthenticatedUserId("shareniu");
	}

	@Test
	public void addInputStreamTest() throws IOException {
		// 定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResource("com/shareniu/chapter3/a.bpmn").openStream();
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
	public void activateProcessDefinitionById() {
		repositoryService.activateProcessDefinitionById("myProcess:13:37504",
				true, null);

	}

	@Test
	public void addClasspathResource() throws InterruptedException {
		ProcessEngineConfigurationImpl processEngineConfiguration = Context
				.getProcessEngineConfiguration();
		System.out.println(processEngineConfiguration);
		String resource = "com/shareniu/chapter3/common.bpmn";
		// 流程定义的分类
		String category = "shareniu_addClasspathResource";
		// 构造DeploymentBuilder对象
		Date date = new Date();
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment()
				// .activateProcessDefinitionsOn(date)
				.addClasspathResource(resource).category(category);
		// 部署
		processEngineConfiguration = Context.getProcessEngineConfiguration();
		System.out.println(processEngineConfiguration);
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy);
		// Thread.sleep(10000);

	}

	@Test
	public void addString() {
		String resource = "shareniu_addString.bpmn";
		// 读取文件获取文件中定义的xml信息
		String text = readTxtFile("E:/activitilearing/activiti/src/main/java/com/shareniu/chapter3/common.bpmn");
		// 构造DeploymentBuilder对象
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment().addString(resource, text);
		// 部署
		Deployment deploy = deploymentBuilder.deploy();
	}

	@Test
	public void testProcessValidator() {
		BpmnModel bpmnModel = getBpmnModel();
		
		Process process = bpmnModel.getProcesses().get(0);
		process.getFlowElement(flowElementId)
		ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
		ProcessValidator defaultProcessValidator = processValidatorFactory
				.createDefaultProcessValidator();
		List<ValidationError> validate = defaultProcessValidator
				.validate(bpmnModel);
		System.out.println(validate.size());
	}

	@Test
	public void testConvertToXML() throws Exception {
		BpmnModel bpmnModel = getBpmnModel();
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		String bpmn20Xml = new String(bpmnXMLConverter.convertToXML(bpmnModel),
				"UTF-8");
		System.out.println(bpmn20Xml);
		bpmnModel.getProcesses().get(0);
	}
	@Test
	public void testParseDelimitedList() throws Exception {
		List<String> parseDelimitedList = parseDelimitedList("${ccc}");
		System.out.println(parseDelimitedList);
	}

	public static List<String> parseDelimitedList(String s) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isNotEmpty(s)) {

			StringCharacterIterator iterator = new StringCharacterIterator(s);
			char c = iterator.first();

			StringBuilder strb = new StringBuilder();
			boolean insideExpression = false;

			while (c != StringCharacterIterator.DONE) {
				if (c == '{' || c == '$') {
					insideExpression = true;
				} else if (c == '}') {
					insideExpression = false;
				} else if (c == ',' && !insideExpression) {
					result.add(strb.toString().trim());
					strb.delete(0, strb.length());
				}

				if (c != ',' || (insideExpression)) {
					strb.append(c);
				}

				c = iterator.next();
			}

			if (strb.length() > 0) {
				result.add(strb.toString().trim());
			}

		}
		return result;
	}

	@Test
	public void testConvertToBpmnModel() throws Exception {
		String resource = "com/shareniu/chapter3/common.bpmn";
		InputStream xmlStream = this.getClass().getClassLoader()
				.getResourceAsStream(resource);
		InputStreamSource xmlSource = new InputStreamSource(xmlStream);
		ShareniuBpmnXMLConverter bpmnXMLConverter = new ShareniuBpmnXMLConverter();
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSource,
				true, false, "UTF-8");
		// definitionsAttributes
		Map<String, List<ExtensionAttribute>> definitionsAttributes = bpmnModel
				.getDefinitionsAttributes();
		Set<Entry<String, List<ExtensionAttribute>>> entrySet = definitionsAttributes
				.entrySet();
		for (Entry<String, List<ExtensionAttribute>> entry : entrySet) {
			System.out.println(entry.getKey() + "---->>>>" + entry.getValue());
		}
		System.out.println(bpmnModel);
	}

	private BpmnModel getBpmnModel() {
		// 注意所有的setId中的id必须是全局唯一的不能重复
		// 2.setSourceRef和setTargetRef中的值必须是id值，不是name值
		// 流程的定义是：开始节点(id:start1,name:开始节点)-->连线1(id:flow1,name:开始节点->任务节点1)
		// 任务节点1(id:userTask1,name:任务节点1)->连线2(id:flow2,name:任务节点1->任务节点2)
		// 任务节点2(id:userTask2,name:任务节点2)->连线3(id:flow3,name:任务节点2->结束节点)结束节点(id:endEvent,name:结束节点)
		// 开始节点->任务节点1
		SequenceFlow flow1 = new SequenceFlow();
		flow1.setId("flow1");
		flow1.setName("开始节点->任务节点1");
		flow1.setSourceRef("start1");
		flow1.setTargetRef("userTask1");
		// 任务节点1->任务节点2
		SequenceFlow flow2 = new SequenceFlow();
		flow2.setId("flow2");
		flow2.setName("任务节点1->任务节点2");
		flow2.setSourceRef("userTask1");
		flow2.setTargetRef("userTask2");

		// 任务节点1->任务节点2
		SequenceFlow flow3 = new SequenceFlow();
		flow3.setId("flow3");
		flow3.setName("任务节点2->结束节点");
		flow3.setSourceRef("userTask2");
		flow3.setTargetRef("endEvent");

		String resource = "shareniu_addBpmnModel";
		// 声明BpmnModel对象
		BpmnModel bpmnModel = new BpmnModel();
		// 声明Process对象 一个BpmnModel可以包含多个Process对象
		Process process = new Process();
		process.setId("process1");
		// 开始节点的封装
		StartEvent start = new StartEvent();
		start.setName("开始节点");
		start.setId("start1");
		start.setOutgoingFlows(Arrays.asList(flow1));
		// 任务节点1
		UserTask userTask1 = new UserTask();
		userTask1.setName("任务节点1");
		userTask1.setId("userTask1");
		userTask1.setIncomingFlows(Arrays.asList(flow1));
		userTask1.setOutgoingFlows(Arrays.asList(flow2));
		// 任务节点2
		UserTask userTask2 = new UserTask();
		userTask2.setName("任务节点2");
		userTask2.setId("userTask2");
		userTask2.setIncomingFlows(Arrays.asList(flow2));
		userTask2.setOutgoingFlows(Arrays.asList(flow3));

		// 结束节点
		EndEvent endEvent = new EndEvent();
		endEvent.setName("结束节点");
		endEvent.setId("endEvent");
		endEvent.setIncomingFlows(Arrays.asList(flow3));
		// 将所有的FlowElement添加到process中
		process.addFlowElement(start);
		process.addFlowElement(flow1);
		process.addFlowElement(userTask1);
		process.addFlowElement(flow2);
		process.addFlowElement(userTask2);
		process.addFlowElement(flow3);
		process.addFlowElement(endEvent);
		bpmnModel.addProcess(process);
		return bpmnModel;
	}

	@Test
	public void addBpmnModel() throws UnsupportedEncodingException {
		// 注意所有的setId中的id必须是全局唯一的不能重复
		// 2.setSourceRef和setTargetRef中的值必须是id值，不是name值
		// 流程的定义是：开始节点(id:start1,name:开始节点)-->连线1(id:flow1,name:开始节点->任务节点1)
		// 任务节点1(id:userTask1,name:任务节点1)->连线2(id:flow2,name:任务节点1->任务节点2)
		// 任务节点2(id:userTask2,name:任务节点2)->连线3(id:flow3,name:任务节点2->结束节点)结束节点(id:endEvent,name:结束节点)
		// 开始节点->任务节点1
		SequenceFlow flow1 = new SequenceFlow();
		flow1.setId("flow1");
		flow1.setName("开始节点->任务节点1");
		flow1.setSourceRef("start1");
		flow1.setTargetRef("userTask1");
		// 任务节点1->任务节点2
		SequenceFlow flow2 = new SequenceFlow();
		flow2.setId("flow2");
		flow2.setName("任务节点1->任务节点2");
		flow2.setSourceRef("userTask1");
		flow2.setTargetRef("userTask2");

		// 任务节点1->任务节点2
		SequenceFlow flow3 = new SequenceFlow();
		flow3.setId("flow3");
		flow3.setName("任务节点2->结束节点");
		flow3.setSourceRef("userTask2");
		flow3.setTargetRef("endEvent");

		String resource = "shareniu_addBpmnModel";
		// 声明BpmnModel对象
		BpmnModel bpmnModel = new BpmnModel();
		// 声明Process对象 一个BpmnModel可以包含多个Process对象
		Process process = new Process();
		process.setId("process1");
		// 开始节点的封装
		StartEvent start = new StartEvent();
		start.setName("开始节点");
		start.setId("start1");
		start.setOutgoingFlows(Arrays.asList(flow1));
		// 任务节点1
		UserTask userTask1 = new UserTask();
		userTask1.setName("任务节点1");
		userTask1.setId("userTask1");
		userTask1.setIncomingFlows(Arrays.asList(flow1));
		userTask1.setOutgoingFlows(Arrays.asList(flow2));
		// 任务节点2
		UserTask userTask2 = new UserTask();
		userTask2.setName("任务节点2");
		userTask2.setId("userTask2");
		userTask2.setIncomingFlows(Arrays.asList(flow2));
		userTask2.setOutgoingFlows(Arrays.asList(flow3));
		// 结束节点
		EndEvent endEvent = new EndEvent();
		endEvent.setName("结束节点");
		endEvent.setId("endEvent");
		endEvent.setIncomingFlows(Arrays.asList(flow3));
		// 将所有的FlowElement添加到process中
		process.addFlowElement(start);
		process.addFlowElement(flow1);
		process.addFlowElement(userTask1);
		process.addFlowElement(flow2);
		process.addFlowElement(userTask2);
		process.addFlowElement(flow3);
		process.addFlowElement(endEvent);
		bpmnModel.addProcess(process);
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		String bpmn20Xml = new String(bpmnXMLConverter.convertToXML(bpmnModel),
				"UTF-8");
		System.out.println(bpmn20Xml);

		// DeploymentBuilder deploymentBuilder =
		// repositoryService.createDeployment().addBpmnModel(resource,
		// bpmnModel);
		// Deployment deploy = deploymentBuilder.deploy();
	}

	/**
	 * 根据文件的路径获取文件中的内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readTxtFile(String filePath) {
		StringBuffer stringBuffer = new StringBuffer();
		InputStreamReader read = null;
		try {
			String encoding = "UTF-8";// UTF-8编码
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file),
						encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					stringBuffer.append(lineTxt);
				}
				return stringBuffer.toString();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		} finally {
			try {
				read.close();
			} catch (IOException e) {
			}
		}
		return "";
	}

}
