package banger.dao.intf;

import java.util.List;

import banger.domain.permission.PmsRoleMenu;

public interface IRoleMenuDao {
	
	/**
	 * 通过角色得到菜单
	 * @param roleIds
	 * @return
	 */
	List<PmsRoleMenu> getMenuByRoleIds(String roleIds);
	
	/**
	 * 通过角色得到菜单
	 * @param roleId
	 */
	List<PmsRoleMenu> getRoleMenusByRoleId(Integer roleId);
	
	/**
	 * 查询角色菜单Id集合
	 * @param roleId
	 * @return
	 */
	List<String> getMenuIdsByRoleId(Integer roleId);
	
	/**
	 * 新增角色菜单
	 * @param roleMenu
	 */
	void insertRoleMenu(PmsRoleMenu roleMenu);
	
	/**
	 * 删除角色菜单
	 * @param roleMenuId
	 */
	void deleteRoleMenuById(Integer roleMenuId);
	
	/**
	 * 删除角色菜单
	 * @param roleId
	 */
	void deleteMenuByRoleId(Integer roleId);
	
}
