package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysAppVersion;

/**
 * APP升级版本信息表数据访问接口
 */
public interface IAppVersionDao {

	/**
	 * 新增APP升级版本信息表
	 * @param appVersion 实体对像
	 */
	void insertAppVersion(SysAppVersion appVersion);

	/**
	 *修改APP升级版本信息表
	 * @param appVersion 实体对像
	 */
	void updateAppVersion(SysAppVersion appVersion);

	/**
	 * 通过主键删除APP升级版本信息表
	 * @param id 主键Id
	 */
	void deleteAppVersionById(Integer id);

	/**
	 * 通过主键得到APP升级版本信息表
	 * @param id 主键Id
	 */
	SysAppVersion getAppVersionById(Integer id);

	/**
	 * 查询APP升级版本信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysAppVersion> queryAppVersionList(Map<String, Object> condition);

	/**
	 * 分页查询APP升级版本信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysAppVersion> queryAppVersionList(Map<String, Object> condition, IPageSize page);

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:获取最新的版本
	 * @Date: 15:04 2017/9/30
	 */
	SysAppVersion getLastOneVersionUpd();
}
