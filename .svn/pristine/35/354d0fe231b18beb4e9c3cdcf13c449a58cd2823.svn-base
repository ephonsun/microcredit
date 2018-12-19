package banger.service.impl;

import banger.dao.intf.ISysTeamGroupDao;
import banger.dao.intf.IUserDao;
import banger.dao.intf.IUserRoleDao;
import banger.dao.intf.IUserRoleDeptDao;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.permission.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.ArrayUtil;
import banger.framework.util.DateUtil;
import banger.framework.util.Md5Encrypt;
import banger.framework.util.StringUtil;
import banger.service.intf.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static banger.domain.enumerate.GroupRolesEnum.getGroupRoleList;

@Service
public class UserService implements IUserService{

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IUserRoleDao userRoleDao;

	@Autowired
    private IUserRoleDeptDao userRoleDeptDao;

	@Autowired
	private ISysTeamGroupDao sysTeamGroupDao;


	private final int passwordExpiredDays = 90;		//密码过期天数
	private static final String DEFAULT_PASSWORD="111111";
	
	/**
	 * 新增用户
	 */
	public void addUser(PmsUser user,Integer loginUserId){
		String password = Md5Encrypt.encrypt(DEFAULT_PASSWORD);
		user.setUserCreateUser(loginUserId);
		user.setUserUpdateUser(loginUserId);
		user.setUserCreateDate(Calendar.getInstance().getTime());
		user.setUserUpdateDate(Calendar.getInstance().getTime());
		user.setUserPassword(password);
		user.setUserPasswordDate(DateUtil.addDay(Calendar.getInstance().getTime(), passwordExpiredDays));
		this.userDao.insertUser(user);
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @param loginUserId
	 */
	public void editUser(PmsUser user,Integer loginUserId){
		boolean hasChangeDept = false;


		PmsUser_Query dbUser = userDao.getUserById(user.getUserId());
		if (!dbUser.getUserDeptId().equals(user.getUserDeptId())) {
			hasChangeDept = true;
		}

		user.setUserUpdateUser(loginUserId);
		user.setUserUpdateDate(Calendar.getInstance().getTime());
		this.userDao.updateUser(user);

		//如果修改用户的时候归属有变更，则同时更新record_info里面的RECORD_CREATE_DEPT
//		if (hasChangeDept) {
//			Map<String,Object> params = new HashMap<String, Object>();
//			params.put("newDeptId",user.getUserDeptId());
//			params.put("recordCreateId",user.getUserId());
//			videoRecordModuleImpl.updateChangeDept(params);
//		}
	}
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public void deleteUserById(Integer userId){
		//通过主键删除用户
		this.userDao.deleteUserById(userId);
		//通过用户主键,删除用户角色
		this.userRoleDao.deleteRoleByUserId(userId);
        //通过主键删除角色下的机构
        this.userRoleDeptDao.deleteRoleDeptByUserId(userId);
		
	}
	
	/**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	public PmsUser_Query getUserById(Integer userId){
		return this.userDao.getUserById(userId);
	}
	
	/**
	 * 修改用户角色
	 * @param userId
	 * @param roleIds
	 * @param loginUserId
	 */
	public void updateUserRole(Integer userId,String roleIds,Integer loginUserId){
		List<PmsUserRoles> userRoles = this.userRoleDao.getUserRolesByUserId(userId);
		String[] ids = roleIds.split("\\,");
		Set<String> roleSet = ArrayUtil.asSet(ids);
		boolean isDelMem=true;
		for(PmsUserRoles userRole : userRoles){
			if(!roleSet.contains(userRole.getPurRoleId().toString())){
				this.userRoleDao.deleteUserRoleById(userRole.getPurUserRolesId());
			}else{
				roleSet.remove(userRole.getPurRoleId().toString());
			}
		}
		for(String roleId : roleSet){
			PmsUserRoles userRole = new PmsUserRoles();
			userRole.setPurUserId(userId);
			userRole.setPurRoleId(Integer.parseInt(roleId));
			userRole.setPurCreateUser(loginUserId);
			userRole.setPurUpdateUser(loginUserId);
			userRole.setPurCreateDate(Calendar.getInstance().getTime());
			userRole.setPurUpdateDate(Calendar.getInstance().getTime());
			this.userRoleDao.insertUserRole(userRole);

		}

		for(String roleId : ids){
			if(Integer.valueOf(roleId) == 3 || Integer.valueOf(roleId) == 4  || Integer.valueOf(roleId) ==5){
				isDelMem=false;
			}
		}

		if(isDelMem) {
			this.sysTeamGroupDao.deleteMemberByUserId(userId);
		}

	}
    /**
     * 修改用户角色机构
     * @param userId
     * @param roleIds
     * @param loginUserId
     *//*
    @Override
    public void updateDeptRole(Integer userId, String roleIds, Integer loginUserId) {
        List<PmsUserRoles> userRoles = this.userRoleDao.getUserRolesByUserId(userId);
        Set<String> roleSet = ArrayUtil.asSet(roleIds.split(","));
        for(PmsUserRoleDept userRoleDept : userRoles){
            if(!roleSet.contains(userRoleDept.getPurRoleId().toString())){
                this.userRoleDao.deleteUserRoleById(userRoleDept.getPurUserRolesId());
            }else{
                roleSet.remove(userRoleDept.getPurRoleId().toString());
            }
        }
        for(String roleId : roleSet){
            PmsUserRoles userRole = new PmsUserRoles();
            userRole.setPurUserId(userId);
            userRole.setPurRoleId(Integer.parseInt(roleId));
            userRole.setPurCreateUser(loginUserId);
            userRole.setPurUpdateUser(loginUserId);
            userRole.setPurCreateDate(Calendar.getInstance().getTime());
            userRole.setPurUpdateDate(Calendar.getInstance().getTime());
            this.userRoleDao.insertUserRole(userRole);
        }
    }*/

    /**
	 * 查询机构用户
	 * @param
	 * @return
	 */
	public IPageList<PmsUser_Query> queryDeptUserList(IPageSize page,Integer deptId,String account,String userName,Integer userState,boolean containSub){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(deptId!=null)condition.put("deptId", deptId);
		if(!StringUtil.isNullOrEmpty(account))condition.put("account", account);
		if(!StringUtil.isNullOrEmpty(userName))condition.put("userName", userName);
        if(userState!=null)condition.put("userStatus", userState);
		condition.put("containSub", containSub?"true":"false");
		return userDao.queryDeptUserList(page, condition);
	}
	
	/**
	 * 修改用户管理区
	 */
	public void updateUserItem(String itemIds,Integer userId){
		List<PmsUserArea> userAreaList = userDao.getUserAreaList(userId);
		Set<String> areaSet = ArrayUtil.asSet(itemIds.split(","));
		for(PmsUserArea userArea : userAreaList){
			if(!areaSet.contains(userArea.getAreaId().toString())){
				this.userDao.deleleteAreaUserById(userArea.getPdaId());
			}else{
				areaSet.remove(userArea.getAreaId().toString());
			}
		}
		for(String AreaId : areaSet){
			if(!StringUtil.isNullOrEmpty(AreaId)){
				PmsUserArea pmsUserArea = new PmsUserArea();
				pmsUserArea.setAreaId(Integer.valueOf(AreaId));
				pmsUserArea.setAreaUserId(userId);
				this.userDao.insertUserArea(pmsUserArea);
			}
		}
	}
	
	/**
	 * 重置密码
	 * @param userId
	 * @param opUserId
	 * @see banger.service.intf.IUserService#resetPmsUserPassword(java.lang.Integer, java.lang.Integer)
	 */
	public void resetPmsUserPassword(Integer userId, Integer opUserId) {
		PmsUser user = new PmsUser();
		user.setUserId(userId);
		user.setUserPassword(Md5Encrypt.encrypt(DEFAULT_PASSWORD));
		user.setUserPasswordReset(0);
		user.setUserPasswordDate(null);
		user.setUserUpdateUser(opUserId);
		user.setUserUpdateDate(Calendar.getInstance().getTime());
		userDao.updateUser(user);
	}

	/**
	 * 检查用户编号是否存在
	 *
	 * @param user
	 * @return
	 */
	@Override
	public boolean checkUserCodeIsExist(PmsUser user) {
		return userDao.checkUserCodeIsExist(user);
	}

	/**
	 * 检查用户账号是否存在
	 *
	 * @param user
	 * @return
	 */
	@Override
	public boolean checkUserAccountIdExist(PmsUser user) {
		return userDao.checkUserAccountIdExist(user);
	}

	/**
	 * 修改密码
	 *
	 * @param pmsUser
	 */
	@Override
	public void changeUserPassword(PmsUser pmsUser) {
        pmsUser.setUserPasswordReset(1);
        pmsUser.setUserPasswordDate(Calendar.getInstance().getTime());
        pmsUser.setUserUpdateUser(pmsUser.getUserId());
        pmsUser.setUserUpdateDate(Calendar.getInstance().getTime());
		userDao.updateUser(pmsUser);
	}

	/**
	 * 通过userId获取可管理的人员列表
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<PmsUser> getManageUsersByUserId(Integer userId) {
		return (List<PmsUser>)userDao.getManageUsersByUserId(userId,true,false);
	}
	
	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	public List<PmsUser> getManageUsersByUserId(Integer userId,String roleIds) {
		/*
		List<PmsUser> users = new ArrayList<PmsUser>();
		Set<String> roleIdSet = new HashSet<String>();
		for(String id : roleIds.split(","))roleIdSet.add(id);
		if(roleIdSet.contains("1")){
			users = this.userDao.getManageUsersByUserId(userId);
		}else{
			if(roleIdSet.contains("3")){
				users = this.userDao.getManageUsersByUserId(userId,3);
			}
			else if(roleIdSet.contains("2")){
				users = this.userDao.getManageUsersByUserId(userId,2);
			}else if(roleIdSet.contains("4") || roleIdSet.contains("5")){
				users = this.userDao.getManageUsersByUserId(userId);
			}
		}
		*/
		List<PmsUser> users = this.userDao.getManageUsersByUserId(userId);
		return users;
	}

