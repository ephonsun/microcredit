package banger.dao.intf;

import java.util.List;

import banger.domain.permission.PmsFunc;

public interface IFuncDao {
	
	/**
	 * 得到所有功能
	 * @return
	 */
	List<PmsFunc> getAllFunc();

	/**
	 * 查询角色功能
	 * @param roleId
	 * @return
	 */
	List<PmsFunc> getFuncsByRoleId(Integer roleId);
	
	/**
	 * 得到操作权限
	 * @param roleIds
	 * @return
	 */
	List<PmsFunc> getFuncsByRoleIds(String roleIds);
	
}
