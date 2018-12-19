package banger.controller;

import banger.common.BaseController;
import banger.common.tools.StringUtil;
import banger.domain.enumerate.LoanClassEnum;
import banger.domain.loan.*;
import banger.domain.loan.finance.*;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.ILoanCrossCheckQuanyiquanProvider;
import banger.moduleIntf.ILoanFinanceAnalysisService;
import banger.moduleIntf.ILoanIncomeStatementProvider;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.IIndustryGuidelinesService;
import banger.service.intf.ILoanApplyService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangdw
 * @params:  * @param null
 * @Description:服务端财务分析控制层
 * @Date: 9:45 2017/9/4
 */

@RequestMapping("/loanAnalysis")
@Controller
public class LoanFinanceAnalysisController extends BaseController {

    private static final long serialVersionUID = 937448137575661736L;
    public static final String assetLiabilityRatio = "资产负债率";
    public static final String netInterestRate = "销售（营业）利润率";
    public static final String returnOnEquity = "净资产收益率";
    public static final String quickRatio = "速动比";
    public static final String assetTurnover = "资产周转率";
    public static final String accountsTeceivableTurnover = "应收账款周转率";
    public static final String inventoryTurnover = "存货周转率";
    @Resource
    private ILoanModule loanModule;
    @Resource
    private ILoanIncomeStatementProvider loanIncomeStatementProvider;
    @Resource
    private ILoanCrossCheckQuanyiquanProvider loanCrossCheckQuanyiquanProvider;
    @Resource
    private ILoanFinanceAnalysisService loanFinanceAnalysisService;
	@Resource
	ILoanApplyService loanApplyService;
    @Resource
    IIndustryGuidelinesService industryGuidelinesService;

    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:跳转到财务分析
     * @Date: 11:28 2017/9/1
     */
    @RequestMapping("/financeAnalysisPage")
    public ModelAndView financeAnalysisPage() {
        Integer loanId = Integer.valueOf(getParameter("loanId"));
        ModelAndView mv = new ModelAndView("/loan/vm/investigate/loanAssetsInfo");
		LoanApplyInfo applyInfo = loanApplyService.getApplyInfoById(loanId);
		Map<String, Object> map = new HashedMap();
        map.put("loanId", loanId);
        map.put("loanClassId", applyInfo.getLoanClassId());
		mv.addAllObjects(map);
        return mv;
    }

    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:通过贷款id和贷款类别获取财务分析详情
     * @Date: 10:09 2017/9/1
     */
    @RequestMapping("/getFinanceAnalysisByLoanId")
    public void getFinanceAnalysisByLoanId() {
        try {
            Integer loanId = Integer.valueOf(getParameter("loanId"));
            Integer loanClassId = Integer.valueOf(getParameter("loanClassId"));
            log.info("服务端getFinanceAnalysisByLoanId：参数loanId：" + loanId + "---loanClassId：" + loanClassId);
            if (loanId == null || loanClassId == null) {
                renderJson(false, "前端页面参数不完整", null);
            }else{
                JSONObject resultObject = new JSONObject();
                if (LoanClassEnum.LOAN_BUSINESS_CLASS.value == loanClassId) {
                    setBusinessJson(resultObject, loanId);
                } else if (LoanClassEnum.LOAN_CONSUMER_CLASS.value == loanClassId){
                    setConsumeJson(resultObject, loanId);
                }
                renderJson(true, "查询成功", resultObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("服务端getFinanceAnalysisByLoanId：", e);
            renderJson(false, "系统异常", null);
        }
    }

    /**
     * 查询经营类财务分析
     * @param resultObject
     * @param loanId
     */
    private void setBusinessJson(JSONObject resultObject, Integer loanId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("loanId", loanId);

        LoanApplyInfo applyInfo = loanApplyService.getApplyInfoById(loanId);
        //资产分析
        LoanAssetsInfo loanAssetsInfo = loanModule.getAssetsInfoProvider().queryOneAssetsInfoList(condition);
        ManageAssetAnalysis manageAssetAnalysis = loanFinanceAnalysisService.getManageAssetAnalysis(loanAssetsInfo,applyInfo);
        //损益分析
        LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(loanId);
        //LoanCrossCheckQuanyiquan loanCrossCheckQuanyiquan = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(loanId);
        ManageBreakEvenAnalysis manageBreakEvenAnalysis = loanFinanceAnalysisService.getManageBreakEvenAnalysis(loanProfitLossInfo, manageAssetAnalysis,applyInfo);
        //偿还能力分析
        ManageSolvencyAnalysis manageSolvencyAnalysis = loanFinanceAnalysisService.getManageSolvencyAnalysis(manageAssetAnalysis, loanAssetsInfo, loanProfitLossInfo,applyInfo);
        //周转率分析
        ManageTurnoverRatioAnalysis manageTurnoverRatioAnalysis = loanFinanceAnalysisService.getManageTurnoverRatioAnalysis(manageAssetAnalysis, manageBreakEvenAnalysis, loanAssetsInfo,applyInfo);

        resultObject.put("loanAssetsInfo", loanAssetsInfo);
        resultObject.put("assetAnalysis", manageAssetAnalysis);
        resultObject.put("loanProfitLossInfo", loanProfitLossInfo);
        resultObject.put("breakEvenAnalysis", manageBreakEvenAnalysis);
        resultObject.put("solvencyAnalysis", manageSolvencyAnalysis);
        resultObject.put("turnoverRatioAnalysis", manageTurnoverRatioAnalysis);
    }

    /**
     * 查询消费类财务信息
     * @param resultObject
     * @param loanId
     */
    private void setConsumeJson(JSONObject resultObject, Integer loanId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("loanId", loanId);
        //资产分析
        LoanAssetsInfo loanAssetsInfo = loanModule.getAssetsInfoProvider().queryOneAssetsInfoList(condition);
        ConsumeAssetAnalysis consumeAssetAnalysis = loanFinanceAnalysisService.getConsumeAssetAnalysis(loanAssetsInfo);
//        resultObject.put("assetAnalysis", consumeAssetAnalysis);
        //损益分析
        LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(loanId);
        ConsumeBreakEvenAnalysis consumeBreakEvenAnalysis = loanFinanceAnalysisService.getConsumeBreakEvenAnalysis(loanProfitLossInfo);
        resultObject.put("breakEvenAnalysis", consumeBreakEvenAnalysis);
        //偿还能力分析
        ConsumeSolvencyAnalysis consumeSolvencyAnalysis = loanFinanceAnalysisService.getConsumeSolvencyAnalysis(consumeAssetAnalysis);
        resultObject.put("solvencyAnalysis", consumeSolvencyAnalysis);
        resultObject.put("assetAnalysis", consumeAssetAnalysis);
    }
}
