package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.StringUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.config.ModeConfigFile;
import banger.domain.customer.CustPotentialCustomers;
import banger.domain.enumerate.*;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.domain.loan.LoanType;
import banger.domain.loan.PledgInfo;
import banger.framework.collection.DataColumn;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.DateUtil;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.*;
import banger.service.intf.*;
import banger.socket.SocketDemo;
import banger.util.CodedProduceUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款申请
 * @author zhusw
 *
 */
@RequestMapping("/loanLoanMoney")
@Controller
public class LoanLoanMoneyController extends BaseController {
	private static final long serialVersionUID = -7305734846109770428L;

	@Autowired
	private IApplyInfoService applyInfoService;
	@Resource
	private ICustomerBlackProvider customerBlackProvider;
	@Autowired
	private ILoanApprovalService loanApprovalService;
	@Autowired
	private ICurrentAuditStatusService currentAuditStatusService;
	@Autowired
	private IPotentialCustomersProvider potentialCustomersProvider;
	@Autowired
	private ITypeService typeService;
	@Autowired
	private IConfigModule configModule;
	@Autowired
	private IRepayPlanInfoService repayPlanInfoService;
	@Autowired
	private ITableInfoProvider tableInfoProvider;
	@Autowired
	private IAutoFieldProvider autoFieldProvider;
	@Autowired
	private IScoreDetailInfoService scoreDetailInfoService;

	@Autowired
	private ILoanOperationService loanOperationService;

	@Autowired
	private IPermissionModule permissionModule;

	@Resource
	private ILoanTaskProvider loanTaskProvider;
	@Resource
	ILoanApplyService loanApplyService;

	@Resource
	ITrustedPaymentService trustedPaymentService;
	@Autowired
	private SocketDemo socketDemo;
	private String basePath = "/loan/vm/loan/";

