package com.shareniu.chapter12.command;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class Client {
	 public static void main(String[] args) {
	        assemble();//客户端使用者调用
	    }

	public static void assemble() {
		//创建接收者
		Receiver receiver = new Receiver();
		//创建命令对象，设定它的接收者
		Command command = new ConcreteCommand(receiver);
		//创建请求者，把命令对象设置进去
		Invoker invoker = new Invoker(command);
		//执行方法
		invoker.action();
	}
}
