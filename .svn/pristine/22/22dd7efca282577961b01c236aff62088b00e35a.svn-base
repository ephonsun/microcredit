package banger.service.impl;

import banger.dao.intf.ICustApplyInfoDao;
import banger.dao.intf.IFileInfoDao;
import banger.dao.intf.ILoanUseDao;
import banger.domain.config.IntoLoanUse;
import banger.domain.customer.CustMarketCustomerQuery;
import banger.domain.html5.IntoCustApplyInfo;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.domain.html5.IntoFileInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.IdCardUtil;
import banger.moduleIntf.IIntoCustomersProvider;
import banger.service.intf.ICustApplyInfoService;
import banger.service.intf.ICustApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 进件申请信息表业务访问类
 */
@Repository
	public class CustApplyInfoService implements ICustApplyInfoService ,ICustApplyService ,IIntoCustomersProvider {

	@Autowired
	private ILoanUseDao loanUseDao;
	@Autowired
	private IFileInfoDao fileInfoDao;

	@Autowired
	private ICustApplyInfoDao custApplyInfoDao;
	/**
	 * 团队主管查询分配的进件用户
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> queryIntoCustApplyInfoList(Map<String, Object> condition, IPageSize page) {
		return this.custApplyInfoDao.queryIntoCustApplyInfoList(condition,page);
	}

	/**
	 * 通过主键得到进件附件信息
	 * @param ID 主键Id
	 */
	public IntoFileInfo getFileInfoById(Integer id){
		return this.fileInfoDao.getFileInfoById(id);
	}

	/**
	 * 更新客户的预申请贷款Id
	 * @param applyId
	 * @param loanId
	 */
	@Override
	public void updateIntoCustomerLoanId(Integer applyId, Integer loanId) {
		this.custApplyInfoDao.updateIntoCustomerLoanId(applyId,loanId);
	}


	/**
	 * 客户经理查询分配的进件用户
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page) {
		return this.custApplyInfoDao.IntoCustApplyInfoMemberList(condition,page);
	}


	/**
	 * 新增进件申请信息表
	 * @param custApplyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public Integer insertCustApplyInfo(IntoCustApplyInfo custApplyInfo,Integer loginUserId){
		return this.custApplyInfoDao.insertCustApplyInfo(custApplyInfo);
	}

	/**
	 *修改进件申请信息表
	 * @param custApplyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustApplyInfo(IntoCustApplyInfo custApplyInfo,Integer loginUserId){
		this.custApplyInfoDao.updateCustApplyInfo(custApplyInfo);
	}

	/**
	 * 通过主键删除进件申请信息表
	 * @param APPLY_ID 主键Id
	 */
	public void deleteCustApplyInfoById(Integer applyId){
		this.custApplyInfoDao.deleteCustApplyInfoById(applyId);
	}

	/**
	 * 通过主键得到进件申请信息表
	 * @param APPLY_ID 主键Id
	 */
	public IntoCustApplyInfo getCustApplyInfoById(Integer applyId){
		return this.custApplyInfoDao.getCustApplyInfoById(applyId);
	}

	public IntoCustApplyInfoQuery getCustApplyInfoByIdQuery(Integer applyId){
		return this.custApplyInfoDao.getCustApplyInfoByIdQuery(applyId);
	}

	/**
	 * App端
	 * 客户经理查询分配的进件用户
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */


	@Override
	public IPageList<IntoCustApplyInfoQuery> IntoAppCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page) {
		IPageList<IntoCustApplyInfoQuery> list = this.custApplyInfoDao.IntoAppCustApplyInfoMemberList(condition,page);

		for (IntoCustApplyInfoQuery intoCustApplyInfoQuery : list) {
			convert(intoCustApplyInfoQuery,false);
		}
		return list;
	}

	private void convert(IntoCustApplyInfoQuery customer, boolean isapp){
//		//手机号码
//		String phoneNumber=customer.getCustPhone();
//		if(!isapp){
//			customer.setCustPhone(IdCardUtil.getTelNumX(phoneNumber,1));
//		}
//
//		//年龄
//		if(customer.getCustAge()!=null){
//			customer.setCustAgeStr(customer.getCustAge()+"岁");
//		}
//		if(!isapp){
//			String cardType = customer.getCardType();
//			//身份证
//			customer.setIdCard(IdCardUtil.getIdentifyNumX(customer.getIdCard(), cardType,1));
//		}

		//意向金额
		if(customer.getApplyAmount()!=null) {
			customer.setApplyAmountStr("￥"+IdCardUtil.getFormatMoney(String.valueOf(customer.getApplyAmount())));
		}
//		if(customer.getLoanMoney()!=null && customer.getLoanMoney().doubleValue()>999){
//			DecimalFormat myformat = new DecimalFormat();
//			myformat.applyPattern("##,###");
//			customer.setLoanMoneyStr(myformat.format(customer.getLoanMoney()));
//		}else{
//			customer.setLoanMoneyStr(String.valueOf(customer.getLoanMoney()));
//		}
//		String dateFormat = "yyyy-MM-dd HH:mm";
//		//提交时间
//		customer.getStartDate(DateUtil.format(customer.getCreateDate(), dateFormat));
//
//		//提交时间
//		customer.setSignDateStr(DateUtil.format(customer.getSignDates(), dateFormat));
	}

	/**
	 * app端
	 * 通过主键得到进件申请信息表
	 * @param APPLY_ID 主键Id
	 */
	public IntoCustApplyInfoQuery getAppCustApplyInfoById(Integer applyId){
		return this.custApplyInfoDao.getAppCustApplyInfoById(applyId);
	}

	/**
	 * 通过主键得到进件贷款用途
	 * @param USE_ID 主键Id
	 */
	public IntoLoanUse getLoanUseById(Integer useId){
		return this.loanUseDao.getLoanUseById(useId);
	}

	/**
	 * 查询进件附件信息
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoFileInfo> queryFileInfoList(Map<String,Object> condition){
		return this.fileInfoDao.queryFileInfoList(condition);
	}

//	/**
//	 * 查询进件申请信息表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	public List<IntoCustApplyInfo> queryCustApplyInfoList(Map<String,Object> condition){
//		return this.custApplyInfoDao.queryCustApplyInfoList(condition);
//	}
//
//	public IPageList<IntoCustApplyInfo> queryCustApplyInfoList(Map<String,Object> condition,IPageSize page){
//		return this.custApplyInfoDao.queryCustApplyInfoList(condition,page);
//	}
	/**
	 * 分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoList(Map<String, Object> condition, IPageSize page) {
		return this.custApplyInfoDao.IntoCustApplyInfoList(condition,page);
	}

	@Override
	public void deleteIntoCustomerById(Integer applyId) {
		this.custApplyInfoDao.deleteIntoCustomerById(applyId);
	}

	@Override
	public void signIntoCustomer(Map<String, Object> condition) {
		custApplyInfoDao.signIntoCustomer(condition);
	}



}
