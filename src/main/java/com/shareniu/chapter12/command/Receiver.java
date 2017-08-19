package com.shareniu.chapter12.command;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class Receiver {
	//真正执行命令相应的操作
    public void action(){
    	//真正实现命令方法中的execute
        System.out.println("分享牛执行操作");
    }
}
