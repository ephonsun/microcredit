package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.loan.LoanType;
import banger.domain.product.ProdIntentCustomer;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.domain.product.ProdProductFile;
import banger.domain.product.ProdProduct_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ILoanApplyProvider;
import banger.moduleIntf.IProductModule;
import banger.moduleIntf.ISystemModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppProductService;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class AppProductController extends BaseController{

	private static final long serialVersionUID = 4913822165796724272L;
	
	@Autowired
	private IProductModule productModule;
	@Autowired
	private IAppProductService appProductService;
	@Autowired
	private ILoanApplyProvider loanApplyProvider;

	@Autowired
	private ISystemModule systemModule;
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryProductListData", method = RequestMethod.POST)
	public ResponseEntity<String> queryProductListData(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询产品列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_QUERY_PRODUCT_LIST_DATA);
				if(containsKeys != null)
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
			} catch (Exception e) {
				log.error("查询产品列表接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			reqObj.put("productIsdel", 0);
			//根据产品类型筛选产品
			if(reqObj.containsKey("productType")) {
				String productType = reqObj.getString("productType");
				if (StringUtils.isNotBlank(productType)) {
					String[] types = productType.split(",");
					reqObj.put("types", types);
				}
			}
			IPageSize page = PageSizeUtil.getPageLimit(reqObj,AppParamsConst.PAGE_SIZE_PROUCT_LIST);
			@SuppressWarnings("unchecked")
			List<ProdProduct_Query> productList = productModule.queryProductListData((Map<String, Object>)reqObj,page);
			//处理贷款类型被禁用
			if(productList!=null){
				for(ProdProduct_Query p :productList){
					if(p.getLoanType()!=null){
						LoanType type=loanApplyProvider.getLoanTypeById(p.getLoanType());
						if(type!=null&&type.getIsActived().intValue()==0){
							p.setLoanType(null);
							productModule.updateProduct(p,0);
						}
					}
				}
			}

			JSONArray resArray = AppJsonUtil.toJsonArray(productList, AppParamsConst.PARAMS_RESPONSE_QUERY_PRODUCT_LIST_DATA);
			return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询产品列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	/**
	 * 查询产品类型
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryProductType")
	public ResponseEntity<String> queryProductType(HttpServletRequest request,HttpServletResponse response){
		try{
			JSONObject json = systemModule.queryProductType();
			json.put("code", CodeEnum.CODE_100.getCode());
			json.put("msg", CodeEnum.CODE_100.getMsg());
			String jsonString = json.toString();
			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		}catch (Exception e){
			log.error("获取得到产品类型列表数据异常",e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getProductDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getProductDetail(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询产品详情接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			Integer id = null;
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_GET_PRODUCT_DETAIL);
				if(containsKeys != null)
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
				else
					id = Integer.valueOf(reqObj.getString("productId"));
			} catch (Exception e) {
				log.error("查询产品详情接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			ProdProduct_Query product = productModule.getProductDetail(id);
			JSONObject resObj = new JSONObject();
			if(product != null){
				resObj = AppJsonUtil.toJson(product, AppParamsConst.PARAMS_RESPONSE_GET_PRODUCT_DETAIL);
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("ppfIsdel", 0);
				condition.put("ppfProductId", id);
				List<ProdProductFile> productFiles = productModule.queryProductFileList(condition);
				JSONArray resArray = AppJsonUtil.toJsonArray(productFiles, AppParamsConst.PARAMS_RESPONSE_GET_PRODUCT_DETAIL_FILE);
				resObj.put("productFile", resArray);
			}
			return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询产品详情接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryIntentCustomerListData", method = RequestMethod.POST)
	public ResponseEntity<String> queryIntentCustomerListData(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询意向客户列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = null;
			try {
				reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_QUERY_INTENT_CUSTOMER_LIST_DATA);
				if(containsKeys != null)
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
			} catch (Exception e) {
				log.error("查询意向客户列表接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			reqObj.put("picIsdel", 0);
			IPageSize page = PageSizeUtil.getPageLimit(reqObj,AppParamsConst.PAGE_SIZE_INTENT_CUSTOMER_LIST);
			@SuppressWarnings("unchecked")
			Map<String, Object> con = (Map<String, Object>)reqObj;
			con.put("picCreateUser", con.get("userId"));
			IPageList<ProdIntentCustomer_Query> intentCustomerList = productModule.queryIntentCustomerListData(con,page);
			JSONArray resArray = AppJsonUtil.toJsonArray(intentCustomerList, AppParamsConst.PARAMS_RESPONSE_QUERY_INTENT_CUSTOMER_LIST_DATA);
			return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询意向客户列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getIntentCustomerDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getIntentCustomerDetail(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询意向客户详情接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			Integer id = null;
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_GET_INTENT_CUSTOMER_DETAIL);
				if(containsKeys != null)
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
				else
					id = Integer.valueOf(reqObj.getString("picId"));
			} catch (Exception e) {
				log.error("查询意向客户详情接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			ProdIntentCustomer intentCustomer = productModule.getIntentCustomerDetail(id);
			JSONObject resObj = new JSONObject();
			if(intentCustomer != null){
				resObj = AppJsonUtil.toJson(intentCustomer, AppParamsConst.PARAMS_RESPONSE_GET_INTENT_CUSTOMER_DETAIL);
			}
			return new ResponseEntity<String>(AppJsonResponse.success(resObj), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询意向客户详情接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/insertIntentCustomer", method = RequestMethod.POST)
	public ResponseEntity<String> insertIntentCustomer(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询添加意向客户详情接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			ProdIntentCustomer intentCustomer = null;
			Integer loginUserId = null ;
			try {
				JSONObject reqObj = JSONObject.fromObject(reqJson);
				String containsKeys = AppJsonUtil.containsKeys(reqObj, AppParamsConst.PARAMS_REQUEST_INSERT_INTENT_CUSTOMER);
				if(containsKeys == null){
					loginUserId = Integer.valueOf(reqObj.getString("userId"));
					reqObj.remove("userId");
					intentCustomer = (ProdIntentCustomer) JSONObject.toBean(reqObj,ProdIntentCustomer.class);
					intentCustomer.setPicCreateUser(loginUserId);
				}else {
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("查询意向客户详情接口，json解析出错|"+reqJson,e);
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_105), HttpStatus.OK);
			}
			JSONObject temObj = productModule.insertIntentCustomer(intentCustomer, loginUserId);
			CodeEnum codeEnum = CodeEnum.CODE_100;
			if(!temObj.getBoolean("success")){
				codeEnum = CodeEnum.CODE_108;
			}
			return new ResponseEntity<String>(AppJsonResponse.fail(codeEnum), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询意向客户详情接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadProductFileById")
	public void downloadProductFileById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String ppfId = getParameter("ppfId");
		long skipLen = StringUtils.isNotBlank(getParameter("skipLen"))?Long.parseLong(getParameter("skipLen")):0;
		log.info("下载产品文件接口,下载文件ID|"+ppfId+"skipLen="+skipLen);
		ProdProductFile productFile = productModule.getProductFileById(Integer.valueOf(ppfId));
		if(productFile == null) return;
		String path = productFile.getPpfFilePath();
		File file = new File(path);
		if (file.exists()) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/force-download");// 设置强制下载不打开
			String fileName = URLEncoder.encode(productFile.getPpfFileNameOld(),"UTF-8");
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
			long conLen = productFile.getPpfFileSize() - skipLen ;
			response.addHeader("Content-Length", conLen+"");
			OutputStream os = response.getOutputStream();
			appProductService.downloadProductFile(file, os,skipLen);
		}
	}

}
