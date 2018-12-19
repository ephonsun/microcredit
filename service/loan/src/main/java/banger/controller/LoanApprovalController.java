package banger.controller;


import banger.common.BaseController;
import banger.common.annotation.TokenRepeatAnnotation;
import banger.common.tools.StringUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.config.AutoTableColumn;
import banger.domain.enumerate.LoanAuditResultEnum;
import banger.domain.enumerate.LoanExamineCode;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.*;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.DateUtil;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ICustomerBlackProvider;
import banger.service.intf.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * 贷款审批页面访问类
 */
@Controller
@RequestMapping("/loanApproval")
public class LoanApprovalController extends BaseController {
	private static final long serialVersionUID = 7094900062908645886L;
	@Autowired
	private ILoanApprovalService loanApprovalService;
	@Autowired
	private ILoanExamineService loanExamineService;
	@Autowired
	private ICurrentAuditStatusService currentAuditStatusService;
	@Autowired
	private IApplyInfoService applyInfoService;
	@Autowired
	private IConfigModule configModule;
    @Resource
    private ICustomerBlackProvider customerBlackProvider;
	@Resource
    private IIndustryGradeexpService industryGradeexpService;
	@Autowired
    private IRepayPlanInfoService repayPlanInfoService;
	@Autowired
    private IAuditTableFieldService auditTableFieldService;

	private String basePath = "/loan/vm/approval/";

    @Value("${loan.approval.same}")	//一审、二审是否可以是同一人
    private String approvalSame;

