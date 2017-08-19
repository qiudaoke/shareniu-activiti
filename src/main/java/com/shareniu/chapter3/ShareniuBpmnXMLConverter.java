package com.shareniu.chapter3;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.exceptions.XMLException;
import org.xml.sax.SAXException;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuBpmnXMLConverter extends BpmnXMLConverter {
	  protected static   String BPMN_XSD = "com/shareniu/chapter3/BPMN20.xsd";
	  protected Schema createSchema() throws SAXException {
		    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		    Schema schema = null;
		    if (classloader != null) {
		      schema = factory.newSchema(classloader.getResource(BPMN_XSD));
		    }
		    
		    if (schema == null) {
		      schema = factory.newSchema(BpmnXMLConverter.class.getClassLoader().getResource(BPMN_XSD));
		    }
		    
		    if (schema == null) {
		      throw new XMLException("BPMN XSD could not be found");
		    }
		    return schema;
		  }
}
