package com.shareniu.chapter4;

import java.io.InputStream;
import java.io.Reader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.input.XmlStreamReader;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class StaxTest {
	public static void main(String[] args) throws Exception {
		//使用XMLInputFactory工厂
		 XMLInputFactory xif = XMLInputFactory.newInstance();
		 //使用类加载器加载资源文件
		 InputStream in=StaxTest.class.getClassLoader().getResourceAsStream("com/shareniu/chapter4/stax.xml");
		//用Reader读取资源文件流
		 Reader reader=new XmlStreamReader(in);
		//根据reader 创建XMLStreamReader对象
		XMLStreamReader xtr = xif.createXMLStreamReader(reader);
		while(xtr.hasNext()){
			int event = xtr.next();   
			//文档开始
			if (event == XMLStreamConstants.START_DOCUMENT) {
            } else if (event == XMLStreamConstants.END_DOCUMENT) { //文档结束
            } else if (event == XMLStreamConstants.START_ELEMENT) { //开始节点解析
            	//xtr.getLocalName()对应类似<userTask id="operationTask"/>中的userTask标签
                System.out.println("节点开始解析：" + xtr.getLocalName());
                if (xtr.getLocalName().equals("userTask")||xtr.getLocalName().equals("endEvent")) {
                    for (int i = 0; i < xtr.getAttributeCount(); i++) {// xtr.getAttributeCount()获取标签元素的格式
                    	if (xtr.getAttributeName(i).toString().startsWith("{")) {
                    		//第一个参数标示所使用的xmlns:activiti="http://activiti.org/bpmn" 命名空间 
                    		  System.out.print("--->>>"+xtr.getAttributeValue("http://activiti.org/bpmn", "assignee"));
						}else {
							 System.out.print(xtr.getAttributeName(i) + ":");
		                        System.out.print(xtr.getAttributeValue(i) + ";");
						}
                    }
                    System.out.println();
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) { //节点结束
            } else if (event == XMLStreamConstants.CHARACTERS) { //<text>TEXT</text> 获取xml中类似这样的值
                String text = xtr.getText();
                if (!text.isEmpty() && text.trim().length() > 0) {
                    System.out.println("值：" + xtr.getText());
                }
            }
			
			
			
			
			
		}
		
	}
}
