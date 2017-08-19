package com.shareniu.chapter12.command;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ConcreteCommand  implements Command{
	//持有相应的接收者对象
    private Receiver receiver = null;
    //构造方法中需要设置接收者
    public ConcreteCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        //通常会转调接收者对象的相应方法，让接收者来真正执行功能
        receiver.action();
    }
}
