package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.customer.CustBasicInfo;
import banger.domain.customer.CustBasicInfoQuery;
import banger.domain.customer.CustScheduleQuery;
import banger.domain.enumerate.TableSyncEnum;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUser;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.pagesize.PageList;
import banger.framework.reader.Reader;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.IProductModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppCustomerService;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppCustomerController extends BaseController{


	private static final long serialVersionUID = -4733616928600230508L;
	@Resource
	private ICustomerModuleIntf customerModule;
	@Resource
	IAppCustomerService appCustomerService;
	@Resource
	IConfigModule configModule;
	@Resource
	private IProductModule productModule;
	@Resource
	private IPermissionModule permissionModule;


	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/queryCustomerList", method = RequestMethod.POST)
	public ResponseEntity<String> queryCustomerList(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("设备列表，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		String customerLevel = jsonObject.containsKey("customerLevel")?jsonObject.getString("customerLevel"):"";
		String customer = jsonObject.containsKey("customer")?jsonObject.getString("customer"):"";
		String userId = jsonObject.containsKey("userId")?jsonObject.getString("userId"):"";
		String belongUserIds = jsonObject.containsKey("belongUserIds")?jsonObject.getString("belongUserIds"):"";

		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(customerLevel)){
			condition.put("customerLevel",customerLevel);
		}
		if(StringUtils.isNotBlank(customer)){
			condition.put("customer",customer);
		}
		if(StringUtils.isNotBlank(belongUserIds))
			condition.put("belongUserIds",belongUserIds.split("\\,"));
		else
			condition.put("belongUserId",userId);

		//根据用户id查询用户所有的团队权限
		PmsUser pmsUser = permissionModule.queryUserGroupPermitByUserId(userId);
		String teamGroupIds = pmsUser.getUserGroupPermit();
		if(StringUtils.isNotBlank(teamGroupIds)){
			condition.put("teamGroupIds",teamGroupIds);
		}

		condition.put("isTeamGroup","false");

		//判断用户角色是否是 客户经理 团队主管 后台人员
		List<PmsRole> roles = permissionModule.getRoleIdByUID(Integer.parseInt(userId));
		if(roles != null && roles.size()>0){
			for(PmsRole role : roles){
				if(role.getRoleId() == 3 || role.getRoleId() == 4 || role.getRoleId() == 5){
					condition.put("isTeamGroup","true");
				}
			}
		}


		IPageSize page = PageSizeUtil.getPageLimit(jsonObject,AppParamsConst.PAGE_SIZE_CUSTOMER_LIST);
		IPageList<CustBasicInfoQuery> list = customerModule.queryBasicInfoListForApp(condition,page);

		for(CustBasicInfoQuery custBasicInfoQuery : list){
			if(custBasicInfoQuery.getBelongUserId()!=null && StringUtil.isNotEmpty(userId)){
				if(custBasicInfoQuery.getBelongUserId().intValue()==Integer.parseInt(userId)){
					custBasicInfoQuery.setEditable(true);
				}else{
					custBasicInfoQuery.setEditable(false);
				}
			}else{
				custBasicInfoQuery.setEditable(false);
			}
		}

		JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,customerName,customerLevel,customerSex,customerAge," +
				"phoneNumber,phoneNumberX,identifyType,identifyNum,identifyNumX,updateDate,identifyTypeName,belongUserId,belongUserName,editable");

		return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);

	}

	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/getCustomerTemplateFields", method = RequestMethod.POST)
	public ResponseEntity<String> getCustomerTemplateFields(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);

		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isNotEmpty(reqJson)){
			jsonObject = jsonObject.fromObject(reqJson);
		}

		String templateNames = jsonObject.containsKey("templateNames")?jsonObject.getString("templateNames"):"";
		Integer uesrId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):0;
		String[] templateNameList = new String[]{TableSyncEnum.PERSONAL.targetTableName,TableSyncEnum.FAMILY.targetTableName,TableSyncEnum.SPOUSE.targetTableName};

		if(StringUtils.isNotBlank(templateNames)){
			templateNameList = templateNames.split(",");
		}
		
		String jsonString = appCustomerService.getCustomerTemplateFieldJsonString(templateNameList);
		return new ResponseEntity<String>(jsonString, HttpStatus.OK);

	}

	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/saveCustomer", method = RequestMethod.POST)
	public ResponseEntity<String> saveCustomer(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("设备列表，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		JSONArray customerArray = jsonObject.containsKey("customer")?jsonObject.getJSONArray("customer"):null;
		int userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;
		Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
		
		boolean updateFlag = false;
		if(null!=id && id.intValue()>0){//修改
			updateFlag = true;
		}else{//新增
			id = customerModule.getNewId();
		}

		if(CollectionUtils.isNotEmpty(customerArray)){
			for (int i = 0; i < customerArray.size(); i++) {
				JSONObject table = customerArray.getJSONObject(i);
				String tableName = table.containsKey("tableName")?table.getString("tableName"):null;
				String columnName = table.containsKey("columnName")?table.getString("columnName"):null;
				JSONObject fields = table.containsKey("fields")?table.getJSONObject("fields"):null;

				DataTable datatable = new DataTable();
				if(StringUtils.isNotBlank(tableName)){
					tableName = TableSyncEnum.valueOfTarget(tableName).sourceTableName;
					datatable.setName(tableName);
				}

				datatable.addColumn("ID");
				datatable.newRow().set("ID",id);

				Set<?> keySet = fields.keySet();
				if(CollectionUtils.isNotEmpty(keySet)){
					for (Object key : keySet) {
						Object value = fields.get(key);
						String col = transProperty(key.toString());
						if(value instanceof String && "IDENTIFY_NUM".equalsIgnoreCase(col))
							value = IdCardUtil.toUpperCase((String)value);
						if ("SPOUSE_AGE".equals(col) || "CUSTOMER_AGE".equals(col)) {
							if(value!=null && !"".equals(value)) {
								Integer age = (Integer)TypeUtil.changeType(value,Integer.class);
								if(age <= 0){
									return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_ERROR,"年龄必须大于0"), HttpStatus.OK);
								}
							}
						}
						datatable.getRow(0).set(col,value);
					}
				}

				String customerName = fields.containsKey("customerName")?fields.getString("customerName"):"";
				String identifyType = fields.containsKey("identifyType")?fields.getString("identifyType"):"";
				String identifyNum = fields.containsKey("identifyNum")?fields.getString("identifyNum"):"";
				String customerNum = fields.containsKey("customerNum")?fields.getString("customerNum"):"";


				if("CUST_SPOUSE_INFO".equals(tableName) || "CUST_BASIC_INFO".equals(tableName)) {
					DataRow row = datatable.getRow(0);
					String idType = null;
					String idCard = null;
					String result = null;
					if ("CUST_SPOUSE_INFO".equals(tableName)) {        //配偶信息
						idType = Reader.stringReader().getValue(row, "SPOUSE_IDENTIFY_TYPE");
						idCard = Reader.stringReader().getValue(row, "SPOUSE_IDENTIFY_NUM");
					} else if ("CUST_BASIC_INFO".equals(tableName)) {        //个人信息
						idType = Reader.stringReader().getValue(row, "IDENTIFY_TYPE");
						idCard = Reader.stringReader().getValue(row, "IDENTIFY_NUM");
					}
					result = IdCardUtil.checkIdCard(idType, idCard);
					if(!"pass".equals(result)){
						return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_128), HttpStatus.OK);
					}
				}


				if(StringUtil.isNotEmpty(identifyNum))
					identifyNum = IdCardUtil.toUpperCase(identifyNum);

				if(TableSyncEnum.PERSONAL.sourceTableName.equals(tableName)){
					//客户唯一性校验
					if(StringUtil.isNotEmpty(customerName) && StringUtil.isNotEmpty(identifyType) && StringUtil.isNotEmpty(identifyNum)){
						if(!customerModule.isUniqueCustomer(String.valueOf(id),customerName,identifyType,identifyNum)) {
//							log.error("客户唯一性校验未过！");
							return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_109), HttpStatus.OK);
						}
					}
					if (StringUtil.isNotEmpty(customerNum)) {
						if (!IdCardUtil.isCheckCustomerNum(customerNum)) {
							return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_139), HttpStatus.OK);
						}
						Map<String, Object> condition = new HashedMap();
						condition.put("id", id);
						condition.put("type", 1);
						condition.put("customerNum", customerNum);
						CustBasicInfo customer = customerModule.isUniqueCustomerNum(condition);
						if(customer != null){
							//存在客户编码
							return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_138), HttpStatus.OK);
						}
					}

					datatable.addColumn("CREATE_USER");
					datatable.getRow(0).set("CREATE_USER", userId);
					datatable.addColumn("CREATE_DATE");
					datatable.getRow(0).set("CREATE_DATE",new Date());
					datatable.addColumn("IS_DEL");
					datatable.getRow(0).set("IS_DEL",0);
				}

				if(updateFlag){
					DataTable dataTable = customerModule.getDataTableById(tableName,id);
					if(dataTable!=null && dataTable.rowSize()>0) {
						customerModule.updateCustomerDataTable(datatable, "ID");
					}else{
						customerModule.insertCustomerDataTable(datatable,"ID");
					}
				}else{
					customerModule.insertCustomerDataTable(datatable,"ID");
				}
				
			}

		}else{
			log.error("请填写客户信息");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jo = new JSONObject();
		jo.put("id",id);
		return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);

	}


	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/getCustomerById", method = RequestMethod.POST)
	public ResponseEntity<String> getCustomerById(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("设备列表，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;

		JSONObject jo = new JSONObject();
		DataTable dataTable = new DataTable();
		if(null!=id){
			dataTable = customerModule.getDataTableById(TableSyncEnum.PERSONAL.sourceTableName,id);
			convertJson(jo,dataTable);

			dataTable = customerModule.getDataTableById(TableSyncEnum.FAMILY.sourceTableName,id);
			convertJson(jo,dataTable);

			dataTable = customerModule.getDataTableById(TableSyncEnum.SPOUSE.sourceTableName,id);
			convertJson(jo,dataTable);
		}else{
			jo = null;
		}

		return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);

	}

	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/getCustomerMaritalById", method = RequestMethod.POST)
	public ResponseEntity<String> getCustomerMaritalById(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("id");
		JSONObject jo = new JSONObject();
		DataTable dataTable = new DataTable();
		if(null!=id){
			dataTable = customerModule.getDataTableById(TableSyncEnum.PERSONAL.sourceTableName,Integer.parseInt(id));
			maritalJson(jo,dataTable);
			if("".equals(jo.get("MARITAL_STATUS"))){
				jo.remove("MARITAL_STATUS");
				jo.put("MARITAL_STATUS","-1");
			}
		}else{
			jo = null;
		}
		return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
	}


	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/getCustomerByTemplateAndId", method = RequestMethod.POST)
	public ResponseEntity<String> getCustomerByTemplateAndId(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("设备列表，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
		Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;
		String template = jsonObject.containsKey("template")?jsonObject.getString("template"):null;
		if(null!=id && StringUtils.isNotBlank(template)){
			String jsonString = appCustomerService.getCustomerTemplateFieldJsonString(template, id, userId);
			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
		}
	}

	private JSONObject convertJson(JSONObject jo,DataTable dataTable){

		for(int i=0;i<dataTable.rowSize();i++){
			DataRow row = dataTable.getRow(i);
			for(int j=0;j<dataTable.colSize();j++){
				String columnName = dataTable.getColumn(j).getName();
				Object val = row.get(j);
				if(val!=null){
					jo.put(columnName,val);
				}
			}
		}
		return jo;
	}

	private JSONObject maritalJson(JSONObject jo,DataTable dataTable){

		for(int i=0;i<dataTable.rowSize();i++){
			DataRow row = dataTable.getRow(i);
			for(int j=0;j<dataTable.colSize();j++){
				String columnName = dataTable.getColumn(j).getName();
				Object val = row.get(j);
				if("MARITAL_STATUS".equals(columnName)&&val!=null){
					jo.put(columnName,val);
				}
			}
		}
		return jo;
	}

	private void setValueByProperty(Object o, String propertyName,  String propertyValue) throws Exception {
//        o.getClass().getDeclaredFields();
		PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());
		Method method = pd.getWriteMethod(); //getReadMethod();
		method.invoke(o, propertyValue);
	}

	private Object getValueByProperty(Object o, String propertyName) throws Exception {
		PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());
		Method method = pd.getReadMethod();
		return method.invoke(o);
	}

	private static String transProperty(String propertyName){
		StringBuffer colBf = new StringBuffer();
		if(StringUtils.isNotBlank(propertyName)){
			for (int i = 0; i < propertyName.length(); i++) {
				if(Character.isUpperCase(propertyName.charAt(i))){
					colBf.append("_");
				}
				colBf.append(propertyName.charAt(i));
			}
		}
		return colBf.toString().toUpperCase();
	}

	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/queryCustomerScheduleList", method = RequestMethod.POST)
	public ResponseEntity<String> queryCustomerScheduleList(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null;
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询日程列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject jsonObject = JSONObject.fromObject(reqJson);
			Integer customerId = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
			Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;
			if(customerId!=null) {
				List<CustScheduleQuery> list = customerModule.getCustScheduleListByCustomerId(customerId,userId);
				//JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,customerName,phoneNumber,planType,planTypeCN,planTimeStr,planTimeStr1,state,stateCN,planRemark");
				JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,customerId,customerName,phoneNumber,planType,planTypeCN,planRate,planRateCN,planTimeStr,planTimeStr1,state,stateCN,planRemark");
				return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("查询日程列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}


	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/queryIntentProductByCustomerId", method = RequestMethod.POST)
	public ResponseEntity<String> queryIntentProductByCustomerId(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null;
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询意向客户列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject jsonObject =  JSONObject.fromObject(reqJson);
			Integer customerId = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;

			//查询 姓名 证件类型 证件号码
			String customerName = null,identifyType = null,identifyNum = null;
			if(null!=customerId){
				CustBasicInfo cust = customerModule.getBasicInfoById(customerId);
				if(null!=cust){
					customerName = cust.getCustomerName();
					identifyType = cust.getIdentifyType();
					identifyNum = cust.getIdentifyNum();
				}
			}
			IPageList<ProdIntentCustomer_Query> intentCustomerList = new PageList<ProdIntentCustomer_Query>(0);
			if(StringUtils.isNotBlank(customerName)&&StringUtils.isNotBlank(identifyType)&&StringUtils.isNotBlank(identifyNum)){
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("picIsdel", 0);
				condition.put("picCertificateType", identifyType);
				condition.put("picCertificateNum", identifyNum);
				condition.put("picName", customerName);
				intentCustomerList = productModule.queryIntentCustomerListData(condition,this.getPage());
			}

			JSONArray resArray = AppJsonUtil.toJsonArray(intentCustomerList, AppParamsConst.PARAMS_RESPONSE_QUERY_INTENT_CUSTOMER_LIST_DATA);
			return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询意向客户列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

}
