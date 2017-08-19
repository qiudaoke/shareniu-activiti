package com.shareniu.chapter2.lifecyclelistener;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuLifecycleListener implements
		ProcessEngineLifecycleListener {
	public void onProcessEngineBuilt(ProcessEngine processEngine) {
		System.out.println(processEngine);// 获取processEngine对象并输出
		processEngine.getProcessEngineConfiguration().setActivityFontName("宋体");
		String activityFontName = processEngine.getProcessEngineConfiguration()
				.getActivityFontName();
	
		if (activityFontName == "Arial") {
			throw new RuntimeException("字体设置的不对");
		}
		
		System.err.println(activityFontName);// Arial
	}

	public void onProcessEngineClosed(ProcessEngine processEngine) {
		System.out.println(processEngine);// 获取processEngine对象并输出
	}
}
