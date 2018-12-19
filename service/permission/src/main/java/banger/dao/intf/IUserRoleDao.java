package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.permission.PmsUserRoles;
import banger.domain.permission.PmsUserRoles_Query;

public interface IUserRoleDao {
	
	/**
	 * 通过用户
	 * @return
	 */
	List<PmsUserRoles> getUserRolesByUserId(Integer userId);
	
	/**
	 * 新增用户角色
	 * @param userRole
	 */
	void insertUserRole(PmsUserRoles userRole);
	
	/**
	 * 删除用户角色
	 * @param userRole
	 */
	void deleteUserRoleById(Integer userRoleId);
	
	/**
	 * 通过用户主键,删除用户角色
	 * @param userId
	 */
	void deleteRoleByUserId(Integer userId);

    void insertUserRoles(List<PmsUserRoles> userRoles);

	List<PmsUserRoles_Query> getUserByRoleId(Map<String, Object> condition);

	List<PmsUserRoles_Query> getUserByGroupId(Map<String, Object> condition);
}
