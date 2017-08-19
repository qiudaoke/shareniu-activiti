package com.shareniu.chapter7;

import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuBpmnDeployer extends BpmnDeployer {
	public static final String[] BPMN_RESOURCE_SUFFIXES = new String[] { "bpmn20.xml", "bpmn","shareniu" };
	@Override
	protected boolean isBpmnResource(String resourceName) {
		 for (String suffix : BPMN_RESOURCE_SUFFIXES) {
		      if (resourceName.endsWith(suffix)) {
		        return true;
		      }
		    }
		    return false;
	}
	

}
