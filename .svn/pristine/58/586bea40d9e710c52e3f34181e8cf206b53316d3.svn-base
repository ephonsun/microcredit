package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustCustomerBlack;
import banger.domain.customer.CustCustomerBlackQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 客户黑名单表业务访问接口
 */
public interface ICustCustomerBlackService {

	/**
	 * 新增客户黑名单表
	 * @param custCustomerBlack 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCustCustomerBlack(CustCustomerBlack custCustomerBlack,Integer loginUserId);

	/**
	 *修改客户黑名单表
	 * @param custCustomerBlack 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCustCustomerBlack(CustCustomerBlack custCustomerBlack,Integer loginUserId);

	/**
	 * 通过主键删除客户黑名单表
	 * @param CUSTOMER_BLACK_ID 主键Id
	 */
	void deleteCustCustomerBlackById(Integer customerBlackId);

	/**
	 * 通过主键得到客户黑名单表
	 * @param CUSTOMER_BLACK_ID 主键Id
	 */
	CustCustomerBlack getCustCustomerBlackById(Integer customerBlackId);

	/**
	 * 查询客户黑名单表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustCustomerBlack> queryCustCustomerBlackList(Map<String,Object> condition);

	/**
	 * 分页查询客户黑名单表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustCustomerBlackQuery> queryCustCustomerBlackList(Map<String,Object> condition,IPageSize page);

}
