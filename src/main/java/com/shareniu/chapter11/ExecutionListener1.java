package com.shareniu.chapter11;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
//类
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ExecutionListener1 implements ExecutionListener {
	@Autowired
	private Person person;
	private int i=100;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println(execution);
	}

}
