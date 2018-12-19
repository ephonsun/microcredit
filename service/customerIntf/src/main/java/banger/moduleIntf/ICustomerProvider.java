package banger.moduleIntf;

import banger.domain.customer.Customer;
import banger.framework.collection.DataTable;

/**
 * 提供客户数据查询接口
 * @author zhusw
 *
 */
public interface ICustomerProvider {

	/**
	 * 通过条件查询客户id
	 * @param customerName 客户姓名
	 * @param identifyType 证件类型
	 * @param identifyNum 证件号码
	 * @return
	 */
	Integer getCustomerIdByCardNameType(String customerName,String identifyType,String identifyNum);
	
	/**
	 * 贷款申请保存客户
	 */
	void insertCustomerByLoanApply(Customer customer);

	/**
	 * 更新客户的电话号码
	 * @param telNum
	 * @param customerId
	 */
	void upldateCustomerTelNumById(String telNum,Integer customerId);
	
	/**
	 * 新增客户资料
	 * @param table
	 */
	void insertCustomerDataByLoanApply(DataTable table);
	
	/**
	 * 更新客户资料
	 * @param table
	 */
	void updateCustomerDataByLoanApply(DataTable table);

	/**
	 * 更新客户归属人
	 * @param id
	 * @param belongId
	 */
	void updateBelongId(Integer id, Integer belongId);
}
