package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeScoreCustomField;
import banger.domain.config.ModeTemplateFieldExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.ModeTemplateField;

/**
 * 评分模型积分项模板字段表数据访问接口
 */
public interface ITemplateFieldDao {

	/**
	 * 新增评分模型积分项模板字段表
	 * @param templateField 实体对像
	 */
	void insertTemplateField(ModeTemplateField templateField);

	/**
	 *修改评分模型积分项模板字段表
	 * @param templateField 实体对像
	 */
	void updateTemplateField(ModeTemplateField templateField);

	/**
	 * 通过主键删除评分模型积分项模板字段表
	 * @param fieldId 主键Id
	 */
	void deleteTemplateFieldById(Integer fieldId);

	/**
	 * 查找评分项所有的值
	 */
	List<ModeTemplateField> queryTemplateFieldNames();

	/**
	 * 通过主键得到评分模型积分项模板字段表
	 * @param fieldId 主键Id
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

    IPageList<ModeTemplateField> queryModelTemplateInfoColunm(Map<String, Object> condition, IPageSize page);

	List<ModeTemplateFieldExtend> queryTemplateFieldAndTypeList(Map<String, Object> condition);

	/**
	 * @Author: yangdw
	 * @param modeId 模板id
	 * @Description:
	 * @Date: 10:20 2017/9/14
	 */
	List<ModeScoreCustomField> getLoanModelScoreByModeId(Integer modeId);
}
