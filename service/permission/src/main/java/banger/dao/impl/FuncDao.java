package banger.dao.impl;

import java.util.ArrayList;
import java.util.List;

import banger.dao.intf.IFuncDao;
import banger.domain.permission.PmsFunc;
import banger.framework.dao.EntityDao;
import banger.framework.util.StringUtil;
import org.springframework.stereotype.Repository;

@Repository
public class FuncDao extends EntityDao<PmsFunc> implements IFuncDao {
	
	/**
	 * 得到所有功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsFunc> getAllFunc(){
		return (List<PmsFunc>)this.queryEntities("getAllFunc");
	}

	/**
	 * 查询角色功能
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsFunc> getFuncsByRoleId(Integer roleId){
		return (List<PmsFunc>)this.queryEntities("getFuncsByRoleId", roleId);
	}
	
	/**
	 * 得到操作权限
	 * @param roleIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsFunc> getFuncsByRoleIds(String roleIds){
		if(StringUtil.isNotEmpty(roleIds)){
			return (List<PmsFunc>)this.queryEntities("getFuncsByRoleIds", roleIds);
		}
		return new ArrayList<PmsFunc>();
	}
	
}
