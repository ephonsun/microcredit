package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.MarketCustomerMember;

/**
 * 营销客户分配表业务访问接口
 */
public interface IMarketCustomerMemberService {

	/**
	 * 新增营销客户分配表
	 * @param marketCustomerMember 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertMarketCustomerMember(MarketCustomerMember marketCustomerMember,Integer loginUserId);

	/**
	 *修改营销客户分配表
	 * @param marketCustomerMember 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateMarketCustomerMember(MarketCustomerMember marketCustomerMember,Integer loginUserId);

	/**
	 * 查询营销客户分配表
	 * @param condition 查询条件
	 * @return
	 */
	List<MarketCustomerMember> queryMarketCustomerMemberList(Map<String,Object> condition);

	/**
	 * 分页查询营销客户分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<MarketCustomerMember> queryMarketCustomerMemberList(Map<String,Object> condition,IPageSize page);

}
