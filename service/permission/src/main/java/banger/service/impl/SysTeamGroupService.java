package banger.service.impl;

import banger.dao.intf.ISysTeamGroupDao;
import banger.dao.intf.IUserDao;
import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ITeamGroupProvider;
import banger.service.intf.ISysTeamGroupService;
import banger.service.intf.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysTeamGroupService implements ISysTeamGroupService,ITeamGroupProvider {

	@Autowired
	private ISysTeamGroupDao sysTeamGroupDao;

	@Autowired
	private IUserDao userDao;

	@Override
	public Integer insertGroup(SysTeamGroup group) {
		return sysTeamGroupDao.insertGroup(group);
	}

	/**
	 * 得到当前用户ID的团队成员
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getTeamGroupUserListByUserId(Integer userId){
		return sysTeamGroupDao.getTeamGroupUserListByUserId(userId);
	}

	@Override
	public void updateGroup(SysTeamGroup group) {
		sysTeamGroupDao.updateGroup(group);
	}

	@Override
	public void deleteGroupById(Integer groupId) {
		sysTeamGroupDao.deleteGroupById(groupId);
	}

	@Override
	public SysTeamGroup getGroupById(Integer groupId) {
		return sysTeamGroupDao.getGroupById(groupId);
	}

	@Override
	public SysTeamMember getMemberByUserId(Integer userId) {
		return sysTeamGroupDao.getMemberByUserId(userId);
	}

	@Override
	public IPageList<SysTeamGroup> queryGroupList(IPageSize page, Map<String, Object> condition) {
		return sysTeamGroupDao.queryGroupList(page,condition);
	}

	@Override
	public void insertMember(SysTeamMember member) {
		sysTeamGroupDao.insertMember(member);
	}

	@Override
	public void updateMember(SysTeamMember member) {
		sysTeamGroupDao.updateMember(member);
	}

	@Override
	public void deleteMemberById(Integer memberId) {
		sysTeamGroupDao.deleteMemberById(memberId);
	}

	@Override
	public void deleteMemberByGroupId(Integer groupId) {
		sysTeamGroupDao.deleteMemberByGroupId(groupId);
	}

	@Override
	public List<SysTeamMember> getMembersByGroupId(Integer groupId) {
		return sysTeamGroupDao.getMembersByGroupId(groupId);
	}

	@Override
	public List<SysTeamMember_Query> getMembersByGroupAndRoles(String groupId,String roleIds) {
		return sysTeamGroupDao.getMembersByGroupAndRoles(groupId, roleIds);
	}

	@Override
	public IPageList<SysTeamMember_Query> getMembersByGroupAndRoles(IPageSize page, String groupId, String memberUser,String roleIds) {
		return sysTeamGroupDao.getMembersByGroupAndRoles(page, groupId, memberUser, roleIds);
	}



	@Override
	public IPageList<SysTeamGroup_Query> queryGroupListByName(IPageSize page, Map<String, Object> condition) {
		return sysTeamGroupDao.queryGroupListByName(page, condition);
	}

	@Override
	public List<SysTeamMember_Query> queryAllMemberList() {
		return sysTeamGroupDao.queryAllMemberList();
	}




	@Override
	public List<SysTeamGroup> queryGroupListByGroupName(String groupName) {
		return sysTeamGroupDao.queryGroupListByGroupName(groupName);
	}

	@Override
	public List<SysTeamGroup> queryGroupListByGroupIds(String ids) {
		return sysTeamGroupDao.queryGroupListByGroupIds(ids);
	}


	/**
	 * 团队内
	 * @param roleId 审批角色
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getTeamExamineUserListByUserId(Integer roleId,Integer userId){
		return sysTeamGroupDao.getTeamExamineUserListByUserId(roleId,userId);
	}

	/**
	 * 团队外
	 * @param roleId 审批角色
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getExamineUserListByRoleId(Integer roleId,Integer userId){
		return sysTeamGroupDao.getExamineUserListByRoleId(roleId,userId);
	}

	/**
	 * 查询当前用户所在团队里所有客户经理
	 * @param groupId
	 * @return
	 */
	public List<PmsUser> getCusManageListGroupId(Integer groupId) {
        return sysTeamGroupDao.getCusManageListGroupId(groupId);
    }
	/**
	 * 查询当前用户团队权限下所有客户经理
	 * @param groupId
	 * @return
	 */
	public List<PmsUser> getAllCusManageListGroupId(String groupId) {
		return sysTeamGroupDao.getAllCusManageListGroupId(groupId);
	}

    @Override
	public String getGroupTreeByUserId(Integer userId, String userGroupPermit) {
		List<SysTeamGroup_Query> groups = this.sysTeamGroupDao.getAllGroups();
		JSONArray ja = new JSONArray();
		for(int i=0;i<groups.size();i++){
			SysTeamGroup group = groups.get(i);

			JSONObject jo = new JSONObject();
			jo.put("id", group.getTeamGroupId());
			jo.put("pId", 0);
			jo.put("name", group.getTeamGroupName());
			jo.put("icon", "../core/imgs/icon/agency.gif");

			ja.add(jo);
		}

		return ja.toString();
	}

	@Override
	public List<SysTeamGroup_Query> getAllGroups() {
		return sysTeamGroupDao.getAllGroups();
	}

	@Override
	public Integer queryGroupIdByUserId(String userId) {
		return sysTeamGroupDao.queryGroupIdByUserId(userId);
	}

	@Override
    public List<SysTeamMember_Query> tdzg(Integer userId) {
		return sysTeamGroupDao.tdzg(userId);
    }

    @Override
    public List<SysTeamMember_Query> queryMenberListByGroupId(Integer groupId) {
        return sysTeamGroupDao.queryMenberListByGroupId(groupId);
    }

    @Override
    public List<PmsUserRoles> queryManageExsitInGroup(String selectUser) {
		return sysTeamGroupDao.queryManageExsitInGroup(selectUser);
    }

	/**
	 * 获取用户的管理团队
	 */
	public String getGroupPermitByUserId(Integer userId){
		return this.userDao.getGroupPermitByUserId(userId);
	}
}
