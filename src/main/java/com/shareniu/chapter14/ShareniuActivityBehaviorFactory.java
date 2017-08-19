package com.shareniu.chapter14;

import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.SubProcessActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuActivityBehaviorFactory extends
		DefaultActivityBehaviorFactory {
	// 重写父类中排他网关的分支条件行为类
	public ExclusiveGatewayActivityBehavior createExclusiveGatewayActivityBehavior(
			ExclusiveGateway exclusiveGateway) {
		return new ShareniuExclusiveGatewayActivityBehaviorExt();
	}

	public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask u,
			TaskDefinition t) {
		return new ShareniuUserTaskActivityBehaviorExt(u.getId(), t);
	}
	@Override
	public SubProcessActivityBehavior createSubprocActivityBehavior(
			SubProcess subProcess) {
		return new ShareniuSubProcessActivityBehavior();
	}
}
