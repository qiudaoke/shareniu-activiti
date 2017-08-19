package com.shareniu.chapter8;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import redis.clients.jedis.Jedis;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuProcessDefinitionCache implements
		DeploymentCache<ProcessDefinitionEntity> {
	// 实例化Jedis实例 ip:端口
	Jedis jedis = new Jedis("127.0.0.1", 6379);

	@Override
	public ProcessDefinitionEntity get(String id) {
		// 获取数据
		byte[] bs = jedis.get(id.getBytes());
		if (bs == null)
			return null;
		// 将二进制数据转换为ProcessDefinitionEntity对象
		Object object = toObject(bs);
		if (object == null)
			return null;
		ProcessDefinitionEntity pdf = (ProcessDefinitionEntity) object;
		return pdf;
	}

	@Override
	public void add(String id, ProcessDefinitionEntity object) {
		// 添加到缓存，因为value为object对象，所以需要将该对象转化为二进制进行存储
		jedis.set(id.getBytes(), toByteArray(object));
	}

	@Override
	public void remove(String id) {
		// 删除制定的key
		jedis.del(id.getBytes());
	}

	@Override
	public void clear() {
		// 清除所有的数据，在这里默认不提供实现，避免误操作
	}
	//对象转化为byte[]
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
		}
		return bytes;
	}

	//数组转对象
	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (Exception ex) {
		}
		return obj;
	}
}
