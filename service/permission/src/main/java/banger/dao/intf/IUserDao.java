package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUserArea;
import banger.domain.permission.PmsUser_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

public interface IUserDao {

	/**
	 * 通过账号获取用户实体
	 * @param account
	 * @return
	 */
	PmsUser queryUserByAccount(String account);
	
	/**
	 * 查询机构用户,不包含子机构
	 * @param condition
	 * @return
	 */
	IPageList<PmsUser_Query> queryDeptUserList(IPageSize page,Map<String,Object> condition);
	
	/**
	 * 通过用户主键得到机构名称
	 * @param userId
	 * @return
	 */
	String getDeptNameByUserId(Integer userId);
	
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(PmsUser user);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(PmsUser user);
	
	/**
	 * 通过主键删除用户
	 * @param userId
	 */
	void deleteUserById(Integer userId);
	
	/**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	PmsUser_Query getUserById(Integer userId);
	
	/**
	 * y用户ID查询管理片区集合
	 * @param userId
	 * @return
	 */
	List<PmsUserArea> getUserAreaList(Integer userId);
	
	/**
	 * 删除用户关联的管理区
	 */
	void deleleteAreaUserById(Integer pdaId);
	
	/**
	 * 新增关联服务区
	 * @param pmsUserArea
	 */
	void insertUserArea(PmsUserArea pmsUserArea);
	/**
	 * 通过部门Id查询用户关联的管理区
	 * @param deptId
	 * @return
	 */
	List<PmsUserArea> getUserAreaListById(Integer deptId);
	/**
	 * 根据userDeptId查询未删除用户
	 * @param userUeptId
	 * @return
	 */
	public int getUserDeptIdcount(Integer userUeptId);

	/**
	 * 检查用户编号是否存在
	 * @param user
	 * @return
	 */
	boolean checkUserCodeIsExist(PmsUser user);

	/**
	 * 检查用户账号是否存在
	 * @param user
	 * @return
	 */
	boolean checkUserAccountIdExist(PmsUser user);
	
	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @return
	 */
	List<PmsUser> getManageUsersByUserId(Integer userId);
	
	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @return
	 */
	List<PmsUser> getManageUsersByUserId(Integer userId,Integer roleId);

	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @return
	 */
	List<PmsUser> getManageUsersByUserId(Integer userId,Boolean noManagerLimit, Boolean isBranch);

	/**
	 * 根据用户id获取已分配的服务片区
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> getAssignedAreaMapList(Integer userId);

	/**
	 * 根据用户id获取未分配的服务片区
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> getUnassignedAreaMapList(Integer userId);

	/**
	 * 获取用户自己名下的服务片区
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> getUserManageAreaMapList(Integer userId);

    PmsUser getPmsUserByAccount(Map<String, Object> params);

    /**
	 * 根据主键得到用户
	 * @param userId
	 * @return
	 */
	PmsUser_Query getUserInfoById(Integer userId);

    /**
     *根据所属机构获取柜员信息
     * @param params
     * @return
     */
    List<PmsUser> getUserInfoList(Map<String, Object> params);

    void updateIsActivedTag(Integer id, Integer tag);
    
    /**
     * 得到可以的用户数
     * @return
     */
    Integer getEnableUserCount(Integer userId);

	Integer getOnlineStateCount(Map<String, Object> condition);

	/**
	 * 得到用户名
	 * @param userIds
	 * @return
	 */
	String[] getUserNamesByIds(String[] userIds);

    List<PmsUser> queryUserByGroupPermit(String userGroupPermit);

    Integer queryTotolUserNum();

	/**
	 * 获取用户的管理团队
	 */
	String getGroupPermitByUserId(Integer userId);

	/**
	 * 根据用户id查询团队权限
	 * @param userId
	 * @return
	 */
	PmsUser queryUserGroupPermitByUserId(String userId);

	/**
	 * 根据角色查询用户
	 * @param roleId
	 * @return
	 */
	public List<PmsUser> queryUserListByRoleId(String roleId,String teamGroupId);

	Integer getUserIdsByTeamIds(Map<String, Object> params);

}
