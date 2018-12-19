package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustCustomerCreditCheck;
import banger.domain.customer.CustCustomerCreditCheckQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 客户征信调查表数据访问接口
 */
public interface ICustCustomerCreditCheckDao {

	/**
	 * 新增客户征信调查表
	 * @param custCustomerCreditCheck 实体对像
	 */
	void insertCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck,boolean isapp);

	/**
	 *修改客户征信调查表
	 * @param custCustomerCreditCheck 实体对像
	 */
	void updateCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck);

	/**
	 * 通过主键删除客户征信调查表
	 * @param customerCreditCheckId 主键Id
	 */
	void deleteCustCustomerCreditCheckById(Integer customerCreditCheckId);

	/**
	 * 通过主键得到客户征信调查表
	 * @param customerCreditCheckId 主键Id
	 */
	CustCustomerCreditCheckQuery getCustCustomerCreditCheckById(Integer customerCreditCheckId);

	/**
	 * 查询客户征信调查表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustCustomerCreditCheckQuery> queryCustCustomerCreditCheckList(Map<String,Object> condition);

	/**
	 * 分页查询客户征信调查表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustCustomerCreditCheckQuery> queryCustCustomerCreditCheckList(Map<String,Object> condition,IPageSize page);
	
	/**
	 * 根据姓名、身份证号码查询
	 * @param condition
	 * @return
	 */
	CustCustomerCreditCheck queryByNameAndNo(Map<String,Object> condition);
	
	/**
	 * 根据贷款id查询该笔贷款提交的征信调查
	 * @param loanId
	 * @return
	 */
	List<CustCustomerCreditCheck> getCustomerCreditCheckByLoanId(Integer loanId);

}
