package com.shareniu.chapter16.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.UserEntityManagerFactory;

public class ShareniuUserManagerFactory extends UserEntityManagerFactory {
	@Override
	public Session openSession() {
		return new ShareniuUserManager();
	}
}
