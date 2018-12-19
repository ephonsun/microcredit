package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IImportSettingItemDao;
import banger.domain.config.AutoImportSettingItem;

/**
 * 自动导入配置项表数据访问类
 */
@Repository
public class ImportSettingItemDao extends PageSizeDao<AutoImportSettingItem> implements IImportSettingItemDao {

	/**
	 * 新增自动导入配置项表
	 * @param importSettingItem 实体对像
	 */
	public void insertImportSettingItem(AutoImportSettingItem importSettingItem){
		importSettingItem.setItemId(this.newId().intValue());
		this.execute("insertImportSettingItem",importSettingItem);
	}

	/**
	 *修改自动导入配置项表
	 * @param importSettingItem 实体对像
	 */
	public void updateImportSettingItem(AutoImportSettingItem importSettingItem){
		this.execute("updateImportSettingItem",importSettingItem);
	}

	/**
	 * 通过主键删除自动导入配置项表
	 * @param itemId 主键Id
	 */
	public void deleteImportSettingItemById(Integer itemId){
		this.execute("deleteImportSettingItemById",itemId);
	}

	/**
	 * 通过主键得到自动导入配置项表
	 * @param itemId 主键Id
	 */
	public AutoImportSettingItem getImportSettingItemById(Integer itemId){
		return (AutoImportSettingItem)this.queryEntity("getImportSettingItemById",itemId);
	}

	/**
	 * 查询自动导入配置项表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoImportSettingItem> queryImportSettingItemList(Map<String,Object> condition){
		return (List<AutoImportSettingItem>)this.queryEntities("queryImportSettingItemList", condition);
	}

	/**
	 * 分页查询自动导入配置项表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoImportSettingItem> queryImportSettingItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoImportSettingItem>)this.queryEntities("queryImportSettingItemList", page, condition);
	}

}
