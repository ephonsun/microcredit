package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDeptArea;
import banger.domain.permission.PmsDept_Query;

public interface IDeptDao {
	
	/**
	 * 得到下级子机构集合
	 * @param parentDeptId
	 * @return
	 */
	List<PmsDept> getChildrenByParentId(Integer parentDeptId);
	
	/**
	 * 通过查询码查找所有子机构
	 * @param searchCode
	 * @return
	 */
	List<PmsDept> getChildrenBySearchCode(String searchCode);
	
	/**
	 * 查询所有机构,不包含删除的
	 * @param
	 * @return
	 */
	List<PmsDept> getAllDept();
	
	/**
	 * 通过用户获得管理的机构数
	 * @param userId
	 * @return
	 */
	List<PmsDept> getDeptsByUserId(Integer userId);
	
	/**
	 * 通过用户获得管理的机构数
	 * @param userId
	 * @return
	 */
	List<PmsDept> getDeptsByUserId(Integer userId,Integer roleId);
	
	/**
	 * 通过主键得到机构实例
	 * @param deptId
	 * @return
	 */
	PmsDept_Query getDeptById(Integer deptId);
	
	/**
	 * 通过主键得到机构名称
	 * @param deptId
	 * @return
	 */
	String getDeptNameById(Integer deptId);
	
	/**
	 * 新增机构
	 */
	void insertDept(PmsDept dept);

	/**
	 * 修改机构
	 */
	void updateDept(PmsDept dept);
	
	/**
	 * 通过主键删除机构
	 * @param deptId
	 */
	void deleteDept(Integer deptId);
	
	/**
	 * 单独更新查询码,不触发缓存
	 * @param deptId
	 * @param searchCode
	 */
	void updateDeptSearchCodeById(Integer deptId,String searchCode);
	
	/**
	 * 单独更新查询码,不触发缓存
	 * @param deptId
	 * @param searchCode
	 */
	void updateDeptSearchCodeById(Integer deptId,String searchCode,Integer depth);
	
	/**
	 * 通过用户得到机构查询码
	 * @param userId
	 * @return
	 */
	String getSearchCodeByUserId(Integer userId);
	
	/**
	 * 校验机构名称重复
	 * @param deptName
	 * @param deptId
	 * @return
	 */
	boolean checkUniqueDeptName(String deptName,Integer deptId,Integer deptParentId);
	
	/**
	 * 校验机构编码重复
	 * @param deptName
	 * @param deptId
	 * @return
	 */
	boolean checkUniqueDeptCode(String deptCode,Integer deptId);
	/**
	 * 部门ID查询管理片区集合
	 * @param deptId
	 * @return
	 */
	List<PmsDeptArea> getDeptAreaList(Integer deptId);
	/**
	 * 删除关联的管理服务区
	 * @param areaId
	 */
	void deleleteAreaById(Integer pdaId);
	/**
	 * 新增关联服务区
	 * @param pmsDeptArea
	 */
	void insertDeptArea(PmsDeptArea pmsDeptArea);
	
	/**
     * 根据机构deptSeachCode获取机构
     * @param deptSeachCode
     * @return
     */
    List<PmsDept> getDeptByDeptSeachCode(String deptSeachCode);

    PmsDept getDeptByCode(Map<String, Object> paramMap);


    //List<PmsDept> getDeptByUserId(Integer userId);

}
