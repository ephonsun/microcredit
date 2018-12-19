package banger.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.common.constant.SyncFilterConst;
import banger.dao.intf.ITableColumnDao;
import banger.dao.intf.ITableInfoDao;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.domain.config.MetaTableColumn;
import banger.domain.enumerate.EnumFiledType;
import banger.domain.enumerate.TableSyncEnum;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.reader.Reader;
import banger.framework.sql.dialect.IMetaSql;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;
import banger.framework.util.StringUtil;
import banger.service.intf.ITableSyncService;

/**
 * 同频数据表里的列到另一张表
 * @author zhusw
 *
 */
@Repository
public class TableSyncService implements ITableSyncService {
	@Autowired
	ITableColumnDao tableColumnDao;
	@Autowired
	ITableInfoDao tableInfoDao;

	
	/**
	 * 得制所有客户表到贷款表
	 */
	public void syncAllTableColumns(){
		
		for(TableSyncEnum sync : TableSyncEnum.values()){
			syncTableColumns(sync.sourceTableName,sync.targetTableName);
		}
	}

	/**
	 * 从一张表，复制表结构到另一张表
	 * @param sourceTable	源数据表名
	 * @param targetTable	目标数据表名
	 */
	public void syncTableColumns(String sourceTable, String targetTable) {
		if(!isExistTable(targetTable)){
			createTable(targetTable);
		}
		
		List<MetaTableColumn> list = getAddColumnList(sourceTable,targetTable);
		createColumns(list);
	}
	
	/**
	 * 读取表结构里的字段添加到自定义字段
	 */
	public void refreshAllTableToField(){
		List<AutoTableInfo> list = tableInfoDao.queryAllTableInfoList();
		for(AutoTableInfo tableInfo : list){
			refreshTableToField(tableInfo.getTableDbName());
		}
	}
	
	/**
	 * 读取表结构里的字段添加到自定义字段
	 */
	public void refreshTableToField(String tableName) {
		List<AutoTableColumn> list = getAddFieldList(tableName);
		for(AutoTableColumn column : list){
			tableColumnDao.insertTableColumn(column);
		}
	}
	
	/**
	 * 得到需要添加的自定义字段
	 * @param tableName
	 * @return
	 */
	private List<AutoTableColumn> getAddFieldList(String tableName){
		List<MetaTableColumn> srcList = getTableColumnList(tableName);
		Map<String,AutoTableColumn> map = getTableFieldMap(tableName);
		List<AutoTableColumn> list = new ArrayList<AutoTableColumn>();
		int sortNo = getMaxSortNo(map.values());
		for(MetaTableColumn column : srcList){
			if(!map.containsKey(column.getColumnName())){
				AutoTableColumn col = buildFieldData(column);
				col.setFieldSortno(++sortNo);
				list.add(col);
			}
		}
		return list;
	}

	private Integer getMaxSortNo(Collection<AutoTableColumn> columns){
		int sortNo = 0;
		for(AutoTableColumn column : columns){
			if(column.getFieldSortno()!=null && column.getFieldSortno().intValue()>sortNo)
				sortNo = column.getFieldSortno();
		}
		return sortNo;
	}
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	private Map<String,AutoTableColumn> getTableFieldMap(String tableName){
		List<AutoTableColumn> list = tableColumnDao.getTableColumnListByTableName(tableName);
		Map<String,AutoTableColumn> map = new HashMap<String,AutoTableColumn>();
		for(AutoTableColumn column : list){
			map.put(column.getFieldColumn(), column);
		}
		return map;
	}
	
	/**
	 * 得到需要添加的列
	 * @param sourceTable
	 * @param targetTable
	 * @return
	 */
	private List<MetaTableColumn> getAddColumnList(String sourceTable, String targetTable){
		List<MetaTableColumn> srcList = getTableColumnList(sourceTable);
		Map<String,MetaTableColumn> tarMap = getTableColumnMap(targetTable);
		List<MetaTableColumn> list = new ArrayList<MetaTableColumn>();
		for(MetaTableColumn column : srcList){
			if(!tarMap.containsKey(column.getColumnName())){
				column.setTableName(targetTable);
				list.add(column);
			}
		}
		return list;
	}
	
