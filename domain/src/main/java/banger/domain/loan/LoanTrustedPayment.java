package banger.domain.loan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 受托支付
 */
public class LoanTrustedPayment implements Serializable {

    private static final long serialVersionUID = 6868433187009297185L;
    private Integer id;                         //主健ID
    private Integer loanId;					//贷款
    private String loanProcessType;					//贷款过程类型
    private String paymentId;                       //支付ID
    private String paymentStatus;                   //支付状态 支付状态  0待同步 1已同步 2支付成功  3支付失败
    private String isBankAccount;                   //是否本行账号
    private String counterpartyAccount;             //交易对手账号
    private String counterpartyCardNumber;          //交易对手卡号
    private String counterpartyName;                 //交易对手名称
    private String counterpartyBankName;            //交易对手开户行行名
    private String counterpartyBankNumber;          //交易对手开户行行号
    private BigDecimal paymentAmount;                   //交易金额
    private Date createDate;						//创建时间
    private Date updateDate;						//更新时间
    private Integer createUser;					//创建用户
    private Integer updateUser;					//更新用户


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getLoanProcessType() {
        return loanProcessType;
    }

    public void setLoanProcessType(String loanProcessType) {
        this.loanProcessType = loanProcessType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getIsBankAccount() {
        return isBankAccount;
    }

    public void setIsBankAccount(String isBankAccount) {
        this.isBankAccount = isBankAccount;
    }

    public String getCounterpartyAccount() {
        return counterpartyAccount;
    }

    public void setCounterpartyAccount(String counterpartyAccount) {
        this.counterpartyAccount = counterpartyAccount;
    }

    public String getCounterpartyCardNumber() {
        return counterpartyCardNumber;
    }

    public void setCounterpartyCardNumber(String counterpartyCardNumber) {
        this.counterpartyCardNumber = counterpartyCardNumber;
    }

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    public String getCounterpartyBankName() {
        return counterpartyBankName;
    }

    public void setCounterpartyBankName(String counterpartyBankName) {
        this.counterpartyBankName = counterpartyBankName;
    }

    public String getCounterpartyBankNumber() {
        return counterpartyBankNumber;
    }

    public void setCounterpartyBankNumber(String counterpartyBankNumber) {
        this.counterpartyBankNumber = counterpartyBankNumber;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}
