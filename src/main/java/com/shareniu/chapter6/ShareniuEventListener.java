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
public class ShareniuEventListener implements ActivitiEventListener {

	@Override
	public void onEvent(ActivitiEvent event) {
		System.err.println(event+","+event.getType());
		switch (event.getType()) {
		case VARIABLE_CREATED:
			ActivitiVariableEvent variableEvent = (ActivitiVariableEvent) event;
			break;
		case VARIABLE_DELETED:
			break;
		case VARIABLE_UPDATED:
			break;
		case ENTITY_CREATED:
			ActivitiEventImpl activitiEventImpl=(ActivitiEventImpl) event;
			System.out.println("ENTITY_CREATED======="+activitiEventImpl.getExecutionId()+","+activitiEventImpl.getProcessDefinitionId());
			break;
		}
	}
	@Override
	public boolean isFailOnException() {
		//出线异常程序是否继续执行
		return false;
	}
}
