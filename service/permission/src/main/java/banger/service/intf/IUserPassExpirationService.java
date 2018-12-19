package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PmsUserPassExpiration;

/**
 * 密码过期时间表业务访问接口
 */
public interface IUserPassExpirationService {

	/**
	 * 新增密码过期时间表
	 * @param userPassExpiration 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertUserPassExpiration(PmsUserPassExpiration userPassExpiration, Integer loginUserId);

	/**
	 *修改密码过期时间表
	 * @param userPassExpiration 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateUserPassExpiration(PmsUserPassExpiration userPassExpiration, Integer loginUserId);

	/**
	 * 通过主键删除密码过期时间表
	 * @param EXPIRATION_ID 主键Id
	 */
	void deleteUserPassExpirationById(Integer expirationId);

	/**
	 * 通过主键得到密码过期时间表
	 * @param EXPIRATION_ID 主键Id
	 */
	PmsUserPassExpiration getUserPassExpirationById(Integer expirationId);

	/**
	 * 直接查询密码过期时间表
	 * 没有参数
	 */
	PmsUserPassExpiration getUserPassExpiration();

	/**
	 * 查询密码过期时间表
	 * @param condition 查询条件
	 * @return
	 */
	List<PmsUserPassExpiration> queryUserPassExpirationList(Map<String, Object> condition);

	/**
	 * 分页查询密码过期时间表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<PmsUserPassExpiration> queryUserPassExpirationList(Map<String, Object> condition, IPageSize page);

}
