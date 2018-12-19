package banger.dao.intf;

import java.util.List;

import banger.domain.permission.PmsMenu;

public interface IMenuDao {
	
	/**
	 * 得到所有菜单
	 * @return
	 */
	List<PmsMenu> getAllMenu();
	
	/**
	 * 通过角色得到菜单
	 * @param roleId
	 * @return
	 */
	List<PmsMenu> getMenusByRoleId(Integer roleId);
	
	/**
	 * 
	 * @param roleIds
	 * @return
	 */
	List<PmsMenu> getMenusByRoleIds(String roleIds);
	
}
