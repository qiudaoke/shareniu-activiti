package com.shareniu.chapter16.identity;

import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

public class ShareniuUserManager extends UserEntityManager  {

	@Override
	public void insertUser(User user) {
		super.insertUser(user);
	}
	@Override
	public UserQuery createNewUserQuery() {
		 return new ShareniuUserQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
	}
}
