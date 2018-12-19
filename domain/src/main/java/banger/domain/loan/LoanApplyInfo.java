package banger.domain.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷款申请表
 */
public class LoanApplyInfo implements Serializable {
	private static final long serialVersionUID = 8105084607555260133L;
	private Integer loanId;					//主健ID
	private Integer loanTypeId;					//贷款类型ID
	private String loanProcessType;					//贷款过程类型
	private Integer loanAuditProcessId;					//贷款审核环节ID
	private Integer loanAuditFlowId;					//贷款审核流程ID
	private Integer loanAuditParamId;					//贷款审核参数ID
	private String loanName;					//借款人姓名
	private Integer loanAge;					//借款人年龄
	private String loanSex;					//借款人姓别
	private String loanIdentifyType;					//借款人证件类型
	private String loanIdentifyNum;					//借款人证件号码
	private String loanTelNum;					//借款人联系电话
	private Date loanApplyDate;					//贷款申请时间
	private Date loanAssignmentDate;					//贷款分配时间
	private Date loanInvestigationDate;					//贷款调查时间
	private Date loanAuditDate;					//贷款审合时间
	private Date loanCreditDate;					//贷款放款时间
	private Date loanRefuseDate;					//贷款拒绝时间
	private BigDecimal loanApplyAmount;					//贷款申请金额
	private BigDecimal loanProposeAmount;					//贷款建议金额
	private BigDecimal loanResultAmount;					//贷款决议金额
	private String loanRefuseType;					//贷款拒绝类型
	private String loanRefuseReason;					//贷款拒绝理由
	private Integer loanRefuseUser;					//贷款拒绝人
	private String loanRefuseRemark;					//贷款拒绝备注
	private String loanCollectionState;					//贷款催收状态
	private Date loanCollectionDate;					//贷款催收日期
	private String loanMonitorState;					//贷款监控状态
	private Date loanMonitorDate;					//贷款监控日期
	private BigDecimal loanRepayAmount;					//贷款应还款金额
	private Date loanRepayDate;					//贷款应还款日期
	private String loanMonitorType;					//贷款监控类型
	private String loanProcessTabs;					//历史过程类型
	private Integer loanApplyUserId;					//申请用户ID
	private Integer potentialCustomerId;                 //潜在用户id 默认为0
	private Integer loanInvestigationUserId;					//调查用户ID
	private Integer loanAllotUserId;					//分配用户ID
	private Integer loanCreditUserId;					//放款用户ID
	private Integer loanAfterGroupId;					//放款后团队ID
	private Integer loanBelongId;					//贷款归属人
	private String loanAfterState;					//贷款状态
	private String repaymentMode;					//还款方式等额本金等额本息按月还息到期还本一次性还本付息
	private Integer loanCustomerId;				//贷款的客户ID
	private String clientTime;					//移动端时间
	private Integer isDel;						//是否删除
	private Integer loanAuditState;				//审计状态
	private String loanAuditors;					//审计人
	private Integer loanClassId;					//三表类型
	private Date createDate;						//创建时间
	private Date updateDate;						//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private String loanBusinessCatalog;			//贷款行业分类
	private BigDecimal modelScore;				//评分模型
	private BigDecimal loanCreditAmount;			//放款金额

	private BigDecimal loanBalanceAmount;      	//贷款余额
	private BigDecimal loanAccountAmount;		//还款账户余额
	private String loanAccountNo;				//贷款账户
	private String loanContractNumber;			//放款合同号

	private Date loanContractBegin;				//合同开始日期
	private Date loanContractEnd;				//合同到期日期
	private Integer loanContractTypeId;         //贷款合同类型
	private Integer  syncStatus;                //接口同步状态
	private Integer  contractCheckUser;         //(合同审核人)签订人
	private String contractCode;               //合同编码
	private String guaranteeContractNo;      //担保合同号
	private String guaranteeContractCode;    //担保合同编码
	private String mortgageContractNo;      //抵押合同号
	private String mortgageContractCode;    //抵押合同编码
	private String pledgeContractNo;        //质押合同号
	private String pledgeContractCode;      //质押合同编码
	private String iouNum;						//借据号
	private String authorizationCode;		//授权编码
	private Date contractSubmitDate;        //合同提交时间
	private Date contractSignDate;          //合同签订时间
//	private Integer contractSignUserId;    //合同签订人
	private String accountNum;             //还款账号
	private String enterAccount;           //入账账号
	private String loanStatus;				//贷款状态
	private BigDecimal nextRepaymentAmount;	//下次还款金额
	private BigDecimal loanArrears;	//欠本金额
	private BigDecimal loanIrrevocableInterest;	//欠息金额
	private Integer isContractCancel;   //结清合同注销标志

	private Integer overdueLimit;//逾期期数

	public Integer getOverdueLimit() {
		return overdueLimit;
	}

