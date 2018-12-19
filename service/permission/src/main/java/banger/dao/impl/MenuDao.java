package banger.dao.impl;

import java.util.ArrayList;
import java.util.List;

import banger.dao.intf.IMenuDao;
import banger.domain.permission.PmsMenu;
import banger.framework.dao.EntityDao;
import banger.framework.util.StringUtil;
import org.springframework.stereotype.Repository;

@Repository
public class MenuDao extends EntityDao<PmsMenu> implements IMenuDao {

	/**
	 * 得到所有菜单
	 */
	@SuppressWarnings("unchecked")
	public List<PmsMenu> getAllMenu() {
		return (List<PmsMenu>)this.queryEntities("getAllMenu");
	}
	
	/**
	 * 通过角色得到菜单
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsMenu> getMenusByRoleId(Integer roleId){
		return (List<PmsMenu>)this.queryEntities("getMenusByRoleId", roleId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PmsMenu> getMenusByRoleIds(String roleIds){
		if(StringUtil.isNotEmpty(roleIds)){
			return (List<PmsMenu>)this.queryEntities("getMenusByRoleIds", roleIds);
		}
		return new ArrayList<PmsMenu>();
	}

}
