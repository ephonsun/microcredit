package banger.controller;

import banger.common.BaseController;
import banger.common.constant.DateFormatConst;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.enumerate.LoanMonitorType;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.LoanRepayPlanEnum;
import banger.domain.loan.*;
import banger.domain.system.SysBasicConfig;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.DateUtil;
import banger.framework.util.FormatUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 贷后管理，监控和催收
 * Created by zhusw on 2017/6/16.
 */
@RequestMapping("/api")
@Controller
public class AppLoanAfterController extends BaseController {
    public static final String SAVE_LOAN_COLLECTION_PARAMS = "id,loanRepayAmount,loanRepayAccrualAmount,loanRepayDate";
    public static final String SAVE_LOAN_MONITOR_PARAMS = "id,loanId,loanMonitorState,loanMonitorUserId";
    //public static final String SAVE_REPAY_PLAN_INFO = "id,loanId,loanPrincipalAmount,userId";
    //public static final String SAVE_LOAN_MONITOR_PARAMS = "id,loanId,loanMonitorState,loanResultContent,loanRevisitType,loanMonitorUserId";
    public static final String RESULT_LOAN_MONITOR_LIST_PARAMS = "id,loanMonitorTypeName,userName,loanRevisitTypeName,loanCompleteDate";
    private SimpleDateFormat sdf = new SimpleDateFormat(DateFormatConst.DEFAULT_DATE_FORMAT);

    private String loanCollectionState;					//催收状态

    @Resource
    private ILoanModule loanModule;
    @Resource
    private ILoanAfterProvider loanAfterProvider;

    @Resource
    private ILoanApplyProvider loanApplyProvider;

    @Resource
    private IRepayPlanInfoProvider iRepayPlanInfoProvider;

    @Resource
    private IBasicConfigProvider basicConfigProvider;




