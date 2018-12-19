package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PmsDataPermit;

/**
 * 数据访问接口
 */
public interface IDataPermitDao {

	/**
	 * 新增
	 * @param dataPermit 实体对像
	 */
	void insertDataPermit(PmsDataPermit dataPermit);

	/**
	 *修改
	 * @param dataPermit 实体对像
	 */
	void updateDataPermit(PmsDataPermit dataPermit);

	/**
	 * 通过主键删除
	 * @param pdpId 主键Id
	 */
	void deleteDataPermitById(Integer pdpId);

	/**
	 * 通过主键得到
	 * @param pdpId 主键Id
	 */
	PmsDataPermit getDataPermitById(Integer pdpId);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<PmsDataPermit> queryDataPermitList(Map<String, Object> condition);

	/**
	 * 通过用户ID查询PmsDataPermit列表
	 *
	 * @param roleId
	 * @return
	 */
	List<PmsDataPermit> queryDataPermitListByRoleId(Integer roleId);
	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<PmsDataPermit> queryDataPermitList(Map<String, Object> condition, IPageSize page);


	/**
	 * 判断是否具有改类型的数据权限
	 *
	 *
	 * @param dataPermitType
	 * @param userId
	 * @return
	 */
	 boolean haveDataPermit(String dataPermitType, Integer userId);

}
