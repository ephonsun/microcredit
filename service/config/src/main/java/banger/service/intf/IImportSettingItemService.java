package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoImportSettingItem;

/**
 * 自动导入配置项表业务访问接口
 */
public interface IImportSettingItemService {

	/**
	 * 新增自动导入配置项表
	 * @param importSettingItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertImportSettingItem(AutoImportSettingItem importSettingItem, Integer loginUserId);

	/**
	 *修改自动导入配置项表
	 * @param importSettingItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateImportSettingItem(AutoImportSettingItem importSettingItem, Integer loginUserId);

	/**
	 * 通过主键删除自动导入配置项表
	 * @param ITEM_ID 主键Id
	 */
	void deleteImportSettingItemById(Integer itemId);

	/**
	 * 通过主键得到自动导入配置项表
	 * @param ITEM_ID 主键Id
	 */
	AutoImportSettingItem getImportSettingItemById(Integer itemId);

	/**
	 * 查询自动导入配置项表
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoImportSettingItem> queryImportSettingItemList(Map<String, Object> condition);

	/**
	 * 分页查询自动导入配置项表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoImportSettingItem> queryImportSettingItemList(Map<String, Object> condition, IPageSize page);

}
