package banger.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.IMarketCustomerMemberDao;
import banger.domain.customer.MarketCustomerMember;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.service.intf.IMarketCustomerMemberService;

/**
 * 营销客户分配表业务访问类
 */
@Repository
public class MarketCustomerMemberService implements IMarketCustomerMemberService {

	@Autowired
	private IMarketCustomerMemberDao marketCustomerMemberDao;

	/**
	 * 新增营销客户分配表
	 * @param marketCustomerMember 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertMarketCustomerMember(MarketCustomerMember marketCustomerMember,Integer loginUserId){
		marketCustomerMember.setIsDel(0);
		this.marketCustomerMemberDao.insertMarketCustomerMember(marketCustomerMember);
	}

	/**
	 *修改营销客户分配表
	 * @param marketCustomerMember 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateMarketCustomerMember(MarketCustomerMember marketCustomerMember,Integer loginUserId){
		this.marketCustomerMemberDao.updateMarketCustomerMember(marketCustomerMember);
	}

	/**
	 * 查询营销客户分配表
	 * @param condition 查询条件
	 * @return
	 */
	public List<MarketCustomerMember> queryMarketCustomerMemberList(Map<String,Object> condition){
		return this.marketCustomerMemberDao.queryMarketCustomerMemberList(condition);
	}

	/**
	 * 分页查询营销客户分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<MarketCustomerMember> queryMarketCustomerMemberList(Map<String,Object> condition,IPageSize page){
		return this.marketCustomerMemberDao.queryMarketCustomerMemberList(condition,page);
	}

}
