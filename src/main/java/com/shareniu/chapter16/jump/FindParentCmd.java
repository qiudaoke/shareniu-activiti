package com.shareniu.chapter16.jump;

import java.util.Date;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

public class FindParentCmd implements Command<Void> {

	@Override
	public Void execute(CommandContext commandContext) {
		String executionId = "50008";
		RuntimeService runtimeService = commandContext
				.getProcessEngineConfiguration().getRuntimeService();
		TaskService taskService = commandContext
				.getProcessEngineConfiguration().getTaskService();
		List<Execution> list = runtimeService.createExecutionQuery()
				.executionId(executionId).list();
		for (Execution execution : list) {
			ExecutionEntity ee = (ExecutionEntity) execution;
			ExecutionEntity parent = ee.getParent();
			ExecutionEntity createExecution = parent.createExecution();
			createExecution.setActive(true);
			createExecution.setConcurrent(true);
			createExecution.setScope(false);
			// TaskEntity taskEntity =
			// TaskEntity.createAndInsert(createExecution);
			Task singleResult = taskService.createTaskQuery()
					.executionId(executionId).singleResult();
			TaskEntity t = (TaskEntity) singleResult;
			TaskEntity taskEntity = new TaskEntity();
			taskEntity.setCreateTime(new Date());
			taskEntity.setTaskDefinition(t.getTaskDefinition());
			taskEntity.setProcessDefinitionId(t.getProcessDefinitionId());
			taskEntity.setTaskDefinitionKey(t.getTaskDefinitionKey());
			taskEntity.setProcessInstanceId(t.getProcessInstanceId());
			taskEntity.setExecutionId(createExecution.getId());
			taskEntity.setName(singleResult.getName());
			IdGenerator idGenerator = commandContext
					.getProcessEngineConfiguration().getIdGenerator();
			String nextId = idGenerator.getNextId();
			taskEntity.setId(nextId);
			taskEntity.setExecution(createExecution);
			taskService.saveTask(taskEntity);

			System.out.println(ee + "-->>>>" + parent);
			int loopCounter = getLoopVariable(createExecution, "nrOfInstances");
			int nrOfCompletedInstances = getLoopVariable(createExecution,
					"nrOfActiveInstances");
			setLoopVariable(createExecution, "nrOfInstances", loopCounter + 1,true);
			setLoopVariable(createExecution, "nrOfActiveInstances",
					nrOfCompletedInstances + 1,true);
		}
		return null;
	}

	protected void setLoopVariable(ExecutionEntity execution,
			String variableName, Object value,boolean paretnt) {
	//	execution.setVariableLocal(variableName, value);
		ActivityExecution parent = execution.getParent();
		parent.setVariableLocal(variableName, value);
	}

	protected Integer getLoopVariable(ExecutionEntity execution,
			String variableName) {
		Object value = execution.getVariableLocal(variableName);
		ActivityExecution parent = execution.getParent();
		while (value == null && parent != null) {
			value = parent.getVariableLocal(variableName);
			parent = parent.getParent();
		}
		return (Integer) (value != null ? value : 0);
	}

	protected ActivityExecution getRoot(ExecutionEntity execution) {
		ActivityExecution parent = execution.getParent();
		while (parent != null) {
			parent = parent.getParent();
		}
		return parent;
	}

}