	public void setOverdueLimit(Integer overdueLimit) {
		this.overdueLimit = overdueLimit;
	}

	public Integer getIsContractCancel() {
		return isContractCancel;
	}

	public void setIsContractCancel(Integer isContractCancel) {
		this.isContractCancel = isContractCancel;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getEnterAccount() {
		return enterAccount;
	}

	public void setEnterAccount(String enterAccount) {
		this.enterAccount = enterAccount;
	}

	public Date getContractSignDate() {
		return contractSignDate;
	}

	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}

	public Date getContractSubmitDate() {
		return contractSubmitDate;
	}

	public void setContractSubmitDate(Date contractSubmitDate) {
		this.contractSubmitDate = contractSubmitDate;
	}


	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getGuaranteeContractNo() {
		return guaranteeContractNo;
	}

	public void setGuaranteeContractNo(String guaranteeContractNo) {
		this.guaranteeContractNo = guaranteeContractNo;
	}

	public String getGuaranteeContractCode() {
		return guaranteeContractCode;
	}

	public void setGuaranteeContractCode(String guaranteeContractCode) {
		this.guaranteeContractCode = guaranteeContractCode;
	}

	public String getMortgageContractNo() {
		return mortgageContractNo;
	}

	public void setMortgageContractNo(String mortgageContractNo) {
		this.mortgageContractNo = mortgageContractNo;
	}

	public String getMortgageContractCode() {
		return mortgageContractCode;
	}

	public void setMortgageContractCode(String mortgageContractCode) {
		this.mortgageContractCode = mortgageContractCode;
	}

	public String getPledgeContractNo() {
		return pledgeContractNo;
	}

	public void setPledgeContractNo(String pledgeContractNo) {
		this.pledgeContractNo = pledgeContractNo;
	}

	public String getPledgeContractCode() {
		return pledgeContractCode;
	}

	public void setPledgeContractCode(String pledgeContractCode) {
		this.pledgeContractCode = pledgeContractCode;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Integer getContractCheckUser() {
		return contractCheckUser;
	}

	public void setContractCheckUser(Integer contractCheckUser) {
		this.contractCheckUser = contractCheckUser;
	}

	public Integer getLoanContractTypeId() {
		return loanContractTypeId;
	}

	public void setLoanContractTypeId(Integer loanContractTypeId) {
		this.loanContractTypeId = loanContractTypeId;
	}

	public Date getLoanContractBegin() {
		return loanContractBegin;
	}

	public void setLoanContractBegin(Date loanContractBegin) {
		this.loanContractBegin = loanContractBegin;
	}

	public Date getLoanContractEnd() {
		return loanContractEnd;
	}

	public void setLoanContractEnd(Date loanContractEnd) {
		this.loanContractEnd = loanContractEnd;
	}

	public BigDecimal getLoanAccountAmount() {
		return loanAccountAmount;
	}

	public void setLoanAccountAmount(BigDecimal loanAccountAmount) {
		this.loanAccountAmount = loanAccountAmount;
	}

	public String getLoanAccountNo() {
		return loanAccountNo;
	}

	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}

	public String getLoanContractNumber() {
		return loanContractNumber;
	}

	public void setLoanContractNumber(String loanContractNumber) {
		this.loanContractNumber = loanContractNumber;
	}

	public BigDecimal getLoanBalanceAmount() {
		return loanBalanceAmount;
	}

	public void setLoanBalanceAmount(BigDecimal loanBalanceAmount) {
		this.loanBalanceAmount = loanBalanceAmount;
	}

	public BigDecimal getModelScore() {
		return modelScore;
	}

	public void setModelScore(BigDecimal modelScore) {
		this.modelScore = modelScore;
	}

	public String getLoanBusinessCatalog() {
		return loanBusinessCatalog;
	}

	public void setLoanBusinessCatalog(String loanBusinessCatalog) {
		this.loanBusinessCatalog = loanBusinessCatalog;
	}

	public Integer getLoanId(){
		return this.loanId;
	}

	public void setLoanId(Integer loanId){
		this.loanId=loanId;
	}

	public Integer getLoanTypeId(){
		return this.loanTypeId;
	}

	public void setLoanTypeId(Integer loanTypeId){
		this.loanTypeId=loanTypeId;
	}

	public String getLoanProcessType(){
		return this.loanProcessType;
	}

	public void setLoanProcessType(String loanProcessType){
		this.loanProcessType=loanProcessType;
	}

	public Integer getLoanAuditProcessId(){
		return this.loanAuditProcessId;
	}

	public void setLoanAuditProcessId(Integer loanAuditProcessId){
		this.loanAuditProcessId=loanAuditProcessId;
	}

	public Integer getLoanAuditFlowId(){
		return this.loanAuditFlowId;
	}

