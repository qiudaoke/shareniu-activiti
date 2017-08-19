package com.shareniu.chapter8;

import java.util.List;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionInfoCacheObject;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuDeployer extends BpmnDeployer {
	@Override
	protected void createLocalizationValues(String processDefinitionId,
			Process process) {
		if (process == null) return;
	    
	    CommandContext commandContext = Context.getCommandContext();
	    DynamicBpmnService dynamicBpmnService = commandContext.getProcessEngineConfiguration().getDynamicBpmnService();
	    ObjectNode infoNode = dynamicBpmnService.getProcessDefinitionInfo(processDefinitionId);

	    boolean localizationValuesChanged = false;
	    List<ExtensionElement> localizationElements = process.getExtensionElements().get("localization");
	    if (localizationElements != null) {
	      for (ExtensionElement localizationElement : localizationElements) {
	        if (BpmnXMLConstants.ACTIVITI_EXTENSIONS_PREFIX.equals(localizationElement.getNamespacePrefix())) {
	          String locale = localizationElement.getAttributeValue(null, "locale");
	          String name = localizationElement.getAttributeValue(null, "name");
	          String documentation = null;
	          List<ExtensionElement> documentationElements = localizationElement.getChildElements().get("documentation");
	          if (documentationElements != null) {
	            for (ExtensionElement documentationElement : documentationElements) {
	              documentation = StringUtils.trimToNull(documentationElement.getElementText());
	              break;
	            }
	          }

	          String processId = process.getId();
	          if (isEqualToCurrentLocalizationValue(locale, processId, "name", name, infoNode) == false) {
	            dynamicBpmnService.changeLocalizationName(locale, processId, name, infoNode);
	            localizationValuesChanged = true;
	          }
	          
	          if (documentation != null && isEqualToCurrentLocalizationValue(locale, processId, "description", documentation, infoNode) == false) {
	            dynamicBpmnService.changeLocalizationDescription(locale, processId, documentation, infoNode);
	            localizationValuesChanged = true;
	          }
	          
	          break;
	        }
	      }
	    }

	    boolean isFlowElementLocalizationChanged = localizeFlowElements(process.getFlowElements(), infoNode);
	    boolean isDataObjectLocalizationChanged = localizeDataObjectElements(process.getDataObjects(), infoNode);
	    if (isFlowElementLocalizationChanged || isDataObjectLocalizationChanged) {
	      localizationValuesChanged = true;
	    }
	     ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
	    DeploymentManager deploymentManager = processEngineConfiguration.getDeploymentManager();
	    ProcessDefinitionInfoCacheObject definitionCacheObject = new ProcessDefinitionInfoCacheObject();
	    definitionCacheObject.setInfoNode(infoNode);
	    if (localizationValuesChanged) {
	    	
	    	 deploymentManager.getProcessDefinitionInfoCache().add(processDefinitionId, definitionCacheObject);
	    }
	}
}