	/**
	 * 保存贷款放款信息
	 * @param json 自定义表单信息
	 * @return
	 */
	@RequestMapping(value = "/loanMoneySave", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void loanMoneySave(@RequestParam(value = "json", defaultValue = "") String json,
								  @RequestParam(value = "id", defaultValue = "") String id,
								  @RequestParam(value = "deleteIds", defaultValue = "") String deleteIds){
		try {
			if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
				boolean isBlack = customerBlackProvider.isExistCustomerBlack(Integer.parseInt(id));
				if (isBlack) {
					renderText(false,"客户贷款受限",id);
				} else {
					Map<String, Object> map = JsonTools.parseJSON2Map(json);
					String checkPlans = repayPlanInfoService.checkPlans(Integer.parseInt(id), map);
					if(StringUtils.isNotBlank(checkPlans)) {
						renderText(false, checkPlans, "");
					} else {
						Integer loginUserId = getLoginInfo().getUserId();

						if (map != null && map.size() > 0) {
							Map<String, Object> deleteIdsMap = JsonTools.parseJSON2Map(deleteIds);
							applyInfoService.deleteLoanTemplateByMap(deleteIdsMap);
							String resultId = applyInfoService.saveLoanLoanMoneyInfo(map, id, loginUserId, false);
							if(StringUtils.isNotBlank(resultId))
							{
								//从贷款申请表中 找到潜在客户id
								LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.parseInt(id));

								if(applyInfo.getPotentialCustomerId()!=0)
								{
									CustPotentialCustomers custPotentialCustomers = new CustPotentialCustomers();
									custPotentialCustomers.setId(applyInfo.getPotentialCustomerId());
									custPotentialCustomers.setMarketingSuccess(1);
									custPotentialCustomers.setLoanId(Integer.parseInt(id));
									potentialCustomersProvider.updatePotentialCustomers(custPotentialCustomers,loginUserId);
								}
							}
							renderText(true, StringUtils.isBlank(resultId) ? "保存失败" : "保存成功", String.valueOf(resultId));
						} else {
							renderText(false,"参数错误","json");
						}
					}
//					renderText(false,"参数错误","json");
				}
			} else {
				renderText(false,"参数错误","json");
			}
		} catch (Exception e) {
			log.error("保存贷款返款信息异常|"+json,e);
			renderText(false,"保存失败",String.valueOf(""));
		}

	}

	/**
	 * 判断抵押物 有没有补录
	 * @param loanId
	 * @return
	 */
	private String isPledgIsOk(Integer loanId){
		AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
		DataTable dataTable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 1);

		PledgInfo pledgInfo = new PledgInfo();
		for (final DataRow dataRow:dataTable.getRows()){
			for (final DataColumn dataColumn:dataRow.getTable().getColumns()){
				if ("ASSURANCE_NO".equals(dataColumn.getName())){
					if (dataColumn.getValues()[dataRow.getIndex()]==null||StringUtil.isEmpty(dataColumn.getValues()[dataRow.getIndex()].toString())){
						//投保编号为空 提醒补录
						return "请先补录抵押物信息";
					}
				}else if ("PLEDGE_STATUS".equals(dataColumn.getName())){
					if (dataColumn.getValues()[dataRow.getIndex()]==null||(!"1".equals(dataColumn.getValues()[dataRow.getIndex()].toString())&&!"3".equals(dataColumn.getValues()[dataRow.getIndex()].toString()))){
						//投保编号为空 提醒补录
						return "请先进行抵质押物入库";
					}
				}
			}
		}
		for (final DataRow dataRow:dataTable.getRows()){
			Integer id = null;
			for (final DataColumn dataColumn:dataRow.getTable().getColumns()){
				if("ID".equals(dataColumn.getName())){
					id = (Integer)dataColumn.getValues()[dataRow.getIndex()];
				}
				if("WARRANTY_NUM".equals(dataColumn.getName())){
					final String gurId = (String)dataColumn.getValues()[dataRow.getIndex()];
					if(StringUtil.isNotEmpty(gurId)){
						Map<String,String> map = new HashMap<String, String>(){{put("核心查询交易码",TradeCodeEnum.CODE_600052.tradeCode);put("押品编号",gurId);}};
						String returnStr = socketDemo.relatedDataQuery(loanId,map,SocketCodeTypeEnum.CHNCMI4036);
						JSONObject jsonObject = JSONObject.fromObject(returnStr);
						if(!jsonObject.getBoolean("success")){
							return "抵押物在核心未入库："+jsonObject.getString("rep_msg");
						}
						applyInfoService.updatePledgeStatusById(id,"3");
					}
				}
			}
		}
		return "success";
	}
	/**
	 * 放款
	 * @return
	 */
	@RequestMapping(value = "/saveLoanApply", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void saveLoanApply(@RequestParam(value = "id", defaultValue = "") String id){
		try {
			if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
				boolean isBlack = customerBlackProvider.isExistCustomerBlack(Integer.parseInt(id));
				if (isBlack) {
					renderText(false,"客户贷款受限",id);
				} else {
							LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.parseInt(id));
							//放款时 如果抵押物有填  先进行抵押物补录
							String result =isPledgIsOk(Integer.valueOf(id));
							if (!"success".equals(result)){
								renderText(false,result,"");
								return;
							}
							//放款时 如果有抵质押物 必须等核心入库后才能放款


							Integer loginUserId = getLoginInfo().getUserId();
							//设置受托支付支付ID和支付状态
							trustedPaymentService.setPaymentIDAndPaymentStatus(Integer.parseInt(id));
							//从贷款申请表中 找到潜在客户id

							//更新放款之后的各种参数
							updateLoanInfo(applyInfo,loginUserId,id);
							applyInfo.setLoanProcessType(LoanProcessTypeEnum.AUTHORIZED.type);
							applyInfo.setAuthorizationCode(CodedProduceUtil.getAuthorizedSerialCode(true));
							applyInfo.setIouNum(CodedProduceUtil.getCode(OperationCode.CODE_200.getCode(), true));
							applyInfoService.updateApplyInfo(applyInfo,loginUserId);
							// 记录操作日志
							loanOperationService.addLoanOperation(Integer.parseInt(id), LoanOperationType.LOAN_LOAN.typeName, "", loginUserId, LoanProcessTypeEnum.LOAN.type);
							if(applyInfo.getPotentialCustomerId()!=0)
							{
								CustPotentialCustomers custPotentialCustomers = new CustPotentialCustomers();
								custPotentialCustomers.setId(applyInfo.getPotentialCustomerId());
								custPotentialCustomers.setMarketingSuccess(1);
								custPotentialCustomers.setLoanId(Integer.parseInt(id));
								potentialCustomersProvider.updatePotentialCustomers(custPotentialCustomers,loginUserId);
							}
								renderText(true, "放款成功", String.valueOf(""));
					}
			} else {
				renderText(false,"参数错误","json");
			}
		} catch (Exception e) {
			renderText(false,"放款失败",String.valueOf(""));
		}
	}

	//更新主表中的 放款起始日期 放款金额 放款人用户ID
	private void updateLoanInfo(LoanApplyInfo applyInfo,Integer loginUserId,String id){
		Map<String,String> map = new HashMap<String, String>();
		AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_LOAN_GRANT"}).get(0);
		DataTable datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), LoanProcessTypeEnum.LOAN.type, Integer.parseInt(id));
		if(datatable!=null && datatable.rowSize()>0){
			for (int r = 0; r < datatable.getRows().length; r++) {
				template.setData(datatable.getRows()[r]);
				List<AutoBaseField> fieldList = template.getFields();
				for (int i = 0; i < fieldList.size(); i++) {
					AutoFieldWrapper field = (AutoFieldWrapper) fieldList.get(i);
					Object value = "";
					if (null != template.getData()) {
						value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
					}
					String key = template.getName() + "." + field.getFieldName();
					map.put(key, null != value ? value.toString() : "");
				}
			}
		}
		if (StringUtils.isNotBlank(org.apache.commons.collections4.MapUtils.getString(map, "放贷信息.放款起始日期")) && StringUtils.isNotBlank(org.apache.commons.collections4.MapUtils.getString(map, "放贷信息.放款金额"))) {
			applyInfo.setLoanCreditDate(DateUtil.parser(org.apache.commons.collections4.MapUtils.getString(map, "放贷信息.放款起始日期"), "yyyy-MM-dd"));//贷款放款日期
			applyInfo.setLoanCreditAmount(BigDecimal.valueOf(Double.valueOf(org.apache.commons.collections4.MapUtils.getString(map, "放贷信息.放款金额"))));//贷款放款金额
		}
		applyInfo.setLoanCreditUserId(loginUserId);//贷款放款人ID

	}
	/**
	 * 授权信息同步页面
	 * @return
	 */
	@RequestMapping("getAuthorizationCheckPage")
	public String getAuthorizationCheckPage(){
		String loanId = getRequest().getParameter("loanId");
		setAttribute("loanId",loanId);
		return "/loan/vm/pub/authorizationConfirmCheck";
	}

	/**
	 * 授权确认
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/authorizationConfirm", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void authorizationConfirm(@RequestParam(value = "id", defaultValue = "") String id){
		try {
			if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			    String result = socketDemo.syncLoanAuthorizedInfo(Integer.parseInt(id));
				if("success".equals(result)){
					Integer loginUserId = getLoginInfo().getUserId();
					LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.parseInt(id));
					//更新贷款状态，添加操作日志
					applyInfo.setSyncStatus(2);
					applyInfo.setLoanProcessType(LoanProcessTypeEnum.UNDISCLOSED.type);
//					applyInfo.setLoanProcessType(LoanProcessTypeEnum.AFTER_LOAN.type);
					applyInfo.setLoanAfterState(AfterLoanTypeEnum.NORMAL.code);
					Integer investigateUserId = applyInfo.getLoanInvestigationUserId();
					if (investigateUserId != null) {
						Integer teamGroupId=permissionModule.getGroupIdByUserId(investigateUserId);
						applyInfo.setLoanAfterGroupId(teamGroupId);
					}
					applyInfoService.updateApplyInfo(applyInfo,loginUserId);
					loanApplyService.creatRepayPlanInfo(Integer.parseInt(id), loginUserId);
					loanTaskProvider.updateLoanTaskAmount(Integer.parseInt(id));
					// 记录操作日志
					loanOperationService.addLoanOperation(Integer.parseInt(id), LoanOperationType.LOAN_AUTHORIZATION_CONFIRM.typeName, "", loginUserId, LoanProcessTypeEnum.AUTHORIZED.type);
					applyInfoService.sendLoanMsg(applyInfo, new String[]{String.valueOf(loginUserId)});

					renderText(true, "授权确认成功", String.valueOf(""));
				}else{
                    renderText(false, result, id);
					return;
				}
			} else {
				renderText(false,"参数错误","id");
			}
		} catch (Exception e) {
			renderText(false,"授权确认失败",String.valueOf(""));
		}
	}


	/**
	 * 授权信息同步页面
	 * @return
	 */
	@RequestMapping("getAuthorizationCancelCheckPage")
	public String getAuthorizationCancelCheckPage(){
		String loanId = getRequest().getParameter("loanId");
		setAttribute("loanId",loanId);
		return "/loan/vm/pub/authorizationCancelCheck";
	}
	/**
	 * 授权取消
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/authorizationCancel", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void authorizationCancel(@RequestParam(value = "id", defaultValue = "") String id){
		try {
			if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
//                Map<String,Object> result = new HashMap<String, Object>();
//			    result = socketDemo.selectLoanAccountInfo(Integer.parseInt(id));
//				if("success".equals(result.get("code"))) {
//					renderText(false, "贷款已到账不能进行授权取消", id);
//					return;
//				}
				LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.parseInt(id));
				if(!StringUtil.isNullOrEmpty(applyInfo.getLoanAccountNo())){
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("贷款帐号",applyInfo.getLoanAccountNo());
					String returnStr = socketDemo.queryCusInfo(applyInfo.getLoanId(), map, SocketCodeTypeEnum.QRY10400);
					JSONObject jsonObject = JSONObject.fromObject(returnStr);
					if(jsonObject.getBoolean("success")){
						if("全额发放".equals(jsonObject.getString("lon_state").trim())){
							renderText(false,"核心已放款，不允许撤销！",id);
							return;
						}
					}
				}

                String resultstr = socketDemo.cancelLoanAccountInfo(Integer.parseInt(id));
				if(resultstr.contains("核心已发起撤销交易，请稍后查看授权状态！") || resultstr.contains("success")||resultstr.contains("此操作会导致该笔借据对应的合同可用金额大于合同金额")){
					Integer loginUserId = getLoginInfo().getUserId();
//					LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.parseInt(id));
					//更新贷款状态，添加操作日志
					applyInfo.setSyncStatus(-2);
					applyInfo.setLoanAccountNo("");
					applyInfo.setLoanProcessType(LoanProcessTypeEnum.LOAN.type);
					//贷款账号设置为空
					applyInfo.setLoanAccountNo(null);
					loanApplyService.updateApplyInfo(applyInfo, this.getLoginInfo().getUserId());
					loanOperationService.addLoanOperation(Integer.valueOf(id), LoanOperationType.LOAN_AUTHORIZATION_BACK.typeName, "", loginUserId, LoanProcessTypeEnum.AFTER_LOAN.type);
					renderText(true, resultstr.contains("核心已发起撤销交易，请稍后查看授权状态！")?"核心已发起撤销交易，请稍后查看授权状态！":"授权取消成功", String.valueOf(""));
					//受托支付重新同步
					resetTrustedPayment(Integer.parseInt(id));

				}else{
					renderText(false,resultstr,id);
					return;
				}
			} else {
				renderText(false,"参数错误","id");
			}
		} catch (Exception e) {
			renderText(false,"授权取消失败",String.valueOf(""));
		}
	}

	private List<AutoBaseField> setPaymentIdAndStatus(List<AutoBaseField> autoBaseFields){
		AutoBaseField autoBaseField = new AutoFieldWrapper(3101,"paymentId","PAYMENT_ID","支付ID号","Text","",true,150,true,true,"paymentId",null,null);
		AutoBaseField autoBaseField1 = new AutoFieldWrapper(3102,"paymentStatus","PAYMENT_STATUS","支付状态","Text","",true,150,true,true,"paymentStatus",null,null);
		AutoBaseField autoBaseField2 = new AutoFieldWrapper(0,"id","ID","主键","Text","",true,150,true,true,"id",null,null);
		autoBaseFields.add(autoBaseField);
		autoBaseFields.add(autoBaseField1);
		autoBaseFields.add(autoBaseField2);
		return autoBaseFields;
	}

	private void resetTrustedPayment(Integer loanId) {

		try {

			//查询已同步的受托支付列表  0待同步 1已同步 2支付成功  3支付失败
			AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_TRUSTED_PAYMENY"}).get(0);
			DataTable datatable = applyInfoService.getTrustedDataTableByLoanId(LoanProcessTypeEnum.LOAN.type, loanId, 1);
			if(datatable==null || datatable.rowSize()<=0){
				return ;
			}
			for (int r = 0; r < datatable.getRows().length; r++) {
				template.setData(datatable.getRows()[r]);
				List<AutoBaseField> fieldList = template.getFields();
				//追加支付ID和支付状态
				setPaymentIdAndStatus(fieldList);
				String id = "0";
				for (int i = 0; i < fieldList.size(); i++) {
					AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
					Object value = "";
					if(null!=value&&field.getColumn().equals("ID")){
						if(null!=template.getData()){
							value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
						}
						id = null!=value?value.toString():"";
					}

				}

				// 更新受托支付状态为待同步  支付状态  0待同步 1已同步 2支付成功  3支付失败
				applyInfoService.updateTrustedDataTableById(LoanProcessTypeEnum.LOAN.type, id, 0);

			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}


	}

	/**
	 * 更新受托支付同步状态
	 */
	@RequestMapping(value = "/trustedPaymentInfoSync", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void trustedPaymentInfoSync(@RequestParam(value = "loanId", defaultValue = "") String loanId){
		try {
			if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
				resetTrustedPayment(Integer.parseInt(loanId));
				renderText(true,"操作成功","loanId");
			} else {
				renderText(false,"参数错误","id");
			}
		} catch (Exception e) {
			renderText(false,"操作失败",String.valueOf(""));
		}
	}


	/**
	 * 授权阶段 退回
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/authorizationBack", method = RequestMethod.POST)
	@ResponseBody
	@SqlTransaction
	public void authorizationBack(@RequestParam(value = "id", defaultValue = "") String id){
		try {
			if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
				Integer loginUserId = getLoginInfo().getUserId();
				LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.parseInt(id));
				//更新贷款状态，添加操作日志
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.LOAN.type);
				loanApplyService.updateApplyInfo(applyInfo, this.getLoginInfo().getUserId());
				loanOperationService.addLoanOperation(Integer.valueOf(id), LoanOperationType.LOAN_AUTHORIZATION_CANCELLATION.typeName, "", loginUserId, LoanProcessTypeEnum.AFTER_LOAN.type);
				renderText(true, "退回成功", String.valueOf(""));
//				}else{
//					renderText(false,"授权取消失败",String.valueOf(""));
//				}
			} else {
				renderText(false,"参数错误","id");
			}
		} catch (Exception e) {
			renderText(false,"退回失败",String.valueOf(""));
		}
	}

	/**
	 * 根据卡号/存款帐号查询客户信息
	 * @return
	 */
	@RequestMapping("checkCustomerInfoData")
	@ResponseBody
	@NoLoginInterceptor
	public String checkCusInfoData(@RequestParam(defaultValue ="0" ,value="accNo")  String accNo,
								   @RequestParam(defaultValue ="0" ,value="loanId")  String loanId){
		if(!StringUtil.isNullOrEmpty(accNo) && !StringUtil.isNullOrEmpty(loanId)){
			Map<String,String> map = new HashMap<String, String>();
			map.put("账户",accNo);
			String returnMsg = socketDemo.queryCusInfo(Integer.parseInt(loanId),map, SocketCodeTypeEnum.QRY00400);
			return returnMsg;
		}else{
			return null;
		}
	}

	/**
	 * 得到各个列表页面
	 * @return
	 */
	@RequestMapping("/printLoan")
	public String printLoan(@RequestParam(value = "loanId", defaultValue = "") String loanId,
							@RequestParam(value = "type", defaultValue = "") String type) {
		if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
			LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.valueOf(loanId));
			if (applyInfo != null) {
				Integer loanClassId=applyInfo.getLoanClassId();
				setAttribute("loanClassId",loanClassId);
				setAttribute("loanId", loanId);
				setAttribute("loanTypeId", applyInfo.getLoanTypeId());
			}
		}
		if ("threeTable".equals(type))
			return  basePath + "printThreeTable";
		else
			return basePath + "printLoanInfo";
	}

	/**
	 * 下载贷款资料
	 * @return
	 */
	@RequestMapping("/downloadFiles")
	public void downloadFiles(HttpServletRequest request, HttpServletResponse response,
							  @RequestParam(value = "loanId", defaultValue = "") String loanId) throws IOException {
		if (StringUtils.isBlank(loanId)) {
			renderText("缺少参数");
		} else {
			try {
				LoanApplyInfo_Web_Query loanApplyInfo = applyInfoService.queryOneSubInfo(Integer.parseInt(loanId));
//				LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
				Integer loanTypeId = loanApplyInfo.getLoanTypeId();
				LoanType loanType = typeService.getTypeById(loanTypeId);
				if (loanType != null && loanType.getModeConfigId() != null && loanType.getModeConfigId() != 0) {
					Integer configId = loanType.getModeConfigId();
					ModeConfigFile configFile = configModule.getConfigFileProvider().getConfigFileById(configId);
					loanApprovalService.doExportFromRow(request, response,applyInfoService, configModule, tableInfoProvider, autoFieldProvider, scoreDetailInfoService, loanApplyInfo, configFile);
				} else {
					throw new Exception("没有配置调查报告");
				}
			} catch (Exception e) {
				//e.printStackTrace();
				if (e != null)
					renderText(e.getMessage());
				else
					e.printStackTrace();
			}
		}
	}
}
