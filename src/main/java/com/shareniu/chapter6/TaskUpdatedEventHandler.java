package com.shareniu.chapter6;

import org.activiti.engine.impl.event.logger.handler.AbstractTaskEventHandler;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntity;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class TaskUpdatedEventHandler extends AbstractTaskEventHandler {

	@Override
	public EventLogEntryEntity generateEventLogEntry(
			CommandContext commandContext) {
		return null;
	}

}
