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

public class ShareniuMultiInstanceJumpTaskCmd implements Command<Void> {
	protected String executionId;//执行实例id
	protected String parentId;//流程实例id
	protected ActivityImpl desActivity;//目标节点
	protected Map<String, Object> paramvar;//变量
	protected ActivityImpl currentActivity;//当前的节点

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
			}
			id = executionEntity.getId();
		}
		executionEntity.setVariables(paramvar);
		executionEntity.setExecutions(null);
		executionEntity.setEventSource(this.currentActivity);
		executionEntity.setActivity(this.currentActivity);
		// 根据executionId 获取Task
		Iterator<TaskEntity> localIterator = Context.getCommandContext()
				.getTaskEntityManager().findTasksByProcessInstanceId(id).iterator();

		while (localIterator.hasNext()) {
			TaskEntity taskEntity = (TaskEntity) localIterator.next();
			System.err.println("==================" + taskEntity.getId());
			// 触发任务监听
			taskEntity.fireEvent("complete");
			// 删除任务的原因
			Context.getCommandContext().getTaskEntityManager()
					.deleteTask(taskEntity, "completed", false);
		}
		List<ExecutionEntity> list = executionEntityManager
				.findChildExecutionsByParentExecutionId(parentId);
		for (ExecutionEntity executionEntity2 : list) {
			ExecutionEntity findExecutionById = executionEntityManager.findExecutionById(executionEntity2.getId());

			List<ExecutionEntity> parent = executionEntityManager
					.findChildExecutionsByParentExecutionId(executionEntity2
							.getId());
			for (ExecutionEntity executionEntity3 : parent) {
				executionEntity3.remove();
				System.err.println(executionEntity3.getId()
						+ "----------------->>>>>>>>>>");
				Context.getCommandContext().getHistoryManager()
						.recordActivityEnd(executionEntity3);
			}
			executionEntity2.remove();
	    	  Context.getCommandContext().getHistoryManager().recordActivityEnd(executionEntity2);
	    	  System.err.println(findExecutionById+"----------------->>>>>>>>>>");

		}
	      commandContext
	        .getIdentityLinkEntityManager().deleteIdentityLinksByProcInstance(id);
		executionEntity.executeActivity(this.desActivity);
		//

		return null;
	}

	/**
	 * 构造参数 可以根据自己的业务需要添加更多的字段 分享牛原创(尊重原创
	 * 转载对的时候第一行请注明，转载出处来自分享牛http://blog.csdn.net/qq_30739519)
	 * 
	 * @param executionId
	 * @param desActivity
	 * @param paramvar
	 * @param currentActivity
	 */
	public ShareniuMultiInstanceJumpTaskCmd(String executionId, String parentId,
			ActivityImpl desActivity, Map<String, Object> paramvar,
			ActivityImpl currentActivity) {
		this.executionId = executionId;
		this.parentId = parentId;
		this.desActivity = desActivity;
		this.paramvar = paramvar;
		this.currentActivity = currentActivity;
		
	}
}
