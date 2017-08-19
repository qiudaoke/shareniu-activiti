package com.shareniu.chapter8;

import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionInfoCache;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuStandaloneProcessEngineConfiguration extends
		StandaloneProcessEngineConfiguration {
	// 设置processDefinitionInfoCache对象的值
	public void setProcessDefinitionInfoCache(ProcessDefinitionInfoCache cache) {
		super.processDefinitionInfoCache = cache;
	}
}
