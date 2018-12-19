package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.StringUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.ModeConfigFile;
import banger.domain.enumerate.LoanExamineCode;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.*;
import banger.framework.collection.DataColumn;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ICustomerBlackProvider;
import banger.service.intf.*;
import banger.util.LoanExcelUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款申请
 * @author zhusw
 *
 */
@RequestMapping("/loanInvestigate")
@Controller
public class LoanInvestigateController extends BaseController {
	private static final long serialVersionUID = -7305734846109770428L;

	@Autowired
	private IApplyInfoService applyInfoService;
	@Autowired
	private ILoanExamineService loanExamineService;
	@Resource
	private ICustomerBlackProvider customerBlackProvider;
	@Resource
	private IConfigModule configModule;
	@Resource
	private ITypeService typeService;
	@Resource
	private IRepayPlanInfoService repayPlanInfoService;
	@Autowired
	private IScoreDetailInfoService scoreDetailInfoService;

	/**
	 * 调查报告
	 * @param loanId
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getInvestigateReport")
	public String getInvestigateTabs(@RequestParam(value = "loanId", defaultValue = "") String loanId,@RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId){
		String configIdStr = "";
		if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId) &&
				StringUtils.isNotBlank(loanTypeId) && StringUtils.isNumeric(loanTypeId)) {
			LoanType loanType = typeService.getTypeById(Integer.parseInt(loanTypeId));
			LoanApplyInfo_Web_Query loanApplyInfo = applyInfoService.queryOneSubInfo(Integer.parseInt(loanId));
//			LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
			setAttribute("loanApplyInfo", loanApplyInfo);

			if (loanType != null && loanType.getModeConfigId() != null && loanType.getModeConfigId() != 0) {
				Integer configId = loanType.getModeConfigId();
				configIdStr = String.valueOf(configId);
				ModeConfigFile configFile = configModule.getConfigFileProvider().getConfigFileById(configId);
				if (configFile != null) {
					String[] tableNames = LoanExcelUtil.getTableNames(configFile);
					List<AutoBaseTemplate> autoBaseTemplates = configModule.getAutoTemplateProvider().getTemplateListByTables(tableNames, true);
					for(AutoBaseTemplate template : autoBaseTemplates){
						String tableName = template.getTableName();
						String type = template.getType();
						DataTable datatable;
						if ("LOAN".equals(template.getModule()))
							datatable = applyInfoService.getDataTableByLoanId(tableName, LoanProcessTypeEnum.INVESTIGATE.type, Integer.valueOf(loanId));
						else
							datatable = applyInfoService.getDataTableByLoanId(tableName, null, Integer.valueOf(loanId));
						List<AutoBaseField> autoBaseFields = template.getFields();
						if (TableInputType.FORMS.type.equals(type)) {
							Map<String, Object> map = LoanExcelUtil.getOneMap(datatable, autoBaseFields);
							setAttribute(tableName, map);
						} else {
							List<Map<String, Object>> maps = LoanExcelUtil.getListMaps(datatable, autoBaseFields);
							for (int i = 0; i < maps.size() && i < 5; i++) {
								setAttribute(tableName + (i + 1), maps.get(i));
							}
						}
					}
				} else {
					return  "/loan/vm/investigate/nodata";
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("loanId", loanId);
				List<LoanScoreDetailInfo> scoreInfos = scoreDetailInfoService.queryScoreDetailInfoList(params);
				for (LoanScoreDetailInfo scoreInfo : scoreInfos){
					setAttribute(scoreInfo.getFieldColumn(), scoreInfo.getFieldScore());
				}
				setAttribute("loanId", loanId);
			} else {
				return  "/loan/vm/investigate/nodata";
			}
		} else {
			return  "/loan/vm/investigate/nodata";
		}
		setAttribute("velocityFilePath","/loan/vm/investigate/investigateReport" + configIdStr+".vm");
		return  "/loan/vm/investigate/investigateReport";
	}




	/**
	 * 保存贷款申请信息
	 * @param json 自定义表单信息
	 * @return
	 */
	@RequestMapping(value = "/saveLoanApply", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void saveLoanApplyInfo(@RequestParam(value = "json", defaultValue = "") String json,
								  @RequestParam(value = "id", defaultValue = "") String id,
								  @RequestParam(value = "deleteIds", defaultValue = "") String deleteIds){
		try {
			if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
				Integer loginUserId = getLoginInfo().getUserId();
				Map<String, Object> map = JsonTools.parseJSON2Map(json);
				if (map != null && map.size() > 0) {
					Map<String, Object> deleteIdsMap = JsonTools.parseJSON2Map(deleteIds);
					applyInfoService.deleteLoanTemplateByMap(deleteIdsMap);
					String resultId = applyInfoService.saveLoanInvestigateInfo(map, id, loginUserId);
					renderText(true, StringUtils.isBlank(resultId) ? "保存失败" : "保存成功", String.valueOf(resultId));
				} else {
					renderText(false,"参数错误","json");
				}

			} else {
				renderText(false,"参数错误","json");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存贷款申请信息异常|"+json,e);
			renderText(false,"保存失败",String.valueOf(""));
		}

	}

	/**
	 *是否租凭  为是  租凭 开始和结束时间不能为空
	 */
	private boolean isLeaseOk(Integer loanId){
		AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
		DataTable dataTable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, null);

		PledgInfo pledgInfo = new PledgInfo();
		for (DataRow dataRow:dataTable.getRows()){
			String isLease = null;
			String leaseBegin = null;
			String leaseEnd = null;
			for (DataColumn dataColumn:dataRow.getTable().getColumns()){
				if ("IS_LEASE".equals(dataColumn.getName())){
					isLease=dataColumn.getValues()[dataRow.getIndex()]==null?null:dataColumn.getValues()[dataRow.getIndex()].toString();
				}else if ("LEASE_BEGIN".equals(dataColumn.getName())){
					leaseBegin=dataColumn.getValues()[dataRow.getIndex()]==null?null:dataColumn.getValues()[dataRow.getIndex()].toString();
				}else if ("LEASE_END".equals(dataColumn.getName())){
					leaseEnd=dataColumn.getValues()[dataRow.getIndex()]==null?null:dataColumn.getValues()[dataRow.getIndex()].toString();
				}
			}
			if (StringUtil.isNotEmpty(isLease)&&"01".equals(isLease)){
				//如果 是否租凭不为空  租凭起始到期日期不能为空
				if (StringUtil.isEmpty(leaseBegin)||StringUtil.isEmpty(leaseEnd)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 提交审核
	 * @param loanId 贷款id
	 * @return
	 */
	@RequestMapping(value = "/saveApproveInfo", method = RequestMethod.POST)
	@ResponseBody
	public void saveApproveInfo(@RequestParam(value = "loanId", defaultValue = "") String loanId){
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
				//如果被加入了黑名单，直接返回
				boolean isBlack = customerBlackProvider.isExistCustomerBlack(Integer.parseInt(loanId));
				if(isBlack) {
					renderJson(false, "客户贷款受限", json);
				} else {
					//抵质押物 是否租凭 为是 时  租期日期 租凭到期日期 必填
					if (!isLeaseOk(Integer.valueOf(loanId))){
						renderJson(false, "抵质押物是否租凭为是，需填写租凭开始结束日期", json);
						return;
					}
					LoanExamine loanExamine = loanExamineService.getLoanExamine(Integer.parseInt(loanId));
					if (LoanExamineCode.PASS.code.equals(loanExamine.getExamineCode().code)) {
						json.put("processId", loanExamine.getProcessId());
						json.put("flowId", loanExamine.getFlowId());
						json.put("paramId", loanExamine.getParamId());
						json.put("random", loanExamine.isRandom());
						json.put("reviews", LoanUtil.getJsonByLoanExamine(loanExamine.getReviews()));
						renderJson(true, "", json);
					} else {
						renderJson(false, loanExamine.getExamineCode().message, json);
					}
				}
			} else {
				renderJson(false, "页面参数错误", json);
			}
		} catch (Exception e) {
			log.error("保存贷款申请信息异常|"+json,e);
			renderJson(false, "业务异常", json);
		}

	}
}
