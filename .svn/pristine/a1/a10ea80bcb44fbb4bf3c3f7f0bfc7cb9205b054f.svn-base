package banger.service.impl;

import banger.common.tools.LoanModuleUtil;
import banger.dao.intf.ILoanAuditProcessDao;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.ModeConfigFile;
import banger.domain.enumerate.LoanAuditResultEnum;
import banger.domain.enumerate.LoanOperationType;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.LoanRepayPlanEnum;
import banger.domain.loan.*;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.DateUtil;
import banger.moduleIntf.*;
import banger.service.intf.*;
import banger.util.ExcelDownUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/6/21.
 */
@Service
public class LoanApprovalService implements ILoanApprovalService, ILoanApprovalProvider {
    @Resource
    IConfigModule configModule;
    @Autowired
    private ILoanApplyService loanApplyService;
    @Autowired
    private ICurrentAuditStatusService currentAuditStatusService;
    @Autowired
    private ILoanAuditProcessDao loanAuditProcessDao;
    @Autowired
    private IApplyInfoService applyInfoService;
    @Autowired
    private ILoanOperationService loanOperationService;
    @Autowired
    private IRepayPlanInfoService repayPlanInfoService;
    @Autowired
    private IAuditTableFieldService auditTableFieldService;
    @Autowired
    private IPermissionModule permissionModule;


    /**
     * 调查提交审批时，复制还款计划到审批环节，复制调查结论到审批，保存提交审批操作日志
     * [app,web]公用提交审批
     */
    @Override
    public String saveExamine(Integer loanId,String paramId,String processId,String flowId,String[] userIds,String loginUserIdStr){
        Integer loginUserId = Integer.parseInt(loginUserIdStr);
        LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(loanId);
        if(loanApplyInfo!=null) {
            String[] userNames = permissionModule.getUserNamesByIds(userIds);
            for (int i = 0; i < userIds.length; i++) {
                LoanCurrentAuditStatus loanAudit = new LoanCurrentAuditStatus();
                loanAudit.setAuditUserId(Integer.parseInt(userIds[i]));
                loanAudit.setAuditUserName(userNames[i]);
                loanAudit.setLoanId(loanId);
                loanAudit.setProcessId(Integer.parseInt(processId));
                loanAudit.setCreateUser(loginUserId);
                loanAudit.setCreateDate(new Date());
                currentAuditStatusService.insertCurrentAuditStatus(loanAudit, loginUserId);
            }
            //当调查提交审批流程是，保存调查提交审批日志
            if (LoanProcessTypeEnum.INVESTIGATE.type.equals(loanApplyInfo.getLoanProcessType())) {
                submitApproval(loanId, loginUserId);
                loanApplyInfo.setLoanInvestigationUserId(loginUserId);
                loanApplyInfo.setLoanInvestigationDate(new Date());
                applyInfoService.sendLoanMsg(loanApplyInfo,userIds);
            }
            loanApplyInfo.setLoanProcessType(LoanProcessTypeEnum.APPROVAL.type);
            loanApplyInfo.setLoanAuditParamId(Integer.parseInt(paramId));
            loanApplyInfo.setLoanAuditFlowId(Integer.parseInt(flowId));
            loanApplyInfo.setLoanAuditProcessId(Integer.parseInt(processId));
            loanApplyService.updateApplyInfo(loanApplyInfo, loginUserId);

        }
        return "success";
    }

