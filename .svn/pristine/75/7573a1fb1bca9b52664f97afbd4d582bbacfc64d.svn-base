package banger.moduleIntf;

import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.SysFormSettings;
import banger.domain.loan.LoanApplyTemplate;
import banger.domain.loan.LoanTemplateExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 表单关联设置表业务访问接口
 */
public interface IFormSettingsProvider {

	/**
	 * 新增表单关联设置表
	 * @param formSettings 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertFormSettings(SysFormSettings formSettings, Integer loginUserId);

	/**
	 *修改表单关联设置表
	 * @param formSettings 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateFormSettings(SysFormSettings formSettings, Integer loginUserId);

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
	 * @Author: yangdw
	 * @param templateList 模板list
	 * @Description:处理模板的显示隐藏联动
	 * @Date: 9:25 2017/12/7
	 */
	void setTemplateShowOrHide(List<? extends AutoBaseTemplate> templateList,boolean isApp);
	/**
	 * @Author: yangdw
	 * @param templateList 模板list
	 * @Description:处理模板的显示隐藏联动
	 * @Date: 9:25 2017/12/7
	 */
	void setTemplateShowOrHide(List<? extends AutoBaseTemplate> templateList);

	/**
	 * @param templateList 模板list,app提交申请校验值
	 * @Author: yangdw
	 * @Description:处理模板的显示隐藏联动
	 * @Date: 9:25 2017/12/7
	 */
	void setSubmitTemplateShowOrHide(List<LoanTemplateExtend> templateList);

	/**
	 * 根据表名修改显示名称
	 * @param displayName
	 * @param tablename
	 */
	void updateDisplayNameByTableName(String displayName,String tablename);
}