    /**
     * http://localhost:8080/loan/api/v1/getLoanCollectionInfo.html?loanId=12
     * 得到贷款催收信息数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanCollectionInfo")
    public ResponseEntity<String> getLoanCollectionInfo(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        try {
            if(StringUtil.isNotEmpty(loanId)) {
                LoanApplyInfo_Query applyInfo = loanApplyProvider.getApplyInfoQueryById(Integer.parseInt(loanId));
                JSONObject resObj ;
                if(applyInfo!=null) {
                    applyInfo.setLoanRepayDate(getNextDay(21));
                    resObj = AppJsonUtil.toJson(applyInfo, AppParamsConst.PARAMS_RESPONSE_GET_PRODUCT_DETAIL);
                    resObj.put("tableId",1);
                    return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        }catch(Exception e){
            log.error("得到贷款还款信息数据异常 error:"+e.getMessage(),e);
        }

        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    private Date getNextDay(int day){
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int currDay = ca.get(Calendar.DAY_OF_MONTH);
        if(currDay>day){
            ca.add(Calendar.MONTH,1);
        }
        ca.set(Calendar.DAY_OF_MONTH,day);
        return ca.getTime();
    }

    /**
     * 保存贷款催收信息数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveLoanCollectionInfo")
    public ResponseEntity<String> saveLoanCollectionInfo(HttpServletRequest request, HttpServletResponse response){
        LoanRepayPlanInfo repayPlanInfo = new LoanRepayPlanInfo();
        String reqJson = null;
        try{
            reqJson = HttpParseUtil.getJsonStr(request);
            JSONObject reqObj = JSONObject.fromObject(reqJson);
            String containsKeys = AppJsonUtil.containsKeys(reqObj,SAVE_LOAN_COLLECTION_PARAMS);
            Integer userId = reqObj.getInt("userId");
            if(containsKeys == null){
                //贷款阶段
                String loanAfterState = "";
                if(reqObj.containsKey("loanAfterState")){
                    loanAfterState = reqObj.getString("loanAfterState");
                }
                Integer loanId = reqObj.getInt("loanId");
                Integer id = reqObj.getInt("id");
                repayPlanInfo.setId(id);
                //已还本金
                BigDecimal loanRepayAmount = new BigDecimal(reqObj.getDouble("loanRepayAmount"));
                repayPlanInfo.setLoanRepayAmount(loanRepayAmount);
                //贷款余额 = 主表贷款余额 - 本期已还金额（同时更新主表和还款计划表） 修改：2017年12月18日
                LoanApplyInfo loanApplyInfo = iRepayPlanInfoProvider.getApplyInfoById(loanId);
                BigDecimal balanceAmount = loanApplyInfo.getLoanBalanceAmount();
                if(balanceAmount != null){
                    repayPlanInfo.setLoanAccrualBalanceAmount(balanceAmount.subtract(loanRepayAmount));
                }
                //已还利息
                repayPlanInfo.setLoanRepayAccrualAmount(new BigDecimal(reqObj.getDouble("loanRepayAccrualAmount")));
                //还款日期
                String loanRepayDate = reqObj.getString("loanRepayDate");
                Date date = DateUtil.parser(loanRepayDate,"yyyy-MM-dd");
                repayPlanInfo.setLoanRepayDate(date);
                iRepayPlanInfoProvider.updateRepayPlanInfoById(repayPlanInfo,userId);
                loanAfterProvider.updateLoanAfterState(loanId, loanAfterState);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
            }
        }catch (Exception e){
            log.error("保存贷款申请信息接口，json解析出错|"+reqJson,e);
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
        }
        return new ResponseEntity<String>(AppJsonResponse.success(),HttpStatus.OK);
    }


    private String getLoanRepayPlanJsonString(LoanApplyInfo_Query applyInfo,LoanRepayPlanInfo repayPlanInfo){
        JSONObject root = new JSONObject();

        JSONObject data = new JSONObject();
        data.put("tableId",1);
        data.put("loanId",applyInfo.getLoanId());
        data.put("loanName", applyInfo.getLoanName());
        data.put("loanTypeId", applyInfo.getLoanTypeId());
        data.put("loanProcessType",applyInfo.getLoanProcessType());
        data.put("loanTypeName", applyInfo.getLoanTypeName());
        data.put("loanProcessTypeName",applyInfo.getLoanProcessStateDisplay());
        data.put("loanIdentifyTypeName", applyInfo.getLoanIdentifyTypeName());
        data.put("loanIdentifyNum", applyInfo.getLoanIdentifyNum());
        data.put("loanIdentifyNumX", applyInfo.getLoanIdentifyNumX());
        data.put("loanTelNum", applyInfo.getLoanTelNum());
        data.put("loanTelNumX", applyInfo.getLoanTelNumX());
        data.put("loanApplyAmount", applyInfo.getLoanAmountFormat());
        data.put("loanResultAmount", applyInfo.getLoanResultAmount());
        data.put("loanResultAmountFormat",FormatUtil.formatDecimal(applyInfo.getLoanBalanceAmount()));
        data.put("loanAfterState",applyInfo.getLoanAfterState());
        data.put("loanAfterStateName",applyInfo.getLoanAfterStateName());

        if(repayPlanInfo!=null){
            SysBasicConfig hkfs = basicConfigProvider.getSysBasicConfigByKey("hkfs");
            //1:手动还款,2自动还款
            String isAutoRepay = hkfs.getConfigStatus();
            data.put("isAutoRepay",Integer.parseInt(isAutoRepay));
            data.put("id",repayPlanInfo.getId());
            data.put("loanPrincipalAmount",repayPlanInfo.getLoanPrincipalAmount());  //还款本金
            data.put("loanRepayAmount",repayPlanInfo.getLoanRepayAmount());  //实际还款金额(本金）
            data.put("loanAccrualAmount",repayPlanInfo.getLoanAccrualAmount());//还款利息
            data.put("loanRepayAccrualAmount",repayPlanInfo.getLoanRepayAccrualAmount()); //实际还款利息
            data.put("loanRepayPlanDate",sdf.format(repayPlanInfo.getLoanRepayPlanDate()));  //计划还款日期
            if(repayPlanInfo.getLoanRepayDate() != null){
                data.put("loanRepayDate",sdf.format(repayPlanInfo.getLoanRepayDate()));  //还款日期
            }else{
                data.put("loanRepayDate","");  //还款日期
            }
        }

        root.put("code", CodeEnum.CODE_100.getCode());
        root.put("msg", CodeEnum.CODE_100.getMsg());
        root.put("data",data);
        return root.toString();
    }


    /**
     * http://localhost:8080/loan/api/v1/getLoanMonitorInfo.html?loanId=12
     * 得到贷款催收信息数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanMonitorInfo")
    public ResponseEntity<String> getLoanMonitorInfo(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        try {
            if(StringUtil.isNotEmpty(loanId)) {
                LoanApplyInfo_Query applyInfo = loanApplyProvider.getApplyInfoQueryById(Integer.parseInt(loanId));
                if(applyInfo!=null) {
                    LoanMonitorInfo monitorInfo = loanAfterProvider.getTopLoanMonitorInfo(Integer.parseInt(loanId));
                    String result = getLoanMonitorJsonString(applyInfo,monitorInfo);
                    return new ResponseEntity<String>(result, HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        }catch(Exception e){
            log.error("得到贷款还款信息数据异常 error:"+e.getMessage(),e);
        }

        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 保存贷款催收信息数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveLoanMonitorInfo")
    public ResponseEntity<String> saveLoanMonitorInfo(HttpServletRequest request, HttpServletResponse response){
        LoanMonitorInfo monitorInfo = null;
        String reqJson = null;

        try {
            reqJson = HttpParseUtil.getJsonStr(request);
            JSONObject reqObj = JSONObject.fromObject(reqJson);
            String containsKeys = AppJsonUtil.containsKeys(reqObj, SAVE_LOAN_MONITOR_PARAMS);
            if(containsKeys == null){
                monitorInfo = (LoanMonitorInfo) JSONObject.toBean(reqObj,LoanMonitorInfo.class);
                String loanAfterState = "";
                if(reqObj.containsKey("loanAfterState"))
                    loanAfterState = reqObj.getString("loanAfterState");
                Integer loanId = monitorInfo.getLoanId();
                if(monitorInfo.getId()!=null && monitorInfo.getId().intValue()>0) {
                    loanAfterProvider.updateLoanMonitorInfo(monitorInfo);
                    loanAfterProvider.updateLoanAfterState(loanId, loanAfterState);
                }else{
                    LoanMonitorInfo topMonitorInfo = loanAfterProvider.getTopLoanMonitorInfo(loanId);
                    if(topMonitorInfo!=null) {
                        monitorInfo.setId(topMonitorInfo.getId());
                        loanAfterProvider.updateLoanMonitorInfo(monitorInfo);
                        loanAfterProvider.updateLoanAfterState(loanId, loanAfterState);
                    }else{
                        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_124), HttpStatus.OK);
                    }
                }
                return new ResponseEntity<String>(AppJsonResponse.success(),HttpStatus.OK);
            }else {
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("保存贷款申请信息接口，json解析出错|"+reqJson,e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    private String getLoanMonitorJsonString(LoanApplyInfo_Query applyInfo,LoanMonitorInfo monitorInfo){
        JSONObject root = new JSONObject();

        JSONObject data = new JSONObject();
        data.put("tableId",1);
        data.put("loanId",applyInfo.getLoanId());
        data.put("loanName", applyInfo.getLoanName());
        data.put("loanTypeId", applyInfo.getLoanTypeId());
        data.put("loanProcessType",applyInfo.getLoanProcessType());
        data.put("loanTypeName", applyInfo.getLoanTypeName());
        data.put("loanProcessTypeName",applyInfo.getLoanProcessStateDisplay());
        data.put("loanIdentifyTypeName", applyInfo.getLoanIdentifyTypeName());
        data.put("loanIdentifyNum", applyInfo.getLoanIdentifyNum());
        data.put("loanIdentifyNumX", applyInfo.getLoanIdentifyNumX());
        data.put("loanTelNum", applyInfo.getLoanTelNum());
        data.put("loanTelNumX", applyInfo.getLoanTelNumX());
        data.put("loanApplyAmount", applyInfo.getLoanAmountFormat());
        data.put("loanResultAmount", applyInfo.getLoanResultAmount());
        data.put("loanResultAmountFormat",FormatUtil.formatDecimal(applyInfo.getLoanResultAmount()));
        data.put("loanAfterState",applyInfo.getLoanAfterState());
        data.put("loanAfterStateName",applyInfo.getLoanAfterStateName());

        if(monitorInfo!=null){
            data.put("id",monitorInfo.getId());
            data.put("loanResultContent",monitorInfo.getLoanResultContent());
            data.put("loanRevisitType",monitorInfo.getLoanRevisitType());
            if(StringUtil.isNotEmpty(monitorInfo.getLoanRevisitType())) {
                String monitorRevisiTypeName = CodeTableUtil.getCodeTableValue("cdDictColByName", "CD_MONITOR_REVISIT_TYPE",monitorInfo.getLoanRevisitType());
                data.put("monitorRevisiTypeName",monitorRevisiTypeName);
            }else{
                data.put("monitorRevisiTypeName","");
            }
            LoanMonitorType type = LoanMonitorType.valueOfType(monitorInfo.getLoanMonitorType());
            data.put("loanMonitorType",monitorInfo.getLoanMonitorType());
            data.put("loanMonitorTypeName",type.typeName);
            data.put("loanMonitorState",monitorInfo.getLoanMonitorState());
            data.put("loanMonitorDate",FormatUtil.formatDate(monitorInfo.getLoanMonitorDate(),DateFormatConst.DEFAULT_DATE_FORMAT));
        }else{
            data.put("loanMonitorType",applyInfo.getLoanMonitorType());
            data.put("loanMonitorTypeName",applyInfo.getLoanMonitorTypeName());
            data.put("loanMonitorState",applyInfo.getLoanMonitorState());
            data.put("loanMonitorDate",FormatUtil.formatDate(applyInfo.getLoanMonitorDate(),DateFormatConst.DEFAULT_DATE_FORMAT));
        }

        root.put("code", CodeEnum.CODE_100.getCode());
        root.put("msg", CodeEnum.CODE_100.getMsg());
        root.put("data",data);
        return root.toString();
    }


    /**
     * 得到贷款还款计划信息数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanCollectionInfoList")
    public ResponseEntity<String> getLoanCollectionInfoList(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        try {
            if(StringUtil.isNotEmpty(loanId)) {
                LoanApplyInfo_Query applyInfo = loanApplyProvider.getApplyInfoQueryById(Integer.parseInt(loanId));
                if(applyInfo!=null) {
                    List<LoanRepayPlanInfo> repayPlanInfoList = loanAfterProvider.getLoanRepayPlanInfoListByLoanId(Integer.parseInt(loanId));
                    String result = getLoanCollectionInfoJsonString(repayPlanInfoList);
                    return new ResponseEntity<String>(result, HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        }catch(Exception e){
            log.error("得到贷款还款信息数据异常 error:"+e.getMessage(),e);
        }

        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 得到贷款监控信息数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanMonitorInfoList")
    public ResponseEntity<String> getLoanMonitorInfoList(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        try {
            if(StringUtil.isNotEmpty(loanId)) {
                List<LoanMonitorInfo> monitorInfoList = loanAfterProvider.getLoanMonitorInfoListByLoanId(Integer.parseInt(loanId));
                JSONArray resArray = AppJsonUtil.toJsonArray(monitorInfoList, RESULT_LOAN_MONITOR_LIST_PARAMS);
                return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
            }
        }catch(Exception e){
            log.error("得到贷款监控信息数据异常 error:"+e.getMessage(),e);
        }

        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 得到还款计划列表
     * @param repayPlanInfoList
     * @return
     */
    private String getLoanCollectionInfoJsonString(List<LoanRepayPlanInfo> repayPlanInfoList) {
        JSONObject root = new JSONObject();
        JSONArray ja = new JSONArray();
        int num=1;
        for(LoanRepayPlanInfo repayPlanInfo :repayPlanInfoList){
            JSONObject jo = new JSONObject();
            jo.put("num",num);
            jo.put("loanRepayPlanDate",FormatUtil.formatDate(repayPlanInfo.getLoanRepayPlanDate(),DateFormatConst.DEFAULT_DATE_FORMAT));
            jo.put("loanPrincipalAmount",repayPlanInfo.getLoanPrincipalAmount());
            jo.put("loanAccrualAmount",repayPlanInfo.getLoanAccrualAmount());
            ja.add(jo);
            num++;
        }
        root.put("data",ja);
        root.put("code", CodeEnum.CODE_100.getCode());
        root.put("msg", CodeEnum.CODE_100.getMsg());
        return root.toString();
    }

