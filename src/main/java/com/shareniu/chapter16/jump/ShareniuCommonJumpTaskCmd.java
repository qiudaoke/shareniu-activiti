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
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;

public class ShareniuCommonJumpTaskCmd implements Command<Void> {
	protected String executionId;//执行实例id
	protected String parentId;//流程实例id
	protected ActivityImpl desActivity;//目标节点
	protected Map<String, Object> paramvar;//变量
	protected ActivityImpl currentActivity;//当前的节点
	public Void execute(CommandContext commandContext) {
		// 获取当前流程的executionId，因为常规的流程执行id与流程实例id是相等的。
		ExecutionEntityManager executionEntityManager = Context
				.getCommandContext().getExecutionEntityManager();
		ExecutionEntity executionEntity = executionEntityManager
				.findExecutionById(parentId);
		executionEntity.setVariables(paramvar);
		executionEntity.setExecutions(null);
		executionEntity.setEventSource(this.currentActivity);
		executionEntity.setActivity(this.currentActivity);
		// 根据executionId 获取Task
		Iterator<TaskEntity> localIterator = Context.getCommandContext()
				.getTaskEntityManager().findTasksByProcessInstanceId(parentId)
				.iterator();

		while (localIterator.hasNext()) {
			TaskEntity taskEntity = (TaskEntity) localIterator.next();
			// 触发任务监听器
			taskEntity.fireEvent("complete");
			// 删除任务的原因
			Context.getCommandContext().getTaskEntityManager()
					.deleteTask(taskEntity, "shareniuCompleted", false);
		}
		// 推动流程实例继续向下运转
		executionEntity.executeActivity(this.desActivity);
		executionEntity.performOperation(AtomicOperation.TRANSITION_CREATE_SCOPE);
		return null;
	}

	public ShareniuCommonJumpTaskCmd(String executionId, String parentId,
			ActivityImpl desActivity, Map<String, Object> paramvar,
			ActivityImpl currentActivity) {
		this.executionId = executionId;
		this.desActivity = desActivity;
		this.paramvar = paramvar;
		this.currentActivity = currentActivity;
		this.parentId = parentId;
	}
}
