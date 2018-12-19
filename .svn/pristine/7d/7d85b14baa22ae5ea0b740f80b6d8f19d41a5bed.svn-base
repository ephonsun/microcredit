package banger.dao.impl;

import banger.dao.intf.IUserPassExpirationDao;
import banger.domain.permission.PmsUserPassExpiration;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 密码过期时间表数据访问类
 */
@Repository
public class UserPassExpirationDao extends PageSizeDao<PmsUserPassExpiration> implements IUserPassExpirationDao {

	/**
	 * 新增密码过期时间表
	 * @param userPassExpiration 实体对像
	 */
	public void insertUserPassExpiration(PmsUserPassExpiration userPassExpiration){
		userPassExpiration.setExpirationId(this.newId().intValue());
		this.execute("insertUserPassExpiration",userPassExpiration);
	}

	/**
	 *修改密码过期时间表
	 * @param userPassExpiration 实体对像
	 */
	public void updateUserPassExpiration(PmsUserPassExpiration userPassExpiration){
		this.execute("updateUserPassExpiration",userPassExpiration);
	}

	/**
	 * 通过主键删除密码过期时间表
	 * @param expirationId 主键Id
	 */
	public void deleteUserPassExpirationById(Integer expirationId){
		this.execute("deleteUserPassExpirationById",expirationId);
	}

	/**
	 * 通过主键得到密码过期时间表
	 * @param expirationId 主键Id
	 */
	public PmsUserPassExpiration getUserPassExpirationById(Integer expirationId){
		return (PmsUserPassExpiration)this.queryEntity("getUserPassExpirationById",expirationId);
	}

	/**
	 * 直接查找密码过期表
	 * @return
	 */
	public PmsUserPassExpiration getUserPassExpiration() {
		return (PmsUserPassExpiration)this.queryEntity("getUserPassExpiration");
	}

	/**
	 * 查询密码过期时间表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsUserPassExpiration> queryUserPassExpirationList(Map<String,Object> condition){
		return (List<PmsUserPassExpiration>)this.queryEntities("queryUserPassExpirationList", condition);
	}

	/**
	 * 分页查询密码过期时间表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsUserPassExpiration> queryUserPassExpirationList(Map<String,Object> condition,IPageSize page){
		return (IPageList<PmsUserPassExpiration>)this.queryEntities("queryUserPassExpirationList", page, condition);
	}

}
