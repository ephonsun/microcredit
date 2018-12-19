package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.annotation.TokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.ExcelUtil;
import banger.common.tools.JsonUtil;
import banger.common.tools.StringUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.customer.CustBasicInfo;
import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.enumerate.LoanCollectionState;
import banger.domain.enumerate.LoanOperationType;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.TableSyncEnum;
import banger.domain.html5.IntoCustApplyInfo;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.domain.loan.*;
import banger.domain.loan.trusted.TrustedPayment;
import banger.domain.permission.ILoginInfo;
import banger.domain.permission.PmsUser;
import banger.domain.permission.SysTeamGroup;
import banger.domain.permission.SysTeamMember_Query;
import banger.domain.system.SysBasicConfig;
import banger.domain.system.SysDataDictCol;
import banger.framework.collection.DataColumn;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.PageList;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.DateUtil;
import banger.moduleIntf.*;
import banger.service.intf.*;
import banger.socket.SocketDemo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 页面访问类
 */
@Controller
@RequestMapping("/loan")
public class LoanController extends BaseController {

	private static final long serialVersionUID = -6147758088303481356L;
	@Resource
	IConfigModule configModule;
	@Resource
	IFormulaModule formulaModule;

	@Resource
	ILoanApplyService loanApplyService;
	@Autowired
	private IApplyInfoService applyInfoService;

	@Autowired
	private ITrustedPaymentService trustedPaymentService;
	@Autowired
	private IBasicInfoProvider basicInfoService;
	@Autowired
	private IPermissionModule permissionModule;
	@Resource
	private ICustomerMarketProvider customerMarketProvider;
	@Resource
	private IIntoCustomersProvider intoCustomersProvider;
	@Resource
	private ICustomerModuleIntf customerModuleIntf;
	@Resource
	private ILoanModelScoreService iLoanModelScoreService;
	@Autowired
	private ICurrentAuditStatusService currentAuditStatusService;
	//添加潜在客户逻辑层
	@Autowired
	private IPotentialCustomersProvider iPotentialCustomersProvider;

	@Autowired
	private IBasicInfoProvider basicInfoProvider;

	@Autowired
	private IBasicConfigProvider basicConfigProvider;
	@Autowired
	private IMapTaggingProvider mapTaggingProvider;
	@Autowired
	private ILoanOperationService loanOperationService;
	@Autowired
	private ITypeService typeService;
	@Autowired
	private SocketDemo socketDemo;
	@Autowired
	private IDataDictColService iDataDictColService;

	@Autowired
	private IFormSettingsProvider formSettingsProvider;

	@Value("${loan.three.table}")
	private String threeTable;

	//是否是自动催收
	@Value("${loan.collection}")
	private String collection;

	private String basePath = "/loan/vm/";
	private Map<String, String> moduleMapForList = null;


	/**
	 * 得到各个列表页面
	 * @return
	 */
	@RequestMapping("/getLoanlistPage")
	public String getLoanApplyListPage(@RequestParam(value = "module", defaultValue = "") String module){

//		socketDemo.cancelContractInfo(21); //测试
		//工作台跳转参数
		String montiorState = getParameter("montiorState");
		if(montiorState != null){
			setAttribute("montiorState","Monitor");
		}
		String collectionState = getParameter("collectionState");
		if(collectionState != null){
			setAttribute("collectionState","Collection");
		}
//		String contract = getParameter("contract");
//		if(contract != null){
//			setAttribute("contract",contract);
//		}

		setAttribute("module", module);
		//贷款催收拆分
		if ("collection".equalsIgnoreCase(module)) {
			setAttribute("collectionType",getParameter("collectionType"));
		}
		if ("all".equals(module)) {
			ILoginInfo loginInfo = getLoginInfo();
			String groupIds = loginInfo.getTeamGroupIdByRole();
			if (StringUtils.isNotBlank(groupIds)) {
				List<SysTeamGroup> teamGroups = permissionModule.queryGroupListByGroupIds(groupIds);
				setAttribute("teamGroups", teamGroups);
			} else {
				setAttribute("noTeam", "1");
			}
		} else if ("allot".equals(module)) {	//待分配列表列出本团队下的客户经理
			ILoginInfo loginInfo = getLoginInfo();
			List<SysTeamMember_Query> members = new ArrayList<SysTeamMember_Query>();
			if (this.isCustomerManager()) {
				SysTeamMember_Query member_query = new SysTeamMember_Query();
				member_query.setUserName(loginInfo.getUserName());
				member_query.setUserId(loginInfo.getUserId());
				members.add(member_query);
			}

			String teamGroupIds = loginInfo.getTeamGroupIdByRole();
			if (StringUtils.isNotBlank(teamGroupIds)) {
				String[] groupIds = teamGroupIds.split(",");
				for (String groupId : groupIds) {
					Integer id = Integer.parseInt(groupId);
					List<SysTeamMember_Query> membersTeams=permissionModule.getMangerByGroupId(groupId);
					members.addAll(membersTeams);
				}
			}

			setAttribute("teamMembers",members);
		}else if("intoAllot".equals(module)){
			return  "customer/vm/intoCustApply/intoGroupCustomerList";
		}else if("credit".equals(module)){
			setAttribute("checkStatus",1);
			return  "customer/vm/credit/customerCreditCheckList";
		}else if("authorized".equals(module)){
			setAttribute("authorized","authorized");
		}
//		else if("contract".equals(contract)){
//			return  "loan/vm/contract/loanContractList";
//		}
		//工作台跳转参数
		String benchMode = getParameter("benchMode");
		setAttribute("benchMode",benchMode);
		return  basePath + "pub/loanList";
	}


	/**
	 * 检验贷款类型是否已经使用
	 */
	@RequestMapping("/checkLoanType")
	@ResponseBody
	public void checkLoanType(@RequestParam(value = "id", defaultValue = "0")Integer typeId) {
		renderText(loanApplyService.checkLoanType(typeId), "", "");
	}


	/**
	 * 检验决议金额是否小于等于申请金额
	 */
	@RequestMapping("/checkLoanMoney")
	@ResponseBody
	public void checkLoanMoney(@RequestParam(value = "loanId", defaultValue = "-1")Integer loanId,
							   @RequestParam(value = "money", defaultValue = "")String money,
								  @RequestParam(value = "type", defaultValue = "")String type) {
		LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(loanId);
		if(null!=loanApplyInfo){
			BigDecimal applyMoney = loanApplyInfo.getLoanApplyAmount();
			BigDecimal resultMoney = loanApplyInfo.getLoanResultAmount();
			BigDecimal thisMoney = new BigDecimal(money);
			if ("approval".equals(type)) {
				this.renderText(String.valueOf(applyMoney.compareTo(thisMoney) >= 0));
			} else {
				this.renderText(String.valueOf(resultMoney.compareTo(thisMoney) >= 0));
			}
		} else {
			this.renderText("false");
		}
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:校验客户编码是否唯一
	 * @Date: 16:36 2017/12/18
	 */
	@RequestMapping("/checkCustomerNum")
	@ResponseBody
	public void checkCustomerNum(@RequestParam(value = "customerId", defaultValue = "")String customerId,
								  @RequestParam(value = "customerNum", defaultValue = "")String customerNum,
								 @RequestParam(value = "loanId", defaultValue = "")String loanId) {
		CustBasicInfo custBaseInfo = null;
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("customerNum",customerNum);
		if (StringUtil.isNotEmpty(customerId) && StringUtil.isNullOrEmpty(loanId)) {
			//客户转申请校验
			condition.put("id",customerId);
			condition.put("type",1);
			custBaseInfo = customerModuleIntf.isUniqueCustomerNum(condition);
		} else if (StringUtil.isNotEmpty(loanId) && StringUtil.isNullOrEmpty(customerId)) {
			//贷款编辑校验,防止多条
			condition.put("loanId",loanId);
			condition.put("type",2);
			//此处的custInfo是LBIZ_PERSONAL_INFO映射过来的内容,
			CustBasicInfo custInfo = customerModuleIntf.isUniqueCustomerNum(condition);
			if (custInfo == null) {
				custBaseInfo = null;
			}else{
				custInfo = customerModuleIntf.getCustomerByCardNameType(custInfo.getCustomerName(), custInfo.getIdentifyType(), custInfo.getIdentifyNum());

				condition.clear();
				condition.put("id",custInfo.getId());
				condition.put("type",1);
				condition.put("customerNum",customerNum);
				custBaseInfo = customerModuleIntf.isUniqueCustomerNum(condition);
			}
		}else{
			condition.put("type",1);
			custBaseInfo = customerModuleIntf.isUniqueCustomerNum(condition);
		}


		if(custBaseInfo == null){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
	}



	/**
	 * 检验决议金额是否小于等于申请金额
	 */
	@RequestMapping("/getNextLbizId")
	@ResponseBody
	public void getNextLbizId(@RequestParam(value = "tableName", defaultValue = "")String tableName) {
		Integer lbizId = loanApplyService.newLbizId(tableName);
		if (lbizId != null)
			renderText(lbizId.toString());
		else renderText("");
	}

	/**
	 * 得到新增页面 进件客户
	 */
	@RequestMapping("/getLoanTabs")
	@TokenAnnotation
	public String getLoanTabs(@RequestParam(value = "loanId", defaultValue = "") String loanId,
							  @RequestParam(value = "module", defaultValue = "") String module,
							  @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
							  @RequestParam(value = "showApply", defaultValue = "") String showApply,
							  @RequestParam(value = "applyId", defaultValue = "") String applyId,
							  @RequestParam(value = "potentialId", defaultValue = "") String potentialId,
							  @RequestParam(value = "customerId", defaultValue = "") String customerId){


		if(StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId) ){

			Integer userId = getLoginInfo().getUserId();
			LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.parseInt(loanId));

			if(null!=loanApplyInfo){

				//========================================================================
				//数据权限校验，判断当前数据是不是属于当前等路人应该看到的数据
				Integer belongUserId = loanApplyInfo.getLoanBelongId();
				String processType = loanApplyInfo.getLoanProcessType();
				//判断是否有管理级别角色  （当审批 签订 授权阶段 不判断）
				boolean pass = false;
				if (!isBackenManager()&&!isTeamManager()&&!isMutilTeamManager()&&!isApprovalOrOther()
						&&!(processType.equals(LoanProcessTypeEnum.APPROVAL.type)||processType.equals(LoanProcessTypeEnum.SIGN.type)||processType.equals(LoanProcessTypeEnum.AUTHORIZED.type))){
					//没有任何可以跨归属查看的职能角色
					if (!belongUserId.equals(this.getLoginInfo().getUserId())) {
						return "nopermitform";
					}else{
						pass = true;
					}
				}
				processType = firstLowerCase(loanApplyInfo.getLoanProcessType());

				if(!pass){
					String checkData = permissionModule.checkDataByUserId(getLoginInfo().getTeamGroupIdByRole(false), belongUserId);
					if (StringUtils.isNotBlank(checkData)) {
						return checkData;
					}
				}
				
				//========================================================================
				if (LoanProcessTypeEnum.CONTRACT.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.SIGN.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.LOAN.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.AUTHORIZED.type.equals(loanApplyInfo.getLoanProcessType())
						||LoanProcessTypeEnum.UNDISCLOSED.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.AFTER_LOAN.type.equals(loanApplyInfo.getLoanProcessType())
						||LoanProcessTypeEnum.CLEARANCE.type.equals(loanApplyInfo.getLoanProcessType())) {
					if(LoanProcessTypeEnum.CONTRACT.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.SIGN.type.equals(loanApplyInfo.getLoanProcessType())){
							if(isBackenManager()){
								setAttribute("showDownBtn",true);
							}
					}else{
						setAttribute("showDownBtn",true);
					}
				}

				if ("refuse".equals(processType)) {
				}
				if (loanApplyInfo.getLoanClassId().intValue() == 0){
					setAttribute("threeConfig",false);
				} else {
					setAttribute("threeConfig",true);
				}
				setAttribute("processType", processType);

				loanTypeId = String.valueOf(loanApplyInfo.getLoanTypeId());
				setAttribute("loanProcessTabs",loanApplyInfo.getLoanProcessTabs());
				setAttribute("loanTypeId",loanApplyInfo.getLoanTypeId());

			} else {
				setAttribute("threeTable",threeTable);
			}
		}

		setAttribute("customerId",customerId);
		setAttribute("potentialId",potentialId);
		setAttribute("threeTable",threeTable);
		setAttribute("applyId",applyId);
		setAttribute("loanId",loanId);
		setAttribute("defaultShowPage",0);
		setAttribute("module",module);
		setAttribute("loanTypeId",loanTypeId);
		setAttribute("showApply", showApply);
		return  basePath + "pub/loanTabs";
	}

