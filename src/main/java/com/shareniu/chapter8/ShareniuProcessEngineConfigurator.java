package com.shareniu.chapter8;

import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuProcessEngineConfigurator implements ProcessEngineConfigurator  {
	ShareniuStandaloneProcessEngineConfiguration a;
	ShareniuProcessDefinitionInfoCache cache;
	@Override
	public void beforeInit(
			ProcessEngineConfigurationImpl pc) {
	a=(ShareniuStandaloneProcessEngineConfiguration)pc;
		 cache=new ShareniuProcessDefinitionInfoCache(null);
		a.setProcessDefinitionInfoCache(cache);
	}

	@Override
	public void configure(
			ProcessEngineConfigurationImpl pc) {
		 a=(ShareniuStandaloneProcessEngineConfiguration)pc;
		 if (cache==null) cache=new ShareniuProcessDefinitionInfoCache(a.getCommandExecutor());
		 cache.setCommandExecutor(a.getCommandExecutor());
		a.setProcessDefinitionInfoCache(cache);
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
