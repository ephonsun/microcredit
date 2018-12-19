package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.IntoTemplatesFieldQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.IntoTemplatesField;

/**
 * 进件管理模板字段表业务访问接口
 */
public interface ITemplatesFieldService  {

	/**
	 * 新增进件管理模板字段表
	 * @param templatesField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTemplatesField(IntoTemplatesField templatesField, Integer loginUserId);

	/**
	 *修改进件管理模板字段表
	 * @param templatesField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTemplatesField(IntoTemplatesField templatesField, Integer loginUserId);

	/**
	 * 通过主键删除进件管理模板字段表
	 * @param FIELD_ID 主键Id
	 */
	void deleteTemplatesFieldById(Integer fieldId);

	/**
	 * 通过主键得到进件管理模板字段表
	 * @param FIELD_ID 主键Id
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

	void saveOrUpdate(Integer templateId, String templateName, String[] fieldIds, String[] isActiveds, String[] fieldNames, String[] fieldColumnDisplays,String[] isMusts,Integer loginUserId);

	void moveIntoTemplatesFieldOrderNo(Integer templateId, Integer fieldId, String type);
}
