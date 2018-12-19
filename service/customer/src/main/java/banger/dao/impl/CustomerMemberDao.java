package banger.dao.impl;

import banger.dao.intf.ICustomerMemberDao;
import banger.domain.customer.IntoCustomerMember;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件客户分配表数据访问类
 */
@Repository
public class CustomerMemberDao extends PageSizeDao<IntoCustomerMember> implements ICustomerMemberDao {

	/**
	 * 新增进件客户分配表
	 * @param customerMember 实体对像
	 */
	public void insertCustomerMember(IntoCustomerMember customerMember){
		this.execute("insertCustomerMember",customerMember);
	}

	/**
	 *修改进件客户分配表
	 * @param customerMember 实体对像
	 */
	public void updateCustomerMember(IntoCustomerMember customerMember){
		this.execute("updateCustomerMember",customerMember);
	}

	/**
	 * 查询进件客户分配表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoCustomerMember> queryCustomerMemberList(Map<String,Object> condition){
		return (List<IntoCustomerMember>)this.queryEntities("queryCustomerMemberList", condition);
	}

	/**
	 * 分页查询进件客户分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoCustomerMember> queryCustomerMemberList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoCustomerMember>)this.queryEntities("queryCustomerMemberList", page, condition);
	}

}
