package banger.service.impl;

import java.util.*;

import banger.dao.intf.ITableColumnDao;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.ModeScoreCustomField;
import banger.domain.config.ModeTemplateFieldExtend;
import banger.domain.loan.LoanFieldExtend;
import banger.moduleIntf.ITemplateFieldProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITemplateFieldDao;
import banger.service.intf.ITemplateFieldService;
import banger.domain.config.ModeTemplateField;

/**
 * 评分模型积分项模板字段表业务访问类
 */
@Repository
public class TemplateFieldService implements ITemplateFieldService,ITemplateFieldProvider {

	@Autowired
	private ITemplateFieldDao templateFieldDao;
	@Autowired
	private ITableColumnDao tableColumnDao;

	/**
	 * 新增评分模型积分项模板字段表
	 * @param templateField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTemplateField(ModeTemplateField templateField,Integer loginUserId){
		templateField.setCreateUser(loginUserId);
		templateField.setCreateDate(DateUtil.getCurrentDate());
		templateField.setUpdateUser(loginUserId);
		templateField.setUpdateDate(DateUtil.getCurrentDate());
		this.templateFieldDao.insertTemplateField(templateField);
	}

	/**
	 *修改评分模型积分项模板字段表
	 * @param templateField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTemplateField(ModeTemplateField templateField,Integer loginUserId){
		templateField.setUpdateUser(loginUserId);
		templateField.setUpdateDate(DateUtil.getCurrentDate());
		this.templateFieldDao.updateTemplateField(templateField);
	}

	/**
	 * 通过主键删除评分模型积分项模板字段表
	 */
	public void deleteTemplateFieldById(Integer fieldId){
		this.templateFieldDao.deleteTemplateFieldById(fieldId);
	}


	/**
	 * 查找评分项所有的值
	 */
	public List<ModeTemplateField> queryTemplateFieldNames(){
		return  templateFieldDao.queryTemplateFieldNames();
	}


	/**
	 * 通过主键得到评分模型积分项模板字段表
	 */
	public ModeTemplateField getTemplateFieldById(Integer fieldId){
		return this.templateFieldDao.getTemplateFieldById(fieldId);
	}

	/**
	 * 查询评分模型积分项模板字段表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ModeTemplateField> queryTemplateFieldList(Map<String,Object> condition){
		return this.templateFieldDao.queryTemplateFieldList(condition);
	}

	/**
	 * 分页查询评分模型积分项模板字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ModeTemplateField> queryTemplateFieldList(Map<String,Object> condition,IPageSize page){
		return this.templateFieldDao.queryTemplateFieldList(condition,page);
	}

    @Override
    public void saveOrUpdate(Integer templateId, String templateName, String[] fieldIds, String[] isActiveds, String[] fieldNames, String[] fieldColumnDisplays, Integer loginUserId) {
		//查询表中是否有相应数据
		Map<String,Object> condition =new HashMap<String,Object>();
		condition.put("templateId",templateId);
		List<ModeTemplateField> modeTemplateFields = templateFieldDao.queryTemplateFieldList(condition);

		Set<Integer> idSet = new HashSet<Integer>();
		for (ModeTemplateField field : modeTemplateFields)
			idSet.add(field.getFieldId());

		for (int i = 0; i < fieldIds.length; i++) {
			Integer fieldId = Integer.parseInt(fieldIds[i]);
			if (idSet.contains(fieldId)) {
				ModeTemplateField templateFieldById = templateFieldDao.getTemplateFieldById(Integer.valueOf(fieldIds[i]));
				//不填显示名称就不更新
				if(!" ".equals(fieldColumnDisplays[i])){
					templateFieldById.setFieldDisplay(fieldColumnDisplays[i]);
				}
				templateFieldById.setIsActived(Integer.valueOf(isActiveds[i]));

				templateFieldById.setUpdateUser(loginUserId);
				templateFieldById.setUpdateDate(DateUtil.getCurrentDate());

				this.templateFieldDao.updateTemplateField(templateFieldById);
			}else{
				AutoTableColumn tableColumnById = tableColumnDao.getTableColumnById(fieldId);
				if (tableColumnById == null) {
					//容错处理,删除垃圾数据
					templateFieldDao.deleteTemplateFieldById(fieldId);
					continue;
				}
				String tableColumn = tableColumnById.getFieldColumn();
				String templateModule = tableColumnById.getColumnModule();

				ModeTemplateField modeTemplateField = new ModeTemplateField();
				modeTemplateField.setFieldId(fieldId);
				modeTemplateField.setTemplateId(templateId);
				modeTemplateField.setTableName(templateName);
				modeTemplateField.setTalbeColumn(tableColumn);
				modeTemplateField.setFieldName(fieldNames[i]);
				//不填显示名称就不更新
				if(!" ".equals(fieldColumnDisplays[i])){
					modeTemplateField.setFieldDisplay(fieldColumnDisplays[i]);
				}else{
					//去auto表查出原字段 重新插入
					modeTemplateField.setFieldDisplay(tableColumnDao.getTableColumnById(Integer.valueOf(fieldIds[i])).getFieldColumnDisplay());
				}
				modeTemplateField.setTemplateModule(templateModule);
				modeTemplateField.setIsActived(Integer.valueOf(isActiveds[i]));
				modeTemplateField.setCreateUser(loginUserId);
				modeTemplateField.setCreateDate(DateUtil.getCurrentDate());
				modeTemplateField.setUpdateUser(loginUserId);
				modeTemplateField.setUpdateDate(DateUtil.getCurrentDate());
				//插入
				this.templateFieldDao.insertTemplateField(modeTemplateField);
			}
		}
	}

    @Override
    public IPageList<ModeTemplateField> queryModelTemplateInfoColunm(Map<String, Object> condition, IPageSize page) {
        return templateFieldDao.queryModelTemplateInfoColunm(condition,page);
    }

	@Override
	public List<ModeTemplateFieldExtend> queryTemplateFieldAndTypeList(Map<String, Object> condition) {
		return this.templateFieldDao.queryTemplateFieldAndTypeList(condition);
	}

	@Override
	public List<ModeScoreCustomField> getLoanModelScoreByModeId(Integer modeId) {
		return templateFieldDao.getLoanModelScoreByModeId(modeId);
	}
}
