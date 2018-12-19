package banger.dao.intf;

import banger.domain.permission.PmsUserPassExpiration;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 密码过期时间表数据访问接口
 */
public interface IUserPassExpirationDao {

	/**
	 * 新增密码过期时间表
	 * @param userPassExpiration 实体对像
	 */
	void insertUserPassExpiration(PmsUserPassExpiration userPassExpiration);

	/**
	 *修改密码过期时间表
	 * @param userPassExpiration 实体对像
	 */
	void updateUserPassExpiration(PmsUserPassExpiration userPassExpiration);

	/**
	 * 通过主键删除密码过期时间表
	 * @param expirationId 主键Id
	 */
	void deleteUserPassExpirationById(Integer expirationId);

	/**
	 * 通过主键得到密码过期时间表
	 * @param expirationId 主键Id
	 */
	PmsUserPassExpiration getUserPassExpirationById(Integer expirationId);


	/**
	 * 查询密码过期时间表
	 * 无查询条件
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
