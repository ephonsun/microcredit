package banger.service.impl;

import banger.common.constant.DateFormatConst;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseOption;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyTemplate;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ILoanModule;
import banger.response.CodeEnum;
import banger.service.intf.IAppLoanAuditService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 审批决议
 * Created by zhusw on 2017/8/8.
 */
@Repository
public class AppLoanAuditService implements IAppLoanAuditService {

    @Resource
    private ILoanModule loanModule;
    @Resource
    private IConfigModule configModule;

    /**
     * 根据贷款Id得到模板和字段
     * @param loanId
     * @return
     */
    public String getLoanTemplateFieldJsonString(Integer loanId, boolean editable, Integer processId){
        String processType = LoanProcessTypeEnum.APPROVAL.type;
        Integer tableId = 15;           //审批决议模板
//        Boolean editable = false;
        //如果环节id不为空，则为审批阶段的自定义字段查询，否则为监控查询【或者其他贷后的查看审批字段】
        List<AutoBaseTemplate> templateList;
        if (processId.intValue() == 0) {
            templateList = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{tableId});
        } else {
            List<LoanAuditTableFieldExtend> fieldList = loanModule.getLoanApprovalProvider().queryAuditTableFieldSelect(processId);
            templateList = configModule.getAutoTemplateProvider().getApprovalTemplateListByIds(new Integer[]{tableId}, fieldList);
        }


//        LoanApplyTemplate templateList = loanModule.getLoanApplyProvider().getAuditTemplateField(tableId);
        JSONObject root = new JSONObject();
        JSONArray tja = new JSONArray();
        if (CollectionUtils.isNotEmpty(templateList)){
            AutoBaseTemplate temp = templateList.get(0);
            //审批，监控等查询自定义表单的数据都是从审批追记录查询，如果需要查询某个人的审批记录单独处理
            DataTable table = loanModule.getLoanApprovalProvider().getApprovalDataTableByLoanId(Integer.valueOf(loanId));
            DataRow row = null;
            if(table!=null && table.rowSize()>0){
                row = table.getRow(0);
            } else {
                DataTable applyInfoData = loanModule.getLoanApplyProvider().getLoanTemplateDataById(LoanProcessTypeEnum.INVESTIGATE.type,"LBIZ_SURVEY_RESULT",  Integer.valueOf(loanId));
                //如果是第一次审批，查出调查结论，设置审批默认值。
                DataRow sourceRow = applyInfoData.getRows()[0];
                row = table.newRow();
                row.set("LOAN_MODE", sourceRow.get("LOAN_MODE"));
                row.set("REPAYMENT_MODE", sourceRow.get("REPAYMENT_MODE"));
                row.set("RESULT_LIMIT",sourceRow.get("PROPOSAL_LIMIT"));
                row.set("RESULT_AMOUNT",sourceRow.get("PROPOSAL_AMOUNT"));
                row.set("RESULT_RATIO",sourceRow.get("PROPOSAL_RATIO"));
                row.set("REPAYMENT_DATE",sourceRow.get("REPAYMENT_DATE"));
                row.set("GUARANTEE_MODE",sourceRow.get("GUARANTEE_MODE"));
                row.set("PRODUCT_TYPE",sourceRow.get("PRODUCT_TYPE"));
            }

            JSONObject tjo = new JSONObject();
            tjo.put("tableId", temp.getId());
            tjo.put("name", temp.getName());
            tjo.put("tableName",temp.getTableName());
            tjo.put("type", temp.getType());

            if(temp.getFields()!=null){
                JSONArray fja = new JSONArray();
                int i = 0;
                for(AutoBaseField field : temp.getFields()){
                    if("LBIZ_APPROVAL_RESOLUTION".equalsIgnoreCase(temp.getTableName()) && i == 0){
                        LoanApplyInfo loanApplyInfo = loanModule.getLoanApplyProvider().getApplyInfoById(loanId);
                        if (loanApplyInfo!=null && LoanProcessTypeEnum.APPROVAL.type.equals(loanApplyInfo.getLoanProcessType())){
                            JSONObject rjo = new JSONObject();
                            rjo.put("field", "repayPlan");
                            rjo.put("name", "还款计划");
                            rjo.put("type", "RepayPlan");
                            fja.add(rjo);
                        }
                        i++;
                    }
                    if(field.getIsAppShow()){
                        JSONObject fjo = new JSONObject();
                        fjo.put("field", field.getField());
                        fjo.put("name", field.getName());
                        fjo.put("type", field.getType());
                        fjo.put("editable",editable);
                        fjo.put("column", field.getColumn());
                        Object val = getFieldValue(field,row);
                        fjo.put("value", val);
                        fjo.put("column", field.getColumn());
                        if(field.getInputLength()>0)
                            fjo.put("length", field.getInputLength());
                        if(StringUtil.isNotEmpty(field.getUnit()))
                            fjo.put("unit", field.getUnit());
                        if(field.getNullable()!=null && field.getNullable().equals(true))
                            fjo.put("required", true);
                        if(field.getOptions()!=null){
                            JSONArray oja = new JSONArray();
                            for(AutoBaseOption option : field.getOptions()){
                                JSONObject joo = new JSONObject();
                                joo.put("value", option.getValue());
                                joo.put("name", option.getName());
                                oja.add(joo);
                            }
                            fjo.put("options", oja);
                        }
                        fja.add(fjo);

                    }
                }
                tjo.put("fields", fja);
            }
            tja.add(tjo);
        }else{
            tja.add(new JSONObject());
        }

        root.put("templates", tja);

        setSuccess(root);

        return root.toString();
    }

    private Object getFieldValue(AutoBaseField field, DataRow dataRow) {
        if(dataRow!=null){
            Object val = dataRow.get(field.getColumn());
            if("Date".equals(field.getType())&&null!= val){
                val = new SimpleDateFormat(DateFormatConst.DEFAULT_DATE_FORMAT).format( val);
            }else if(null== val){
                val = field.getDefaultValue();
            }
            return val;
        }
        return null;
    }

    private void setSuccess(JSONObject resultJson){
        resultJson.put("code", CodeEnum.CODE_100.getCode());
        resultJson.put("msg", CodeEnum.CODE_100.getMsg());
    }

}
