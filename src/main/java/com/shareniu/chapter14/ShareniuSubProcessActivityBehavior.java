package com.shareniu.chapter14;

import org.activiti.engine.impl.bpmn.behavior.SubProcessActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuSubProcessActivityBehavior extends SubProcessActivityBehavior {
	public void execute(ActivityExecution execution) throws Exception {
		ActivityExecution parent = execution.getParent();//获取流程实例
		ExecutionEntity entity=null;
		if (null!=parent) {
			String businessKey = parent.getProcessBusinessKey();//获取业务键
			 entity=(ExecutionEntity) execution;
			entity.setBusinessKey(businessKey);//设置子流程的业务键
		}
		super.execute(entity);//调用父类中的execute方法
	}
}
