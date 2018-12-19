package banger.moduleIntf;

import banger.domain.permission.PmsUser;
import banger.domain.permission.SysTeamGroup_Query;

import java.util.List;

/**
 * 团队
 * Created by zhusw on 2017/6/19.
 */
public interface ITeamGroupProvider {

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
     */
    List<PmsUser> getCusManageListGroupId(Integer groupId);

    public List<PmsUser> getAllCusManageListGroupId(String groupId);

    List<SysTeamGroup_Query> getAllGroups();

    Integer queryGroupIdByUserId(String belongId);

    /**
     * 获取用户的管理团队
     */
    String getGroupPermitByUserId(Integer userId);

}
