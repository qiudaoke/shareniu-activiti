package com.shareniu.chapter11;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
@Service
@Transactional //委托表达式
public class A  implements TaskListener,ExecutionListener {
	@Autowired
	private Person person;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		System.err.println(person);
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
System.out.println(execution);		
	}
	
}
