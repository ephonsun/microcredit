package banger.moduleIntf;

import banger.domain.loan.*;

import java.math.BigDecimal;
import java.util.List;

public interface ILoanAssetsProvider {


    LoanAssetsInfo getAssetsInfoByLoanId(Integer loanId);

    void insertAssetsAccountItem(LoanAssetsAccountItem accountItem);

    void updateAssetsAccountItem(LoanAssetsAccountItem accountItem);

    void insertAssetsAmountItem(LoanAssetsAmountItem amountItem);

    void updateAssetsAmountItem(LoanAssetsAmountItem amountItem);

    void insertAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem);

    void updateAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem);

    void insertAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem);

    void updateAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem);

    void insertAssetsStockItem(LoanAssetsStockItem assetsStockItem);

    void updateAssetsStockItem(LoanAssetsStockItem assetsStockItem);

    LoanAssetsAccountItem getAssetsAccountById(Integer id);

    LoanAssetsDebtsItem getAssetsDebtsById(Integer id);

    LoanAssetsFixedItem getAssetsFixedById(Integer id);

    LoanAssetsAmountItem getAssetsAmountById(Integer id);

    LoanAssetsStockItem getAssetsStockById(Integer id);

    void updateAssetsAmount(Integer loanId, Integer loanClassId, Integer userId, BigDecimal amount, String columnName);

    List<LoanAssetsAmountItem> getAssetsItemListByColumnName(Integer loanId, String columnName, String tableName);

    void removeAssetsAccountById(Integer id);

    void removeAssetsDebtsById(Integer id);

    void removeAssetsFixedById(Integer id);

    void removeAssetsAmountById(Integer id);

    void removeAssetsStockById(Integer id);

    void updateAssetsInfo(LoanAssetsInfo assetsInfo);

    void insertAssetsFixedItem(LoanAssetsInfo assetsInfo);
}
