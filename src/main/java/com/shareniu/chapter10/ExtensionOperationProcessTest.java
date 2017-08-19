package com.shareniu.chapter10;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import com.shareniu.activiti.learing.ch3.DeploymentBuilderTest;
import com.shareniu.chapter9.App;
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
	IdentityService identityService;
	RuntimeService runtimeService;
	TaskService taskService;
	ActivitiEventDispatcher eventDispatcher;

	@Before
	public void init() {
		InputStream in = App.class.getClassLoader().getResourceAsStream(
				"com/shareniu/chapter10/activiti.cfg.xml");
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
	public void addInputStreamTest() {
		// 定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class.getClassLoader()
				.getResourceAsStream("com/shareniu/chapter10/pvm.bpmn20.xml");
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
	public void listenerInputStreamTest() {
		// 定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class
				.getClassLoader()
				.getResourceAsStream(
						"com/shareniu/activiti/learing/ch6/listener.bpmn20.xml");
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
	public void multiInstanceLoopCharacteristicsInputStreamTest() {
		// 定义的文件信息的流读取
		InputStream inputStream = DeploymentBuilderTest.class
				.getClassLoader()
				.getResourceAsStream(
						"com/shareniu/activiti/learing/ch6/multiInstanceLoopCharacteristics.bpmn20.xml");
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
	public void startProcessInstanceById() {
		runtimeService
				.startProcessInstanceById("extensionOperationProcess:2:62504");
	}

	@Test
	public void pvm1() {
		String processDefinitionId = "extensionOperationProcess:1:3";
		ProcessEngineConfigurationImpl pec = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
		DeploymentManager deploymentManager = pec.getDeploymentManager();
		DeploymentCache<ProcessDefinitionEntity> processDefinitionCache = deploymentManager.getProcessDefinitionCache();
		ProcessDefinitionEntity pde = processDefinitionCache.get(processDefinitionId);
	
		if (pde==null) {
			pde = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(processDefinitionId);
		}
		System.out.println(pde);
		// 获取流程定义实体对象
		/*ProcessDefinitionEntity */
		// 节点的名称
		String taskDefKey = "operationTask";
		ActivityImpl currActiviti =  pde.findActivity(taskDefKey);//当前活动节点
		// 获取TaskDefinition实例对象
		TaskDefinition task = (TaskDefinition) currActiviti
				.getProperty("taskDefinition"); // 获取流程引擎配置类实例对象
		ProcessEngineConfigurationImpl conf = (ProcessEngineConfigurationImpl) processEngine
				.getProcessEngineConfiguration();
		// 创建表达式
		Expression expression = conf.getExpressionManager().createExpression(
				"shareniu");
		// 将表达式设置到任务节点中
		task.setNameExpression(expression);
		processDefinitionCache.add(processDefinitionId, pde);
		runtimeService.startProcessInstanceById(processDefinitionId);
	}
	@Test
	public void SimpleContext() {
		String processDefinitionId="extensionOperationProcess:6:92503";
		ProcessDefinitionEntity pdf = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		List<ActivityImpl> activities = pdf.getActivities();
		for (ActivityImpl activityImpl : activities) {
			if (activityImpl.getId().equals("operationTask")) {
				List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
				PvmTransition destination = outgoingTransitions.get(0);				
				Object conditionText = destination.getProperty("conditionText");					
				Condition expressionCondition = (Condition) destination.getProperty("condition");	
				System.out.println(conditionText+","+expressionCondition);
				ExpressionFactory factory = new ExpressionFactoryImpl();  
		        SimpleContext context = new SimpleContext();  
		        context.setVariable("shareniu", factory.createValueExpression(10000, String.class));   
		        ValueExpression e = factory.createValueExpression(context, (String) conditionText, boolean.class);  
		        System.out.println(e.getValue(context));  
			}
		}
	}
	@Test
	public void findActivities() {
		String processDefinitionId = "extensionOperationProcess:1:3";
		ProcessDefinitionEntity pdf = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		List<ActivityImpl> activities = pdf.getActivities();
		for (ActivityImpl activityImpl : activities) {
			Object object = activityImpl.getProperties().get(
					ExtensionBpmnConstants.PROPERTY_OPERATIONS_DEFINITION);
			if (object instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) object;
				for (Object obj : map.keySet()) {
					ExtensionOperation value = (ExtensionOperation) map
							.get(obj);
				}
			}
			if (activityImpl.getId().equals("operationTask")) {
				// 获取operationTask节点的入线
				List<PvmTransition> incomingTransitions = activityImpl
						.getIncomingTransitions();
				System.out.println(incomingTransitions.get(0).getId());
				// 获取operationTask节点的出线
				List<PvmTransition> outgoingTransitions = activityImpl
						.getOutgoingTransitions();
				for (PvmTransition pvmTransition : outgoingTransitions) {
				}
			}
		}
	}
}
