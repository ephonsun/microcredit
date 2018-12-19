package banger.framework.dao;

import banger.framework.CodeTableException;
import banger.framework.codetable.CodeTable;
import banger.framework.codetable.ICodeTable;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.entity.EntityUtil;
import banger.framework.pagesize.IPageSize;
import banger.framework.reflector.PropertyInfo;
import banger.framework.spring.SpringContext;
import banger.framework.sql.mapping.SqlItem;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class EntityDao<E> extends DataDao implements IEntityDao<E> {
	private Class<?> clazz;

	public Class<?> getEntityType() {
		if (this.clazz == null) {
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			this.clazz = (Class<?>) pt.getActualTypeArguments()[0];
		}
		return this.clazz;
	}

	/**
	 * 查询返回实体列表 Object ... conditions sqlMap的外陪参数
	 */
	public List<?> queryEntities(String sqlId, Object... conditions) {
		return this.queryEntities(sqlId, -1, -1, conditions);
	}

	/**
	 * 查询返回实体列表 Object ... conditions sqlMap的外陪参数
	 */
	public List<?> queryEntities(String sqlId, int pageSize, int pageNum,
			Object... conditions) {
		DataTable table = (pageSize > 0 && pageNum > 0) ? this.queryData(sqlId,
				pageSize, pageNum, conditions) : this.queryData(sqlId,
				conditions);
		Class<?> entityType = (this.getResultClass(sqlId) != null) ? this
				.getResultClass(sqlId) : this.getEntityType();
		List<Object> list = new ArrayList<Object>();
		Map<String,String> proxyMap = getProxyPropertyMap(sqlId);
		for (DataRow row : table) {
			if(proxyMap!=null){
				Object object = EntityUtil.newProxy(entityType,row,proxyMap);
				list.add(object);
			}else{
				Object object = EntityUtil.newInstance(entityType,row,this.getPropertyMap(sqlId));
				list.add(object);
			}
		}
		CodeTableHelper.linkCodeTable(list);
		return list;
	}
	
	/**
	 * 查询返回实体列表 Object ... conditions sqlMap的外陪参数
	 */
	public List<?> queryPageEntities(String sqlId, IPageSize page,
			Object... conditions) {
		DataTable table = this.queryData(sqlId,page,conditions);
		Class<?> entityType = (this.getResultClass(sqlId) != null) ? this
				.getResultClass(sqlId) : this.getEntityType();
		List<Object> list = new ArrayList<Object>();
		Map<String,String> proxyMap = getProxyPropertyMap(sqlId);
		for (DataRow row : table) {
			if(proxyMap!=null){
				Object object = EntityUtil.newProxy(entityType,row,proxyMap);
				list.add(object);
			}else{
				Object object = EntityUtil.newInstance(entityType,row,this.getPropertyMap(sqlId));
				list.add(object);
			}
		}
		CodeTableHelper.linkCodeTable(list);
		return list;
	}
	
	
	/**
	 * 前几条
	 * @param sqlId
	 * @param top
	 * @param conditions
	 * @return
	 */
	public List<?> queryTopEntities(String sqlId,int top,Object ... conditions){
		DataTable table = this.queryData(sqlId,top,conditions);
		Class<?> entityType = (this.getResultClass(sqlId) != null) ? this
				.getResultClass(sqlId) : this.getEntityType();
		List<Object> list = new ArrayList<Object>();
		Map<String,String> proxyMap = getProxyPropertyMap(sqlId);
		for (DataRow row : table) {
			if(proxyMap!=null){
				Object object = EntityUtil.newProxy(entityType,row,proxyMap);
				list.add(object);
			}else{
				Object object = EntityUtil.newInstance(entityType,row,this.getPropertyMap(sqlId));
				list.add(object);
			}
		}
		CodeTableHelper.linkCodeTable(list);
		return list;
	}
	
	/**
	 * 查询返回单个实体对像
	 */
	public Object queryEntity(String sqlId, Object... conditions) {
		DataTable table = this.queryData(sqlId, conditions);
		if (table.rowSize() > 0) {
			Class<?> entityType = (this.getResultClass(sqlId) != null) ? this
					.getResultClass(sqlId) : this.getEntityType();
			DataRow row = table.getRow(0);
			Map<String,String> proxyMap = getProxyPropertyMap(sqlId);
			Object object = (proxyMap!=null)?EntityUtil.newProxy(entityType,row,proxyMap):
				EntityUtil.newInstance(entityType,row,this.getPropertyMap(sqlId));
			CodeTableHelper.linkCodeTable(object);
			return object;
		} else
			return null;
	}
	
	/**
	 * 返回代理类的,扩展映射
	 * @param sqlId
	 * @return
	 */
	protected Map<String,String> getProxyPropertyMap(String sqlId){
		SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
		if (item != null) {
			if (item.getResult() != null) {
				if(item.getResult().getProperties()!=null && item.getResult().getProperties().size()>0){
					Class<?> entityType = this.getResultClass(sqlId);
					if(entityType == null)
						return item.getResult().getProperties();
				}
			}
		}
		return null;
	}

	/**
	 * 得到返回结果类形
	 * @param sqlId
	 * @return
	 */
	protected Class<?> getResultClass(String sqlId) {
		SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
		if (item != null) {
			if (item.getResult() != null) {
				if (!StringUtil.isNullOrEmpty(item.getResult().getClassName())) {
					return ReflectorUtil.getClassForName(item.getResult()
							.getClassName());
				}
			}else if(!StringUtil.isNullOrEmpty(item.getResultClass())){
				return ReflectorUtil.getClassForName(item.getResultClass());
			}
		}
		return null;
	}
	
	protected Map<String, String> getPropertyMap(String sqlId) {
		SqlItem item = (SqlItem) this.getSqlMap().get(sqlId);
		if (item != null) {
			if (item.getResult() != null) {
				if (item.getResult().getProperties() != null) {
					return item.getResult().getProperties();
				}
			}
		}
		return null;
	}

	/**
	 * 产生一个新的数字Id
	 * 
	 * @return
	 */
	@Override
	public Long newId() {
		return this.getIdGenerator().getId(this.getEntityType(), this.dbConfig);
	}

	@Override
	public Long newId(Class<?> clazz) {
		return this.getIdGenerator().getId(clazz, this.dbConfig);
	}
	
	/**
	 * 产生多个新的数字Id
	 * 
	 * @return
	 */
	@Override
	public Long[] newId(int num){
		return this.getIdGenerator().getId(this.getEntityType(),num,this.dbConfig);
	}

	static class ArrayHelper {
		@SuppressWarnings("rawtypes")
		public static Object newArray(Class<?> arrayClass) {
			if (arrayClass.isInterface()) {
				if (arrayClass.isAssignableFrom(List.class)) {
					return new ArrayList();
				} else if (arrayClass.isAssignableFrom(Set.class)) {
					return new HashSet();
				}
			} else {
				return ReflectorUtil.newInstance(arrayClass);
			}
			return null;
		}
	}

	static class PageSizeHelper {
		public static int getLimit(int pagesize, int pagenum) {
			return pagesize * pagenum;
		}

		public static int getOffset(int pagesize, int pagenum) {
			return pagesize * (pagenum - 1);
		}
	}
	
	static class CodeTableHelper {
		
		protected static ICodeTable getCodeTable(String beanName){
			return (ICodeTable)SpringContext.instance().get(beanName);
		}
		
		
		public static void linkCodeTable(List<?> entities){
			if(entities!=null && entities.size()>0){
				Object entity = entities.get(0);
				PropertyInfo[] pis = ReflectorUtil.getProperties(entity.getClass());
				Map<String,PropertyInfo> cdMap = null;
				Map<String,String> itemMap = null;
				for(PropertyInfo pi : pis){
					if(pi.getField()!=null){
						CodeTable cd = (CodeTable)pi.getField().getAnnotation(CodeTable.class);
						if(cd!=null){
							if(!StringUtil.isNullOrEmpty(cd.name())){
								if(cdMap==null) cdMap = new HashMap<String,PropertyInfo>();
								if(itemMap==null) itemMap = new HashMap<String,String>();
								String cdKey = cd.name()+"_"+cd.params();
								cdMap.put(cdKey, pi);
								ICodeTable codeTable = getCodeTable(cd.name());
								if(codeTable==null)throw new CodeTableException(String.format("找不能代码表 {0} 配置",cd.name()));
								Object[] params = (!StringUtil.isNullOrEmpty(cd.params()))?cd.params().split(","):new Object[0];
								List<ICodeTable.IItem> items = codeTable.getItems(params);
								for(ICodeTable.IItem item : items){
									String itemKey = cdKey+"_"+item.getValue();
									itemMap.put(itemKey, item.getName());
								}
							}
						}
					}
				}
				
				if(cdMap!=null){
					CodeTable cd = null;
					for(int i=0;i<entities.size();i++){
						for(PropertyInfo pi : cdMap.values()){
							cd = (CodeTable)pi.getField().getAnnotation(CodeTable.class);
							Object val = ReflectorUtil.getPropertyValue(entities.get(i),cd.key());
							if(val!=null){
								if(val instanceof String) val = ((String)val).trim();
								if(!StringUtil.isNullOrEmpty(cd.split())){
									String[] vs = val.toString().split(cd.split(),-1);
									String text = "";
									for(String v : vs){
										String itemKey = cd.name()+"_"+cd.params()+"_"+v;
										if(itemMap.containsKey(itemKey)){
											text+=(text.length()>0)?cd.split()+itemMap.get(itemKey):itemMap.get(itemKey);
										}
									}
									pi.setValue(entities.get(i), text);
								}else{
									String itemKey = cd.name()+"_"+cd.params()+"_"+val;
									if(itemMap.containsKey(itemKey)){
										pi.setValue(entities.get(i), itemMap.get(itemKey));
									}
								}
							}
						}
					}
				}
			}
		}
		
		public static void linkCodeTable(Object entity){
			if(entity!=null){
				PropertyInfo[] pis = ReflectorUtil.getProperties(entity.getClass());
				Map<String,PropertyInfo> cdMap = null;
				Map<String,String> itemMap = null;
				for(PropertyInfo pi : pis){
					if(pi.getField()!=null){
						CodeTable cd = (CodeTable)pi.getField().getAnnotation(CodeTable.class);
						if(cd!=null){
							if(!StringUtil.isNullOrEmpty(cd.name())){
								if(cdMap==null) cdMap = new HashMap<String,PropertyInfo>();
								if(itemMap==null) itemMap = new HashMap<String,String>();
								String cdKey = cd.name()+"_"+cd.params();
								cdMap.put(cdKey, pi);
								ICodeTable codeTable = getCodeTable(cd.name());
								if(codeTable==null)throw new CodeTableException(String.format("找不能代码表 {0} 配置",cd.name()));
								Object[] params = (!StringUtil.isNullOrEmpty(cd.params()))?cd.params().split(","):new Object[0];
								List<ICodeTable.IItem> items = codeTable.getItems(params);
								for(ICodeTable.IItem item : items){
									String itemKey = cdKey+"_"+item.getValue();
									itemMap.put(itemKey, item.getName());
								}
							}
						}
					}
				}
				
				if(cdMap!=null){
					CodeTable cd = null;
					for(PropertyInfo pi : cdMap.values()){
						cd = (CodeTable)pi.getField().getAnnotation(CodeTable.class);
						Object val = ReflectorUtil.getPropertyValue(entity,cd.key());
						if(val!=null){
							if(val instanceof String) val = ((String)val).trim();
							if(!StringUtil.isNullOrEmpty(cd.split())){
								String[] vs = val.toString().split(cd.split(),-1);
								String text = "";
								for(String v : vs){
									String itemKey = cd.name()+"_"+cd.params()+"_"+v;
									if(itemMap.containsKey(itemKey)){
										text+=(text.length()>0)?cd.split()+itemMap.get(itemKey):itemMap.get(itemKey);
									}
								}
								pi.setValue(entity, text);
							}else{
								String itemKey = cd.name()+"_"+cd.params()+"_"+val;
								if(itemMap.containsKey(itemKey)){
									pi.setValue(entity, itemMap.get(itemKey));
								}
							}
						}
					}
				}
			}
		}
	}
}
