package com.shareniu.chapter14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuExclusiveGatewayActivityBehaviorExt extends
		ExclusiveGatewayActivityBehavior {
	@Override
	protected void leave(ActivityExecution execution) {
		List<PvmTransition> transitionsToTake = new ArrayList<PvmTransition>();
		// 获取排他网关所有的出线
		List<PvmTransition> outgoingTransitions = execution.getActivity()
				.getOutgoingTransitions();
		for (PvmTransition outgoingTransition : outgoingTransitions) {
			// 获取连线中配置的条件表达式
			Condition condition = (Condition) outgoingTransition
					.getProperty("condition");
			// 验证连线中配置的条件表达式是否返回true
			boolean evaluate = condition.evaluate(outgoingTransition.getId(),
					execution);
			if (evaluate) {
				transitionsToTake.add(outgoingTransition);
			}

		}
		// 开始排他网关的出线操作
		execution.takeAll(transitionsToTake, Arrays.asList(execution));
	}
}
