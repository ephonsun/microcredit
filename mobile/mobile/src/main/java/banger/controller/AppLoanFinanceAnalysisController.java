package banger.controller;

import banger.action.AppBaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.enumerate.LoanClassEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanAssetsInfo;
import banger.domain.loan.LoanProfitLossInfo;
import banger.domain.loan.finance.*;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 财务分析
 * Created by Administrator on 2017/8/24.
 */
@RequestMapping("/api")
@Controller
public class AppLoanFinanceAnalysisController  extends AppBaseController {

    private static final long serialVersionUID = 937448137575661736L;

    @Resource
    private ILoanModule loanModule;
    @Resource
    private ILoanIncomeStatementProvider loanIncomeStatementProvider;
    @Resource
    private ILoanCrossCheckQuanyiquanProvider loanCrossCheckQuanyiquanProvider;

    //资产负债字段
    private final static String loanAssets = "assetsCashAmount,assetsStockAmount,assetsReceivableAmount,assetsPaymentAmount," +
            "assetsOperatingAmount,assetsNonOperatingAmount,assetsFixedAmount,assetsOtherAmount,offAssetsAmount," +
            "offDebtsAmount,debtsPayableAmount,debtsReceivedAmount,debtsShortAmount,debtsLongAmount,debtsOtherAmount";
    //损益分析字段
    private final static String loanProfitLoss = "businessIncomeAmount,otherIncomeAmount,fixedCostDefrayAmount,texDefrayAmount,otherDefrayAmount";
    /**
     * app端通过贷款id和贷款类别获取财务分析详情接口
     *
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getFinanceAnalysisByLoanId", method = RequestMethod.POST)
    public ResponseEntity<String> getFinanceAnalysisByLoanId(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            ResponseEntity<String> responseEntity = checkAppParams("获取财务分析详情接口", reqJson, AppParamsConst.PARAMS_REQUEST_LOAN_FINANCE_ANALYS_QUERY);
            if (responseEntity != null)
                return responseEntity;
            JSONObject reqObj = JSONObject.fromObject(reqJson);
            Integer loanId = reqObj.getInt("loanId");
            Integer loanClassId = reqObj.getInt("loanClassId");

            JSONObject resultObject = new JSONObject();
            if (LoanClassEnum.LOAN_BUSINESS_CLASS.value == loanClassId) {
                setBusinessJson(resultObject, loanId);
            } else if (LoanClassEnum.LOAN_CONSUMER_CLASS.value == loanClassId){
                setConsumeJson(resultObject, loanId);
            }
            return new ResponseEntity<String>(AppJsonResponse.success(resultObject), HttpStatus.OK);
        } catch (Exception e) {
            log.error("查询财务分析详情接口异常" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }


    /**
     * 查询经营类细腻系
     * @param resultObject
     * @param loanId
     */
    private void setBusinessJson(JSONObject resultObject, Integer loanId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("loanId", loanId);
        LoanApplyInfo applyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
        //资产分析
        LoanAssetsInfo loanAssetsInfo = loanModule.getAssetsInfoProvider().queryOneAssetsInfoList(condition);
        ManageAssetAnalysis manageAssetAnalysis = loanModule.getLoanFinanceAnalysisService().getManageAssetAnalysis(loanAssetsInfo, applyInfo);
        resultObject.put("manageAssetAnalysis", manageAssetAnalysis);
        //损益分析
        LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(loanId);
//        LoanCrossCheckQuanyiquan loanCrossCheckQuanyiquan = loanCrossCheckQuanyiquanProvider.getCrossCheckQuanyiquanByLoanId(loanId);
        ManageBreakEvenAnalysis manageBreakEvenAnalysis = loanModule.getLoanFinanceAnalysisService().getManageBreakEvenAnalysis(loanProfitLossInfo, manageAssetAnalysis, applyInfo);
        resultObject.put("manageBreakEvenAnalysis", manageBreakEvenAnalysis);
        //偿还能力分析
        ManageSolvencyAnalysis manageSolvencyAnalysis = loanModule.getLoanFinanceAnalysisService().getManageSolvencyAnalysis(manageAssetAnalysis, loanAssetsInfo, loanProfitLossInfo, applyInfo);
        resultObject.put("manageSolvencyAnalysis", manageSolvencyAnalysis);
        //周转率分析
        ManageTurnoverRatioAnalysis manageTurnoverRatioAnalysis = loanModule.getLoanFinanceAnalysisService().getManageTurnoverRatioAnalysis(manageAssetAnalysis, manageBreakEvenAnalysis, loanAssetsInfo, applyInfo);
        resultObject.put("manageTurnoverRatioAnalysis", manageTurnoverRatioAnalysis);

        loanAssetsInfo.setDebtsOtherAmount(loanAssetsInfo.getDebtsBizOtherAmount());
        resultObject.put("loanAssetsInfo", AppJsonUtil.toJson(loanAssetsInfo,loanAssets));
        resultObject.put("loanProfitLossInfo", AppJsonUtil.toJson(loanProfitLossInfo,loanProfitLoss));
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
        ConsumeAssetAnalysis consumeAssetAnalysis = loanModule.getLoanFinanceAnalysisService().getConsumeAssetAnalysis(loanAssetsInfo);
        resultObject.put("consumeAssetAnalysis", consumeAssetAnalysis);
        //损益分析
        LoanProfitLossInfo loanProfitLossInfo = loanIncomeStatementProvider.getLoanProfitLossInfoByLoanId(loanId);
        ConsumeBreakEvenAnalysis consumeBreakEvenAnalysis = loanModule.getLoanFinanceAnalysisService().getConsumeBreakEvenAnalysis(loanProfitLossInfo);
        resultObject.put("consumeBreakEvenAnalysis", consumeBreakEvenAnalysis);
        //偿还能力分析
        ConsumeSolvencyAnalysis consumeSolvencyAnalysis = loanModule.getLoanFinanceAnalysisService().getConsumeSolvencyAnalysis(consumeAssetAnalysis);
        resultObject.put("consumeSolvencyAnalysis", consumeSolvencyAnalysis);
    }
}
