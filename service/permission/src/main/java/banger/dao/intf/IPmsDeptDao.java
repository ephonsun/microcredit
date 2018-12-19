package banger.dao.intf;

import java.util.List;

import banger.domain.permission.PmsDept;

public interface IPmsDeptDao {
	/**
	 * (non-Javadoc)
	 * @see banger.service.intf.IPmsDeptService#getAllPmsDeptForCache()
	 */
	public List<PmsDept> getAllPmsDeptForCache();
	
	/**
	 * 新建机构
	 * @param pmsDept
	 * @return
	 */
	public PmsDept addPmsDept(PmsDept pmsDept);
	
	/**
	 * 编辑机构
	 * @param pmsDept
	 * @return
	 */
	public PmsDept updatePmsDept(PmsDept pmsDept);

	/**
	 * 根据机构Id获取机构
	 * @param deptId
	 * @return
	 */
	public PmsDept GetPmsDeptByDeptId(int deptId);
	
	/**
	 * 根据机构ParentId获取机构
	 * @param ParentId
	 * @return
	 */
	public List<PmsDept> GetPmsDeptByDeptParentId(int ParentId);
	
	/**
     * 根据机构deptSeachCode获取机构
     * @param ParentId
     * @return
     */
    public List<PmsDept> GetPmsDeptByDeptSeachCode(String deptSeachCode);
    
	public int getCountOfDept(PmsDept pmsDept);
	
	public int getCountOfDeptByDeptCode(PmsDept pmsDept);
}
