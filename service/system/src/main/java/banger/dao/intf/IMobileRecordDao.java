package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysMobileRecord;

/**
 * 系统登录记录数据访问接口
 */
public interface IMobileRecordDao {

	/**
	 * 新增系统登录记录
	 * @param mobileRecord 实体对像
	 */
	void insertMobileRecord(SysMobileRecord mobileRecord);

	/**
	 *修改系统登录记录
	 * @param mobileRecord 实体对像
	 */
	void updateMobileRecord(SysMobileRecord mobileRecord);

	/**
	 * 通过主键删除系统登录记录
	 * @param id 主键Id
	 */
	void deleteMobileRecordById(Integer id);

	/**
	 * 通过主键得到系统登录记录
	 * @param id 主键Id
	 */
	SysMobileRecord getMobileRecordById(Integer id);

	/**
	 * 查询系统登录记录
	 * @param condition 查询条件
	 * @return
	 */
	List<SysMobileRecord> queryMobileRecordList(Map<String, Object> condition);

	/**
	 * 分页查询系统登录记录
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysMobileRecord> queryMobileRecordList(Map<String, Object> condition, IPageSize page);

}
