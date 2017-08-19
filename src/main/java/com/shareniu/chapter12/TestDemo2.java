package com.shareniu.chapter12;

import static org.junit.Assert.assertNotNull;

import org.activiti.engine.impl.pvm.ProcessDefinitionBuilder;
import org.activiti.engine.impl.pvm.PvmExecution;
import org.activiti.engine.impl.pvm.PvmProcessDefinition;
import org.activiti.engine.impl.pvm.PvmProcessInstance;
import org.junit.Test;

import com.shareniu.activiti.learing.ch14.pvm.WaitState;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class TestDemo2 {
	@Test
	public void pvmControll(){
		PvmProcessDefinition processDefinition=new ProcessDefinitionBuilder()
				.createActivity("a").initial().behavior(new WaitState()).transition("b").endActivity()
				.createActivity("b").behavior(new WaitState()).transition("c").endActivity()
				.createActivity("c").behavior(new WaitState()).endActivity().buildProcessDefinition();
		PvmProcessInstance processInstance=processDefinition.createProcessInstance();
		processInstance.start();
		PvmExecution activityInstance=processInstance.findExecution("a");//activitiId
		assertNotNull(activityInstance);
		//信号怎么样触发的？？？需要出入什么参数触发
		activityInstance.signal(null,null);
		//触发之后如何流向b节点，b节点怎样变成avtiviti的了呢？？？
		activityInstance=processInstance.findExecution("b");
		assertNotNull(activityInstance);
		activityInstance.signal(null, null);
		activityInstance=processInstance.findExecution("c");
		assertNotNull(activityInstance);
		
	}
}
