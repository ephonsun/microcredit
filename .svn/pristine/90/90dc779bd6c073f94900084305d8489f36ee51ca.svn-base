package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IDeptDao;
import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDeptArea;
import banger.domain.permission.PmsDept_Query;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class DeptDao extends EntityDao<PmsDept> implements IDeptDao {

	/**
	 * 得到下级子机构
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDept> getChildrenByParentId(Integer deptId) {
		return (List<PmsDept>)this.queryEntities("getChildrenByParentId",deptId);
	}
	
	/**
	 * 得到所有子机构
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDept> getChildrenBySearchCode(String searchCode) {
		return (List<PmsDept>)this.queryEntities("getChildrenBySearchCode",searchCode);
	}
	
	/**
	 * 查询所有机构
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDept> getAllDept() {
		return (List<PmsDept>)this.queryEntities("getAllDept");
	}
	
	/**
	 * 通过用户获得管理的机构数
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDept> getDeptsByUserId(Integer userId,Integer roleId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
		condition.put("roleId", roleId);
		//if(roleId!=null && roleId.intValue()>0){
		//	return (List<PmsDept>)this.queryEntities("getDeptListByDataPermit",condition);
		//}else{
			return (List<PmsDept>)this.queryEntities("getDeptsByUserId",condition);
		//}
	}
	
	/**
	 * 通过用户获得管理的机构数
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDept> getDeptsByUserId(Integer userId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
        //if(userId.intValue()>1){
        //	condition.put("roleId", 2);
		//    return (List<PmsDept>)this.queryEntities("getDeptListByDataPermit",condition);
        //}else{
            return (List<PmsDept>)this.queryEntities("getDeptsByUserId",condition);
        //}
	}
	
	/**
	 * 通过主键得到机构实例
	 */
	public PmsDept_Query getDeptById(Integer deptId) {
		return (PmsDept_Query)this.queryEntity("getDeptListById", deptId);
	}
	
	/**
	 * 通过主键得到机构名称
	 * @param deptId
	 * @return
	 */
	public String getDeptNameById(Integer deptId){
		return (String)this.queryValue("getDeptNameById", deptId);
	}

	/**
	 * 新增机构
	 */
	public void insertDept(PmsDept dept) {
		dept.setDeptId(this.newId().intValue());
		this.execute("insertDept", dept);
	}

	/**
	 * 修改机构
	 */
	public void updateDept(PmsDept dept) {
		this.execute("updateDept", dept);
	}
	
	/**
	 * 通过主键删除机构
	 * @param deptId
	 */
	public void deleteDept(Integer deptId){
		this.execute("deleteDept", deptId);
	}
	
	/**
	 * 单独更新查询码,不触发缓存
	 * @param deptId
	 * @param searchCode
	 */
	public void updateDeptSearchCodeById(Integer deptId,String searchCode){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("deptId", deptId);
		condition.put("searchCode", searchCode);
		this.execute("updateDeptSearchCodeById", condition);
	}
	
	/**
	 * 单独更新查询码,不触发缓存
	 * @param deptId
	 * @param searchCode
	 */
	public void updateDeptSearchCodeById(Integer deptId,String searchCode,Integer depth){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("deptId", deptId);
		condition.put("searchCode", searchCode);
		condition.put("depth", depth);
		this.execute("updateDeptSearchCodeById", condition);
	}

	/**
	 * 通过用户得到查询码
	 */
	public String getSearchCodeByUserId(Integer userId) {
		return (String)this.queryValue("getSearchCodeByUserId", userId);
	}
	
	/**
	 * 校验机构名称重复
	 * @param deptName
	 * @param deptId
	 * @return
	 */
	public boolean checkUniqueDeptName(String deptName,Integer deptId,Integer deptParentId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("deptName", deptName);
		if(deptId!=null && deptId>0)condition.put("deptId", deptId);
		condition.put("deptParentId", deptParentId);
		Integer count = (Integer)this.queryInt("checkUniqueDeptName", condition);
		return count>0;
	}

	/**
	 * 部门ID查询管理片区集合
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDeptArea> getDeptAreaList(Integer deptId){
		return (List<PmsDeptArea>)this.queryEntities("getDeptAreaList", deptId);
	}
	/**
	 * 删除关联的管理服务区
	 * @param areaId
	 */
	public void deleleteAreaById(Integer pdaId){
		this.execute("deleleteAreaById", pdaId);
	}
	/**
	 * 新增关联服务区
	 * @param pmsDeptArea
	 */
	public void insertDeptArea(PmsDeptArea pmsDeptArea){
		pmsDeptArea.setPdaId(this.newId().intValue());
		this.execute("insertDeptArea", pmsDeptArea);
	}
	/**
     * 根据机构deptSeachCode获取机构
     * @param deptSeachCode
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<PmsDept> getDeptByDeptSeachCode(String deptSeachCode){
    	return (List<PmsDept>) this.queryEntities("getDeptByDeptSeachCode", deptSeachCode);
    }

    /**
     * 根据机构码查询机构信息
     * @param paramMap
     * @return
     */
    public PmsDept getDeptByCode(Map<String,Object> paramMap){
        return (PmsDept)this.queryEntity("getDeptByCode",paramMap);
    }

    /**
     * 校验机构编码重复
     * @param deptName
     * @param deptId
     * @return
     */
    public boolean checkUniqueDeptCode(String deptCode,Integer deptId){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("deptCode", deptCode);
        if(deptId!=null && deptId>0)condition.put("deptId", deptId);
        Integer count = (Integer)this.queryInt("checkUniqueDeptCode", condition);
        return count>0;
    }

}
