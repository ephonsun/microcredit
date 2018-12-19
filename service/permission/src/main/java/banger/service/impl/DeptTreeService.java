package banger.service.impl;

import banger.dao.intf.IDeptDao;
import banger.dao.intf.IUserDao;
import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsUser;
import banger.service.intf.IDeptTreeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptTreeService implements IDeptTreeService {
	@Autowired
	private IDeptDao deptDao;
	@Autowired
	private IUserDao userDao;
	/**
	 * 
	 * @param deptDao
	 */
	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * 得到上级机构树
	 * @param userId
	 * @return
	 */
	public String getEditDeptTreeByUserId(Integer userId){
		List<PmsDept> depts = this.deptDao.getDeptsByUserId(userId);
		Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(int i=0;i<depts.size();i++){
			PmsDept dept = depts.get(i);
			deptMap.put(dept.getDeptId(), dept);
			if(!deptMap.containsKey(dept.getDeptParentId()))rootCount++;
			
			JSONObject jo = new JSONObject();
			jo.put("id", dept.getDeptId());
			jo.put("pId", dept.getDeptParentId());
			jo.put("name", dept.getDeptName());
			jo.put("icon", "../core/imgs/icon/agency.gif");
			ja.add(jo);
		}
		
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}

	/**
	 * 通过用户得到机构树
	 */
	public String getDeptTreeByUserId(Integer userId,String roleIds) {
		/*
		Set<String> roleIdSet = new HashSet<String>();
		for(String id : roleIds.split(","))roleIdSet.add(id);
		List<PmsDept> depts = new ArrayList<PmsDept>();//包含没有用户的机构，用户没有服务经理限制条件
		if(roleIdSet.contains("1")){
			depts = this.deptDao.getDeptsByUserId(userId,0);
		}else{
			if(roleIdSet.contains("3")){
				depts = this.deptDao.getDeptsByUserId(userId,3);
			}
			else if(roleIdSet.contains("2")){
				depts = this.deptDao.getDeptsByUserId(userId,2);
			}else if(roleIdSet.contains("4") || roleIdSet.contains("5")){
				depts = this.deptDao.getDeptsByUserId(userId,0);
			}
		}
		*/
		List<PmsDept> depts = this.deptDao.getDeptsByUserId(userId,0);
		Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(int i=0;i<depts.size();i++){
			PmsDept dept = depts.get(i);
			deptMap.put(dept.getDeptId(), dept);
			if(!deptMap.containsKey(dept.getDeptParentId()))rootCount++;
			
			JSONObject jo = new JSONObject();
			jo.put("id", dept.getDeptId());
			jo.put("pId", dept.getDeptParentId());
			jo.put("name", dept.getDeptName());
			jo.put("icon", "../core/imgs/icon/agency.gif");
			ja.add(jo);
		}
		
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}


	private JSONObject getDeptJson(PmsDept dept){
		JSONObject jo = new JSONObject();
		jo.put("id", "d-"+dept.getDeptId());
		jo.put("pId", "d-"+dept.getDeptParentId());
		jo.put("name", dept.getDeptName());
		jo.put("icon", "../core/imgs/icon/agency.gif");
		jo.put("flag","D");
		return jo;
	}


	private JSONObject getUserJson(PmsUser user){
		JSONObject jo = new JSONObject();
		jo.put("id", "u-"+user.getUserId());
		jo.put("pId", "d-"+user.getUserDeptId());
		jo.put("name", user.getUserName());
		jo.put("icon", "../core/imgs/icon/staff.gif");
		jo.put("flag","U");
		return jo;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public String getDeptUserTreeByUserId(Integer userId,String roleIds){
		
		/*
		List<PmsDept> depts = new ArrayList<PmsDept>();
		List<PmsUser> users = new ArrayList<PmsUser>();
		Set<String> roleIdSet = new HashSet<String>();
		for(String id : roleIds.split(","))roleIdSet.add(id);
		if(roleIdSet.contains("1")){
			users = this.userDao.getManageUsersByUserId(userId);
			depts = this.deptDao.getDeptsByUserId(userId,0);
		}else{
			if(roleIdSet.contains("3")){
				users = this.userDao.getManageUsersByUserId(userId,3);
				depts = this.deptDao.getDeptsByUserId(userId,3);
			}
			else if(roleIdSet.contains("2")){
				users = this.userDao.getManageUsersByUserId(userId,2);
				depts = this.deptDao.getDeptsByUserId(userId,2);
			}else if(roleIdSet.contains("4") || roleIdSet.contains("5")){
				users = this.userDao.getManageUsersByUserId(userId);
				depts = this.deptDao.getDeptsByUserId(userId,0);
			}
		}*/
		List<PmsUser> users = this.userDao.getManageUsersByUserId(userId);
		List<PmsDept> depts = this.deptDao.getDeptsByUserId(userId,0);
		Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(PmsDept dept : depts){
			deptMap.put(dept.getDeptId(), dept);
			if(!deptMap.containsKey(dept.getDeptParentId()))rootCount++;
			ja.add(getDeptJson(dept));
		}

		for(PmsUser user : users){
			if("admin".equals(user.getUserName()))
				continue;
			ja.add(getUserJson(user));
		}
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}

    /**
     * 获取机构人员树
     * @param userId
     * @return
     */
    @Override
	public String getDeptUserTreeByUserId(Integer userId,Boolean inculeNoUserDept,Boolean noManagerLimit, Boolean isBranch) {
		List<PmsDept> depts = this.deptDao.getDeptsByUserId(userId);
		List<PmsUser> users = this.userDao.getManageUsersByUserId(userId,true,isBranch);
		Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
		JSONArray ja = new JSONArray();
		int rootCount = 0;			//根节点数量
		for(PmsDept dept : depts){
			deptMap.put(dept.getDeptId(), dept);
			if(!deptMap.containsKey(dept.getDeptParentId()))rootCount++;
			ja.add(getDeptJson(dept));
		}

		for(PmsUser user : users){
			if("admin".equals(user.getUserName()))
				continue;
			ja.add(getUserJson(user));
		}
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}

	/**
	 * 获取机构人员片区树
	 *
	 *
	 * @param userId
	 * @param isManageDept
	 * @return
	 */
	@Override
	public String getDeptUserAreaTreeByUserId(Integer userId, boolean isManageDept) {
		Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
		int rootCount = 0;			//根节点数量
		List<PmsDept> depts = new ArrayList<PmsDept>();
		List<PmsUser> users = new ArrayList<PmsUser>();
		List<Map<String,Object>> deptAreaList = new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> userAreaList = new ArrayList<Map<String, Object>>();
		if(isManageDept){
			depts = this.deptDao.getDeptsByUserId(userId);//包含没有用户的机构，用户没有服务经理限制
			users = this.userDao.getManageUsersByUserId(userId,true,false);//没有服务经理限制
			deptAreaList = this.userDao.getUnassignedAreaMapList(userId);
			userAreaList = this.userDao.getAssignedAreaMapList(userId);
		}else{
			users.add(userDao.getUserById(userId));
			rootCount = 1;
			userAreaList = userDao.getUserManageAreaMapList(userId);
		}
		JSONArray ja = new JSONArray();
		for(PmsDept dept : depts){
			deptMap.put(dept.getDeptId(), dept);
			if(!deptMap.containsKey(dept.getDeptParentId()))rootCount++;
			ja.add(getDeptJson(dept));
		}
		for(PmsUser user : users){			
			if("admin".equals(user.getUserName()))
				continue;
			ja.add(getUserJson(user));
		}
		for(Map<String,Object> map : deptAreaList){
			JSONObject jo = new JSONObject();
			jo.put("id", "a-"+map.get("ID"));
			jo.put("pId", "d-"+map.get("PARENT"));
			jo.put("name", map.get("VALUE"));
			jo.put("flag","A");
			jo.put("icon", "../core/imgs/icon/area.gif");
			ja.add(jo);
		}
		for(Map<String,Object> map : userAreaList){
			JSONObject jo = new JSONObject();
			jo.put("id", "a-"+map.get("ID"));
			jo.put("pId", "u-"+map.get("PARENT"));
			jo.put("name", map.get("VALUE"));
			jo.put("flag","A");
			jo.put("icon", "../core/imgs/icon/area.gif");
			ja.add(jo);
		}
		if(rootCount==1){
			((JSONObject)ja.get(0)).put("open", true);
		}
		return ja.toString();
	}

    public String getDeptByUserId(Integer userId) {
        List<PmsDept> depts = this.deptDao.getDeptsByUserId(userId);
        Map<Integer,PmsDept> deptMap = new HashMap<Integer,PmsDept>();
        JSONArray ja = new JSONArray();
        int rootCount = 0;			//根节点数量
        for(int i=0;i<depts.size();i++){
            PmsDept dept = depts.get(i);
            deptMap.put(dept.getDeptId(), dept);
            if(!deptMap.containsKey(dept.getDeptParentId()))rootCount++;

            JSONObject jo = new JSONObject();
            jo.put("id", dept.getDeptId());
            jo.put("pId", dept.getDeptParentId());
            jo.put("name", dept.getDeptName());
            jo.put("icon", "../core/imgs/icon/agency.gif");
            ja.add(jo);
        }

        if(rootCount==1){
            ((JSONObject)ja.get(0)).put("open", true);
        }
        return ja.toString();
    }
}
