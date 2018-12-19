package banger.service.impl;

import banger.dao.intf.ICustomerGroupDao;
import banger.domain.customer.IntoCustomerGroup;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.service.intf.ICustomerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件客户团队分配表业务访问类
 */
@Repository
public class CustomerGroupService implements ICustomerGroupService {

	@Autowired
	private ICustomerGroupDao customerGroupDao;

	/**
	 * 新增进件客户团队分配表
	 * @param customerGroup 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCustomerGroup(IntoCustomerGroup customerGroup,Integer loginUserId){
		this.customerGroupDao.insertCustomerGroup(customerGroup);
	}

	/**
	 *修改进件客户团队分配表
	 * @param customerGroup 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustomerGroup(IntoCustomerGroup customerGroup,Integer loginUserId){
		this.customerGroupDao.updateCustomerGroup(customerGroup);
	}

	/**
	 * 查询进件客户团队分配表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoCustomerGroup> queryCustomerGroupList(Map<String,Object> condition){
		return this.customerGroupDao.queryCustomerGroupList(condition);
	}

	/**
	 * 分页查询进件客户团队分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoCustomerGroup> queryCustomerGroupList(Map<String,Object> condition,IPageSize page){
		return this.customerGroupDao.queryCustomerGroupList(condition,page);
	}

}