    /**
     * 根据用户账号或者用户编辑获取用户信息
     *
     * @param params
     * @return
     */
    public PmsUser getPmsUserByAccount(Map<String,Object> params){
        return userDao.getPmsUserByAccount(params);
    }

    /**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	public PmsUser_Query getUserInfoById(Integer userId) {
		return this.userDao.getUserInfoById(userId);
	}

	/**
	 * 检查密码等级
	 * @param userPassword
	 * @return
	 */
	public int checkPwdLevel(String userPassword) {
		String pwd = Md5Encrypt.encrypt(userPassword);
		boolean n = false, s = false, t = false;
		int score = 0;
		if (pwd.length() < 6){
	        score = 1;
	    }else{
	        for(int i = 0; i < pwd.length(); i++){
	            int asc = checkstr(pwd.charAt(i));
	            if(asc == 1 && n == false){ 
	                score += 1; n = true; 
	            }else if((asc == 2 || asc == 3) && s == false){ 
	                score += 1; s = true; 
	            }else if(asc == 4 && t == false){ 
	                score += 1; t = true; 
	            }
	        };
	    };
	    return score;
	}

    public int checkstr(char c){
		if(c >= 48 && c <= 57){ //数字 
	        return 1; 
	    }else if(c >= 65 && c <= 90){ //大写字母
	        return 2;
	    }else if(c >= 97 && c <= 122){ //小写字母
	        return 3;
	    }else{ //特殊字符  
	        return 4;
	    }
	}

