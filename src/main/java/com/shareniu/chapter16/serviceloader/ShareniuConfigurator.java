package com.shareniu.chapter16.serviceloader;

import org.activiti.engine.cfg.AbstractProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

public class ShareniuConfigurator extends AbstractProcessEngineConfigurator {
	public int getPriority() {
		return 1;
	}

	public void beforeInit(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		System.out.println("Shareniu:beforeInit");
	}

	public void configure(
			ProcessEngineConfigurationImpl processEngineConfiguration) {
		System.out.println("Shareniu:configure");
	}
}
