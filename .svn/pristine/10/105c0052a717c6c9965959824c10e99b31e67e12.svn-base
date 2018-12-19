package banger.dao.impl;

import banger.dao.intf.IRoleDao;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUserRoles;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao extends PageSizeDao<PmsRole> implements IRoleDao {

	/**
	 * 通过用户得到角色
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsRole> getRolesByUserId(Integer userId){
		return (List<PmsRole>)this.queryEntities("getRolesByUserId", userId);
	}
	
	/**
	 * 得到用户所有角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsRole> getUserAllRoles(){
		return (List<PmsRole>)this.queryEntities("getUserAllRoles");
	}
	
	/**
	 * 通过主键取角色
	 * @param roleId
	 */
	public PmsRole getRoleById(Integer roleId){
		return (PmsRole)this.queryEntity("getRoleById", roleId);
	}
	
	/**
	 * 新增角色
	 * @param role
	 */
	public void innsertRole(PmsRole role){
		role.setRoleId(this.newId().intValue());
		this.execute("insertRole", role);
	}
	
	/**
	 * 修改角色
	 * @param role
	 */
	public void updateRole(PmsRole role){
		this.execute("updateRole", role);
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void deleteRoleById(Integer roleId){
		this.execute("deleteRoleById", roleId);
	}
	
	/**
	 * 查询角色列表
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsRole> queryRoleList(IPageSize page,Map<String,Object> condition){
		return (IPageList<PmsRole>)this.queryEntities("queryRoleList",page,condition);
	}

	/**
	 * 判断是否存在该角色名称
	 *
	 * @param role
	 * @return
	 */
	@Override
	public boolean checkRoleIsRepeat(PmsRole role) {
		PmsRole pmsRole = (PmsRole)this.queryEntity("getRoleForCheckRepeat",role);
		if(pmsRole == null){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 校验角色是否可以删除
	 *
	 * @return
	 * @param roleId
	 */
	@Override
	public boolean checkRoleCanDelete(Integer roleId) {
		Integer count = this.queryInt("getUserCountByRoleId",roleId);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<PmsRole> getRolesByType(Integer type) {
		return (List<PmsRole>)this.queryEntities("getRolesByType", type);
	}

	@Override
	public List<PmsRole> getCanApprovalRole() {
		return (List<PmsRole>)this.queryEntities("getCanApprovalRole");
	}

    @Override
    public List<PmsUserRoles> getPmsRoleListByUserId(Integer userId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("purUserId",userId);
        return (List<PmsUserRoles>) this.queryEntities("getPmsRoleListByUserId",condition);
    }

	@Override
	public List<PmsRole> getRoleIdByUID(Integer uid) {
		return (List<PmsRole>) this.queryEntities("getRoleIdbyUID",uid);
	}

	@Override
	public List<PmsRole>  getTeamRolesByGroupId(Integer groupId){
		return (List<PmsRole>) this.queryEntities("getTeamRolesByGroupId",groupId);
	}

}










