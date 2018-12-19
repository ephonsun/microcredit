package banger.domain.loan;

import banger.framework.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanggl on 2017/6/20.
 */
public class LoanRepayPlanInfoExtend extends LoanRepayPlanInfo{

    public BigDecimal repayment;

    private String userName;

    private Boolean isSj;

    private Boolean manager;

    //是否自动还款
    private Boolean isAutoRepay;

    //是否允许编辑还款计划(是否进入催收阶段)
    private Boolean isCollection;

    //格式化的还款本金
    private String loanPrincipalAmountF;
    //格式化的还款利息
    private String loanAccrualAmountF;
    //格式化的实际还款金额(本金）
    private String loanRepayAmountF;
    //格式化的实际还款利息
    private String loanRepayAccrualAmountF;
    //起息日
    private Date ratioDate;
    //起息日
    private String ratioDateF;

    public String getRatioDateF() {
        return  DateUtil.format(getRatioDate(), DateUtil.DEFAULT_DATE_FORMAT);
    }

    public void setRatioDateF(String ratioDateF) {
        this.ratioDateF = ratioDateF;
    }

    public String getLoanRepayPlanDateF() {
        return DateUtil.format(getLoanRepayPlanDate(), DateUtil.DEFAULT_DATE_FORMAT);
    }

    public void setLoanRepayPlanDateF(String loanRepayPlanDateF) {
        this.loanRepayPlanDateF = loanRepayPlanDateF;
    }

    //格式化计划还款日期
    private String loanRepayPlanDateF;

    public Boolean getCollection() {
        return isCollection;
    }

    public void setCollection(Boolean collection) {
        isCollection = collection;
    }

    public BigDecimal getRepayment() {
        if (getLoanPrincipalAmount() == null)
            return getLoanAccrualAmount();
        if (getLoanAccrualAmount() == null)
            return getLoanPrincipalAmount();
        return getLoanPrincipalAmount().add(getLoanAccrualAmount());
    }

    public void setRepayment(BigDecimal repayment) {
        this.repayment = repayment;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getSj() {
        return isSj;
    }

    public void setSj(Boolean sj) {
        isSj = sj;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Boolean getAutoRepay() {
        return isAutoRepay;
    }

    public void setAutoRepay(Boolean autoRepay) {
        isAutoRepay = autoRepay;
    }

    public String getLoanPrincipalAmountF() {
        return loanPrincipalAmountF;
    }

    public void setLoanPrincipalAmountF(String loanPrincipalAmountF) {
        this.loanPrincipalAmountF = loanPrincipalAmountF;
    }

    public String getLoanAccrualAmountF() {
        return loanAccrualAmountF;
    }

    public void setLoanAccrualAmountF(String loanAccrualAmountF) {
        this.loanAccrualAmountF = loanAccrualAmountF;
    }

    public String getLoanRepayAmountF() {
        return loanRepayAmountF;
    }

    public void setLoanRepayAmountF(String loanRepayAmountF) {
        this.loanRepayAmountF = loanRepayAmountF;
    }

    public String getLoanRepayAccrualAmountF() {
        return loanRepayAccrualAmountF;
    }

    public void setLoanRepayAccrualAmountF(String loanRepayAccrualAmountF) {
        this.loanRepayAccrualAmountF = loanRepayAccrualAmountF;
    }

    public void setDefaultValue(Integer loginUserId, Integer loanId){
        setCreateDate(new Date());
        setCreateUser(loginUserId);
        setUpdateDate(new Date());
        setUpdateUser(loginUserId);
        setLoanRepayState(0);
        setLoanIsOverdue(0);
        setLoanId(loanId);
    }

    public Date getRatioDate() {
        return ratioDate;
    }

    public void setRatioDate(Date ratioDate) {
        this.ratioDate = ratioDate;
    }
}
