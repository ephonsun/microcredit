package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.customer.CustBasicInfo;
import banger.domain.customer.Customer;
import banger.domain.customer.CustomerQuery;
import banger.domain.enumerate.TableSyncEnum;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.domain.permission.SysTeamMember_Query;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.framework.collection.DataColumn;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.*;
import banger.service.intf.IBasicInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 页面访问类
 */
@Controller
@RequestMapping("/customer")
public class  CustomerController extends BaseController {
	private static final long serialVersionUID = 7617413530455472793L;
	@Autowired
	private IBasicInfoService basicInfoService;

	@Resource
	IConfigModule configModule;

	@Resource
	IProductModule iProductModule;
	@Resource
	ILoanModule loanModule;
	@Autowired
	private IFormSettingsProvider formSettingsProvider;
	@Autowired
	private IMapTaggingProvider mapTaggingProvider;

	@Autowired
	private IPermissionModule permissionModule;

	private String BASE_PATH_CUSTOMER = "/customer/vm/customer/";
	private String BASE_PATH_INTENTPROD = "/customer/vm/intentProd/";


	/**
	 * 得到客户列表页面0
	 * @return
	 */
	@RequestMapping("/getCustomerListPage")
	public String getBasicInfoListPage(){
		if (isCustomerManager()) {
			setAttribute("selectMy", "1");
		}
		setAttribute("userId",this.getLoginInfo().getUserId());
		if(!isCustomerManager()){
			setAttribute("allotFlag",true);
		}
		return BASE_PATH_CUSTOMER + "customerListPage";
	}

	/**
	 * 查询列表数据0
	 * @return
	 */
	@RequestMapping("/queryBasicInfoListData")
	@ResponseBody
	public void queryBasicInfoListData(){
		Integer userId = this.getLoginInfo().getUserId();
		String teamGroupIds = this.getLoginInfo().getTeamGroupIdByRole(false);
		String customerLevel = this.getRequest().getParameter("customerLevel");
		String customer = this.getRequest().getParameter("customer");
		String selectFlag = this.getRequest().getParameter("selectFlag");
		String belongUserName = this.getRequest().getParameter("belongUserName");
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(customerLevel)){
			condition.put("customerLevel",customerLevel);
		}
		if(StringUtils.isNotBlank(customer)){
			condition.put("customer",customer);
		}
		if (StringUtils.isBlank(teamGroupIds)) {
			teamGroupIds = "-1";
		}
		if (selectFlag == null) {
			String selectMy = this.getRequest().getParameter("selectMy");
			if ("0".equals(selectMy)) {
				condition.put("teamGroupIds",teamGroupIds);
			}
		} else {
			if(StringUtils.isBlank(selectFlag)||!"on".equals(selectFlag)){
				condition.put("teamGroupIds",teamGroupIds);
			}
		}

		condition.put("belongUserId",userId);
		if(StringUtils.isNotBlank(belongUserName)){
			condition.put("belongUserName",belongUserName);
		}

