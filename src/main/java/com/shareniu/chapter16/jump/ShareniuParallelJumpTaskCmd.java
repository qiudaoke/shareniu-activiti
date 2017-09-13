package com.shareniu.chapter16.jump;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class ShareniuParallelJumpTaskCmd implements Command<Void> {
	protected String executionId;// 执行实例id
	protected String parentId;// 流程实例id
	protected ActivityImpl desActivity;// 目标节点
	protected Map<String, Object> paramvar;// 变量
	protected ActivityImpl currentActivity;// 当前的节点

	public Void execute(CommandContext commandContext) {
		ExecutionEntityManager executionEntityManager = Context
				.getCommandContext().getExecutionEntityManager();
		ExecutionEntity executionEntity = executionEntityManager
				.findExecutionById(executionId);
		String id = null;
		if (executionEntity.getParent() != null) {
			executionEntity = executionEntity.getParent();
			if (executionEntity.getParent() != null) {
				executionEntity = executionEntity.getParent();
				id = executionEntity.getId();
			} else {
				id = executionEntity.getId();
			}
		}
		executionEntity.setVariables(paramvar);
		executionEntity.setEventSource(this.currentActivity);
		executionEntity.setActivity(this.currentActivity);
		// 根据executionId 获取Task
		Iterator<TaskEntity> localIterator = Context.getCommandContext()
				.getTaskEntityManager()
				.findTasksByExecutionId(this.executionId).iterator();
		while (localIterator.hasNext()) {
			TaskEntity taskEntity = (TaskEntity) localIterator.next();
			// 触发任务监听
			taskEntity.fireEvent("complete");
			// 删除任务的原因
			Context.getCommandContext().getTaskEntityManager()
					.deleteTask(taskEntity, "shareniuCompleted", false);
		}

		List<ExecutionEntity> list = executionEntityManager
				.findChildExecutionsByParentExecutionId(parentId);

		for (ExecutionEntity ee : list) {
			ee.remove();
		}
		executionEntity.executeActivity(this.desActivity);

		return null;
	}

	public ShareniuParallelJumpTaskCmd(String executionId, String parentId,
			ActivityImpl desActivity, Map<String, Object> paramvar,
			ActivityImpl currentActivity) {
		this.executionId = executionId;
		this.desActivity = desActivity;
		this.paramvar = paramvar;
		this.currentActivity = currentActivity;
		this.parentId = parentId;
	}
}
