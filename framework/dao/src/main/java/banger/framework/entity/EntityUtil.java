package banger.framework.entity;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import banger.framework.dao.EntityMappingException;
import banger.framework.reader.Reader;
import banger.framework.reflector.PropertyInfo;
import banger.framework.spring.SpringContext;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;

public class EntityUtil {
	
	private static final String EntityMapKey = "EntityMap";
	private static Log log = LogFactory.getLog(EntityUtil.class);
	
	
	public static IEntityMap getEntityMap(){
		return (IEntityMap)SpringContext.instance().get(EntityMapKey);
	}
	
	static Class<?> getPropertyType(Object entity,String propertyName){
		Class<?> type = entity.getClass();
		String property = getProperty(type,propertyName);
		PropertyInfo pi = ReflectorUtil.getProperty(type, property);
		if(pi!=null){
			return pi.getType();
		}
		return null;
	}
	
	static String getProperty(Class<?> type,String propertyName){
		String property = getEntityMap().getProperty(type, propertyName);
		if(StringUtil.isNullOrEmpty(property))property = propertyName;
		return property;
	}
	
	static Object getPropertyValue(Object entity,String propertyName){
		Class<?> type = entity.getClass();
		String property = getProperty(type,propertyName);
		if(ReflectorUtil.containProperty(type,property)){
			return ReflectorUtil.getPropertyValue(entity,property);
		}
		if(log.isWarnEnabled())log.warn(StringUtil.format("实体对像 {0} 找不到该属性 {1}",type.getName(),property));
		return null;
	}
	
	static void setPropertyValue(Object entity,String propertyName,Object propertyValue){
		Class<?> type = entity.getClass();
		String property = getProperty(type,propertyName);
		if(ReflectorUtil.containProperty(type,property)){
			ReflectorUtil.setPropertyValue(entity, property, propertyValue);
		}
		else throw new EntityMappingException(StringUtil.format("实体对像 {0} 找不到该属性 {1} ",type.getName(),property));
	}
	
	public static void copyProperties(Object entity,Object data){
		if(entity!=null){
			Class<?> type = entity.getClass();
			while(type!=null){
				if(getEntityMap().containKey(type)){
					Map<String,String> propertyMap = getEntityMap().getMap(type);
					copyProperties(entity,data,propertyMap);
					break;
				}else type = type.getSuperclass();
			}
		}
	}
	
	public static void copyProperties(Class<?> type,Object entity,Object data){
		if(entity!=null){
			if(getEntityMap().containKey(type)){
				Map<String,String> propertyMap = getEntityMap().getMap(type);
				copyProperties(entity,data,propertyMap);
			}
		}
	}
	
	public static void copyProperties(Object entity,Object data,Map<String,String> propertyMap){	
		if(entity!=null){
			if(propertyMap!=null){
				for(String propertyName : propertyMap.keySet()){
					 String columnName = propertyMap.get(propertyName);
					 Object value = Reader.objectReader().getValue(data,columnName);
			         if(value==null)value = Reader.objectReader().getValue(data,propertyName);
			         ReflectorUtil.setPropertyValue(entity, propertyName, value);
				}
			}
		}
	}
	
	/**
	 * 生成代理对像实例，并拷贝属性值
	 * @param entityType
	 * @param data
	 * @param propertyMap
	 * @return
	 */
	public static Object newProxy(Class<?> entityType,Object data,Map<String,String> propertyMap){
		Object object = ReflectorUtil.newProxy(entityType,propertyMap.keySet());
		EntityUtil.copyProperties(object, data );
		EntityUtil.copyProperties(object, data ,propertyMap);
		return object;
	}
	
	/**
	 * 产生对像实例，并拷贝属性值
	 * @param entityType
	 * @param data
	 * @param propertyMap
	 * @return
	 */
	public static Object newInstance(Class<?> entityType,Object data,Map<String,String> propertyMap){
		Object object = ReflectorUtil.newInstance(entityType);
		EntityUtil.copyProperties(object, data );
		EntityUtil.copyProperties(object, data ,propertyMap);
		return object;
	}
	
	static boolean containProperty(Object entity,String propertyName){
		Class<?> type = entity.getClass();
		String property = getProperty(type,propertyName);
		return ReflectorUtil.containProperty(type, property);
	}

}
