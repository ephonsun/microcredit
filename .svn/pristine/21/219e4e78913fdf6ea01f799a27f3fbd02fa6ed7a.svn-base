package banger.dao.intf;

import java.util.List;

import banger.domain.permission.PmsRoleFunc;

public interface IRoleFuncDao {
	
	/**
	 * 通过角色主键,得到角色功能
	 * @param roleId
	 */
	List<PmsRoleFunc> getRoleFuncsByRoleId(Integer roleId);
	
	/**
	 * 查询角色功能Id集合
	 * @param roleId
	 * @return
	 */
	List<String> getFuncIdsByRoleId(Integer roleId);
	
	/**
	 * 新增角色功能
	 * @param roleFunc
	 */
	void insertRoleFunc(PmsRoleFunc roleFunc);
	
	/**
	 * 删除角色功能
	 * @param roleFuncId
	 */
	void deleteRoleFuncById(Integer roleFuncId);
	
	/**
	 * 删除角色功能
	 * @param roleId
	 */
	void deleteFuncByRoleId(Integer roleId);
}