	public void setLoanAuditFlowId(Integer loanAuditFlowId){
		this.loanAuditFlowId=loanAuditFlowId;
	}

	public Integer getLoanAuditParamId(){
		return this.loanAuditParamId;
	}

	public void setLoanAuditParamId(Integer loanAuditParamId){
		this.loanAuditParamId=loanAuditParamId;
	}

	public String getLoanName(){
		return this.loanName;
	}

	public void setLoanName(String loanName){
		this.loanName=loanName;
	}

	public Integer getLoanAge(){
		return this.loanAge;
	}

	public void setLoanAge(Integer loanAge){
		this.loanAge=loanAge;
	}

	public String getLoanSex(){
		return this.loanSex;
	}

	public void setLoanSex(String loanSex){
		this.loanSex=loanSex;
	}

	public String getLoanIdentifyType(){
		return this.loanIdentifyType;
	}

	public void setLoanIdentifyType(String loanIdentifyType){
		this.loanIdentifyType=loanIdentifyType;
	}

	public String getLoanIdentifyNum(){
		return this.loanIdentifyNum;
	}

	public void setLoanIdentifyNum(String loanIdentifyNum){
		this.loanIdentifyNum=loanIdentifyNum;
	}

	public String getLoanTelNum(){
		return this.loanTelNum;
	}

	public void setLoanTelNum(String loanTelNum){
		this.loanTelNum=loanTelNum;
	}

	public Date getLoanApplyDate(){
		return this.loanApplyDate;
	}

	public void setLoanApplyDate(Date loanApplyDate){
		this.loanApplyDate=loanApplyDate;
	}

	public Date getLoanAssignmentDate(){
		return this.loanAssignmentDate;
	}

	public void setLoanAssignmentDate(Date loanAssignmentDate){
		this.loanAssignmentDate=loanAssignmentDate;
	}

	public Date getLoanInvestigationDate(){
		return this.loanInvestigationDate;
	}

	public void setLoanInvestigationDate(Date loanInvestigationDate){
		this.loanInvestigationDate=loanInvestigationDate;
	}

	public Date getLoanAuditDate(){
		return this.loanAuditDate;
	}

	public void setLoanAuditDate(Date loanAuditDate){
		this.loanAuditDate=loanAuditDate;
	}

	public Date getLoanCreditDate(){
		return this.loanCreditDate;
	}

	public void setLoanCreditDate(Date loanCreditDate){
		this.loanCreditDate=loanCreditDate;
	}

	public Date getLoanRefuseDate(){
		return this.loanRefuseDate;
	}

	public void setLoanRefuseDate(Date loanRefuseDate){
		this.loanRefuseDate=loanRefuseDate;
	}

	public BigDecimal getLoanApplyAmount(){
		return this.loanApplyAmount;
	}

	public void setLoanApplyAmount(BigDecimal loanApplyAmount){
		this.loanApplyAmount=loanApplyAmount;
	}

	public BigDecimal getLoanProposeAmount(){
		return this.loanProposeAmount;
	}

	public void setLoanProposeAmount(BigDecimal loanProposeAmount){
		this.loanProposeAmount=loanProposeAmount;
	}

	public BigDecimal getLoanResultAmount(){
		return this.loanResultAmount;
	}

	public void setLoanResultAmount(BigDecimal loanResultAmount){
		this.loanResultAmount=loanResultAmount;
	}

	public String getLoanRefuseType(){
		return this.loanRefuseType;
	}

	public void setLoanRefuseType(String loanRefuseType){
		this.loanRefuseType=loanRefuseType;
	}

	public String getLoanRefuseReason(){
		return this.loanRefuseReason;
	}

	public void setLoanRefuseReason(String loanRefuseReason){
		this.loanRefuseReason=loanRefuseReason;
	}

	public Integer getLoanRefuseUser(){
		return this.loanRefuseUser;
	}

	public void setLoanRefuseUser(Integer loanRefuseUser){
		this.loanRefuseUser=loanRefuseUser;
	}

	public String getLoanRefuseRemark(){
		return this.loanRefuseRemark;
	}

	public void setLoanRefuseRemark(String loanRefuseRemark){
		this.loanRefuseRemark=loanRefuseRemark;
	}

	public String getLoanCollectionState(){
		return this.loanCollectionState;
	}

	public void setLoanCollectionState(String loanCollectionState){
		this.loanCollectionState=loanCollectionState;
	}

	public Date getLoanCollectionDate(){
		return this.loanCollectionDate;
	}

	public void setLoanCollectionDate(Date loanCollectionDate){
		this.loanCollectionDate=loanCollectionDate;
	}

	public String getLoanMonitorState(){
		return this.loanMonitorState;
	}

	public void setLoanMonitorState(String loanMonitorState){
		this.loanMonitorState=loanMonitorState;
	}

