package banger.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import banger.framework.util.IdCardUtil;
import banger.moduleIntf.ICustomerMarketProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustMarketCustomerDao;
import banger.domain.customer.CustMarketCustomer;
import banger.domain.customer.CustMarketCustomerQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.SensitiveInfoUtils;
import banger.service.intf.ICustMarketCustomerService;

/**
 * 营销客户表业务访问类
 */
@Repository
public class CustMarketCustomerService implements ICustMarketCustomerService,ICustomerMarketProvider {

	@Autowired
	private ICustMarketCustomerDao custMarketCustomerDao;

	/**
	 * 新增营销客户表
	 * @param custMarketCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCustMarketCustomer(CustMarketCustomer custMarketCustomer,Integer loginUserId){
		custMarketCustomer.setCreateUser(loginUserId);
		custMarketCustomer.setCreateDate(DateUtil.getCurrentDate());
		custMarketCustomer.setUpdateUser(loginUserId);
		custMarketCustomer.setUpdateDate(DateUtil.getCurrentDate());
		this.custMarketCustomerDao.insertCustMarketCustomer(custMarketCustomer);
	}

	/**
	 *修改营销客户表
	 * @param custMarketCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustMarketCustomer(CustMarketCustomer custMarketCustomer,Integer loginUserId){
		custMarketCustomer.setUpdateUser(loginUserId);
		custMarketCustomer.setUpdateDate(DateUtil.getCurrentDate());
		this.custMarketCustomerDao.updateCustMarketCustomer(custMarketCustomer);
	}

	/**
	 * 通过主键删除营销客户表
	 * @param marketCustomerId 主键Id
	 */
	public void deleteCustMarketCustomerById(Integer marketCustomerId){
		this.custMarketCustomerDao.deleteCustMarketCustomerById(marketCustomerId);
	}

	/**
	 * 通过主键得到营销客户表
	 * @param marketCustomerId 主键Id
	 */
	public CustMarketCustomer getCustMarketCustomerById(Integer marketCustomerId){
		return this.custMarketCustomerDao.getCustMarketCustomerById(marketCustomerId);
	}

	/**
	 * 查询营销客户表
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustMarketCustomerQuery> queryCustMarketCustomerList(Map<String,Object> condition){
		return this.custMarketCustomerDao.queryCustMarketCustomerList(condition);
	}

	/**
	 * 分页查询营销客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustMarketCustomerQuery> queryCustMarketCustomerList(Map<String,Object> condition,IPageSize page){
		
		IPageList<CustMarketCustomerQuery> list=this.custMarketCustomerDao.queryCustMarketCustomerList(condition,page);
		
		for (CustMarketCustomerQuery custMarketCustomerQuery : list) {
			convert(custMarketCustomerQuery,false);
		}
		
		return list;
	}
	
	private void convert(CustMarketCustomerQuery  customer,boolean isapp){
		//手机号码
		String phoneNumber=customer.getPhoneNumber();	
		if(!isapp){
			customer.setPhoneNumber(IdCardUtil.getTelNumX(phoneNumber,1));
		}
		
		//年龄
		if(customer.getAge()!=null){
			customer.setAgeStr(customer.getAge()+"岁");
		}
		if(!isapp){
			String cardType = customer.getCardType();
			//身份证		
			customer.setCardNumber(IdCardUtil.getIdentifyNumX(customer.getCardNumber(), cardType,1));
		}
		
		//意向金额
		if(customer.getLoanMoney()!=null) {
			customer.setLoanMoneyStr(IdCardUtil.getFormatMoney(String.valueOf(customer.getLoanMoney())));
		}
//		if(customer.getLoanMoney()!=null && customer.getLoanMoney().doubleValue()>999){
//			DecimalFormat myformat = new DecimalFormat();
//			myformat.applyPattern("##,###");
//			customer.setLoanMoneyStr(myformat.format(customer.getLoanMoney()));
//		}else{
//			customer.setLoanMoneyStr(String.valueOf(customer.getLoanMoney()));
//		}
		String dateFormat = "yyyy-MM-dd HH:mm";
		//提交时间
		customer.setCreateDateStr(DateUtil.format(customer.getCreateDate(), dateFormat));

		//提交时间
		customer.setSignDateStr(DateUtil.format(customer.getSignDates(), dateFormat));
	}

	@Override
	public void signCustomer(Map<String, Object> condition) {
		custMarketCustomerDao.signCustomer(condition);
	}

	@Override
	public IPageList<CustMarketCustomerQuery> queryGroupMarketCustomerList(
			Map<String, Object> condition, IPageSize page) {
		IPageList<CustMarketCustomerQuery> list=custMarketCustomerDao.queryGroupMarketCustomerList(condition, page);
		for (CustMarketCustomerQuery custMarketCustomerQuery : list) {
			convert(custMarketCustomerQuery,false);
		}
		return list;
	}

	@Override
	public IPageList<CustMarketCustomerQuery> queryMemberMarketCustomerList(
			Map<String, Object> condition, IPageSize page,boolean isapp) {
		IPageList<CustMarketCustomerQuery> list=custMarketCustomerDao.queryMemberMarketCustomerList(condition, page);
		for (CustMarketCustomerQuery custMarketCustomerQuery : list) {
			convert(custMarketCustomerQuery,isapp);
		}
		return list;
	}

	/**
	 * 更新客户的预申请贷款Id
	 * @param marketCustomerId
	 * @param loanId
	 */
	public void updateCustomerLoanId(Integer marketCustomerId,Integer loanId){
		this.custMarketCustomerDao.updateCustomerLoanId(marketCustomerId,loanId);
	}

}
