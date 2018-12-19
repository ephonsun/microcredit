package banger.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;

import banger.common.tools.StringUtil;
import banger.service.intf.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.permission.PmsRole;
import banger.domain.task.GroupUnsignedTaskBean;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskGroupTaskAssignQuery;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.domain.task.TskTeamMemberTaskAssignQuery;
import banger.domain.task.UserTaskAssignBean;
import banger.moduleIntf.IPermissionModule;

/**
 * 团队任务分配表页面访问类
 */
@Controller
@RequestMapping("/groupTask")
public class GroupTaskAssignController extends BaseController {
	private static final long serialVersionUID = 2847059234078752841L;
	@Autowired
	private IGroupTaskAssignService groupTaskAssignService;
	@Autowired
	private ITaskInfoService taskInfoService;
	@Autowired
	private ITeamMemberTaskAssignService teamMemberTaskAssignService;
	@Autowired
	private IPermissionModule permissionModule;
	@Autowired
	private ILoanTaskService loanTaskService;
	@Autowired
	private IMarketingTaskService marketingTaskService;

	/**
	 * 得到团队任务分配表列表页面
	 * @return
	 */
	@RequestMapping("/getGroupTaskAssignListPage")
	public String getGroupTaskAssignListPage(){
		String taskId=this.getRequest().getParameter("taskId");
		String taskTitle="";
		try {
			taskTitle = URLDecoder.decode(this.getRequest().getParameter("taskTitle"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("分配任务信息任务标题转码出错", e);
		}
		String taskDateLimit=this.getRequest().getParameter("taskDateLimit");
		setAttribute("taskId",taskId);
		setAttribute("taskTitle",taskTitle);
		setAttribute("taskDateLimit",taskDateLimit);

		TskTaskInfo taskInfo=taskInfoService.getTaskInfoById(Integer.valueOf(taskId));
		Integer teamGroupId = taskInfo.getTeamGroupId();
		Integer userId = getLoginInfo().getUserId();
		Integer createUserId=taskInfo.getCreateUser();
		String roleId = getLoginInfo().getRoleIds();

		boolean isCreateUserTeamManager=false;
		if(this.isMutilTeamManager()){//如果是主管
			//如果是客户经理创建的、团队主管创建的任务，不能分配
			if(createUserId.intValue()==userId.intValue()){
				setAttribute("canAssign","1");
			}
			if(createUserId.intValue()==userId.intValue()){
				setAttribute("showSignBtn",true);
			}
		}

		if(this.isTeamManager()){//如果团队主管有数据权限看其他团队任务并且有分配任务操作权限，应该能查看本团队和勾选的其他团队分配任务信息，但是应该不能分配其他团队的任务
			Integer userTeamGroupId = getLoginInfo().getTeamGroupId();
			if(teamGroupId==null || teamGroupId.intValue() == 0 || teamGroupId.intValue()==userTeamGroupId.intValue()){
				setAttribute("canAssign","1");
				setAttribute("userTeamGroupId",userTeamGroupId);
			}
		}
		List<PmsRole> createroles=permissionModule.getRolesByUserId(createUserId);
		if(createroles!=null){
			for(PmsRole role:createroles){
				if (StringUtils.isNotBlank(roleId) && roleId.contains("4")) {
					if(role.getRoleId()==3 || role.getRoleId() == 2){
						isCreateUserTeamManager=true;
						break;
					}
				} else {
					if(role.getRoleId()==3){
						isCreateUserTeamManager=true;
						break;
					}
				}
			}
		}
		/*//如果团队主管建的任务，直接跳转到客户经理分配详情页面
		if(isCreateUserTeamManager){
			setAttribute("showSignBtn",true);
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("taskId", taskId);
			if (StringUtils.isNotBlank(roleId) && roleId.contains("4")) {
				teamGroupId = getLoginInfo().getTeamGroupId();
			}
			condition.put("teamGroupId", teamGroupId);
			//查询团队任务情况
			TskGroupTaskAssign groupTaskAssign=groupTaskAssignService.queryGroupTaskAssignByTeam(condition);
			if(groupTaskAssign!=null){
				setAttribute("groupTaskAssignId",groupTaskAssign.getGroupTaskAssignId());
			}
			setAttribute("teamGroupId",teamGroupId);

			return "/task/vm/teamTaskAssignList";
		}*/
		if (isTeamManager()) {
			setAttribute("canAssign","0");
		}
		return "/task/vm/groupTaskAssignList";
	}

	/**
	 * 查询团队客户经理任务分配
	 * @return
	 */
	@RequestMapping("/queryGroupTaskAssignListData")
	@ResponseBody
	public void queryGroupTaskAssignListData(){
		Integer taskId=Integer.parseInt(this.getRequest().getParameter("taskId"));
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("taskId", taskId);
		if(this.isTeamManager()){
//			Integer teamGroupId=getLoginInfo().getTeamGroupId();
			//团队主管也需要根据数据权限控制
			String teamGroupId = getLoginInfo().getTeamGroupIdByRole(false);
			condition.put("teamGroupId", teamGroupId);
		}

		if(this.isMutilTeamManager()){
			String teamGroupId = getLoginInfo().getTeamGroupIdByRole(false);
//			Integer teamGroupId=getLoginInfo().getTeamGroupId();
			if(StringUtil.isEmpty(teamGroupId))
				condition.put("teamGroupId", -1);
			else
				condition.put("teamGroupId", teamGroupId);
		}

		List<TskGroupTaskAssignQuery> assignList=groupTaskAssignService.queryGroupTaskAssignByTaskId(condition,taskId);
		
		renderText(JsonUtil.toJson(assignList, "groupTaskAssignId","assignTargetF,assignTarget,taskId,teamGroupId,assignNumF,assignNum,finishNum,finishNumF,teamGroupName,unSiginedNumF,unSiginedNum,siginedPercent,finishPercent").toString());

	}

	/**
	 * 得到团队任务分配页面
	 * @return
	 */
	@RequestMapping("/getGroupTaskAssignPage")
	public String getGroupTaskAssignPage(){		
		//查询已分配任务团队
		Integer taskId=Integer.parseInt(this.getRequest().getParameter("taskId"));
		TskTaskInfo taskInfo=taskInfoService.getTaskInfoById(taskId);

		Integer teamGroupId = getLoginInfo().getTeamGroupId();
		if(this.isTeamManager() && teamGroupId==null){
			setAttribute("msg","您不属于任何团队，不能分配任务！");
			return "/task/vm/teamTaskAssignError";
		}
		//根据角色，如果是团队主管，跳转到客户经理任务分配界面
		if(this.isTeamManager()){
			//查询登录人的所在的团队
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("taskId", taskId);
			condition.put("teamGroupId", teamGroupId);
			//查询团队任务情况
			TskGroupTaskAssign groupTaskAssign=groupTaskAssignService.queryGroupTaskAssignByTeam(condition);
			if(groupTaskAssign==null){
				setAttribute("msg","您的团队还没有分配任务，不能分配任务！");
				return "/task/vm/teamTaskAssignError";
			}			
			List<UserTaskAssignBean> userList=teamMemberTaskAssignService.queryUnsiginedTeamMemberById(condition);
			if(groupTaskAssign != null && groupTaskAssign.getAssignTarget() != null){
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###");
				setAttribute("target",myformat.format(groupTaskAssign.getAssignTarget()));
			}
			setAttribute("task",taskInfo);
			setAttribute("teamGroupId",teamGroupId);
			setAttribute("teamGroupAssign",groupTaskAssign);
			setAttribute("userList",userList);
			Integer unSingedNum=groupTaskAssign.getAssignTarget().intValue()-groupTaskAssign.getAssignNum().intValue();
			setAttribute("unSingedNum",unSingedNum);
						
			return "/task/vm/teamTaskAssign";
		}else{//如果是团队主管以上，跳转到团队任务分配界面
			//查询团队任务分配情况
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("taskId", taskId);
			if(taskInfo != null && taskInfo.getTaskTarget() != null){
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###");
				setAttribute("target",myformat.format(taskInfo.getTaskTarget()));
			}
			String teamGroupIds=getLoginInfo().getTeamGroupIdByRole(false);
			condition.put("teamGroupId", teamGroupIds);
			List<GroupUnsignedTaskBean> teamGroupList=groupTaskAssignService.queryUnsiginedGroupByTaskId(condition);
			
			setAttribute("task",taskInfo);
			setAttribute("teamGroupList",teamGroupList);
			Integer unSingedNum=taskInfo.getTaskTarget().intValue()-taskInfo.getTaskAssign().intValue();
			setAttribute("unSingedNum",unSingedNum);
		}
				
		return "/task/vm/groupTaskAssign";
	}

	/**
	 * 保存团队任务分配结果
	 */
	@RequestMapping("/saveGroupTaskAssign")
	@ResponseBody
	public void saveGroupTaskAssign(){
		try {
			String teamGroupIdStr=this.getRequest().getParameter("teamGroupIdArr");
			String groupAssignTargetStr=this.getRequest().getParameter("groupTaskAssignIdArr");
			String groupTaskAssignIdStr=this.getRequest().getParameter("groupAssignTargetArr");
			String[] teamGroupIdArray={};
			String[] groupTaskAssignIdArray={};
			String[] groupAssignTargetArray={};
			
			if(teamGroupIdStr!=null){
				teamGroupIdArray=teamGroupIdStr.split(",");
			}
			if(groupAssignTargetStr!=null){
				groupTaskAssignIdArray=groupAssignTargetStr.split(",");
			}
			if(groupTaskAssignIdStr!=null){
				groupAssignTargetArray=groupTaskAssignIdStr.split(",");
			}
			
			List<Integer> teamGroupIds=convertArrayToList(teamGroupIdArray);	    
		    List<Integer> groupTaskAssignIds=convertArrayToList(groupTaskAssignIdArray);
		    List<Integer> groupAssignTargets=convertArrayToList(groupAssignTargetArray);
					
			Integer taskId=Integer.parseInt(this.getRequest().getParameter("taskId"));
			TskTaskInfo taskInfo = taskInfoService.getTaskInfoById(taskId);

			Integer loginUserId = getLoginInfo().getUserId();
			List<TskGroupTaskAssign> groupTaskList = new ArrayList<TskGroupTaskAssign>();
			for (int i=0;i<groupTaskAssignIds.size();i++) {
				Integer groupTaskAssignId=groupTaskAssignIds.get(i);
				Integer groupAssignTarget=groupAssignTargets.get(i);
				if(groupTaskAssignId==0){//新增团队分配				
					if(groupAssignTarget>0){
						TskGroupTaskAssign assign=new TskGroupTaskAssign();
						assign.setAssignTarget(groupAssignTarget);
						assign.setTaskId(taskId);
						assign.setTeamGroupId(teamGroupIds.get(i));
						assign.setAssignNum(0);
						assign.setFinishNum(0);
						groupTaskList.add(assign);
						groupTaskAssignService.insertGroupTaskAssign(assign, loginUserId);
					}				
				}else{//修改任务分配数量
					if (groupAssignTarget.intValue()==0) {
						groupTaskAssignService.deleteGroupTaskAssignById(groupTaskAssignId);

					} else {
						TskGroupTaskAssign assign=new TskGroupTaskAssign();
						assign.setGroupTaskAssignId(groupTaskAssignId);
						assign.setAssignTarget(groupAssignTarget);
						groupTaskList.add(assign);
						groupTaskAssignService.updateGroupTaskAssign(assign, loginUserId);
					}
				}
			}

			//统计任务完成金额
			if (taskInfo != null && taskInfo.getTaskMold() == 0) {
				loanTaskService.updateGroupTaskFinishAmount(taskInfo,groupTaskList);
			} else if (taskInfo != null && taskInfo.getTaskMold() == 1) {
				marketingTaskService.updateGroupTaskFinishAmount(taskInfo, groupTaskList);
			}

			//修改任务已分配数量
			taskInfo=new TskTaskInfo();
			Integer total=0;
			for (Integer target : groupAssignTargets) {
				total=total.intValue()+target.intValue();
			}
			taskInfo.setTaskId(taskId);
			taskInfo.setTaskAssign(total);
			taskInfo.setAssignUserId(loginUserId);
			taskInfoService.updateTaskInfo(taskInfo, loginUserId);
			renderText(true, "分配任务成功！", null);
			return;
		} catch (Exception e) {
			log.error("保存任务分配结果出错", e);
		}
		renderText(false, "保存任务分配结果失败！", null);
	}
	
	private List<Integer> convertArrayToList(String[] array){
		List<Integer> list=new ArrayList<Integer>();
		if(array.length==0){
			return list;
		}
		for(int i=0;i<array.length;i++){
			list.add(new Integer(array[i]));
		}
		return list;
	}
	
	/**
	 * 保存客户经理任务分配结果
	 */
	@RequestMapping("/saveTeamTaskAssign")
	@ResponseBody
	public void saveTeamTaskAssign(){
		try {
			String memberUserIdStr=this.getRequest().getParameter("memberUserIdArr");
			String teamMemberTaskAssignIdStr=this.getRequest().getParameter("teamMemberTaskAssignIdArr");
			String assignNumStr=this.getRequest().getParameter("assignNumArr");
			
			String[] memberUserIdArray={};
			String[] teamMemberTaskAssignIdArray={};
			String[] assignNumArray={};
			
			if(memberUserIdStr!=null){
				memberUserIdArray=memberUserIdStr.split(",");
			}
			if(teamMemberTaskAssignIdStr!=null){
				teamMemberTaskAssignIdArray=teamMemberTaskAssignIdStr.split(",");
			}
			if(assignNumStr!=null){
				assignNumArray=assignNumStr.split(",");
			}
			
			List<Integer> memberUserIds=convertArrayToList(memberUserIdArray);	 
			List<Integer> teamMemberTaskAssignIds=convertArrayToList(teamMemberTaskAssignIdArray);	 
			List<Integer> assignNums=convertArrayToList(assignNumArray);	 
			
			Integer taskId=Integer.parseInt(this.getRequest().getParameter("taskId"));
			Integer loginUserId = getLoginInfo().getUserId();
			Integer groupTaskAssignId=Integer.parseInt(this.getRequest().getParameter("groupTaskAssignId"));
			Integer teamGroupId=Integer.parseInt(this.getRequest().getParameter("teamGroupId"));

			TskTaskInfo taskInfo = taskInfoService.getTaskInfoById(taskId);

			List<TskTeamMemberTaskAssign> userTaskList = new ArrayList<TskTeamMemberTaskAssign>();
			for(int i=0;i<teamMemberTaskAssignIds.size();i++){
				Integer teamMemberTaskAssignId=teamMemberTaskAssignIds.get(i);
				Integer assignNum=assignNums.get(i);
				if(teamMemberTaskAssignId==0){//新增客户经理分配
					if(assignNum>0){
						TskTeamMemberTaskAssign teamMemberTaskAssign=new TskTeamMemberTaskAssign();
						teamMemberTaskAssign.setGroupTaskAssignId(groupTaskAssignId);
						teamMemberTaskAssign.setFinishNum(0);
						teamMemberTaskAssign.setTaskFinishStatus(1);
						teamMemberTaskAssign.setTeamGroupId(teamGroupId);
						teamMemberTaskAssign.setAssignNum(assignNum);
						teamMemberTaskAssign.setTaskId(taskId);
						teamMemberTaskAssign.setMemberUserId(memberUserIds.get(i));
						teamMemberTaskAssign.setAssignUserId(loginUserId);
						teamMemberTaskAssign.setAssignTime(new Date());
						userTaskList.add(teamMemberTaskAssign);
						teamMemberTaskAssignService.insertTeamMemberTaskAssign(teamMemberTaskAssign, loginUserId);
					}				
				}else{//修改客户经理分配
					TskTeamMemberTaskAssign teamMemberTaskAssignById = teamMemberTaskAssignService.getTeamMemberTaskAssignById(teamMemberTaskAssignId);
					if (assignNum.intValue() == 0 && teamMemberTaskAssignById.getAssignNum() == 0) {
						teamMemberTaskAssignService.deleteByMemberId(teamMemberTaskAssignId);
					} else {
						TskTeamMemberTaskAssign teamMemberTaskAssign = new TskTeamMemberTaskAssign();
						teamMemberTaskAssign.setTeamMemberTaskAssignId(teamMemberTaskAssignId);
						teamMemberTaskAssign.setAssignNum(assignNum);
						teamMemberTaskAssign.setAssignUserId(loginUserId);
						teamMemberTaskAssign.setAssignTime(new Date());
						teamMemberTaskAssign.setTeamGroupId(teamGroupId);
						teamMemberTaskAssign.setMemberUserId(memberUserIds.get(i));
						userTaskList.add(teamMemberTaskAssign);
						teamMemberTaskAssignService.updateTeamMemberTaskAssign(teamMemberTaskAssign, loginUserId);
					}
				}
			}

			if (taskInfo != null && taskInfo.getTaskMold() == 0) {
				loanTaskService.updateUserTaskFinishAmount(taskInfo,userTaskList);
			} else if (taskInfo != null && taskInfo.getTaskMold() == 1) {
				marketingTaskService.updateUserTaskFinishAmount(taskInfo, userTaskList);
			}

			//修改团队已分配数量
			Integer total=0;
			for (Integer target : assignNums) {
				total=total.intValue()+target.intValue();
			}
			TskGroupTaskAssign assign=new TskGroupTaskAssign();
			assign.setGroupTaskAssignId(groupTaskAssignId);
			assign.setAssignNum(total);
			assign.setAssignUserId(loginUserId);
			assign.setAssignTime(new Date());
			groupTaskAssignService.updateGroupTaskAssign(assign, loginUserId);
			renderText(true, "分配任务成功！", null);
			return;
		} catch (Exception e) {
			log.error("保存客户经理任务分配结果出错", e);
		}
		renderText(false, "保存任务分配结果失败！", null);
	}
	
	/**
	 * 得到客户经理任务分配明细
	 * @return
	 */
	@RequestMapping("/getTeamTaskAssignListPage")
	public String getTeamTaskAssignListPage(){
		String taskId=this.getRequest().getParameter("taskId");
		String groupTaskAssignId=this.getRequest().getParameter("groupTaskAssignId");
		String teamGroupId=this.getRequest().getParameter("teamGroupId");
		
		setAttribute("taskId",taskId);
		setAttribute("groupTaskAssignId",groupTaskAssignId);
		setAttribute("teamGroupId",teamGroupId);
		return "/task/vm/teamTaskAssignList";
	}
	
	/**
	 * 查询客户经理任务分配明细
	 * @return
	 */
	@RequestMapping("/queryTeamTaskAssignListData")
	@ResponseBody
	public void queryTeamTaskAssignListData(){
		Integer taskId=Integer.parseInt(this.getRequest().getParameter("taskId"));
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("taskId", taskId);
		List<TskTeamMemberTaskAssignQuery> teamList=null;
		if(StringUtils.isNotBlank(this.getRequest().getParameter("groupTaskAssignId"))){
			Integer groupTaskAssignId=Integer.parseInt(this.getRequest().getParameter("groupTaskAssignId"));
			Integer teamGroupId=Integer.parseInt(this.getRequest().getParameter("teamGroupId"));
			condition.put("groupTaskAssignId", groupTaskAssignId);
			condition.put("teamGroupId", teamGroupId);
			teamList=groupTaskAssignService.queryTeamMemberTaskAssignDetail(condition);
		/*List<TskTeamMemberTaskAssignQuery> teamList= new ArrayList<TskTeamMemberTaskAssignQuery>() ;
		String teamGroupId = getLoginInfo().getTeamGroupIdByRole(false);
		if(StringUtils.isNotBlank(teamGroupId)){
			String[] teamGroups = teamGroupId.split(",");
			for (int i = 0; i < teamGroups.length; i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("taskId", taskId);
				map.put("teamGroupId", Integer.valueOf(teamGroups[i]));
				map.put("isDel",0);
				List<TskGroupTaskAssign> tskGroupTaskAssigns = groupTaskAssignService.queryGroupTaskAssignList(map);
				Set<Integer> set = new HashSet<Integer>();
				for (TskGroupTaskAssign tskGroupTaskAssign : tskGroupTaskAssigns) {
					set.add(tskGroupTaskAssign.getGroupTaskAssignId());
				}
				for (Integer groupTaskAssignId : set) {
					condition.put("groupTaskAssignId", groupTaskAssignId);
					condition.put("teamGroupId",Integer.valueOf(teamGroups[i]));
					List<TskTeamMemberTaskAssignQuery> teamLists=groupTaskAssignService.queryTeamMemberTaskAssignDetail(condition);
					teamList.addAll(teamLists);
				}
			}*/
		}else{
			teamList=new ArrayList<TskTeamMemberTaskAssignQuery>();
		}
				
		renderText(JsonUtil.toJson(teamList, "teamMemberTaskAssignId","teamMemberName,assignTargetF,assignTarget,assignNum,unSiginedNumF,unSiginedNum,assignPercent,finishNum,finishNumF,finishPercent").toString());
	}

}