		IPageList<CustomerQuery> basicInfoList = basicInfoService.queryBasicInfoList(condition,this.getPage());
		renderText(JsonUtil.toJson(basicInfoList, "id","customerInfo,cardInfo,belongUserName,customerLevelName,belongUserId,customerName").toString());
	}


	/**
	 * 得到新增页面0
	 * @return
	 */
	@RequestMapping("/getCustomerTabs")
	public String getCustomerTabs(){
		String id = getParameter("id");
		//---------------------------------------权限判断
		CustBasicInfo custBasicInfo = basicInfoService.getBasicInfoById(Integer.parseInt(id));
		//数据权限校验，判断当前数据是不是属于当前等路人应该看到的数据
		Integer belongUserId = custBasicInfo.getBelongUserId();
		
		boolean pass = false;
		//判断是否有管理级别角色
		if (!isBackenManager()&&!isTeamManager()&&!isMutilTeamManager()&&!isApprovalOrOther()){
			//没有任何可以跨归属查看的职能角色
			Integer loginUserId = this.getLoginInfo().getUserId();
			if (!belongUserId.equals(loginUserId)) {
				return "nopermitform";
			}else{
				pass = true;
			}
		}
		
		if(!pass){
			String checkData = permissionModule.checkDataByUserId(getLoginInfo().getTeamGroupIdByRole(false), belongUserId);
			if (StringUtils.isNotBlank(checkData)) {
				return checkData;
			}
		}
		//---------------------------------------end
		String flag = getParameter("flag");
		//客户移交所需数据
		String isHandOver = getParameter("isHandOver");
		String belongId = getParameter("belongId");
		setAttribute("belongId",belongId);
		setAttribute("isHandOver",isHandOver);
		setAttribute("id",id);
		setAttribute("defaultShowPage",0);
		setAttribute("flag",flag);
		return BASE_PATH_CUSTOMER + "customerTabs";

	}


	/**
	 * 得到新增页面0
	 * @return
	 */
	@RequestMapping("/getCustomerInsertPage")
	public String getCustomerInsertPage(@RequestParam(value = "id", defaultValue = "") String id){
		String flag = getParameter("flag");
		String isHandOver = getParameter("isHandOver");
		String belongId = getParameter("belongId");
		String isapply="0";//用来前台页面判断是否展示可申请按钮
		//判断 如果是客户经理就允许转申请，不是 就不允许转申请
		String userRoles=getLoginInfo().getRoleIds();
		String[] roles_user = userRoles.split(",");
		for (int i=0;roles_user.length>i;i++)
		{
			if(Integer.parseInt(roles_user[i])==4)
			{
				isapply=1+""; //如果有
			}
		}



		List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByIds(new Integer[]{1,2,3});

		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){

			//校验归属人是否为当前登录客户经理
			CustBasicInfo custBasicInfo = basicInfoService.getBasicInfoById(Integer.valueOf(id));
			if(this.isCustomerManager()&&!this.getLoginInfo().getUserId().equals(custBasicInfo.getBelongUserId())){
				return "nopermitform";
			}

			for(AutoBaseTemplate template : templateList){
				String tableName = TableSyncEnum.valueOfTarget(template.getTableName()).sourceTableName;
				DataTable datatable = basicInfoService.getDataTableById(tableName, Integer.valueOf(id));
				if(datatable!=null && datatable.rowSize()>0){
					template.setData(datatable.getRows());
				}
			}
		}


		setAttribute("belongId",belongId);
		setAttribute("isHandOver",isHandOver);
		setAttribute("id",id);
		setAttribute("flag",flag);
		setAttribute("isapply",isapply);

		//2017-12-08确认不做联动校验
		formSettingsProvider.setTemplateShowOrHide(templateList);
		this.setAttribute("templateList", templateList);
		JSONArray address = mapTaggingProvider.getAddressJson(null, id);
		setAttribute("address", address);

		return BASE_PATH_CUSTOMER + "customerInsert";

	}

	/**
	 * 申请新建，三要素检查0
	 * @return
	 */
	@RequestMapping(value = "/getCustomerInfoPage", method = RequestMethod.POST)
	@ResponseBody
	public void getCustomerInfoPage(@RequestParam(value = "identifyType", defaultValue = "") String identifyType,
									  @RequestParam(value = "identifyNum", defaultValue = "") String identifyNum,
									  @RequestParam(value = "tableName", defaultValue = "") String tableName,
									  @RequestParam(value = "customerName", defaultValue = "") String customerName){
		Integer id = null;
		if(StringUtils.isNotBlank(identifyType)&&StringUtils.isNotBlank(identifyNum)&&StringUtils.isNotBlank(customerName)){
			identifyNum = IdCardUtil.toUpperCase(identifyNum);
			id = basicInfoService.getCustomerIdByCardNameType(customerName, identifyType, identifyNum);
		}
		List<AutoBaseTemplate> templateList = new ArrayList<AutoBaseTemplate>();
		if(StringUtils.isNotBlank(tableName)){
			templateList = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{tableName});
		}
		tableName = TableSyncEnum.valueOfTarget(tableName).sourceTableName;
		if(null!=id&&CollectionUtils.isNotEmpty(templateList)){
			JSONObject resultJson = new JSONObject();
			JSONArray datas = new JSONArray();
			AutoBaseTemplate template = templateList.get(0);
			DataTable datatable = basicInfoService.getDataTableById(tableName, Integer.valueOf(id));
			if(datatable!=null && datatable.rowSize()>0){
				DataRow row = datatable.getRow(0);
				DataColumn[] columns = datatable.getColumns();
				Map<String, Object> tempMap = new HashMap<String, Object>();
				row.set("ID", "");
				for (DataColumn column : columns) {

					tempMap.put(column.getName(), row.get(column.getName()));
				}
				for (AutoBaseField field : template.getFields()) {
					Object val = tempMap.get(field.getColumn());
					if (val != null) {
						JSONObject json = new JSONObject();
						if ("Date".equals(field.getType())) {
							System.out.println(val.toString());
						}
						json.put("key", field.getField());
						json.put("value", val.toString());
						json.put("type", field.getType());
						datas.add(json);
					}
				}
				resultJson.put("data", datas);
				resultJson.put("customerId", id);
				renderJson(true, "", resultJson);
			}
		}
	}




