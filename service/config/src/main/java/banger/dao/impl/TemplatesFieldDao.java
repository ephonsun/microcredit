package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoTableColumn;
import banger.domain.config.ModeTemplateField;

import banger.domain.config.IntoTemplatesFieldQuery;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITemplatesFieldDao;
import banger.domain.config.IntoTemplatesField;

/**
 * 进件管理模板字段表数据访问类
 */
@Repository
public class TemplatesFieldDao extends PageSizeDao<IntoTemplatesField> implements ITemplatesFieldDao {

	/**
	 * 新增进件管理模板字段表
	 * @param templatesField 实体对像
	 */
	public void insertTemplatesField(IntoTemplatesField templatesField){
		templatesField.setFieldId(this.newId().intValue());
		this.execute("insertTemplatesField",templatesField);
	}

	/**
	 *修改进件管理模板字段表
	 * @param templatesField 实体对像
	 */
	public void updateTemplatesField(IntoTemplatesField templatesField){
		this.execute("updateTemplatesField",templatesField);
	}

	/**
	 * 通过主键删除进件管理模板字段表
	 * @param fieldId 主键Id
	 */
	public void deleteTemplatesFieldById(Integer fieldId){
		this.execute("deleteTemplatesFieldById",fieldId);
	}

	/**
	 * 通过主键得到进件管理模板字段表
	 * @param fieldId 主键Id
	 */
	public IntoTemplatesFieldQuery getTemplatesFieldById(Integer fieldId){
		return (IntoTemplatesFieldQuery)this.queryEntity("getTemplatesFieldById",fieldId);
	}

	/**
	 * 查询进件管理模板字段表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoTemplatesFieldQuery> queryTemplatesFieldList(Map<String,Object> condition){
		return (List<IntoTemplatesFieldQuery>)this.queryEntities("queryTemplatesFieldList", condition);
	}

	/**
	 * 分页查询进件管理模板字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoTemplatesField> queryTemplatesFieldList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoTemplatesField>)this.queryEntities("queryTemplatesFieldList", page, condition);
	}

	public IPageList<IntoTemplatesField> queryIntoTemplatesInfoColunm(Map<String, Object> condition, IPageSize page) {
		return (IPageList<IntoTemplatesField>) this.queryEntities("queryIntoTemplatesInfoColunm",page,condition);
	}

	/**
	 * @Author: yangdw
	 * @param condition
	 * @Description: 上移下移
	 * @Date: 11:32 2017/12/12
	 */

	public List<IntoTemplatesField> queryIntoTemplatesFieldByConditionOrder(Map<String, Object> condition) {
		return (List<IntoTemplatesField>) this.queryEntities("queryIntoTemplatesFieldByConditionOrder", condition);
	}
}
