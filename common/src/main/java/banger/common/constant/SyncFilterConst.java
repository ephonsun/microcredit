package banger.common.constant;

/**
 * 同步数据排除字段
 * @author Administrator
 *
 */
public class SyncFilterConst {

	/**
	 * 客户过滤字段
	 */
	public final static String CUSTOMER_FILTER_FIELDS = "ID,BELONG_USER_ID,IS_DEL,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE";
	
	/**
	 * 贷款过滤字段
	 */
	public final static String LOAN_FILTER_FIELDS = "ID,LOAN_ID,LOAN_PROCESS_TYPE,LOAN_PROCESS_TYPE,IS_MASTER";
	
}
