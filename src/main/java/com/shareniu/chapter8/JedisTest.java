package com.shareniu.chapter8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shareniu.activiti.learing.ch14.infoache.MyProcessDefinitionInfoCache;
import com.shareniu.chapter8.ShareniuProcessDefinitionInfoCache.ProcessDefinitionInfoCacheObjectVo;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class JedisTest {
	public static void main(String[] args) {
		Jedis jedis=new Jedis("127.0.0.1");//Redis的ip端口
		String key="shareniuNodeCache:operationProcess:1:4";
		String string=
			"{\"bpmn\":{\"usertask1\":{\"userTaskName\":\"${shaneiu}\",\"userTaskAssignee\":\"shaneiu\"}}}";
		ProcessDefinitionInfoCacheObjectVo v=new ProcessDefinitionInfoCacheObjectVo();
		
		v.setInfoNode(string);
		v.setRevision(1);
		//自定义的VO对象，这个地方定义的数据格式需要根自定义的节点缓存类中的get函数处理逻辑一样即可
		/*
		//已经部署的流程定义Id
		
		//添加到redis中 key一定要与自定义缓存类中的get函数key一直
		*/
		jedis.set(key.getBytes(), MyProcessDefinitionInfoCache.toByteArray(v));
		get(jedis, key);
	}

	private static void get(Jedis jedis, String key) {
		byte[] bs = jedis.get(key.getBytes());
		//将二进制数据转换为ProcessDefinitionEntity对象
		Object object = toObject(bs);
		ProcessDefinitionInfoCacheObjectVo pdf=(ProcessDefinitionInfoCacheObjectVo) object;
		ObjectMapper objectMapper = new ObjectMapper();
		 ObjectNode infoNode=null;
		try {
			if (objectMapper.readTree(pdf.getInfoNode())!=null) {
				infoNode = (ObjectNode)objectMapper.readTree(pdf.getInfoNode()) ;
				System.err.println(infoNode.toString());
			}
		
	}catch(Exception e){
		
	}
	}
		
	    public static Object toObject (byte[] bytes) {     
	        Object obj = null;     
	        try {       
	            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);       
	            ObjectInputStream ois = new ObjectInputStream (bis);       
	            obj = ois.readObject();     
	            ois.close();  
	            bis.close();  
	        } catch (IOException ex) {       
	            ex.printStackTrace();  
	        } catch (ClassNotFoundException ex) {       
	            ex.printStackTrace();  
	        }     
	        return obj;   
	    }  
}
