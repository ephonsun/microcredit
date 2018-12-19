package banger.moduleIntf;

import banger.domain.config.AutoTableColumn;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段接口
 * @author zhusw
 *
 */
public interface IAutoFieldProvider {

	/**
	 * 得到审核条件字段
	 * @return
	 */
	List<AutoTableColumn> getConditionFieldList();

	List<AutoTableColumn> getAllActivedTableColumnList();

	/**
	 * 得到审核条件字段
	 * @param loanType
	 * @return
	 */
	List<AutoTableColumn> getConditionFieldList(Integer loanType);
	
	/**
	 * 得到字段信息
	 * @param fieldId
	 * @return
	 */
	AutoTableColumn getTableColumnById(Integer fieldId);
	
	/**
	 * 得到表字段
	 * @return
	 */
	List<AutoTableColumn> getFieldListByTableName(String tableName);

	/**
	 * 通过表名和例名通到自定义字段
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	AutoTableColumn getTableColumnByTableAndColumn(String tableName,String columnName);

	List<AutoTableColumn> queryTableColumnList(Map<String, Object> map);

	/**
	 * 获取所有自定义字段
	 * @return
	 */
	List<AutoTableColumn> getAllTableColumnList();
}
