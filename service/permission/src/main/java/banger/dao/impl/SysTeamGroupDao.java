package banger.dao.impl;

import banger.dao.intf.ISysTeamGroupDao;
import banger.domain.layout.Page;
import banger.domain.permission.*;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysTeamGroupDao extends PageSizeDao<Object> implements ISysTeamGroupDao {

	@Override
	public Integer insertGroup(SysTeamGroup group) {
		Integer id = this.newId(SysTeamGroup.class).intValue();
		group.setTeamGroupId(id);
		this.execute("insertGroup", group);
		return id;
	}

	@Override
	public void updateGroup(SysTeamGroup group) {
		this.execute("updateGroup", group);
	}

	@Override
	public void deleteGroupById(Integer groupId) {
		this.execute("deleteGroupById", groupId);
	}

	@Override
	public SysTeamGroup getGroupById(Integer groupId) {
		return (SysTeamGroup)this.queryEntity("getGroupById", groupId);
	}

	@Override
	public IPageList<SysTeamGroup> queryGroupList(IPageSize page, Map<String, Object> condition) {
		return (IPageList<SysTeamGroup>)this.queryEntities("queryGroupList",page,condition);
	}

	@Override
	public void insertMember(SysTeamMember member) {
		member.setTeamMemberId(this.newId(SysTeamMember.class).intValue());
		this.execute("insertMember", member);
	}

	@Override
	public void updateMember(SysTeamMember member) {
		this.execute("updateMember", member);
	}

	@Override
	public void deleteMemberById(Integer memberId) {
		this.execute("deleteMemberById", memberId);
	}

	@Override
	public List<SysTeamMember> getMembersByGroupId(Integer groupId) {
		return (List<SysTeamMember>)this.queryEntities("queryMembersByGroupId", groupId);
	}

	@Override
	public IPageList<SysTeamGroup_Query> queryGroupListByName(IPageSize page, Map<String, Object> condition) {
		return (IPageList<SysTeamGroup_Query>)this.queryEntities("queryGroupListByName",page,condition);
	}

	@Override
	public List<SysTeamMember_Query> queryAllMemberList() {
		return (List<SysTeamMember_Query>)this.queryEntities("queryAllMemberList");
	}

	@Override
	public void deleteMemberByGroupId(Integer groupId) {
		this.execute("deleteMemberByGroupId", groupId);
	}

	@Override
	public SysTeamMember getMemberByUserId(Integer userId) {
		return (SysTeamMember)this.queryEntity("getMemberByUserId", userId);
	}

	@Override
	public List<SysTeamGroup> queryGroupListByGroupName(String groupName) {
		return (List<SysTeamGroup>)this.queryEntities("queryGroupListByGroupName", groupName);
	}

	@Override
	public List<SysTeamGroup> queryGroupListByGroupIds(String ids) {
		return (List<SysTeamGroup>)this.queryEntities("queryGroupListByGroupIds", ids);
	}

	@Override
	public List<SysTeamGroup_Query> getAllGroups() {
		return (List<SysTeamGroup_Query>)this.queryEntities("getAllGroups");
	}

	@Override
	public List<SysTeamMember_Query> getMembersByGroupAndRoles(String groupId, String roleIds) {
		//贷款分配开关是否启用 1 开启 2 关闭
		String allotConfigStatus = (String) this.queryValue("queryLoanAllotConfigStatus");
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("groupId",groupId);
		condition.put("roleIds",roleIds);
		if(allotConfigStatus != null && !"".equals(allotConfigStatus)){
			condition.put("allotConfigStatus",allotConfigStatus);
		}
		return (List<SysTeamMember_Query>)this.queryEntities("getMembersByGroupAndRoles",condition);
	}

	@Override
	public IPageList<SysTeamMember_Query> getMembersByGroupAndRoles(IPageSize page, String groupId, String memberUser, String roleIds) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("groupId",groupId);
		condition.put("roleIds",roleIds);
		condition.put("memberUser",memberUser);
		return (IPageList<SysTeamMember_Query>)this.queryEntities("getMembersByGroupAndRoles",page,condition);
	}

	/**
	 * 得到当前用户ID的团队成员
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getTeamGroupUserListByUserId(Integer userId){
		return (List<PmsUser>)this.queryEntities("getTeamGroupUserListByUserId", userId);
	}

	/**
	 * 团队内
	 * @param roleId 审批角色
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getTeamExamineUserListByUserId(Integer roleId,Integer userId){
		return (List<PmsUser>)this.queryEntities("getTeamExamineUserListByUserId", new Object[]{roleId,userId});
	}

	/**
	 * 团队外
	 * @param roleId 审批角色
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getExamineUserListByRoleId(Integer roleId,Integer userId){
		return (List<PmsUser>)this.queryEntities("getExamineUserListByRoleId",new Object[]{roleId,userId});
	}

	/**
	 * 查询当前用户所在团队里所有客户经理
	 * @param groupId
	 * @return
	 */
    public List<PmsUser> getCusManageListGroupId(Integer groupId) {
		//贷款分配开关是否启用 1 开启 2 关闭
		String allotConfigStatus = (String) this.queryValue("queryLoanAllotConfigStatus");
    	Map<String,Object> condition =new HashMap<String, Object>();
    	condition.put("groupId",groupId);
		if(allotConfigStatus != null && !"".equals(allotConfigStatus)){
			condition.put("allotConfigStatus",allotConfigStatus);
		}
        return (List<PmsUser>) this.queryEntities("getCusManageListGroupId",condition);
    }
	/**
	 * 查询当前用户团队权限下所有客户经理
	 * @param groupId
	 * @return
	 */
	public List<PmsUser> getAllCusManageListGroupId(String groupId) {
		//贷款分配开关是否启用 1 开启 2 关闭
		String allotConfigStatus = (String) this.queryValue("queryLoanAllotConfigStatus");
		Map<String,Object> condition =new HashMap<String, Object>();
		condition.put("groupId",groupId);
		if(allotConfigStatus != null && !"".equals(allotConfigStatus)){
			condition.put("allotConfigStatus",allotConfigStatus);
		}
		return (List<PmsUser>) this.queryEntities("getAllCusManageListGroupId",condition);
	}

    @Override
    public List<SysTeamMember_Query> tdzg(Integer userId) {
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("userId",userId);
        return (List<SysTeamMember_Query>) this.queryEntities("tdzg",condition);
    }

    @Override
    public List<SysTeamMember_Query> queryMenberListByGroupId(Integer groupId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("groupId",groupId);
        return (List<SysTeamMember_Query>) this.queryEntities("queryMenberListByGroupId",condition);
    }

    @Override
    public List<PmsUserRoles> queryManageExsitInGroup(String selectUser) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("userId",selectUser);
        return (List<PmsUserRoles>) this.queryEntities("queryManageExsitInGroup",condition);
    }

    @Override
    public Integer queryGroupIdByUserId(String userId) {
    	Map<String,Object> condition = new HashMap<String,Object>();
    	condition.put("userId",userId);
		return this.queryInt("queryGroupIdByUserId", condition);
    }

	/**
	 * 根据用户id删除团队成员
	 * @param userId
	 */
	@Override
	public void deleteMemberByUserId(Integer userId) {
		this.execute("deleteMemberByUserId", userId);
	}

}
