package banger.domain.enumerate;

/**
 * 数据同步类型
 * @author zhusw
 *
 */
public enum DataSyncType {
	 LOAN_TO_BIZ("LOAN_TO_BIZ", "贷款主表同步到子表"),
	 BIZ_TO_LOAN("BIZ_TO_LOAN", "贷款子表同步到主表"),
	 MARKET_TO_LOAN("MARKET_TO_LOAN", "预申请同步到贷款子表"),
	 ;
	 
	 public final String type; // 拒绝类型
	 public final String typeName; // 拒绝类型名称
	 
	 DataSyncType(String type,String typeName){
		 this.type = type;
		 this.typeName = typeName;
	 }
}
