package banger.moduleIntf;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.domain.loan.LoanCrossCheckNetProfit;
import banger.domain.loan.LoanCrossCheckSale;

public interface ILoanCrossCheckSaleProvider {

    /**
     * 通过贷款id得到交叉检验权益表
     * @param loanId
     */
    LoanCrossCheckSale getCrossCheckSaleByLoanId(Integer loanId);

    /**
     * app端保存销售额检验详情（第一次时的插入表）
     * @param crossCheckSale 实体对象
     * @param loginUserId 登入用户Id
     * @return
     */
    void saveCrossCheckSale(LoanCrossCheckSale crossCheckSale, Integer loginUserId);

    /**
     * 计算并修改交叉检验销售额表偏差率 （用在检查销售率发生改变时使用）
     * @param loanId 贷款id
     */
    void updateSaleDev(Integer loanId);
}
