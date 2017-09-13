package com.shareniu.chapter16.identity;

import java.util.List;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;

public class ShareniuFindIdentityLinksByTaskId  implements Command<List<IdentityLinkEntity>>{
	private String taskId;
	@Override
	public List<IdentityLinkEntity> execute(CommandContext commandContext) {
		List<IdentityLinkEntity> linksByTaskId = commandContext.getIdentityLinkEntityManager().findIdentityLinksByTaskId(taskId);
		return linksByTaskId;
	}
	public ShareniuFindIdentityLinksByTaskId(String taskId) {
		this.taskId = taskId;
	}

}
