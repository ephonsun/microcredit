package banger.dao.intf;

import banger.domain.customer.IntoCustomerGroup;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 进件客户团队分配表数据访问接口
 */
public interface ICustomerGroupDao {

	/**
	 * 新增进件客户团队分配表
	 * @param customerGroup 实体对像
	 */
	void insertCustomerGroup(IntoCustomerGroup customerGroup);

	/**
	 *修改进件客户团队分配表
	 * @param customerGroup 实体对像
	 */
	void updateCustomerGroup(IntoCustomerGroup customerGroup);

	/**
	 * 查询进件客户团队分配表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoCustomerGroup> queryCustomerGroupList(Map<String, Object> condition);

	/**
	 * 分页查询进件客户团队分配表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoCustomerGroup> queryCustomerGroupList(Map<String, Object> condition, IPageSize page);

}
