package banger.moduleIntf;

import banger.domain.loan.LoanCrossCheckNetProfit;

public interface ILoanCrossCheckNetProfitProvider {

    /**
     * app端通过贷款id获取净利检验详情
     * @param loanId 贷款id ,
     * @return
     */
    LoanCrossCheckNetProfit getCrossCheckNetProfitByLoanId(Integer loanId);

    /**
     * app端保存净利检验详情（第一次时的插入表）
     * @param crossCheckNetProfit 实体对象
     * @param loginUserId 登入用户Id
     * @return
     */
    void saveCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit,Integer loginUserId);

    /**
     * 计算更新毛利表和净利表中偏差率（用在检查净利率，检查毛利率，和损益表中毛利率发生改变时调用）
     * @param loanId
     */
    void updateGroProAndNetProDeviation(Integer loanId);

    /**
     * 计算并修改交叉检验净利表偏差率 （用在检查净利率发生改变时使用）
     * @param loanId 贷款id
     */
    void updateNetProDev(Integer loanId);

}
