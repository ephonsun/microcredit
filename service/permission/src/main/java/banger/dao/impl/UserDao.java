package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IUserDao;
import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUserArea;
import banger.domain.permission.PmsUser_Query;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.TypeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends PageSizeDao<PmsUser> implements IUserDao {


	/**
	 * 通过账号获取用户实体
	 * @param account
	 * @return
	 */
	public PmsUser queryUserByAccount(String account) {
		return (PmsUser)this.queryEntity("queryUserByAccount",account);
	}
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insertUser(PmsUser user) {
		user.setUserId(this.newId().intValue());
		this.execute("insertUser", user);
	}
	
	/**
	 * 修改用户
	 * @param user
	 */
	public void updateUser(PmsUser user) {
		this.execute("updateUser", user);
	}
	
	/**
	 * 通过主键删除用户
	 * @param userId
	 */
	public void deleteUserById(Integer userId){
		this.execute("deleteUserById", userId);
	}

	/**
	 * 获取用户的管理团队
	 */
	public String getGroupPermitByUserId(Integer userId){
		Object val = this.queryValue("getGroupPermitByUserId", userId);
		if(val!=null){
			return (String)TypeUtil.changeType(val,String.class);
		}
		return "";
	}

	/**
	 * 查询机构用户,不包含子机构
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsUser_Query> queryDeptUserList(IPageSize page,Map<String,Object> condition){
		return (IPageList<PmsUser_Query>)this.queryEntities("queryDeptUserList", page, condition);
	}
	
	/**
	 * 通过用户主键得到机构名称
	 * @param userId
	 * @return
	 */
	public String getDeptNameByUserId(Integer userId){
		return (String)this.queryValue("getDeptNameByUserId", userId);
	}
	
	/**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	public PmsUser_Query getUserById(Integer userId){
		return (PmsUser_Query)this.queryEntity("getUserById", userId);
	}
	/**
	 * 用户ID查询管理片区集合
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsUserArea> getUserAreaList(Integer userId){
		return (List<PmsUserArea>)this.queryEntities("getUserAreaList", userId);
	}
	/**
	 * 删除关联的管理服务区
	 * @param pdaId
	 */
	public void deleleteAreaUserById(Integer pdaId){
		this.execute("deleleteAreaUserById", pdaId);
	}
	/**
	 * 新增关联服务区
	 * @param pmsUserArea
	 */
	public void insertUserArea(PmsUserArea pmsUserArea){
		pmsUserArea.setPdaId(this.newId().intValue());
		this.execute("insertUserArea", pmsUserArea);
	}
	/**
	 * 通过部门Id查询用户关联的管理区
	 * @param deptId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsUserArea> getUserAreaListById(Integer deptId){
		return (List<PmsUserArea>)this.queryEntities("getUserAreaListById", deptId);
	}
    
    /**
	 * 根据userDeptId查询未删除用户
	 * @param userUeptId
	 * @return
	 */
	public int getUserDeptIdcount(Integer userUeptId){
		return this.queryInt("getUserDeptIdcount", userUeptId);
	}

	/**
	 * 检查用户编号是否存在
	 *
	 * @param user
	 * @return
	 */
	@Override
	public boolean checkUserCodeIsExist(PmsUser user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId",user.getUserId());
		map.put("userCode",user.getUserCode());
		map.put("userCode2", user.getUserCode2());
		PmsUser u = (PmsUser)this.queryEntity("getUserForCheckRepeat",map);
		if(u == null){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 检查用户账号是否存在
	 *
	 * @param user
	 * @return
	 */
	@Override
	public boolean checkUserAccountIdExist(PmsUser user) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId",user.getUserId());
		map.put("userAccount",user.getUserAccount());
		PmsUser u = (PmsUser)this.queryEntity("getUserForCheckRepeat",map);
		if(u == null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getManageUsersByUserId(Integer userId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
		return (List<PmsUser>)this.queryEntities("getManageUsersByUserId",condition);
	}
	
	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @return
	 */
	public List<PmsUser> getManageUsersByUserId(Integer userId,Integer roleId) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId", userId);
		condition.put("roleId", roleId);
		return (List<PmsUser>)this.queryEntities("getDeptManageUsersByUserId",condition);
	}

	/**
	 * 通过userId获取可管理的人员列表
	 *
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PmsUser> getManageUsersByUserId(Integer userId,Boolean noManagerLimit, Boolean isBranch) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("userId", userId);
        if(!noManagerLimit){
            condition.put("managerLimit", 1);
        }
        if(isBranch){
            // 通过userId获取可管理的网点的用户
            return (List<PmsUser>)this.queryEntities("getManageBranchUsersByUserId",condition);
        } else {
            // 通过userId获取可管理的用户，包含分行和支行的用户
            return (List<PmsUser>)this.queryEntities("getManageUsersByUserId",condition);
        }
	}

	/**
	 * 根据用户id获取已分配的服务片区
	 * @param userId
	 * @return
	 */

	public List<Map<String,Object>> getAssignedAreaMapList(Integer userId){
		return this.queryMapList("getAssignedAreaMapList",userId);
	}
	/**
	 * 根据用户id获取未分配的服务片区
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getUnassignedAreaMapList(Integer userId){
		return this.queryMapList("getUnassignedAreaMapList",userId);
	}


	/**
	 * 获取用户自己名下的服务片区
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getUserManageAreaMapList(Integer userId){
		return this.queryMapList("getUserManageAreaMapList",userId);
	}

    /**
     * 根据用户账号或者用户编辑获取用户信息
     * 
     * @param params
     * @return
     */
    public PmsUser getPmsUserByAccount(Map<String,Object> params){
        return (PmsUser)this.queryEntity("getPmsUserByAccount",params);
    }

    /**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	public PmsUser_Query getUserInfoById(Integer userId) {
		return (PmsUser_Query)this.queryEntity("getUserInfoById", userId);
	}

    /**
     *根据所属机构获取柜员信息
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<PmsUser> getUserInfoList(Map<String, Object> params) {
        return (List<PmsUser>)this.queryEntities("getUserInfoList", params);
    }

     /**启用人员*/
    public void updateIsActivedTag(Integer id, Integer tag) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("userId",id);
        condition.put("userStatus", tag);
        this.execute("updateUserStatue",condition);
    }
    
    /**
     * 得到可以的用户数
     * @return
     */
    public Integer getEnableUserCount(Integer userId){
    	Integer count = this.queryInt("getEnableUserCount",new Object[]{userId});
    	return count;
    }

	/**
	 * 获得摄像头状态数量
	 * @param condition
	 * @return
	 */
	@Override
	public Integer getOnlineStateCount(Map<String, Object> condition) {
		return this.queryInt("getOnlineStateCount", condition);
	}

	/**
	 * 根据组ID查询客户经理
	 * @param userGroupPermit
	 * @return
	 */
    public List<PmsUser> queryUserByGroupPermit(String userGroupPermit) {
    	Map<String,Object> condition =new HashMap<String, Object>();
    	condition.put("userGroupPermit",userGroupPermit);
        return (List<PmsUser>) this.queryEntities("queryUserByGroupPermit",condition);
    }

    @Override
    public Integer queryTotolUserNum() {
        return this.queryInt("queryTotolUserNum");
    }

    /**
	 * 得到用户名
	 * @param userIds
	 * @return
	 */
	public String[] getUserNamesByIds(String[] userIds){
		List<Object> list =  (List<Object>)this.queryValueList("getUserNamesByIds",new Object[]{userIds});
		if(list!=null){
			return list.toArray(new String[0]);
		}
		return new String[0];
	}

	@Override
	public PmsUser queryUserGroupPermitByUserId(String userId) {
		Integer uid = Integer.parseInt(userId);
		return (PmsUser) this.queryEntity("queryUserGroupPermitByUserId",uid);
	}

	@Override
	public List<PmsUser> queryUserListByRoleId(String roleId,String teamGroupId) {
		return (List<PmsUser>) this.queryEntities("queryUserListByRoleId",new Object[]{Integer.parseInt(roleId),Integer.parseInt(teamGroupId)});
	}

	@Override
	public Integer getUserIdsByTeamIds(Map<String, Object> params) {
		return this.queryInt("getUserIdsByTeamIds",params);
	}
}