    /**
     *根据所属机构获取柜员信息
     * @param params
     * @return
     */
    public List<PmsUser> getUserInfoList(Map<String, Object> params) {
        return this.userDao.getUserInfoList(params);
    }

    /**启用人员*/
    public void updateIsActivedTag(Integer id, Integer tag) {
        this.userDao.updateIsActivedTag(id,tag);
    }
    
    /**
     * 得到可以的用户数
     * @return
     */
    public Integer getEnableUserCount(Integer userId){
    	return this.userDao.getEnableUserCount(userId);
    }

	/**
	 * 通过账号获取用户实体
	 * @param account
	 * @return
	 */
	public PmsUser queryUserByAccount(String account){
		return userDao.queryUserByAccount(account);
	}

	public String getGroupSelectUser(String userName, String exUserIds, String groupId) {

		List<PmsUserRoles_Query> users = null;
		String roleIds = GroupRolesEnum.ALL_GROUP_ROLE.getRoleName();

		if(StringUtils.isNotBlank(groupId)){
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("groupId",groupId);
			condition.put("roleIds",roleIds);
			users = this.userRoleDao.getUserByGroupId(condition);
		}else{
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("userName",userName);
			condition.put("exUserIds",exUserIds);
			condition.put("roleIds",roleIds);
			users = this.userRoleDao.getUserByRoleId(condition);
		}


		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(users)){
			for(int i=0;i<users.size();i++){
				PmsUserRoles_Query user = users.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", user.getPurUserId());
				jo.put("pId", "R_"+user.getPurRoleId());
				jo.put("name", user.getUserName()+"("+user.getDeptName()+")");
				jo.put("icon", "../core/imgs/icon/agency.gif");
				ja.add(jo);
			}
		}

