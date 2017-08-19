package com.shareniu.chapter14;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.SkipExpressionUtil;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuUserTaskActivityBehaviorExt extends
		UserTaskActivityBehavior {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShareniuUserTaskActivityBehaviorExt.class);

	public ShareniuUserTaskActivityBehaviorExt(String userTaskId,
			TaskDefinition t) {
		super(userTaskId, t);

	}

	@Override
	protected void handleAssignments(Expression assigneeExpression,
			Expression ownerExpression,
			Set<Expression> candidateUserExpressions,
			Set<Expression> candidateGroupExpressions, TaskEntity task,
			ActivityExecution execution) {
		PvmActivity activity = execution.getActivity();
		Expression skipExpression = task.getTaskDefinition()
				.getSkipExpression();
		if (!SkipExpressionUtil.isSkipExpressionEnabled(execution,
				skipExpression)
				|| !SkipExpressionUtil.shouldSkipFlowElement(execution,
						skipExpression)) {
			Object property = activity.getProperty("shareniuExt");// 获取shareniuExt属性值
			Map<String, List<ExtensionAttribute>> extensionAttribute = null;
			if (property != null) {
				extensionAttribute = (Map<String, List<ExtensionAttribute>>) property;
			}
			
			boolean useFlag = false;
			if (extensionAttribute != null) {
				List<ExtensionAttribute> list = extensionAttribute
						.get("autoUse");// 获取autoUse属性值
				if (list != null && list.size() > 0) {
					for (ExtensionAttribute extensionAttribute2 : list) {
						String name = extensionAttribute2.getName();
						String value = extensionAttribute2.getValue();
						if (Boolean.valueOf(value)) {
							useFlag = true;
						}
						System.out.println(name + "," + value);
					}
					if (useFlag) {
						List<ExtensionAttribute> list1 = extensionAttribute
								.get("userId");
						for (ExtensionAttribute extensionAttribute2 : list1) {
							String value = extensionAttribute2.getValue();
							String[] split = value.split(",");
							for (String str : split) {
								task.addCandidateUser(str);// 为当前节点添加自定义的处理人
							}
						}
					}
					// 调用父类的handleAssignments方法对任务节点的处理人进行分配
				}
			}
			super.handleAssignments(assigneeExpression, ownerExpression,
					candidateUserExpressions, candidateGroupExpressions, task,
					execution);
		}

	}

}
