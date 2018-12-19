package banger.domain.app;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/18.
 */
public class AppScreenLoan implements Serializable {

    private static final long serialVersionUID = 5004287646943648289L;
    private String userName;
    private String loanTypeName;
    private Integer allNum;
    private BigDecimal allMoney;
    private BigDecimal money;
    private Integer num;
    private Integer refusenum;
    private Integer legalid;

    public Integer getLegalid() {
        return legalid;
    }

    public void setLegalid(Integer legalid) {
        this.legalid = legalid;
    }

    public Integer getRefusenum() {
        return refusenum;
    }

    public void setRefusenum(Integer refusenum) {
        this.refusenum = refusenum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public BigDecimal getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(BigDecimal allMoney) {
        this.allMoney = allMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
