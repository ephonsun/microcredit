package banger.service.intf;

import java.util.List;

public interface IMenuTreeService {

	/**
	 * 得到角色菜单树
	 * @return
	 */
	String getRoleMenuTree();
	
	/**
	 * 得到角色菜单树
	 * @return
	 */
	String getRoleMenuTree(List<String> checkIds);
	
	/**
	 * 
	 * @param roleIds
	 * @param userId TODO
	 * @param userId TODO
	 * @return
	 */
	String getMenuTreeByRoleIds(String roleIds, Integer userId, String account);

	/**
	 * 得到不可勾选角色菜单树
	 * @return
	 */
	String getRoleDisMenuTree(List<String> menuIds);

	/**
	 * 根扰菜单ID得到Json数据
	 * @param menuIds
	 * @return
	 */
	String getMenuByMenuIds(String[] menuIds);
	
}
