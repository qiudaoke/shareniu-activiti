package com.shareniu.chapter16;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.DefaultHistoryManagerSessionFactory;

import com.shareniu.chapter16.sessionfactory.ShareniuHistoryManager;

public class ShareniuProcessHistoryManagerSessionFactory  extends DefaultHistoryManagerSessionFactory {
	@Override
	public Session openSession() {
		return new ShareniuHistoryManager();
	}
}
