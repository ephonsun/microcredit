/*
 * banger Inc.
 * ToDo       :This is Class Description...
 * Author     :Administrator
 * Create Date:2014-3-5
 */
package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IPmsRoleService;
import banger.service.intf.ISysTeamGroupService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/group")
public class SysTeamGroupController extends BaseController {

	private static final long serialVersionUID = 1746006285862576499L;

	@Autowired
	private ISysTeamGroupService groupService;
	/**
	 * 查询客户列表
	 * @return
	 */
	@RequestMapping("queryGroupList")
	@ResponseBody
	public void queryGroupList(){

		String groupName = this.getParameter("groupName");
		String userName = this.getParameter("userName");

		Map<String, Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(groupName)){
			condition.put("groupName",groupName);
		}
		if(StringUtils.isNotBlank(userName)){
			condition.put("userName",userName);
		}

		IPageList<SysTeamGroup_Query> groupPageList = groupService.queryGroupListByName(this.getPage(), condition);
		if(CollectionUtils.isNotEmpty(groupPageList)){
			List<SysTeamMember_Query> memberList = groupService.queryAllMemberList();
			//key=groupId,roleId value=name,name,name
			Map<String,String> userNameMap = new HashMap<String,String>();
			if(CollectionUtils.isNotEmpty(memberList)){
				String key = "";
				SysTeamMember_Query member;
				for (int i = 0; i < memberList.size(); i++) {
					member = memberList.get(i);
					key=member.getTeamGroupId()+","+member.getRoleId();
					if(userNameMap.containsKey(key)){
						userNameMap.put(key, MapUtils.getString(userNameMap,key)+","+member.getUserName());
					}else{
						userNameMap.put(key,member.getUserName());
					}
				}
			}

			SysTeamGroup_Query group = null;
			for (int i = 0; i < groupPageList.size() ; i++) {
				group = groupPageList.get(i);
				group.setLeaderName(MapUtils.getString(userNameMap,group.getTeamGroupId()+","+ GroupRolesEnum.TEAM_LEADER.getRoleId()));
				group.setManagerName(MapUtils.getString(userNameMap, group.getTeamGroupId() + "," + GroupRolesEnum.MANAGER.getRoleId()));
				group.setBackerName(MapUtils.getString(userNameMap, group.getTeamGroupId() + "," + GroupRolesEnum.BACKER.getRoleId()));
			}
		}

