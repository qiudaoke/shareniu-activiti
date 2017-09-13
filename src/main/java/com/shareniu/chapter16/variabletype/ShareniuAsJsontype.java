package com.shareniu.chapter16.variabletype;

import java.io.IOException;

import org.activiti.engine.impl.variable.ValueFields;
import org.activiti.engine.impl.variable.VariableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ShareniuAsJsontype implements VariableType {
	ObjectMapper mapper = new ObjectMapper();

	// 变量类型为shareniu 我们自定义的名称，可以随意修改
	public String getTypeName() {
		return "shareniu";
	}

	// 开启缓存，提升效率
	public boolean isCachable() {
		return true;
	}

	// 如果是value是ShareniuAsJsontype类或者ShareniuAsJsontype的子类均ok
	public boolean isAbleToStore(Object value) {
		if (value == null) {
			return true;
		}
		return Shareniu.class.isAssignableFrom(value.getClass());

	}

	public void setValue(Object value, ValueFields valueFields) {
		if (null == value) {
			valueFields.setTextValue("");
		} else {
			try {
				valueFields.setTextValue(mapper.writeValueAsString(value));
			} catch (IOException ioe) {
			}
		}
	}

	public Object getValue(ValueFields valueFields) {
		try {
			return mapper.readValue(valueFields.getTextValue(), Shareniu.class);
		} catch (IOException ioe) {
			return null;
		}
	}
}
