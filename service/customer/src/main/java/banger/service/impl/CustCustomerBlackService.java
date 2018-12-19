package banger.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanApplyInfo;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ICustomerBlackProvider;
import banger.moduleIntf.ILoanApplyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustCustomerBlackDao;
import banger.domain.customer.CustCustomerBlack;
import banger.domain.customer.CustCustomerBlackQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ICustCustomerBlackService;

import javax.annotation.Resource;

/**
 * 客户黑名单表业务访问类
 */
@Repository
public class CustCustomerBlackService implements ICustCustomerBlackService,ICustomerBlackProvider {

	@Autowired
	private ICustCustomerBlackDao custCustomerBlackDao;

	@Resource
	private ILoanApplyProvider loanApplyProvider;

	/**
	 * 新增客户黑名单表
	 * @param custCustomerBlack 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCustCustomerBlack(CustCustomerBlack custCustomerBlack,Integer loginUserId){
		custCustomerBlack.setCreateUser(loginUserId);
		custCustomerBlack.setCreateDate(DateUtil.getCurrentDate());
		this.custCustomerBlackDao.insertCustCustomerBlack(custCustomerBlack);
	}

	/**
	 *修改客户黑名单表
	 * @param custCustomerBlack 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustCustomerBlack(CustCustomerBlack custCustomerBlack,Integer loginUserId){
		this.custCustomerBlackDao.updateCustCustomerBlack(custCustomerBlack);
	}

	/**
	 * 通过主键删除客户黑名单表
	 * @param customerBlackId 主键Id
	 */
	public void deleteCustCustomerBlackById(Integer customerBlackId){
		this.custCustomerBlackDao.deleteCustCustomerBlackById(customerBlackId);
	}

	/**
	 * 通过主键得到客户黑名单表
	 * @param customerBlackId 主键Id
	 */
	public CustCustomerBlack getCustCustomerBlackById(Integer customerBlackId){
		return this.custCustomerBlackDao.getCustCustomerBlackById(customerBlackId);
	}

	/**
	 * 查询客户黑名单表
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustCustomerBlack> queryCustCustomerBlackList(Map<String,Object> condition){
		return this.custCustomerBlackDao.queryCustCustomerBlackList(condition);
	}

	/**
	 * 分页查询客户黑名单表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustCustomerBlackQuery> queryCustCustomerBlackList(Map<String,Object> condition,IPageSize page){
		return this.custCustomerBlackDao.queryCustCustomerBlackList(condition,page);
	}

	/**
	 * 保存黑名称客户
	 * @param custCustomerBlack
	 */
	public void saveCustomerBlack(CustCustomerBlack custCustomerBlack){
		if(StringUtil.isNotEmpty(custCustomerBlack.getCustomerName()) && StringUtil.isNotEmpty(custCustomerBlack.getCardType()) && StringUtil.isNotEmpty(custCustomerBlack.getCardNo())){
			Integer count = this.custCustomerBlackDao.getCustomerBlackCount(custCustomerBlack.getCustomerName(),custCustomerBlack.getCardType(),custCustomerBlack.getCardNo());
			if(count.intValue()==0){
				custCustomerBlack.setCreateDate(new Date());
				this.custCustomerBlackDao.insertCustCustomerBlack(custCustomerBlack);
			}
		}
	}

	/**
	 * 是否是黑名单客户
	 * @param loanId
	 */
	public boolean isExistCustomerBlack(Integer loanId){
		LoanApplyInfo loanApplyInfo = loanApplyProvider.getApplyInfoById(loanId);
		if(loanApplyInfo!=null){
			return isExistCustomerBlack(loanApplyInfo.getLoanName(),loanApplyInfo.getLoanIdentifyType(),loanApplyInfo.getLoanIdentifyNum());
		}
		return false;
	}

	/**
	 * 是否是黑名单客户
	 * @param customerName
	 * @param cardType
	 * @param cardNo
	 * @return
	 */
	public boolean isExistCustomerBlack(String customerName,String cardType,String cardNo) {
		if (StringUtil.isNotEmpty(customerName) && StringUtil.isNotEmpty(cardType) && StringUtil.isNotEmpty(cardNo)) {
			Integer count = this.custCustomerBlackDao.getCustomerBlackCount(customerName, cardType, cardNo);
			return count.intValue()>0;
		}
		return false;
	}

}
