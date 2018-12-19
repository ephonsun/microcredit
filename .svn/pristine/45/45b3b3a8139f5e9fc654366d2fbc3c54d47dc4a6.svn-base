package banger.moduleIntf;


import banger.domain.loan.LoanProfitLossRatioItem;
import java.util.List;

public interface ILoanProfitLossRatioItemProvider {
    /**
     * 查询毛利率列表
     */
    List<LoanProfitLossRatioItem> queryGrossProfitMarginList(Integer loanId,String columnName);

    /**
     * 根据主键id删除毛利率明细表
     * @param id
     */
    void deleteProfitLossRatioItemById(Integer id);

    LoanProfitLossRatioItem getProfitLossRatioItemById(Integer id);

    void insertProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem,Integer loginUserId);

    void updateProfitLossRatioItem(LoanProfitLossRatioItem profitLossRatioItem,Integer loginUserId);
}
