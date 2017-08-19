package com.shareniu.chapter11;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Service;
@Service("bean")
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuExpress implements Serializable {
	public void doSomething(DelegateExecution execution) throws Exception {
		execution.setVariable("myVar", execution.getVariable("msg"));
		System.out.println("444444444444444444444444444444444");
	}
	public void doSomething1(Person person,String b,String cc) throws Exception {
		
		System.out.println("444444444444444444444444444444444");
	}
}
