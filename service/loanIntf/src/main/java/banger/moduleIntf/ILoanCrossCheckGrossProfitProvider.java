package banger.moduleIntf;

import banger.domain.loan.LoanCrossCheckGrossProfit;
import banger.domain.loan.LoanCrossCheckIncome;

public interface ILoanCrossCheckGrossProfitProvider {

    /**
     * 根据贷款id获取毛利检验信息
     * @param loanId
     * @return
     */
     LoanCrossCheckGrossProfit getCrossCheckGrossProfitByLoanId(Integer loanId);

    /**
     *保存毛利检验信息
     *
     * @param lccgp
     * @param userId
     */
    void saveLoanCrossCheckGrossProfit(LoanCrossCheckGrossProfit lccgp,Integer userId);

    /**
     * 计算并修改交叉检验毛利表偏差率（用在检查毛利率发生改变时使用）
     * @param loanId 贷款id
     */
    void updateGroProDev(Integer loanId);
}
