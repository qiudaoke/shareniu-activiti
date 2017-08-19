package com.shareniu.chapter6;

import org.activiti.engine.impl.event.logger.AbstractEventFlusher;
import org.activiti.engine.impl.event.logger.handler.EventLoggerEventHandler;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntity;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityManager;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class KafkaEventFlusher extends AbstractEventFlusher {
	
	@Override
	public void closing(CommandContext c) {
		EventLogEntryEntityManager eventLogEntryEntityManager = c.getEventLogEntryEntityManager();
		for (EventLoggerEventHandler eventHandler : eventHandlers) {
			try {
				EventLogEntryEntity generateEventLogEntry = eventHandler.generateEventLogEntry(c);
				//TODO
				System.err.println(generateEventLogEntry);
			} catch (Exception e) {
			}
		}
	}

}