    /**
     * 【调查结论/审批决议/放款】还款计划
     * @param request
     * @param response
     * @return
     * http://localhost:8080/api/v1/getLoanCollectionInfo.html?loanId=46
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanRepayPlanInfoList")
    public ResponseEntity<String> getLoanRepayPlanInfoList(HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JSONObject();   //返回给app的json
        String msg = "";
        String reqJson = null;
        try{
            reqJson = HttpParseUtil.getJsonStr(request);
            if(StringUtils.isEmpty(reqJson)){
                log.error("还款计划接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }

            JSONObject reqJo = JSONObject.fromObject(reqJson);
            if(!reqJo.containsKey("loanId")){
                log.error("还款计划接口，参数错误，缺少loanId");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR,"还款计划接口，缺少loanId参数"), HttpStatus.OK);
            }
            if(!reqJo.containsKey("module")){
                log.error("还款计划接口，参数错误，缺少module");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR,"还款计划接口，缺少module参数"), HttpStatus.OK);
            }
            Integer lid = reqJo.getInt("loanId");
            String module = reqJo.getString("module");
            //灵活还款,打开按钮
            JSONArray jsonArray = reqJo.getJSONArray("data");
            reqJo = jsonArray.getJSONObject(0);
            if (LoanProcessTypeEnum.INVESTIGATE.type.equals(module)) {
                msg = checkJson(reqJo);
            }else if(LoanProcessTypeEnum.LOAN.type.equals(module)){
                msg = checkJsonLoan(reqJo);
            }
            String loanId = lid.toString();
            String loanProcessType = iRepayPlanInfoProvider.getApplyInfoById(lid).getLoanProcessType();
            if(reqJo.getBoolean("isClear")){
                iRepayPlanInfoProvider.deleteRepayPlanInfoByLoanId(lid, loanProcessType);
            }
            if(StringUtils.isBlank(msg)){
                String repaymentMode =  reqJo.containsKey("repaymentMode") ? reqJo.getString("repaymentMode") : "";
                if(repaymentMode == null || "".equals(repaymentMode)) repaymentMode =  reqJo.containsKey("loanBackMode") ? reqJo.getString("loanBackMode") : "";
                if(repaymentMode != null && !"".equals(repaymentMode)){
                    Integer repay = Integer.parseInt(repaymentMode);
                    if(repay >= 4){
                        repaymentMode =  LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
                    }
                }
//                LoanProcessTypeEnum.INVESTIGATE.type
//&&!"Approval".equals(loanProcessType)
                if((LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.equalValue(repaymentMode) && module.equals(loanProcessType)) || "Loan".equals(loanProcessType)){
                    json.put("isShowSaveBtn",true);
                    json.put("isShowRegenerate",true);
                }else{
                    json.put("isShowSaveBtn",false);
                    json.put("isShowRegenerate",false);
                }
                //还款方式为等额本金时，还款计划中的还款利息和本期还款一列不用需要
                if(!LoanRepayPlanEnum.EQUAL_PRINCIPAL.equalValue(repaymentMode) || "Loan".equals(loanProcessType)){
                    json.put("isShowAccrualAmount",true); //还款利息
                    json.put("isShowRepayment",true);      //本期还款
                } else{
                    json.put("isShowAccrualAmount",false); //还款利息
                    json.put("isShowRepayment",false);      //本期还款
                }

                Integer userId = reqJo.getInt("userId");
                String proposalAmount, proposalLimit, proposalRatio,loanRatioDate,loanEndDate="",loanBackDate;
                if (LoanProcessTypeEnum.APPROVAL.type.equals(module)) {
                    proposalAmount =  reqJo.containsKey("resultAmount") ? reqJo.getString("resultAmount") : "";
                    proposalLimit =   reqJo.containsKey("resultLimit") ? reqJo.getString("resultLimit") : "";
                    proposalRatio =  reqJo.containsKey("resultRatio") ? reqJo.getString("resultRatio") : "";
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    loanRatioDate = formatter.format(new Date());
                    loanBackDate =  reqJo.containsKey("repaymentDate") ? reqJo.getString("repaymentDate") : "";
                    DataTable applyInfoData = loanModule.getLoanApprovalProvider().getApprovalDataTableByLoanId(Integer.valueOf(loanId));
                    DataRow row = applyInfoData.getRow(0);
                    if (StringUtils.isBlank(repaymentMode))
                        repaymentMode = row.get("REPAYMENT_MODE").toString();
                    if (StringUtils.isBlank(proposalAmount))
                        proposalAmount =  row.get("RESULT_AMOUNT").toString();
                    if (StringUtils.isBlank(proposalLimit))
                        proposalLimit =  row.get("RESULT_LIMIT").toString();
                    if (StringUtils.isBlank(proposalRatio))
                        proposalRatio = row.get("RESULT_RATIO").toString();
                    if (StringUtils.isBlank(loanBackDate))
                        loanBackDate = row.get("REPAYMENT_DATE").toString();
                }else if(LoanProcessTypeEnum.LOAN.type.equals(module)){
                    proposalAmount =  reqJo.containsKey("loanAmount") ? reqJo.getString("loanAmount") : "";
                    proposalLimit =   reqJo.containsKey("loanLimit") ? reqJo.getString("loanLimit") : "";
                    proposalRatio =  reqJo.containsKey("loanRatio") ? reqJo.getString("loanRatio") : "";
                    loanRatioDate =  reqJo.containsKey("loanRatioDate") ? reqJo.getString("loanRatioDate") : "";
                    loanEndDate =  reqJo.containsKey("loanEndDate") ? reqJo.getString("loanEndDate") : "";
                    loanBackDate =  reqJo.containsKey("loanBackDate") ? reqJo.getString("loanBackDate") : "";
                    DataTable applyInfoData = loanModule.getLoanApprovalProvider().getApprovalDataTableByLoanId(Integer.valueOf(loanId));
                    DataRow row = applyInfoData.getRow(0);
                    if (StringUtils.isBlank(repaymentMode))
                        repaymentMode = row.get("LOAN_BACK_MODE").toString();
                    if (StringUtils.isBlank(proposalAmount))
                        proposalAmount =  row.get("loan_AMOUNT").toString();
                    if (StringUtils.isBlank(proposalLimit))
                        proposalLimit =  row.get("loan_LIMIT").toString();
                    if (StringUtils.isBlank(proposalRatio))
                        proposalRatio = row.get("loan_RATIO").toString();
                    if (StringUtils.isBlank(loanRatioDate))
                        loanRatioDate =  row.get("LOAN_RATIO_DATE").toString();
                    if (StringUtils.isBlank(loanEndDate))
                        loanEndDate =  row.get("LOAN_END_DATE").toString();
                    if (StringUtils.isBlank(loanBackDate))
                        loanBackDate = row.get("LOAN_BACK_DATE").toString();
                }else{
                    proposalAmount =  reqJo.containsKey("proposalAmount") ? reqJo.getString("proposalAmount") : "";
                    proposalLimit =   reqJo.containsKey("proposalLimit") ? reqJo.getString("proposalLimit") : "";
                    proposalRatio =  reqJo.containsKey("proposalRatio") ? reqJo.getString("proposalRatio") : "";
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    loanRatioDate = formatter.format(new Date());
                    loanBackDate =  reqJo.containsKey("repaymentDate") ? reqJo.getString("repaymentDate") : "";
                }

                List<LoanRepayPlanInfoExtend> repayPlanInfoList =null;
                if(StringUtil.isNotEmpty(loanId)) {
                    repayPlanInfoList = iRepayPlanInfoProvider.getRepayPlanInfoListForLoan_ZS(loanId,repaymentMode,proposalAmount,proposalLimit,proposalRatio,loanRatioDate,loanEndDate,Integer.parseInt(loanBackDate), userId,module,false);
                    String result =getLoanRepayPlanJson(repayPlanInfoList,json,repaymentMode);
                    return new ResponseEntity<String>(result, HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
                }
            }
        }catch (Exception e){
            log.error("得到还款计划信息数据异常 error:"+e.getMessage(),e);
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
        }
        return new ResponseEntity<String>(msg, HttpStatus.OK);
    }

    /**
     * 校验json必填项，并返回未填项的信息
     * 成功返回null
     * @param reqJo
     * @return
     */
    private String checkJson(JSONObject reqJo){
        JSONObject jo = new JSONObject();
        String repaymentMode =  reqJo.containsKey("repaymentMode") ? reqJo.getString("repaymentMode") : "";
        if ("".equals(repaymentMode) || repaymentMode == null){
            jo.put("code","101");
            jo.put("msg","还款方式，不能为空");
            return jo.toString();
        }
        String proposalAmount =  reqJo.containsKey("proposalAmount") ? reqJo.getString("proposalAmount") : "";
        if("".equals(proposalAmount) || proposalAmount == null || Double.parseDouble(proposalAmount) <0 ){
            jo.put("code","101");
            jo.put("msg","建议金额，参数为空");
            return jo.toString();
        }
        String proposalLimit =   reqJo.containsKey("proposalLimit") ? reqJo.getString("proposalLimit") : "";
        if("".equals(proposalLimit) || proposalLimit == null || Integer.parseInt(proposalLimit) <= 0){
            jo.put("code","101");
            jo.put("msg","建议期限，参数为空");
            return jo.toString();
        }
        String repaymentDate =   reqJo.containsKey("repaymentDate") ? reqJo.getString("repaymentDate") : "";
        if("".equals(repaymentDate) || repaymentDate == null || Integer.parseInt(repaymentDate) <= 0){
            jo.put("code","101");
            jo.put("msg","还款日期，参数为空");
            return jo.toString();
        }
        //还款方式是等额本金 本息
        if(LoanRepayPlanEnum.EQUAL_PRINCIPAL.equalValue(repaymentMode) || LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.equalValue(repaymentMode)){
            String proposalRatio =  reqJo.containsKey("proposalRatio") ? reqJo.getString("proposalRatio") : "";
            if("".equals(proposalRatio) || proposalRatio == null || Double.parseDouble(proposalRatio)< 0){
                jo.put("code","101");
                jo.put("msg","建议利率，参数为空");
                return jo.toString();
            }
        }
        return null;
    }

