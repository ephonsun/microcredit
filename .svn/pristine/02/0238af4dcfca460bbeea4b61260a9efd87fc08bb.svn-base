package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysMobileRecord;

/**
 * 系统登录记录业务访问接口
 */
public interface IMobileRecordService {

	/**
	 * 新增系统登录记录
	 * @param mobileRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertMobileRecord(SysMobileRecord mobileRecord, Integer loginUserId);

	/**
	 *修改系统登录记录
	 * @param mobileRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateMobileRecord(SysMobileRecord mobileRecord, Integer loginUserId);

	/**
	 * 通过主键删除系统登录记录
	 * @param ID 主键Id
	 */
	void deleteMobileRecordById(Integer id);

	/**
	 * 通过主键得到系统登录记录
	 * @param ID 主键Id
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
