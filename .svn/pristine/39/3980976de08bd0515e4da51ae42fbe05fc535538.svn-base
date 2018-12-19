package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.MarketCustomerGroup;

/**
 * 营销客户团队分配表数据访问接口
 */
public interface IMarketCustomerGroupDao {

	/**
	 * 新增营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 */
	void insertMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup);

	/**
	 *修改营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 */
	void updateMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup);

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
