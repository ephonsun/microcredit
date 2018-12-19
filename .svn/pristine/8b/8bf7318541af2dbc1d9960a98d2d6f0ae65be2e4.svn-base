package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PmsDept;

/**
 * 机构表业务访问接口
 */
public interface IDeptListService {

	/**
	 * 新增机构表
	 * @param dept 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertDept(PmsDept dept, Integer loginUserId);

	/**
	 *修改机构表
	 * @param dept 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateDept(PmsDept dept, Integer loginUserId);

	/**
	 * 通过主键删除机构表
	 * @param DEPT_ID 主键Id
	 */
	void deleteDeptById(Integer deptId);

	/**
	 * 通过主键得到机构表
	 * @param DEPT_ID 主键Id
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
    List<PmsDept> getDeptListTree(Integer userId);

	List<PmsDept> getTopDeptList();

	List<PmsDept> getBottomDeptList();

    PmsDept getDeptByDeptId(Integer deptId);

    String checkCanDelete(String deptId);

    void delDept(String deptSeachCode);

    /**
     *
     * @param userId
     * @return
     */
    List<String> getDeptSearchCodesByUserId(Integer userId,Integer roleId);
    void updateSearchCode(Integer deptId,Integer parentDeptId);
	void updateDeptSort(Map<String, Object> deptMap);

	List<PmsDept> selectSecondDeptId(Map<String, Object> deptMap);

}
