package banger.framework.entity;

import banger.framework.reader.DefaultObjectReader;
import banger.framework.spring.SpringContext;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;

public class EntityReader extends DefaultObjectReader  {
	
	private static final String EntityMapKey = "EntityMap";
	private IEntityMap entityMap;
	
	protected IEntityMap getEntityMap(){
		if(entityMap==null)
			entityMap = (IEntityMap)SpringContext.instance().get(EntityMapKey);
		return entityMap;
	}
	
	public Object getValue(Object entity, String propertyName) {
		if(entity instanceof IPersistEntity){
			return ((IPersistEntity<?>)entity).getPropertyValue(propertyName);
		}else{
			if(entity != null) {
				if(this.getEntityMap().containKey(entity.getClass())){
					String property = getEntityMap().getProperty(entity.getClass(), propertyName);
					if(StringUtil.isNullOrEmpty(property))property = propertyName;
					if(ReflectorUtil.containProperty(entity.getClass(),property)) {
						return ReflectorUtil.getPropertyValue(entity,property);
					}else return null;
				}
			}
			return super.getValue(entity, propertyName);
		}
	}

	public Object getValue(Object entity, int propertyIndex) {
		if(entity instanceof IPersistEntity){
			throw new RuntimeException("实体对像属性不支持,索引读取");
		}else{
			return super.getValue(entity, propertyIndex);
		}
	}
}
