package banger.moduleIntf;

import java.util.List;
import java.util.Map;

import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * wangyiyi
 */
public interface IPermissionModule {

    List<PmsUser> getUserInfoList(Map<String, Object> params);

    PmsUser getPmsUserByAccount(Map<String,Object> params);
    
    PmsUser getPmsUserByUserId(Integer userId);

    public String login(String account, String password);

    /**
     * 得到用户所有角色
     *
     * @return
     */
    public List<PmsRole> getRolesByUserId(Integer userId);

    /**
     * 查询角色功能
     *
     * @param roleId
     * @return
     */
    public List<PmsFunc> getFuncsByRoleId(Integer roleId);

    /**
     * 查询角色功能
     *
     * @param roleIds
     * @return
     */
    public List<PmsFunc> getFuncsByRoleIds(String roleIds);
    
    
    /**
	 * 通过设备ID获取详细
	 * @param deviceId
	 * @return
	 */
	PadClentDevice getPadClentDeviceInfo(String deviceId);	
	
	/**
	 * 新增
	 * @param padClentDevice
	 */
	void innsertPadClentDevice(PadClentDevice padClentDevice);

    /**
     * 根据用户id查询团队
     *
     * @param userId
     * @return
     */
    public Integer getGroupIdByUserId(Integer userId);
    
    /**
     * 查询所有团队
     * @return
     */
    List<SysTeamGroup_Query> queryAllSysTeamGroup();

    List<SysTeamMember_Query> getMangerByGroupId(String groupId);

    List<SysTeamGroup> queryGroupListByGroupIds(String ids);

    /**
     * 得到用户名
     * @param userIds
     * @return
     */
    String[] getUserNamesByIds(String[] userIds);


    IPageList<SysTeamMember_Query> queryMemberListByRoles(IPageSize page, String groupId, String memberUser);

    /**
     * 通过用户id查询用户角色
     * @param uid
     * @return
     */
    List<PmsRole> getRoleIdByUID(Integer uid);

    /**
     * 根据用户id查询团队权限
     * @param userId
     * @return
     */
    PmsUser queryUserGroupPermitByUserId(String userId);

    /**
     * 所有角色列表
     */
    public List<PmsRole> getUserAllRoles();
    /**
     * 根据角色查询用户
     */
    public List<PmsUser> queryUserListByRoleId(String roleId,String teamGroupId);

    public List<PmsRole>  getTeamRolesByGroupId(Integer groupId);

    String checkDataByUserId(String teamIds, Integer userId);
}
