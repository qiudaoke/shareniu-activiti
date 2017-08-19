package com.shareniu.chapter12.command;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class Invoker {
	//持有命令对象
    private Command command = null;
    public Invoker(Command command){
        this.command = command;
    }
    //行动方法
    public void action(){
        command.execute();
    }
}
