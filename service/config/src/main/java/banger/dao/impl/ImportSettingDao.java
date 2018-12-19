package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoImportSettingItem;
import banger.domain.config.AutoTableColumn;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IImportSettingDao;
import banger.domain.config.AutoImportSetting;

/**
 * 自动导入配置信息表数据访问类
 */
@Repository
public class ImportSettingDao extends PageSizeDao<AutoImportSetting> implements IImportSettingDao {

	/**
	 * 新增自动导入配置信息表
	 * @param importSetting 实体对像
	 */
	public void insertImportSetting(AutoImportSetting importSetting){
		importSetting.setSettingId(this.newId().intValue());
		this.execute("insertImportSetting",importSetting);
	}

	/**
	 *修改自动导入配置信息表
	 * @param importSetting 实体对像
	 */
	public void updateImportSetting(AutoImportSetting importSetting){
		this.execute("updateImportSetting",importSetting);
	}

	/**
	 * 通过主键删除自动导入配置信息表
	 * @param settingId 主键Id
	 */
	public void deleteImportSettingById(Integer settingId){
		this.execute("deleteImportSettingById",settingId);
	}

	/**
	 * 通过主键得到自动导入配置信息表
	 * @param settingId 主键Id
	 */
	public AutoImportSetting getImportSettingById(Integer settingId){
		return (AutoImportSetting)this.queryEntity("getImportSettingById",settingId);
	}

	/**
	 * 查询自动导入配置信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoImportSetting> queryImportSettingList(Map<String,Object> condition){
		return (List<AutoImportSetting>)this.queryEntities("queryImportSettingList", condition);
	}

	/**
	 * 分页查询自动导入配置信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoImportSetting> queryImportSettingList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoImportSetting>)this.queryEntities("queryImportSettingList", page, condition);
	}
}
