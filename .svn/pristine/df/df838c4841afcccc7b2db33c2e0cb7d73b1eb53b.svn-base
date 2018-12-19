package banger.framework.metadata;

import banger.framework.sql.meta.Column;
import banger.framework.sql.meta.Table;

public interface IDbMetaStore {

	/**
	 * 
	 * @param tableName
	 * @return
	 */
	Table getTable(String tableName);
	
	/**
	 * 得到例信息
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	Column getColumn(String tableName,String columnName);

	/**
	 * 清除元数据缓存
	 * @param tableName
	 */
	void clear(String tableName);
	
}
