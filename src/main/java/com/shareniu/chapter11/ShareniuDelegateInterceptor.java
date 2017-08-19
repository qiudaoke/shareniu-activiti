package com.shareniu.chapter11;

import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.delegate.DelegateInvocation;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuDelegateInterceptor implements DelegateInterceptor {

	@Override
	public void handleInvocation(DelegateInvocation invocation)
			throws Exception {
		//调用的参数
		Object[] invocationParameters = invocation.getInvocationParameters();
		//调用之后函数返回的执行结果，通过调用invocation.proceed()之后才有值
		Object invocationResult = invocation.getInvocationResult();
		//执行的目标类
		Object target = invocation.getTarget();
		if (target instanceof TaskListener) {
			
		}else {
			//可以根据自己的业务对执行的监听类进行控制，如果不期望目标执行则不调用proceed函数即可
			invocation.proceed();
		}
		System.out.println("invocationParameters:"+invocationParameters+",invocationResult:"+
		invocationResult+",target:"+target);
	}

}
