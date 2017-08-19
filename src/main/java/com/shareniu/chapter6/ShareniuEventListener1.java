package com.shareniu.chapter6;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuEventListener1 implements ActivitiEventListener {

	@Override
	public void onEvent(ActivitiEvent event) {
		System.err.println(event+","+event.getType());
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

}
