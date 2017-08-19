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
public class InterceptorB  extends AbstractCommandInterceptor {

	@Override
	public <T> T execute(CommandConfig config, Command<T> command) {
		 // 输出字符串和命令  
        System.out.println("this is interceptor B "  
                + command.getClass().getName());  
        // 然后让责任链中的下一请求处理者处理命令  
        return next.execute(config,command);  
	}



}
