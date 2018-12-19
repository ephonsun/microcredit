package banger.controller;

import banger.action.AppBaseController;
import banger.common.BaseController;
import banger.common.annotation.TokenRepeatAnnotation;
import banger.common.annotation.FuncPermitAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.enumerate.LoanAuditResultEnum;
import banger.domain.enumerate.LoanExamineCode;
import banger.domain.enumerate.LoanExamineType;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.*;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanAuditService;
import banger.util.ErrorUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款审批
 * Created by zhusw on 2017/7/3.
 */
@RequestMapping("/api")
@Controller
public class AppLoanAuditController extends AppBaseController {

    @Resource
    private ILoanModule loanModule;

    @Resource
    private IPermissionModule permissionModule;

    @Autowired
    private IAppLoanAuditService appLoanAuditService;

    @Resource
    private ICustomerBlackProvider customerBlackProvider;
    @Resource
    private IIndustryGradeexpProvider industryGradeexpProvider;
    @Resource
    private IRepayPlanInfoProvider repayPlanInfoProvider;

    @Value("${loan.approval.same}")	//一审、二审是否可以是同一人
    private String approvalSame;
    /**
     * http://localhost:8080/loan/api/v1/getLoanAuditFlowInfo.html?loanId=13
     * 得到贷款提交审批信息
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanAuditFlowInfo")
    public ResponseEntity<String> getLoanAuditFlowInfo(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        try {
            if (StringUtil.isNotEmpty(loanId)) {
                LoanApplyInfo loanInfo = loanModule.getLoanApplyProvider().getApplyInfoById(Integer.parseInt(loanId));
                if(loanInfo!=null) {
                    Integer code = loanModule.getLoanApplyProvider().checkTemplateRequireField(loanInfo.getLoanTypeId(), loanInfo.getLoanProcessType(), loanInfo.getLoanId());
                    CodeEnum codeEnum = CodeEnum.valueOf(code);
                    if (CodeEnum.CODE_100.equals(codeEnum)) {
                        LoanExamine loanExamine = loanModule.getLoanAuditProvider().getLoanExamine(Integer.parseInt(loanId));
                        if (loanExamine != null) {
                            if(loanExamine.getExamineCode().equals(LoanExamineCode.PASS)) {
                                String result = getLoanAuditFlowInfoJsonString(loanExamine).toString();
                                return new ResponseEntity<String>(result, HttpStatus.OK);
                            }else{
                                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_122,loanExamine.getExamineCode().message), HttpStatus.OK);
                            }
                        }
                    } else {
                        return new ResponseEntity<String>(AppJsonResponse.fail(codeEnum), HttpStatus.OK);
                    }
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_119), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        }catch(Exception e){
            ErrorUtil.logError("得到贷款指交审批信息异常",log,e,request);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    private JSONObject getLoanAuditFlowInfoJsonString(LoanExamine loanExamine){
        JSONObject root = new JSONObject();

        JSONArray ja = new JSONArray();
        if(loanExamine.getExamineCode().equals(LoanExamineCode.PASS)){
            root.put("paramId",loanExamine.getParamId());
            root.put("nextProcessId",loanExamine.getProcessId());
            root.put("processId",loanExamine.getProcessId());
            root.put("loanId",loanExamine.getLoanId());
            root.put("flowId",loanExamine.getFlowId());
            boolean random = true;
            for(LoanExamineReview review : loanExamine.getReviews()){
                JSONObject rjo = new JSONObject();
                rjo.put("id",review.getId());
                rjo.put("reviewCount",review.getReviewCount());       //审合人数
                rjo.put("limit",review.isLimit());                    //是否开启限额
                rjo.put("roleId",review.getRoleId());                   //角色ID
                rjo.put("roleName",review.getRoleName());               //角色名称
                rjo.put("type",review.getType().type);                //审合模式

                JSONArray rja = new JSONArray();

                if(review.getType().equals(LoanExamineType.RANDOM_USER)){
                    List<LoanExamineUser> examineUserList = review.getRandomUserList();
                    if(examineUserList!=null && examineUserList.size()>0){
                        for(LoanExamineUser examineUser : examineUserList){
                            JSONObject jo = new JSONObject();
                            jo.put("userId",examineUser.getUserId());
                            jo.put("userName",examineUser.getUserName());
                            if(examineUser.getLimitAmount()!=null) {
                                jo.put("limitAmount", examineUser.getLimitAmount().doubleValue());
                            }else{
                                jo.put("limitAmount", 0.0);
                            }
                            jo.put("limitEnable",examineUser.isLimitEnable());
                            rja.add(jo);
                        }
                    }
                }else{
                    random = false;
                    List<LoanExamineUser> examineUserList = review.getExamineUserList();
                    if(examineUserList!=null && examineUserList.size()>0){
                        for(LoanExamineUser examineUser : examineUserList){
                            JSONObject jo = new JSONObject();
                            jo.put("userId",examineUser.getUserId());
                            jo.put("userName",examineUser.getUserName());
                            if(examineUser.getLimitAmount()!=null) {
                                jo.put("limitAmount", examineUser.getLimitAmount().doubleValue());
                            }else{
                                jo.put("limitAmount", 0.0);
                            }
                            jo.put("limitEnable",examineUser.isLimitEnable());
                            rja.add(jo);
                        }
                    }
                }
                rjo.put("users",rja);
                ja.add(rjo);
            }
            root.put("random", random);
            root.put("code", CodeEnum.CODE_100.getCode());
            root.put("message",CodeEnum.CODE_100.getMsg());
        }else{
            root.put("code", CodeEnum.CODE_118.getCode());
            root.put("error",loanExamine.getExamineCode().code);
            root.put("message",loanExamine.getExamineCode().message);
        }
        root.put("data",ja);
        return root;
    }

    /*
     * 提交贷款审批人信息
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @SqlTransaction                //开启数据库事务
    @RequestMapping(value = "/v1/submitLoanAuditFlowInfo")
    public ResponseEntity<String> submitLoanAuditFlowInfo(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        String paramId = this.getParameter("paramId");
        String processId = this.getParameter("processId");
        String flowId = this.getParameter("flowId");
        String userIdStrs = this.getParameter("userIds");
        String createUserId = this.getParameter("createUserId");

        if (StringUtil.isNotEmpty(loanId) || StringUtil.isNotEmpty(paramId) || StringUtil.isNotEmpty(processId) || StringUtil.isNotEmpty(userIdStrs) || StringUtil.isNotEmpty(createUserId)) {
            //黑名单判断
            boolean isBlack = customerBlackProvider.isExistCustomerBlack(Integer.parseInt(loanId));
            if (isBlack)
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_117), HttpStatus.OK);
            LoanApplyInfo applyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(Integer.parseInt(loanId));
            if(applyInfo == null)
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_127), HttpStatus.OK);
            if (!LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType()))
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_126), HttpStatus.OK);
            String checkPlans = repayPlanInfoProvider.checkPlans(Integer.parseInt(loanId), null);
            if(StringUtils.isNotBlank(checkPlans)) {
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR,checkPlans), HttpStatus.OK);
            }
            //如果是调查提交审批,先更新经营类贷款行业指引等级说明表
            if (applyInfo.getLoanClassId() == 1 && StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()))
                industryGradeexpProvider.saveIndustryGradeexpByLoanId(Integer.parseInt(loanId));

            if(StringUtil.isNullOrEmpty(flowId))flowId = "0";           //向下兼容
            Integer loginUserId = this.getUserId();
            if(loginUserId!=null){
	            String result = this.saveLoanAuditFlowInfo(loanId,paramId,processId,flowId,userIdStrs,loginUserId.toString());
	            if("success".equals(result)) {
	                return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
	            }else{
	                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_121,result), HttpStatus.OK);
	            }
            }else{
            	return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_97), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }
    }

    private String saveLoanAuditFlowInfo(String loanId,String paramId,String processId,String flowId,String userIdStrs,String createUserId){
        String[] userIds = userIdStrs.split("\\,");
        String result = checkUserIds(userIds);
        if (StringUtils.isNotBlank(result)) {
            return result;
        }
        return loanModule.getLoanApprovalProvider().saveExamine(Integer.parseInt(loanId),paramId,processId,flowId,userIds,createUserId);
    }

    /**
     * 校验选择的审批人是否重复
     * @param userIds
     * @return
     */
    private String checkUserIds(String[] userIds) {
        Set<String> userIdSet = new HashSet<String>();
        for(String userId : userIds){
            if(!userIdSet.contains(userId))
                userIdSet.add(userId);
            else{
                return "选择的审批人员不能重复";
            }
        }
        return "";
    }


