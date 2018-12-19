package banger.moduleIntf;

import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanAssetsInfo;
import banger.domain.loan.LoanProfitLossInfo;
import banger.domain.loan.finance.*;

/**
 * Created by Administrator on 2017/8/24.
 */
public interface ILoanFinanceAnalysisService {

    ConsumeAssetAnalysis getConsumeAssetAnalysis(LoanAssetsInfo loanAssetsInfo);

    ConsumeBreakEvenAnalysis getConsumeBreakEvenAnalysis(LoanProfitLossInfo loanProfitLossInfo);

    ConsumeSolvencyAnalysis getConsumeSolvencyAnalysis(ConsumeAssetAnalysis consumeAssetAnalysis);

    ManageAssetAnalysis getManageAssetAnalysis(LoanAssetsInfo loanAssetsInfo, LoanApplyInfo loanBusinessCatalog);

    ManageBreakEvenAnalysis getManageBreakEvenAnalysis(LoanProfitLossInfo loanProfitLossInfo, ManageAssetAnalysis manageAssetAnalysis, LoanApplyInfo loanBusinessCatalog);

    ManageSolvencyAnalysis getManageSolvencyAnalysis(ManageAssetAnalysis manageAssetAnalysis, LoanAssetsInfo loanAssetsInfo, LoanProfitLossInfo loanProfitLossInfo, LoanApplyInfo loanBusinessCatalog);

    ManageTurnoverRatioAnalysis getManageTurnoverRatioAnalysis(ManageAssetAnalysis manageAssetAnalysis, ManageBreakEvenAnalysis manageBreakEvenAnalysis, LoanAssetsInfo loanAssetsInfo, LoanApplyInfo loanBusinessCatalog);


}
