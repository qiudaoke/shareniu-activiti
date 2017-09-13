package com.shareniu.chapter16.identity;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;

public class ShareniuUserQueryImpl extends UserQueryImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShareniuUserQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}
	protected String loginId;
	
	public UserQuery loginId(String loginId) {
	    if (loginId == null) {
	      throw new ActivitiIllegalArgumentException("Provided id is null");
	    }
	    this.loginId = loginId;
	    return this;
	  }

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
}
