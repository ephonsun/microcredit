package banger.dao.impl;

import banger.dao.intf.ITableColumnDao;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableColumnQuery;
import banger.domain.config.IntoTemplatesField;
import banger.domain.config.ModeTemplateField;
import banger.domain.enumerate.EnumFiledType;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.sql.command.IDataReader;
import banger.framework.sql.command.ValueType;
import banger.framework.sql.dialect.IMetaSql;
import banger.framework.sql.meta.Column;
import banger.framework.sql.meta.Table;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;
import com.hundsun.common.lang.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class TableColumnDao extends PageSizeDao<AutoTableColumn> implements ITableColumnDao {

	/**
	 * 新增
	 * 
	 * @param tableColumn
	 *            实体对像
	 */
	public void insertTableColumn(AutoTableColumn tableColumn) {
		tableColumn.setFieldId(this.newId().intValue());
		this.execute("insertTableColumn", tableColumn);
	}

	/**
	 * 修改
	 * 
	 * @param tableColumn
	 *            实体对像
	 */
	public void updateTableColumn(AutoTableColumn tableColumn) {
		this.execute("updateTableColumn", tableColumn);
	}

	/**
	 * 通过主键删除
	 * 
	 * @param fieldId
	 *            主键Id
	 */
	public void deleteTableColumnById(Integer fieldId) {
		this.execute("deleteTableColumnById", fieldId);
	}

	/**
	 * 通过主键得到
	 * 
	 * @param fieldId
	 *            主键Id
	 */
	public AutoTableColumn getTableColumnById(Integer fieldId) {
		return (AutoTableColumn) this.queryEntity("getTableColumnById", fieldId);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> getAllTableColumnList() {
		return (List<AutoTableColumn>) this.queryEntities("getAllTableColumnList", "");
	}

	/**
	 * 得到条件字段
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> getConditionFieldList() {
		return (List<AutoTableColumn>) this.queryEntities("getConditionFieldList", "");
	}


	/**
	 * 得到条件字段
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoTableColumn> getAllActivedTableColumnList() {
		return (List<AutoTableColumn>) this.queryEntities("getAllActivedTableColumnList");
	}

	/**
	 * 得到条件字段
	 *
	 * @return
	 */
	public List<AutoTableColumn> getConditionFieldList(Integer loanType){
		return (List<AutoTableColumn>) this.queryEntities("getConditionFieldList", loanType);
	}

	/**
	 * 查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> getTableColumnListByTableName(String tableName) {
		return (List<AutoTableColumn>) this.queryEntities("getTableColumnListByTableName", tableName);
	}

	/**
	 * 通过表名和例名通到自定义字段
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public AutoTableColumn getTableColumnByTableAndColumn(String tableName,String columnName){
		return (AutoTableColumn) this.queryEntity("getTableColumnByTableAndColumn", tableName , columnName);
	}


	/**
	 * 查询
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> queryTableColumnList(Map<String, Object> condition) {
		return (List<AutoTableColumn>) this.queryEntities("queryTableColumnList", condition);
	}

	/**
	 * 分页查询
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoTableColumn> queryTableColumnList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<AutoTableColumn>) this.queryEntities("queryTableColumnList", page, condition);
	}

	/**
	 * 得到条件字段
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> getFieldSortListByTableIds(Integer[] tableIds) {
		return (List<AutoTableColumn>) this.queryEntities("getFieldSortListByTableIds", new Object[] { tableIds });
	}

	/**
	 * 得到条件字段
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> getFieldSortListByTableNames(String[] tableNames) {
		return (List<AutoTableColumn>) this.queryEntities("getFieldSortListByTableNames", new Object[] { tableNames });
	}

	/**
	 * 上移下移
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> queryTableColumnByConditionOrder(Map<String, Object> condition) {
		return (List<AutoTableColumn>) this.queryEntities("queryTableColumnByConditionOrder", condition);
	}
	
	/**
	 * 得到表字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumn> getFieldListByTableName(String tableName){
		return (List<AutoTableColumn>) this.queryEntities("getFieldListByTableName", tableName);
	}

	@Override
	public List<AutoTableColumn> getFieldSortListByLoanType(String loanTypeId, String precType,Integer tableId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("loanTypeId",loanTypeId);
		condition.put("precType",precType);
		condition.put("tableId",tableId);
		return (List<AutoTableColumn>) this.queryEntities("getFieldSortListByLoanType", condition);
	}

	@Override
	public List<ModeTemplateField> queryModelTemplateInfoColumn(Map<String, Object> condition) {
		return (List<ModeTemplateField>) this.queryEntities("queryModelTemplateInfoColumn",condition);
	}

	@Override
	public List<IntoTemplatesField> queryIntoTemplatesInfoColumn(Map<String, Object> condition) {
		return (List<IntoTemplatesField>) this.queryEntities("queryIntoTemplatesInfoColumn",condition);
	}

	@Override
	public List<AutoTableColumn> queryTableColumnListOfCust() {
		return (List<AutoTableColumn>) this.queryEntities("queryTargetTabInfo");
	}

	@Override
	public AutoTableColumn getTemplateTableByTableId(Integer tableId) {
		return (AutoTableColumn) this.queryEntity("getTableColumnByTableId",  tableId);
	}

	/**
	 * 添加表字段
	 * @return
	 *///
	@Override
	public void addTableField(String tableName, String fieldColumn, String fieldName, String fieldType,Integer length) {
		ISqlHelper ish = SqlHelper.instance();
		IMetaSql metaSql = ish.getDialect().getMetaSql();

		String sqlString = "";
		EnumFiledType enumFiledType = EnumFiledType.valueOfType(fieldType);
		if(enumFiledType!=null){
			if(EnumFiledType.RATIO.equalType(fieldType)) {
				sqlString = metaSql.getAddTableColumnSql(tableName, fieldColumn, length, 6, enumFiledType.valueType);
			}else{
				sqlString = metaSql.getAddTableColumnSql(tableName, fieldColumn, length, 2, enumFiledType.valueType);
			}
		}
//		if("Text".equals(fieldType)){
//			sqlString = metaSql.getAddTableColumnSql(tableName, fieldName, 90, 0 , ValueType.Varchar);

		if(StringUtil.isNotEmpty(sqlString)){
			ish.executeNoneQuery(sqlString);
		}

		sqlString = metaSql.getCommentTableColumnSql(tableName, fieldColumn, fieldName);

		if(StringUtil.isNotEmpty(sqlString)){
			ish.executeNoneQuery(sqlString);
		}
	}
	/**
	 * 修改列务注
	 *
	 * @param tableName
	 *
	 *
	 */
	@Override
	public void commentTableColumn(String tableName, String fieldColumn, String fieldName) {
		ISqlHelper ish = SqlHelper.instance();
		IMetaSql metaSql = ish.getDialect().getMetaSql();
		String sqlString = metaSql.getCommentTableColumnSql(tableName,fieldColumn,fieldName);
		if(StringUtil.isNotEmpty(sqlString)){
			ish.executeNoneQuery(sqlString);
		}
	}
	//插入自定义字段表
	@Override
	public void insertTemplateField(AutoTableColumn autoTableColumn) {
		autoTableColumn.setFieldId(this.newId().intValue());
		this.execute("insertTableColumn",autoTableColumn);
	}

	@Override
	public Integer queryTableColumnNum(Map<String, Object> condition) {
		AutoTableColumn autoTableColumn = (AutoTableColumn)this.queryEntity("queryTableColumnNum",condition);
		return autoTableColumn.getFieldSortno();
	}
	/**
	 * 查询所有自定义字段数据包括表名描述
	 * @return
	 */
	@Override
	public List<AutoTableColumnQuery> getAllTableColumnQueryList() {
		return (List<AutoTableColumnQuery>) this.queryEntities("getAllTableColumnQueryList", "");
	}

	/**
	 * @Author: yangdw
	 * @param tableName 表名
	 * @param tableColumn 列名
	 * @Description:返回为true表示数据表中存在当前字段
	 * @Date: 10:22 2017/11/15
	 */
	public boolean checkedFieldIsExist(String tableName, String tableColumn) {
		String sqlString  = "SELECT * FROM "+tableName+" WHERE 1=0";
		ISqlHelper ish = SqlHelper.instance();
		IDataReader reader = ish.getDataReader(sqlString);
		Table table = new Table();
		table.setName(tableName);
		List<Column> columns = reader.getColumns();
		reader.close();
		ish.dispose();
		if(columns!=null && columns.size()>0){
			for(Column column : columns){
				if (column.getName().equals(tableColumn)) {
					return true;
				}
			}
		}
		return false;
	}

}