//	/**
//	 * 得到修改页面
//	 * @return
//	 */
//	@RequestMapping("/getBasicInfoUpdatePage")
//	public String getBasicInfoUpdatePage(){
//		String customerId = getParameter("customerId");
//		CustBasicInfo basicInfo = basicInfoService.getBasicInfoById(Integer.parseInt(customerId));
//		setAttribute("basicInfo",basicInfo);
//		return SUCCESS;
//	}
//
//	/**
//	 * 得到查看页面
//	 * @return
//	 */
//	@RequestMapping("/getBasicInfoDetailPage")
//	public String getBasicInfoDetailPage(){
//		String cuatomerId = getParameter("cuatomerId");
//		CustBasicInfo basicInfo = basicInfoService.getBasicInfoById(Integer.parseInt(cuatomerId));
//		setAttribute("basicInfo",basicInfo);
//		return SUCCESS;
//	}

	/**
	 * 新增数据0
	 * @return
	 */
	@RequestMapping("/saveCustomerInfo")
	@ResponseBody
	public void saveCustomerInfo(@ModelAttribute Customer customer,HttpServletRequest request,HttpServletResponse response){

		Integer userId = this.getLoginInfo().getUserId();
		String postJson = this.getParameter("json");
		String idStr = this.getParameter("id");

		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) this.getDojoConvert().toObject(HashMap.class, postJson);
		Map<String, Object> customerMap = new HashMap<String, Object>();

		for (Object obj : map.keySet()) {
			String key = obj.toString();
			String val = (String) map.get(key);
			if (key.indexOf("field.") > -1) {
				// 自定义字段
				String keystr = "field.";
				String propertyName = key.substring(keystr.length());
				if(!StringUtil.isNullOrEmpty(val)){
					if ("identifyNum".equals(propertyName))
						val = IdCardUtil.toUpperCase(val);
					customerMap.put(propertyName, val);
				}
			}
		}

		String[] templateNameList = new String[]{TableSyncEnum.PERSONAL.targetTableName,TableSyncEnum.FAMILY.targetTableName,TableSyncEnum.SPOUSE.targetTableName};
		List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getTemplateListByTables(templateNameList);
		Integer id = null;
		if(CollectionUtils.isNotEmpty(templateList)&& MapUtils.isNotEmpty(customerMap)){

			boolean updateFlag = false;
			if(StringUtils.isNotBlank(idStr)&&StringUtils.isNumeric(idStr)){
				id = Integer.valueOf(idStr);
				updateFlag = true;
			}else{
				id = basicInfoService.getNewId();
			}

			DataTable dataTable = null;
			for (AutoBaseTemplate autoTemplate : templateList) {
				dataTable = new DataTable();
				String tableName = TableSyncEnum.valueOfTarget(autoTemplate.getTableName()).sourceTableName;
				dataTable.setName(tableName);
				dataTable.addColumn("ID");
				dataTable.newRow().set("ID", id);

				List<AutoBaseField> fieldList = autoTemplate.getFields();
				if(CollectionUtils.isNotEmpty(fieldList)){
					for (AutoBaseField baseFiled : fieldList) {
						String column = baseFiled.getColumn();
						String field = baseFiled.getField();
						if(StringUtils.isNotBlank(column)&&StringUtils.isNotBlank(field)){
							dataTable.getRow(0).set(column,MapUtils.getObject(customerMap,field));
						}
					}
				}
				//
				if(TableSyncEnum.PERSONAL.sourceTableName.equals(tableName)){
					dataTable.addColumn("CREATE_USER");
					dataTable.getRow(0).set("CREATE_USER", userId);
					dataTable.getRow(0).set("BELONG_USER_ID", userId);
					dataTable.addColumn("CREATE_DATE");
					dataTable.getRow(0).set("CREATE_DATE",new Date());
					dataTable.addColumn("IS_DEL");
					dataTable.getRow(0).set("IS_DEL",0);
				}

				if(updateFlag){
					DataTable table = basicInfoService.getDataTableById(tableName,id);
					if(table!=null && table.rowSize()>0){
						basicInfoService.updateCustomerDataTable(dataTable,"ID");
					}else {
						basicInfoService.insertCustomerDataTable(dataTable,"ID");
					}
				}else{
					basicInfoService.insertCustomerDataTable(dataTable,"ID");
				}
			}
		}

		renderText(true,"保存成功",String.valueOf(id));
	}


	/**
	 *  检查用户名是否重复0
	 * @return
	 */
	@RequestMapping("/checkCustomerIsRepeat")
	@ResponseBody
	public void checkCustomerIsRepeat(){
		String customerName = this.getParameter("field.customerName");
		String identifyType = this.getParameter("field.identifyType");
		String identifyNum = this.getParameter("field.identifyNum");
		String id = this.getParameter("id");
		if(StringUtils.isNotBlank(customerName)&&StringUtils.isNotBlank(identifyType)&&StringUtils.isNotBlank(identifyNum)){
			if(!basicInfoService.isUniqueCustomer(id,customerName,identifyType,identifyNum)){
				renderText(false, "customerRepeat", "已存在相同姓名、证件类型、证件号码客户，请重新输入");
			}
		}
	}


	/**
	 * form表单提交 Date类型数据绑定
	 * <功能详细描述>
	 * @param binder
	 * @see [类、类#方法、类#成员]
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}


	/**
	 * 删除数据
	 * @return
	 */
	@RequestMapping("/deleteCustomer")
	public void deleteBasicInfo(){
		String id = getParameter("id");
		boolean delete = basicInfoService.deleteBasicInfoById(Integer.parseInt(id));
		renderText(delete ? SUCCESS : ERROR );
	}


	/**
	 * 得到新增页面
	 * @return
	 */
	@RequestMapping("/getCustomerLoanListPage")
	public String getCustomerCreditListPage(){
		String id = getParameter("id");
		setAttribute("id",id);
		return BASE_PATH_CUSTOMER + "customerLoanList";
	}

	/**
	 * 得到新增页面
	 * @return
	 */
	@RequestMapping("/getIntentProdListPage")
	public String getIntentProdListPage(){
		String id = getParameter("id");
		setAttribute("id",id);
		return BASE_PATH_INTENTPROD + "intentProdList";
	}

