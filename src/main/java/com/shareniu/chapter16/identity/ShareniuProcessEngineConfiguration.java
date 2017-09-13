package com.shareniu.chapter16.identity;

import java.io.InputStream;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class ShareniuProcessEngineConfiguration extends StandaloneProcessEngineConfiguration {
	@Override
	protected InputStream getMyBatisXmlConfigurationSteam() {
		return getResourceAsStream("com/shareniu/chapter16/identity/mappings.xml");
	}
}