    /**
     *  放款阶段 校验json必填项，并返回未填项的信息
     * 成功返回null
     * @param reqJo
     * @return
     */
    private String checkJsonLoan(JSONObject reqJo){
        JSONObject jo = new JSONObject();
        String loanBackMode =  reqJo.containsKey("loanBackMode") ? reqJo.getString("loanBackMode") : "";
        if ("".equals(loanBackMode) || loanBackMode == null){
            jo.put("code","101");
            jo.put("msg","还款方式，不能为空");
            return jo.toString();
        }
        String loanAmount =  reqJo.containsKey("loanAmount") ? reqJo.getString("loanAmount") : "";
        if("".equals(loanAmount) || loanAmount == null || Double.parseDouble(loanAmount) <0 ){
            jo.put("code","101");
            jo.put("msg","建议金额，参数为空");
            return jo.toString();
        }
        String loanLimit =   reqJo.containsKey("loanLimit") ? reqJo.getString("loanLimit") : "";
        if("".equals(loanLimit) || loanLimit == null || Integer.parseInt(loanLimit) <= 0){
            jo.put("code","101");
            jo.put("msg","建议期限，参数为空");
            return jo.toString();
        }
        String loanBackDate =   reqJo.containsKey("loanBackDate") ? reqJo.getString("loanBackDate") : "";
        if("".equals(loanBackDate) || loanBackDate == null || Integer.parseInt(loanBackDate) <= 0){
            jo.put("code","101");
            jo.put("msg","还款日期，参数为空");
            return jo.toString();
        }
        //还款方式是等额本金 本息
        if(LoanRepayPlanEnum.EQUAL_PRINCIPAL.equalValue(loanBackMode) || LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.equalValue(loanBackMode)){
            String loanRatio =  reqJo.containsKey("loanRatio") ? reqJo.getString("loanRatio") : "";
            if("".equals(loanRatio) || loanRatio == null || Double.parseDouble(loanRatio)< 0){
                jo.put("code","101");
                jo.put("msg","建议利率，参数为空");
                return jo.toString();
            }
        }
        return null;
    }

