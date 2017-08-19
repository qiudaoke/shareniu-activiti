package com.shareniu.chapter9.job;

import org.activiti.engine.impl.jobexecutor.TimerStartEventJobHandler;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuTimerStartEventJobHandler extends TimerStartEventJobHandler {
	 public static final String TYPE = "shareniu-timer-start-event";

	  public String getType() {
	    return TYPE;
	  }
}
