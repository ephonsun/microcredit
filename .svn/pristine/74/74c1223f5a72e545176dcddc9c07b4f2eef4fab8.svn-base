package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoImportSettingItem;
import banger.domain.config.AutoTableColumn;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IImportSettingDao;
import banger.service.intf.IImportSettingService;
import banger.domain.config.AutoImportSetting;

/**
 * 自动导入配置信息表业务访问类
 */
@Repository
public class ImportSettingService implements IImportSettingService {

	@Autowired
	private IImportSettingDao importSettingDao;

	/**
	 * 新增自动导入配置信息表
	 * @param importSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertImportSetting(AutoImportSetting importSetting,Integer loginUserId){
		this.importSettingDao.insertImportSetting(importSetting);
	}

	/**
	 *修改自动导入配置信息表
	 * @param importSetting 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateImportSetting(AutoImportSetting importSetting,Integer loginUserId){
		this.importSettingDao.updateImportSetting(importSetting);
	}

	/**
	 * 通过主键删除自动导入配置信息表
	 * @param SETTING_ID 主键Id
	 */
	public void deleteImportSettingById(Integer settingId){
		this.importSettingDao.deleteImportSettingById(settingId);
	}

	/**
	 * 通过主键得到自动导入配置信息表
	 * @param SETTING_ID 主键Id
	 */
	public AutoImportSetting getImportSettingById(Integer settingId){
		return this.importSettingDao.getImportSettingById(settingId);
	}

	/**
	 * 查询自动导入配置信息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoImportSetting> queryImportSettingList(Map<String,Object> condition){
		return this.importSettingDao.queryImportSettingList(condition);
	}

	/**
	 * 分页查询自动导入配置信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoImportSetting> queryImportSettingList(Map<String,Object> condition,IPageSize page){
		return this.importSettingDao.queryImportSettingList(condition,page);
	}
}
