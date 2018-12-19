package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.permission.PmsSu;

/**
 * 配置平台数据访问接口
 */
public interface ISuDao {

	/**
	 * 新增配置平台
	 * @param su 实体对像
	 */
	void insertSu(PmsSu su);

	/**
	 *修改配置平台
	 * @param su 实体对像
	 */
	void updateSu(PmsSu su);

	/**
	 * 通过主键删除配置平台
	 * @param id 主键Id
	 */
	void deleteSuById(Integer id);

	/**
	 * 通过主键得到配置平台
	 * @param id 主键Id
	 */
	PmsSu getSuById(Integer id);

	/**
	 * 通过帐号得到对像
	 * @param account
	 * @return
	 */
	PmsSu getSuByAccount(String account);

	/**
	 * 查询配置平台
	 * @param condition 查询条件
	 * @return
	 */
	List<PmsSu> querySuList(Map<String, Object> condition);

	/**
	 * 分页查询配置平台
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<PmsSu> querySuList(Map<String, Object> condition, IPageSize page);

}