	public Date getLoanMonitorDate(){
		return this.loanMonitorDate;
	}

	public void setLoanMonitorDate(Date loanMonitorDate){
		this.loanMonitorDate=loanMonitorDate;
	}

	public BigDecimal getLoanRepayAmount(){
		return this.loanRepayAmount;
	}

	public void setLoanRepayAmount(BigDecimal loanRepayAmount){
		this.loanRepayAmount=loanRepayAmount;
	}

	public Date getLoanRepayDate(){
		return this.loanRepayDate;
	}

	public void setLoanRepayDate(Date loanRepayDate){
		this.loanRepayDate=loanRepayDate;
	}

	public String getLoanMonitorType(){
		return this.loanMonitorType;
	}

	public void setLoanMonitorType(String loanMonitorType){
		this.loanMonitorType=loanMonitorType;
	}

	public String getLoanProcessTabs(){
		return this.loanProcessTabs;
	}

	public void setLoanProcessTabs(String loanProcessTabs){
		this.loanProcessTabs=loanProcessTabs;
	}

	public Integer getLoanApplyUserId(){
		return this.loanApplyUserId;
	}

	public void setLoanApplyUserId(Integer loanApplyUserId){
		this.loanApplyUserId=loanApplyUserId;
	}

	public Integer getLoanInvestigationUserId(){
		return this.loanInvestigationUserId;
	}

	public void setLoanInvestigationUserId(Integer loanInvestigationUserId){
		this.loanInvestigationUserId=loanInvestigationUserId;
	}

	public Integer getLoanAllotUserId(){
		return this.loanAllotUserId;
	}

	public void setLoanAllotUserId(Integer loanAllotUserId){
		this.loanAllotUserId=loanAllotUserId;
	}

	public Integer getLoanCreditUserId(){
		return this.loanCreditUserId;
	}

	public void setLoanCreditUserId(Integer loanCreditUserId){
		this.loanCreditUserId=loanCreditUserId;
	}

	public Integer getLoanAfterGroupId(){
		return this.loanAfterGroupId;
	}

	public void setLoanAfterGroupId(Integer loanAfterGroupId){
		this.loanAfterGroupId=loanAfterGroupId;
	}

	public Integer getLoanBelongId(){
		return this.loanBelongId;
	}

	public void setLoanBelongId(Integer loanBelongId){
		this.loanBelongId=loanBelongId;
	}

	public String getLoanAfterState(){
		return this.loanAfterState;
	}

	public void setLoanAfterState(String loanAfterState){
		this.loanAfterState=loanAfterState;
	}

	public String getRepaymentMode(){
		return this.repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode){
		this.repaymentMode=repaymentMode;
	}

	public Integer getLoanCustomerId(){
		return this.loanCustomerId;
	}

	public void setLoanCustomerId(Integer loanCustomerId){
		this.loanCustomerId=loanCustomerId;
	}

	public String getClientTime(){
		return this.clientTime;
	}

	public void setClientTime(String clientTime){
		this.clientTime=clientTime;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getLoanAuditState(){
		return this.loanAuditState;
	}

	public void setLoanAuditState(Integer loanAuditState){
		this.loanAuditState=loanAuditState;
	}

	public String getLoanAuditors(){
		return this.loanAuditors;
	}

	public void setLoanAuditors(String loanAuditors){
		this.loanAuditors=loanAuditors;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public Integer getLoanClassId() {
		return loanClassId;
	}

	public void setLoanClassId(Integer loanClassId) {
		this.loanClassId = loanClassId;
	}

	public BigDecimal getLoanCreditAmount() {
		return loanCreditAmount;
	}

	public void setLoanCreditAmount(BigDecimal loanCreditAmount) {
		this.loanCreditAmount = loanCreditAmount;
	}

	public Integer getPotentialCustomerId() {
		return potentialCustomerId;
	}

	public void setPotentialCustomerId(Integer potentialCustomerId) {
		this.potentialCustomerId = potentialCustomerId;
	}

	public String getIouNum() {
		return iouNum;
	}

	public void setIouNum(String iouNum) {
		this.iouNum = iouNum;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public BigDecimal getNextRepaymentAmount() {
		return nextRepaymentAmount;
	}

	public void setNextRepaymentAmount(BigDecimal nextRepaymentAmount) {
		this.nextRepaymentAmount = nextRepaymentAmount;
	}

	public BigDecimal getLoanArrears() {
		return loanArrears;
	}

	public void setLoanArrears(BigDecimal loanArrears) {
		this.loanArrears = loanArrears;
	}

	public BigDecimal getLoanIrrevocableInterest() {
		return loanIrrevocableInterest;
	}

	public void setLoanIrrevocableInterest(BigDecimal loanIrrevocableInterest) {
		this.loanIrrevocableInterest = loanIrrevocableInterest;
	}
}
