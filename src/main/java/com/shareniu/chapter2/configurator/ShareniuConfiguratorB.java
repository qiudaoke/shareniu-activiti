package com.shareniu.chapter2.configurator;

import org.activiti.engine.cfg.AbstractProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuConfiguratorB extends AbstractProcessEngineConfigurator {
	public int getPriority() {
		return 2;
	}
	public void beforeInit(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		System.out.println("B:beforeInit");
		processEngineConfiguration.setDatabaseSchemaUpdate("false");
	}
	public void configure(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		System.out.println("B:configure");
	}
}