	/**
	 *  新增申请时确认按钮
	 * @param module
	 * @param loanId
	 * @param precType
	 * @param loanTypeId
	 * @param showApply
	 * @param applyId
	 * @param potentialId
	 * @return
	 */
	@RequestMapping("/getLoanTab")
	public String getLoanTab(@RequestParam(value = "module", defaultValue = "") String module,
								  @RequestParam(value = "loanId", defaultValue = "") String loanId,
								  @RequestParam(value = "precType", defaultValue = "") String precType,
								  @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
								  @RequestParam(value = "showApply", defaultValue = "") String showApply,
								  @RequestParam(value = "applyId", defaultValue = "") String applyId,
								  @RequestParam(value = "potentialId", defaultValue = "") String potentialId,
								  @RequestParam(value = "customerId", defaultValue = "") String customerId){
		setAttribute("loanId", loanId);
		setAttribute("precType", precType);
		setAttribute("module", module);
		setAttribute("loanTypeId", loanTypeId);
		setAttribute("showApply", showApply);
		setAttribute("applyId",applyId);
		setAttribute("potentialId",potentialId);
		setAttribute("customerId",customerId);
		LoanApplyInfo loanApplyInfo = null;
		if(StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
			loanApplyInfo = loanApplyService.getApplyInfoById(Integer.valueOf(loanId));
			setAttribute("loanContractType", loanApplyInfo.getLoanContractTypeId());
			//放贷信息页面显示授权取消按钮
			if("loan".equals(precType)) {
				setAttribute("syncStatus", loanApplyInfo.getSyncStatus());
			}
		}
		if("contract".equals(precType)){
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("isActived",1);
			condition.put("isDel",0);
			condition.put("isContractType",1);
			List<LoanType> typeList = typeService.queryTypeList(condition);
			setAttribute("typeList",typeList);
			setAttribute("contractCheckValue","");
			if(loanApplyInfo!=null){
				Integer contractCheckUserId = loanApplyInfo.getContractCheckUser();
				if(contractCheckUserId!=null&&contractCheckUserId!=0){
					PmsUser pmsUser = permissionModule.getPmsUserByUserId(contractCheckUserId);
					setAttribute("contractCheckValue",pmsUser.getUserName()+"("+pmsUser.getUserAccount()+")");
				}
				if((LoanProcessTypeEnum.LOAN.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.AUTHORIZED.type.equals(loanApplyInfo.getLoanProcessType())||LoanProcessTypeEnum.CLEARANCE.type.equals(loanApplyInfo.getLoanProcessType()))&&loanApplyInfo.getSyncStatus()==1){
					setAttribute("contractSyncStatus",1);
				}


				DataTable table = applyInfoService.selectApprovalDataTableByLoanId(loanApplyInfo.getLoanId());
				if(table!=null&&table.rowSize()>0){
					DataRow row = table.getRow(0);
					//基准利率
					Integer value = (Integer)row.get("RESULT_LIMIT");
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("dataDictName","CD_RULINGIR");
					if(value<=12){
						map.put("orderNo",1);
					}else if(value>60){
						map.put("orderNo",3);
					}else {
						map.put("orderNo",2);
					}
					List<SysDataDictCol> sysDataDictCols= iDataDictColService.queryDataDictColList(map);
					if(!CollectionUtils.isEmpty(sysDataDictCols)){
						setAttribute("baseRatio",sysDataDictCols.get(0).getDictCode());
					}
					setAttribute("loanAmount",(BigDecimal)row.get("RESULT_AMOUNT"));
					setAttribute("repayMode",(String)row.get("REPAYMENT_MODE"));
					setAttribute("resultLimit",(Integer)row.get("RESULT_LIMIT"));
					setAttribute("loanRatio",((BigDecimal)row.get("RESULT_RATIO")).toPlainString());
				}
			}

		}
		if(loanApplyInfo!=null){
			if(LoanProcessTypeEnum.REFUSE.type.equals(loanApplyInfo.getLoanProcessType())){
				setAttribute("contractApplyShow","show");
			}

			//只能是客户经理本人
			if(getLoginInfo().getUserId()==loanApplyInfo.getLoanBelongId()){
				setAttribute("btnContractCancel","show");
			}

			setAttribute("idCard",loanApplyInfo.getLoanIdentifyNum());
			setAttribute("loanName",loanApplyInfo.getLoanName());

			if (LoanProcessTypeEnum.CLEARANCE.type.equals(loanApplyInfo.getLoanProcessType())){
				setAttribute("contCancelShow",true);
			}

		}

		String forPrint = getParameter("forPrint");
		setAttribute("forPrint", forPrint);
		if (StringUtils.isNotBlank(applyId)) {
			IntoCustApplyInfo intoCustApplyInfo = intoCustomersProvider.getAppCustApplyInfoById(Integer.parseInt(applyId));
			if(StringUtils.isNotBlank(intoCustApplyInfo.getIdCard())) {
				Integer custId = customerModuleIntf.getCustomerIdByCardNameType(intoCustApplyInfo.getCustName(), "1", intoCustApplyInfo.getIdCard());
				setAttribute("customerId", custId);
			}
		}
		if (StringUtils.isNotBlank(potentialId)) {
			CustPotentialCustomerQuery custMarketCustomer = iPotentialCustomersProvider.getPotentialCustomersQueryById(Integer.parseInt(potentialId));
			if(StringUtils.isNotBlank(custMarketCustomer.getCardNumber()) && StringUtils.isNotBlank(custMarketCustomer.getCardType())) {
				Integer custId = customerModuleIntf.getCustomerIdByCardNameType(custMarketCustomer.getCustomerName(), custMarketCustomer.getCardType(), custMarketCustomer.getCardNumber());
				setAttribute("customerId", custId);
			}
		}

		if ("approval".equals(precType)) {
			if ((StringUtils.isNotBlank(showApply) && "1".equals(showApply)) || !precType.equals(module)) {
				this.setAttribute("showApply", "1");
			}
			return  basePath + "approval/approvalPage";
		}
		if("sign".equals(module)&&"contract".equals(precType)){
			setAttribute("signShow","show");
		}
		if("loan".equals(module) && "loan".equals(precType)){
			DataTable dataTable = applyInfoService.selectApprovalDataTableByLoanId(loanApplyInfo.getLoanId());
			if(dataTable!=null && dataTable.rowSize()>0){
				DataRow row = dataTable.getRow(0);
				//期限 利率
				setAttribute("approvalRatio",row.get("RESULT_RATIO"));
				setAttribute("approvalLimit",row.get("RESULT_LIMIT"));
			}
			dataTable = applyInfoService.getDataTableByLoanId("LOAN_CONTRACT", LoanProcessTypeEnum.CONTRACT.type, Integer.valueOf(loanId));
			if(dataTable!=null && dataTable.rowSize()>0){
				DataRow row = dataTable.getRow(0);
				//起始终止日期
				setAttribute("loanStartDate",row.get("LOAN_CONTRACT_BEGIN"));
				setAttribute("loanEndDate",row.get("LOAN_CONTRACT_END"));
			}

		}
		if("contract".equals(precType)){
			return basePath +"contract/loanContractTab";
		}
		return  basePath + "pub/loanTab";
	}

