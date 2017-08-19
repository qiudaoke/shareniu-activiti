package com.shareniu.chapter6;

import org.activiti.engine.impl.event.logger.EventLogger;
import org.activiti.engine.impl.event.logger.EventLoggerListener;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class MyEventLoggerListener implements EventLoggerListener {

	@Override
	public void eventsAdded(EventLogger databaseEventLogger) {
		System.out.println(databaseEventLogger);
	}

}
