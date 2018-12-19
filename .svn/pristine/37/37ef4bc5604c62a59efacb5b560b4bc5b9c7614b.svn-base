package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IRoleDataPermitDao;
import banger.domain.permission.PmsRoleDataPermit;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 */

@Repository
public class RoleDataPermitDao extends PageSizeDao<PmsRoleDataPermit> implements IRoleDataPermitDao {

	/**
	 * 新增
	 * @param roleDataPermit 实体对像
	 */
	public void insertRoleDataPermit(PmsRoleDataPermit roleDataPermit){
		roleDataPermit.setPrdpId(this.newId().intValue());
		this.execute("insertRoleDataPermit",roleDataPermit);
	}

	/**
	 *修改
	 * @param roleDataPermit 实体对像
	 */
	public void updateRoleDataPermit(PmsRoleDataPermit roleDataPermit){
		this.execute("updateRoleDataPermit", roleDataPermit);
	}

	/**
	 * 通过主键删除
	 * @param prdpId 主键Id
	 */
	public void deleteRoleDataPermitById(Integer prdpId){
		this.execute("deleteRoleDataPermitById",prdpId);
	}

	/**
	 * 通过主键得到
	 * @param prdpId 主键Id
	 */
	public PmsRoleDataPermit getRoleDataPermitById(Integer prdpId){
		return (PmsRoleDataPermit)this.queryEntity("getRoleDataPermitById",prdpId);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsRoleDataPermit> queryRoleDataPermitList(Map<String,Object> condition){
		return (List<PmsRoleDataPermit>)this.queryEntities("queryRoleDataPermitList", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PmsRoleDataPermit> queryRoleDataPermitByRoleId(Integer roleId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("prdpRoleId",roleId);
		return (List<PmsRoleDataPermit>)this.queryEntities("queryRoleDataPermitList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsRoleDataPermit> queryRoleDataPermitList(Map<String,Object> condition,IPageSize page){
		return (IPageList<PmsRoleDataPermit>)this.queryEntities("queryRoleDataPermitList", condition, page);
	}

}
