package banger.service.impl;

import banger.common.tools.StringUtil;
import banger.dao.intf.IIndustryGradeexpDao;
import banger.domain.enumerate.LoanIndustryGradeEnum;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanAssetsInfo;
import banger.domain.loan.LoanIndustryGradeexp;
import banger.domain.loan.LoanProfitLossInfo;
import banger.domain.loan.finance.ManageAssetAnalysis;
import banger.domain.loan.finance.ManageBreakEvenAnalysis;
import banger.domain.loan.finance.ManageSolvencyAnalysis;
import banger.domain.loan.finance.ManageTurnoverRatioAnalysis;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IIndustryGradeexpProvider;
import banger.moduleIntf.ILoanIncomeStatementProvider;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.IIndustryGradeexpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款行业指引等级说明表业务访问类
 */
@Repository
public class IndustryGradeexpService implements IIndustryGradeexpService,IIndustryGradeexpProvider {

	@Autowired
	private IIndustryGradeexpDao industryGradeexpDao;

	@Resource
	private ILoanModule loanModule;
	@Resource
	private ILoanIncomeStatementProvider loanIncomeStatementProvider;

	/**
	 * 新增贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertIndustryGradeexp(LoanIndustryGradeexp industryGradeexp,Integer loginUserId){
		industryGradeexp.setCreateDate(DateUtil.getCurrentDate());
		industryGradeexp.setUpdateDate(DateUtil.getCurrentDate());
		this.industryGradeexpDao.insertIndustryGradeexp(industryGradeexp);
	}

	/**
	 *修改贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateIndustryGradeexp(LoanIndustryGradeexp industryGradeexp,Integer loginUserId){
		industryGradeexp.setUpdateDate(DateUtil.getCurrentDate());
		this.industryGradeexpDao.updateIndustryGradeexp(industryGradeexp);
	}

	/**
	 * 通过主键删除贷款行业指引等级说明表
	 * @param id 主键Id
	 */
	public void deleteIndustryGradeexpById(Integer id){
		this.industryGradeexpDao.deleteIndustryGradeexpById(id);
	}

	/**
	 * 通过主键得到贷款行业指引等级说明表
	 * @param id 主键Id
	 */
	public LoanIndustryGradeexp getIndustryGradeexpById(Integer id){
		return this.industryGradeexpDao.getIndustryGradeexpById(id);
	}

