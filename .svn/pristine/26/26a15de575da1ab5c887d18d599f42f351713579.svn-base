package banger.service.impl;

import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import banger.dao.intf.IDeptDao;
import banger.dao.intf.IUserDao;
import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDeptArea;
import banger.domain.permission.PmsDept_Query;
import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUserArea;
import banger.framework.util.ArrayUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ICusBasicDataProvider;
import banger.service.intf.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptService implements IDeptService {

	@Autowired
	private IDeptDao deptDao;

	@Autowired
	private IUserDao userDao;

	private ICusBasicDataProvider cusBasicDataProvider;

	private final int searchCodeLen = 3;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	/**
	 * 新增机构
	 */
	public void addDept(PmsDept dept,Integer loginUserId) {
		String searchCode = this.getSearchCode(dept.getDeptParentId());
		Integer sortNo = this.getSortNo(dept.getDeptParentId());
		Integer depth = this.getDepth(dept.getDeptParentId());
		dept.setDeptDepth(depth);
		dept.setDeptSort(sortNo);
		dept.setDeptSearchCode(searchCode);
		dept.setDeptCreateDate(Calendar.getInstance().getTime());
		dept.setDeptUpdateDate(Calendar.getInstance().getTime());
		dept.setDeptCreateUser(loginUserId);
		dept.setDeptUpdateUser(loginUserId);
		this.deptDao.insertDept(dept);
	}
	
	/**
	 * 修改机关构
	 * @param dept
	 */
	public void editDept(PmsDept dept,Integer loginUserId) {
		PmsDept oldDept = this.deptDao.getDeptById(dept.getDeptId());
		boolean parentDirty = !oldDept.getDeptParentId().equals(dept.getDeptParentId());
		dept.setDeptUpdateUser(loginUserId);
		dept.setDeptUpdateDate(Calendar.getInstance().getTime());
		if(parentDirty){
			Integer sortNo = this.getSortNo(dept.getDeptParentId());
			Integer depth = this.getDepth(dept.getDeptParentId());
			dept.setDeptDepth(depth);
			dept.setDeptSort(sortNo);
		}
		this.deptDao.updateDept(dept);
		if(parentDirty)this.resetAllSearchCode();
	}
	
	/**
	 * 通过主键得到机构实例
	 */
	public PmsDept_Query getDeptById(Integer deptId) {
		return this.deptDao.getDeptById(deptId);
	}
	
	/**
	 * 通过主键得到机构名称
	 * @param deptId
	 * @return
	 */
	public String getDeptNameById(Integer deptId){
		return this.deptDao.getDeptNameById(deptId);
	}
	
	/**
	 * 通过用户获得管理的机构数
	 * @param userId
	 * @return
	 */
	public List<PmsDept> getDeptsByUserId(Integer userId) {
		return this.deptDao.getDeptsByUserId(userId,2);
	}
	
	/**
	 * 校验新增或修改机构
	 */
	public JSONArray checkSaveDept(PmsDept dept){
		JSONArray ja = new JSONArray();
		if(this.deptDao.checkUniqueDeptCode(dept.getDeptCode(), dept.getDeptId())){
			JSONObject jo = new JSONObject();
			jo.put("id", "deptCode");
			jo.put("message", "已存在相同的机构编码");
			ja.add(jo);
		}
		if(this.deptDao.checkUniqueDeptName(dept.getDeptName(), dept.getDeptId(),dept.getDeptParentId())){
			JSONObject jo = new JSONObject();
			jo.put("id", "deptName");
			jo.put("message", "已存在相同的机构名称");
			ja.add(jo);
		}
		return ja.size()>0?ja:null;
	}
	
	/**
	 * 得到机构查询码
	 * @return
	 */
	private String getSearchCode(Integer parentDeptId){
		int searchCode = 0;
		String parentSearchCode = "";
		PmsDept parentDept = this.deptDao.getDeptById(parentDeptId);
		if(parentDept!=null){
			parentSearchCode = parentDept.getDeptSearchCode();
			List<PmsDept> depts = this.deptDao.getChildrenByParentId(parentDeptId);
			for(PmsDept dept : depts){
				String code = dept.getDeptSearchCode();
				int deptSearchCode = Integer.parseInt(code.substring(code.length()-searchCodeLen));
				if(deptSearchCode>searchCode)searchCode=deptSearchCode;
			}
		}
		return parentSearchCode+StringUtil.padLeft(String.valueOf(searchCode+1), searchCodeLen, '0');
	}
	
	/**
	 * 重置机构所有查询问码
	 * @return
	 */
	private void resetAllSearchCode(){
		List<PmsDept> depts = this.deptDao.getAllDept();
		Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
		Map<Integer,Integer> countMap = new HashMap<Integer,Integer>();
		for(PmsDept dept : depts){
			Integer parentId = dept.getDeptParentId();
			String parentSearchCode = deptMap.containsKey(parentId)?deptMap.get(parentId).getDeptSearchCode():"";
			Integer count = countMap.containsKey(parentId)?countMap.get(parentId):0;
			dept.setDeptSearchCode(parentSearchCode+StringUtil.padLeft(String.valueOf(count+1), searchCodeLen, '0'));
			deptMap.put(dept.getDeptId(), dept);
			countMap.put(dept.getDeptParentId(), count+1);
			this.deptDao.updateDeptSearchCodeById(dept.getDeptId(), dept.getDeptSearchCode());
		}
	}
	
	/**
	 * 得到机构排序号
	 * @param parentDeptId
	 * @return
	 */
	private Integer getSortNo(Integer parentDeptId){
		int sortNo = 0;
		List<PmsDept> depts = this.deptDao.getChildrenByParentId(parentDeptId);
		for(PmsDept dept : depts){
			if(sortNo<dept.getDeptSort().intValue())sortNo=dept.getDeptSort();
		}
		return sortNo+1;
	}
	
	/**
	 * 取得节点深度
	 * @param parentDeptId
	 * @return
	 */
	private Integer getDepth(Integer parentDeptId){
		PmsDept parentDept = this.deptDao.getDeptById(parentDeptId);
		return parentDept.getDeptDepth()+1;
	}
	/**
	 * 修改部门管理区
	 */
	public void updateDeptItem(String itemIds,Integer deptId){
		List<PmsDeptArea> deptAreaList = deptDao.getDeptAreaList(deptId);
		List<PmsUserArea> userAreaList = userDao.getUserAreaListById(deptId);
		Set<String> areaSet = ArrayUtil.asSet(itemIds.split(","));
		//删除用户的管理片区
		for(PmsUserArea userArea : userAreaList){
			if(!areaSet.contains(userArea.getAreaId().toString())){
				this.userDao.deleleteAreaUserById(userArea.getPdaId());
			}
		}
		for(PmsDeptArea deptArea : deptAreaList){
			if(!areaSet.contains(deptArea.getAreaId().toString())){
				this.deptDao.deleleteAreaById(deptArea.getPdaId());
			}else{
				areaSet.remove(deptArea.getAreaId().toString());
			}
		}
		for(String AreaId : areaSet){
			if(!StringUtil.isNullOrEmpty(AreaId)){
				PmsDeptArea pmsDeptArea = new PmsDeptArea();
				pmsDeptArea.setAreaId(Integer.valueOf(AreaId));
				pmsDeptArea.setAreaDeptId(deptId);
				this.deptDao.insertDeptArea(pmsDeptArea);
			}
		}
	}
	
	/**
	 * 校验删除机构时是否有人员
	 * @param deptId
	 * @return
	 */
	public String checkCanDelete(String deptId){
		//获得机构
		PmsDept pmsDept=deptDao.getDeptById(Integer.parseInt(deptId));
		//获得本机构及其子机构
		List<PmsDept> pmsDepts = deptDao.getDeptByDeptSeachCode(pmsDept.getDeptSearchCode());
		for(PmsDept dept:pmsDepts){
			//查看该机构下是否有人员
            if(userDao.getUserDeptIdcount(dept.getDeptId())>0){
                return "hasUser";
            }
            //查看该机构下是否有客户
            if(cusBasicDataProvider != null && cusBasicDataProvider.getBelongDeptIdCount(dept.getDeptId()) > 0 ){
            	return "hasCustomer";
            }
        }
        return "";
	}
	/**
	 * 删除机构(伪删除)
	 * @param
	 */
	public void delDept(String deptSeachCode){
		List<PmsDept> deptList = deptDao.getDeptByDeptSeachCode(deptSeachCode);
        //（伪）删除该节点的所有n级子节点
        for(PmsDept pmsDept:deptList){
            pmsDept.setDeptIsdel(1);
            deptDao.updateDept(pmsDept);
        }
	}
	/**
	 * 上移机构
	 * @param currentDeptId
	 * @param prevDeptId
	 * @param userId
	 */
	public void moveupPmsDept(int currentDeptId, int prevDeptId, int userId){
		movetoPmsDept(currentDeptId, prevDeptId, userId);
	}

	/**
	 * 下移机构
	 * @param currentDeptId
	 * @param nextDeptId
	 * @param userId
	 */
	public void movedownPmsDept(int currentDeptId, int nextDeptId, int userId){
		movetoPmsDept(currentDeptId, nextDeptId, userId);
	}
	
	private void movetoPmsDept(int originalDeptId, int moveDeptId, int userId) {
        PmsDept originalDept = deptDao.getDeptById(originalDeptId);
        int sourceSort = originalDept.getDeptSort();
        PmsDept moveDept = deptDao.getDeptById(moveDeptId);
        int prevSort = moveDept.getDeptSort();
        originalDept.setDeptSort(prevSort);
        moveDept.setDeptSort(sourceSort);
        originalDept.setDeptUpdateUser(userId);
        moveDept.setDeptUpdateUser(userId);
        deptDao.updateDept(originalDept);
        deptDao.updateDept(moveDept);
    }

	
	/**
	 * 获取当前用户负责的部门id号集合
	 * @param userId
	 * @return 部门id集合
	 */
	public Integer[] getInchargeOfDeptIds(Integer userId){
		List<PmsDept> depts = deptDao.getDeptsByUserId(userId,2);
		if(depts!=null&&depts.size()>0){
			Integer[] deptIds = new Integer[depts.size()];
			for(int i=0;i<depts.size();i++){
				deptIds[i] = depts.get(i).getDeptId();
			}
			return deptIds;
		}
		//假如这个人负责的下属部门被删除
		return new Integer[]{-1};
	} 
	
	/**
	 * 获取当前用户负责的用户id集合
	 * @param userId
	 * @return
	 */
	public Integer[] getInchargeOfUserIds(Integer userId){
		List<PmsUser> users = userDao.getManageUsersByUserId(userId,true,false);
		if(users!=null&&users.size()>0){
			Integer[] userIds = new Integer[users.size()];
			for(int i=0;i<users.size();i++){
				userIds[i] = users.get(i).getUserId();
			}
			return userIds;
		}
			
		return new Integer[]{-1};	
	}

    /**
     * 根据机构码查询机构信息
     * @param paramMap
     * @return
     */
    public PmsDept getDeptByCode(Map<String,Object> paramMap){
        return deptDao.getDeptByCode(paramMap);
    }



    /**
     * 校验机构编号是否重复
     * @param deptCode
     * @param deptId
     */
    @Override
    public boolean checkUniqueDeptCode(String deptCode, Integer deptId) {
        return deptDao.checkUniqueDeptCode(deptCode, deptId);
    }
    
    /**
     * 校验机构名称是否重复
     * */
    public boolean checkUniqueDeptName(PmsDept dept){
        return deptDao.checkUniqueDeptName(dept.getDeptName(), dept.getDeptId(), dept.getDeptParentId());
    }
    /**
     * 校验机构的归属机构是否为当前机构的子机构
     * */
    public boolean checkDeptBelong(Integer deptId,Integer belongToDeptId) {
        PmsDept_Query dept  = deptDao.getDeptById(deptId);
        List<PmsDept> list = deptDao.getDeptByDeptSeachCode(dept.getDeptSearchCode());
        for(PmsDept d : list){
            if(d.getDeptId().equals(belongToDeptId)){
                return true;
            }
        }
        return false;
    }
}
