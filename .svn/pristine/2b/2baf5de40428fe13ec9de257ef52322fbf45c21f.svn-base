package banger.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustCustomerCreditCheckDao;
import banger.domain.customer.CustCustomerCreditCheck;
import banger.domain.customer.CustCustomerCreditCheckQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 客户征信调查表数据访问类
 */
@Repository
public class CustCustomerCreditCheckDao extends PageSizeDao<CustCustomerCreditCheck> implements ICustCustomerCreditCheckDao {

	/**
	 * 新增客户征信调查表
	 * @param custCustomerCreditCheck 实体对像
	 */
	public void insertCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck,boolean isapp){
		custCustomerCreditCheck.setCustomerCreditCheckId(this.newId().intValue());
		if(isapp){
			this.execute("customerCreditCheckApply",custCustomerCreditCheck);
		}else{
			this.execute("insertCustCustomerCreditCheck",custCustomerCreditCheck);
		}		
	}

	/**
	 *修改客户征信调查表
	 * @param custCustomerCreditCheck 实体对像
	 */
	public void updateCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck){
		this.execute("updateCustCustomerCreditCheck",custCustomerCreditCheck);
	}

	/**
	 * 通过主键删除客户征信调查表
	 * @param customerCreditCheckId 主键Id
	 */
	public void deleteCustCustomerCreditCheckById(Integer customerCreditCheckId){
		this.execute("deleteCustCustomerCreditCheckById",customerCreditCheckId);
	}

	/**
	 * 通过主键得到客户征信调查表
	 * @param customerCreditCheckId 主键Id
	 */
	public CustCustomerCreditCheckQuery getCustCustomerCreditCheckById(Integer customerCreditCheckId){
		return (CustCustomerCreditCheckQuery)this.queryEntity("getCustCustomerCreditCheckById",customerCreditCheckId);
	}

	/**
	 * 查询客户征信调查表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustCustomerCreditCheckQuery> queryCustCustomerCreditCheckList(Map<String,Object> condition){
		return (List<CustCustomerCreditCheckQuery>)this.queryEntities("queryCustCustomerCreditCheckList", condition);
	}

	/**
	 * 分页查询客户征信调查表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustCustomerCreditCheckQuery> queryCustCustomerCreditCheckList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustCustomerCreditCheckQuery>)this.queryEntities("queryCustCustomerCreditCheckList", page, condition);
	}

	@Override
	public CustCustomerCreditCheck queryByNameAndNo(
			Map<String, Object> condition) {
		return (CustCustomerCreditCheck)this.queryEntity("queryByNameAndNo",condition);
	}

	@Override
	public List<CustCustomerCreditCheck> getCustomerCreditCheckByLoanId(
			Integer loanId) {
		return (List<CustCustomerCreditCheck>)this.queryEntities("getCustomerCreditCheckByLoanId", loanId);
	}

}
