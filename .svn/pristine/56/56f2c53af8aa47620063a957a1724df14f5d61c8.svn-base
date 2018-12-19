package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IImportSettingItemDao;
import banger.service.intf.IImportSettingItemService;
import banger.domain.config.AutoImportSettingItem;

/**
 * 自动导入配置项表业务访问类
 */
@Repository
public class ImportSettingItemService implements IImportSettingItemService {

	@Autowired
	private IImportSettingItemDao importSettingItemDao;

	/**
	 * 新增自动导入配置项表
	 * @param importSettingItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertImportSettingItem(AutoImportSettingItem importSettingItem,Integer loginUserId){
		this.importSettingItemDao.insertImportSettingItem(importSettingItem);
	}

	/**
	 *修改自动导入配置项表
	 * @param importSettingItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateImportSettingItem(AutoImportSettingItem importSettingItem,Integer loginUserId){
		this.importSettingItemDao.updateImportSettingItem(importSettingItem);
	}

	/**
	 * 通过主键删除自动导入配置项表
	 * @param ITEM_ID 主键Id
	 */
	public void deleteImportSettingItemById(Integer itemId){
		this.importSettingItemDao.deleteImportSettingItemById(itemId);
	}

	/**
	 * 通过主键得到自动导入配置项表
	 * @param ITEM_ID 主键Id
	 */
	public AutoImportSettingItem getImportSettingItemById(Integer itemId){
		return this.importSettingItemDao.getImportSettingItemById(itemId);
	}

	/**
	 * 查询自动导入配置项表
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoImportSettingItem> queryImportSettingItemList(Map<String,Object> condition){
		return this.importSettingItemDao.queryImportSettingItemList(condition);
	}

	/**
	 * 分页查询自动导入配置项表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoImportSettingItem> queryImportSettingItemList(Map<String,Object> condition,IPageSize page){
		return this.importSettingItemDao.queryImportSettingItemList(condition,page);
	}

}
