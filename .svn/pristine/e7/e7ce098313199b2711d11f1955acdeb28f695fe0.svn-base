package banger.moduleImpl;

import banger.dao.intf.IFuncDao;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.IRoleService;
import banger.service.intf.ISysTeamGroupService;
import banger.service.intf.IUserLoginService;
import banger.service.intf.IUserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * wangyiyi
 */
@Service
public class PermissionModule implements InitializingBean,IPermissionModule {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserLoginService userLoginService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISysTeamGroupService groupService;
    @Autowired
    private ISysTeamGroupService sysTeamGroupService;
    @Autowired
    private IFuncDao funcDao;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void afterPropertiesSet() throws Exception {
        
    }

    public void setUserLoginService(IUserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     *根据所属机构获取柜员信息
     * @param params
     * @return
     */
    public List<PmsUser> getUserInfoList(Map<String, Object> params) {
        return this.userService.getUserInfoList(params);
    }

    @Override
    public PmsUser getPmsUserByAccount(Map<String, Object> params) {
        return userService.getPmsUserByAccount(params);
    }

    @Override
    public String login(String account, String password) {
        return this.userLoginService.login(account, password);
    }

    @Override
    public List<PmsRole> getRolesByUserId(Integer userId) {
        return this.roleService.getRolesByUserId(userId);
    }

    @Override
    public List<PmsFunc> getFuncsByRoleId(Integer roleId) {
        return this.roleService.getFuncsByRoleId(roleId);
    }

    @Override
    public List<PmsFunc> getFuncsByRoleIds(String roleIds) {
        return this.funcDao.getFuncsByRoleIds(roleIds);
    }

	@Override
	public PadClentDevice getPadClentDeviceInfo(String deviceId) {
		return userLoginService.getPadClentDeviceInfo(deviceId);
	}

	@Override
	public void innsertPadClentDevice(PadClentDevice padClentDevice) {
		userLoginService.innsertPadClentDevice(padClentDevice);
	}

    @Override
    public Integer getGroupIdByUserId(Integer userId) {
        SysTeamMember member = groupService.getMemberByUserId(userId);
        if(null!=member){
            return member.getTeamGroupId();
        }
        return  null;

    }

	@Override
     public List<SysTeamGroup_Query> queryAllSysTeamGroup() {
        return groupService.getAllGroups();
    }

    @Override
    public List<SysTeamMember_Query> getMangerByGroupId(String groupId) {
        return groupService.getMembersByGroupAndRoles(groupId, GroupRolesEnum.MANAGER.getRoleId().toString());
    }

	@Override
	public PmsUser getPmsUserByUserId(Integer userId) {
		return userService.getUserById(userId);
	}


    @Override
    public List<SysTeamGroup> queryGroupListByGroupIds(String ids) {
        return sysTeamGroupService.queryGroupListByGroupIds(ids);
    }

    /**
     * 得到用户名
     * @param userIds
     * @return
     */
    public String[] getUserNamesByIds(String[] userIds){
        return userService.getUserNamesByIds(userIds);
    }

    @Override
    public IPageList<SysTeamMember_Query> queryMemberListByRoles(IPageSize page, String groupId, String memberUser) {
        return sysTeamGroupService.getMembersByGroupAndRoles(page, groupId, memberUser, GroupRolesEnum.MANAGER.getRoleId().toString());
    }

    @Override
    public List<PmsRole> getRoleIdByUID(Integer uid) {
        return roleService.getRoleIdByUID(uid);
    }

    @Override
    public PmsUser queryUserGroupPermitByUserId(String userId) {
        return userService.queryUserGroupPermitByUserId(userId);
    }
    @Override
    public List<PmsRole> getUserAllRoles(){
        return roleService.getUserAllRoles();
    }

    /**
     * 根据角色查询用户
     */
    public List<PmsUser> queryUserListByRoleId(String roleId,String teamGroupId){
        return userService.queryUserListByRoleId(roleId,teamGroupId);
    }

    public List<PmsRole>  getTeamRolesByGroupId(Integer groupId){
        return roleService.getTeamRolesByGroupId(groupId);
    }

    @Override
    public String checkDataByUserId(String teamIds, Integer userId) {
        if (userId == null) {
            return "nopermitform";
        }
        //角色判断

        if (StringUtils.isBlank(teamIds)){
            //如果teamId为空的时候，传入-1防止in语句报错，返回空结果
            teamIds = "-1";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("teamIds", teamIds);
        params.put("userId", userId);
        Integer userIds =  userService.getUserIdsByTeamIds(params);
        return userIds == 0 ? "nopermitform" : null;
    }

}
