package com.shareniu.chapter16.identity;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.UserEntity;

public class ShareniuUserEntity extends UserEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String id;
	protected String password;
	protected String loginId;
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("name", name);
		persistentState.put("password", password);
		persistentState.put("loginId", loginId);
		return persistentState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}
