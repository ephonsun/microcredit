package banger.moduleIntf;

import banger.domain.permission.PmsDept_Query;
import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUser_Query;

import java.util.List;
import java.util.Map;

public interface IPermissionService {

	/**
	 * 通过roleIds获取菜单json字符串
	 * @param roleIds
	 * @param userId TODO
	 * @return
	 */
	String getMenuTreeByRoleIds(String roleIds, Integer userId,String account);

	/**
	 * 根扰菜单ID得到Json数据
	 * @param menuIds
	 * @return
	 */
	String getMenuByMenuIds(String menuIds);

	 /**
	 * 通过用户ID判断是否管理机构
	 * @param userId
	 * @return
	 */
	boolean isManageDept(Integer userId);

	/**
	 * 获取当前用户负责的部门id号集合
	 * @param userId
	 * @return 部门id集合
	 */
	public Integer[] getInchargeOfDeptIds(Integer userId);
	
	/**
	 * 获取当前用户负责的用户id集合
	 * @param userId
	 * @return
	 */
	public Integer[] getInchargeOfUserIds(Integer userId);
	
	/**
	 * 获取当前用户负责的用户id集合
	 * @param userId
	 * @return
	 */
	public Integer[] getInchargeOfUserIds(Integer userId,String roleIds);
	
	/**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	public PmsUser_Query getUserById(Integer userId);

	public PmsDept_Query getDeptById(Integer deptId);

    /**
     * 根据机构id获取本机构下的所有柜员
     * @param params
     * @return
     */
    List<PmsUser> getUserInfoList(Map<String, Object> params);

	boolean isManager(Integer userId);

	/**
	 *
	 * @param roleId
	 * @return
	 */
	String getRoleNameByRoleId(Integer roleId);

    Integer queryTotolUserNum();

	/**
	 * 得到用户角色ID集合
	 * @param userId
	 * @return
	 */
	Integer[] getRoleIdsByUserId(Integer userId);
}
