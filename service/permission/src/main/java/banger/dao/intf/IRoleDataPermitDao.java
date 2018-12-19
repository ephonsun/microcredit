package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PmsRoleDataPermit;

/**
 * 数据访问接口
 */
public interface IRoleDataPermitDao {

	/**
	 * 新增
	 * @param roleDataPermit 实体对像
	 */
	void insertRoleDataPermit(PmsRoleDataPermit roleDataPermit);

	/**
	 *修改
	 * @param roleDataPermit 实体对像
	 */
	void updateRoleDataPermit(PmsRoleDataPermit roleDataPermit);

	/**
	 * 通过主键删除
	 * @param PRDP_ID 主键Id
	 */
	void deleteRoleDataPermitById(Integer prdpId);

	/**
	 * 通过主键得到
	 * @param PRDP_ID 主键Id
	 */
	PmsRoleDataPermit getRoleDataPermitById(Integer prdpId);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<PmsRoleDataPermit> queryRoleDataPermitList(Map<String, Object> condition);


	List<PmsRoleDataPermit> queryRoleDataPermitByRoleId(Integer roleId);
	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<PmsRoleDataPermit> queryRoleDataPermitList(Map<String, Object> condition, IPageSize page);

}
