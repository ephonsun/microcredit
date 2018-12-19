package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.permission.SysTeamMember;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustPotentialCustomers;

/**
 * 潜在客户表数据访问接口
 */
public interface IPotentialCustomersDao {

	/**
	 * 新增潜在客户表
	 * @param potentialCustomers 实体对像
	 */
	void insertPotentialCustomers(CustPotentialCustomers potentialCustomers);

	/**
	 *修改潜在客户表
	 * @param potentialCustomers 实体对像
	 */
	void updatePotentialCustomers(CustPotentialCustomers potentialCustomers);
	/**
	 *修改潜在客户表 意向时间可为null
	 * @param potentialCustomers 实体对像
	 */
	void updatePotentialCustomersByDate(CustPotentialCustomers potentialCustomers);


	/**
	 * 通过主键删除潜在客户表
     * @param id 主键Id
     */
	Integer deletePotentialCustomersById(Integer id);

	/**
	 * 通过主键得到潜在客户表
	 * @param id 主键Id
	 */
	CustPotentialCustomers getPotentialCustomersById(Integer id);

	/**
	 * 查询潜在客户表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustPotentialCustomers> queryPotentialCustomersList(Map<String, Object> condition);

	/**
	 * 分页查询潜在客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustPotentialCustomerQuery> queryPotentialCustomersList(Map<String, Object> condition, IPageSize page);
	boolean isUniquePointCustome(String id, String customerName, String cardType, String cardNumber);

	Integer getNewId();

    CustPotentialCustomerQuery getPotentialCustomersQueryById(Integer id);
	/**
	 * @Author: yangdw
	 * @param
	 * @Description:批量插入潜在客户
	 * @Date: 17:04 2017/9/26
	 */
	void batchInsertPotential(List<CustPotentialCustomers> list);
	/**
	 * @Author: yangdw
	 * @param
	 * @Description:团队
	 * @Date:
	 */
	SysTeamMember getTeamIdByUserId(Integer userId);

    void deletePotentialCustomersAll(Integer userId);
	/**
	 * 团队主管删除全部
	 * @param **/
    void deletePotentialCustomersAllByGroupId(Integer groupId);

	/**
	 * @Author: yangdw
	 * @param phoneStr 电话号码集合的str
	 * @Description: 通过电话号码查询所有的潜在用户(in)
	 * @Date: 10:28 2017/10/31
	 */
	List<CustPotentialCustomers> getPotentialCustomersByPhones(String phoneStr);

	//通过产品编号清空潜在客户的意向产品
	void updatePotentialCustomersByProductCode(String productCode);
}
