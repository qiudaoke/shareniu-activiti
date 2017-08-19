package com.shareniu.chapter10;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
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
public class ExtensionUserTaskParseHandler extends UserTaskParseHandler {
	@Override
	protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
		super.executeParse(bpmnParse, userTask);
		ActivityImpl activity  = bpmnParse.getCurrentScope().findActivity(userTask.getId());
		Map<String, ExtensionOperation> operationMap = parseUserTaskOperations(bpmnParse, userTask);
        //将扩展属性设置给activity
        activity.setProperty(ExtensionBpmnConstants.PROPERTY_OPERATIONS_DEFINITION, operationMap);
        String id = activity.getProcessDefinition().getId();
        System.out.println(id);
	}

	 public Map<String, ExtensionOperation> parseUserTaskOperations(BpmnParse bpmnParse, UserTask userTask) {
	        Map<String, ExtensionOperation> operationMap = new HashMap<String, ExtensionOperation>();
	        //获取扩展属性标签元素
	        List<ExtensionElement> operationsElements = userTask.getExtensionElements().get(ExtensionBpmnConstants.EXTENSION_ELEMENT_OPERATIONS);
	       
	        if (operationsElements==null||operationsElements.size()==0) {
			}else {
				ExtensionElement   operationsElement=operationsElements.get(0);
		        if (operationsElement != null) {
		        	Collection<List<ExtensionElement>> values = operationsElement.getChildElements().values();
		           
		        	Iterator<List<ExtensionElement>> iterator = values.iterator();
		        	while (iterator.hasNext()) {
						List<org.activiti.bpmn.model.ExtensionElement> list = (List<org.activiti.bpmn.model.ExtensionElement>) iterator
								.next();
						for (ExtensionElement operationElement :list) {
			                ExtensionOperation userTaskOperation = new ExtensionOperation(operationElement.getName());
			 
			                if (operationElement != null && !operationElement.getAttributes().isEmpty()) {
			                	Iterator<List<ExtensionAttribute>> values2 = operationElement.getAttributes().values().iterator();
			                	while (values2.hasNext()) {
									List<ExtensionAttribute> list2 = values2.next();
									for (ExtensionAttribute attributeElement : list2) {
				                        userTaskOperation.addProperty(attributeElement.getName(), attributeElement.getValue());
				                        operationMap.put(operationElement.getName(), userTaskOperation);
									}
								}
			                    
			                }
			                
			            }
					}
		        	
		        	
		        }
			}
	        
	        
	 
	        return operationMap;
	    }
	

}
