package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.customer.CustSchedule;
import banger.domain.customer.CustScheduleQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.IScheduleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/schedule")
public class CustScheduleController extends BaseController {
	
	
	private static final long serialVersionUID = 7614150307215501551L;
	@Autowired
	private IScheduleService scheduleService;
	@Autowired
	private IPermissionModule permissionModule;


	private String BASE_PATH = "/customer/vm/schedule/";
	/**
	 * 得到日程表列表页面
	 * @return
	 */
	@RequestMapping("/getCustScheduleListPage")
	public String getCustScheduleListPage(){
		if(this.isCustomerManager()){//客户经理		
			setAttribute("myTaskCheck",true);
		}
		Integer loginUserId = getLoginInfo().getUserId();
		setAttribute("userId",loginUserId);
		//控制台参数
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = getParameter("timess");
		if(time != null){
			setAttribute("time",df.format(new Date()));
		}
		return BASE_PATH + "scheduleList";
	}

	/**
	 * 得到其他日程表列表页面
	 * @return
	 */
	@RequestMapping("/getOtherScheduleListPage")
	public String getOtherScheduleListPage(){
		if(this.isCustomerManager()){//客户经理
			setAttribute("myTaskCheck",true);
		}
		Integer loginUserId = getLoginInfo().getUserId();
		setAttribute("userId",loginUserId);
		//控制台参数
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = getParameter("timess");
		if(time != null){
			setAttribute("time",df.format(new Date()));
		}
		return BASE_PATH + "otherScheduleList";
	}

	/**
	 * 查询日程列表数据
	 * @return
	 */
	@RequestMapping("/queryCustScheduleListData")
	@ResponseBody
	public void queryCustScheduleListData(CustScheduleQuery custScheduleQuery){
		String scheduleType = this.getRequest().getParameter("scheduleType");
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("customerLevel", custScheduleQuery.getCustomerLevel());
		condition.put("customerName", custScheduleQuery.getCustomerName());
		if(scheduleType != null){
			condition.put("scheduleType",Integer.parseInt(scheduleType));
		}
		//工作台参数
		String startDate = getParameter("startDate");
		String endDate = getParameter("endDate");
		if(startDate != null && !"".equals(startDate)){
			condition.put("startDate", getParameter("startDate"));
		}else{
			condition.put("startDate", custScheduleQuery.getStartDate());
		}
		if(endDate != null && !"".equals(endDate)){
			condition.put("endDate", getParameter("endDate"));
		}else{
			condition.put("endDate", custScheduleQuery.getEndDate());
		}
//		condition.put("startDate", custScheduleQuery.getStartDate());
//		condition.put("endDate", custScheduleQuery.getEndDate());
		condition.put("isOverDate", String.valueOf(custScheduleQuery.getIsOverDate()));
		condition.put("planRate", custScheduleQuery.getPlanRate());
		condition.put("state", custScheduleQuery.getState());
		condition.put("nowDate", DateUtil.format(new Date(),DateUtil.DEFAULT_DATEMINUTE_FORMAT));
		
		Integer loginUserId = getLoginInfo().getUserId();
		String teamGroupIds=getLoginInfo().getTeamGroupIdByRole(false);
		condition.put("createUser", loginUserId);
//		if (StringUtils.isBlank(teamGroupIds)) {
//			teamGroupIds = "-1";
//		}
//		if (custScheduleQuery.getMySchedule() == null) {
//			String selectMy = this.getRequest().getParameter("selectMy");
//			if ("0".equals(selectMy)) {
//				condition.put("teamGroupIds", teamGroupIds);
//			}
//		} else if(custScheduleQuery.getMySchedule()==0){
//			condition.put("teamGroupIds", teamGroupIds);
//		}


		if(scheduleType != null && Integer.parseInt(scheduleType) == 1){
		IPageList<CustScheduleQuery> custScheduleList = scheduleService.queryCustScheduleList(condition, this.getPage());
		renderText(JsonUtil.toJson(custScheduleList, "id","customerId,customerLevel,customerLevelCN,customerName,customerSex,customerSexCN," +
				"customerAgeStr,phoneNumber,planTimeStr,planRate,planRateCN,planTypeCN,planType,planRemark,state,createUser").toString());
		}else if(scheduleType != null && Integer.parseInt(scheduleType) == 0){
			IPageList<CustScheduleQuery> custScheduleList = scheduleService.queryScheduleList(condition, this.getPage());
			renderText(JsonUtil.toJson(custScheduleList, "id","planTimeStr,planRemark,state,createUser").toString());
		}else{
			IPageList<CustScheduleQuery> custScheduleList = scheduleService.queryCustScheduleList(condition, this.getPage());
			renderText(JsonUtil.toJson(custScheduleList, "id","customerId,customerLevel,customerLevelCN,customerName,customerSex,customerSexCN," +
					"customerAgeStr,phoneNumber,planTimeStr,planRate,planRateCN,planTypeCN,planType,planRemark,state,createUser").toString());
		}
	}

