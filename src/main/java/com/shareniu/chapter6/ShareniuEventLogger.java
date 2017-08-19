package com.shareniu.chapter6;

import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.event.logger.EventFlusher;
import org.activiti.engine.impl.event.logger.EventLogger;
import org.activiti.engine.runtime.Clock;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuEventLogger  extends EventLogger{
	@Override
	protected EventFlusher createEventFlusher() {
		KafkaEventFlusher kafkaEventFlusher = new KafkaEventFlusher();
		return kafkaEventFlusher;
	}
	public ShareniuEventLogger(Clock clock, ObjectMapper objectMapper) {
		super();
		this.clock = clock;
		this.objectMapper = objectMapper;
		/*List<EventLoggerListener> listeners=new ArrayList<EventLoggerListener>();
		listeners.add(new MyEventLoggerListener());
		setListeners(listeners);*/
	}
	@Override
	protected void initializeDefaultHandlers() {
		super.initializeDefaultHandlers();
		addEventHandler(ActivitiEventType.ENTITY_UPDATED, TaskUpdatedEventHandler.class);
	}
}
