package com.shareniu.chapter16.identity;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.task.IdentityLinkType;

public class AddIdentitylinkCmd implements Command<Void> {
	
	private String depId;
	private String taskId;
	@Override
	public Void execute(CommandContext commandContext) {
		ShareniuIdentityLinkEntity identityLinkEntity = new ShareniuIdentityLinkEntity();
		    identityLinkEntity.setType(IdentityLinkType.CANDIDATE);
		    identityLinkEntity.setDepId(depId);
		    identityLinkEntity.setTaskId(taskId);
		    identityLinkEntity.insert();
		return null;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public AddIdentitylinkCmd(String depId, String taskId) {
		this.depId = depId;
		this.taskId = taskId;
	}
	
}
