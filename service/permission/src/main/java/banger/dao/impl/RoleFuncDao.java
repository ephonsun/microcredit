package banger.dao.impl;

import java.util.List;

import banger.dao.intf.IRoleFuncDao;
import banger.domain.permission.PmsRoleFunc;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class RoleFuncDao extends EntityDao<PmsRoleFunc> implements IRoleFuncDao {

	/**
	 * 通过角色主键,得到角色功能
	 * @param roleId
	 */
	@SuppressWarnings("unchecked")
	public List<PmsRoleFunc> getRoleFuncsByRoleId(Integer roleId){
		return (List<PmsRoleFunc>)this.queryEntities("getRoleFuncsByRoleId", roleId);
	}
	
	/**
	 * 查询角色功能Id集合
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFuncIdsByRoleId(Integer roleId){
		return (List<String>)this.queryValueList("getFuncIdsByRoleId", roleId);
	}
	
	/**
	 * 新增角色功能
	 * @param roleFunc
	 */
	public void insertRoleFunc(PmsRoleFunc roleFunc){
		roleFunc.setPrfRoleFuncId(this.newId().intValue());
		this.execute("insertRoleFunc", roleFunc);
	}
	
	/**
	 * 删除角色功能
	 * @param roleFuncId
	 */
	public void deleteRoleFuncById(Integer roleFuncId){
		this.execute("deleteRoleFuncById", roleFuncId);
	}
	
	/**
	 * 删除角色功能
	 * @param roleId
	 */
	public void deleteFuncByRoleId(Integer roleId){
		this.execute("deleteFuncByRoleId", roleId);
	}
	
}
