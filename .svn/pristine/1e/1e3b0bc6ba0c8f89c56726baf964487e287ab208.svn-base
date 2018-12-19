package banger.moduleIntf;
import banger.domain.loan.LoanProfitLossInfo;

import java.util.Map;

public interface ILoanIncomeStatementProvider {
    /**
     *
     * 查询损益主界面
     * @param loanId
     * @return
     */
    LoanProfitLossInfo getLoanProfitLossInfoByLoanId(Integer loanId);

    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description: 修改收入和支出,主表要重新被修改
     * @Date: 12:52 2017/8/24
     */
    Boolean updateLoanProfitLossInfoByOpt(Integer loanId,Integer loanClassId,Integer loginUserId);
    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:更改月份的支出和收入,修改收入和支出明细表
     * @Date: 17:58 2017/8/25
     */

    Boolean updateLoanProfitBizIncomeItemByOpt(Integer loanId,Integer month,Integer loginUserId);

    LoanProfitLossInfo getProfitLossInfoByLoanId(Integer loanId);

    void updateProfitLossInfoByLoanId(LoanProfitLossInfo profitLossInfo,Integer loginUserId);

    Boolean updateInterval(String startYearMonth, String endYearMonth, String loanId, String loanClassId,String userId);


}
