package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableColumnQuery;
import banger.domain.config.IntoTemplatesField;
import banger.domain.config.ModeTemplateField;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 数据访问接口
 */
public interface ITableColumnDao {

	/**
	 * 新增
	 * 
	 * @param tableColumn
	 *            实体对像
	 */
	void insertTableColumn(AutoTableColumn tableColumn);

	/**
	 * 修改
	 * 
	 * @param tableColumn
	 *            实体对像
	 */
	void updateTableColumn(AutoTableColumn tableColumn);

	/**
	 * 通过主键删除
	 * 
	 * @param fieldId
	 *            主键Id
	 */
	void deleteTableColumnById(Integer fieldId);

	/**
	 * 通过主键得到
	 * 
	 * @param fieldId
	 *            主键Id
	 */
	AutoTableColumn getTableColumnById(Integer fieldId);

	/**
	 * 查询
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableColumn> getAllTableColumnList();

	/**
	 * 得到条件字段
	 * 
	 * @return
	 */
	List<AutoTableColumn> getConditionFieldList();

	@SuppressWarnings("unchecked")
	List<AutoTableColumn> getAllActivedTableColumnList();

	/**
	 * 得到条件字段
	 *
	 * @return
	 */
	List<AutoTableColumn> getConditionFieldList(Integer loanType);

	/**
	 * 得到条件字段
	 * 
	 * @return
	 */
	List<AutoTableColumn> getFieldSortListByTableIds(Integer[] tableIds);

	/**
	 * 得到条件字段
	 * 
	 * @return
	 */
	List<AutoTableColumn> getFieldSortListByTableNames(String[] tableNames);
	
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

	/**
	 * 查询
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableColumn> getTableColumnListByTableName(String tableName);

	/**
	 * 查询
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableColumn> queryTableColumnList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<AutoTableColumn> queryTableColumnList(Map<String, Object> condition, IPageSize page);

	/**
	 * 上移下移
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableColumn> queryTableColumnByConditionOrder(Map<String, Object> condition);

	List<AutoTableColumn> getFieldSortListByLoanType(String loanTypeId, String precType, Integer tableId);

	List<ModeTemplateField> queryModelTemplateInfoColumn(Map<String, Object> condition);

	List<IntoTemplatesField> queryIntoTemplatesInfoColumn(Map<String, Object> condition);
	/**
	 * 查询用户自定义表
	 * @return
	 */
	List<AutoTableColumn> queryTableColumnListOfCust();

	/**
	 * 通过表tableId获取自定义表信息
	 * @return
	 */
    AutoTableColumn getTemplateTableByTableId(Integer tableId);

	/**
	 * 添加表字段
	 * @return
	 *///
    void addTableField(String tableName, String fieldColumn, String fieldName, String fieldType,Integer length);
	/**
	 * 修改列务注
	 *
	 * @param tableName
	 *
	 */
	void commentTableColumn(String tableName, String fieldColumn, String fieldName);
	//插入自定义字段表
    void insertTemplateField(AutoTableColumn autoTableColumn);

	Integer queryTableColumnNum(Map<String, Object> condition);
	/**
	 * 查询所有自定义字段数据包括表名描述
	 * @return
	 */
    List<AutoTableColumnQuery> getAllTableColumnQueryList();

	boolean checkedFieldIsExist(String tableName, String tableColumn);

}