	/**
	 * 跳转到客户日程标签页面
	 * @return
	 */
	@RequestMapping("/getCustScheduleTabPage")
	public String getCustScheduleTabPage(){
		String customerId=getParameter("customerId");
		setAttribute("customerId",customerId);
		String flag=getParameter("flag");
		setAttribute("flag",flag);
		
		Integer loginUserId = getLoginInfo().getUserId();
		setAttribute("userId",loginUserId);
		return BASE_PATH + "scheduleTab";
	}
	
	/**
	 * 查询单个客户任务表列表数据
	 * @return
	 */
	@RequestMapping("/queryOneCustScheduleList")
	@ResponseBody
	public void queryOneCustScheduleList(CustScheduleQuery custScheduleQuery){
		Map<String,Object> condition = new HashMap<String,Object>();
				
		Integer loginUserId = getLoginInfo().getUserId();		
		condition.put("createUser", loginUserId);
		Integer customerId=Integer.valueOf(getParameter("customerId"));
		condition.put("customerId", customerId);
		
		IPageList<CustScheduleQuery> custScheduleList = scheduleService.queryScheduleList(condition, this.getPage());
		renderText(JsonUtil.toJson(custScheduleList, "id","customerId," +
				",planTimeStr,planTypeCN,planRemark,planRate,planRateCN,state,createUser").toString());
	}

	/**
	 * 得到日程修改页面
	 * @return
	 */
	@RequestMapping("/getCustScheduleAddPage")
	public String getCustScheduleAddPage(){
		String customerId=getParameter("customerId");
		setAttribute("customerId",customerId);
		return BASE_PATH + "scheduleAdd";
	}

	/**
	 * 得到其他日程修改页面
	 * @return
	 */
	@RequestMapping("/getOtherScheduleAddPage")
	public String getOtherScheduleAddPage(){
		String customerId=getParameter("customerId");
		setAttribute("customerId",customerId);
		return BASE_PATH + "otherScheduleEdit";
	}

	/** 
     * form表单提交 Date类型数据绑定 
     * <功能详细描述> 
     * @param binder 
     * @see [类、类#方法、类#成员] 
     */  
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}	
	
	/**
	 * 添加日程数据
	 * @return
	 */
	@RequestMapping("/addCustSchedule")
	@ResponseBody
	public void addCustSchedule(CustSchedule schedule){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			scheduleService.insertSchedule(schedule, loginUserId);
			renderText(true, "添加日程成功！", null);
			return;
		} catch (Exception e) {
			log.error("添加日程报错", e);
		}
		renderText(false, "添加日程失败！", null);
		return;
	}
	
	/**
	 * 得到日程修改页面
	 * @return
	 */
	@RequestMapping("/getCustScheduleUpdatePage")
	public String getCustScheduleUpdatePage(){
		String scheduleId = getParameter("scheduleId");
		String scheduleType = getParameter("scheduleType");
		CustSchedule custSchedule = scheduleService.getScheduleById(Integer.valueOf(scheduleId));
		setAttribute("custSchedule",custSchedule);
		setAttribute("planTime",DateUtil.format(custSchedule.getPlanTime(), "yyyy-MM-dd HH:mm"));
		if(scheduleType != null && Integer.parseInt(scheduleType) == 0){
			return BASE_PATH + "otherScheduleEdit";
		}
		return BASE_PATH + "scheduleEdit";
	}


	/**
	 * 修改日程数据
	 * @return
	 */
	@RequestMapping("/updateCustSchedule")
	@ResponseBody
	public void updateCustSchedule(CustSchedule schedule){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			if(schedule.getId() != null){
				scheduleService.updateSchedule(schedule, loginUserId);
				renderText(true, "编辑日程成功！", "editSchedule");
			}else{
				schedule.setScheduleType(0);
				scheduleService.insertSchedule(schedule, loginUserId);
				renderText(true, "添加成功！", "addSchedule");
			}
			return;
		} catch (Exception e) {
			if(schedule.getId() != null){
				log.error("修改日程报错", e);
			}else{
				log.error("添加日程报错", e);
			}

		}
		if(schedule.getId() != null){
			renderText(false, "编辑日程失败！", null);
		}else{
			renderText(false, "添加日程失败！", null);
		}
		return;
	}

}
