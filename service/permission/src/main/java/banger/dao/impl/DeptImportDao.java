package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import banger.dao.intf.IDeptImportDao;
import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDeptImport;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

@Repository
public class DeptImportDao extends EntityDao<PmsDept> implements IDeptImportDao {

	/**
	 * 插入一批机构
	 * @param deptList
	 */
	public void insertDeptListByImport(List<PmsDeptImport> deptList){
		if(deptList.size()>0){
			/*
			Long[] ids = this.newId(deptList.size());
			for(int i=0;i<deptList.size();i++){
				deptList.get(i).setDeptId(ids[i].intValue());
			}
			*/
			this.executes("insertDeptListByImport", deptList);
		}
	}
	
	/**
	 * 修改一批机构
	 * @param deptList
	 */
	public void updateDeptListByImport(List<PmsDeptImport> deptList){
		if(deptList.size()>0){
			this.executes("updateDeptListByImport", deptList);
		}
	}

	/**
	 * 通过机构编码查询已存在的机构
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDeptImport> getExistDeptListByDeptCodes(Set<String> codes) {
		return (List<PmsDeptImport>)this.queryEntities("getExistDeptListByDeptCodes", codes);
	}
	
	/**
	 * 得到所有机构
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDeptImport> getAllExistDeptList(){
		return (List<PmsDeptImport>)this.queryEntities("getAllExistDeptList", new Object[0]);
	}

	public List<PmsDeptImport> getExistDeptListByParentCode(String parentCode){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("parentCode", parentCode);
		return (List<PmsDeptImport>)this.queryEntities("getExistDeptListByParentCode",condition);
	}
	
	/**
	 * 产生一个新的机构ID
	 * @return
	 */
	public Integer newDeptId(){
		Long id = this.newId();
		return id.intValue();
	}


}
