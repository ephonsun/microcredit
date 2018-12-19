package banger.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.customer.CustMarketCustomerQuery;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;

@Controller
@RequestMapping("/api")
public class AppMarketCustomerController extends BaseController{

	private static final long serialVersionUID = 4913822165796724272L;
	
	@Resource
	private ICustomerModuleIntf customerModule;
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryMarketCustomerList", method = RequestMethod.POST)
	public ResponseEntity<String> queryMarketCustomerList(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询客户预申请列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
					
			IPageSize page = PageSizeUtil.getPageLimit(reqObj,10);
			@SuppressWarnings("unchecked")
			List<CustMarketCustomerQuery> custList = customerModule.queryMemberMarketCustomerList((Map<String, Object>)reqObj,page);
			JSONArray resArray = AppJsonUtil.toJsonArray(custList, "marketCustomerId,customerName," +
					"phoneNumber,phoneNumberFormat,cardType,cardTypeName,cardNumber,cardNumberFormat,loanMoney,loanMoneyFormat,loanUse");
			return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询客户预申请列表异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	/**
	 * 对分配给客户经理的预申请客户进行 作废、转申请操作
	 * @param request
	 * @param response
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/dealCustomer", method = RequestMethod.POST)
	public ResponseEntity<String> dealCustomer(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("保存预申请客户接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
			
			Integer operator=reqObj.getInt("operator");
			Integer marketCustomerId=reqObj.getInt("marketCustomerId");
			if(operator==1){
				customerModule.deleteCustMarketCustomerById(marketCustomerId);
			}else{//转申请
				
			}
					
			return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("保存预申请客户接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	
}
