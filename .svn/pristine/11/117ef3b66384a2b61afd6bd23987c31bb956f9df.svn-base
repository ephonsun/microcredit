package banger.dao.impl;

import java.util.List;

import banger.dao.intf.IRoleMenuDao;
import banger.domain.permission.PmsRoleMenu;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class RoleMenuDao extends EntityDao<PmsRoleMenu> implements IRoleMenuDao {

	/**
	 * 通过角色得到菜单
	 * @param roleIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsRoleMenu> getMenuByRoleIds(String roleIds){
		return (List<PmsRoleMenu>)this.queryEntities("getMenuByRoleIds", roleIds);
	}
	
	/**
	 * 通过角色得到菜单
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public List<PmsRoleMenu> getRoleMenusByRoleId(Integer roleId){
		return (List<PmsRoleMenu>)this.queryEntities("getRoleMenusByRoleId",roleId);
	}
	
	/**
	 * 查询角色菜单Id集合
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMenuIdsByRoleId(Integer roleId){
		return (List<String>)this.queryValueList("getMenuIdsByRoleId", roleId);
	}
	
	/**
	 * 新增角色菜单
	 * @param roleMenu
	 */
	public void insertRoleMenu(PmsRoleMenu roleMenu){
		roleMenu.setPrmRoleMenuId(this.newId().intValue());
		this.execute("insertRoleMenu", roleMenu);
	}
	
	/**
	 * 删除角色菜单
	 * @param roleMenuId
	 */
	public void deleteRoleMenuById(Integer roleMenuId){
		this.execute("deleteRoleMenuById", roleMenuId);
	}
	
	/**
	 * 删除角色菜单
	 * @param roleId
	 */
	public void deleteMenuByRoleId(Integer roleId){
		this.execute("deleteMenuByRoleId", roleId);
	}
	
}
