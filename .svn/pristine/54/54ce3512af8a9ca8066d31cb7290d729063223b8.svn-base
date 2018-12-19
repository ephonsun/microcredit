package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoTemplatesFieldQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTemplatesField;

/**
 * 进件管理模板字段表数据访问接口
 */
public interface ITemplatesFieldDao {

	/**
	 * 新增进件管理模板字段表
	 * @param templatesField 实体对像
	 */
	void insertTemplatesField(IntoTemplatesField templatesField);

	/**
	 *修改进件管理模板字段表
	 * @param templatesField 实体对像
	 */
	void updateTemplatesField(IntoTemplatesField templatesField);

	/**
	 * 通过主键删除进件管理模板字段表
	 * @param fieldId 主键Id
	 */
	void deleteTemplatesFieldById(Integer fieldId);

	/**
	 * 通过主键得到进件管理模板字段表
	 * @param fieldId 主键Id
	 */
	IntoTemplatesFieldQuery getTemplatesFieldById(Integer fieldId);

	/**
	 * 查询进件管理模板字段表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoTemplatesFieldQuery> queryTemplatesFieldList(Map<String, Object> condition);

	/**
	 * 分页查询进件管理模板字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoTemplatesField> queryTemplatesFieldList(Map<String, Object> condition, IPageSize page);

	IPageList<IntoTemplatesField> queryIntoTemplatesInfoColunm(Map<String, Object> condition, IPageSize page);

	List<IntoTemplatesField> queryIntoTemplatesFieldByConditionOrder(Map<String, Object> condition);
}