    /**
     * 调查结论还款计划列表
     * @param repayPlanInfoList
     * @param json
     * @param repaymentMode  还款方式
     * @return
     */
    private String getLoanRepayPlanJson(List<LoanRepayPlanInfoExtend> repayPlanInfoList,JSONObject json,String repaymentMode) {
        JSONArray ja = new JSONArray();
        if(repayPlanInfoList != null && repayPlanInfoList.size() > 0){
            for(LoanRepayPlanInfoExtend repayPlanInfo : repayPlanInfoList){
                JSONObject jo = new JSONObject();
                jo.put("id",repayPlanInfo.getId());    //还款计划表id
                jo.put("loanPrincipalAmount",repayPlanInfo.getLoanPrincipalAmount()); //还款本金
                jo.put("loanAccrualAmount",repayPlanInfo.getLoanAccrualAmount());     //还款利息
                jo.put("getLoanRepayPlanDateF",repayPlanInfo.getLoanRepayPlanDateF()); //计划还款日期
                jo.put("loanAccrualDays",repayPlanInfo.getLoanAccrualDays());           //计息天数
                //还款方式是等额本金 本息
                if(LoanRepayPlanEnum.EQUAL_PRINCIPAL.equalValue(repaymentMode) || LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.equalValue(repaymentMode)){
                    jo.put("repayment",repayPlanInfo.getRepayment());             //本期还款
                }
                ja.add(jo);
            }
        }
        json.put("data",ja);
        json.put("repaymentMode",repaymentMode);
        json.put("code", CodeEnum.CODE_100.getCode());
        json.put("msg", CodeEnum.CODE_100.getMsg());
        return json.toString();
    }

