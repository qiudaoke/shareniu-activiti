package com.shareniu.chapter10;

import java.util.HashMap;
import java.util.Map;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ExtensionOperation {
	protected Map<String, String> propeies = new HashMap<String, String>();
	protected String name;

	public ExtensionOperation(String name) {

	}

	public void addProperty(String name, String value) {
		propeies.put(name, value);
	}

	public Map<String, String> getPropeies() {
		return propeies;
	}
}
