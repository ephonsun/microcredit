package banger.domain.loan;

import banger.domain.enumerate.*;
import banger.framework.codetable.CodeTable;
import banger.framework.util.DateUtil;
import banger.framework.util.FormatUtil;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class LoanApplyInfo_Query extends LoanApplyInfo {

	private static final long serialVersionUID = -4045035813021048022L;

	@CodeTable(name = "cdDictColByName", key = "loanIdentifyType", params = "CD_IDENTIFY_TYPE")
	private String loanIdentifyTypeName;
	@CodeTable(name = "cdLoanType", key = "loanTypeId", params = "")
	private String loanTypeName;

	private String loanIdentifyNumX;
	private String loanTelNumX;
	private String belongUserName;
	private boolean loanStep = false;			//流程步骤

	@CodeTable(name = "cdDictColByName", key = "loanRefuseReason", params = "CD_LOAN_BANK_REFUSE_REASON")
	private String loanBankRefuseReasonDisplay;				//银行拒绝原因

	@CodeTable(name = "cdDictColByName", key = "loanRefuseReason", params = "CD_LOAN_CUSTOMER_REFUSE_REASON")
	private String loanCustomerRefuseReasonDisplay;				//银行拒绝原因

	@CodeTable(name = "cdDictColByName", key = "loanAfterState", params = "CD_LOAN_AFTERLOAN_STATUS")
	private String loanAfterStateName;				//贷款状态名称

	private String loanAccountAmountFormat;

	private String nextRepaymentAmountFormat;

	private String loanBalanceAmountFormat;


	private String loanArrearsFormat;
	private String loanIrrevocableInterestFormat;


	public String getLoanIdentifyTypeName() {
		return loanIdentifyTypeName;
	}

	public void setLoanIdentifyTypeName(String loanIdentifyTypeName) {
		this.loanIdentifyTypeName = loanIdentifyTypeName;
	}

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public String getBelongUserName() {
		return belongUserName;
	}

	public void setBelongUserName(String belongUserName) {
		this.belongUserName = belongUserName;
	}

	//贷款过程类型名称
	public String getLoanProcessTypeName(){
		LoanProcessTypeEnum type = LoanProcessTypeEnum.valueOfType(this.getLoanProcessType());
		if(type!=null){
			return type.typeName;
		}
		return "";
	}

	/**
	 * 得到贷款监控类型名称
	 * @return
	 */
	public String getLoanMonitorTypeName(){
		LoanMonitorType type = LoanMonitorType.valueOfType(this.getLoanMonitorType());
		if (type != null) {
			return type.typeName;
		}
		return "";
	}

	/**
	 * 是否可以提效,app专用
	 * @return
	 */
	public boolean getSubmitEnable(){
		if(!StringUtil.isNotEmpty(this.getLoanRefuseType())){
			if (LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.INVESTIGATE.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.APPROVAL.equalType(this.getLoanProcessType()) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否可以拒绝,app专用
	 * @return
	 */
	public boolean getRefuseEnable(){
		if(!StringUtil.isNotEmpty(this.getLoanRefuseType())) {
			if (LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.INVESTIGATE.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.APPROVAL.equalType(this.getLoanProcessType()) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否可以退回,app专用
	 * @return
	 */
	public boolean getGobackEnable(){
		if(!StringUtil.isNotEmpty(this.getLoanRefuseType())) {
			if (LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.INVESTIGATE.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.APPROVAL.equalType(this.getLoanProcessType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否可以分配,app专用
	 * @return
	 */
	public boolean getAllotEnable(){
		if(!StringUtil.isNotEmpty(this.getLoanRefuseType())) {
			if (LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否被拒绝
	 * @return
	 */
	public boolean getIsRefuse(){
		if(this.getLoanRefuseUser()!=null && this.getLoanRefuseUser().intValue()>0){
			return true;
		}
		return false;
	}

	/**
	 * 流程步骤
	 * @return
	 */
	public boolean getLoanStep() {
		return loanStep;
	}

	/**
	 * 流程步骤
	 * @param loanStep
	 */
	public void setLoanStep(boolean loanStep) {
		this.loanStep = loanStep;
	}

	/**
	 * app贷款标题
	 * @return
	 */
	public String getLoanTitle(){
		String typeName = LoanProcessTypeEnum.getTypeNameByType(this.getLoanProcessType());
		return typeName;
	}

	//拒绝原因
	public String getLoanRefuseReasonDisplay() {
		if(this.getLoanRefuseType()!=null) {
			if (LoanRefuseType.BANK.equalType(this.getLoanRefuseType())) {
				return this.getLoanBankRefuseReasonDisplay();
			} else if (LoanRefuseType.CUSTOMER.equalType(this.getLoanRefuseType())) {
				return this.getLoanCustomerRefuseReasonDisplay();
			}
		}
		return "";
	}

	//贷款过程名称
	public String getLoanProcessStateDisplay() {
		if(StringUtil.isNotEmpty(this.getLoanRefuseType())){
			if(LoanRefuseType.CUSTOMER.equalType(this.getLoanRefuseType())){
				return LoanRefuseType.CUSTOMER.typeName;
			}else if(LoanRefuseType.BANK.equalType(this.getLoanRefuseType())){
				return LoanRefuseType.BANK.typeName;
			}
		}else{
			String typeName = LoanProcessTypeEnum.getTypeNameByType(this.getLoanProcessType());
			return typeName;
		}
		return "";
	}

	//贷款催收状态名称
	public String getLoanCollectionStateDisplay() {
		LoanCollectionState state = LoanCollectionState.valueOfState(this.getLoanCollectionState());
		if(LoanCollectionState.COLLECTION.equals(state) && LoanProcessTypeEnum.AFTER_LOAN.equalType(this.getLoanProcessType())){
			if(this.getLoanRepayDate().compareTo(DateUtil.getNeedTime(0,0,0,0)) > -1){
				return "还款提醒";
			}else{
				return "不良催收";
			}
//			return "未提醒";
		}
		return "";
	}

	//贷款监控状态名称
	public String getLoanMonitorStateDisplay() {
		LoanMonitorState state  = LoanMonitorState.valueOfState(this.getLoanMonitorState());
		if(LoanMonitorState.MONITOR.equals(state)  && LoanProcessTypeEnum.AFTER_LOAN.equalType(this.getLoanProcessType())){
			return "监控";
		}
		return "";
	}

	public BigDecimal getLoanAmount(){
		if(StringUtil.isNotEmpty(this.getLoanProcessType())){
			if(LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.INVESTIGATE.equalType(this.getLoanProcessType())){
				return this.getLoanApplyAmount();
			}else if(LoanProcessTypeEnum.APPROVAL.equalType(this.getLoanProcessType())){
				return this.getLoanProposeAmount();
			}else{
				return this.getLoanResultAmount();
			}
		}
		return new BigDecimal(0.0);
	}

	//格式化后的代款金额
	public String getLoanAmountFormat() {
		if(StringUtil.isNotEmpty(this.getLoanProcessType())){
			if(LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType()) || LoanProcessTypeEnum.INVESTIGATE.equalType(this.getLoanProcessType())){
				return getDecimalFormat(this.getLoanApplyAmount());
			}else if(LoanProcessTypeEnum.APPROVAL.equalType(this.getLoanProcessType())){
				return getDecimalFormat(this.getLoanProposeAmount());
			}else{
				return getDecimalFormat(this.getLoanResultAmount());
			}
		}
		return "";
	}

	public String getLoanApplyAmountFormat(){
		if(this.getLoanApplyAmount()!=null){
			return getDecimalFormat(this.getLoanApplyAmount());
		}
		return "";
	}

	//应还款金额
	public String getLoanRepayAmountFormat(){
		if(this.getLoanRepayAmount()!=null){
			return getDecimalFormat(this.getLoanRepayAmount());
		}
		return "";
	}

	//决议金额
	public String getLoanResultAmountFormat(){
		if(this.getLoanResultAmount()!=null){
			return getDecimalFormat(this.getLoanResultAmount());
		}
		return "";
	}

	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	private String getDecimalFormat(BigDecimal amount){
		if(amount!=null){
			return FormatUtil.formatDecimal(amount);
		}
		return "";
	}

	public String getLoanIdentifyNumX() {
		loanIdentifyNumX = getLoanIdentifyNum();
		String identifyType = getLoanIdentifyType();
		return IdCardUtil.getIdentifyNumX(loanIdentifyNumX, identifyType ,1);
	}

	public String getLoanIdentifyNumX(int flag) {
		loanIdentifyNumX = getLoanIdentifyNum();
		String identifyType = getLoanIdentifyType();
		return IdCardUtil.getIdentifyNumX(loanIdentifyNumX, identifyType ,flag);
	}

	public void setLoanIdentifyNumX(String loanIdentifyNumX) {
		this.loanIdentifyNumX = loanIdentifyNumX;
	}

	public String getLoanTelNumX() {
		loanTelNumX = getLoanTelNum();
		if(StringUtils.isBlank(loanTelNumX))
			return "";
		return IdCardUtil.getTelNumX(loanTelNumX,1);
	}

	public String getLoanTelNumX(int flag) {
		loanTelNumX = getLoanTelNum();
		if(StringUtils.isBlank(loanTelNumX))
			return "";
		return IdCardUtil.getTelNumX(loanTelNumX,flag);
	}

	public void setLoanTelNumX(String loanTelNumX) {
		this.loanTelNumX = loanTelNumX;
	}

	public String getLoanAfterStateName() {
		return loanAfterStateName;
	}

	public void setLoanAfterStateName(String loanAfterStateName) {
		this.loanAfterStateName = loanAfterStateName;
	}

	public String getLoanBankRefuseReasonDisplay() {
		return loanBankRefuseReasonDisplay;
	}

	public void setLoanBankRefuseReasonDisplay(String loanBankRefuseReasonDisplay) {
		this.loanBankRefuseReasonDisplay = loanBankRefuseReasonDisplay;
	}

	public String getLoanCustomerRefuseReasonDisplay() {
		return loanCustomerRefuseReasonDisplay;
	}

	public void setLoanCustomerRefuseReasonDisplay(String loanCustomerRefuseReasonDisplay) {
		this.loanCustomerRefuseReasonDisplay = loanCustomerRefuseReasonDisplay;
	}

	public String getLoanAccountAmountFormat() {
		if(this.getLoanAccountAmount()!=null){
			return getDecimalFormat(this.getLoanAccountAmount());
		}
		return "";
	}

	public void setLoanAccountAmountFormat(String loanAccountAmountFormat) {
		this.loanAccountAmountFormat = loanAccountAmountFormat;
	}

	public String getNextRepaymentAmountFormat() {

		if(this.getNextRepaymentAmount()!=null){
			return getDecimalFormat(this.getNextRepaymentAmount());
		}
		return "";
	}

	public void setNextRepaymentAmountFormat(String nextRepaymentAmountFormat) {
		this.nextRepaymentAmountFormat = nextRepaymentAmountFormat;
	}

	public String getLoanBalanceAmountFormat() {
		if(this.getLoanBalanceAmount()!=null){
			return getDecimalFormat(this.getLoanBalanceAmount());
		}
		return "";
	}

	public void setLoanBalanceAmountFormat(String loanBalanceAmountFormat) {
		this.loanBalanceAmountFormat = loanBalanceAmountFormat;
	}

	public String getLoanArrearsFormat() {
		if(this.getLoanArrears()!=null){
			return getDecimalFormat(this.getLoanArrears());
		}
		return "";
	}

	public void setLoanArrearsFormat(String loanArrearsFormat) {
		this.loanArrearsFormat = loanArrearsFormat;
	}

	public String getLoanIrrevocableInterestFormat() {
		if(this.getLoanIrrevocableInterest()!=null){
			return getDecimalFormat(this.getLoanIrrevocableInterest());
		}
		return "";
	}

	public void setLoanIrrevocableInterestFormat(String loanIrrevocableInterestFormat) {
		this.loanIrrevocableInterestFormat = loanIrrevocableInterestFormat;
	}
}
