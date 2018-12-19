package banger.service.intf;

/**
 * 同频数据表里的列到另一张表
 * @author zhusw
 *
 */
public interface ITableSyncService {
	
	/**
	 * 得制所有客户表到贷款表
	 */
	void syncAllTableColumns();

	/**
	 * 从一张表，复制表结构到另一张表
	 * @param sourceTable	源数据表名
	 * @param targetTable	目标数据表名
	 */
	void syncTableColumns(String sourceTable,String targetTable);
	
	/**
	 * 读取表结构里的字段添加到自定义字段
	 */
	void refreshAllTableToField();
	
	/**
	 * 读取表结构里的字段添加到自定义字段
	 */
	void refreshTableToField(String tableName);
	
}
