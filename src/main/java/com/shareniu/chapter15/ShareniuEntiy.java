package com.shareniu.chapter15;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.db.PersistentObject;

public class ShareniuEntiy  implements  Serializable, PersistentObject {
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Object getPersistentState() {
		 Map<String, Object> persistentState = new HashMap<String, Object>();
		    persistentState.put("id", id);
		    persistentState.put("name", name);
		    return persistentState;
	}
	public ShareniuEntiy(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
