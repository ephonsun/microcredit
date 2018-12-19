package banger.domain.system;

import java.io.Serializable;

/**
 * Created by wanggl on 2017/7/31.
 */
public class SysWorkingTable implements Serializable {
    private Integer apply;
    private Integer allot;
    private Integer investigate;
    private Integer approval;
    private Integer contract;
    private Integer sign;
    private Integer loan;
    private Integer afterLoan;
    private Integer collection;
    private Integer collectionReminder; //还款提醒条数
    private Integer collectionBad;      //不良催收
    private Integer monitor;
    private Integer custSchedule;
    private Integer intoAllot;
    private Integer credit;             //征信
    private Integer authorized; //待授权

    public Integer getCustSchedule() {
        return custSchedule;
    }

    public void setCustSchedule(Integer custSchedule) {
        this.custSchedule = custSchedule;
    }

    public Integer getApply() {
        return apply;
    }

    public void setApply(Integer apply) {
        this.apply = apply;
    }

    public Integer getAllot() {
        return allot;
    }

    public void setAllot(Integer allot) {
        this.allot = allot;
    }

    public Integer getInvestigate() {
        return investigate;
    }

    public void setInvestigate(Integer investigate) {
        this.investigate = investigate;
    }

    public Integer getApproval() {
        return approval;
    }

    public void setApproval(Integer approval) {
        this.approval = approval;
    }

    public Integer getContract() {
        return contract;
    }

    public void setContract(Integer contract) {
        this.contract = contract;
    }

    public Integer getLoan() {
        return loan;
    }

    public void setLoan(Integer loan) {
        this.loan = loan;
    }

    public Integer getAfterLoan() {
        return afterLoan;
    }

    public void setAfterLoan(Integer afterLoan) {
        this.afterLoan = afterLoan;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getCollectionReminder() {
        return collectionReminder;
    }

    public void setCollectionReminder(Integer collectionReminder) {
        this.collectionReminder = collectionReminder;
    }

    public Integer getCollectionBad() {
        return collectionBad;
    }

    public void setCollectionBad(Integer collectionBad) {
        this.collectionBad = collectionBad;
    }

    public Integer getMonitor() {
        return monitor;
    }

    public void setMonitor(Integer monitor) {
        this.monitor = monitor;
    }

    public Integer getIntoAllot() {
        return intoAllot;
    }

    public void setIntoAllot(Integer intoAllot) {
        this.intoAllot = intoAllot;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

}
