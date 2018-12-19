package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeTemplateFieldExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeTemplateField;

/**
 * 评分模型积分项模板字段表业务访问接口
 */
public interface ITemplateFieldService {

	/**
	 * 新增评分模型积分项模板字段表
	 * @param templateField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTemplateField(ModeTemplateField templateField, Integer loginUserId);

	/**
	 *修改评分模型积分项模板字段表
	 * @param templateField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTemplateField(ModeTemplateField templateField, Integer loginUserId);

	/**
	 * 通过主键删除评分模型积分项模板字段表
	 */
	void deleteTemplateFieldById(Integer fieldId);

	List<ModeTemplateField> queryTemplateFieldNames();

	/**
	 * 通过主键得到评分模型积分项模板字段表
	 */
	ModeTemplateField getTemplateFieldById(Integer fieldId);

	/**
	 * 查询评分模型积分项模板字段表
	 * @param condition 查询条件
	 * @return
	 */
	List<ModeTemplateField> queryTemplateFieldList(Map<String, Object> condition);

	/**
	 * 分页查询评分模型积分项模板字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ModeTemplateField> queryTemplateFieldList(Map<String, Object> condition, IPageSize page);

    void saveOrUpdate(Integer templateId, String templateName, String[] fieldIds, String[] isActiveds, String[] fieldNames, String[] fieldColumnDisplays, Integer loginUserId);

    IPageList<ModeTemplateField> queryModelTemplateInfoColunm(Map<String, Object> condition, IPageSize page);

	List<ModeTemplateFieldExtend> queryTemplateFieldAndTypeList(Map<String, Object> condition);
}
