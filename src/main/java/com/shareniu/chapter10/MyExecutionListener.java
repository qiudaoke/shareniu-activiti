package com.shareniu.chapter10;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class MyExecutionListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -139603646293616249L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println(execution.getEventName());
	}

}
