package banger.dao.intf;

import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUserRoles;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

public interface IRoleDao {
	
	/**
	 * 通过用户得到角色
	 * @param userId
	 * @return
	 */
	List<PmsRole> getRolesByUserId(Integer userId);
	
	/**
	 * 得到用户所有角色
	 * @return
	 */
	List<PmsRole> getUserAllRoles();
	
	/**
	 * 新增角色
	 * @param role
	 */
	void innsertRole(PmsRole role);
	
	/**
	 * 修改角色
	 * @param role
	 */
	void updateRole(PmsRole role);
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	void deleteRoleById(Integer roleId);
	
	/**
	 * 通过主键取角色
	 * @param roleId
	 */
	PmsRole getRoleById(Integer roleId);
	
	/**
	 * 查询角色列表
	 * @param condition
	 * @return
	 */
	IPageList<PmsRole> queryRoleList(IPageSize page,Map<String,Object> condition);

	/**
	 * 判断是否存在该角色名称
	 * @param role
	 * @return
	 */
	boolean checkRoleIsRepeat(PmsRole role);


	/**
	 * 校验角色是否可以删除
	 * @return
	 * @param roleId
	 */
	boolean checkRoleCanDelete(Integer roleId);

	List<PmsRole> getRolesByType(Integer type);
	
	/**
	 * 查询所有可以审批的角色
	 * @return
	 */
	List<PmsRole> getCanApprovalRole();

    List<PmsUserRoles> getPmsRoleListByUserId(Integer userId);

	/**
	 * 通过用户id查询用户角色
	 * @param uid
	 * @return
	 */
	List<PmsRole> getRoleIdByUID(Integer uid);

	public List<PmsRole>  getTeamRolesByGroupId(Integer groupId);
}
