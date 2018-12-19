package banger.service.intf;

import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;

public interface IRoleService {
	
	/**
	 * 得到所有角色
	 * @return
	 */
	List<PmsRole> getUserAllRoles();
	
	/**
	 * 得到用户所有角色
	 * @return
	 */
	List<PmsRole> getRolesByUserId(Integer userId);
	
	/**
	 * 通过主键取角色
	 * @param roleId
	 */
	PmsRole getRoleById(Integer roleId);
	
	/**
	 * 查询角色菜单
	 * @param roleId
	 * @return
	 */
	List<PmsMenu> getMenusByRoleId(Integer roleId);
	
	/**
	 * 查询角色菜单Id集合
	 * @param roleId
	 * @return
	 */
	List<String> getMenuIdsByRoleId(Integer roleId);
	
	/**
	 * 查询角色功能
	 * @param roleId
	 * @return
	 */
	List<PmsFunc> getFuncsByRoleId(Integer roleId);
	
	/**
	 * 查询角色功能Id集合
	 * @param roleId
	 * @return
	 */
	List<String> getFuncIdsByRoleId(Integer roleId);
	
	/**
	 * 添加角色
	 * @param role
	 * @param loginUserId
	 */
	void addRole(PmsRole role,Integer loginUserId);
	
	/**
	 * 修改角色
	 * @param role
	 * @param loginUserId
	 */
	void editRole(PmsRole role,Integer loginUserId);
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	void deleteRoleById(Integer roleId);
	
	/**
	 * 修改角色菜单
	 * @param roleId
	 * @param menuIds
	 * @param loginUserId
	 */
	void updateRoleMenus(Integer roleId,String menuIds,Integer loginUserId);
	
	/**
	 * 修改角色功能
	 * @param roleId
	 * @param funcIds
	 * @param loginUserId
	 */
	void updateRoleFuncs(Integer roleId,String funcIds,Integer loginUserId);
	
	/**
	 * 查询角色列表
	 * @param page
	 * @param roleName
	 * @return
	 */
	IPageList<PmsRole> queryRoleList(IPageSize page,String roleName);

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


	/**
	 * 获取所有数据权限列表
	 * @return
	 */
	List<PmsDataPermit> getAllDataPermits();

	/**
	 * 通过用户roleId获取所有权限ids
	 *
	 *
	 * @param roleId
	 * @return
	 */
	String getDataPermitByRoleId(Integer roleId);

	/**
	 * 修改角色功能
	 * @param roleId
	 * @param dataPermitIds
	 * @param loginUserId
	 */
	void updateDataPermits(Integer roleId, String dataPermitIds, Integer loginUserId);

	/**
	 * 判断是否具有改类型的数据权限
	 *
	 * @param dataPermitType
	 * @param userId
	 * @return
	 */
	boolean haveDataPermit(String dataPermitType, Integer userId);

	List<PmsRole> getRolesByType(Integer type);

	List<PmsUserRoles> getPmsRoleListByUserId(Integer integer);

	/**
	 * 通过用户id查询用户角色
	 * @param uid
	 * @return
	 */
	List<PmsRole> getRoleIdByUID(Integer uid);

	public List<PmsRole>  getTeamRolesByGroupId(Integer groupId);
}
