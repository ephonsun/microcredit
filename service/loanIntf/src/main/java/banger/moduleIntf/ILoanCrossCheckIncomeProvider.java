package banger.moduleIntf;

import banger.domain.loan.LoanCrossCheckIncome;

public interface ILoanCrossCheckIncomeProvider {

    /**
     * 通过贷款id获取交叉检验 收入检验详情
     * @param loanId
     * @return
     */
    LoanCrossCheckIncome getCrossCheckIncomeByLoanId(Integer loanId);

    /**
     * 保存收入检验信息
     * @param lcci
     */
    void saveLoanCrossCheckIncome(LoanCrossCheckIncome lcci,Integer userId);

}