	/**
	 * 得到表字段的Map
	 * @param tableName
	 * @return
	 */
	private Map<String,MetaTableColumn> getTableColumnMap(String tableName){
		List<MetaTableColumn> list = getTableColumnList(tableName);
		Map<String,MetaTableColumn> map = new HashMap<String,MetaTableColumn>();
		for(MetaTableColumn column : list){
			map.put(column.getColumnName(), column);
		}
		return map;
	}
	
	/**
	 * 得到表字段的列
	 * @param tableName
	 * @return
	 */
	private List<MetaTableColumn> getTableColumnList(String tableName){
		ISqlHelper ish = SqlHelper.instance();
		IMetaSql meta = ish.getDialect().getMetaSql();
		String fieldSql = meta.getFieldsSql(tableName);
		DataTable table = ish.getDataTable(fieldSql);
		List<MetaTableColumn> list = new ArrayList<MetaTableColumn>();
		if(table!=null && table.rowSize() > 0){
			for(DataRow row : table){
				String table_name = Reader.stringReader().getValue(row,"TABLE_NAME");
				String field_name = Reader.stringReader().getValue(row,"FIELD_NAME");
				String field_type = Reader.stringReader().getValue(row,"FIELD_TYPE");
				Integer field_length = Reader.intReader().getValue(row,"FIELD_LENGTH");
				Integer field_scale = Reader.intReader().getValue(row, "FIELD_SCALE");
				String comments = Reader.stringReader().getValue(row, "FIELD_REMARK");
				
				if(isUserField(field_name,field_type,field_length,field_scale)){
					MetaTableColumn column = new MetaTableColumn();
					column.setTableName(table_name);
					column.setColumnName(field_name);
					column.setLength(field_length);
					column.setScale(field_scale);
					column.setComments(comments);
					column.setColumnType(field_type);
					EnumFiledType type = getColumnType(field_type,field_length,field_scale);
					column.setType(type.valueType);
					list.add(column);
				}
				
			}
		}
		return list;
	}
	
	private boolean isUserField(String fieldName,String fieldType,Integer length,Integer scale){
		if(isFilterFieldName(fieldName)){
			return false;
		}else{
			EnumFiledType type = getColumnType(fieldType,length,scale);
			return type!=null;
		}
	}
	
	private boolean isFilterFieldName(String fieldName){
		String[] custFilterFields = SyncFilterConst.CUSTOMER_FILTER_FIELDS.split("\\,");
		String[] loanFilterFields = SyncFilterConst.LOAN_FILTER_FIELDS.split("\\,");
		for(String filter : custFilterFields){
			if(filter.equals(fieldName)){
				return true;
			}
		}
		for(String filter : loanFilterFields){
			if(filter.equals(fieldName)){
				return true;
			}
		}
		return false;
	}
	
