package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.SysFormSettings;

/**
 * 表单关联设置表数据访问接口
 */
public interface IFormSettingsDao {

	/**
	 * 新增表单关联设置表
	 * @param formSettings 实体对像
	 */
	void insertFormSettings(SysFormSettings formSettings);

	/**
	 *修改表单关联设置表
	 * @param formSettings 实体对像
	 */
	void updateFormSettings(SysFormSettings formSettings);

	/**
	 * 通过主键删除表单关联设置表
	 * @param id 主键Id
	 */
	void deleteFormSettingsById(Integer id);

	/**
	 * 通过主键得到表单关联设置表
	 * @param id 主键Id
	 */
	SysFormSettings getFormSettingsById(Integer id);

	/**
	 * 查询表单关联设置表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysFormSettings> queryFormSettingsList(Map<String, Object> condition);

	/**
	 * 分页查询表单关联设置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysFormSettings> queryFormSettingsList(Map<String, Object> condition, IPageSize page);

	/**
	 * 根据表名修改显示名称
	 * @param displayName
	 * @param tablename
	 */
	void updateDisplayNameByTableName(String displayName,String tablename);

}
