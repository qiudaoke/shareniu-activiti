package com.shareniu.chapter9.job;

import java.util.Date;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TimerEntity;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class AutoStartProcessCmd  implements Command<Void>{
	
	private Date target;
	private String processKey;
	public AutoStartProcessCmd(Date target, String processKey) {
		this.target = target;
		this.processKey = processKey;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		// 时间事件声明
		TimerEntity timer = new TimerEntity();
		timer.setDuedate(target);
		timer.setExclusive(true);
		timer.setJobHandlerConfiguration(processKey);// 这里存入需要启动的流程key
		timer.setJobHandlerType(ShareniuTimerStartEventJobHandler.TYPE);
		// 保存作业事件
		Context.getCommandContext().getJobEntityManager().schedule(timer);
		return null;
	}

}
