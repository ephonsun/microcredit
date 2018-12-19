package banger.service.intf;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDept_Query;

public interface IDeptService {
	
	/**
	 * 
	 * @param dept
	 */
	void addDept(PmsDept dept,Integer loginUserId);
	
	/**
	 * 修改机关构
	 * @param dept
	 */
	void editDept(PmsDept dept,Integer loginUserId);
	
	/**
	 * 通过用户获得管理的机构数
	 * @param userId
	 * @return
	 */
	List<PmsDept> getDeptsByUserId(Integer userId);
	
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
	 * 校验新增或修改机构
	 * @return
	 */
	JSONArray checkSaveDept(PmsDept dept);
	/**
	 * 修改部门管理区
	 */
	void updateDeptItem(String itemIds,Integer deptId);
	/**
	 * 校验删除机构时是否有人员
	 * @param deptId
	 * @return
	 */
	public String checkCanDelete(String deptId);
	
	/**
	 * 删除机构(伪删除)
	 * @param deptId
	 */
	public void delDept(String deptSeachCode);
	
	/**
	 * 上移机构
	 * @param currentDeptId
	 * @param prevDeptId
	 * @param userId
	 */
	public void moveupPmsDept(int currentDeptId, int prevDeptId, int userId);

	/**
	 * 下移机构
	 * @param currentDeptId
	 * @param nextDeptId
	 * @param userId
	 */
	public void movedownPmsDept(int currentDeptId, int nextDeptId, int userId);
	
	/**
	 * 获取当前用户负责的部门id号集合
	 * @param userId
	 * @return 部门id集合
	 */
	public Integer[] getInchargeOfDeptIds(Integer userId);
	
	/**
	 * 获取当前用户负责的用户id集合
	 * @param userId
	 * @return
	 */
	public Integer[] getInchargeOfUserIds(Integer userId);

	/**
	 * 校验机构编号是否重复
	 * */
	public boolean checkUniqueDeptCode(String deptCode,Integer deptId);
	
	/**
	 * 校验机构名称是否重复
	 * */
	boolean checkUniqueDeptName(PmsDept dept);
	
    PmsDept getDeptByCode(Map<String, Object> paramMap);
    /**
     * 校验机构归属机构是否当前机构的子机构
     * */
    boolean checkDeptBelong(Integer deptId,Integer belongToDeptId);
}
