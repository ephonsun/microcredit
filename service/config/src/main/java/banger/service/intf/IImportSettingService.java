package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoImportSettingItem;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoImportSetting;

/**
 * 自动导入配置信息表业务访问接口
 */
public interface IImportSettingService {

	/**
	 * 新增自动导入配置信息表
	 * @param importSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertImportSetting(AutoImportSetting importSetting, Integer loginUserId);

	/**
	 *修改自动导入配置信息表
	 * @param importSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateImportSetting(AutoImportSetting importSetting, Integer loginUserId);

	/**
	 * 通过主键删除自动导入配置信息表
	 * @param SETTING_ID 主键Id
	 */
	void deleteImportSettingById(Integer settingId);

	/**
	 * 通过主键得到自动导入配置信息表
	 * @param SETTING_ID 主键Id
	 */
	AutoImportSetting getImportSettingById(Integer settingId);

	/**
	 * 查询自动导入配置信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoImportSetting> queryImportSettingList(Map<String, Object> condition);

	/**
	 * 分页查询自动导入配置信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoImportSetting> queryImportSettingList(Map<String, Object> condition, IPageSize page);

}
