package banger.dao.intf;

import banger.domain.system.SysMobileInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IMobileInfoDao {

	/**
	 * 新增
	 * @param mobileInfo 实体对像
	 */
	void insertMobileInfo(SysMobileInfo mobileInfo);

	/**
	 *修改
	 * @param mobileInfo 实体对像
	 */
	void updateMobileInfo(SysMobileInfo mobileInfo);

	/**
	 * 通过主键删除
	 * @param serialNo 主键Id
	 */
	void deleteMobileInfoById(String serialNo);

	/**
	 * 通过主键得到
	 * @param serialNo 主键Id
	 */
	SysMobileInfo getMobileInfoById(String serialNo);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<SysMobileInfo> queryMobileInfoList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysMobileInfo> queryMobileInfoList(Map<String, Object> condition, IPageSize page);

}
