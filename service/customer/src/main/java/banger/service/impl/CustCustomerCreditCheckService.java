package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.framework.util.IdCardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustCustomerCreditCheckDao;
import banger.domain.customer.CustCustomerCreditCheck;
import banger.domain.customer.CustCustomerCreditCheckQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.SensitiveInfoUtils;
import banger.service.intf.ICustCustomerCreditCheckService;

/**
 * 客户征信调查表业务访问类
 */
@Repository
public class CustCustomerCreditCheckService implements ICustCustomerCreditCheckService {

	@Autowired
	private ICustCustomerCreditCheckDao custCustomerCreditCheckDao;

	/**
	 * 新增客户征信调查表
	 * @param custCustomerCreditCheck 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck,Integer loginUserId,boolean isapp){
		custCustomerCreditCheck.setUpdateUser(loginUserId);
		custCustomerCreditCheck.setUpdateDate(DateUtil.getCurrentDate());
		this.custCustomerCreditCheckDao.insertCustCustomerCreditCheck(custCustomerCreditCheck,isapp);
	}

	/**
	 *修改客户征信调查表
	 * @param custCustomerCreditCheck 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustCustomerCreditCheck(CustCustomerCreditCheck custCustomerCreditCheck,Integer loginUserId){
		custCustomerCreditCheck.setUpdateUser(loginUserId);
		custCustomerCreditCheck.setUpdateDate(DateUtil.getCurrentDate());
		this.custCustomerCreditCheckDao.updateCustCustomerCreditCheck(custCustomerCreditCheck);
	}

	/**
	 * 通过主键删除客户征信调查表
	 * @param customerCreditCheckId 主键Id
	 */
	public void deleteCustCustomerCreditCheckById(Integer customerCreditCheckId){
		this.custCustomerCreditCheckDao.deleteCustCustomerCreditCheckById(customerCreditCheckId);
	}

	/**
	 * 通过主键得到客户征信调查表
	 * @param customerCreditCheckId 主键Id
	 */
	public CustCustomerCreditCheckQuery getCustCustomerCreditCheckById(Integer customerCreditCheckId){
		return this.custCustomerCreditCheckDao.getCustCustomerCreditCheckById(customerCreditCheckId);
	}

	/**
	 * 查询客户征信调查表
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustCustomerCreditCheckQuery> queryCustCustomerCreditCheckList(Map<String,Object> condition){
		return this.custCustomerCreditCheckDao.queryCustCustomerCreditCheckList(condition);
	}

	/**
	 * 分页查询客户征信调查表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustCustomerCreditCheckQuery> queryCustCustomerCreditCheckList(Map<String,Object> condition,IPageSize page,boolean isapp){
		IPageList<CustCustomerCreditCheckQuery> list=this.custCustomerCreditCheckDao.queryCustCustomerCreditCheckList(condition,page);
		
		for (CustCustomerCreditCheckQuery custCustomerCreditCheck : list) {
			   convert(custCustomerCreditCheck,isapp);
		}
		
		return list;
	}
	
	private void convert(CustCustomerCreditCheckQuery custCustomerCreditCheck,boolean isapp){		
		if(!isapp){
			//String identifyNum=custCustomerCreditCheck.getCardNo();
			//custCustomerCreditCheck.setCardNo(IdCardUtil.getIdentifyNumX(identifyNum,1));			//去掉脱敏
		}
	}

	@Override
	public CustCustomerCreditCheck queryByNameAndNo(
			Map<String, Object> condition) {
		return custCustomerCreditCheckDao.queryByNameAndNo(condition);
	}

	@Override
	public List<CustCustomerCreditCheck> getCustomerCreditCheckByLoanId(
			Integer loanId) {
		return custCustomerCreditCheckDao.getCustomerCreditCheckByLoanId(loanId);
	}

}