    /**
     * http://localhost:8080/loan/api/v1/getLoanAuditTemplateField.html?loanId=134
     * 得到审批决议模板字段
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getLoanAuditTemplateField")
    public ResponseEntity<String> getLoanAuditTemplateField(HttpServletRequest request,HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        String processIdStr = this.getParameter("processId");
        try {
            if(StringUtil.isNotEmpty(loanId)){
                //如果环节id不为空，则为审批阶段的自定义字段查询，否则为监控查询【或者其他贷后的查看审批字段】
                if (StringUtils.isBlank(processIdStr))
                    processIdStr = "0";
                String editableStr = this.getParameter("editable");
                boolean editable = StringUtils.isBlank(editableStr) ? false : Boolean.parseBoolean(editableStr);
                String jsonString = appLoanAuditService.getLoanTemplateFieldJsonString(Integer.parseInt(loanId), editable, Integer.parseInt(processIdStr));
                return new ResponseEntity<String>(jsonString, HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        }catch(Exception e){
            ErrorUtil.logError("得到审批决议模板字段数据异常",log,e,request);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }



    @NoLoginInterceptor
    @SqlTransaction                //开启数据库事务
    @FuncPermitAnnotation(values="loanAuditSave")
    @RequestMapping(value = "/v1/saveExamine")
    public ResponseEntity<String> saveExamine(HttpServletRequest request,HttpServletResponse response){
        String reqJson = null;
            reqJson = HttpParseUtil.getJsonStr(request);
        //参数判断
        ResponseEntity<String> responseEntity = checkAppParams("保存审批人接口",  reqJson, AppParamsConst.LOAN_ID);
        if (responseEntity != null)
            return responseEntity;
        JSONObject reqObj = JSONObject.fromObject(reqJson);
            return saveExamineInfo(reqObj, request);
    }

    private ResponseEntity<String> saveExamineInfo(JSONObject reqObj, HttpServletRequest request) {
        try {
            Integer loanId = reqObj.getInt("loanId");
            //主键判断
            if(loanId == null)
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            //黑名单判断
            boolean isBlack = customerBlackProvider.isExistCustomerBlack(loanId);
            if (isBlack)
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_117), HttpStatus.OK);
            LoanApplyInfo applyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
            if(applyInfo == null)
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_127), HttpStatus.OK);
            if (!LoanProcessTypeEnum.APPROVAL.type.equals(applyInfo.getLoanProcessType()))
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_126), HttpStatus.OK);

            String nextProcessId = reqObj.getString("nextProcessId");
            String userId = reqObj.getString("userId");
            String flowId = reqObj.getString("flowId");
            String paramId = reqObj.getString("paramId");
            String userIdStr = reqObj.getString("userIds");
            String[] userIds = userIdStr.split("\\,");
            //保存之前先校验审批人是否重复
            String check = checkUserIds(userIds);
            if (StringUtils.isNotBlank(check))
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR, check), HttpStatus.OK);

            Integer loginUserId = this.getUserId();
	        if(loginUserId!=null){
	            String result = loanModule.getLoanApprovalProvider().saveExamine(loanId,paramId,nextProcessId,flowId,userIds,loginUserId.toString());
	            if("success".equals(result)) {
	                saveLoanApprovalInfoByJson(reqObj, "");
	                return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
	            }else{
	                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_121,result), HttpStatus.OK);
	            }
            }else{
            	return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_97), HttpStatus.OK);
            }
        }catch(Exception e){
            ErrorUtil.logError("得到审批决议模板字段数据异常",log,e,request);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    @NoLoginInterceptor
    @SqlTransaction				//开启数据库事务
    @RequestMapping(value = "/v1/saveLoanApprovalInfo")
    public ResponseEntity<String> saveLoanApprovalInfo(HttpServletRequest request,HttpServletResponse response){
        String reqJson = HttpParseUtil.getJsonStr(request);
        //参数判断
        ResponseEntity<String> responseEntity = checkAppParams("保存审批信息接口", reqJson, AppParamsConst.LOAN_ID);
        if (responseEntity != null)
            return responseEntity;
        JSONObject reqObj = JSONObject.fromObject(reqJson);
        Integer loanId = reqObj.getInt("loanId");
        Integer userId = reqObj.getInt("userId");
        //主键判断
        if(loanId == null)
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        //黑名单判断
        boolean isBlack = customerBlackProvider.isExistCustomerBlack(loanId);
        if (isBlack)
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_117), HttpStatus.OK);
        LoanApplyInfo applyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
        if(applyInfo == null)
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_127), HttpStatus.OK);
        if (!LoanProcessTypeEnum.APPROVAL.type.equals(applyInfo.getLoanProcessType()))
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_126), HttpStatus.OK);

        if(reqObj!=null && reqObj.containsKey("repaymentDate")){
            Object val = reqObj.get("repaymentDate");
            if(val!=null && val instanceof String){
                String str = (String)val;
                if(StringUtil.isNotEmpty(str)){
                    Integer day = Integer.parseInt(str);
                    if(day <= 0 || day > 28){
                        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_134, "决议还款日仅允许填写1-28"),HttpStatus.OK);
                    }
                }
            }
        }

        String repaymentMode =  reqObj.containsKey("repaymentMode") ? reqObj.getString("repaymentMode") : "";
        String resultAmount =  reqObj.containsKey("resultAmount") ? reqObj.getString("resultAmount") : "";
        String resultLimit =  reqObj.containsKey("resultLimit") ? reqObj.getString("resultLimit") : "";
        String resultRatio =  reqObj.containsKey("resultRatio") ? reqObj.getString("resultRatio") : "";
        //校验还款计划之前校验所填金额（如果有）和申请金额是否符合要求（决议金额不能大于申请金额）
        if (StringUtils.isNotBlank(resultAmount)) {
            LoanApplyInfo loanApplyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
            if(null!=loanApplyInfo){
                BigDecimal applyMoney = loanApplyInfo.getLoanApplyAmount();
                BigDecimal thisMoney = new BigDecimal(resultAmount);
                if (applyMoney.compareTo(thisMoney) < 0) {
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR, "决议金额不能大于申请金额"), HttpStatus.OK);
                }
            }
        }

        String message = loanModule.getLoanApprovalProvider().checkPlansForApproval(loanId.toString(), repaymentMode, resultAmount, resultLimit, resultRatio);
        if (StringUtils.isNotBlank(message))
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR, message), HttpStatus.OK);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loanId", loanId);
        paramMap.put("isValid", "1");
        paramMap.put("auditResult", LoanAuditResultEnum.PERSONAL.value);
        List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = loanModule.getLoanAuditProvider().queryLoanAuditByCondition(paramMap);

        JSONArray resultJson = new JSONArray();
        if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses) && loanCurrentAuditStatuses.size() == 1) {
            LoanExamine loanExamine;
            if (Boolean.parseBoolean(approvalSame))
                loanExamine = loanModule.getLoanAuditProvider().getNextLoanExamine(loanId);
            else
                loanExamine = loanModule.getLoanAuditProvider().getNextLoanExamine(loanId, true);
            if (loanExamine != null) {
                if (loanExamine.isLastProcess()) {
                    String result = saveLoanApprovalInfoByJson(reqObj,"1");
                    if ("success".equals(result)){
                        return new ResponseEntity<String>(AppJsonResponse.success(resultJson), HttpStatus.OK);
                    }else{
                        return new ResponseEntity<String>(result, HttpStatus.OK);
                    }
                } else {
                    if(loanExamine.getExamineCode().equals(LoanExamineCode.PASS)) {
                        JSONObject result = getLoanAuditFlowInfoJsonString(loanExamine);
                        boolean random = result.getBoolean("random");
                        if (random) {
                            JSONArray jsonArray = result.getJSONArray("data");
                            StringBuffer userIds = new StringBuffer();
                            int b = 0;
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                JSONArray users = jsonObject.getJSONArray("users");
                                for (int j = 0; j < users.size(); j++) {
                                    JSONObject user = (JSONObject) users.get(j);
                                    if (b != 0) {
                                        userIds.append(",");
                                    }
                                    b++;
                                    userIds.append(user.get("userId"));
                                }
                            }
                            String[] checkUserIds = userIds.toString().split(",");
                            String checkUserIdMessage = checkUserIds(checkUserIds);
                            if (StringUtils.isNotBlank(checkUserIdMessage)) {
                                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR, checkUserIdMessage), HttpStatus.OK);
                            }
                            result.putAll(reqObj);
                            result.put("userId", userId);
                            result.put("userIds", userIds.toString());
                            return saveExamineInfo(result, request);
                        }
                        return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
                    }else{
                        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_122,loanExamine.getExamineCode().message), HttpStatus.OK);
                    }
                }
            }
        } else {
            String result = saveLoanApprovalInfoByJson(reqObj,"");
            if ("success".equals(result)){
                return new ResponseEntity<String>(AppJsonResponse.success(resultJson), HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(result, HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>(AppJsonResponse.success(resultJson), HttpStatus.OK);
    }

    /**
     * 最后一个审批人，又是最后一个审批环节，或者不是最后一个审批人，直接保存审批信息
     * @param reqObj
     */
    private String saveLoanApprovalInfoByJson(JSONObject reqObj, String last) {
        boolean moreAudit = reqObj.getBoolean("moreAudit");
        boolean flag = true;
        Integer loanId = reqObj.getInt("loanId");
        Integer processId = reqObj.getInt("processId");
        Integer userId = reqObj.getInt("userId");
        if (moreAudit) { //多人审批
            flag = loanModule.getLoanApprovalProvider().saveLoanApprovalInfo(null, loanId,processId,"1", last, userId);
        } else { //单人审批
            String repaymentDate =  reqObj.containsKey("repaymentDate") ? reqObj.getString("repaymentDate") : "";
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator iterator = reqObj.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                map.put("field." + key, reqObj.getString(key));
            }

            if(repaymentDate!=null && !"".equals(repaymentDate)) {
                Integer day = (Integer) TypeUtil.changeType(repaymentDate,Integer.class);
                if(day <= 0 || day > 28){
                    return AppJsonResponse.fail(CodeEnum.CODE_134, "还款日仅允许填写1-28");
                }
            }

            flag = loanModule.getLoanApprovalProvider().saveLoanApprovalInfo(map, loanId,processId,"", last, userId);
        }
        if(flag){
            return AppJsonResponse.success();
        }else{
            return AppJsonResponse.fail(CodeEnum.CODE_101);
        }
    }
}
