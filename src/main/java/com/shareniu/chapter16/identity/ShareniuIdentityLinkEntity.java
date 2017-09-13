package com.shareniu.chapter16.identity;

import java.util.Map;

import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;

public class ShareniuIdentityLinkEntity extends IdentityLinkEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String depId;

	public Object getPersistentState() {
		Map<String, Object> persistentState = (Map<String, Object>) super
				.getPersistentState();
		if (this.depId != null) {
			persistentState.put("depId", this.depId);
		}
		return persistentState;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}
}
