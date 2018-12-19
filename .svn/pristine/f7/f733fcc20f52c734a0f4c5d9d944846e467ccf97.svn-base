package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustMarketCustomer;
import banger.domain.customer.CustMarketCustomerQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 营销客户表业务访问接口
 */
public interface ICustMarketCustomerService {

	/**
	 * 新增营销客户表
	 * @param custMarketCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCustMarketCustomer(CustMarketCustomer custMarketCustomer,Integer loginUserId);

	/**
	 *修改营销客户表
	 * @param custMarketCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCustMarketCustomer(CustMarketCustomer custMarketCustomer,Integer loginUserId);

	/**
	 * 通过主键删除营销客户表
	 * @param MARKET_CUSTOMER_ID 主键Id
	 */
	void deleteCustMarketCustomerById(Integer marketCustomerId);

	/**
	 * 通过主键得到营销客户表
	 * @param MARKET_CUSTOMER_ID 主键Id
	 */
	CustMarketCustomer getCustMarketCustomerById(Integer marketCustomerId);

	/**
	 * 查询营销客户表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustMarketCustomerQuery> queryCustMarketCustomerList(Map<String,Object> condition);

	/**
	 * 分页查询营销客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustMarketCustomerQuery> queryCustMarketCustomerList(Map<String,Object> condition,IPageSize page);
	
	
	/**
	 * 修改客户分配状态
	 * @param condition
	 */
	void signCustomer(Map<String,Object> condition);
	
	
	/**
	 * 查询预申请分配营销客户
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustMarketCustomerQuery> queryGroupMarketCustomerList(Map<String,Object> condition,IPageSize page);

	/**
	 * 客户经理查询预申请分配营销客户
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustMarketCustomerQuery> queryMemberMarketCustomerList(Map<String,Object> condition,IPageSize page,boolean isapp);	
}
