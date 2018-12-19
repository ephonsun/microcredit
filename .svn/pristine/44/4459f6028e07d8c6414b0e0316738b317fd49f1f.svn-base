package banger.domain.loan;

/**
 * 审核人员
 * Created by zhusw on 2017/6/19.
 */

import java.math.BigDecimal;

/**
 * 审核用户
 */
public class LoanExamineUser {
    private Integer userId;         //用户ID
    private String userName;        //用户姓名
    private BigDecimal limitAmount;             //审核限额
    private boolean limitEnable;                //是否符合审核金额

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public boolean isLimitEnable() {
        return limitEnable;
    }

    public void setLimitEnable(boolean limitEnable) {
        this.limitEnable = limitEnable;
    }

    public LoanExamineUser(){
        this.limitEnable = false;
    }
}
