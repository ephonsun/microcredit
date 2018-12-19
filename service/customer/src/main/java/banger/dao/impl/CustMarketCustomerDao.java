package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustMarketCustomerDao;
import banger.domain.customer.CustMarketCustomer;
import banger.domain.customer.CustMarketCustomerQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 营销客户表数据访问类
 */
@Repository
public class CustMarketCustomerDao extends PageSizeDao<CustMarketCustomer> implements ICustMarketCustomerDao {

	/**
	 * 新增营销客户表
	 * @param custMarketCustomer 实体对像
	 */
	public void insertCustMarketCustomer(CustMarketCustomer custMarketCustomer){
		custMarketCustomer.setMarketCustomerId(this.newId().intValue());
		this.execute("insertCustMarketCustomer",custMarketCustomer);
	}

	/**
	 *修改营销客户表
	 * @param custMarketCustomer 实体对像
	 */
	public void updateCustMarketCustomer(CustMarketCustomer custMarketCustomer){
		this.execute("updateCustMarketCustomer",custMarketCustomer);
	}

	/**
	 * 通过主键删除营销客户表
	 * @param marketCustomerId 主键Id
	 */
	public void deleteCustMarketCustomerById(Integer marketCustomerId){
		this.execute("deleteCustMarketCustomerById",marketCustomerId);
	}

	/**
	 * 通过主键得到营销客户表
	 * @param marketCustomerId 主键Id
	 */
	public CustMarketCustomer getCustMarketCustomerById(Integer marketCustomerId){
		return (CustMarketCustomer)this.queryEntity("getCustMarketCustomerById",marketCustomerId);
	}

	/**
	 * 查询营销客户表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustMarketCustomerQuery> queryCustMarketCustomerList(Map<String,Object> condition){
		return (List<CustMarketCustomerQuery>)this.queryEntities("queryCustMarketCustomerList", condition);
	}

	/**
	 * 分页查询营销客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustMarketCustomerQuery> queryCustMarketCustomerList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustMarketCustomerQuery>)this.queryEntities("queryCustMarketCustomerList", page, condition);
	}

	@Override
	public void signCustomer(Map<String, Object> condition) {
		this.execute("signCustomer",condition);
	}

	@Override
	public IPageList<CustMarketCustomerQuery> queryGroupMarketCustomerList(
			Map<String, Object> condition, IPageSize page) {
		return (IPageList<CustMarketCustomerQuery>)this.queryEntities("queryGroupMarketCustomerList", page, condition);
	}

	@Override
	public IPageList<CustMarketCustomerQuery> queryMemberMarketCustomerList(
			Map<String, Object> condition, IPageSize page) {
		return (IPageList<CustMarketCustomerQuery>)this.queryEntities("queryMemberMarketCustomerList", page, condition);
	}

	/**
	 * 更新客户的预申请贷款Id
	 * @param marketCustomerId
	 * @param loanId
	 */
	public void updateCustomerLoanId(Integer marketCustomerId,Integer loanId){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("marketCustomerId",marketCustomerId);
		condition.put("loanId",loanId);
		this.execute("updateCustomerLoanId",condition);
	}

}