	private EnumFiledType getColumnType(String fieldType,int length,int scale){
		if("VARCHAR".equalsIgnoreCase(fieldType)){
			if(inEqualArray(EnumFiledType.TEXT.dataLengths,length)){
				return EnumFiledType.TEXT;
			}else if(inEqualArray(EnumFiledType.TEXTAREA.dataLengths,length)){
				return EnumFiledType.TEXTAREA;
			}else if(EnumFiledType.SELECT.dataLength==length){
				return EnumFiledType.SELECT;
			}else if(EnumFiledType.MULTIPLE_SELECT.dataLength==length){
				return EnumFiledType.MULTIPLE_SELECT;
			}else if(EnumFiledType.YES_NO.dataLength==length){
				return EnumFiledType.YES_NO;
			}else if(EnumFiledType.ADDRESS.dataLength==length){
				return EnumFiledType.ADDRESS;
			}else if(EnumFiledType.QUEST.dataLength==length){
				return EnumFiledType.QUEST;
			}
		}else if("TIMESTAMP".equalsIgnoreCase(fieldType)){
			return EnumFiledType.DATE;
		}else if("INTEGER".equalsIgnoreCase(fieldType) || "NUMBER".equalsIgnoreCase(fieldType) || "DECIMAL".equalsIgnoreCase(fieldType) || "FLOAT".equalsIgnoreCase(fieldType)){
			if(EnumFiledType.NUMBER.dataLength==length){
				return EnumFiledType.NUMBER;
			}else if(EnumFiledType.DECIMAL.dataLength==length){
				return EnumFiledType.DECIMAL;
			}else if(EnumFiledType.RATIO.dataLength==length){
				return EnumFiledType.RATIO;
			}else if(EnumFiledType.FLOAT.dataLength==length){
				return EnumFiledType.RATIO;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param zhusw
	 * @return
	 */
	private boolean inEqualArray(int[] dataLengths,int length){
		for(int dataLength : dataLengths){
			if(dataLength==length)
				return true;
		}
		return false;
	}
	
	/**
	 * 判断表是否存在
	 * @param tableName 表名
	 * @return
	 */
	private boolean isExistTable(String tableName){
		ISqlHelper ish = SqlHelper.instance();
		IMetaSql meta = ish.getDialect().getMetaSql();
		String selectTableSql = meta.getTablesSql(tableName);
		DataTable table = ish.getDataTable(selectTableSql);
		if(table!=null && table.rowSize()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 创建列
	 * @param tableName
	 */
	private void createTable(String tableName){
		ISqlHelper ish = SqlHelper.instance();
		IMetaSql meta = ish.getDialect().getMetaSql();
		String createTableSql = meta.getCreateTableSql(tableName, "ID", new String[]{"LOAN_ID INTEGER","LOAN_PROCESS_TYPE VARCHAR(32)"});
		ish.executeNoneQuery(createTableSql);
	}
	
	/**
	 * 创建表
	 * @param columns
	 */
	private void createColumns(List<MetaTableColumn> columns){
		ISqlHelper ish = SqlHelper.instance();
		IMetaSql meta = ish.getDialect().getMetaSql();
		for(MetaTableColumn column : columns){
			String createColumnSql = meta.getAddTableColumnSql(column.getTableName(), column.getColumnName(), column.getLength(), column.getScale(), column.getType());
			ish.executeNoneQuery(createColumnSql);
			if(StringUtil.isNotEmpty(column.getComments())){
				String commentColumnSql = meta.getCommentTableColumnSql(column.getTableName(), column.getColumnName(),column.getComments());
				ish.executeNoneQuery(commentColumnSql);
			}
		}
	}
	
	/**
	 * 创建一个自定义字段对像
	 * @param column
	 * @return
	 */
	private AutoTableColumn buildFieldData(MetaTableColumn column){
		
		AutoTableColumn col = new AutoTableColumn();
		col.setTableName(column.getTableName());
		col.setFieldColumn(column.getColumnName());
		
		col.setFieldAppIsShow(1);
		col.setFieldWebIsShow(1);
		col.setFieldIsQuery(0);
		col.setFieldIsRequired(0);
		col.setIsActived(1);
		col.setIsFixed(0);
		
		col.setCreateUser(1);
		col.setUpdateUser(1);
		col.setCreateDate(new Date());
		col.setUpdateDate(new Date());
		col.setFieldLength(column.getLength());
		
		String fieldName = getNameByComments(column.getComments());
		col.setFieldName(fieldName);
		
		col.setFieldColumnDisplay(fieldName);
		
		AutoTableInfo tableInfo = tableInfoDao.getTableInfoByTableName(column.getTableName());
		if(tableInfo!=null){
			col.setTableId(tableInfo.getTableId());
		}
		
		EnumFiledType type = getColumnType(column.getColumnType(),column.getLength(),column.getScale());
		if(type!=null){
			col.setFieldType(type.fieldType);
			
			if(type==EnumFiledType.SELECT || type==EnumFiledType.MULTIPLE_SELECT){
				String dictName = getDataDictName(column.getColumnName());
				if(StringUtil.isNotEmpty(dictName)){
					col.setFieldDictName(dictName);
				}
			}
		}
		
		return col;
		
	}
	
	/**
	 * 得到数据字典名
	 * @param columnName 列名
	 * @return
	 */
	private String getDataDictName(String columnName){
		String dictName = "CD"+"_"+columnName;
		return dictName;
	}
	
	/**
	 * 通过注解取字段名
	 * @param comments
	 * @return
	 */
	private String getNameByComments(String comments){
		if(StringUtil.isNotEmpty(comments)){
			String str = comments;
			int l = str.indexOf("\n");
			if(l>0){
				str = str.substring(0,l);
			}
			int n = str.indexOf(" ");
			if(n>0){
				str = str.substring(0,n);
			}
			int m = str.indexOf("\t");
			if(m>0){
				str = str.substring(0,m);
			}
			return str;
		}
		return "";
	}


	
}
