package banger.framework.web.query;

import java.util.List;
import java.util.Map;

import banger.framework.entity.IPersistEntity;
import banger.framework.web.layout.Field;

public interface IFormsQuery {
	
	/**
	 * 得到查询条件字符串
	 * @param entity 查询数据
	 * @param fields 字段集合
	 * @param tableAlias 表别名
	 * @return
	 */
	String getConditions(IPersistEntity<?> entity,List<Field> fields,String tableAlias);
	
	/**
	 * 得到查询条件字符串
	 * @param data 查询数据
	 * @param fields 字段集合
	 * @param type 实体类型
	 * @param tableAlias 表别名
	 * @return
	 */
	String getConditions(Object data,List<Field> fields,Class<?> type,String tableAlias);
	
	/**
	 * 得到查询条件字符串
	 * @param data 查询数据
	 * @param fields 字段集合
	 * @param mapping 属性和列映射
	 * @param tableAlias 表别名
	 * @return
	 */
	String getConditions(Object data,List<Field> fields,Map<String,String> mapping,String tableAlias);
	
}