    /**
     * 私有，复制还款计划到审批环节，复制调查结论到审批，保存提交审批操作日志
     * @param loanId
     * @param loginUserId
     */
    private void submitApproval(Integer loanId, Integer loginUserId) {
        //
        DataTable datatable = applyInfoService.getDataTableById("LBIZ_SURVEY_RESULT", loanId);
        //调查提交，如果调查结论存在，复制调查信息到审批
        if (datatable != null && datatable.getRows() != null) {
            List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{15});
            if (CollectionUtils.isNotEmpty(templateList)) {
                AutoBaseTemplate autoTemplate = templateList.get(0);
                DataTable dataTable = new DataTable();
                String tableName = autoTemplate.getTableName();
                dataTable.setName(tableName);
                dataTable.addColumn("ID");
                dataTable.newRow();
                DataRow dataRow = dataTable.getRow(0);
                dataRow.set("LOAN_ID", loanId);
                dataRow.set("LOAN_PROCESS_TYPE", LoanProcessTypeEnum.APPROVAL.type);
                dataRow.set("CREATE_USER", loginUserId);
                dataRow.set("CREATE_DATE", new Date());
                dataRow.set("UPDATE_USER", loginUserId);
                dataRow.set("UPDATE_DATE", new Date());
                dataRow.set("IS_MASTER", 1);
                //从调查结论里查数据
                DataTable resultData = applyInfoService.getDataTableByLoanId("LBIZ_SURVEY_RESULT", LoanProcessTypeEnum.INVESTIGATE.type, loanId);
                if (resultData != null && resultData.getRows() != null) {
                    DataRow sourceRow = resultData.getRow(0);
                    dataRow.set("LOAN_MODE", sourceRow.get("LOAN_MODE"));
                    dataRow.set("REPAYMENT_MODE", sourceRow.get("REPAYMENT_MODE"));
                    dataRow.set("RESULT_LIMIT",sourceRow.get("PROPOSAL_LIMIT"));
                    dataRow.set("RESULT_AMOUNT",sourceRow.get("PROPOSAL_AMOUNT"));
                    dataRow.set("RESULT_RATIO",sourceRow.get("PROPOSAL_RATIO"));
                    dataRow.set("REPAYMENT_DATE",sourceRow.get("REPAYMENT_DATE"));
                    dataRow.set("GUARANTEE_MODE",sourceRow.get("GUARANTEE_MODE"));
                    dataRow.set("PRODUCT_TYPE",sourceRow.get("MAIN_PRO_TYPE_NAME"));
                }
                loanApplyService.saveLoanTemplateInfo(dataTable);
            }
        }
        // 记录操作日志
        loanOperationService.addLoanOperation(loanId, LoanOperationType.LOAN_INVESTIGATE_SUBMIT.typeName, "", loginUserId, LoanProcessTypeEnum.INVESTIGATE.type);
        repayPlanInfoService.copyPlansToApproval(loanId);
    }


    @Override
    public boolean saveLoanApprovalInfo(Map<String, Object> map, Integer loanId, Integer processId, String moreReview, String last, Integer loginUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loanId", loanId);
        paramMap.put("isValid", "1");
        paramMap.put("auditResult", LoanAuditResultEnum.PERSONAL.value);
        paramMap.put("auditUserId", loginUserId);
        paramMap.put("processId", processId);
        //查出当前审核人的审核状态信息，并更新
        List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);
        if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses)) {
            LoanCurrentAuditStatus loanCurrentAuditStatus = loanCurrentAuditStatuses.get(0);
            LoanCurrentAuditStatus currentAuditStatus = new LoanCurrentAuditStatus();
            currentAuditStatus.setId(loanCurrentAuditStatus.getId());
            currentAuditStatus.setAuditResult(LoanAuditResultEnum.FAMILY.value);
            Map<String, Object> customerMap = new HashMap<String, Object>();
            if (map != null ){
                for (String key : map.keySet()) {
                    String val = (String) map.get(key);
                    if (key.indexOf("field.") > -1) {
                        String propertyName = key.substring("field.".length());
                        if (StringUtils.isNotBlank(val)) {
                            customerMap.put(propertyName, val);
                        }
                    }
                }
            }
            //如果是最后一个审批更新主表信息
            if ("1".equals(last) || "true".equals(last)) {
                LoanApplyInfo loanApplyInfo = new LoanApplyInfo();
                if (!"1".equals(moreReview)) {
                    String resultAmount = (String) customerMap.get("resultAmount");
                    if (StringUtils.isNotBlank(resultAmount))
                        loanApplyInfo.setLoanResultAmount(new BigDecimal(resultAmount));
                    else {
                        //如果最后一个单审，没有填写审批金额，从主记录里更新决议金额
                        DataTable dataTable = applyInfoService.getApprovalDataTableByLoanId(loanId);
                        DataRow row = dataTable.getRow(0);
                        if (row != null)
                            loanApplyInfo.setLoanResultAmount(new BigDecimal(String.valueOf(row.get("RESULT_AMOUNT"))));
                    }
                    String repaymentMode = (String) customerMap.get("repaymentMode");
                    if (StringUtils.isNotBlank(repaymentMode))
                        loanApplyInfo.setRepaymentMode(repaymentMode);
                } else {
                    loanApplyInfo = applyInfoService.getApplyInfoById(loanId);
                    if (loanApplyInfo.getLoanResultAmount() == null) {
                        loanApplyInfo.setLoanResultAmount(loanApplyInfo.getLoanProposeAmount());
                    }
                }
                loanApplyInfo.setLoanId(loanId);
                loanApplyInfo.setLoanAuditDate(new Date());
                loanApplyInfo.setLoanProcessType(LoanProcessTypeEnum.CONTRACT.type);
                loanApplyService.updateApplyInfo(loanApplyInfo, loginUserId);


            }
            LinkedHashMap<String, String> processMap = getProcessMapByLoanId(Integer.valueOf(loanId));
            String num = processMap.get(String.valueOf(loanCurrentAuditStatus.getProcessId()));
            String content = StringUtils.isBlank(num) ? "" : num + "审";
            // 记录操作日志
            loanOperationService.addLoanOperation(loanId, LoanOperationType.LOAN_APPROVAL_PASS.typeName, content, loginUserId, LoanProcessTypeEnum.APPROVAL.type);
            LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(loanId);
            applyInfoService.sendLoanMsg(loanApplyInfo, new String[]{String.valueOf(loginUserId)});

            Integer approvalId = null;
            if (!"1".equals(moreReview)) {
                List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{15});
                if (CollectionUtils.isNotEmpty(templateList)) {
                    AutoBaseTemplate autoTemplate = templateList.get(0);
                    DataTable dataTable = new DataTable();
                    String tableName = autoTemplate.getTableName();
                    dataTable.setName(tableName);
                    dataTable.addColumn("ID");
                    dataTable.newRow();
                    DataRow dataRow = dataTable.getRow(0);
                    DataTable dataTableMaster = applyInfoService.getApprovalDataTableByLoanId(loanId);
                    DataRow dataRowMaster = dataTableMaster.getRow(0);
                    dataTableMaster.setName(tableName);
                    dataRow.set("LOAN_ID", loanId);
                    dataRow.set("LOAN_PROCESS_TYPE", LoanProcessTypeEnum.APPROVAL.type);
                    dataRow.set("create_user", loginUserId);
                    dataRow.set("create_date", new Date());
                    dataRow.set("update_user", loginUserId);
                    dataRow.set("update_date", new Date());
                    dataRow.set("IS_MASTER", 0);
                    List<AutoBaseField> fieldList = autoTemplate.getFields();
                    if(CollectionUtils.isNotEmpty(fieldList)){
                        for (AutoBaseField baseFiled : fieldList) {
                            String column = baseFiled.getColumn();
                            String field = baseFiled.getField();
                            if(StringUtils.isNotBlank(column) && StringUtils.isNotBlank(field)){
                                Object o= MapUtils.getObject(customerMap,field);
                                dataRow.set(column,o);
                                dataRowMaster.set(column, o);
                            }
                        }
                    }
                    loanApplyService.saveLoanTemplateInfo(dataTableMaster);
                    approvalId = loanApplyService.saveLoanTemplateInfo(dataTable);

                }
                currentAuditStatus.setAuditResolutionId(approvalId);
            }
            currentAuditStatusService.updateCurrentAuditStatus(currentAuditStatus, loginUserId);

        }
        return true;
    }

    @Override
    public LinkedHashMap<String, String> getProcessMapByLoanId(Integer loanId) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        List<LoanAuditProcess> loanAuditProcesses = loanAuditProcessDao.getLoanAuditProcessByLoanId(loanId);
        if (CollectionUtils.isNotEmpty(loanAuditProcesses)) {
            for (int i = 0; i < loanAuditProcesses.size(); i++) {
                LoanAuditProcess loanAuditProcess = loanAuditProcesses.get(i);
                map.put(String.valueOf(loanAuditProcess.getProcessId()), LoanModuleUtil.getProcessNameByIndex(i+1));
                map.put(String.valueOf("count" + loanAuditProcess.getProcessId()), String.valueOf(loanAuditProcess.getTotalCount()));
            }
        }
        return map;
    }

    @Override
    public void doExportFromRow(HttpServletRequest request, HttpServletResponse response, IApplyInfoService applyInfoService, IConfigModule configModule, ITableInfoProvider tableInfoProvider, IAutoFieldProvider autoFieldProvider, IScoreDetailInfoService scoreDetailInfoService, LoanApplyInfo_Web_Query loanApplyInfo, ModeConfigFile configFile) {
        //待审核信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loanId", loanApplyInfo.getLoanId());
        paramMap.put("isValid",1);
        paramMap.put("auditResult", LoanAuditResultEnum.PERSONAL.value);
        List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{15});
        //已审核（通过）信息。
        List<Map<String, Object>> passMaps = new ArrayList<Map<String, Object>>();
        LinkedHashMap<String, String> processMap = getProcessMapByLoanId(loanApplyInfo.getLoanId());
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
                                template.setData(datatable.getRow(0));
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loanId", loanApplyInfo.getLoanId());
        List<LoanScoreDetailInfo> scoreInfos = scoreDetailInfoService.queryScoreDetailInfoList(params);
        ExcelDownUtil.doExportFromRow(request, response,applyInfoService, configModule, tableInfoProvider, autoFieldProvider, loanApplyInfo, configFile, passMaps, scoreInfos);
    }



    /**
     * 查询选择审批字段
     */
    public List<LoanAuditTableFieldExtend> queryAuditTableFieldSelect(Integer processId) {
        return this.auditTableFieldService.queryAuditTableFieldSelect(processId);
    }

    @Override
    public DataTable getApprovalDataTableByLoanId(Integer loanId) {
        return applyInfoService.getApprovalDataTableByLoanId(loanId);
    }


    public String checkPlansForApproval(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio){
        //还款计划校验
        DataTable applyInfoData = applyInfoService.getApprovalDataTableByLoanId(Integer.valueOf(loanId));
        DataRow row = applyInfoData.getRow(0);
        if (StringUtils.isBlank(repaymentMode))
            repaymentMode = row.get("REPAYMENT_MODE").toString();
        if (StringUtils.isBlank(proposalAmount))
            proposalAmount =  row.get("RESULT_AMOUNT").toString();
        if (StringUtils.isBlank(proposalLimit))
            proposalLimit =  row.get("RESULT_LIMIT").toString();
//        if (StringUtils.isBlank(proposalRatio))
//            proposalRatio = row.get("RESULT_RATIO").toString();
        if (Integer.parseInt(repaymentMode) > 4)
            repaymentMode = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
        if (StringUtils.isNotBlank(repaymentMode)) {
            repayPlanInfoService.deleteRepayPlanInfoByLoanIdAndMode(loanId, LoanProcessTypeEnum.APPROVAL.type, repaymentMode);
        }
        if (LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value.equals(repaymentMode)){
            List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = repayPlanInfoService.queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, LoanProcessTypeEnum.APPROVAL.type);
            if ((CollectionUtils.isEmpty(loanRepayPlanInfos) || loanRepayPlanInfos.size() == 0)) return "还款计划未保存";
            else if (loanRepayPlanInfos.size() != Integer.parseInt(String.valueOf(proposalLimit)))  return "还款计划条数和期限不匹配";
            BigDecimal all = new BigDecimal(proposalAmount);
            for (LoanRepayPlanInfo loanRepayPlanInfo : loanRepayPlanInfos){
                all = all.subtract(loanRepayPlanInfo.getLoanPrincipalAmount());
            }
            if (all.doubleValue() != 0) return "还款计划总金额不匹配";
        }
        return "";
    }

}
