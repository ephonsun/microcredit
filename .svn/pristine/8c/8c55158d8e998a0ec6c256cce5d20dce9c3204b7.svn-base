package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IFormSettingsDao;
import banger.domain.config.SysFormSettings;

/**
 * 表单关联设置表数据访问类
 */
@Repository
public class FormSettingsDao extends PageSizeDao<SysFormSettings> implements IFormSettingsDao {

	/**
	 * 新增表单关联设置表
	 * @param formSettings 实体对像
	 */
	public void insertFormSettings(SysFormSettings formSettings){
		formSettings.setId(this.newId().intValue());
		this.execute("insertFormSettings",formSettings);
	}

	/**
	 *修改表单关联设置表
	 * @param formSettings 实体对像
	 */
	public void updateFormSettings(SysFormSettings formSettings){
		this.execute("updateFormSettings",formSettings);
	}

	/**
	 * 通过主键删除表单关联设置表
	 * @param id 主键Id
	 */
	public void deleteFormSettingsById(Integer id){
		this.execute("deleteFormSettingsById",id);
	}

	/**
	 * 通过主键得到表单关联设置表
	 * @param id 主键Id
	 */
	public SysFormSettings getFormSettingsById(Integer id){
		return (SysFormSettings)this.queryEntity("getFormSettingsById",id);
	}

	/**
	 * 查询表单关联设置表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFormSettings> queryFormSettingsList(Map<String,Object> condition){
		return (List<SysFormSettings>)this.queryEntities("queryFormSettingsList", condition);
	}

	/**
	 * 分页查询表单关联设置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysFormSettings> queryFormSettingsList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysFormSettings>)this.queryEntities("queryFormSettingsList", page, condition);
	}

	/**
	 * 根据表名修改显示名称
	 * @param displayName
	 * @param tablename
	 */
	@Override
	public void updateDisplayNameByTableName(String displayName, String tablename) {
		this.execute("updateDisplayNameByTableName",displayName,tablename);
	}

}
