package com.shareniu.chapter6;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionInfoCache;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class MyStandaloneProcessEngineConfiguration extends
		StandaloneProcessEngineConfiguration {
	protected void initDatabaseEventLogging() {
		if (enableDatabaseEventLogging) {
			getEventDispatcher().addEventListener(
					new ShareniuEventLogger(clock, objectMapper));
		}
	}
}
