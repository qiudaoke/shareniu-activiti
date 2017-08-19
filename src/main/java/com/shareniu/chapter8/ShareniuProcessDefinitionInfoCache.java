package com.shareniu.chapter8;

import java.io.IOException;
import java.io.Serializable;

import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionInfoCache;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionInfoCacheObject;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ShareniuProcessDefinitionInfoCache extends ProcessDefinitionInfoCache {
	public static class ProcessDefinitionInfoCacheObjectVo   implements Serializable {
		protected String id;	//id
		  protected int revision;//版本
		 // 因为需要序列化，ProcessDefinitionInfoCacheObjec类中的infoNode类型为ObjectNode,而ObjectNode没有实现序列化接口，所以需要对象与String
		  //之间相互进行转换
		  protected String infoNode;
		  
		  public String getId() {
		    return id;
		  }
		  
		  public void setId(String id) {
		    this.id = id;
		  }
		  
		  public int getRevision() {
		    return revision;
		  }
		  
		  public void setRevision(int revision) {
		    this.revision = revision;
		  }

		public String getInfoNode() {
			return infoNode;
		}

		public void setInfoNode(String infoNode) {
			this.infoNode = infoNode;
		}

		@Override
		public String toString() {
			return "ProcessDefinitionInfoCacheObjectVo [id=" + id
					+ ", revision=" + revision + ", infoNode=" + infoNode + "]";
		}
		
	}
	protected CommandExecutor commandExecutor;
	public void setCommandExecutor(CommandExecutor commandExecutor) {
		super.commandExecutor = commandExecutor;
		this.commandExecutor = commandExecutor;
	}

	Jedis jedis = new Jedis("127.0.0.1");

	String prex = "shareniuNodeCache:";

	@Override
	public  void add(String id, ProcessDefinitionInfoCacheObject obj) {
		// redis获取
		byte[] bs = jedis.get((prex + id).getBytes()); // 从缓存中进行节点数据的获取
		Object object = null;
		// 实例化自定义的类并完成与ProcessDefinitionInfoCacheObject类进行相互转化
		ProcessDefinitionInfoCacheObjectVo v = new ProcessDefinitionInfoCacheObjectVo();
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode dataBaseInfoNode = obj.getInfoNode();// 获取节点定义信息的数据
		try {
			ObjectNode redisObjectNode = objectMapper.createObjectNode();// 如果不存在则实例化
			String redisStr = null;
			if (bs != null) {
				// 将二进制流转化为对象
				object = ShareniuProcessDefinitionCache.toObject(bs);
				// 对象转化，因为添加的时候了类型为ProcessDefinitionInfoCacheObjectVo，因此如果存在值转化不会报错
				ProcessDefinitionInfoCacheObjectVo pdf = (ProcessDefinitionInfoCacheObjectVo) object;
				redisStr = pdf.getInfoNode();// 获取缓存中的数据并转化为ObjectNode类型的对象
				redisObjectNode = (ObjectNode) objectMapper.readTree(redisStr);
			}
			v.setId(obj.getId());// 设置id
			// 如果添加数据的时候，发现redis有值则将新的值与已经存在的值进行合并，并填充对象的属性值
			dataBaseInfoNode.putAll(redisObjectNode);
			String finall = objectMapper.writeValueAsString(dataBaseInfoNode);
			v.setInfoNode(finall);
			v.setRevision(obj.getRevision());
			// 将对象转化为byte[]存储到redis
			System.out.println(v);
			jedis.set((prex + id).getBytes(),
					ShareniuProcessDefinitionCache.toByteArray(v));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public ProcessDefinitionInfoCacheObject get(final String id) {
		// 从缓存中获取数据
		byte[] bs = jedis.get((prex + id).getBytes());
		if (bs == null)
			return null;
		// 将二进制数据转换为object对象防止为空
		Object object = ShareniuProcessDefinitionCache.toObject(bs);
		if (object == null)
			return null;
		ProcessDefinitionInfoCacheObjectVo pdf = (ProcessDefinitionInfoCacheObjectVo) object;
		// 将从缓存中获取到的对象进行转换并返回
		ProcessDefinitionInfoCacheObject v = new ProcessDefinitionInfoCacheObject();
		v.setId(pdf.getId());
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode infoNode = null;
		try {
			if (objectMapper.readTree(pdf.getInfoNode()) != null) {
				infoNode = (ObjectNode) objectMapper
						.readTree(pdf.getInfoNode());
			}
			v.setInfoNode(infoNode);
			v.setRevision(pdf.getRevision());
			return v;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void clear() {
	}

	public ShareniuProcessDefinitionInfoCache(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

/*	public ProcessDefinitionInfoCacheObject get(
			final String processDefinitionId, String id) {
		ProcessDefinitionInfoCacheObject infoCacheObject = null;

		infoCacheObject = commandExecutor
				.execute(new Command<ProcessDefinitionInfoCacheObject>() {

					@Override
					public ProcessDefinitionInfoCacheObject execute(
							CommandContext commandContext) {
						ProcessDefinitionInfoEntityManager infoEntityManager = commandContext
								.getProcessDefinitionInfoEntityManager();
						ObjectMapper objectMapper = commandContext
								.getProcessEngineConfiguration()
								.getObjectMapper();

						ProcessDefinitionInfoCacheObject cacheObject = new ProcessDefinitionInfoCacheObject();
						ProcessDefinitionInfoEntity infoEntity = infoEntityManager
								.findProcessDefinitionInfoByProcessDefinitionId(processDefinitionId);
						if (infoEntity != null
								&& infoEntity.getRevision() != cacheObject
										.getRevision()) {
							cacheObject.setRevision(infoEntity.getRevision());
							if (infoEntity.getInfoJsonId() != null) {
								byte[] infoBytes = infoEntityManager
										.findInfoJsonById(infoEntity
												.getInfoJsonId());
								try {
									ObjectNode infoNode = (ObjectNode) objectMapper
											.readTree(infoBytes);
									cacheObject.setInfoNode(infoNode);
								} catch (Exception e) {
									throw new ActivitiException(
											"Error reading json info node for process definition "
													+ processDefinitionId, e);
								}
							}
						} else if (infoEntity == null) {
							cacheObject.setRevision(0);
							cacheObject.setInfoNode(objectMapper
									.createObjectNode());
						}
						return cacheObject;
					}
				});

		return infoCacheObject;
	}*/
}
