package banger.dao.intf;

import java.util.List;
import java.util.Set;

import banger.domain.permission.PmsDeptImport;

/**
 * 机构导入
 * @author zhusw
 *
 */
public interface IDeptImportDao {
	
	/**
	 * 插入一批机构
	 * @param deptList
	 */
	void insertDeptListByImport(List<PmsDeptImport> deptList);
	
	/**
	 * 修改一批机构
	 * @param deptList
	 */
	void updateDeptListByImport(List<PmsDeptImport> deptList);
	
	/**
	 * 修改一批机构
	 * @return
	 */
	List<PmsDeptImport> getExistDeptListByDeptCodes(Set<String> codes);
	
	/**
	 * 得到所有机构
	 * @return
	 */
	List<PmsDeptImport> getAllExistDeptList();
	
	/**
	 * 产生一个新的机构ID
	 * @return
	 */
	Integer newDeptId();

	/**
	 * 根据父编码查询下级部门list
	 * @param parentCode
	 * @return
	 */
	List<PmsDeptImport> getExistDeptListByParentCode(String parentCode);
	
}
