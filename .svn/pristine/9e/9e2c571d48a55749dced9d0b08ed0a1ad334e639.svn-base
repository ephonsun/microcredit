package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.session.ValidateCodeManage;
import banger.common.annotation.NoTokenAnnotation;
import banger.domain.permission.PmsFunc;
import banger.domain.permission.PmsMenu;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUser;
import banger.domain.system.SysBasicConfig;
import banger.domain.system.SysMobileInfo;
import banger.framework.util.DateUtil;
import banger.framework.util.JwtUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IBasicConfigProvider;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.IPremissionMenu;
import banger.moduleIntf.ISystemModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
public class AppLoginController extends BaseController{

	private static final long serialVersionUID = 6071923859857186541L;
		
	@Autowired
	private IPermissionModule permissionModule;

	@Autowired
	private ISystemModule systemModule;

	@Autowired
	private IPremissionMenu premissionMenu;

	@Autowired
	private IBasicConfigProvider basicConfigProvider;

	@Value("${file_upload_port}")
	private String fileUploadPort;
	@Value("${map.app.use.www}")	//app地图是否使用www（万维网）外网
	private boolean mapUsewww;
	@Value("${map.use.ruitu}")
	private boolean useRuitu;

	@RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)
	@NoLoginInterceptor
	@NoTokenAnnotation
	public ResponseEntity<String> appLogin(HttpServletRequest request,HttpServletResponse response){
		String reqJson=HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("注册接口，参数为空");
			//组装返回失败json
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}
		
		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		final String account=jsonObject.getString("account");
		String password=jsonObject.getString("password");
		
		if(StringUtil.isNullOrEmpty(account)){
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_107), HttpStatus.OK);
		}else if(StringUtil.isNullOrEmpty(password)){
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_107), HttpStatus.OK);
		}else{
			Integer errorCount = ValidateCodeManage.getErrorCount(account);
			if(errorCount>2){
				String requestId = null;
				if(jsonObject.containsKey("requestId")){
					requestId =jsonObject.getString("requestId");
				}
				String validateCode = null;
				if(jsonObject.containsKey("validateCode")){
					validateCode =jsonObject.getString("validateCode");
				}
				if(StringUtil.isNullOrEmpty(validateCode) || StringUtil.isNullOrEmpty(requestId)){
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_152), HttpStatus.OK);
				}
				if(!ValidateCodeManage.valid(requestId, validateCode)){
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_153), HttpStatus.OK);
				}
			}
		}
		
		//查询数据库用户是否存在
		//验证用户名 密码
		String result = "账号或密码错误";
		if(!StringUtils.isEmpty(account)&&!StringUtils.isEmpty(password)){
			result = permissionModule.login(account, password);
		}
		
		if("needRandNum".equals(result)){		//验证超过次数，需要验证码
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_154), HttpStatus.OK);
		}
		
		String userName = "";
		Integer userId = 0;
		String serialNo = jsonObject.containsKey("serialNo")?jsonObject.getString("serialNo"):"";
		String result1 = isLogin(serialNo,account);
		if(result1 != null){
			result = result1;
		}
		if (result.equals("success")) {
			//记录设备信息
//			String serialNo = jsonObject.containsKey("serialNo")?jsonObject.getString("serialNo"):"";
			String brand = jsonObject.containsKey("brand")?jsonObject.getString("brand"):"";
			String model = jsonObject.containsKey("model")?jsonObject.getString("model"):"";
			String sysVersion = jsonObject.containsKey("sysVersion")?jsonObject.getString("sysVersion"):"";
			String appVersion = jsonObject.containsKey("appVersion")?jsonObject.getString("appVersion"):"";
			String loginLongitude = jsonObject.containsKey("loginLongitude")?jsonObject.getString("loginLongitude"):"";
			String loginLatitude = jsonObject.containsKey("loginLatitude")?jsonObject.getString("loginLatitude"):"";
			String loginIp = jsonObject.containsKey("loginIp")?jsonObject.getString("loginIp"):"";

			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("userAccount",account);
			condition.put("userIsdel","0");
			PmsUser user = permissionModule.getPmsUserByAccount(condition);

			userId = user.getUserId();
			userName = user.getUserName();

			List<PmsRole> roles=permissionModule.getRoleIdByUID(userId);
			//根据用户 id 获取用户角色

			String roleIds = "";
			for (PmsRole pmsRole : roles) {
				if(roleIds.equals("")){
					roleIds = pmsRole.getRoleId()+"";
				}else{
					roleIds += ","+pmsRole.getRoleId();
				}
			}

			if(!StringUtils.isEmpty(serialNo)){
				systemModule.saveSysMobileInfo(serialNo,brand,model,sysVersion,appVersion,userId,loginLongitude,loginLatitude,loginIp);
			}
			JSONObject json = new JSONObject();
			json.put("userId", userId);
			json.put("userAccount", account);
			json.put("userName", userName);

			json.put("loginDate", DateUtil.format(Calendar.getInstance().getTime(), DateUtil.DEFAULT_DATE_FORMAT));
			JSONArray menuArray = initMenu(json,roleIds);
			json.put("roleMenu",menuArray);
			if(fileUploadPort == null){
				json.put("fileUploadPort","41414");  //上传端口为空，设置默认值为41414
			}else{
				json.put("fileUploadPort",fileUploadPort);
			}

			List<PmsFunc> funcList = permissionModule.getFuncsByRoleIds(roleIds);

			initExtendParams(json);
			initAppMenuAndFunc(json,roles,funcList);
			
			String tokenStr = JwtUtil.generToken(userId.toString(), "banger", "micro");
			json.put("token", tokenStr);
			response.addHeader("token", tokenStr);
			response.addHeader("userId", userId.toString());
			
			return new ResponseEntity<String>(AppJsonResponse.success(json), HttpStatus.OK);
		}else if (result.equals("账号或密码错误")) {
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_107), HttpStatus.OK);
		}else if (result.equals("用户已停用")) {
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_106), HttpStatus.OK);
		}else if(result.equals("该设备已经被禁用")){
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_140), HttpStatus.OK);
		}else if(result.equals("该设备已经被绑定")){
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_141), HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_107), HttpStatus.OK);
		}
	}

	/**
	 * 初始化额外参数
	 */
	private void initExtendParams(JSONObject json){
		//默认设置地图有关参数
		json.put("collectGPSRate",30);			//经纬度采集时间间隔
		json.put("updateGPSRate",300);			//经纬度上传时间间隔
		json.put("fixedPosition",2);			//强制定位,1开启,2关闭
		json.put("activeTime",600);			//经纬度有效时间
		//数据库对地图参数进行确认
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("configType",4);//3表示地图参数记录
		List<SysBasicConfig> sysBasicConfigList =basicConfigProvider.querySysBasicConfigList(condition);
		for (SysBasicConfig basicConfig : sysBasicConfigList) {
			if ("qzdw".equals(basicConfig.getBasicConfigKey())) {
				json.put("fixedPosition",basicConfig.getConfigStatus());
			}else if("jwdcjzq".equals(basicConfig.getBasicConfigKey())){
				json.put("collectGPSRate",basicConfig.getConfigValue());
			}else if("jwdsqzq".equals(basicConfig.getBasicConfigKey())){
				json.put("updateGPSRate",basicConfig.getConfigValue());
			}else if("jwdyxsj".equals(basicConfig.getBasicConfigKey())){
				json.put("activeTime",basicConfig.getConfigValue());
			}
		}
	}

	/**
	 * App首界面顶部菜单项显示初始化
	 * @param json   返回给App的数据
	 * @param roles    用户id
	 */
	private void initAppMenuAndFunc(JSONObject json,List<PmsRole> roles,List<PmsFunc> funcList){
		json.put("countShow",false);
		json.put("taskShow",false);
		json.put("creaditIsEditable",false);			//征信调查
		json.put("loanFileIsEditable",true);			//贷款资料
		json.put("preLoanApply",false);								//预申请
		json.put("loanStep",false);						//流程步骤
		json.put("loanAssert",false);                   //三表
		json.put("mapUsewww", useRuitu ? true : mapUsewww);
		json.put("useRuitu", useRuitu);
		for(PmsRole role : roles){
			Integer rid = role.getRoleId();
			if(rid == 3) {
				//团队主管
				json.put("countShow",true);
			}
			if(rid == 4){
				//客户经理
				json.put("countShow",true);
				json.put("taskShow",true);
				json.put("creaditIsEditable",true);
				json.put("loanFileIsEditable",true);
				json.put("preLoanApply",true);
				json.put("loanStep",true);
				json.put("loanAssert",true);
			}
		}
		json.put("countShow",true);
		json.put("taskShow",true);
		JSONArray ja = new JSONArray();
		for(PmsFunc pmsFunc : funcList){
			if(filterFunc(pmsFunc)) {
				JSONObject jo = new JSONObject();
				jo.put("funcId", pmsFunc.getFuncId());
				jo.put("enable", true);
				ja.add(jo);
			}
		}
		json.put("funcs",ja);


	}

	/**
	 * app要过滤的菜单
	 * @param pmsFunc
	 * @return
	 */
	private boolean filterFunc(PmsFunc pmsFunc){
		if(!"func".equals(pmsFunc.getFuncParentId())){
			if("loanModule".equals(pmsFunc.getFuncModule()) && !"loanManager".equals(pmsFunc.getFuncParentId())){
				return true;
			}else if("potentialCustomerModule".equals(pmsFunc.getFuncModule())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化App首界面用户权限菜单项
	 * @param json  返回给App的数据
	 * @param roleIds   用户id
	 * @return
	 */
	private JSONArray initMenu(JSONObject json, String roleIds){
		json.put("isPotentialCustomer",false);   //潜在客户
		JSONArray jsonArray = new JSONArray();
		Set<String> menuSet = new HashSet<String>();
		//根据用户角色获得用户菜单
		List<PmsMenu> menuLists = premissionMenu.getMenuByRoleIds(roleIds);
		for(PmsMenu menu : menuLists) {
			//判断用户是否具有潜在客户，如果存在则加入json
			if("customerPotentialListPage".equals(menu.getMenuId())){
				json.put("isPotentialCustomer",true);
			}
			//判断是否是贷款管理菜单项下的目录
			if ("loanManage".equals(menu.getMenuParentId()) && selectMenu(menu.getMenuId())) {
				//获取贷款管理下的菜单项,拼接json并存入数组
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", menu.getMenuId());
				jsonObject.put("display", menu.getMenuName());
				jsonObject.put("show", true);
				if (!menuSet.contains(menu.getMenuId())) {
					menuSet.add(menu.getMenuId());    //去重
					if(jsonObject.get("display").equals("进件分配")){
					}else{
						jsonArray.add(jsonObject);
					}
				}
			}
		}
		return jsonArray;
	}

	/**
	 * 过滤菜单项
	 * @param menuId
	 * @return
	 */
	private boolean selectMenu(String menuId){
		String[] menuIds = {"loanAllLoanList","loanAfterLoanList","loanAuditManage","groupCustomerList"};//所有贷款 放款 贷后 审计 预申请分配
		for(int i = 0;i<menuIds.length;i++){
			if(menuIds[i].equals(menuId)){
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断设备是否禁用、是否绑定
	 */
	private String isLogin(String serialNo,String account){
		String result = null;
		SysBasicConfig config = basicConfigProvider.getSysBasicConfigByKey("yhbd");
		SysMobileInfo mobileInfo = systemModule.getMobileInfoById(serialNo);
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("userAccount",account);
		PmsUser user = permissionModule.getPmsUserByAccount(condition);
		if(mobileInfo != null){
			if(mobileInfo.getIsActived()==2){
				return "该设备已经被禁用";
			}else if(config!=null&&"1".equals(config.getConfigStatus())){		//用户绑定 1：绑定开启 2：绑定关闭
				if(mobileInfo.getIsBind()==1 && user.getUserId()!=mobileInfo.getLastUserId()){
					return "该设备已经被绑定";
				}
			}
		}
		return null;
	}
}
