package banger.domain.enumerate;

/**
 * 客户表和贷款表映射关系
 * @author zhusw
 *
 */
public enum TableSyncEnum {
	PERSONAL("CUST_BASIC_INFO","LBIZ_PERSONAL_INFO"),
	FAMILY("CUST_FAMILY_INFO","LBIZ_FAMILY_INFO"),
	SPOUSE("CUST_SPOUSE_INFO","LBIZ_SPOUSE_INFO"),
	;
	
	public final String sourceTableName;				//客户自定义表
	public final String targetTableName;					//贷款自定义表

	TableSyncEnum(String sourceTableName, String targetTableName) {
		this.sourceTableName = sourceTableName;
		this.targetTableName = targetTableName;
	}
	
	public static TableSyncEnum valueOfTarget(String targetTableName){
		for(TableSyncEnum tableSync : TableSyncEnum.values()){
			if(tableSync.targetTableName.equals(targetTableName)){
				return tableSync;
			}
		}
		return null;
	}
	
	public static TableSyncEnum valueOfSource(String sourceTableName){
		for(TableSyncEnum tableSync : TableSyncEnum.values()){
			if(tableSync.sourceTableName.equals(sourceTableName)){
				return tableSync;
			}
		}
		return null;
	}

	/**
	 * 得到反置的表名，如果找不到，返回当前表名
	 * @param tableName
	 * @return
	 */
	public String getReverseTableName(String tableName){
		TableSyncEnum tableSync = valueOfSource(tableName);
		if(tableSync==null){
			tableSync = valueOfTarget(tableName);
			if(tableSync==null){
				return tableName;
			}else{
				return tableSync.sourceTableName;
			}
		}else{
			return tableSync.targetTableName;
		}
	}

}
