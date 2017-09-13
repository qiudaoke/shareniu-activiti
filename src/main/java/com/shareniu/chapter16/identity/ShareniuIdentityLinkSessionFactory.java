package com.shareniu.chapter16.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager;

public class ShareniuIdentityLinkSessionFactory  implements SessionFactory{

	@Override
	public Class<?> getSessionType() {
		return IdentityLinkEntityManager.class;
	}

	@Override
	public Session openSession() {
		return new ShareniuIdentityLinkEntityManager();
	}

}
