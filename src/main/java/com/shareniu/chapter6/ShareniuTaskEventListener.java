package com.shareniu.chapter6;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEventImpl;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuTaskEventListener implements ActivitiEventListener {

	@Override
	public void onEvent(ActivitiEvent event) {
		System.err.println("ssssssssssssssssssssssssssssssssss"+event+","+event.getType());
		
	}

	@Override
	public boolean isFailOnException() {
		//该值决定了 出现异常的容忍度
		return true;
	}

}
