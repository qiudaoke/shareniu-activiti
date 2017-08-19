package com.shareniu.chapter2.configurator;

import org.activiti.engine.cfg.AbstractProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuConfiguratorA extends AbstractProcessEngineConfigurator {
	public int getPriority() {
		return 1;
	}
	public void beforeInit(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		System.out.println("A:beforeInit");
		processEngineConfiguration.setDatabaseSchemaUpdate("true");
	}
	public void configure(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		System.out.println("A:configure");
	}
}
