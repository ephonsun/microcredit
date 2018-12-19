package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IUserPassExpirationDao;
import banger.service.intf.IUserPassExpirationService;
import banger.domain.permission.PmsUserPassExpiration;

/**
 * 密码过期时间表业务访问类
 */
@Repository
public class UserPassExpirationService implements IUserPassExpirationService {

	@Autowired
	private IUserPassExpirationDao userPassExpirationDao;

	/**
	 * 新增密码过期时间表
	 * @param userPassExpiration 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertUserPassExpiration(PmsUserPassExpiration userPassExpiration,Integer loginUserId){
		userPassExpiration.setCreateUser(loginUserId);
		userPassExpiration.setCreateDate(DateUtil.getCurrentDate());
		userPassExpiration.setUpdateUser(loginUserId);
		userPassExpiration.setUpdateDate(DateUtil.getCurrentDate());
		this.userPassExpirationDao.insertUserPassExpiration(userPassExpiration);
	}

	/**
	 *修改密码过期时间表
	 * @param userPassExpiration 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateUserPassExpiration(PmsUserPassExpiration userPassExpiration,Integer loginUserId){
		userPassExpiration.setUpdateUser(loginUserId);
		userPassExpiration.setUpdateDate(DateUtil.getCurrentDate());
		this.userPassExpirationDao.updateUserPassExpiration(userPassExpiration);
	}

	/**
	 * 通过主键删除密码过期时间表
	 * @param EXPIRATION_ID 主键Id
	 */
	public void deleteUserPassExpirationById(Integer expirationId){
		this.userPassExpirationDao.deleteUserPassExpirationById(expirationId);
	}

	/**
	 * 通过主键得到密码过期时间表
	 * @param EXPIRATION_ID 主键Id
	 */
	public PmsUserPassExpiration getUserPassExpirationById(Integer expirationId){
		return this.userPassExpirationDao.getUserPassExpirationById(expirationId);
	}

	/**
	 * 直接查询密码过期时间表
	 * 没有参数
	 * @return
	 */
	public PmsUserPassExpiration getUserPassExpiration() {
		return this.userPassExpirationDao.getUserPassExpiration();
	}

	/**
	 * 查询密码过期时间表
	 * @param condition 查询条件
	 * @return
	 */
	public List<PmsUserPassExpiration> queryUserPassExpirationList(Map<String,Object> condition){
		return this.userPassExpirationDao.queryUserPassExpirationList(condition);
	}

	/**
	 * 分页查询密码过期时间表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<PmsUserPassExpiration> queryUserPassExpirationList(Map<String,Object> condition,IPageSize page){
		return this.userPassExpirationDao.queryUserPassExpirationList(condition,page);
	}

}
