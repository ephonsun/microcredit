package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.MarketCustomerGroup;

/**
 * 营销客户团队分配表业务访问接口
 */
public interface IMarketCustomerGroupService {

	/**
	 * 新增营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup,Integer loginUserId);

	/**
	 *修改营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup,Integer loginUserId);

	/**
	 * 查询营销客户团队分配表
	 * @param condition 查询条件
	 * @return
	 */
	List<MarketCustomerGroup> queryMarketCustomerGroupList(Map<String,Object> condition);

	/**
	 * 分页查询营销客户团队分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<MarketCustomerGroup> queryMarketCustomerGroupList(Map<String,Object> condition,IPageSize page);

}
