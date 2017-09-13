package com.shareniu.chapter16.identity;

import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.task.TaskDefinition;

public class ShareniuActivityBehaviorFactory extends
		DefaultActivityBehaviorFactory {
	

	public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask u,
			TaskDefinition t) {
		return new ShareniuUserTaskActivityBehavior(u.getId(), t);
	}
}