		List<GroupRolesEnum> rolesList = getGroupRoleList();
		if(CollectionUtils.isNotEmpty(rolesList)){
			for (int i = 0; i < rolesList.size(); i++) {
				GroupRolesEnum gre = rolesList.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", "R_"+gre.getRoleId());
				jo.put("pId", 0);
				jo.put("name", gre.getRoleName());
				jo.put("icon", "../core/imgs/icon/agency.gif");
				jo.put("open", true);
				ja.add(jo);
			}
		}

//		if(rootCount==1){
//			((JSONObject)ja.get(0)).put("open", true);
//		}
		return ja.toString();
	}

	public String getGroupSelectSubUser(String userName, String exUserIds, String groupId) {

		List<PmsUserRoles_Query> users = null;
		String roleIds = "15";

		if(StringUtils.isNotBlank(groupId)){
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("groupId",groupId);
			condition.put("roleIds",roleIds);
			users = this.userRoleDao.getUserByGroupId(condition);
		}else{
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("userName",userName);
			condition.put("exUserIds",exUserIds);
			condition.put("roleIds",roleIds);
			users = this.userRoleDao.getUserByRoleId(condition);
		}


		JSONArray ja = new JSONArray();
		if(CollectionUtils.isNotEmpty(users)){
			for(int i=0;i<users.size();i++){
				PmsUserRoles_Query user = users.get(i);
				JSONObject jo = new JSONObject();
				jo.put("id", user.getPurUserId());
				jo.put("pId", "R_"+user.getPurRoleId());
				jo.put("name", user.getUserName()+"("+user.getDeptName()+")");
				jo.put("icon", "../core/imgs/icon/agency.gif");
				ja.add(jo);
			}
		}

//		List<GroupRolesEnum> rolesList = getGroupRoleList();
//		if(CollectionUtils.isNotEmpty(rolesList)){
//			for (int i = 0; i < rolesList.size(); i++) {
//				GroupRolesEnum gre = rolesList.get(i);
//				JSONObject jo = new JSONObject();
//				jo.put("id", "R_"+gre.getRoleId());
//				jo.put("pId", 0);
//				jo.put("name", gre.getRoleName());
//				jo.put("icon", "../core/imgs/icon/agency.gif");
//				jo.put("open", true);
//				ja.add(jo);
//			}
//		}

//		if(rootCount==1){
//			((JSONObject)ja.get(0)).put("open", true);
//		}
		return ja.toString();
	}

	/**
	 * 根据组ID查询客户经理
	 * @param userGroupPermit
	 * @return
	 */
    public List<PmsUser> queryUserByGroupPermit(String userGroupPermit) {
    	return this.userDao.queryUserByGroupPermit(userGroupPermit);
    }

	/**
	 * 得到用户名
	 * @param userIds
	 * @return
	 */
	public String[] getUserNamesByIds(String[] userIds){
		return userDao.getUserNamesByIds(userIds);
	}

    @Override
    public Integer queryTotolUserNum() {
        return userDao.queryTotolUserNum();
    }

	/**
	 * 获取用户的管理团队
	 */
	public String getGroupPermitByUserId(Integer userId){
		return this.userDao.getGroupPermitByUserId(userId);
	}

	@Override
	public PmsUser queryUserGroupPermitByUserId(String userId) {
		return userDao.queryUserGroupPermitByUserId(userId);
	}
	/**
 	* 根据角色查询用户
 	*/
	@Override
	public List<PmsUser> queryUserListByRoleId(String roleId,String teamGroupId) {
		return userDao.queryUserListByRoleId(roleId,teamGroupId);
	}

	@Override
	public Integer getUserIdsByTeamIds(Map<String, Object> params) {
		return userDao.getUserIdsByTeamIds(params);
	}
}