    /**
     * 保存还款计划信息列表
     * @param request
     * @param response
     * @return
     * 审核阶段和放款阶段公用一个方法唯一区别还款方式的字段名名称不一样
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/saveLoanRepayPlanInfoList")
    public ResponseEntity<String> saveLoanRepayPlanInfoList(HttpServletRequest request, HttpServletResponse response) {
        String reqJson = null;
        try{
            reqJson = HttpParseUtil.getJsonStr(request);
            if(StringUtils.isEmpty(reqJson)){
                log.error("还款计划接口，参数为空");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
            }
            JSONObject reqObj = JSONObject.fromObject(reqJson);
            JSONArray jsonArray = reqObj.getJSONArray("data");
            List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = new ArrayList<LoanRepayPlanInfoExtend>();
            Integer loanId = reqObj.getInt("loanId");
            //灵活还款,打开按钮
            String loanProcessType = iRepayPlanInfoProvider.getApplyInfoById(loanId).getLoanProcessType();
            Integer loginUserId = reqObj.getInt("userId");
            String repaymentMode = null;
            String  loanAmount = reqObj.getString("loanAmount");
            if(reqObj.has("repaymentMode")) repaymentMode = reqObj.getString("repaymentMode");
            else if(reqObj.has("loanBackMode")) repaymentMode = reqObj.getString("loanBackMode");
            for (int i = 0;i<jsonArray.size();i++){
                LoanRepayPlanInfoExtend repayPlanInfo = new LoanRepayPlanInfoExtend();
                JSONObject jo = jsonArray.getJSONObject(i);
                Double loanPrincipalAmount = jo.getDouble("loanPrincipalAmount");
                Double loanAccrualAmount = jo.getDouble("loanAccrualAmount");
                String loanAccrualDays = jo.getString("loanAccrualDays");
                Integer id = jo.getInt("id");
                String loanRepayPlanDate = jo.getString("loanRepayPlanDateF");
                if (StringUtils.isNotBlank(loanRepayPlanDate))
                    repayPlanInfo.setLoanRepayPlanDate(DateUtil.parser(loanRepayPlanDate, DateUtil.DEFAULT_DATE_FORMAT));
                repayPlanInfo.setLoanRepayAccrualAmount(new BigDecimal(loanAmount).subtract(new BigDecimal(loanAmount)));
                repayPlanInfo.setId(id);
                repayPlanInfo.setLoanPrincipalAmount(new BigDecimal(loanPrincipalAmount));
                repayPlanInfo.setLoanPrincipalAmount(new BigDecimal(loanAccrualAmount));
                repayPlanInfo.setLoanAccrualDays(Integer.parseInt(loanAccrualDays));
                repayPlanInfo.setRepaymentMode(repaymentMode);  //还款方式 xl
                repayPlanInfo.setDefaultValue(loginUserId,loanId);
                repayPlanInfo.setLoanProcessType(loanProcessType);
                loanRepayPlanInfos.add(repayPlanInfo);
            }
            iRepayPlanInfoProvider.deleteRepayPlanInfoByLoanId(loanId,loanProcessType);
            iRepayPlanInfoProvider.savePlanList(loanRepayPlanInfos);
        }catch (Exception e){
            log.error("保存贷款申请信息接口，json解析出错|"+reqJson,e);
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
        }
        return new ResponseEntity<String>(AppJsonResponse.success(),HttpStatus.OK);
    }
}
