package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IMarketCustomerMemberDao;
import banger.domain.customer.MarketCustomerMember;

/**
 * 营销客户分配表数据访问类
 */
@Repository
public class MarketCustomerMemberDao extends PageSizeDao<MarketCustomerMember> implements IMarketCustomerMemberDao {

	/**
	 * 新增营销客户分配表
	 * @param marketCustomerMember 实体对像
	 */
	public void insertMarketCustomerMember(MarketCustomerMember marketCustomerMember){
		this.execute("insertMarketCustomerMember",marketCustomerMember);
	}

	/**
	 *修改营销客户分配表
	 * @param marketCustomerMember 实体对像
	 */
	public void updateMarketCustomerMember(MarketCustomerMember marketCustomerMember){
		this.execute("updateMarketCustomerMember",marketCustomerMember);
	}

	/**
	 * 查询营销客户分配表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarketCustomerMember> queryMarketCustomerMemberList(Map<String,Object> condition){
		return (List<MarketCustomerMember>)this.queryEntities("queryMarketCustomerMemberList", condition);
	}

	/**
	 * 分页查询营销客户分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<MarketCustomerMember> queryMarketCustomerMemberList(Map<String,Object> condition,IPageSize page){
		return (IPageList<MarketCustomerMember>)this.queryEntities("queryMarketCustomerMemberList", page, condition);
	}

}
