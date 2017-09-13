package com.shareniu.chapter16.identity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.engine.ManagementService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.task.TaskDefinition;

public class ShareniuUserTaskActivityBehavior extends UserTaskActivityBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShareniuUserTaskActivityBehavior(String userTaskId,
			TaskDefinition taskDefinition) {
		super(userTaskId, taskDefinition);
	}

	@Override
	protected void handleAssignments(Expression assigneeExpression,
			Expression ownerExpression,
			Set<Expression> candidateUserExpressions,
			Set<Expression> candidateGroupExpressions, TaskEntity task,
			ActivityExecution execution) {
		super.handleAssignments(assigneeExpression, ownerExpression,
				candidateUserExpressions, candidateGroupExpressions, task,
				execution);
		PvmActivity activity = execution.getActivity();
		ManagementService managementService = execution.getEngineServices().getManagementService();
		Object property = activity.getProperty("shareniuExt");// 获取shareniuExt属性值
		Map<String, List<ExtensionAttribute>> extensionAttribute = null;
		if (property != null) {
			extensionAttribute = (Map<String, List<ExtensionAttribute>>) property;
		}
		List<ExtensionAttribute> list1 = extensionAttribute.get("depId");
		for (ExtensionAttribute extensionAttribute2 : list1) {
			String value = extensionAttribute2.getValue();
			String[] split = value.split(",");
			for (String depId : split) {
				managementService.executeCommand(new AddIdentitylinkCmd(depId,task.getId()));
				
			}
		}
	}
}



















/*	public IdentityLinkEntity addIdentityLink(String userId, String depId,
TaskEntity task, String type) {
ShareniuIdentityLinkEntity identityLinkEntity = new ShareniuIdentityLinkEntity();
identityLinkEntity.setType(IdentityLinkType.CANDIDATE);
identityLinkEntity.setDepId(depId);
identityLinkEntity.setTaskId(task.getId());
identityLinkEntity.insert();
return identityLinkEntity;
}

public void addCandidateDep(String depId, TaskEntity task) {
addIdentityLink(null, depId, task, IdentityLinkType.CANDIDATE);
}

public void addCandidateDeps(Collection<String> candidateGroups,
TaskEntity task) {
for (String candidateGroup : candidateGroups) {
addCandidateDep(candidateGroup, task);
}
}*/
