package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PmsDept;

/**
 * 机构表数据访问接口
 */
public interface IDeptListDao {

	/**
	 * 新增机构表
	 * @param dept 实体对像
	 */
	void insertDept(PmsDept dept);

	/**
	 *修改机构表
	 * @param dept 实体对像
	 */
	void updateDept(PmsDept dept);

	/**
	 * 通过主键删除机构表
	 * @param deptId 主键Id
	 */
	void deleteDeptById(Integer deptId);

	/**
	 * 通过主键得到机构表
	 * @param deptId 主键Id
	 */
	PmsDept getDeptById(Integer deptId);

	/**
	 * 查询机构表
	 * @param condition 查询条件
	 * @return
	 */
	List<PmsDept> queryDeptList(Map<String, Object> condition);

	/**
	 * 分页查询机构表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<PmsDept> queryDeptList(Map<String, Object> condition, IPageSize page);

    /**
     *
     * @param userId
     * @return
     */
    List<PmsDept> getDeptListTree(Integer userId);

    /**
     *
     * @param deptId
     * @return
     */
    PmsDept getDeptByDeptId(Integer deptId);

    /**
     *
     * @param deptSearchCode
     * @return
     */
    List<PmsDept> getDeptByDeptSeachCode(String deptSearchCode);

    /**
     *
     * @param userId
     * @return
     */
    public List<?> getDeptSearchCodesByUserId(Integer userId,Integer roleId);

	void updateDeptSort(Map<String, Object> deptMap);

	List<PmsDept> selectSecondDeptId(Map<String, Object> deptMap);

	List<PmsDept> getTopDeptList();

	List<PmsDept> getBottomDeptList();
}
