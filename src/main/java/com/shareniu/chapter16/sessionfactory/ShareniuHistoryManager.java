package com.shareniu.chapter16.sessionfactory;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;

public class ShareniuHistoryManager extends DefaultHistoryManager {
	@Override
	public void recordActivityStart(ExecutionEntity executionEntity) {
		if (isHistoryLevelAtLeast(HistoryLevel.ACTIVITY)) {
			if (executionEntity.getActivity() != null) {
				ExpressionManager expressionManager = Context
						.getProcessEngineConfiguration().getExpressionManager();
				Expression exp = expressionManager
						.createExpression((String) executionEntity
								.getActivity().getProperty("name"));
				IdGenerator idGenerator = Context
						.getProcessEngineConfiguration().getIdGenerator();
				String processDefinitionId = executionEntity
						.getProcessDefinitionId();
				String processInstanceId = executionEntity
						.getProcessInstanceId();
				String executionId = executionEntity.getId();
				HistoricActivityInstanceEntity historicActivityInstance = new HistoricActivityInstanceEntity();
				historicActivityInstance.setId(idGenerator.getNextId());
				historicActivityInstance
						.setProcessDefinitionId(processDefinitionId);
				historicActivityInstance
						.setProcessInstanceId(processInstanceId);
				historicActivityInstance.setExecutionId(executionId);
				historicActivityInstance.setActivityId(executionEntity
						.getActivityId());
				historicActivityInstance.setActivityName((String) exp
						.getValue(executionEntity));
				historicActivityInstance
						.setActivityType((String) executionEntity.getActivity()
								.getProperty("type"));
				historicActivityInstance.setStartTime(Context
						.getProcessEngineConfiguration().getClock()
						.getCurrentTime());
				if (executionEntity.getTenantId() != null) {
					historicActivityInstance.setTenantId(executionEntity
							.getTenantId());
				}

				getDbSqlSession().insert(historicActivityInstance);

				ProcessEngineConfigurationImpl config = Context
						.getProcessEngineConfiguration();
				if (config != null && config.getEventDispatcher().isEnabled()) {
					config.getEventDispatcher()
							.dispatchEvent(
									ActivitiEventBuilder
											.createEntityEvent(
													ActivitiEventType.HISTORIC_ACTIVITY_INSTANCE_CREATED,
													historicActivityInstance));
				}
			}
		}
	}
}
