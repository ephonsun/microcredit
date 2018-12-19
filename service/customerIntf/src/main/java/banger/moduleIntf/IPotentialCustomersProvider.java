package banger.moduleIntf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.permission.SysTeamMember;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustPotentialCustomers;

/**
 * 潜在客户表业务访问接口(外部接口)
 */
public interface IPotentialCustomersProvider {

	/**
	 * 新增潜在客户表
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertPotentialCustomers(CustPotentialCustomers potentialCustomers, Integer loginUserId);

	/**
	 *修改潜在客户表
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updatePotentialCustomers(CustPotentialCustomers potentialCustomers, Integer loginUserId);

	/**
	 * 通过主键删除潜在客户表
	 * @param ID 主键Id
	 */
	boolean deletePotentialCustomersById(Integer id);

	/**
	 * 通过主键得到潜在客户表
	 * @param ID 主键Id
	 */
	CustPotentialCustomers getPotentialCustomersById(Integer id);

	/**
	 * 查询潜在客户表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustPotentialCustomers> queryPotentialCustomersList(Map<String, Object> condition);

	/**
	 * 分页查询潜在客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustPotentialCustomerQuery> queryPotentialCustomersList(Map<String, Object> condition, IPageSize page);

	Integer getNewId();
	/**
	 *
	 *客户唯一性校验
	 */
	boolean isUniqueCustomer(String s, String customerName, String cardType, String cardNumber);
	/**
	 *
	 *客户详细信息
	 */
	CustPotentialCustomerQuery getPotentialCustomersQueryById(Integer id);

	SysTeamMember getTeamIdByUserId(Integer userId);

	//通过产品编号清空潜在客户的意向产品
	void updatePotentialCustomersByProductCode(String productCode);
}
