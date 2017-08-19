package com.shareniu.chapter11;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.task.TaskDefinition;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuUserTaskParserHandler  extends UserTaskParseHandler{
	@Override
	protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
		super.executeParse(bpmnParse, userTask);
		TaskDefinition taskDefinition = (TaskDefinition) bpmnParse
                .getCurrentActivity().getProperty(PROPERTY_TASK_DEFINITION);
        ActivitiListener activitiListener = new ActivitiListener();
        activitiListener.setEvent(TaskListener.EVENTNAME_CREATE);
        activitiListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        activitiListener.setImplementation("#{shareniuTaskListener}");
        taskDefinition
                .addTaskListener(TaskListener.EVENTNAME_CREATE, bpmnParse
                        .getListenerFactory()
                        .createDelegateExpressionTaskListener(activitiListener));
	}
}
