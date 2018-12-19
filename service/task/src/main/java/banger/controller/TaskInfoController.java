package banger.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.permission.ILoginInfo;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ILoanTaskProvider;
import banger.service.intf.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTaskInfoQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IPermissionModule;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 任务表页面访问类
 */
@Controller
@RequestMapping("/task")
public class TaskInfoController extends BaseController {
	private static final long serialVersionUID = 7343642084905296542L;
	@Autowired
	private ITaskInfoService taskInfoService;
	@Autowired
	private IPermissionModule permissionModule;
	@Autowired
	private IGroupTaskAssignService groupTaskAssignService;
	@Autowired
	private ITeamMemberTaskAssignService teamMemberTaskAssignService;
	@Autowired
	private ILoanTaskService loanTaskService;
	@Autowired
	private IMarketingTaskService marketingTaskService;

	/**
	 * 得到任务表列表页面
	 * @return
	 */
	@RequestMapping("/getTaskInfoListPage")
	public String getTaskInfoListPage(){
		Integer loginUserId = getLoginInfo().getUserId();
		Integer groupId = getLoginInfo().getTeamGroupId();
		Integer roleId = getLoginInfo().getTeamRoleId();
		setAttribute("roleId",roleId);
		setAttribute("userId",loginUserId);
		setAttribute("groupId",groupId);
		if(this.isCustomerManager()){//客户经理		
			setAttribute("myTaskCheck",true);
			setAttribute("assignBtn",2);
		}else{
			setAttribute("assignBtn",1);
		}

		//工作台跳转参数
		String taskStatus = getParameter("taskStatus");
		setAttribute("taskStatus",taskStatus);
		/*if(taskStatus != null){
			TskTaskInfo taskInfo = new TskTaskInfo();
			taskInfo.setTaskStatus(Integer.valueOf(taskStatus));
			setAttribute("task",taskInfo);
			setAttribute("myTaskCheck",1);
		}*/

		//跳转个人任务页面还是团队任务页面
		String taskType = getParameter("taskType");
		if("myTaskPage".equals(taskType))
			//我执行的任务
			return "task/vm/myTaskList";
		//团队执行的任务
		return "/task/vm/taskList";
	}

