package banger.service.impl;

import banger.dao.intf.ICustomerMemberDao;
import banger.domain.customer.IntoCustomerMember;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.service.intf.ICustomerMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件客户分配表业务访问类
 */
@Repository
public class CustomerMemberService implements ICustomerMemberService {

	@Autowired
	private ICustomerMemberDao customerMemberDao;

	/**
	 * 新增进件客户分配表
	 * @param customerMember 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCustomerMember(IntoCustomerMember customerMember,Integer loginUserId){
		customerMember.setIsDel(0);
		this.customerMemberDao.insertCustomerMember(customerMember);
	}

	/**
	 *修改进件客户分配表
	 * @param customerMember 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustomerMember(IntoCustomerMember customerMember,Integer loginUserId){
		this.customerMemberDao.updateCustomerMember(customerMember);
	}

	/**
	 * 查询进件客户分配表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoCustomerMember> queryCustomerMemberList(Map<String,Object> condition){
		return this.customerMemberDao.queryCustomerMemberList(condition);
	}

	/**
	 * 分页查询进件客户分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoCustomerMember> queryCustomerMemberList(Map<String,Object> condition,IPageSize page){
		return this.customerMemberDao.queryCustomerMemberList(condition,page);
	}

}
