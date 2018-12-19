package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.loan.LoanCrossCheckGrossProfit;
import banger.domain.loan.LoanCrossCheckIncome;
import banger.domain.loan.LoanCrossCheckNetProfit;
import banger.framework.util.ApiJsonUtil;
import banger.moduleIntf.ILoanAnalysislBusinessAndConsumProvider;
import banger.moduleIntf.ILoanCrossCheckGrossProfitProvider;
import banger.moduleIntf.ILoanCrossCheckIncomeProvider;
import banger.moduleIntf.ILoanCrossCheckNetProfitProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 黄福州
 *
 */

@Controller
@RequestMapping("/api")
public class AppLoanCrossCheckController extends BaseController {

    private static final long serialVersionUID = 937448137575661736L;

    @Resource
    private ILoanCrossCheckNetProfitProvider loanCroCheNetProPro;

    @Resource
    private ILoanCrossCheckIncomeProvider loanCroCheIncPro;

    @Resource
    private ILoanCrossCheckGrossProfitProvider loanCroCheGroProPro;

    @Autowired
    private ILoanAnalysislBusinessAndConsumProvider analysislBusinessAndConsumProvider;
    /**
     * app端通过贷款id和贷款类别获取净利检验详情接口
     *
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getCrossCheckNetProfitByLoanId", method = RequestMethod.POST)
    public ResponseEntity<String> getCrossCheckNetProfitByLoanId(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);

            if (StringUtils.isBlank(reqJson)) {
                log.error("查询净利检验详情接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = null;
            try {
                reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA);
                if (containsKeys != null)
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
            } catch (Exception e) {
                log.error("查询净利检验详情接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
            Integer loanId = reqObj.containsKey("loanId") ? reqObj.getInt("loanId") : null;
            LoanCrossCheckNetProfit loanCrossCheckNetProfit = loanCroCheNetProPro.getCrossCheckNetProfitByLoanId(loanId);
            JSONObject resObj = new JSONObject();
            if (loanCrossCheckNetProfit != null) {
                resObj = ApiJsonUtil.toJson(loanCrossCheckNetProfit, AppParamsConst.PARAMS_RESPONSE_GET_CROSSCHECKNETPROFIT_BYLOANID);
            }
            return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
        } catch (Exception e) {
            log.error("查询净利检验详情接口异常" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }


    /**
     * app端选择保存净利检验信息接口
     *
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveCrossCheckNetProfit", method = RequestMethod.POST)
    public ResponseEntity<String> saveCrossCheckNetProfit(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            if (StringUtils.isBlank(reqJson)) {
                log.error("保存净利检验信息接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = null;
            LoanCrossCheckNetProfit lccnp = null;
            Integer userId=null;
            try {
                reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA_PRIMARY);
                if (containsKeys == null) {
                    lccnp = (LoanCrossCheckNetProfit)JSONObject.toBean(reqObj,LoanCrossCheckNetProfit.class);
                    userId=reqObj.containsKey("userId")?reqObj.getInt("userId"):0;
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
                }
            } catch (Exception e){
                log.error("保存净利检验信息接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
            loanCroCheNetProPro.saveCrossCheckNetProfit(lccnp,userId);
            Integer loanId=lccnp.getLoanId();
            if(null!=loanId) {
                loanCroCheNetProPro.updateNetProDev(loanId);
                //根据贷款id处理三表数据,另存财务分析详情明细
                analysislBusinessAndConsumProvider.saveAnalysislBusinessOrConsum(loanId);
                return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
            }
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,  "loanId参数不能为空！"), HttpStatus.OK);

        } catch (Exception e) {
            log.error("保存净利检验信息接口" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * app端查看收入检验信息详情接口
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getCrossCheckIncomeByLoanId", method = RequestMethod.POST)
    public ResponseEntity<String> getCrossCheckIncomeByLoanId(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            if (StringUtils.isBlank(reqJson)) {
                log.error("查询收入检验详情接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = null;
            try {
                reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA);
                if (containsKeys != null)
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
            } catch (Exception e) {
                log.error("查询收入检验详情接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
            Integer loanId = reqObj.getInt("loanId");
            LoanCrossCheckIncome loaCroCheInc = loanCroCheIncPro.getCrossCheckIncomeByLoanId(loanId);
            JSONObject resObj = new JSONObject();
            if (loaCroCheInc != null) {
                resObj = ApiJsonUtil.toJson(loaCroCheInc, AppParamsConst.PARAMS_RESPONSE_GET_CROSSCHECK_INCOME_BYLOANID);
            }
            return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
        } catch (Exception e) {
            log.error("查询收入检验详情接口异常" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * app端选择保存收入检验信息接口
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveLoanCrossCheckIncome", method = RequestMethod.POST)
    public ResponseEntity<String> saveLoanCrossCheckIncome(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            if (StringUtils.isBlank(reqJson)) {
                log.error("保存收入检验信息接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = null;
            LoanCrossCheckIncome lcci = null;
            Integer userId=null;
            try {
                reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA_PRIMARY);
                if (containsKeys == null) {
                    lcci = (LoanCrossCheckIncome)JSONObject.toBean(reqObj,LoanCrossCheckIncome.class);
                    userId=reqObj.containsKey("userId")?reqObj.getInt("userId"):0;
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
                }
            } catch (Exception e) {
                log.error("保存收入检验信息接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
            loanCroCheIncPro.saveLoanCrossCheckIncome(lcci,userId);
            //根据贷款id处理三表数据,另存财务分析详情明细
            analysislBusinessAndConsumProvider.saveAnalysislBusinessOrConsum(lcci.getLoanId());
            return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);

        } catch (Exception e) {
            log.error("保存收入检验信息接口" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * app端查看毛利检验信息详情接口
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanCroCheGrossProByLoanId", method = RequestMethod.POST)
    public ResponseEntity<String> getLoanCroCheGrossProByLoanId(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            if (StringUtils.isBlank(reqJson)) {
                log.error("查询毛利检验详情接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = null;
            try {
                reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA);
                if (containsKeys != null)
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
            } catch (Exception e) {
                log.error("查询毛利检验详情接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
            Integer loanId = reqObj.getInt("loanId");
            LoanCrossCheckGrossProfit locrCheGroPro = loanCroCheGroProPro.getCrossCheckGrossProfitByLoanId(loanId);
            JSONObject resObj = new JSONObject();
            if (locrCheGroPro != null) {
                resObj = ApiJsonUtil.toJson(locrCheGroPro, AppParamsConst.PARAMS_RESPONSE_GET_CROSSCHECK_GROPRO_BYLOANID);
            }
            return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
        } catch (Exception e) {
            log.error("查询毛利检验详情接口异常" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * app端选择保存毛利检验信息接口
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveLoanCroCheGrossPro", method = RequestMethod.POST)
    public ResponseEntity<String> saveLoanCroCheGrossPro(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            if (StringUtils.isBlank(reqJson)) {
                log.error("保存毛利检验信息接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = null;
            LoanCrossCheckGrossProfit lccgp = null;
            Integer userId=null;
            try {
                reqObj = JSONObject.fromObject(reqJson);
                String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA_PRIMARY);
                if (containsKeys == null) {
                    lccgp = (LoanCrossCheckGrossProfit)JSONObject.toBean(reqObj,LoanCrossCheckGrossProfit.class);
                    userId=reqObj.containsKey("userId")?reqObj.getInt("userId"):0;
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102, containsKeys + "参数不能为空！"), HttpStatus.OK);
                }
            } catch (Exception e) {
                log.error("保存毛利检验信息接口，json解析出错|" + reqJson, e);
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
            }
            loanCroCheGroProPro.saveLoanCrossCheckGrossProfit(lccgp,userId);
            //根据贷款id处理三表数据,另存财务分析详情明细
            analysislBusinessAndConsumProvider.saveAnalysislBusinessOrConsum(lccgp.getLoanId());
            return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("保存毛利检验信息接口" + reqJson, e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }



}
