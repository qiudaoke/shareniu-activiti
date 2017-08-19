package com.shareniu.chapter5;

import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.StartEventXMLConverter;
import org.activiti.bpmn.converter.util.BpmnXMLUtil;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.alfresco.AlfrescoStartEvent;
import org.apache.commons.lang3.StringUtils;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuStartEventXMLConverter extends StartEventXMLConverter {
	  protected static final List<ExtensionAttribute> defaultElementAttributes = Arrays.asList(
		      new ExtensionAttribute(ATTRIBUTE_ID),
		      new ExtensionAttribute(ATTRIBUTE_NAME)
		  );
	public Class<? extends BaseElement> getBpmnElementType() {
	    return StartEvent.class;
	  }
	  @Override
	  protected String getXMLElementName() {
	    return ELEMENT_EVENT_START;
	  }
	  @Override
	  protected BaseElement convertXMLToElement(XMLStreamReader xtr, BpmnModel model) throws Exception {
		 	
		  String formKey = xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_FORM_FORMKEY);
		    StartEvent startEvent = null;
		    if (StringUtils.isNotEmpty(formKey)) {
		      if (model.getStartEventFormTypes() != null && model.getStartEventFormTypes().contains(formKey)) {
		        startEvent = new AlfrescoStartEvent();
		      }
		    }
		    if (startEvent == null) {
		      startEvent = new StartEvent();
		    }
		    BpmnXMLUtil.addXMLLocation(startEvent, xtr);
		    startEvent.setInitiator(xtr.getAttributeValue(ACTIVITI_EXTENSIONS_NAMESPACE, ATTRIBUTE_EVENT_START_INITIATOR));
		    startEvent.setFormKey(formKey);
		    BpmnXMLUtil.addCustomAttributes(xtr, startEvent, defaultElementAttributes);
		    parseChildElements(getXMLElementName(), startEvent, model, xtr);
	    return startEvent;
	  }
}
