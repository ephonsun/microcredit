package banger.domain.enumerate;

/**
 * 数据同步类型
 * @author zhusw
 *
 */
public enum LoanPersonTable {
	LBIZ_PERSONAL_INFO("LBIZ_PERSONAL_INFO", "贷款申请人信息"),
	LBIZ_JOINT_BORROWER("LBIZ_JOINT_BORROWER", "共同借款人"),
	LBIZ_LOAN_GUARANTEE("LBIZ_LOAN_GUARANTEE", "担保人");
	 
	 public final String table; // 拒绝类型
	 public final String tableName; // 拒绝类型名称
	 
	 LoanPersonTable(String table,String tableName){
		 this.table = table;
		 this.tableName = tableName;
	 }
}
