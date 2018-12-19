package banger.moduleImpl;

import java.util.*;

import banger.domain.permission.*;
import banger.moduleIntf.IPermissionService;
import banger.service.intf.*;
import org.springframework.beans.factory.InitializingBean;
import banger.common.tools.RegisterInterfaceImpl;
import banger.moduleIntf.ICusBasicDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 这是老的写法，新的参考PermissionModule类
 */
@Service
public class PermissionModuleImpl implements InitializingBean, IPermissionService {

	private final String DATAPERMIT_MANAGE_DEPT="manageDept";

	@Autowired
	private IMenuTreeService menuTreeService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IUserService userService;



	/**
	 * 通过roleIds获取菜单json字符串
	 */
	public String getMenuTreeByRoleIds(String roleIds, Integer userId, String account) {
		return this.menuTreeService.getMenuTreeByRoleIds(roleIds, userId,account);
	}

	/**
	 * 根扰菜单ID得到Json数据
	 * @param menuIds
	 * @return
	 */
	public String getMenuByMenuIds(String menuIds){
		return this.menuTreeService.getMenuByMenuIds(menuIds.split("\\,"));
	}

	
	/**
	 * 通过用户ID判断是否管理机构
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public boolean isManageDept(Integer userId) {
		return roleService.haveDataPermit(DATAPERMIT_MANAGE_DEPT,userId);
	}

	/**
	 * 获取当前用户负责的部门id号集合
	 * @param userId
	 * @return 部门id集合
	 */
	public Integer[] getInchargeOfDeptIds(Integer userId){
		List<PmsDept> depts = deptService.getDeptsByUserId(userId);
		if(depts!=null&&depts.size()>0){
			Integer[] deptIds = new Integer[depts.size()];
			for(int i=0;i<depts.size();i++){
				deptIds[i] = depts.get(i).getDeptId();
			}
			return deptIds;
		}
		//假如这个人负责的下属部门被删除
		return new Integer[]{-1};
	} 
	
	/**
	 * 获取当前用户负责的用户id集合
	 * @param userId
	 * @return
	 */
	public Integer[] getInchargeOfUserIds(Integer userId){
		List<PmsUser> users = userService.getManageUsersByUserId(userId);
		if(users!=null&&users.size()>0){
			Integer[] userIds = new Integer[users.size()];
			for(int i=0;i<users.size();i++){
				userIds[i] = users.get(i).getUserId();
			}
			return userIds;
		}
			
		return new Integer[]{-1};	
	}
	
	/**
	 * 获取当前用户负责的用户id集合
	 * @param userId
	 * @return
	 */
	public Integer[] getInchargeOfUserIds(Integer userId,String roleIds){
		List<PmsUser> users = userService.getManageUsersByUserId(userId,roleIds);
		if(users!=null&&users.size()>0){
			Integer[] userIds = new Integer[users.size()];
			for(int i=0;i<users.size();i++){
				userIds[i] = users.get(i).getUserId();
			}
			return userIds;
		}
			
		return new Integer[]{-1};	
	}

	@Override
	public PmsUser_Query getUserById(Integer userId) {
		return userService.getUserById(userId);
	}

	@Override
	public PmsDept_Query getDeptById(Integer deptId) {
		return this.deptService.getDeptById(deptId);
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
	public boolean isManager(Integer userId) {
		List<PmsRole> roleList=roleService.getRolesByUserId(userId);
        for(PmsRole role:roleList){
            if(role.getRoleType()==4){
                return true;
            }
        }
        return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 *
	 * @param roleId
	 * @return
	 */
	public String getRoleNameByRoleId(Integer roleId){
		PmsRole role = roleService.getRoleById(roleId);
		if(role!=null){
			return role.getRoleName();
		}
		return "";
	}

    @Override
    public Integer queryTotolUserNum() {
        return userService.queryTotolUserNum();
    }

	/**
	 * 得到用户角色ID集合
	 * @param userId
	 * @return
	 */
	public Integer[] getRoleIdsByUserId(Integer userId){
		List<PmsRole> roleList = roleService.getRolesByUserId(userId);
		Integer[] ids = new Integer[roleList.size()];
		for(int i=0;i<roleList.size();i++){
			ids[i] = roleList.get(i).getRoleId();
		}
		return ids;
	}



}
