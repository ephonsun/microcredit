package banger.dao.impl;

import banger.dao.intf.IUserRoleDao;
import banger.domain.permission.PmsUserRoles;
import banger.domain.permission.PmsUserRoles_Query;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRoleDao extends EntityDao<PmsUserRoles> implements IUserRoleDao {

	/**
	 * 通过用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsUserRoles> getUserRolesByUserId(Integer userId){
		return (List<PmsUserRoles>)this.queryEntities("getUserRolesByUserId", userId);
	}
	
	/**
	 * 新增用户角色
	 * @param userRole
	 */
	public void insertUserRole(PmsUserRoles userRole){
		userRole.setPurUserRolesId(this.newId().intValue());
		this.execute("insertUserRole", userRole);
	}
	/**
	 * 批量新增用户角色
	 * @param userRoles
	 */
	public void insertUserRoles(List<PmsUserRoles> userRoles){
        if(userRoles != null && userRoles.size() > 0){
            for (PmsUserRoles userRole : userRoles){
                userRole.setPurUserRolesId(this.newId().intValue());
            }
            this.executes("insertUserRole", userRoles);
        }
	}

	@Override
	public List<PmsUserRoles_Query> getUserByRoleId(Map<String, Object> condition) {
		return (List<PmsUserRoles_Query>)this.queryEntities("getUserByRoleId", condition);
	}

	@Override
	public List<PmsUserRoles_Query> getUserByGroupId(Map<String, Object> condition) {
		return (List<PmsUserRoles_Query>)this.queryEntities("getUserByGroupId", condition);
	}

	/**
	 * 删除用户角色
	 * @param userRole
	 */
	public void deleteUserRoleById(Integer userRoleId){
		this.execute("deleteUserRoleById", userRoleId);
	}
	
	/**
	 * 通过用户主键,删除用户角色
	 * @param userId
	 */
	public void deleteRoleByUserId(Integer userId){
		this.execute("deleteRoleByUserId", userId);
	}
	
}
