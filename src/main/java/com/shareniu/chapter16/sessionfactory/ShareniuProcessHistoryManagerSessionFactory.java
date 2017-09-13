package com.shareniu.chapter16.sessionfactory;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.DefaultHistoryManagerSessionFactory;

public class ShareniuProcessHistoryManagerSessionFactory  extends DefaultHistoryManagerSessionFactory {
	@Override
	public Session openSession() {
		return new ShareniuHistoryManager();
	}
}
