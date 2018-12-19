package banger.service.intf;

import banger.framework.collection.DataTable;

public interface IAppLoanApplyService {
	
	/**
	 * 获取贷款申请字段和下拉项
	 * @return
	 */
	String getLoanApplyFieldJsonString();

	/**
	 * 得到贷款类型JSON数据
	 * @return
	 */
	String getLoanTypeJsonString();

	/**
	 * 得到贷款合同类型JSON数据
	 * @return
	 */
	String getLoanContractTypeJsonString();
	
	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanId
	 * @return
	 */
	String getLoanTemplateFieldJsonString(Integer loanTypeId,String loanProcessType,Integer tableId);
	
	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanId
	 * @return
	 */
	String getLoanTemplateFieldJsonString(Integer loanTypeId,String loanProcessType,Integer tableId,Integer loanId,String listAddFlag);

	/**
	 * 根据贷款Id得到担保人模版 但是 内容为配偶信息
	 * @param loanId
	 * @return
	 */
	String getGuaFieldBySpouse(Integer loanTypeId,String loanProcessType,Integer tableId,Integer loanId,String listAddFlag);

	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanId
	 * @return
	 */
	String getLoanTemplateJsonString(Integer loanTypeId,String loanProcessType);
	
	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanId
	 * @return
	 */
	String getLoanTemplateJsonString(Integer loanTypeId,String loanProcessType,Integer loanId);
	
	/**
	 * 根据贷款Id得到模板和字段
	 * @param loanTypeId
	 * @param loanProcessType
	 * @return
	 */
	String getLoanTemplateFieldJsonString(Integer loanTypeId,String loanProcessType);

	/**
	 * 得到贷款审批状态信息
	 * @param loanId
	 * @return
	 */
	String getLoanAuditStateInfoJsonString(Integer loanId,Integer processId);

	/**
	 * 得到合同模板
	 * @param loanId
	 * @return
	 */
	String getLoanContractTemplateJsonString(Integer contractTypeId,Integer loanId);

	/**
	 * 得到合同模板字段
	 * @param loanTypeId
	 * @param tableId
	 * @param loanId
	 * @return
	 */
	public String getContractTemplateFieldJsonString(Integer contractTypeId,Integer tableId,Integer loanId);


}
