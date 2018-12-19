package banger.dao.impl;

import banger.dao.intf.ICustomerGroupDao;
import banger.domain.customer.IntoCustomerGroup;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 进件客户团队分配表数据访问类
 */
@Repository
public class CustomerGroupDao extends PageSizeDao<IntoCustomerGroup> implements ICustomerGroupDao {

	/**
	 * 新增进件客户团队分配表
	 * @param customerGroup 实体对像
	 */
	public void insertCustomerGroup(IntoCustomerGroup customerGroup){
		this.execute("insertCustomerGroup",customerGroup);
	}

	/**
	 *修改进件客户团队分配表
	 * @param customerGroup 实体对像
	 */
	public void updateCustomerGroup(IntoCustomerGroup customerGroup){
		this.execute("updateCustomerGroup",customerGroup);
	}

	/**
	 * 查询进件客户团队分配表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoCustomerGroup> queryCustomerGroupList(Map<String,Object> condition){
		return (List<IntoCustomerGroup>)this.queryEntities("queryCustomerGroupList", condition);
	}

	/**
	 * 分页查询进件客户团队分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoCustomerGroup> queryCustomerGroupList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoCustomerGroup>)this.queryEntities("queryCustomerGroupList", page, condition);
	}

}
