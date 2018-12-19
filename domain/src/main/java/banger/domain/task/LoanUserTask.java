package banger.domain.task;

import java.math.BigDecimal;

/**
 * 统计贷款任务的完成情况
 * Created by zhusw on 2017/7/18.
 */
public class LoanUserTask {
    private Integer userId;                     //用户ID
    private Integer groupId;                    //团队Id
    private BigDecimal loanAmount;              //贷款总额
    private Integer loanTotal;                  //贷款总数

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTotal() {
        return loanTotal;
    }

    public void setLoanTotal(Integer loanTotal) {
        this.loanTotal = loanTotal;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
