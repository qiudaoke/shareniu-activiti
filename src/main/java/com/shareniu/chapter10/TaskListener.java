package com.shareniu.chapter10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class TaskListener implements org.activiti.engine.delegate.TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		ExecutionEntity ex = (ExecutionEntity) delegateTask.getExecution();
		// 根据执行对象获取所有的虚拟机对象
		Map<String, Object> properties = ex.getActivity().getProperties();
		// 从集合中获取自定义的属性信息
		Map<String, Object> map = (Map<String, Object>) properties
				.get(ExtensionBpmnConstants.PROPERTY_OPERATIONS_DEFINITION);
		for (Object obj : map.keySet()) {// 这里的获取与前面的存储过程相吻合
			ExtensionOperation value = (ExtensionOperation) map.get(obj);
			String val = value.getPropeies().get("transferTo");// 获取transferTo属性值
			Collection<String> candidateUsers = new ArrayList<>();
			candidateUsers.add(val);
			delegateTask.addCandidateUsers(candidateUsers);// 属性值作为当前任务的处理人
		}
	}

}
