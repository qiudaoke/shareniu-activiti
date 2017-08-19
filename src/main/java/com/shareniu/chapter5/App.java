package com.shareniu.chapter5;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.junit.Test;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class App {
	@Test
	public void readXMLFile() {
		// 流程文档
		String resource = "com/shareniu/chapter5/ShareniuExtensionElement.bpmn20.xml";
		// 获取流程文档数据流
		InputStream xmlStream = this.getClass().getClassLoader()
				.getResourceAsStream(resource);
		InputStreamSource xmlSource = new InputStreamSource(xmlStream);
		// 实例化BpmnXMLConverter
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		// 转换为流程模型
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSource,
				true, true, "UTF-8");
		// 获取id为operationTask的任务节点所有信息
		FlowElement flowElement = bpmnModel.getProcesses().get(0)
				.getFlowElement("operationTask");
		// 获取扩展元素的信息
		Map<String, List<ExtensionElement>> extensionElements = flowElement
				.getExtensionElements();
		Iterator<Entry<String, List<ExtensionElement>>> it = extensionElements
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, List<ExtensionElement>> entry = it.next();
			// 获取根标签的名称
			System.err.println("rookey= " + entry.getKey());
			List<ExtensionElement> value = entry.getValue();
			for (ExtensionElement e : value) {
				Map<String, List<ExtensionElement>> childElements = e
						.getChildElements();
				Iterator<Entry<String, List<ExtensionElement>>> it1 = childElements
						.entrySet().iterator();
				while (it1.hasNext()) {
					Entry<String, List<ExtensionElement>> entry1 = it1.next();
					System.err.println("childKey= " + entry1.getKey());
					List<ExtensionElement> value1 = entry1.getValue();
					for (ExtensionElement e1 : value1) {
						String elementText = e1.getElementText();// 获取文本
						System.err.println(elementText);
						Map<String, List<ExtensionAttribute>> attributes = e1
								.getAttributes();
						Collection<List<ExtensionAttribute>> values = attributes
								.values();
						for (List<ExtensionAttribute> list : values) {
							for (ExtensionAttribute extensionAttribute : list) {
								System.err.println(extensionAttribute.getName()
										+ "," + extensionAttribute.getValue());
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void getAttributes() {
		// 流程文档
		String resource = "com/shareniu/chapter5/customTask.bpmn";
		// 文件流
		InputStream xmlStream = this.getClass().getClassLoader()
				.getResourceAsStream(resource);
		InputStreamSource xmlSource = new InputStreamSource(xmlStream);
		// 实例化BpmnXMLConverter
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		// 转换为流程模型
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSource,true, false, "UTF-8");
		// 获取id为operationTask任务节点的所有信息
		FlowElement flowElement = bpmnModel.getProcesses().get(0)
				.getFlowElement("operationTask");
		Map<String, List<ExtensionAttribute>> attributes = flowElement
				.getAttributes();
		List<ExtensionAttribute> list = attributes.get("id");
		String name = list.get(0).getName();
		String value = list.get(0).getValue();
		System.out.println(name + "," + value);
		list = attributes.get("name");
		name = list.get(0).getName();
		value = list.get(0).getValue();
		System.out.println(name + "," + value);
	}
	@Test
	public void sequencegetAttributes() {
		// 流程文档
		String resource = "com/shareniu/chapter5/startEvent.bpmn";
		// 文件流
		InputStream xmlStream = this.getClass().getClassLoader()
				.getResourceAsStream(resource);
		InputStreamSource xmlSource = new InputStreamSource(xmlStream);
		// 实例化BpmnXMLConverter
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		BpmnXMLConverter.addConverter(new ShareniuStartEventXMLConverter());
		// 转换为流程模型
		BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSource,
				true, false, "UTF-8");
		// 获取id为operationTask任务节点的所有信息
		FlowElement flowElement = bpmnModel.getProcesses().get(0)
				.getFlowElement("startevent1");
		Map<String, List<ExtensionAttribute>> attributes = flowElement
				.getAttributes();
		List<ExtensionAttribute> list = attributes.get("id");
		String name = list.get(0).getName();
		String value = list.get(0).getValue();
		System.out.println(name + "," + value);
		list = attributes.get("name");
		name = list.get(0).getName();
		value = list.get(0).getValue();
		System.out.println(name + "," + value);
	}
}
