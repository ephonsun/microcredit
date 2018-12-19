package banger.moduleIntf;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.domain.loan.LoanCrossCheckNetProfit;
import banger.domain.loan.LoanCrossCheckQuanyiquan;

public interface ILoanCrossCheckQuanyiquanProvider {

    /**
     * 通过贷款id得到交叉检验权益表
     * @param loanId
     */
    LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanByLoanId(Integer loanId);


    /**
     * app端保存净利检验详情（第一次时的插入表）
     * @param crossCheckQuanyiquan 实体对象
     * @param loginUserId 登入用户Id
     * @return
     */
    void saveCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan, Integer loginUserId);


    /**
     * 计算并修改交叉检验权益表偏差率 （用在检查权益表发生改变时使用）
     * @param loanId 贷款id
     */
    void updateQuanyiquanDev(Integer loanId);
}