	/**
	 * 查询任务表列表数据
	 * @return
	 */
	@RequestMapping("/queryTaskInfoListData")
	@ResponseBody
	public void queryTaskInfoListData(TskTaskInfoQuery task){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("taskTitle", task.getTaskTitle());
		condition.put("startDate", task.getStartDate());
		condition.put("endDate", task.getEndDate());
		//工作台参数
		String taskStatus = getParameter("taskStatus");
		if(taskStatus != null && !"".equals(taskStatus)){
			condition.put("taskStatus", getParameter("taskStatus"));
		}else{
			condition.put("taskStatus", task.getTaskStatus());
		}
		condition.put("isOverDate", String.valueOf(task.getIsOverDate()));
		condition.put("nowDate", DateUtil.format(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
		ILoginInfo loginInfo = getLoginInfo();
		Integer loginUserId = loginInfo.getUserId();
		String teamGroupId = loginInfo.getTeamGroupIdByRole(false);

		condition.put("createUser", loginUserId);
		IPageList<TskTaskInfoQuery> taskInfoList=null;
		if (StringUtils.isBlank(teamGroupId)) {
			teamGroupId = "-1";
		}
		String selectMy = this.getRequest().getParameter("selectMy");
		if (task.getMyTask() == null) {
			if ("1".equals(selectMy)) {
				condition.put("assignUserId", loginUserId);
				if(!isCustomerManager()){
					condition.put("createUser", null);
				}
			} else {
				condition.put("teamGroupId", teamGroupId);
			}
		} else if(task.getMyTask()==1){
			if (isTeamManager()) {
				condition.put("teamGroupId", loginInfo.getTeamGroupId());
			}
			condition.put("assignUserId", loginUserId);
		} else if(task.getMyTask()==0){//查询分配给本团队任务
			condition.put("teamGroupId", teamGroupId);
		} else {
		}
		//主管之间的任务互看
		if(isMutilTeamManager()){
			if(!"1".equals(selectMy)){
				condition.put("tskLevel", 1);
			}
		}
		taskInfoList = taskInfoService.queryTaskInfoList(condition, this.getPage());
		renderText(JsonUtil.toJson(taskInfoList, "taskId","taskTitle,taskDateLimit,remark,taskMold,tskLevel,target,createUserName,teamGroupId,taskPercent,createUser,taskStatus").toString());
	}

	/**
	 * 得到任务表新增页面
	 * @return
	 */
	@RequestMapping("/getTaskInfoInsertPage")
	public String getTaskInfoInsertPage(HttpServletRequest request){
		String taskType = getParameter("taskType");
		request.setAttribute("taskType",taskType);
		return "/task/vm/taskAdd";
	}

	/**
	 * 得到任务表修改页面
	 * @return
	 */
	@RequestMapping("/getTaskInfoUpdatePage")
	public String getTaskInfoUpdatePage(){
		String taskId = getParameter("taskId");
		String targetNum = getParameter("targetNum");
		String isShow = getParameter("isShow");
		TskTaskInfo taskInfo = taskInfoService.getTaskInfoById(Integer.parseInt(taskId));
		if(taskInfo != null && taskInfo.getTaskTarget() != null){
			DecimalFormat myformat = new DecimalFormat();
			myformat.applyPattern("##,###");
			if (StringUtil.isNotEmpty(targetNum)) {
				setAttribute("target",targetNum);
			}else{
				setAttribute("target",myformat.format(taskInfo.getTaskTarget()));
			}
		}
		if ("true".equals(isShow)) {
			taskInfo.setRemark(taskInfo.getRemark().replace("\r\n","<br>"));
			taskInfo.setRemark(taskInfo.getRemark().replace("\n","<br>"));
		}
		setAttribute("task",taskInfo);
		setAttribute("isShow",isShow);
		return "/task/vm/taskEdit";
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
	} 	

	/**
	 * 新增任务表数据
	 * @return
	 */
	@RequestMapping(value = "/insertTaskInfo", method = RequestMethod.POST)
	@ResponseBody
	public void insertTaskInfo(TskTaskInfo task){		
		try {
			Integer teamGroupId=getLoginInfo().getTeamGroupId();
			if ((teamGroupId == null || teamGroupId.intValue() == 0) && !this.isMutilTeamManager()) {
				renderText(false, "该用户无团队，不能创建任务！", null);
				return;
			}
			task.setTeamGroupId(teamGroupId);
			Integer loginUserId = getLoginInfo().getUserId();
			if(this.isMutilTeamManager()){
				task.setTskLevel(1);
			}else if(this.isTeamManager()){
				task.setTskLevel(2);
				task.setTaskAssign(task.getTaskTarget());
			}else{
				task.setTskLevel(3);
			}

			task.setEndDate(weeHours(task.getEndDate(),1));
			taskInfoService.insertTaskInfo(task,loginUserId);
			
			//如果是团队主管新建任务，在团队任务分配表默认插入一条数据
			if(this.isTeamManager()){
				TskGroupTaskAssign assign=new TskGroupTaskAssign();
				assign.setAssignTarget(task.getTaskTarget());
				assign.setTaskId(task.getTaskId());
				assign.setTeamGroupId(teamGroupId);
				assign.setFinishNum(0);
				assign.setAssignUserId(loginUserId);
				assign.setAssignTime(new Date());
				assign.setAssignTarget(task.getTaskTarget());
				groupTaskAssignService.insertGroupTaskAssign(assign, loginUserId);
				/* 团队主管建的任务，新建完直接统计 */
				if(task.getTaskMold() !=null &&task.getTaskMold()==0){
					loanTaskService.updateGroupTaskFinishAmount(task,assign);
				} else if (task.getTaskMold() != null && task.getTaskMold() == 1) {
					marketingTaskService.updateGroupTaskFinishAmount(task, assign);
				}
			}else if(this.isCustomerManager()){
				TskTeamMemberTaskAssign teamMemberTaskAssign=new TskTeamMemberTaskAssign();
//				teamMemberTaskAssign.setTeamMemberTaskAssignId(teamMemberTaskAssignId);
				teamMemberTaskAssign.setAssignNum(task.getTaskTarget());
				teamMemberTaskAssign.setAssignUserId(loginUserId);
				teamMemberTaskAssign.setAssignTime(new Date());
				teamMemberTaskAssign.setTeamGroupId(teamGroupId);
				teamMemberTaskAssign.setMemberUserId(loginUserId);
				teamMemberTaskAssign.setTaskId(task.getTaskId());
				teamMemberTaskAssign.setTaskFinishStatus(1);
				teamMemberTaskAssignService.insertTeamMemberTaskAssign(teamMemberTaskAssign, loginUserId);
				if(task.getTaskMold() !=null &&task.getTaskMold()==0){
					loanTaskService.updateUserTaskFinishAmount(task,teamMemberTaskAssign);
				} else if (task.getTaskMold() != null && task.getTaskMold() == 1) {
					marketingTaskService.updateUserTaskFinishAmount(task, teamMemberTaskAssign);
				}
			}
			
			renderText(true, "新建任务成功！", null);
			return;
		} catch (Exception e) {
			log.error("新建任务报错", e);
		}
		renderText(false, "新建任务失败！", null);
		return;
	}

	/**
	 * 任务名称唯一性校验
	 */
	@RequestMapping(value = "/checkTaskTitle", method = RequestMethod.POST)
	@ResponseBody
	public void checkTaskTitle(){
		String taskTitle=this.getParameter("taskTitle");		
		List<TskTaskInfo> list=taskInfoService.getTaskInfoByTitle(taskTitle);
		if(!CollectionUtils.isEmpty(list)){
			this.renderText(false, "任务名称已存在，请重新输入", "");
			return;
		}
		this.renderText(true, "", "");
	}

	/**
	 * 修改任务表数据
	 * @return
	 */
	@RequestMapping("/updateTaskInfo")
	@ResponseBody
	public void updateTaskInfo(TskTaskInfo taskInfo){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			taskInfoService.updateTaskInfo(taskInfo,loginUserId);
			renderText(true, "编辑任务成功！", null);
			return;
		} catch (Exception e) {
			log.error("修改任务报错", e);
		}
		renderText(false, "编辑任务失败！", null);
		return;
	}

	/**
	 * 删除任务表数据
	 * @return
	 */
	@RequestMapping("/deleteTaskInfo")
	@ResponseBody
	public void deleteTaskInfo(){
		String taskId = getParameter("taskId");
		try {
			taskInfoService.deleteTaskInfoById(Integer.parseInt(taskId));
			renderText(true, "删除任务成功！", null);
			return;
		} catch (NumberFormatException e) {
			log.error("删除任务报错", e);
		}
		renderText(false, "删除任务失败！", null);
		return;
	}

	/**
	 * @Author: yangdw
	 * @param @param date
	 * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
	 *       1 返回yyyy-MM-dd 23:59:59日期
	 * @return
	 * @Description:
	 * @Date: 10:15 2017/11/2
	 */

	public static Date weeHours(Date date, int flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		//时分秒（毫秒数）
		long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
		//凌晨00:00:00
		cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

		if (flag == 0) {
			return cal.getTime();
		} else if (flag == 1) {
			//凌晨23:59:59
			cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
		}
		return cal.getTime();
	}

	@Resource
	private ILoanTaskProvider loanTaskProvider;

	@NoLoginInterceptor
	@RequestMapping("/ceshi")
	public void test(){
		loanTaskProvider.updateLoanTaskAmount(226);
	}
}
