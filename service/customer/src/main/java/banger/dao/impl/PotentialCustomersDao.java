package banger.dao.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.permission.SysTeamMember;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IPotentialCustomersDao;
import banger.domain.customer.CustPotentialCustomers;

/**
 * 潜在客户表数据访问类
 */
@Repository
public class PotentialCustomersDao extends PageSizeDao<CustPotentialCustomers> implements IPotentialCustomersDao {

	/**
	 * 新增潜在客户表
	 * @param potentialCustomers 实体对像
	 */
	public void insertPotentialCustomers(CustPotentialCustomers potentialCustomers){
		potentialCustomers.setId(this.newId().intValue());
		this.execute("insertPotentialCustomers",potentialCustomers);
	}

	@Override
	public void batchInsertPotential(List<CustPotentialCustomers> list) {
		Long[] ids = this.newId(list.size());
		for (int i=0;i<list.size();i++) {
			list.get(i).setId(ids[i].intValue());
		}
		this.executes("batchInsertPotentialCustomers",list);
	}

	@Override
	public SysTeamMember getTeamIdByUserId(Integer userId) {
		return (SysTeamMember) this.queryEntity("getTeamById",userId);
	}

	@Override
	public void deletePotentialCustomersAll(Integer userId) {
		this.execute("deletePotentialCustomersAll",userId);
	}

	@Override
	public void deletePotentialCustomersAllByGroupId(Integer groupId) {
		this.execute("deletePotentialCustomersAllByGroupId",groupId);
	}

	/**
	 *修改潜在客户表
	 * @param potentialCustomers 实体对像
	 */
	public void updatePotentialCustomers(CustPotentialCustomers potentialCustomers){
		this.execute("updatePotentialCustomers",potentialCustomers);
	}
	/**
	 *修改潜在客户表 意向时间可为null
	 * @param potentialCustomers 实体对像
	 */
	public void updatePotentialCustomersByDate(CustPotentialCustomers potentialCustomers){
		this.execute("updatePotentialCustomersByDate",potentialCustomers);
	}

	/**
	 * 通过主键删除潜在客户表
     * @param id 主键Id
     */
	public Integer deletePotentialCustomersById(Integer id){

        return this.execute("deletePotentialCustomersById",id);
    }

	/**
	 * 通过主键得到潜在客户表
	 * @param id 主键Id
	 */
	public CustPotentialCustomers getPotentialCustomersById(Integer id){
		return (CustPotentialCustomers)this.queryEntity("getPotentialCustomersById",id);
	}

	/**
	 * 查询潜在客户表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustPotentialCustomers> queryPotentialCustomersList(Map<String,Object> condition){
		return (List<CustPotentialCustomers>)this.queryEntities("queryPotentialCustomersList", condition);
	}

	/**
	 * 分页查询潜在客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustPotentialCustomerQuery> queryPotentialCustomersList(Map<String,Object> condition, IPageSize page){
//		[userId],[roleIds],[attributionManager]
		return (IPageList<CustPotentialCustomerQuery>)this.queryEntities("queryPotentialCustomersList", page, condition);
	}
	/**
	 * 检验客户唯一性
	 *
	 *
	 * @return
	 */
	@Override
	public boolean isUniquePointCustome(String id, String customerName, String cardType, String cardNumber) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("customerName",customerName);
		map.put("cardType",cardType);
		map.put("cardNumber",cardNumber);
		CustPotentialCustomers u = ( CustPotentialCustomers)this.queryEntity("getPointCustomerForCheckRepeat",map);
		if(u == null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Integer getNewId() {
		return this.newId().intValue();
	}

	@Override
	public CustPotentialCustomerQuery getPotentialCustomersQueryById(Integer id) {
		return (CustPotentialCustomerQuery)this.queryEntity("getPotentialCustomersQueryById",id);
	}

	@Override
	public List<CustPotentialCustomers> getPotentialCustomersByPhones(String phoneStr) {
		return (List<CustPotentialCustomers>)this.queryEntities("getPotentialCustomersByPhones", phoneStr);
	}

	//通过产品编号清空潜在客户的意向产品
	@Override
	public void updatePotentialCustomersByProductCode(String productCode) {
		this.execute("updatePotentialCustomersByProductCode",productCode);
	}
}