	@RequestMapping("/getInvestigateTabs")
	public String getInvestigateTabs(@RequestParam(value = "module", defaultValue = "") String module,
								  @RequestParam(value = "loanId", defaultValue = "") String loanId,
								  @RequestParam(value = "precType", defaultValue = "") String precType,
								  @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
								  @RequestParam(value = "showApply", defaultValue = "") String showApply){
		LoanApplyInfo applyInfo=applyInfoService.getApplyInfoById(Integer.valueOf(loanId));
		precType = firstUpperCase(precType);
		if ((StringUtils.isNotBlank(showApply) && "1".equals(showApply)) || !precType.equals(firstUpperCase(module))) {
			showApply = "1";
		}
		if (applyInfo.getLoanClassId().intValue() == 0){
			setAttribute("threeConfig",false);
		} else {
			setAttribute("threeConfig",true);
		}
		Integer loanClassId=applyInfo.getLoanClassId();
		setAttribute("loanClassId",loanClassId);
		setAttribute("loanId", loanId);
		setAttribute("precType", precType);
		setAttribute("module", module);
		setAttribute("loanTypeId", loanTypeId);
		setAttribute("showApply", showApply);
		return  basePath + "investigate/investigateTabs";
	}

	/**
	 * 根据状态获取自定义表单信息
	 * @param module 【各个阶段的key】
	 * @param loanTypeId 【贷款类型id】
	 */
	@RequestMapping("/getLoanTemplate")
	public String getLoanTemplate(@RequestParam(value = "module", defaultValue = "") String module,
								  @RequestParam(value = "loanId", defaultValue = "") String loanId,
								  @RequestParam(value = "precType", defaultValue = "") String precType,
								  @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
								  @RequestParam(value = "showApply", defaultValue = "") String showApply,
								  @RequestParam(value = "applyId", defaultValue = "") String applyId,
								  @RequestParam(value = "potentialId", defaultValue = "") String potentialId,
								  @RequestParam(value = "customerId", defaultValue = "") String customerId){
		setAttribute("precType",precType);//页卡的流程阶段
		precType = firstUpperCase(precType);
		if ((StringUtils.isNotBlank(showApply) && "1".equals(showApply)) || !precType.equals(firstUpperCase(module))) {
			showApply = "1";
		}
		if(StringUtils.isNotBlank(loanTypeId)&&StringUtils.isNumeric(loanTypeId)&&StringUtils.isNotBlank(precType)){
			List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByLoanType(loanTypeId,precType,null);
			if ("Loan".equals(precType))
				precType = null;

			for(AutoBaseTemplate template : templateList){
				String tableName = template.getTableName();
				//有贷款id的时候，为贷款编辑；放款时如果没有放款数据，从审批中拉取；列表编辑时，如果没有数据，新增一个id（标记地图使用）
				if(StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId) ){
					DataTable datatable = applyInfoService.getDataTableByLoanId(tableName, precType, Integer.valueOf(loanId));
					if(datatable!=null && datatable.rowSize()>0){
						template.setData(datatable.getRows());
					} else {
						if (StringUtils.isBlank(showApply)) {
							if ("LBIZ_LOAN_GRANT".equals(tableName)){
								//如果放款编辑的时候，没有贷款数据，从审批里拉
								setLoanInfo(loanId, template);
							} else {
								Map<String, Object> row = new HashMap<String, Object>();
								//如果有贷款id，没有自定义表单数据，需要处理一下初始化数据：1，放款表单；2，list表单，需要设置ID，标记地图使用；3，设置attributeMap，标记为需要设置默认值
								if ("List".equals(template.getType()))
									row.put("id", loanApplyService.newLbizId(template.getTableName()));
								//没有数据的表单，在编辑是需要设置默认值
								setDefaultDataValue(template, row);
								template.setData(new Object[]{row});
							}
						}
					}
				} else {	//没有贷款id的时候，主要有以下几种新增贷款的方式【进件客户转申请，潜在客户转申请，客户转申请，无关联新增贷款】
					Map<String, Object> row = new HashMap<String, Object>();
					if(StringUtils.isNotBlank(applyId) &&StringUtils.isNumeric(applyId)){
						//进件客户
						setApplyInfo(tableName, applyId, template, row);
					} else if(StringUtils.isNotBlank(potentialId) &&StringUtils.isNumeric(potentialId)){
						//潜在客户
						setPotentiaInfo(tableName, potentialId, template, row);
					} else if ((StringUtils.isNotBlank(customerId) && StringUtils.isNumeric(customerId))){
						//存量客户
						setCustomerInfo(tableName, customerId, template, row);
					} else {
						//其他
						setDefaultDataValue(template, row);
					}
					template.setData(new Object[]{row});
				}
				if("1".equals(showApply)){//查看
					formulaModule.getTableFormulaByTemplate(template);
				}

			}
			formSettingsProvider.setTemplateShowOrHide(templateList);
			this.setAttribute("templateList", templateList);
		}
		//设置被标记过的地址，规则【根据表名和列名选择增加样式】
		setAttribute("address",mapTaggingProvider.getAddressJson(loanId, null));
		setAttribute("showApply", showApply);
		setAttribute("module",module);
		setAttribute("loanId",loanId);
		if (StringUtil.isNotEmpty(loanId)){
			LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
		setAttribute("loanPrecType",loanApplyInfo.getLoanProcessType());//当前贷款的流程阶段
		}
		setAttribute("id",customerId);//校验客户编码,客户编辑页面为id,这里也取id
		return  basePath + "pub/loanTemplate";

	}


	/**
	 * 潜在客户转申请，如果已经成为客户，拉取客户信息
	 * @param tableName
	 * @param potentialId
	 * @param template
	 * @param row
	 */
	private void setPotentiaInfo(String tableName, String potentialId, AutoBaseTemplate template, Map<String, Object> row) {
		if (TableSyncEnum.PERSONAL.targetTableName.equals(tableName) || TableSyncEnum.FAMILY.targetTableName.equals(tableName) ||
				TableSyncEnum.SPOUSE.targetTableName.equals(tableName) ){
			CustPotentialCustomerQuery potentialCustomerQuery = iPotentialCustomersProvider.getPotentialCustomersQueryById(Integer.parseInt(potentialId));
			//潜在客户转申请，如果存在客户先从客户拉取信息
			if (potentialCustomerQuery != null) {
				String customerName = potentialCustomerQuery.getCustomerName();
				String identifyNum = potentialCustomerQuery.getCardNumber();
				String identifyType = potentialCustomerQuery.getCardType();
				Integer customerId = customerModuleIntf.getCustomerIdByCardNameType(customerName, identifyType, identifyNum);
				if (customerId != null) {
					setCustomerInfo(tableName, customerId.toString(), template, row);
				}
			}
			//潜在客户转申请，个人信息，从潜在客户覆盖为最新的
			if ("LBIZ_PERSONAL_INFO".equals(tableName)) {
				if (potentialCustomerQuery != null) {
					row.put("customerName", potentialCustomerQuery.getCustomerName());
					if(potentialCustomerQuery.getCustomerSex().length() != 0){
						row.put("customerSex", potentialCustomerQuery.getCustomerSex());
					}
					if (potentialCustomerQuery.getAge() != null){
						row.put("customerAge", potentialCustomerQuery.getAge());
					}
					row.put("identifyType", potentialCustomerQuery.getCardType());
					row.put("identifyNum", potentialCustomerQuery.getCardNumber());
					row.put("phoneNumber", potentialCustomerQuery.getTelephoneNumber());
					if(potentialCustomerQuery.getAddress().length() != 0){
						row.put("liveAddress", potentialCustomerQuery.getAddress());
					}
				}

			}
		} else
			setDefaultDataValue(template, row);

	}

	/**
	 * 设置（潜在）客户转申请的表单信息，如果存在客户，将客户表单信息带出，如果不存在拉出默认值
	 * @param tableName
	 * @param customerId
	 * @param template
	 * @param row
	 */
	private void setCustomerInfo(String tableName, String customerId, AutoBaseTemplate template, Map<String, Object> row) {
		if (TableSyncEnum.PERSONAL.targetTableName.equals(tableName) || TableSyncEnum.FAMILY.targetTableName.equals(tableName) ||
				TableSyncEnum.SPOUSE.targetTableName.equals(tableName) ){
			DataTable dataTableCustomer = customerModuleIntf.getDataTableById(TableSyncEnum.valueOfTarget(tableName).sourceTableName, Integer.parseInt(customerId));
			List<AutoBaseField> fieldList = template.getFields();
			if(CollectionUtils.isNotEmpty(fieldList)){
				for (AutoBaseField baseFiled : fieldList) {
					String column = baseFiled.getColumn();
					String field = baseFiled.getField();
					if(StringUtils.isNotBlank(column)&&StringUtils.isNotBlank(field) && dataTableCustomer.getRows() != null && dataTableCustomer.getRows().length > 0){
						row.put(field, dataTableCustomer.getRow(0).get(column));
					}
				}
			}
		} else
			setDefaultDataValue(template, row);
	}


	/**
	 * 进件客户转申请，设置进件客户信息
	 * @param tableName
	 * @param applyId
	 * @param template
	 * @param row
	 */
	private void setApplyInfo(String tableName, String applyId, AutoBaseTemplate template, Map<String, Object> row){
		//进件客户客户转申请，只有个人信息需要设置表单内容，其他都取默认值
		if ("LBIZ_PERSONAL_INFO".equals(tableName) || "LBIZ_LOAN_APPLY_INFO".equals(tableName)) {
			IntoCustApplyInfoQuery intoCustApplyInfo = intoCustomersProvider.getAppCustApplyInfoById(Integer.parseInt(applyId));
			//设置默认值到Data数据里
			setDefaultDataValue(template, row);
			if ("LBIZ_PERSONAL_INFO".equals(tableName)) {
				row.put("customerName", intoCustApplyInfo.getCustName());
				//空字符串时不进行覆盖
				if(intoCustApplyInfo.getCustSex().length() != 0){
					row.put("customerSex", intoCustApplyInfo.getCustSex());
				}
				row.put("customerAge", intoCustApplyInfo.getCustAge());
				row.put("identifyType", "1");
				row.put("identifyNum", intoCustApplyInfo.getIdCard());
				row.put("phoneNumber", intoCustApplyInfo.getCustPhone());
				if(intoCustApplyInfo.getHomeAddress().length() != 0){
					row.put("liveAddress", intoCustApplyInfo.getHomeAddress());
				}
			} else if ("LBIZ_LOAN_APPLY_INFO".equals(tableName)) {
				row.put("loanApplyAmount", intoCustApplyInfo.getApplyAmount());
				row.put("loanUserOption", intoCustApplyInfo.getLoanUserOption());
			}
		} else
			setDefaultDataValue(template, row);
	}

	/**
	 * 进件客户转申请，潜在客户转申请，客户转申请，贷款新增表单，需要设置默认值
	 * @param template
	 * @param row
	 */
	private void setDefaultDataValue(AutoBaseTemplate template, Map<String, Object> row) {
		List<AutoBaseField> fieldList = template.getFields();
		if(CollectionUtils.isNotEmpty(fieldList)){
			for (AutoBaseField baseFiled : fieldList) {
				AutoFieldWrapper autoFieldWrapper = (AutoFieldWrapper) baseFiled;
				String column = baseFiled.getColumn();
				String field = baseFiled.getField();
				if(StringUtils.isNotBlank(column)&&StringUtils.isNotBlank(field)){
					//时间类型默认值设置 0代表昨天，1代表今天，2代表明天 指定日期就存入指定日期
					if("Date".equals(autoFieldWrapper.getFieldType())&&StringUtils.isNotBlank(autoFieldWrapper.getDefaultValue())){
						if ("0".equals(autoFieldWrapper.getDefaultValue())){
							Date date = DateUtil.addDay(new Date(), -1);
							row.put(field, DateUtil.format(date,DateUtil.DEFAULT_DATE_FORMAT));
						}else if("1".equals(autoFieldWrapper.getDefaultValue())){
							row.put(field, DateUtil.format(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
						}else if("2".equals(autoFieldWrapper.getDefaultValue())){
							Date date = DateUtil.addDay(new Date(), 1);
							row.put(field, DateUtil.format(date,DateUtil.DEFAULT_DATE_FORMAT));
						}
					}
					else{
						row.put(field, autoFieldWrapper.getDefaultValue());
					}
				}
			}
		}
	}

	/**
	 * 如果贷款编辑的时候，没有贷款数据，从审批里拉
	 * @param loanId
	 * @param template
	 */
	private void setLoanInfo(String loanId, AutoBaseTemplate template){
		DataTable applyInfoData = applyInfoService.getApprovalDataTableByLoanId(Integer.valueOf(loanId));
		DataTable applyInfoContract = applyInfoService.getDataTableByLoanId("LOAN_CONTRACT", LoanProcessTypeEnum.CONTRACT.type, Integer.valueOf(loanId));
		DataRow dataRow = applyInfoData.getRow(0);
		DataRow dataRow1 = applyInfoContract.getRow(0);
		Map<String, Object> row = new HashMap<String, Object>();
		if (dataRow != null){
			row.put("loanMode", dataRow.get("LOAN_MODE"));
			row.put("loanBackMode", dataRow.get("REPAYMENT_MODE"));
			row.put("loanAmount", dataRow.get("RESULT_AMOUNT"));
			row.put("loanLimit", dataRow.get("RESULT_LIMIT"));
			row.put("loanRatio", dataRow.get("RESULT_RATIO"));
			row.put("loanBackDate", dataRow.get("REPAYMENT_DATE"));
		}
		if(dataRow1 != null){
			row.put("loanCreditDate", new Date());
			row.put("loanRatioDate", dataRow1.get("LOAN_CONTRACT_BEGIN"));
			row.put("loanEndDate",dataRow1.get("LOAN_CONTRACT_END"));
		}
		template.setData(new Object[]{row});
	}


	/**
	 * 跳转到拒绝页面
	 */
	@RequestMapping("/gotoRefusePage")
	public String gotoRefusePage(@RequestParam(value = "loanId", defaultValue = "") String loanId,
								 @RequestParam(value = "module", defaultValue = "") String module){
		setAttribute("loanId", loanId);
		setAttribute("module", module);
		if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
			setAttribute("loanProcessType", applyInfo.getLoanProcessType());
		}
		return basePath + "pub/loanRefuse";
	}



	/**
	 *保存拒绝信息表单
	 */
	@RequestMapping("/saveRefusePage")
	@ResponseBody
	public void saveRefuseInfo(@RequestParam(value = "loanId", defaultValue = "") String loanId,
							   @RequestParam(value = "refuseType", defaultValue = "") String refuseType,
							   @RequestParam(value = "refuseReason", defaultValue = "") String refuseReason,
							   @RequestParam(value = "refuseRemark", defaultValue = "") String refuseRemark,
							   @RequestParam(value = "joinBlack", defaultValue = "") String joinBlack,
							   @RequestParam(value = "module", defaultValue = "") String module){
		try {

			if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
				LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
				if ((module.toLowerCase()).equals(applyInfo.getLoanProcessType().toLowerCase())) {
					Integer loginerId = getLoginInfo().getUserId();
					boolean result = applyInfoService.saveRefuseInfo(refuseType, refuseReason, refuseRemark, joinBlack, loanId, loginerId);
					renderText(result, result ? "操作成功" : "操作失败", "");
				} else {
					renderText(false, "当前贷款信息状态不正确！", "");
				}
			} else {
				renderText(false,"参数错误","");
			}
		} catch (Exception e) {
			log.error("保存拒绝信息异常",e);
			renderText(false,"保存失败", "");
		}
	}



	/**
	 * 回退【分配回退，】
	 * @param loanId
	 */
	@RequestMapping("/backApplyInfo")
	public String backApplyInfo(@RequestParam(value = "loanId", defaultValue = "") String loanId,@RequestParam(value = "module", defaultValue = "") String module){
		setAttribute("loanId", loanId);
		setAttribute("module", module);
		return basePath + "pub/loanBackApply";
	}
	/**
	 * 回退【分配回退，】
	 * @param loanId
	 */
	@RequestMapping("/saveBackApplyInfo")
	@SqlTransaction
	@ResponseBody
	public void saveBackApplyInfo(@RequestParam(value = "loanId", defaultValue = "") String loanId,
								  @RequestParam(value = "note", defaultValue = "") String note,
								  @RequestParam(value = "module", defaultValue = "") String module){
		if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
			Integer loginerId = getLoginInfo().getUserId();//approval approval
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
			if ((module.toLowerCase()).equals(applyInfo.getLoanProcessType().toLowerCase())) {
				boolean result = applyInfoService.backApplyInfo(loanId, loginerId, note);
				renderText(result, result ? "保存成功" : "保存失败", "");
			} else {
				renderText(false, "当前贷款信息状态不正确！", "");
			}
		} else {
			renderText(false,"参数错误","");
		}
	}



	/**
	 * 查询列表数据
 	 * @param processType 【各个阶段】
	 * @param customer 【客户信息，模糊查询，姓名电话身份证号】
	 * @param loanTypeId【贷款类型id】
	 * @param startDate 【开始时间】
	 * @param endDate【结束时间】
	 * @param auditStartDate【审批开始时间】
	 * @param auditEndDate【审批结束时间】
	 * @param montiorState【监控状态】
	 * @param afterState【贷款状态】
	 * @param collectionType【催收状态】
	 * @param memberUserId【申请人id】
	 */
	@RequestMapping(value = "/queryLoanInfoListData")
	@ResponseBody
	public void queryApplyInfoListData(){
		
		Integer maxLength = 20;
		String defulatVavlue = "";
		String module = this.getParameter("module", defulatVavlue, maxLength);
		String processType = this.getParameter("processType", defulatVavlue, maxLength);
		String customer = this.getParameter("customer", defulatVavlue, maxLength);
		String loanTypeId = this.getParameter("loanTypeId", defulatVavlue, Integer.class);
		
		String startDate = this.getParameter("startDate", defulatVavlue, Date.class);
		String endDate = this.getParameter("endDate", defulatVavlue, Date.class);
		String auditStartDate = this.getParameter("auditStartDate", defulatVavlue, Date.class);
		String auditEndDate = this.getParameter("auditEndDate", defulatVavlue, Date.class);
		String loanContractStartDate = this.getParameter("loanContractStartDate", defulatVavlue, Date.class);
		String loanContractEndDate = this.getParameter("loanContractEndDate", defulatVavlue, Date.class);
		
		String loanStartDate = this.getParameter("loanStartDate", defulatVavlue, Date.class);
		String loanEndDate = this.getParameter("loanEndDate", defulatVavlue, Date.class);
		String loanContractEndStartDate = this.getParameter("loanContractEndStartDate", defulatVavlue, Date.class);
		String loanContractEndEndDate = this.getParameter("loanContractEndEndDate", defulatVavlue, Date.class);
		String auditEndStartDate = this.getParameter("auditEndStartDate", defulatVavlue, Date.class);
		String auditEndEndDate = this.getParameter("auditEndEndDate", defulatVavlue, Date.class);
		
		String loanStartTime = this.getParameter("loanStartTime", defulatVavlue, maxLength);
		String loanEndTime = this.getParameter("loanEndTime", defulatVavlue, maxLength);
		
		String isNew = this.getParameter("isNew", defulatVavlue, maxLength);
		String productType = this.getParameter("productType", defulatVavlue, maxLength);
		
		String montiorState = this.getParameter("montiorState", defulatVavlue, maxLength);
		String collectionState = this.getParameter("collectionState", defulatVavlue, maxLength);
		String afterState = this.getParameter("afterState", defulatVavlue, maxLength);
		
		String memberUser = this.getParameter("memberUser", defulatVavlue, maxLength);
		String teamId = this.getParameter("teamId", defulatVavlue, 200);
		String benchMode = this.getParameter("benchMode", defulatVavlue, maxLength);
		String memberUserId = this.getParameter("memberUserId", defulatVavlue, Integer.class);
//		String collectionType = this.getParameter("collectionType", defulatVavlue, maxLength);
		String firstMonitorState = this.getParameter("firstMonitorState", "-1", maxLength);
		String isExistAccountNo = this.getParameter("isExistAccountNo","",maxLength);
		String isOverdue = this.getParameter("isOverdue","",maxLength);
		String isEnough = this.getParameter("isEnough","",maxLength);

		module = firstUpperCase(module);

		Map<String,Object> condition = new HashMap<String,Object>();
		if ("All".equals(module)) {
			//贷款阶段有筛选值的时候，选择不选拒绝的列表，排除已拒绝的数据
			if (StringUtils.isBlank(benchMode)) {
				if (StringUtils.isNotBlank(processType)) {
					if (LoanProcessTypeEnum.REFUSE.type.equals(processType)) {
						condition.put("refuse", "1");
					} else {
						condition.put("notrefuse", "1");
						condition.put("loanProcessType", processType);
					}
				}
			} else{
				//工作台跳转来的参数
				condition.put("notrefuse", "1");
				condition.put("loanProcessType", benchMode);
			}
		} else {
			condition.put("loanProcessType", module);
			condition.put("notrefuse", "1");
			//催收列表过滤条件属于贷款状态
			if ("Collection".equals(module) || "Monitor".equals(module) ) {
				condition.put("loanProcessType", LoanProcessTypeEnum.AFTER_LOAN.type);
			}
		}
		condition.put("isDel", 0);
		condition.put("customerInfo", customer);
		condition.put("loanTypeId", loanTypeId);
		condition.put("montiorState", montiorState);
		condition.put("afterState", afterState);
		condition.put("collectionState", collectionState);
		condition.put("module", module);
		condition.put("isExistAccountNo", isExistAccountNo);
		SysBasicConfig cssz = null;
//		if (StringUtil.isNotEmpty(collectionState)) {
//			//显示催收贷款(当前日期+催收设置天数>=应还款时间)
//			cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
//			Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());
//			condition.put("collectionDate", collectionDate);
//			//催收拆分,1表示还款提醒,2表示不良催收计划还款时间>DateUtil.getCurrentDate()
//			condition.put("collectionType", collectionType);
//			condition.put("nowDate", DateUtil.getNeedTime(0,0,0,0));
//		}

		ILoginInfo loginInfo = getLoginInfo();
		Integer userId = loginInfo.getUserId();
		if ("Approval".equals(module)) {
			condition.put("investigationBeginDate", startDate);
			condition.put("investigationEndDate", endDate);
			condition.put("auditUserId", userId);
		} else if ("Apply".equals(module) || "Investigate".equals(module) || "AfterLoan".equals(module)) {
			condition.put("loanBelongId", userId);
			if ("Apply".equals(module)) {
				condition.put("createBeginDate", startDate);
				condition.put("createEndDate", endDate);
			} else if ("Investigate".equals(module)) {
				condition.put("assignmentBeginDate", startDate);
				condition.put("assignmentEndDate", endDate);
			} else if ("AfterLoan".equals(module)) {
				condition.put("creditBeginDate", startDate);
				condition.put("creditEndDate", endDate);
			}
		} else if ("All".equals(module) || "Allot".equals(module) || "Loan".equals(module) || "Monitor".equals(module) || "Collection".equals(module)||"Contract".equals(module) || "Authorized".equals(module)) {
			String groupIds = loginInfo.getTeamGroupIdByRole();
			condition.put("loanBelongId", userId);
			if (StringUtils.isNotBlank(groupIds))
				condition.put("groupIds", groupIds);
			else
				condition.put("groupIds", "-1");
			if ("All".equals(module)) {
				condition.put("belongUser", "true");
				condition.put("memberUser", memberUser);
				condition.put("createBeginDate", startDate);
				condition.put("createEndDate", endDate);
				if (StringUtils.isNotBlank(teamId)) {
					condition.remove("loanBelongId");
					condition.put("groupIds", teamId);
				}
				condition.put("investigationBeginDate", auditStartDate);
				condition.put("investigationEndDate", auditEndDate);
				condition.put("applyUser", "true");
				condition.put("investigateUser", "true");
				condition.put("team", "true");
				condition.put("creditBeginDate", loanStartDate);
				condition.put("creditEndDate", loanEndDate);
				condition.put("auditBeginDate", auditEndStartDate);
				condition.put("auditEndDate", auditEndEndDate);
				condition.put("isNew", isNew);
				condition.put("productType", productType);
				condition.put("loanContractBeginStartDate", loanContractStartDate);
				condition.put("loanContractBeginEndDate", loanContractEndDate);
				condition.put("loanContractEndStartDate", loanContractEndStartDate);
				condition.put("loanContractEndEndDate", loanContractEndEndDate);
				condition.put("loanStartTime", loanStartTime);
				condition.put("loanEndTime", loanEndTime);
			} else if ("Allot".equals(module) || "Loan".equals(module)||"Contract".equals(module) || "Authorized".equals(module)) {
				condition.put("applyBeginDate", startDate);
				condition.put("applyEndDate", endDate);
				if ("Allot".equals(module)) {
					condition.put("applyUser", "true");
					if (StringUtils.isNotBlank(memberUserId)) {
						condition.put("loanApplyUserId", Integer.parseInt(memberUserId));
					}
				} else {
					condition.put("auditBeginDate", auditStartDate);
					condition.put("auditEndDate", auditEndDate);
				}
			} else if ("Monitor".equals(module) ) {
				condition.put("creditBeginDate", startDate);
				condition.put("creditEndDate", endDate);
				condition.put("firstMonitorState", firstMonitorState);
			} else if ("Collection".equals(module)) {
				condition.put("isOverdue", isOverdue);
				condition.put("isEnough", isEnough);
//				condition.put("repayBeginDate", startDate);
//				condition.put("repayEndDate", endDate);
				//condition.put("collection", "1");		//目前显示所有贷后信息
			}
//			else if("Sign".equals(module)){
//				condition.put("contractSubmitStartDate",contractSubmitStartDate);
//				condition.put("contractSubmitEndDate",contractSubmitEndDate);
//				condition.put("",userId);
//			}
		} else {
			if("Sign".equals(module)){
				condition.remove("loanBelongId");
				condition.put("contractCheckUser",userId);
				condition.put("contractSubmitStartDate",startDate);
				condition.put("contractSubmitEndDate",endDate);
			}else {
				condition.put("loanBelongId", 0);
			}
		}
		IPageList<LoanApplyInfo_Web_Query> allotInfos ;
		allotInfos = loanApplyService.queryAllInfoList(condition,this.getPage());

//		if("Collection".equals(module)){
//			allotInfos = loanApplyService.queryCollectionList(condition,this.getPage());
//		}else{
//			allotInfos = loanApplyService.queryAllInfoList(condition,this.getPage());
//		}
//		if ("AfterLoan".equals(module) || "Collection".equals(module) || "Monitor".equals(module)){
//			dateFormat = "yyyy-MM-dd";
//		//重新赋值loanCollectionState状态
////		if (LoanProcessTypeEnum.AFTER_LOAN.type.equalsIgnoreCase(module)) {
//			//显示催收贷款(当前日期+催收设置天数>=应还款时间)
//			if(null==cssz){
//				cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
//			}
//			Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());
//			for (LoanApplyInfo_Web_Query query : allotInfos) {
////				LoanApplyInfo query1 = (LoanApplyInfo)query;
//				if (query.getLoanCollectionState() != null && LoanCollectionState.COLLECTION.state.equals(query.getLoanCollectionState())) {
//					if (query.getLoanRepayDate() != null && collectionDate != null) {
//						if (query.getLoanRepayDate().compareTo(collectionDate) > 0) {
//							query.setLoanCollectionState("");
//						}
//					}
//				}
//			}
//		}

		String dateFormat = "yyyy-MM-dd HH:mm";
		if ("AfterLoan".equals(module) || "Collection".equals(module) || "Monitor".equals(module)){
			dateFormat = "yyyy-MM-dd";
			Date date21 = getNextDay(21);
			for (LoanApplyInfo_Web_Query query : allotInfos) {
				query.setLoanRepayDate(date21);
			}
		}

		renderText(JsonUtil.toJson(allotInfos, "loanId", getModuleMapForList().get(module), dateFormat).toString());
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
	 * 查询列表数据
	 * @param processType 【各个阶段】
	 * @param customer 【客户信息，模糊查询，姓名电话身份证号】
	 * @param loanTypeId【贷款类型id】
	 * @param startDate 【开始时间】
	 * @param endDate【结束时间】
	 * @param auditStartDate【审批开始时间】
	 * @param auditEndDate【审批结束时间】
	 * @param montiorState【监控状态】
	 * @param afterState【贷款状态】
	 * @param collectionType【催收状态】
	 * @param memberUserId【申请人id】
	 */
	@RequestMapping(value = "/exportFirstMonitor")
	@ResponseBody
	public void exportFirstMonitor(@RequestParam(value = "module", defaultValue = "") String module,
									   @RequestParam(value = "processType", defaultValue = "") String processType,
									   @RequestParam(value = "customer", defaultValue = "") String customer,
									   @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
									   @RequestParam(value = "startDate", defaultValue = "") String startDate,
									   @RequestParam(value = "endDate", defaultValue = "") String endDate,
									   @RequestParam(value = "auditStartDate", defaultValue = "") String auditStartDate,
									   @RequestParam(value = "auditEndDate", defaultValue = "") String auditEndDate,
									   @RequestParam(value = "loanContractStartDate", defaultValue = "") String loanContractStartDate,
									   @RequestParam(value = "loanContractEndDate", defaultValue = "") String loanContractEndDate,
									   @RequestParam(value = "loanStartDate", defaultValue = "") String loanStartDate,
									   @RequestParam(value = "loanEndDate", defaultValue = "") String loanEndDate,
									   @RequestParam(value = "loanContractEndStartDate", defaultValue = "") String loanContractEndStartDate,
									   @RequestParam(value = "loanContractEndEndDate", defaultValue = "") String loanContractEndEndDate,
									   @RequestParam(value = "auditEndStartDate", defaultValue = "") String auditEndStartDate,
									   @RequestParam(value = "auditEndEndDate", defaultValue = "") String auditEndEndDate,
									   @RequestParam(value = "loanStartTime", defaultValue = "") String loanStartTime,
									   @RequestParam(value = "loanEndTime", defaultValue = "") String loanEndTime,
									   @RequestParam(value = "isNew", defaultValue = "") String isNew,
									   @RequestParam(value = "productType", defaultValue = "") String productType,

									   @RequestParam(value = "montiorState", defaultValue = "") String montiorState,
									   @RequestParam(value = "collectionState", defaultValue = "") String collectionState,
									   @RequestParam(value = "afterState", defaultValue = "") String afterState,
									   @RequestParam(value = "memberUser", defaultValue = "") String memberUser,
									   @RequestParam(value = "teamId", defaultValue = "") String teamId,
									   @RequestParam(value = "benchMode", defaultValue = "") String benchMode,
									   @RequestParam(value = "memberUserId", defaultValue = "") String memberUserId,
									   @RequestParam(value = "collectionType", defaultValue = "") String collectionType,
									   @RequestParam(value = "firstMonitorState", defaultValue = "-1") String firstMonitorState){
		module = firstUpperCase(module);

		Map<String,Object> condition = new HashMap<String,Object>();
		if ("All".equals(module)) {
			//贷款阶段有筛选值的时候，选择不选拒绝的列表，排除已拒绝的数据
			if (StringUtils.isBlank(benchMode)) {
				if (StringUtils.isNotBlank(processType)) {
					if (LoanProcessTypeEnum.REFUSE.type.equals(processType)) {
						condition.put("refuse", "1");
					} else {
						condition.put("notrefuse", "1");
						condition.put("loanProcessType", processType);
					}
				}
			} else{
				//工作台跳转来的参数
				condition.put("notrefuse", "1");
				condition.put("loanProcessType", benchMode);
			}
		} else {
			condition.put("loanProcessType", module);
			condition.put("notrefuse", "1");
			//催收列表过滤条件属于贷款状态
			if ("Collection".equals(module) || "Monitor".equals(module) ) {
				condition.put("loanProcessType", LoanProcessTypeEnum.AFTER_LOAN.type);
			}
		}
		condition.put("isDel", 0);
		condition.put("customerInfo", customer);
		condition.put("loanTypeId", loanTypeId);
		condition.put("montiorState", montiorState);
		condition.put("afterState", afterState);
		condition.put("collectionState", collectionState);
		condition.put("module", module);
		if (StringUtil.isNotEmpty(collectionState)) {
			//显示催收贷款(当前日期+催收设置天数>=应还款时间)
			SysBasicConfig cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
			Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());
			condition.put("collectionDate", collectionDate);
			//催收拆分,1表示还款提醒,2表示不良催收计划还款时间>DateUtil.getCurrentDate()
			condition.put("collectionType", collectionType);
			condition.put("nowDate", DateUtil.getNeedTime(0,0,0,0));
		}

		ILoginInfo loginInfo = getLoginInfo();
		Integer userId = loginInfo.getUserId();
		if ("Approval".equals(module)) {
			condition.put("investigationBeginDate", startDate);
			condition.put("investigationEndDate", endDate);
			condition.put("auditUserId", userId);
		} else if ("Apply".equals(module) || "Investigate".equals(module) || "AfterLoan".equals(module)) {
			condition.put("loanBelongId", userId);
			if ("Apply".equals(module)) {
				condition.put("createBeginDate", startDate);
				condition.put("createEndDate", endDate);
			} else if ("Investigate".equals(module)) {
				condition.put("assignmentBeginDate", startDate);
				condition.put("assignmentEndDate", endDate);
			} else if ("AfterLoan".equals(module)) {
				condition.put("creditBeginDate", startDate);
				condition.put("creditEndDate", endDate);
			}
		} else if ("All".equals(module) || "Allot".equals(module) || "Loan".equals(module) || "Monitor".equals(module) || "Collection".equals(module)||"Contract".equals(module)) {
			String groupIds = loginInfo.getTeamGroupIdByRole();
			condition.put("loanBelongId", userId);
			if (StringUtils.isNotBlank(groupIds))
				condition.put("groupIds", groupIds);
			else
				condition.put("groupIds", "-1");
			if ("All".equals(module)) {
				condition.put("belongUser", "true");
				condition.put("memberUser", memberUser);
				condition.put("createBeginDate", startDate);
				condition.put("createEndDate", endDate);
				if (StringUtils.isNotBlank(teamId)) {
					condition.remove("loanBelongId");
					condition.put("groupIds", teamId);
				}
				condition.put("investigationBeginDate", auditStartDate);
				condition.put("investigationEndDate", auditEndDate);
				condition.put("applyUser", "true");
				condition.put("investigateUser", "true");
				condition.put("team", "true");
				condition.put("creditBeginDate", loanStartDate);
				condition.put("creditEndDate", loanEndDate);
				condition.put("auditBeginDate", auditEndStartDate);
				condition.put("auditEndDate", auditEndEndDate);
				condition.put("isNew", isNew);
				condition.put("productType", productType);
				condition.put("loanContractBeginStartDate", loanContractStartDate);
				condition.put("loanContractBeginEndDate", loanContractEndDate);
				condition.put("loanContractEndStartDate", loanContractEndStartDate);
				condition.put("loanContractEndEndDate", loanContractEndEndDate);
				condition.put("loanStartTime", loanStartTime);
				condition.put("loanEndTime", loanEndTime);
			} else if ("Allot".equals(module) || "Loan".equals(module)||"Contract".equals(module)) {
				condition.put("applyBeginDate", startDate);
				condition.put("applyEndDate", endDate);
				if ("Allot".equals(module)) {
					condition.put("applyUser", "true");
					if (StringUtils.isNotBlank(memberUserId)) {
						condition.put("loanApplyUserId", Integer.parseInt(memberUserId));
					}
				} else {
					condition.put("auditBeginDate", auditStartDate);
					condition.put("auditEndDate", auditEndDate);
				}
			} else if ("Monitor".equals(module) ) {
				condition.put("creditBeginDate", startDate);
				condition.put("creditEndDate", endDate);
				condition.put("firstMonitorState", firstMonitorState);
			} else if ("Collection".equals(module)) {
				condition.put("repayBeginDate", startDate);
				condition.put("repayEndDate", endDate);
				//condition.put("collection", "1");		//目前显示所有贷后信息
			}
		} else {
			condition.put("loanBelongId", 0);
		}

		List<LoanApplyInfo_Web_Query> loanInfos = loanApplyService.queryAllInfoList(condition);
		String dateFormat = "yyyy-MM-dd HH:mm";
		if ("AfterLoan".equals(module) || "Collection".equals(module) || "Monitor".equals(module)){
			dateFormat = "yyyy-MM-dd";
			//重新赋值loanCollectionState状态
//		if (LoanProcessTypeEnum.AFTER_LOAN.type.equalsIgnoreCase(module)) {
			//显示催收贷款(当前日期+催收设置天数>=应还款时间)
			SysBasicConfig cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
			Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());
			for (LoanApplyInfo_Web_Query query : loanInfos) {
				LoanApplyInfo query1 = (LoanApplyInfo)query;
				if (query.getLoanCollectionState() != null && LoanCollectionState.COLLECTION.state.equals(query.getLoanCollectionState())) {
					if (query.getLoanRepayDate() != null && collectionDate != null) {
						if (query.getLoanRepayDate().compareTo(collectionDate) > 0) {
							query.setLoanCollectionState("");
						}
					}
				}
			}
		}
		//下载excel
		exportExcel(loanInfos,module);
		renderText(JsonUtil.toJson(loanInfos, "loanId", getModuleMapForList().get(module), dateFormat).toString());
	}

	private void exportExcel(List<LoanApplyInfo_Web_Query> loanInfos, String module) {

//		fields = [{ display: '客户信息', field: 'customerInfo', width: 140 ,align : 'center' },
//		{ display: '证件信息', field: 'cardInfo', width: 200 ,align : 'center' },
//		{ display: '贷款类型', field: 'loanTypeName', width: 120 ,align : 'center' },
//		{ display: '放款金额(元)', field: 'loanCreditAmountFormat', width: 100 ,align : 'center' },
//		{ display: '放贷日期', field: 'loanCreditDate', width: 80 ,align : 'center' },
//		{ display: '贷款状态', field: 'loanAfterStateName', width: 80 ,align : 'center' },
//		{ display: '首次监控状态', field: 'firstMonitorState', width: 80 ,align : 'center' },
//		{ display: '首次监控日期', field: 'firstMonitorDate', width: 80 ,align : 'center' },
//		{ display: '归属人', field: 'belongUserName', width: 80 ,align : 'center' }];

		//excel标题
		String[] title = {"客户姓名","证件类型","证件号码","贷款类型","放款金额","放款日期","贷款状态","首次监控状态","首次监控日期","归属人"};

		//excel文件名
		 String fileName = "贷后监控信息表"+System.currentTimeMillis()+".xls";

		//sheet名
		 String sheetName = "首次监控状态";
		String[][] content = new String[loanInfos.size()][];
		for (int i = 0; i < loanInfos.size(); i++) {
			content[i] = new String[title.length];
			content[i][0] = loanInfos.get(i).getLoanName();
			content[i][1] = loanInfos.get(i).getLoanIdentifyTypeName();
			content[i][2] = loanInfos.get(i).getLoanIdentifyNum();
			content[i][3] = loanInfos.get(i).getLoanTypeName();
			content[i][4] = loanInfos.get(i).getLoanCreditAmountFormat();
			content[i][5] = DateUtil.format(loanInfos.get(i).getLoanCreditDate(),"yyyy-MM-dd");
			content[i][6] = loanInfos.get(i).getLoanAfterStateName();
			content[i][7] = loanInfos.get(i).getFirstMonitorState();
			content[i][8] = DateUtil.format(loanInfos.get(i).getFirstMonitorDate(),"yyyy-MM-dd");
			content[i][9] = loanInfos.get(i).getBelongUserName();
		}

		//创建HSSFWorkbook 
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

		//响应到客户端
		try {
			this.setResponseHeader(this.getResponse(), fileName);
			OutputStream os = this.getResponse().getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
			} catch (Exception e) {
			e.printStackTrace();
			}
	}

	//发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	/**
	 * 私有方法，配置贷款相关各个列表要显示的字段，注意列属性名称一致
	 * @return
	 */
	private Map<String, String> getModuleMapForList(){
		if (moduleMapForList == null) {
			moduleMapForList = new HashMap<String, String>();
			//申请列表
			moduleMapForList.put("All","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,belongUserName,loanProcessTypeName,loanAfterStateName,applyUserName,investigateUserName,teamName,createDate,loanInvestigationDate,loanContractEnd,loanAccountNo");
			//申请列表
			moduleMapForList.put("Apply","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,createDate");
			//待分配列表
			moduleMapForList.put("Allot","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,applyUserName,loanApplyDate");
			//待调查列表
			moduleMapForList.put("Investigate","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,loanAssignmentDate");
			//待审批列表
			moduleMapForList.put("Approval","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanProposeAmountFormat,loanInvestigationDate");
			//贷款合同列表
			moduleMapForList.put("Contract","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanResultAmountFormat,loanAuditDate,loanApplyDate");
			//合同签订列表
			moduleMapForList.put("Sign","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,contractSubmitDate,contractSignUserId,belongUserName");
			//待放款列表
			moduleMapForList.put("Loan","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanResultAmountFormat,loanAuditDate,loanApplyDate");
			//待授权列表
			moduleMapForList.put("Authorized","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanCreditAmountFormat,loanAuditDate,loanApplyDate");
			//贷后列表
			moduleMapForList.put("AfterLoan","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanCreditAmountFormat,loanMonitorStateName,collectionState,loanCreditDate,loanAfterStateName");
			//监控列表
			moduleMapForList.put("Monitor","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,loanCreditAmountFormat,loanCreditDate,loanAfterStateName,belongUserName,firstMonitorDate,firstMonitorState");
			//催收列表""
			moduleMapForList.put("Collection","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,collectionState,loanRepayAmount,loanRepayDate,belongUserName,nextRepaymentAmount,loanArrears,loanIrrevocableInterest,loanBalanceAmount,loanAccountAmount,overdueLimit");
			return moduleMapForList;
		} else
			return moduleMapForList;
	}

	/**
	 * 将首字母变为大写
	 * @param module
	 * @return
	 */
	private String firstUpperCase(String module){
		if (StringUtils.isNotBlank(module)) {
			return module.substring(0,1).toUpperCase()+module.substring(1);
		} else {
			return "";
		}
	}

	/**
	 * 将首字母变为小写
	 * @param module
	 * @return
	 */
	private String firstLowerCase(String module){
		if (StringUtils.isNotBlank(module)) {
			return module.substring(0,1).toLowerCase()+module.substring(1);
		} else {
			return "";
		}
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:测试评分项得分
	 * @Date: 14:09 2017/9/15
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/countLoanModelScoreByModeId")
	@SqlTransaction
	public void countLoanModelScoreByModeId(HttpServletRequest request, HttpServletResponse response){
		String loanId = this.getParameter("loanId");
		String loanTypeId = this.getParameter("loanTypeId");
		BigDecimal decimal = iLoanModelScoreService.countLoanModelScoreByModeId(
				Integer.parseInt(loanId), Integer.parseInt(loanTypeId), "Investigate");

		System.out.println("decimal-------------------" + decimal);
		renderText(true, "评分项得分计算成功！", decimal+"");
	}


	/**
	 *放贷驳回
	 */
	@RequestMapping("/loanGiveBack")
	@ResponseBody
	public void saveRefuseInfo(@RequestParam(value = "loanId", defaultValue = "") String loanId) {
		try {
			LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
			Integer loginUserId = this.getLoginInfo().getUserId();
			if(LoanProcessTypeEnum.LOAN.type.equals(applyInfo.getLoanProcessType())) {
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.CONTRACT.type);
//				applyInfo.setSyncStatus(-1);
				applyInfo.setContractCheckUser(0);
				loanApplyService.updateApplyInfo(applyInfo, this.getLoginInfo().getUserId());
			}
			// 记录操作日志
			loanOperationService.addLoanOperation(Integer.valueOf(loanId), LoanOperationType.LOAN_GIVE_BACK.typeName, "", loginUserId, LoanProcessTypeEnum.LOAN.type);
			renderText(true, "操作成功", "json");

		}catch (Exception e){
			log.error("放款驳回失败",e);
			renderText(false,"驳回失败",String.valueOf(""));
		}
	}

	/**
	 * 抵质押物补入信息页面
	 * @return
	 */
	@RequestMapping("/getPledgEidtPage")
	public String getPledgEidtPage(@RequestParam(value = "id",required = true) String id){

		DataTable dataTable = applyInfoService.getPledgeDataTableById(Integer.valueOf(id));
		PledgInfo pledgInfo = new PledgInfo();
		for (DataColumn column:dataTable.getColumns()){
			if ("ID".equals(column.getName())){
				pledgInfo.setId((Integer) column.getValues()[0]);
			}else if ("OTHER_CRED_NO".equals(column.getName())){
				pledgInfo.setTxqzh(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("RIGHT_ORG".equals(column.getName())){
				pledgInfo.setQsdjjg(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("INPUT_DATE".equals(column.getName())){
				pledgInfo.setDjrq(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("INPUT_BR_ID".equals(column.getName())){
				pledgInfo.setDjjg(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("CREATE_USER_NO".equals(column.getName())){
				pledgInfo.setDjr(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("ASSURANCE_TYPE".equals(column.getName())){
				pledgInfo.setTbxz(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("ASSURANCE_NO".equals(column.getName())){
				pledgInfo.setDbbh(column.getValues()[0]==null?null:column.getValues()[0].toString());
			}else if ("ASSURANCE_AMT".equals(column.getName())){
				pledgInfo.setDbje(column.getValues()[0]==null||!StringUtil.isNumber(column.getValues()[0].toString())?null:BigDecimal.valueOf(Double.parseDouble(column.getValues()[0].toString())));
			}else if ("CO_NAME".equals(column.getName())){
				pledgInfo.setDbgsmc(column.getValues()[0]==null?"":column.getValues()[0].toString());
			}else if ("ASSURANCE_DATE".equals(column.getName())){
				pledgInfo.setTbrq(column.getValues()[0]==null?"":column.getValues()[0].toString());
			}else if ("END_DATE".equals(column.getName())){
				pledgInfo.setTbdqrq(column.getValues()[0]==null?"":column.getValues()[0].toString());
			}
		}
		getRequest().setAttribute("pledg",pledgInfo);
		return basePath+"pledgPage";
	}

	/**
	 * 保存抵质押物信息
	 */
	@RequestMapping(value = "/updatePledg",method = RequestMethod.POST)
	@ResponseBody
	public void updatePledg(@ModelAttribute PledgInfo pledgInfo){
		try {
			applyInfoService.updatePledgeById(pledgInfo);
			String result = socketDemo.updatePledg(pledgInfo.getId());
			if ("success".equals(result)) {
				//更新LBIZ_PLEDGE_ITEM PLEDGE_STATUS为0 0代表补录成功
				applyInfoService.updatePledgeStatusById(pledgInfo.getId(),"0");
				renderText(true, "保存成功", "json");
			}else {
				renderText(false,"保存失败:"+result,String.valueOf(""));
			}
		}catch (Exception e){
			log.error("updatePledg error",e);
			renderText(false,"保存失败",String.valueOf(""));
		}
	}


	/**
	 * 受托支付状态列表
	 * @return
	 */
	@RequestMapping("/getTrustedlistPage")
	public String getTrustedlistPage(){
		return basePath+"trusted/trustedPaymenyList";
	}

	/**
	 * 获取授权支付列表
	 */
	@ResponseBody
	@RequestMapping("/queryTrustedPaymentListData")
	public void queryTrustedPaymentListData(){
		String dateFormat = "yyyy-MM-dd HH:mm";
		String customer = getRequest().getParameter("customer");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customer",customer);
		String groupIds = getLoginInfo().getTeamGroupIdByRole();
		map.put("groupIds",groupIds);
		IPageList<TrustedPayment> list = trustedPaymentService.getTrustedPayment(map,this.getPage());
		renderText(JsonUtil.toJson(list, "id","loanId,isBankAccount,counterpartyAccount,counterpartyCardNumber,counterpartyName,counterpartyBankName,counterpartyBankNumber,paymentAmount,paymentStatus,loanName,loanIdentifyNum,loanTelNum", dateFormat).toString());
	}

	/**
	 * 修改受托支付信息
	 * @return
	 */
	@RequestMapping("/getTrusedPayment")
	public String getTrusedPayment(@RequestParam(value = "id",required = true) String id){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id",id);
		IPageList<TrustedPayment> list = trustedPaymentService.getTrustedPayment(map,this.getPage());
		setAttribute("data",list.get(0));
		return basePath+"trusted/trustedPaymeny";
	}


	/**
	 * 修改受托支付
	 */
	@ResponseBody
	@RequestMapping("/updateTrustedPayment")
	public void updateTrustedPayment(@RequestParam(value = "id",required = true) String id,
									 @RequestParam(value = "loanId",required = true) String loanId,
									 @RequestParam(value = "isBankAccount",required = true) String isBankAccount,
									 @RequestParam(value = "counterpartyAccount",required = true) String counterpartyAccount,
									 @RequestParam(value = "counterpartyCardNumber",required = true) String counterpartyCardNumber,
									 @RequestParam(value = "counterpartyName",required = true) String counterpartyName,
									 @RequestParam(value = "counterpartyBankName",required = true) String counterpartyBankName,
									 @RequestParam(value = "counterpartyBankNumber",required = true) String counterpartyBankNumber){


		LoanTrustedPayment loanTrustedPayment = new LoanTrustedPayment();
		loanTrustedPayment.setId(Integer.valueOf(id));
		//查询 最大受托支付id +1
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loanId",loanId);
		List<LoanTrustedPayment> list = trustedPaymentService.getTrustedPaymentList(map);
		String paymentId = "000";
		for (LoanTrustedPayment payment:list){
			if (Integer.parseInt(payment.getPaymentId())>Integer.parseInt(paymentId)){
				paymentId=payment.getPaymentId();
			}
		}
		String newPaymentId = "00"+Integer.parseInt(paymentId);
		loanTrustedPayment.setPaymentId(newPaymentId.substring(newPaymentId.length()-3));
		loanTrustedPayment.setIsBankAccount(isBankAccount);
		loanTrustedPayment.setCounterpartyAccount(counterpartyAccount);
		loanTrustedPayment.setCounterpartyCardNumber(counterpartyCardNumber);
		loanTrustedPayment.setCounterpartyName(counterpartyName);
		loanTrustedPayment.setCounterpartyBankName(counterpartyBankName);
		loanTrustedPayment.setCounterpartyBankNumber(counterpartyBankNumber);
		String result =  socketDemo.syncTrustedPayment(Integer.valueOf(loanId));
		if ("success".equals(result)) {
			loanTrustedPayment.setPaymentStatus("1");
			trustedPaymentService.updateTrustedPaymentInfo(loanTrustedPayment);
			renderText(true, "保存同步成功", "json");
		}else {
			loanTrustedPayment.setPaymentStatus("0");
			trustedPaymentService.updateTrustedPaymentInfo(loanTrustedPayment);
			renderText(false,"同步失败:"+result,"json");
		}
	}

	/**
	 * 共有人信息列表页面
	 * @return
	 */
	@RequestMapping("/queryCommPeoInfoList")
	public String getCommPeoInfoListPage(@RequestParam(value = "id",required = true) String id,
										 @RequestParam(value = "loanId",required = true) String loanId,
										 @RequestParam(value = "precType",required = true) String precType){

		setAttribute("itemId",id);
		setAttribute("loanId",loanId);
		setAttribute("precType",precType);
		setAttribute("userId",this.getLoginInfo().getUserId());
		return basePath+"commPeoInfoListPage";
	}

	/**
	 * 共有人信息页面
	 * @return
	 */
	@RequestMapping("/queryCommPeoInfoListData")
	@ResponseBody
	public void getCommPeoInfoListPage(){

		String itemId = this.getRequest().getParameter("itemId");
		String loanId = this.getRequest().getParameter("loanId");
		String precType = this.getRequest().getParameter("precType");
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtil.isNotEmpty(itemId) ){
			condition.put("itemId",itemId);
		}
		if(StringUtil.isNotEmpty(loanId) ){
			condition.put("loanId",loanId);
		}
		IPageList<CommPeoInfo_Query> comPeoInfoList = loanApplyService.queryCommPeoInfoListData(condition, this.getPage());
		renderText(JsonUtil.toJson(comPeoInfoList, "id","commPeoName,commPeoCerTypeName,commLicenseNo,commNo,commKeeperName,corporation,telephone,facsimile,address,indivSpsName,userId").toString());
	}

	/**
	 * 共有人信息添加页面
	 * @return
	 */
	@RequestMapping("/addCommPeoInfoPage")
	public String getAddCommPeoInfoPage(@RequestParam(value = "id",required = true) Integer id,
										 @RequestParam(value = "loanId",required = true) Integer loanId,
										 @RequestParam(value = "precType",required = true) String precType,
										 @RequestParam(value = "editFlag",required = true) Integer editFlag){
		CommPeoInfo commPeoInfo = new CommPeoInfo();
		commPeoInfo.setItemId(id);
		commPeoInfo.setLoanId(loanId);
		commPeoInfo.setLoanProcessType(precType);
		setAttribute("editFlag",editFlag);
		setAttribute("commPeoInfo",commPeoInfo);
		return basePath+"commPeoInfoPage";
	}

	/**
	 * 保存共有人信息
	 */
	@RequestMapping(value = "/updateCommPeoInfo",method = RequestMethod.POST)
	@ResponseBody
	public void updateCommPeoInfo(@ModelAttribute CommPeoInfo commPeoInfo){
		try {
			if(commPeoInfo!=null){
				commPeoInfo.setUserId(this.getLoginInfo().getUserId());
				loanApplyService.updateCommPeoInfo(commPeoInfo);
				renderText(true,"保存成功",String.valueOf(""));
			}else{
				renderText(false,"保存失败",String.valueOf(""));
			}
		}catch (Exception e){
			log.error("updateCommPeoInfo error",e);
			renderText(false,"保存失败",String.valueOf(""));
		}
	}

	/**
	 * 删除共有人信息
	 */
	@RequestMapping(value = "/deleteCommPeoInfo")
	@ResponseBody
	public void deleteCommPeoInfo(@RequestParam(value = "id",required = true) Integer id){
		try {
			if(id!=null){
				loanApplyService.deleteCommPeoInfo(id);
				renderText(true, "删除成功", String.valueOf(""));
			}else{
				renderText(false,"删除失败",String.valueOf(""));
			}
		}catch (Exception e){
			log.error("updateCommPeoInfo error",e);
			renderText(false,"保存失败",String.valueOf(""));
		}
	}

	/**
	 * 查看共有人信息
	 */
	@RequestMapping(value = "/queryCommPeoInfoById")
	public String queryCommPeoInfoById(@RequestParam(value = "id",required = true) Integer id,
									 @RequestParam(value = "editFlag",required = true) Integer editFlag){
		setAttribute("editFlag",editFlag);
		CommPeoInfo commPeoInfo = loanApplyService.queryCommPeoInfoById(id);
		setAttribute("commPeoInfo",commPeoInfo);
		return basePath+"commPeoInfoPage";
	}

	/**
	 * 编辑共有人信息
	 */
	@RequestMapping(value = "/updateCommPeoInfoById")
	public String updateCommPeoInfoById(@RequestParam(value = "id",required = true) Integer id,
									   @RequestParam(value = "editFlag",required = true) Integer editFlag){
		setAttribute("editFlag",editFlag);
		CommPeoInfo commPeoInfo = loanApplyService.queryCommPeoInfoById(id);
		setAttribute("commPeoInfo",commPeoInfo);
		return basePath+"commPeoInfoPage";
	}


	/**
	 * 刷新贷款账户
	 */
	@RequestMapping("/refreshLoanAccount")
	@ResponseBody
	@NoTokenAnnotation
	@NoLoginInterceptor
	public void refreshLoanAccount() {
		Integer userId = this.getLoginInfo().getUserId();

		String result = applyInfoService.refreshLoanAccount(userId);
		if (StringUtils.isNotBlank(result)) {
			if ("100".equals(result)) {
				renderText(true, "成功！", "");
			} else if ("155".equals(result)) {
				renderText(false, "操作频繁，请稍后再试！", "");
			} else {
				renderText(false, "系统异常", "");
			}
		}
	}

	/**
	 * 刷新贷款账户
	 */
	@RequestMapping(value="/refreshLoanAccountTab",method = RequestMethod.POST)
	@ResponseBody
	@NoTokenAnnotation
	@NoLoginInterceptor
	public void refreshLoanAccountTab(@RequestParam(value = "loanId",required = true) Integer loanId) {

		String result = applyInfoService.refreshLoanAccountTab(loanId);
		if (StringUtils.isNotBlank(result)) {
			if ("success".equals(result)) {
				renderText(true, "成功！", "");
			} else {
				renderText(false, "系统异常", "");
			}
		}
	}

}
