package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysMobileInfo;

/**
 * 业务访问接口
 */
public interface IMobileInfoService {

	/**
	 * 新增
	 * @param mobileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertMobileInfo(SysMobileInfo mobileInfo, Integer loginUserId);

	/**
	 *修改
	 * @param mobileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateMobileInfo(SysMobileInfo mobileInfo, Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param SERIAL_NO 主键Id
	 */
	void deleteMobileInfoById(String serialNo);

	/**
	 * 通过主键得到
	 * @param SERIAL_NO 主键Id
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