    /**
     * 保存审批信息
     * @param loanId 需要审批的信息
     * @return
     */
    @RequestMapping(value = "/saveLoanApprovalInfoBefore", method = RequestMethod.POST)
    @ResponseBody
    public void saveLoanApprovalInfoBefore(@RequestParam(value = "loanId", defaultValue = "") String loanId,
                                           @RequestParam(value = "repaymentMode", defaultValue = "") String repaymentMode,
                                           @RequestParam(value = "proposalAmount", defaultValue = "") String proposalAmount,
                                           @RequestParam(value = "proposalLimit", defaultValue = "") String proposalLimit,
                                           @RequestParam(value = "proposalRatio", defaultValue = "") String proposalRatio){
        JSONObject resultJson = new JSONObject();
        try {
            if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
                LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
                if (!LoanProcessTypeEnum.APPROVAL.type.equals(applyInfo.getLoanProcessType()))  {
                    renderText(false, "当前贷款信息状态不正确！", "");
                } else {
                    boolean isBlack = customerBlackProvider.isExistCustomerBlack(Integer.parseInt(loanId));
                    if (isBlack) {
                        renderText(false,"客户贷款受限",loanId);
                    } else {
                        String message = loanApprovalService.checkPlansForApproval(loanId, repaymentMode,proposalAmount, proposalLimit, proposalRatio);
                        if (StringUtils.isNotBlank(message))
                            renderText(false, message, "");
                         else {
                            Map<String, Object> paramMap = new HashMap<String, Object>();
                            paramMap.put("loanId", loanId);
                            paramMap.put("isValid", "1");
                            paramMap.put("auditResult", LoanAuditResultEnum.PERSONAL.value);
                            List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);
                            if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses) && loanCurrentAuditStatuses.size() == 1) {
                                resultJson.put("thisProcessId", loanCurrentAuditStatuses.get(0).getProcessId());
                                LoanExamine loanExamine;
                                if (Boolean.parseBoolean(approvalSame))
                                    loanExamine = loanExamineService.getNextLoanExamine(Integer.parseInt(loanId));
                                else
                                    loanExamine = loanExamineService.getNextLoanExamine(Integer.parseInt(loanId), true);
                                resultJson.put("processId", loanExamine.getProcessId());
                                if (loanExamine.isLastProcess()) {
                                    resultJson.put("last", true);
                                    renderJson(true, "", resultJson);
                                } else {
                                    if (LoanExamineCode.PASS.code.equals(loanExamine.getExamineCode().code)) {
                                        resultJson.put("next", true);
                                        resultJson.put("processId", loanExamine.getProcessId());
                                        resultJson.put("flowId", loanExamine.getFlowId());
                                        resultJson.put("paramId", loanExamine.getParamId());
                                        resultJson.put("random", loanExamine.isRandom());
                                        resultJson.put("reviews", LoanUtil.getJsonByLoanExamine(loanExamine.getReviews()));
                                        renderJson(true, "", resultJson);
                                    } else {
                                        renderText(false,loanExamine.getExamineCode().message,"json");
                                    }
                                }
                            } else {
                                renderJson(true, "", resultJson);
                            }
                        }

                    }
                }
            } else {
                renderText(false,"参数错误","json");
            }
        } catch (Exception e) {
            log.error("提交审核信息异常|",e);
            renderText(false,"保存失败",String.valueOf(""));
        }

    }
    /**
     * 保存审批信息
     * @param json 需要审批的人
     * @return
     */
    @RequestMapping(value = "/saveLoanApprovalInfo", method = RequestMethod.POST)
    @ResponseBody
    @SqlTransaction
    public void saveLoanApprovalInfo(@RequestParam(value = "json", defaultValue = "") String json,
                                     @RequestParam(value = "loanId", defaultValue = "") String loanId,
                                     @RequestParam(value = "thisProcessId", defaultValue = "") Integer thisProcessId,
                                     @RequestParam(value = "last", defaultValue = "") String last){
        try {
            if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
                Integer loginUserId = getLoginInfo().getUserId();
                Map<String, Object> map = JsonTools.parseJSON2Map(json);
                if (map != null && map.size() > 0) {
                    boolean result = loanApprovalService.saveLoanApprovalInfo(map, Integer.parseInt(loanId), thisProcessId,"", last, loginUserId);
                    renderText(true, result ? "操作成功" : "操作失败", loanId);
                } else {
                    boolean result = loanApprovalService.saveLoanApprovalInfo(null, Integer.parseInt(loanId),thisProcessId,"1", last, loginUserId);
                    renderText(true, result ? "操作成功" : "操作失败", loanId);
                }
//                renderText(false,"参数错误","json");
            } else {
                renderText(false,"参数错误","json");
            }
        } catch (Exception e) {
            log.error("提交审核信息异常|"+json,e);
            renderText(false,"操作失败",String.valueOf(""));
        }

    }


	/**
	 * 保存审批流程
	 * @param reviews 需要审批的人
	 * @return
	 */
	@RequestMapping(value = "/saveExamine", method = RequestMethod.POST)
	@ResponseBody
    @SqlTransaction
    @TokenRepeatAnnotation
	public void saveLoanApplyInfo(@RequestParam(value = "reviews", defaultValue = "") String reviews,
								  @RequestParam(value = "loanId", defaultValue = "") String loanId,
                                  @RequestParam(value = "processId", defaultValue = "") String processId,
                                  @RequestParam(value = "flowId", defaultValue = "") String flowId,
                                  @RequestParam(value = "paramId", defaultValue = "") String paramId){
		try {
			if (StringUtils.isNotBlank(reviews) && StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)
                    && StringUtils.isNotBlank(flowId) && StringUtils.isNumeric(flowId)
                    && StringUtils.isNotBlank(paramId) && StringUtils.isNumeric(paramId)
                    && StringUtils.isNotBlank(processId) && StringUtils.isNumeric(processId)) {
                LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
                if (LoanProcessTypeEnum.APPROVAL.type.equals(applyInfo.getLoanProcessType()) ||
                        (LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())
                                && getLoginInfo().getUserId().intValue() == applyInfo.getLoanInvestigationUserId().intValue() )) {
                    Integer loginUserId = getLoginInfo().getUserId();
                    List<Map<String, Object>> maps = JsonTools.parseJSON2List(reviews);
                    String errorMessage = "";
                    StringBuffer userIdStr = new StringBuffer();
                    int i = 0;
                    List<String> userIdList = new ArrayList<String>();
                    for (Map<String, Object> map : maps) {
                        List<Map<String, Object>> users = (List<Map<String, Object>>) map.get("users");
                        for (Map<String, Object> userMap : users) {
                            if (i > 0) userIdStr.append(",");
                            else i++;
                            String userId = String.valueOf(userMap.get("userId"));
                            if (userIdList.indexOf(userId)>-1) {
                                errorMessage = "审批人选择重复";
                                break;
                            } else {
                                userIdList.add(userId);
                                userIdStr.append(userId);
                            }
                        }
                    }
                    String[] userIds = userIdStr.toString().split(",");
                    //如果是调查提交审批,先更新经营类贷款行业指引等级说明表
                    if (StringUtils.isBlank(errorMessage) && LoanProcessTypeEnum.INVESTIGATE.type.equals(applyInfo.getLoanProcessType())){
                        errorMessage = repayPlanInfoService.checkPlans(Integer.parseInt(loanId),null);
                    }

                    if(StringUtils.isNotBlank(errorMessage)) {
                        renderText(false, errorMessage, "");
                    } else {
                        if (applyInfo.getLoanClassId() == 1 && StringUtil.isNotEmpty(applyInfo.getLoanBusinessCatalog()))
                            industryGradeexpService.saveIndustryGradeexpByLoanId(Integer.parseInt(loanId));
                        //然后在保存提交申请信息
                        loanApprovalService.saveExamine(Integer.parseInt(loanId), paramId, processId, flowId, userIds, loginUserId.toString());
                        renderText(true,"操作成功","json");
                    }
                } else {
                    renderText(false, "当前贷款信息状态不正确！", "");
                }
			} else {
				renderText(false,"参数错误","json");
			}
		} catch (Exception e) {
            log.error("保存审批人失败",e);
			renderText(false,"保存失败",String.valueOf(""));
		}
	}



	/**
	 * 得到选择审批人页面
	 * @return
	 */
	@RequestMapping("/selectUserForward")
	public String selectUser(@RequestParam(value = "loanId", defaultValue = "") String loanId,
                             @RequestParam(value = "next", defaultValue = "") String next
                             ) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
            LoanExamine loanExamine;
		    if ("1".equals(next)) {
                if (Boolean.parseBoolean(approvalSame))
                    loanExamine = loanExamineService.getNextLoanExamine(Integer.parseInt(loanId));
                else
                    loanExamine = loanExamineService.getNextLoanExamine(Integer.parseInt(loanId), true);
		    } else {
                loanExamine = loanExamineService.getLoanExamine(Integer.parseInt(loanId));
            }
            setAttribute("reviews", LoanUtil.getJsonByLoanExamine(loanExamine.getReviews()));
            setAttribute("random", loanExamine.isRandom());
            setAttribute("loanId", loanId);
			setAttribute("processId", loanExamine.getProcessId());
            setAttribute("flowId", loanExamine.getFlowId());
            setAttribute("paramId", loanExamine.getParamId());
		}
		return  basePath + "selectUser";
	}




	/**
     *  审批信息
	 * @return
	 */
	@RequestMapping("/getApprovalInfo")
	public String getLoanTemplate(@RequestParam(value = "loanId", defaultValue = "") String loanId,
								  @RequestParam(value = "showApply", defaultValue = "") String showApply){
        //待审核信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loanId", loanId);
        paramMap.put("isValid",1);
        paramMap.put("auditResult",LoanAuditResultEnum.PERSONAL.value);
        List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);

        setAttribute("auditStatuses", loanCurrentAuditStatuses);


        //已审核（通过）信息。
        List<Map<String, Object>> passMaps = new ArrayList<Map<String, Object>>();
        LinkedHashMap<String, String> processMap = loanApprovalService.getProcessMapByLoanId(Integer.valueOf(loanId));
        paramMap.put("auditResult",LoanAuditResultEnum.FAMILY.value);
        for (String processIdKey : processMap.keySet()) {
            if (processIdKey.contains("count")) {
                continue;
            }
            List<AutoBaseTemplate> templates = new ArrayList<AutoBaseTemplate>();
            Map<String, Object> passMap = new HashMap<String, Object>();
            passMap.put("processName", processMap.get(processIdKey));
            paramMap.put("processId", processIdKey);

            //表单信息
            List<LoanCurrentAuditStatus> loanCurrentAuditStatusesPass = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);
            List<LoanAuditTableFieldExtend> fieldList = auditTableFieldService.queryAuditTableFieldSelect(Integer.parseInt(processIdKey));
            List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getApprovalTemplateListByIds(new Integer[]{15}, fieldList);
            if (CollectionUtils.isNotEmpty(templateList)) {
                AutoBaseTemplate oldTemplate = templateList.get(0);
                String tableName = oldTemplate.getTableName();
                if (CollectionUtils.isNotEmpty(loanCurrentAuditStatusesPass)) {
                    for (LoanCurrentAuditStatus pass : loanCurrentAuditStatusesPass) {
                        AutoBaseTemplate template = new AutoBaseTemplate();
                        template.setFields(oldTemplate.getFields());
                        template.setTableName(tableName);
                        template.setName(oldTemplate.getName());
                        template.setType(oldTemplate.getType());
                        template.setJsonFields(oldTemplate.getJsonFields());
                        Map<String,Object> attributeMap = new HashMap<String, Object>();
                        int processCount = Integer.parseInt(processMap.get(String.valueOf("count" + pass.getProcessId())));
                        if (processCount > 1) {
                            attributeMap.put("noData", "1");
                        } else {
                            DataTable datatable = applyInfoService.getDataTableById(tableName, pass.getAuditResolutionId());
                            if(datatable!=null && datatable.rowSize()>0){
                                template.setData(datatable.getRows());
                            } else {
                                attributeMap.put("noData", "1");
                            }
                        }
                        attributeMap.put("userName", pass.getAuditUserName());
                        attributeMap.put("date", DateUtil.format(pass.getUpdateDate(), DateUtil.DEFAULT_DATEMINUTE_FORMAT));
                        template.setAttributeMap(attributeMap);
                        templates.add(template);
                    }
                }
            }
            passMap.put("templates", templates);
            passMaps.add(passMap);
        }

        //默认表单信息
        Integer loginUserId = this.getLoginInfo().getUserId();
        paramMap.put("auditResult",LoanAuditResultEnum.PERSONAL.value);
        paramMap.put("auditUserId", loginUserId);
        List<LoanCurrentAuditStatus> loanCurrentAuditStatusSelf = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);
        if (CollectionUtils.isNotEmpty(loanCurrentAuditStatusSelf)) {
            LoanCurrentAuditStatus loanCurrentAuditStatus = loanCurrentAuditStatusSelf.get(0);
            List<LoanAuditTableFieldExtend> fieldList = auditTableFieldService.queryAuditTableFieldSelect(loanCurrentAuditStatus.getProcessId());
            List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getApprovalTemplateListByIds(new Integer[]{15}, fieldList);
            AutoBaseTemplate oldTemplate = templateList.get(0);
            int newValue = Integer.parseInt(processMap.get(String.valueOf("count" + loanCurrentAuditStatus.getProcessId())));
//            String processName = processMap.get(String.valueOf(loanCurrentAuditStatus.getProcessId()));
            if (newValue > 1) {
                this.setAttribute("moreReview", "1");
            } else if (newValue == 1) {
//                DataTable applyInfoData = applyInfoService.getDataTableByLoanId("LBIZ_APPROVAL_RESOLUTION", LoanProcessTypeEnum.APPROVAL.type, Integer.valueOf(loanId));
                DataTable applyInfoData = applyInfoService.getApprovalDataTableByLoanId(Integer.valueOf(loanId));
                if (applyInfoData.getRows() == null || applyInfoData.getRows().length == 0) {
                    setFormInfo(oldTemplate, oldTemplate.getTableName(), loanId);
                } else {
                    DataRow sourceRow = applyInfoData.getRow(0);
                    List<AutoBaseField> baseFiledList = oldTemplate.getFields();
                    for(AutoBaseField baseFiled: baseFiledList){
                        AutoFieldWrapper autoFieldWrapper = (AutoFieldWrapper) baseFiled;
                        String column = baseFiled.getColumn();
                        if(null == sourceRow.get(column)&& StringUtil.isNotEmpty(autoFieldWrapper.getDefaultValue())){
                            sourceRow.set(column,autoFieldWrapper.getDefaultValue());
                        }
                    }
                    oldTemplate.setData(new DataRow[]{sourceRow});
                    this.setAttribute("template", oldTemplate);
                }
            }
        }
        this.setAttribute("showApply", showApply);
        this.setAttribute("passMaps", passMaps);
        return  basePath + "approvalInfo";

	}

    /**
     * 设置表单信息
     * @param template
     * @param tableName
     * @param loanId
     */
    private void setFormInfo (AutoBaseTemplate template, String tableName, String loanId) {
        DataTable applyInfoData = applyInfoService.getDataTableByLoanId("LBIZ_SURVEY_RESULT", LoanProcessTypeEnum.INVESTIGATE.type, Integer.valueOf(loanId));
        //如果是第一次审批，查出调查结论，设置审批默认值。
        if(template.getData() == null && applyInfoData!=null && applyInfoData.rowSize()>0){
            DataRow sourceRow = applyInfoData.getRows()[0];
            DataTable table = new DataTable(tableName);
            DataRow targetRow = table.newRow();
            targetRow.set("LOAN_MODE", sourceRow.get("LOAN_MODE"));
            targetRow.set("REPAYMENT_MODE", sourceRow.get("REPAYMENT_MODE"));
            targetRow.set("RESULT_LIMIT",sourceRow.get("PROPOSAL_LIMIT"));
            targetRow.set("RESULT_AMOUNT",sourceRow.get("PROPOSAL_AMOUNT"));
            targetRow.set("RESULT_RATIO",sourceRow.get("PROPOSAL_RATIO"));
            targetRow.set("REPAYMENT_DATE",sourceRow.get("REPAYMENT_DATE"));
            targetRow.set("GUARANTEE_MODE",sourceRow.get("GUARANTEE_MODE"));
            targetRow.set("PRODUCT_TYPE",sourceRow.get("PRODUCT_TYPE"));

            template.setData(new DataRow[]{targetRow});
        }
        this.setAttribute("template", template);
    }

}