		renderText(JsonUtil.toJson(groupPageList, "teamGroupId", "leaderName,managerName,backerName,teamGroupName,createDate,createUser", "yyyy-MM-dd").toString());
	}
	

	/**
	 * 跳转到新增或编辑用户页面
	 * @return
	 */
	@RequestMapping("getSysTeamGroupPage")
	public String getSysTeamGroupPage(HttpServletRequest request){
		return "/permission/vm/sysTeamGroupList";
	}

	/**
	 * 跳转到新增或编辑用户页面  zz
	 * @return
	 */
	@RequestMapping("getAddSysTeamGroupPage")
	public String getAddSysTeamGroupPage(HttpServletRequest request) throws UnsupportedEncodingException {
//		request.setCharacterEncoding("UTF-8");
//		String groupName = this.getParameter("groupName");
//		if(StringUtils.isNotBlank(groupName)){
//			groupName = new String(groupName.getBytes("iso-8859-1"),"utf-8");
//		}
//		String groupName = URLDecoder.decode(this.getRequest().getParameter("groupName"), "UTF-8");
		String groupIdStr = getParameter("groupId");

		Integer memberCount = 0;
		String userIds = "";
		if(StringUtils.isNotBlank(groupIdStr)){

			SysTeamGroup group = groupService.getGroupById(Integer.valueOf(groupIdStr));
			List<SysTeamMember> members = groupService.getMembersByGroupId(Integer.valueOf(groupIdStr));
			if(CollectionUtils.isNotEmpty(members)){
				for (int i = 0; i < members.size(); i++) {
					if(!"".equals(userIds)){
						userIds+=",";
					}
					userIds+=members.get(i).getUserId();
				}
			}
			memberCount = members.size();
			request.setAttribute("userIds", userIds);
			request.setAttribute("groupId", groupIdStr);
			request.setAttribute("groupName", group.getTeamGroupName());
		}
		request.setAttribute("memberCount", memberCount);
		request.setAttribute("leaderRoleId", "R_"+GroupRolesEnum.TEAM_LEADER.getRoleId());

		return "/permission/vm/addSysTeamGroup";
	}

	/**
	 * 新增编辑机构
	 * @return
	 */
	@RequestMapping("addUpdateGroup")
	@ResponseBody
	public void addUpdateGroup(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();

		String groupName = this.getParameter("groupName");
		String groupIdStr = this.getParameter("groupId");
		String selectUserList = this.getParameter("selectUserList");

		Integer groupId = 0;

		Map<String, Object> condition = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(groupName)){
			condition.put("groupName",groupName);
		}

		List<SysTeamGroup> groupPageList = groupService.queryGroupListByGroupName(groupName);
		if (StringUtils.isNotBlank(groupIdStr)){
			//编辑
			if(CollectionUtils.isNotEmpty(groupPageList)&&groupPageList.size()>1){
				renderText(false,"团队名称已存在！",null);
				return;
			}
			//判断如果团队中有成员，其中是否有团队主管
			if(!queryManageExsitInGroup(selectUserList)){
				renderText(false,"请选择团队主管！",null);
				return;
			}
			groupId = Integer.valueOf(groupIdStr);
			SysTeamGroup group = groupService.getGroupById(groupId);
			group.setTeamGroupName(groupName);
			groupService.updateGroup(group);
		}else{
			//新增
			if(CollectionUtils.isNotEmpty(groupPageList)){
				renderText(false,"团队名称已存在！",null);
				return;
			}
			//判断如果团队中有成员，其中是否有团队主管
			if(!queryManageExsitInGroup(selectUserList)){
				renderText(false,"请选择团队主管！",null);
				return;
			}
			SysTeamGroup group = new SysTeamGroup();
			group.setTeamGroupName(groupName);
			group.setDelFlag(1);
			group.setCreateUser(userId);
			group.setCreateDate(new Date());
			groupId = groupService.insertGroup(group);
		}

		groupService.deleteMemberByGroupId(groupId);
		if(!"".equals(selectUserList)){
			String[] selectUsers = selectUserList.split(",");
			for (int i = 0; i < selectUsers.length; i++) {
				SysTeamMember member = new SysTeamMember();
				member.setCreateDate(new Date());
				member.setCreateUser(userId);
				member.setTeamGroupId(groupId);
				member.setUserId(Integer.valueOf(selectUsers[i]));
				groupService.insertMember(member);
			}
		}
		/**
		 * SysTeamMember memberByUserId = groupService.getMemberByUserId(Integer.valueOf(selectUsers[i]));
		 //如果表中没数据
		 if(memberByUserId == null){
		 //如果此userId时团队主管且在别的团队中
		 List<SysTeamMember_Query> list = groupService.tdzg(Integer.valueOf(selectUsers[i]));
		 if(list != null && list.size() > 0){
		 //提醒此团队中主管已经在别的团队中
		 renderText(false,"",list.get(0).getUserName()+"是别的团队的主管！");
		 return null;
		 }else{
		 SysTeamMember member = new SysTeamMember();
		 member.setCreateDate(new Date());
		 member.setCreateUser(userId);
		 member.setTeamGroupId(groupId);
		 member.setUserId(Integer.valueOf(selectUsers[i]));
		 groupService.insertMember(member);
		 }
		 }else {
		 SysTeamMember member = new SysTeamMember();
		 member.setCreateDate(new Date());
		 member.setCreateUser(userId);
		 member.setTeamGroupId(groupId);
		 member.setUserId(Integer.valueOf(selectUsers[i]));
		 groupService.insertMember(member);
		 }
		 */
		renderText(true,"",null);
	}

	/**
	 * 查询是否有团队成员，如果有团队成员，判断是否拥有团队主管
	 */
	private Boolean queryManageExsitInGroup(String selectUserList){
		//查询是否有团队成员
		if(!"".equals(selectUserList)) {
			String[] selectUsers = selectUserList.split(",");
			for (int i = 0; i < selectUsers.length; i++) {
				//查询是否有团队主管权限
				List<PmsUserRoles> list = groupService.queryManageExsitInGroup(selectUsers[i]);
				if (list != null && list.size() > 0) {
					return true;
				}
			}
			return false;
		}else{
			return true;
		}
	}


	/**
	 * 删除机构
	 * @return
	 */
	@RequestMapping("deleteGroupById")
	@ResponseBody
	public void deleteGroupById(HttpServletRequest request){

		Integer userId = this.getLoginInfo().getUserId();
		String groupIdStr = this.getParameter("groupId");

		Integer groupId = 0;
		if(StringUtils.isNotBlank(groupIdStr)){
			groupId = Integer.valueOf(groupIdStr);
			List<SysTeamMember_Query> list = groupService.queryMenberListByGroupId(groupId);
			if(list != null  && list.size() > 0){
				renderText(false,"",null);
				return;
			}else{
				groupService.deleteGroupById(groupId);
				groupService.deleteMemberByGroupId(groupId);
				renderText(true,"",null);
			}

		}
	}

	@RequestMapping("/initGroupTree")
	@ResponseBody
	public String initDeGroupTree(){
		Integer userId = this.getLoginInfo().getUserId();
		String userGroupPermit = this.getLoginInfo().getUserGroupPermit();
		String groupJson = this.groupService.getGroupTreeByUserId(userId, userGroupPermit);
		renderText(groupJson);
		return null;
	}



}
