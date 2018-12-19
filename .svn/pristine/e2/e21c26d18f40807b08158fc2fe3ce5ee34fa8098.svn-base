package banger.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.IMarketCustomerGroupDao;
import banger.domain.customer.MarketCustomerGroup;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.service.intf.IMarketCustomerGroupService;

/**
 * 营销客户团队分配表业务访问类
 */
@Repository
public class MarketCustomerGroupService implements IMarketCustomerGroupService {

	@Autowired
	private IMarketCustomerGroupDao marketCustomerGroupDao;

	/**
	 * 新增营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup,Integer loginUserId){
		marketCustomerGroup.setIsDel(0);
		marketCustomerGroup.setSignDate(new Date());
		marketCustomerGroup.setSignUserId(loginUserId);
		this.marketCustomerGroupDao.insertMarketCustomerGroup(marketCustomerGroup);
	}

	/**
	 *修改营销客户团队分配表
	 * @param marketCustomerGroup 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateMarketCustomerGroup(MarketCustomerGroup marketCustomerGroup,Integer loginUserId){
		this.marketCustomerGroupDao.updateMarketCustomerGroup(marketCustomerGroup);
	}

	/**
	 * 查询营销客户团队分配表
	 * @param condition 查询条件
	 * @return
	 */
	public List<MarketCustomerGroup> queryMarketCustomerGroupList(Map<String,Object> condition){
		return this.marketCustomerGroupDao.queryMarketCustomerGroupList(condition);
	}

	/**
	 * 分页查询营销客户团队分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<MarketCustomerGroup> queryMarketCustomerGroupList(Map<String,Object> condition,IPageSize page){
		return this.marketCustomerGroupDao.queryMarketCustomerGroupList(condition,page);
	}

}
