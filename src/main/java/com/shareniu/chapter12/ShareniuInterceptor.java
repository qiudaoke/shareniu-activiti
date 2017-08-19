package com.shareniu.chapter12;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuInterceptor extends AbstractCommandInterceptor {

	@Override
	public <T> T execute(CommandConfig config, Command<T> command) {
		long start=System.currentTimeMillis();
		try {
			System.out.println("需要执行的命令 "
					+ command.getClass().getName());
			// 然后让责任链中的下一请求处理者处理命令
			return next.execute(config, command);
		} finally {
			long end=System.currentTimeMillis();
			System.out.println(end-start);
		}
	}

}
