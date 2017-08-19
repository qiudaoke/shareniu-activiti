package com.shareniu.chapter14;

import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuUserTaskParseHandler extends UserTaskParseHandler {
	@Override
	protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
		super.executeParse(bpmnParse, userTask);//调用父类对userTask对象进行解析	
		Map<String, List<ExtensionAttribute>> attributes = userTask.getAttributes();
		//查找activityImpl对象
		ActivityImpl activityImpl = findActivity(bpmnParse, userTask.getId());
		//将扩展属性值设置到activityImpl对象中
		activityImpl.setProperty("shareniuExt", attributes);
	}
}
