package banger.service.intf;

import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUser_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

public interface IUserService {
	
	/**
	 * 新增用户
	 * @param user
	 * @param loginUserId
	 */
	void addUser(PmsUser user,Integer loginUserId);
	
	/**
	 * 修改用户
	 * @param user
	 * @param loginUserId
	 */
	void editUser(PmsUser user,Integer loginUserId);
	
	/**
	 * 删除用户
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
	 * 修改用户角色
	 * @param userId
	 * @param roleIds
	 * @param loginUserId
	 */
	void updateUserRole(Integer userId,String roleIds,Integer loginUserId);
 //   void updateDeptRole(Integer userId,String roleIds,Integer loginUserId);
	/**
	 * 查询机构用户
	 * @param condition
	 * @return
	 */
	IPageList<PmsUser_Query> queryDeptUserList(IPageSize page,Integer deptId,String account,String userName,Integer userState,boolean containSub);
	
	/**
	 * 修改用户管理区
	 */
	void updateUserItem(String itemIds,Integer userId);
	
	/**
	 * 重置用户密码
	 * @param userId 用户userId
	 * @param opUserId 操作者userId
	 */
	void resetPmsUserPassword(Integer userId,Integer opUserId);


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
	 * 修改密码
	 * @param user
	 */
	void changeUserPassword(PmsUser user);
	
	/**
	 * 通过userId获取可管理的人员列表
	 *
	 * @param userId
	 * @return
	 */
	List<PmsUser> getManageUsersByUserId(Integer userId);
	
	/**
	 * 通过userId获取可管理的人员列表
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	List<PmsUser> getManageUsersByUserId(Integer userId,String roleIds);

    PmsUser getPmsUserByAccount(Map<String, Object> params);

    /**
	 * 根据主键得到用户信息
	 * @param userId
	 * @return
	 */
	PmsUser_Query getUserInfoById(Integer userId);

	/**
	 * 检查密码等级
	 * @param userPassword
	 * @return
	 */
	int checkPwdLevel(String userPassword);

    /**
     * 根据机构id获取本机构下的所有柜员
     * @param params
     * @return
     */
    List<PmsUser> getUserInfoList(Map<String, Object> params);

    void updateIsActivedTag(Integer i, Integer tag);
    
    /**
     * 得到可以的用户数
     * @return
     */
    Integer getEnableUserCount(Integer Integer);

	/**
	 * 通过账号获取用户实体
	 * @param account
	 * @return
	 */
	PmsUser queryUserByAccount(String account);

	String getGroupSelectUser(String userName, String exUserIds, String groupId);
	String getGroupSelectSubUser(String userName, String exUserIds, String groupId);

    List<PmsUser> queryUserByGroupPermit(String userGroupPermit);

	/**
	 * 得到用户名
	 * @param userIds
	 * @return
	 */
	String[] getUserNamesByIds(String[] userIds);

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
	 */
	public List<PmsUser> queryUserListByRoleId(String roleId,String teamGroupId);

	Integer getUserIdsByTeamIds(Map<String, Object> params);

}
