package banger.framework.web.query;

import java.util.List;
import java.util.Map;

import banger.framework.entity.IEntityMap;
import banger.framework.entity.IPersistEntity;
import banger.framework.spring.SpringContext;
import banger.framework.sql.condition.ISqlCondition;
import banger.framework.util.StringUtil;
import banger.framework.web.layout.Field;

public class FormsQuery implements IFormsQuery {
	private IEntityMap entityMap;
	
	public IEntityMap getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(IEntityMap entityMap) {
		this.entityMap = entityMap;
	}
	
	/**
	 * 得到查询条件字符串
	 * @param entity 查询数据
	 * @param fields 字段集合
	 * @param tableAlias 表别名
	 * @return
	 */
	public String getConditions(IPersistEntity<?> entity,List<Field> fields,String tableAlias) {
		StringBuilder sql = new StringBuilder();
		Class<?> type = entity.getClass();
		for(Field field : fields){
			String query = field.getQuery();
			if(!StringUtil.isNullOrEmpty(query)){
				if(SpringContext.instance().contains(query)){
					Object bean = SpringContext.instance().get(query);
					if(bean instanceof ISqlCondition){
						ISqlCondition condition = (ISqlCondition)bean;
						String property = field.getName();
						String valueType = field.getValueType();
						String column = (!StringUtil.isNullOrEmpty(field.getColumn()))?field.getColumn()
								:entityMap.getColumn(type, field.getName());
						ISqlCondition.Args args = new ISqlCondition.Args(property,column,tableAlias,valueType);
						String part = condition.getCondition(entity, args);
						sql.append(part); 
					}
				}
			}
		}
		return sql.toString();
	}

	/**
	 * 得到查询条件字符串
	 * @param data 查询数据
	 * @param fields 字段集合
	 * @param type 实体类型
	 * @param tableAlias 表别名
	 * @return
	 */
	public String getConditions(Object data,List<Field> fields,Class<?> type,String tableAlias) {
		StringBuilder sql = new StringBuilder();
		for(Field field : fields){
			String query = field.getQuery();
			if(!StringUtil.isNullOrEmpty(query)){
				if(SpringContext.instance().contains(query)){
					Object bean = SpringContext.instance().get(query);
					if(bean instanceof ISqlCondition){
						ISqlCondition condition = (ISqlCondition)bean;
						String property = field.getName();
						String valueType = field.getValueType();
						String column = (!StringUtil.isNullOrEmpty(field.getColumn()))?field.getColumn()
								:entityMap.getColumn(type, field.getName());
						ISqlCondition.Args args = new ISqlCondition.Args(property,column,tableAlias,valueType);
						String part = condition.getCondition(data, args);
						sql.append(part); 
					}
				}
			}
		}
		return sql.toString();
	}
	
	/**
	 * 得到查询条件字符串
	 * @param data 查询数据
	 * @param fields 字段集合
	 * @param mapping 属性和列映射
	 * @param tableAlias 表别名
	 * @return
	 */
	public String getConditions(Object data,List<Field> fields,Map<String,String> mapping,String tableAlias) {
		StringBuilder sql = new StringBuilder();
		for(Field field : fields){
			String query = field.getQuery();
			if(!StringUtil.isNullOrEmpty(query)){
				if(SpringContext.instance().contains(query)){
					Object bean = SpringContext.instance().get(query);
					if(bean instanceof ISqlCondition){
						ISqlCondition condition = (ISqlCondition)bean;
						String property = field.getName();
						String valueType = field.getValueType();
						String column = mapping.get(property);
						ISqlCondition.Args args = new ISqlCondition.Args(property,column,tableAlias,valueType);
						String part = condition.getCondition(data, args);
						sql.append(part); 
					}
				}
			}
		}
		return sql.toString();
	}
	
}
