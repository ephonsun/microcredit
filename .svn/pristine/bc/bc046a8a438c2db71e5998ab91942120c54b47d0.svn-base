package banger.controller;

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

import banger.action.AppBaseController;
import banger.common.BaseController;
import banger.common.annotation.TokenRepeatAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.customer.CustSchedule;
import banger.domain.customer.CustScheduleQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.JwtUtil;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;

@Controller
@RequestMapping("/api")
public class AppScheduleController extends AppBaseController {

	private static final long serialVersionUID = -1601612316032019918L;
	@Resource
	private ICustomerModuleIntf customerModule;

	public static final String SAVE_LOAN_SCHEDULE_PARAMS = "customerId,planType,planRate,planTime,state,userId,customerId";
	public static final String SAVE_LOAN_SCHEDULE_PARAMS_TYPE = "planTime,state,userId,planRemark,scheduleType";


	@NoLoginInterceptor
	@RequestMapping(value = "/v1/queryScheduleList", method = RequestMethod.POST)
	public ResponseEntity<String> queryScheduleList(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("查询日程列表接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
			Object userId = reqObj.get("userId");
			reqObj.put("createUser", userId);
			if(reqObj.getInt("scheduleType")==0){
				IPageSize page = PageSizeUtil.getPageLimit(reqObj,10,true);
				@SuppressWarnings("unchecked")
				IPageList<CustScheduleQuery> list= customerModule.queryOtherScheduleListForApp((Map<String, Object>)reqObj,page);
				JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,customerId,customerName,phoneNumber,planType,planTypeCN,planRate,planRateCN,planTimeStr,planTimeStr1,state,stateCN,planRemark");
				return new ResponseEntity<String>(AppJsonResponse.success(resArray,page.getTotal()), HttpStatus.OK);
			}
			IPageSize page = PageSizeUtil.getPageLimit(reqObj,10,true);

			@SuppressWarnings("unchecked")
			IPageList<CustScheduleQuery> list= customerModule.queryCustScheduleListForApp((Map<String, Object>)reqObj,page);
			JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,customerId,customerName,phoneNumber,planType,planTypeCN,planRate,planRateCN,planTimeStr,planTimeStr1,state,stateCN,planRemark");
			return new ResponseEntity<String>(AppJsonResponse.success(resArray,page.getTotal()), HttpStatus.OK);
		} catch (Exception e) {
			log.error("查询日程列表接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}
	
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/upateSchedule", method = RequestMethod.POST)
	public ResponseEntity<String> upateSchedule(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null; 
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("保存日程接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
			
			CustSchedule schedule=new CustSchedule();
			schedule.setId(reqObj.getInt("id"));
			schedule.setPlanType(reqObj.getString("planType"));
			schedule.setPlanRate(reqObj.getString("planRate"));
			schedule.setPlanTime(DateUtil.parser(reqObj.getString("planTime"), "yyyy-MM-dd HH:mm"));
			schedule.setState(reqObj.getInt("state"));
			int loginUserId=reqObj.getInt("userId");
			int customerId=reqObj.getInt("customerId");
			schedule.setPlanRemark(reqObj.getString("planRemark"));
			customerModule.updateSchedule(schedule, loginUserId);
			return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
		} catch (Exception e) {
			log.error("保存日程接口异常|"+reqJson,e);
		}
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

	@NoLoginInterceptor
	@TokenRepeatAnnotation
	@RequestMapping(value = "/v1/saveSchedule", method = RequestMethod.POST)
	public ResponseEntity<String> saveSchedule(HttpServletRequest request,HttpServletResponse response){
		String reqJson = null;
		try {
			reqJson=HttpParseUtil.getJsonStr(request);
			if(StringUtils.isBlank(reqJson)){
				log.error("保存日程接口，参数为空");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
			}
			JSONObject reqObj = JSONObject.fromObject(reqJson);
			String containsKeys = null;
			if(reqObj.getInt("scheduleType")==0){
				containsKeys = AppJsonUtil.containsKeys(reqObj, SAVE_LOAN_SCHEDULE_PARAMS_TYPE);
			}else{
				containsKeys = AppJsonUtil.containsKeys(reqObj, SAVE_LOAN_SCHEDULE_PARAMS);
			}
			if(containsKeys == null) {
				CustSchedule schedule = new CustSchedule();
				Integer id = 0;
				if (reqObj.containsKey("id"))
					id = reqObj.getInt("id");
				schedule.setId(id);
				if(reqObj.getInt("scheduleType")==1){
					schedule.setPlanType(reqObj.getString("planType"));
					schedule.setPlanRate(reqObj.getString("planRate"));
					int customerId = reqObj.getInt("customerId");
					schedule.setCustomerId(customerId);
				}
				schedule.setPlanTime(DateUtil.parser(reqObj.getString("planTime"), "yyyy-MM-dd HH:mm"));
				schedule.setState(reqObj.getInt("state"));
				//int loginUserId = reqObj.getInt("userId");
				schedule.setPlanRemark(reqObj.getString("planRemark"));
				int scheduleType = reqObj.getInt("scheduleType");
				schedule.setScheduleType(scheduleType);
				Integer loginUserId = this.getUserId();
				if(loginUserId!=null){
					if (id.intValue() > 0) {
						CustSchedule custScheduleQuery = customerModule.getScheduleById(id);
						if(custScheduleQuery!=null && custScheduleQuery.getCreateUser()!=null && !custScheduleQuery.getCreateUser().equals(loginUserId)){
							return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_99), HttpStatus.OK);
						}
						customerModule.updateSchedule(schedule, loginUserId);
					} else {
						customerModule.insertSchedule(schedule, loginUserId);
					}
				}else{
					return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_97), HttpStatus.OK);
				}
				this.getResponse().setHeader("result", "success");
				return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
			}else{
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102,containsKeys+"参数不能为空！"), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("保存日程接口异常|"+reqJson,e);
		}
		
		return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
	}

}
