package banger.service.intf;

import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

public interface ISysTeamGroupService {

	Integer insertGroup(SysTeamGroup group);

	void updateGroup(SysTeamGroup group);

	void deleteGroupById(Integer groupId);

	SysTeamGroup getGroupById(Integer groupId);

	SysTeamMember getMemberByUserId(Integer userId);

	IPageList<SysTeamGroup> queryGroupList(IPageSize page, Map<String, Object> condition);

	void insertMember(SysTeamMember member);

	void updateMember(SysTeamMember member);

	void deleteMemberById(Integer memberId);

	void deleteMemberByGroupId(Integer groupId);

	List<SysTeamMember> getMembersByGroupId(Integer groupId);

	List<SysTeamMember_Query> getMembersByGroupAndRoles(String groupId,String roleIds);

	IPageList<SysTeamMember_Query> getMembersByGroupAndRoles(IPageSize page, String groupId, String memberUser, String roleIds);

	IPageList<SysTeamGroup_Query> queryGroupListByName(IPageSize page, Map<String, Object> condition);

	List<SysTeamMember_Query> queryAllMemberList();

	List<SysTeamGroup> queryGroupListByGroupName(String groupName);

	List<SysTeamGroup> queryGroupListByGroupIds(String ids);

	String getGroupTreeByUserId(Integer userId, String userGroupPermit);
	
	List<SysTeamGroup_Query> getAllGroups();

	List<SysTeamMember_Query> tdzg(Integer integer);

    List<SysTeamMember_Query> queryMenberListByGroupId(Integer groupId);

    List<PmsUserRoles> queryManageExsitInGroup(String selectUser);
}
