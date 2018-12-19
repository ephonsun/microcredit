package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.common.constant.GlobalConst;
import banger.dao.intf.ITableColumnDao;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.IntoTemplatesFieldQuery;
import banger.moduleIntf.ITemplatesFieldProvider;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITemplatesFieldDao;
import banger.service.intf.ITemplatesFieldService;
import banger.domain.config.IntoTemplatesField;

/**
 * 进件管理模板字段表业务访问类
 */
@Repository
public class TemplatesFieldService implements ITemplatesFieldService,ITemplatesFieldProvider {

	@Autowired
	private ITemplatesFieldDao templatesFieldDao;
	@Autowired
	private ITableColumnDao tableColumnDao;

	/**
	 * 新增进件管理模板字段表
	 * @param templatesField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTemplatesField(IntoTemplatesField templatesField,Integer loginUserId){
		templatesField.setCreateUser(loginUserId);
		templatesField.setCreateDate(DateUtil.getCurrentDate());
		templatesField.setUpdateUser(loginUserId);
		templatesField.setUpdateDate(DateUtil.getCurrentDate());
		this.templatesFieldDao.insertTemplatesField(templatesField);
	}

	/**
	 *修改进件管理模板字段表
	 * @param templatesField 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTemplatesField(IntoTemplatesField templatesField,Integer loginUserId){
		templatesField.setUpdateUser(loginUserId);
		templatesField.setUpdateDate(DateUtil.getCurrentDate());
		this.templatesFieldDao.updateTemplatesField(templatesField);
	}

	/**
	 * 通过主键删除进件管理模板字段表
	 * @param fieldId 主键Id
	 */
	public void deleteTemplatesFieldById(Integer fieldId){
		this.templatesFieldDao.deleteTemplatesFieldById(fieldId);
	}

	/**
	 * 通过主键得到进件管理模板字段表
	 * @param fieldId 主键Id
	 */
	public IntoTemplatesFieldQuery getTemplatesFieldById(Integer fieldId){
		return this.templatesFieldDao.getTemplatesFieldById(fieldId);
	}

	/**
	 * 查询进件管理模板字段表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoTemplatesFieldQuery> queryTemplatesFieldList(Map<String,Object> condition){
		return this.templatesFieldDao.queryTemplatesFieldList(condition);
	}

	/**
	 * 分页查询进件管理模板字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoTemplatesField> queryTemplatesFieldList(Map<String,Object> condition,IPageSize page){
		return this.templatesFieldDao.queryTemplatesFieldList(condition,page);
	}


	public IPageList<IntoTemplatesField> queryIntoTemplatesInfoColunm(Map<String, Object> condition, IPageSize page) {
		return this.templatesFieldDao.queryIntoTemplatesInfoColunm(condition,page);
	}


	public void saveOrUpdate(Integer templateId, String templateName, String[] fieldIds, String[] isActiveds, String[] fieldNames, String[] fieldColumnDisplays, String[] isMusts, Integer loginUserId) {
//查询表中是否有相应数据
		Map<String,Object> condition =new HashMap<String,Object>();
		condition.put("templateId",templateId);
		List<IntoTemplatesFieldQuery> modeTemplateFields = templatesFieldDao.queryTemplatesFieldList(condition);

		if(modeTemplateFields != null && modeTemplateFields.size() > 0){
			//有，更新
			for (int i = 0; i < fieldIds.length; i++) {
				IntoTemplatesField templateFieldById = templatesFieldDao.getTemplatesFieldById(Integer.valueOf(fieldIds[i]));
				//不填显示名称就不更新
				if(!" ".equals(fieldColumnDisplays[i])){
					templateFieldById.setFieldDisplay(fieldColumnDisplays[i]);
				}
				templateFieldById.setIsActived(Integer.valueOf(isActiveds[i]));
				templateFieldById.setIsMust(Integer.valueOf(isMusts[i]));
				templateFieldById.setUpdateUser(loginUserId);
				templateFieldById.setUpdateDate(DateUtil.getCurrentDate());

				this.templatesFieldDao.updateTemplatesField(templateFieldById);
			}
		}else{
			//没，新增
			for (int i = 0; i < fieldIds.length; i++) {
				AutoTableColumn tableColumnById = tableColumnDao.getTableColumnById(Integer.valueOf(fieldIds[i]));
				String tableColumn = tableColumnById.getFieldColumn();
				String templateModule = tableColumnById.getColumnModule();

				IntoTemplatesField intoTemplatesField = new IntoTemplatesField();
				intoTemplatesField.setFieldId(Integer.valueOf(fieldIds[i]));
				intoTemplatesField.setTemplateId(templateId);
				intoTemplatesField.setTableName(templateName);
				intoTemplatesField.setTalbeColumn(tableColumn);
				intoTemplatesField.setFieldName(fieldNames[i]);
				intoTemplatesField.setIsMust(Integer.valueOf(isMusts[i]));
				intoTemplatesField.setSortNo(tableColumnById.getFieldSortno());
				//不填显示名称就不更新
				if(!" ".equals(fieldColumnDisplays[i])){
					intoTemplatesField.setFieldDisplay(fieldColumnDisplays[i]);
				}else{
					//去auto表查出原字段 重新插入
					intoTemplatesField.setFieldDisplay(tableColumnDao.getTableColumnById(Integer.valueOf(fieldIds[i])).getFieldColumnDisplay());
				}
				intoTemplatesField.setTemplateModule(templateModule);
				intoTemplatesField.setIsActived(Integer.valueOf(isActiveds[i]));
				intoTemplatesField.setCreateUser(loginUserId);
				intoTemplatesField.setCreateDate(DateUtil.getCurrentDate());
				intoTemplatesField.setUpdateUser(loginUserId);
				intoTemplatesField.setUpdateDate(DateUtil.getCurrentDate());
				//插入
				this.templatesFieldDao.insertTemplatesField(intoTemplatesField);
			}
		}
	}

	/**
	 * 上移下移
	 */
	@Override
	public void moveIntoTemplatesFieldOrderNo(Integer templateId, Integer fieldId, String type) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("templateId", templateId);
//		condition.put("isActived", GlobalConst.YesOrNo.YES);
		// 查出当前模块的所有列
		List<IntoTemplatesField> list = templatesFieldDao.queryIntoTemplatesFieldByConditionOrder(condition);
		IntoTemplatesField moveField = null;
		IntoTemplatesField changeField = null;
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFieldId().intValue() == fieldId.intValue()) {
				// 移动行
				moveField = list.get(i);
				// 下移
				if ("moveDown".equals(type)) {
					int last = i + 1;
					// 如果不是最后一行
					if (last < list.size()) {
						changeField = list.get(last);
					}
					// 上移
				} else if ("moveUp".equals(type)) {
					int pre = i - 1;
					if (i > 0) {
						changeField = list.get(pre);
					}
				}
			}
		}
		// 对换排序号
		if (changeField != null && moveField != null) {
			int sortno = moveField.getSortNo();
			moveField.setSortNo(changeField.getSortNo());
			changeField.setSortNo(sortno);
			this.templatesFieldDao.updateTemplatesField(moveField);
			this.templatesFieldDao.updateTemplatesField(changeField);
		}
	}
}
