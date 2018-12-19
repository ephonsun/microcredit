package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IMarketCustomerGroupDao;
import banger.domain.customer.MarketCustomerGroup;

/**
 * 营销客户团队分配表数据访问类
 */
@Repository
public class MarketCustomerGroupDao extends PageSizeDao<MarketCustomerGroup> implements IMarketCustomerGroupDao {

	/**
	 * 新增营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 */
	public void insertMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup){
		this.execute("insertMarketCustomerGroup",marketCustomerGroup);
	}

	/**
	 *修改营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 */
	public void updateMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup){
		this.execute("updateMarketCustomerGroup",marketCustomerGroup);
	}

	/**
	 * 查询营销客户团队分配表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarketCustomerGroup> queryMarketCustomerGroupList(Map<String,Object> condition){
		return (List<MarketCustomerGroup>)this.queryEntities("queryMarketCustomerGroupList", condition);
	}

	/**
	 * 分页查询营销客户团队分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<MarketCustomerGroup> queryMarketCustomerGroupList(Map<String,Object> condition,IPageSize page){
		return (IPageList<MarketCustomerGroup>)this.queryEntities("queryMarketCustomerGroupList", page, condition);
	}

}