//	/**
//	 * 得到新增页面
//	 * @return
//	 */
//	@RequestMapping("/getCreditFilePage")
//	public String getCreditFilePage(){
//		String id = getParameter("id");
//		setAttribute("id",id);
//		return BASE_PATH_CREDIT + "creditFileList";
//	}

	/**
	 * 查询列表数据
	 * @return
	 */
	@RequestMapping("/queryCreditListData")
	@ResponseBody
	public void queryCreditListData(){
		Integer userId = this.getLoginInfo().getUserId();
		String id = this.getRequest().getParameter("id");

		Map<String,Object> condition = new HashMap<String,Object>();
//		condition.put("belongUserId",1);
		condition.put("isDel", 0);
		condition.put("applyUser", "true");
		condition.put("investigateUser", "true");
		condition.put("loanBelongId", userId);
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			condition.put("customerId",id);
		} else {
			condition.put("customerId",-1);
		}
//		moduleMapForList.put("All","loanName,loanProcessType,customerInfo,cardInfo,loanTypeName,,belongUser,,loanAfterStateName,teamName,,loanInvestigationDate");
		IPageList<LoanApplyInfo_Web_Query> basicInfoList = loanModule.queryBasicInfoListForCredit(condition,this.getPage());
		renderText(JsonUtil.toJson(basicInfoList, "loanId","createDate,loanApplyAmountFormat,loanProcessTypeName,loanTypeName,applyUserName,investigateUserName").toString());

