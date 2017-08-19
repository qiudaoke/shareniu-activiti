package com.shareniu.chapter1;

import static org.junit.Assert.assertNotNull;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ProcessEnginesTest {
	@Test
	public void getDefaultProcessEngine() {
		/*ProcessEngineConfiguration p1 = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com/shareniu/chapter1/activiti.cfg.xml", "processEngineConfiguration");
		p1.buildProcessEngine();
		ProcessEngineConfiguration p2 = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com/shareniu/chapter1/activiti1.cfg.xml", "processEngineConfiguration");
		 p2.buildProcessEngine();*/
	
		
		ProcessEngine  pe = ProcessEngines.getDefaultProcessEngine();
		System.out.println(pe);
		assertNotNull("not null", pe); 
		/* HashMap<String,Integer> map = new HashMap<String, Integer>();  
	        map.put("a", 4);  
	        map.put("b", 2);  
	        map.put("c", 3);  
	        Integer next = map.values().iterator().next();
	        System.out.println(next);*/
		
	}
	
}