	/**
	 * 查询贷款行业指引等级说明表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanIndustryGradeexp> queryIndustryGradeexpList(Map<String,Object> condition){
		return this.industryGradeexpDao.queryIndustryGradeexpList(condition);
	}

	/**
	 * 分页查询贷款行业指引等级说明表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanIndustryGradeexp> queryIndustryGradeexpList(Map<String,Object> condition,IPageSize page){
		return this.industryGradeexpDao.queryIndustryGradeexpList(condition,page);
	}

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description: 根据loanId更新或者新增贷款行业指引等级说明
	 * @Date: 9:19 2017/9/25
	 */
	public void saveIndustryGradeexpByLoanId(Integer loanId) {
		LoanIndustryGradeexp gradeexp = null;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanId", loanId);
		LoanApplyInfo applyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
		if (LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType()) && applyInfo.getLoanClassId() == 1) {
			//资产分析
			LoanAssetsInfo loanAssetsInfo = loanModule.getAssetsInfoProvider().queryOneAssetsInfoList(condition);
			ManageAssetAnalysis manageAssetAnalysis = loanModule.getLoanFinanceAnalysisService().getManageAssetAnalysis(loanAssetsInfo, applyInfo);
			//损益分析
			LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(loanId);
			ManageBreakEvenAnalysis manageBreakEvenAnalysis = loanModule.getLoanFinanceAnalysisService().getManageBreakEvenAnalysis(loanProfitLossInfo, manageAssetAnalysis, applyInfo);
			//偿还能力分析
			ManageSolvencyAnalysis manageSolvencyAnalysis = loanModule.getLoanFinanceAnalysisService().getManageSolvencyAnalysis(manageAssetAnalysis, loanAssetsInfo, loanProfitLossInfo, applyInfo);
			//周转率分析
			ManageTurnoverRatioAnalysis manageTurnoverRatioAnalysis = loanModule.getLoanFinanceAnalysisService().getManageTurnoverRatioAnalysis(manageAssetAnalysis, manageBreakEvenAnalysis, loanAssetsInfo, applyInfo);

			//根据loanId删除之前贷款行业指引等级说明
			industryGradeexpDao.deleteIndustryGradeexpByLoanId(loanId);
			//资产分析_资产_负债率
			if (StringUtil.isNotEmpty(manageAssetAnalysis.getAssetLiabilityRatioGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.资产_负债率.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.资产_负债率.item);
				gradeexp.setItemGradecn(manageAssetAnalysis.getAssetLiabilityRatioGrade());
				gradeexp.setItemGradeen(manageAssetAnalysis.getAssetLiabilityRatioGradeEn());
				gradeexp.setItemGradeexp(manageAssetAnalysis.getAssetLiabilityRatioExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
			//损益分析_净利率
			if (StringUtil.isNotEmpty(manageBreakEvenAnalysis.getNetInterestRateGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.净利率.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.净利率.item);
				gradeexp.setItemGradecn(manageBreakEvenAnalysis.getNetInterestRateGrade());
				gradeexp.setItemGradeen(manageBreakEvenAnalysis.getNetInterestRateGradeEn());
				gradeexp.setItemGradeexp(manageBreakEvenAnalysis.getNetInterestRateExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
			//损益分析_净资产收益率
			if (StringUtil.isNotEmpty(manageBreakEvenAnalysis.getReturnOnEquityGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.净资产收益率.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.净资产收益率.item);
				gradeexp.setItemGradecn(manageBreakEvenAnalysis.getReturnOnEquityGrade());
				gradeexp.setItemGradeen(manageBreakEvenAnalysis.getReturnOnEquityGradeEn());
				gradeexp.setItemGradeexp(manageBreakEvenAnalysis.getReturnOnEquityExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
			//偿还能力分析_速动比
			if (StringUtil.isNotEmpty(manageSolvencyAnalysis.getQuickRatioGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.速动比.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.速动比.item);
				gradeexp.setItemGradecn(manageSolvencyAnalysis.getQuickRatioGrade());
				gradeexp.setItemGradeen(manageSolvencyAnalysis.getQuickRatioGradeEn());
				gradeexp.setItemGradeexp(manageSolvencyAnalysis.getQuickRatioExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
			//周转率分析_资产周转率
			if (StringUtil.isNotEmpty(manageTurnoverRatioAnalysis.getAssetTurnoverGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.总资产周转率.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.总资产周转率.item);
				gradeexp.setItemGradecn(manageTurnoverRatioAnalysis.getAssetTurnoverGrade());
				gradeexp.setItemGradeen(manageTurnoverRatioAnalysis.getAssetTurnoverGradeEn());
				gradeexp.setItemGradeexp(manageTurnoverRatioAnalysis.getAssetTurnoverExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
			//周转率分析_应收账款周转率
			if (StringUtil.isNotEmpty(manageTurnoverRatioAnalysis.getAccountsTeceivableTurnoverGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.应收账款周转率.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.应收账款周转率.item);
				gradeexp.setItemGradecn(manageTurnoverRatioAnalysis.getAccountsTeceivableTurnoverGrade());
				gradeexp.setItemGradeen(manageTurnoverRatioAnalysis.getAccountsTeceivableTurnoverGradeEn());
				gradeexp.setItemGradeexp(manageTurnoverRatioAnalysis.getAccountsTeceivableTurnoverExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
			//周转率分析_存货周转率
			if (StringUtil.isNotEmpty(manageTurnoverRatioAnalysis.getInventoryTurnoverGrade())) {
				gradeexp = new LoanIndustryGradeexp();
				gradeexp.setLoanId(loanId);
				gradeexp.setItemName(LoanIndustryGradeEnum.存货周转率.name);
				gradeexp.setItemColumn(LoanIndustryGradeEnum.存货周转率.item);
				gradeexp.setItemGradecn(manageTurnoverRatioAnalysis.getInventoryTurnoverGrade());
				gradeexp.setItemGradeen(manageTurnoverRatioAnalysis.getInventoryTurnoverGradeEn());
				gradeexp.setItemGradeexp(manageTurnoverRatioAnalysis.getInventoryTurnoverExp());
				gradeexp.setCreateDate(new Date());
				gradeexp.setUpdateDate(DateUtil.getCurrentDate());
				industryGradeexpDao.insertIndustryGradeexp(gradeexp);
			}
		}
	}
}