//		return SUCCESS;
	}

	/**
	 * 查询列表数据
	 * @return
	 */
	@RequestMapping("/queryIntentProdListData")
	@ResponseBody
	public void queryIntentProdListData(){
		Integer userId = this.getLoginInfo().getUserId();
		String id = this.getRequest().getParameter("id");

		IPageList<ProdIntentCustomer_Query> list = null;
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			CustBasicInfo cust = basicInfoService.getBasicInfoById(Integer.valueOf(id));
			if(null!=cust){
				Map<String,Object> condition = new HashMap<String, Object>();
				condition.put("picCertificateType",cust.getIdentifyType());
				condition.put("picCertificateNum",cust.getIdentifyNum());
				condition.put("picName",cust.getCustomerName());
				list = iProductModule.queryIntentCustomerListData(condition, this.getPage());
			}
		}

		renderText(JsonUtil.toJson(list, "picId","picProductName,picRemark,picCreateDate,picCreateUserName").toString());

	}

	/**
	 * @description 客户分配
	 * @return
	 */
	@RequestMapping("/allotCustomerPage")
	public String allotCustomerPage(@RequestParam(value = "ids", defaultValue = "") String ids){
		//查询所有团队的客户经理
		if(StringUtils.isNotBlank(ids) && StringUtils.isNumeric(ids)) {
			Integer userId = getLoginInfo().getUserId();
			Integer teamGroupId = permissionModule.getGroupIdByUserId(userId);
			String groupPermit = getLoginInfo().getTeamGroupIdByRole();
			StringBuffer sb = new StringBuffer("");
			if (teamGroupId != null) {
				sb.append(teamGroupId);
			}
			if(teamGroupId != null&&StringUtil.isNotEmpty(groupPermit)){
				sb.append(",");
			}
			if(StringUtil.isNotEmpty(groupPermit)){
				sb.append(groupPermit);
			}
			String teamGroupIdStr = StringUtil.isNotEmpty(sb.toString()) ? sb.toString():"-1";
			List<SysTeamMember_Query> members=permissionModule.getMangerByGroupId(teamGroupIdStr);
			setAttribute("userId",userId);
			setAttribute("teamMembers",members);
		}
		setAttribute("ids",ids);
		return BASE_PATH_CUSTOMER + "allotCustomerPage";
	}

	@RequestMapping(value = "/allotCustomerSave")
	@SqlTransaction
	@ResponseBody
	public void allotCustomerSave(@RequestParam(value = "ids", defaultValue = "") String ids,
								  @RequestParam(value = "memberUserId", defaultValue = "") String memberUserId){
		if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(memberUserId)) {
			Customer cust = basicInfoService.getCustomerDetailById(Integer.valueOf(ids));
			if(cust!=null){
				cust.setBelongUserId(Integer.parseInt(memberUserId));
				basicInfoService.updateBasicInfo(cust,getLoginInfo().getUserId());
				renderText(true,"分配成功","");
			}else{
				renderText(false,"客户数据异常","");
			}
		} else {
			renderText(false,"参数错误","");
		}
	}

}
