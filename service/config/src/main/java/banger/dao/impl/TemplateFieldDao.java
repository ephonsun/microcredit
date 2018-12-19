package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.config.ModeScoreCustomField;
import banger.domain.config.ModeTemplateFieldExtend;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITemplateFieldDao;
import banger.domain.config.ModeTemplateField;

/**
 * 评分模型积分项模板字段表数据访问类
 */
@Repository
public class TemplateFieldDao extends PageSizeDao<ModeTemplateField> implements ITemplateFieldDao {

	/**
	 * 新增评分模型积分项模板字段表
	 * @param templateField 实体对像
	 */
	public void insertTemplateField(ModeTemplateField templateField){
		if (templateField.getFieldId() == null || templateField.getFieldId().intValue() < 1) {
			templateField.setFieldId(this.newId().intValue());
		}
		this.execute("insertTemplateField",templateField);
	}

	/**
	 *修改评分模型积分项模板字段表
	 * @param templateField 实体对像
	 */
	public void updateTemplateField(ModeTemplateField templateField){
		this.execute("updateTemplateField",templateField);
	}

	/**
	 * 查找评分项所有的值
	 */
	public List<ModeTemplateField> queryTemplateFieldNames(){
		return  (List<ModeTemplateField>)this.queryEntities("queryTemplateFieldNames");
	}


	/**
	 * 通过主键删除评分模型积分项模板字段表
	 * @param fieldId 主键Id
	 */
	public void deleteTemplateFieldById(Integer fieldId){
		this.execute("deleteTemplateFieldById",fieldId);
	}


	/**
	 * 通过主键得到评分模型积分项模板字段表
	 * @param fieldId 主键Id
	 */
	public ModeTemplateField getTemplateFieldById(Integer fieldId){
		return (ModeTemplateField)this.queryEntity("getTemplateFieldById",fieldId);
	}

	/**
	 * 查询评分模型积分项模板字段表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ModeTemplateField> queryTemplateFieldList(Map<String,Object> condition){
		return (List<ModeTemplateField>)this.queryEntities("queryTemplateFieldList", condition);
	}

	/**
	 * 分页查询评分模型积分项模板字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ModeTemplateField> queryTemplateFieldList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ModeTemplateField>)this.queryEntities("queryTemplateFieldList", page, condition);
	}

    public IPageList<ModeTemplateField> queryModelTemplateInfoColunm(Map<String, Object> condition, IPageSize page) {
        return (IPageList<ModeTemplateField>) this.queryEntities("queryModelTemplateInfoColunm",page,condition);
    }

	public List<ModeTemplateFieldExtend> queryTemplateFieldAndTypeList(Map<String, Object> condition) {
		return (List<ModeTemplateFieldExtend>)this.queryEntities("queryTemplateFieldAndTypeList", condition);
	}

	public List<ModeScoreCustomField> getLoanModelScoreByModeId(Integer modeId) {
		return (List<ModeScoreCustomField>)this.queryEntities("getLoanModelScoreByModeId", modeId);
	}
}
