package banger.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.constant.AppParamsConst;
import banger.domain.system.SysDataDictCol;
import banger.domain.system.SysMobileInfo;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.ISystemModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AppSystemController extends BaseController{


	private static final long serialVersionUID = -4946082010218959983L;

	@Autowired
	private ISystemModule systemModule;

	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/queryMobileList", method = RequestMethod.POST)
	public ResponseEntity<String> appLogin(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("设备列表，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		String serialNo= jsonObject.containsKey("serialNo")?jsonObject.getString("serialNo"):"";
		String brand=jsonObject.containsKey("brand")?jsonObject.getString("brand"):"";
		String model=jsonObject.containsKey("model")?jsonObject.getString("model"):"";
		String sysVersion=jsonObject.containsKey("sysVersion")?jsonObject.getString("sysVersion"):"";
		String appVersion=jsonObject.containsKey("appVersion")?jsonObject.getString("appVersion"):"";
		String userName=jsonObject.containsKey("userName")?jsonObject.getString("userName"):"";

		IPageList<SysMobileInfo> mobileInfoList = systemModule.queryMobileList(serialNo, brand, model, sysVersion, appVersion, userName, getPage());
		return new ResponseEntity<String>(AppJsonResponse.success(JsonUtil.toJson(mobileInfoList, "", "serialNo,userName,lastLoginTime,mobileBrand,lastUserId,systemVersion,mobileModel")), HttpStatus.OK);
	}

//	private IPageSize getPage(JSONObject json){
//		IPageSize page = new PageSize();
//		String pageNum=json.containsKey("pageNum")?json.getString("pageNum"):"";
//		if(StringUtils.isNotBlank(pageNum)&&StringUtils.isNumeric(pageNum)){
//			page.setPageNum(Integer.valueOf(pageNum));
//		}
//		String pageSize=json.containsKey("pageSize")?json.getString("pageSize"):"";
//		if(StringUtils.isNotBlank(pageSize)&&StringUtils.isNumeric(pageSize)){
//			page.setPageSize(Integer.valueOf(pageSize));
//		}
//		return page;
//	}
	
	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/queryDataDictColListByName", method = RequestMethod.POST)
	public ResponseEntity<String> queryDataDictColListByName(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson) || "[]".equals(reqJson)){
				log.error("查询数据字典接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			String name = null;
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_QUERY_DATA_DICT_COL_LIST_BY_NAME);
				if(containsKeys != null)
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
				else
					name = reqObj.getString("dataDictName");
			} catch (Exception e) {
				log.error("查询数据字典接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			if("CD_CUSTOMER_DEGREE".equals(name))
				name = "CD_DEGREE";
			List<SysDataDictCol> dataDictCols = systemModule.queryDataDictColListByName(name);
			JSONArray resArray = AppJsonUtil.toJsonArray(dataDictCols, AppParamsConst.PARAMS_RESPONSE_QUERY_DATA_DICT_COL_LIST_BY_NAME);
			return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询数据字典接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	@NoLoginInterceptor
	@RequestMapping(value = "/api/v1/queryDataDictColListByNames", method = RequestMethod.POST)
	public ResponseEntity<String> queryDataDictColListByNames(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson) || "[]".equals(reqJson)){
				log.error("查询数据字典列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONArray array = null;
			try {
				array = JSONArray.fromObject(reqJson);
			} catch (Exception e) {
				log.error("查询数据字典列表接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			JSONObject resObj = new JSONObject();
			for (Object temp : array) {
				List<SysDataDictCol> dataDictCols = systemModule.queryDataDictColListByName((String)temp);
				JSONArray tempArray = AppJsonUtil.toJsonArray(dataDictCols, AppParamsConst.PARAMS_RESPONSE_QUERY_DATA_DICT_COL_LIST_BY_NAME);
				resObj.put(temp, tempArray);
			}
			return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询数据字典列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

}
