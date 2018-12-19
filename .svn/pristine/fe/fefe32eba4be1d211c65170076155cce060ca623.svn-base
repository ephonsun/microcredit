package banger.dao.intf;

import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

public interface ISysTeamGroupDao {

	Integer insertGroup(SysTeamGroup group);

	void updateGroup(SysTeamGroup group);

	void deleteGroupById(Integer groupId);

	SysTeamGroup getGroupById(Integer groupId);

	IPageList<SysTeamGroup> queryGroupList(IPageSize page, Map<String, Object> condition);

	void insertMember(SysTeamMember member);

	void updateMember(SysTeamMember member);

	void deleteMemberById(Integer memberId);

	List<SysTeamMember> getMembersByGroupId(Integer groupId);

	IPageList<SysTeamGroup_Query> queryGroupListByName(IPageSize page, Map<String, Object> condition);

	List<SysTeamMember_Query> queryAllMemberList();

	void deleteMemberByGroupId(Integer groupId);

	SysTeamMember getMemberByUserId(Integer userId);

	List<SysTeamGroup> queryGroupListByGroupName(String groupName);

	List<SysTeamGroup> queryGroupListByGroupIds(String ids);

	List<SysTeamGroup_Query> getAllGroups();

	List<SysTeamMember_Query> getMembersByGroupAndRoles(String groupId, String roleIds);

	IPageList<SysTeamMember_Query> getMembersByGroupAndRoles(IPageSize page, String groupId, String memberUser, String roleIds);
	/**
	 * 得到当前用户ID的团队成员
	 * @param userId
	 * @return
	 */
	List<PmsUser> getTeamGroupUserListByUserId(Integer userId);

	/**
	 * 团队内
	 * @param roleId 审批角色
	 * @param userId
	 * @return
	 */
	List<PmsUser> getTeamExamineUserListByUserId(Integer roleId,Integer userId);

	/**
	 * 团队外
	 * @param roleId 审批角色
	 * @param userId
	 * @return
	 */
	List<PmsUser> getExamineUserListByRoleId(Integer roleId,Integer userId);

	/**
	 * 查询当前用户所在团队里所有客户经理
	 * @param userId
	 * @return
	 */
    List<PmsUser> getCusManageListGroupId(Integer groupId);

	public List<PmsUser> getAllCusManageListGroupId(String groupId);

    List<SysTeamMember_Query> tdzg(Integer userId);

	List<SysTeamMember_Query> queryMenberListByGroupId(Integer groupId);

    List<PmsUserRoles> queryManageExsitInGroup(String selectUser);

    Integer queryGroupIdByUserId(String userId);

    void deleteMemberByUserId(Integer userId);
}
