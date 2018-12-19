package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IDeptListDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.domain.permission.PmsDept;
import org.springframework.stereotype.Repository;

/**
 * 机构表数据访问类
 */

@Repository
public class DeptListDao extends PageSizeDao<PmsDept> implements IDeptListDao {

	/**
	 * 新增机构表
	 * @param dept 实体对像
	 */
	public void insertDept(PmsDept dept){
		dept.setDeptId(this.newId().intValue());
		this.execute("insertDept",dept);
	}

	/**
	 *修改机构表
	 * @param dept 实体对像
	 */
	public void updateDept(PmsDept dept){
		this.execute("updateDept",dept);
	}

	/**
	 * 通过主键删除机构表
	 * @param deptId 主键Id
	 */
	public void deleteDeptById(Integer deptId){
		this.execute("deleteDeptById",deptId);
	}

	/**
	 * 通过主键得到机构表
	 * @param deptId 主键Id
	 */
	public PmsDept getDeptById(Integer deptId){
		return (PmsDept)this.queryEntity("getDeptById",deptId);
	}

	/**
	 * 查询机构表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDept> queryDeptList(Map<String,Object> condition){
		return (List<PmsDept>)this.queryEntities("queryDeptList", condition);
	}

	/**
	 * 分页查询机构表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsDept> queryDeptList(Map<String,Object> condition,IPageSize page){
		return (IPageList<PmsDept>)this.queryEntities("queryDeptList", page, condition);
	}

    @SuppressWarnings("unchecked")
	@Override
    /**加载树形grid*/
    public  List<PmsDept> getDeptListTree(Integer userId) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("userId", userId);
        //if(userId.intValue()>1){
        //	condition.put("roleId", 2);
        //    return (List<PmsDept>)this.queryEntities("getDeptListByDataPermit",condition);
        //}else{
            return (List<PmsDept>)this.queryEntities("getDeptsByUserId",condition);
        //}
    }

    @Override
    public PmsDept getDeptByDeptId(Integer deptId) {
        return (PmsDept)this.queryEntity("getDeptByDeptId",deptId);
    }

    /**
     *
     * @param userId
     * @return
     */
    public List<?> getDeptSearchCodesByUserId(Integer userId,Integer roleId){
        return this.queryValueList("getDeptSearchCodesByUserId",userId,roleId);
    }

    @SuppressWarnings("unchecked")
	@Override
    /**
     * 根据机构deptSeachCode获取机构
     * @param deptSeachCode
     * @return
     */
    public List<PmsDept> getDeptByDeptSeachCode(String deptSearchCode) {
        return (List<PmsDept>) this.queryEntities("getDeptByDeptSeachCode", deptSearchCode);
    }

	@Override
	public void updateDeptSort(Map<String, Object> deptMap) {
		// TODO Auto-generated method stub
		this.execute("updateDeptSort", deptMap);
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PmsDept> selectSecondDeptId(Map<String, Object> deptMap) {
		// TODO Auto-generated method stub
		return (List<PmsDept>) this.queryEntities("selectSecondDeptId", deptMap);
	}

	public List<PmsDept> getTopDeptList(){
		Map<String, Object> deptMap = new HashMap<String, Object>();
		return (List<PmsDept>) this.queryEntities("getTopDeptList",deptMap);
	}

	public List<PmsDept> getBottomDeptList(){
		Map<String, Object> deptMap = new HashMap<String, Object>();
		return (List<PmsDept>) this.queryEntities("getBottomDeptList",deptMap);
	}
}
