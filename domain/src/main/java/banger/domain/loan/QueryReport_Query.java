package banger.domain.loan;

import antlr.StringUtils;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.framework.util.StringUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryReport_Query implements Serializable{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");;
	private String loanStatus;//贷款阶段
	private String creditDate;//放款日期
	private String customerName;//客户姓名
	private String  approvalMoney;//决议金额
	private String  creditMoney;//放款金额
	private String loanLimit;//贷款期限
	private String loanRatio;//贷款利率
	private String repayWay;//还款方式
	private String guarantorWay;//担保方式
	private String applyUserName;//客户经理-申请
	private String investigateUserName;//客户经理-调查
	private String productName;//产品名称
	private String loanType;//贷款类型
	private String isNew;//贷款形式
	private String isAgriculture;//是否涉农
	private String customerLevel;//客户类型
	private String industryCode;//所属行业
	private String businessScope;//经营范围
	private String isReferred;//是否转介
	private String referredUser;//转介人员
	private String referredDept;//转介支行
	private String loanContractNum;//合同号
	private String loanAccountNo;//贷款账号
	private String loanContractBegin;//合同开始时间
	private String loanContractEnd;//合同结束时间
	private String isLocal;//是否居民
	private String iouAmount;//放款金额
	private String balanceAmount;//贷款余额
	private String businessLineName;//业务条线
	private String loanRatioDate;//放款开始时间
	private String loanEndDate;//放款结束时间
	private String contractCode;//合同编码
	private String id;//主键
	private String loanAfterState;
	private String fiveMarks;
	private String orientationName;

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus==null?"": LoanProcessTypeEnum.getTypeNameByType(loanStatus);
	}

	public String getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate==null?"":sdf.format(creditDate);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApprovalMoney() {
		return approvalMoney;
	}

	public void setApprovalMoney(BigDecimal approvalMoney) {
		this.approvalMoney = approvalMoney==null?"":approvalMoney.stripTrailingZeros().toPlainString();
	}

	public String getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(BigDecimal creditMoney) {
		this.creditMoney = creditMoney==null?"":creditMoney.stripTrailingZeros().toPlainString();
	}

	public String getLoanLimit() {
		return loanLimit;
	}

	public void setLoanLimit(String loanLimit) {
		this.loanLimit = loanLimit;
	}

	public String getLoanRatio() {
		return loanRatio;
	}

	public void setLoanRatio(BigDecimal loanRatio) {
		this.loanRatio = loanRatio==null?"":loanRatio.stripTrailingZeros().toPlainString();
	}

	public String getRepayWay() {
		return repayWay;
	}

	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}

	public String getGuarantorWay() {
		return guarantorWay;
	}

	public void setGuarantorWay(String guarantorWay) {
		this.guarantorWay = guarantorWay;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName= "0".equals(applyUserName)?"":applyUserName;
	}

	public String getInvestigateUserName() {
		return investigateUserName;
	}

	public void setInvestigateUserName(String investigateUserName) {
		this.investigateUserName = "0".equals(investigateUserName)?"":investigateUserName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getIsAgriculture() {
		return isAgriculture;
	}

	public void setIsAgriculture(String isAgriculture) {
		this.isAgriculture = isAgriculture;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getIsReferred() {
		return isReferred;
	}

	public void setIsReferred(String isReferred) {
		this.isReferred = isReferred;
	}

	public String getReferredUser() {
		return referredUser;
	}

	public void setReferredUser(String referredUser) {
		this.referredUser = referredUser;
	}

	public String getReferredDept() {
		return referredDept;
	}

	public void setReferredDept(String referredDept) {
		this.referredDept = referredDept;
	}

	public String getLoanContractNum() {
		return loanContractNum;
	}

	public void setLoanContractNum(String loanContractNum) {
		this.loanContractNum = loanContractNum;
	}

	public String getLoanAccountNo() {
		return loanAccountNo;
	}

	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}

	public String getLoanContractBegin() {
		return loanContractBegin;
	}

	public void setLoanContractBegin(Date loanContractBegin) {
		this.loanContractBegin = loanContractBegin==null?"":sdf.format(loanContractBegin);
	}

	public String getLoanContractEnd() {
		return loanContractEnd;
	}

	public void setLoanContractEnd(Date loanContractEnd) {
		this.loanContractEnd = loanContractEnd==null?"":sdf.format(loanContractEnd);
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}

	public String getIouAmount() {
		return iouAmount;
	}

	public void setIouAmount(BigDecimal iouAmount) {
		this.iouAmount = iouAmount==null?"":iouAmount.stripTrailingZeros().toPlainString();
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount==null?"":balanceAmount.stripTrailingZeros().toPlainString();
	}

	public String getBusinessLineName() {
		return businessLineName;
	}

	public void setBusinessLineName(String businessLineName) {
		this.businessLineName = businessLineName;
	}

	public String getLoanRatioDate() {
		return loanRatioDate;
	}

	public void setLoanRatioDate(Date loanRatioDate) {
		this.loanRatioDate = loanRatioDate==null?"":sdf.format(loanRatioDate);
	}

	public String getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate==null?"":sdf.format(loanEndDate);
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanAfterState() {
		return loanAfterState;
	}

	public void setLoanAfterState(String loanAfterState) {
		this.loanAfterState = loanAfterState;
	}

	public String getFiveMarks() {
		return fiveMarks;
	}

	public void setFiveMarks(String fiveMarks) {
		this.fiveMarks = fiveMarks;
	}

	public String getOrientationName() {
		return orientationName;
	}

	public void setOrientationName(String orientationName) {
		this.orientationName = orientationName==null?"":orientationName.replaceAll("&gt;",">");
	}
}
