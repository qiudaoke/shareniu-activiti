package com.shareniu.chapter16.jump;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.runtime.Execution;

public class ShareniuMinusSignCmd implements Command<Void> {
	protected String taskId;
	
	
	public ShareniuMinusSignCmd(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		ProcessEngineConfigurationImpl pec = commandContext.getProcessEngineConfiguration();
		
		TaskService taskService = pec.getTaskService();
		RuntimeService runtimeService = pec.getRuntimeService();
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		String executionId = task.getExecutionId();
		
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(executionId).singleResult();
		int loopCounter = getLoopVariable(execution, "nrOfInstances");
		int nrOfCompletedInstances = getLoopVariable(execution,
				"nrOfActiveInstances");
		setLoopVariable(execution, "nrOfInstances", loopCounter - 1,true);
		setLoopVariable(execution, "nrOfActiveInstances",
				nrOfCompletedInstances - 1,true);
		
		task.setProcessInstanceId(null);
		task.setExecutionId(null);
		taskService.saveTask(task);
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		executionEntityManager.deleteProcessInstance(executionId, "shareniu", false);
		taskService.deleteTask(taskId, false);
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
