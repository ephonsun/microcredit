package banger.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.loan.LoanApplyTemplate;
import banger.domain.loan.LoanFieldExtend;
import banger.domain.loan.LoanTemplateExtend;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.reader.Reader;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.IFormSettingsProvider;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IFormSettingsDao;
import banger.service.intf.IFormSettingsService;
import banger.domain.config.SysFormSettings;

/**
 * 表单关联设置表业务访问类
 */
@Repository
public class FormSettingsService implements IFormSettingsService,IFormSettingsProvider {

	private static final Logger logger = LoggerFactory.getLogger(FormSettingsService.class);

	@Autowired
	private IFormSettingsDao formSettingsDao;

	/**
	 * 新增表单关联设置表
	 * @param formSettings 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertFormSettings(SysFormSettings formSettings,Integer loginUserId){
		formSettings.setCreateUser(loginUserId);
		formSettings.setCreateDate(DateUtil.getCurrentDate());
		formSettings.setUpdateUser(loginUserId);
		formSettings.setUpdateDate(DateUtil.getCurrentDate());
		this.formSettingsDao.insertFormSettings(formSettings);
	}

	/**
	 *修改表单关联设置表
	 * @param formSettings 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateFormSettings(SysFormSettings formSettings,Integer loginUserId){
		formSettings.setUpdateUser(loginUserId);
		formSettings.setUpdateDate(DateUtil.getCurrentDate());
		this.formSettingsDao.updateFormSettings(formSettings);
	}

	/**
	 * 通过主键删除表单关联设置表
	 * @param id 主键Id
	 */
	public void deleteFormSettingsById(Integer id){
		this.formSettingsDao.deleteFormSettingsById(id);
	}

	/**
	 * 通过主键得到表单关联设置表
	 * @param id 主键Id
	 */
	public SysFormSettings getFormSettingsById(Integer id){
		return this.formSettingsDao.getFormSettingsById(id);
	}

	/**
	 * 查询表单关联设置表
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysFormSettings> queryFormSettingsList(Map<String,Object> condition){
		return this.formSettingsDao.queryFormSettingsList(condition);
	}

	/**
	 * 分页查询表单关联设置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysFormSettings> queryFormSettingsList(Map<String,Object> condition,IPageSize page){
		return this.formSettingsDao.queryFormSettingsList(condition,page);
	}

	/**
	 * @Author: yangdw
	 * @param templateList 模板list
	 * @Description:处理模板的显示隐藏联动
	 * @Date: 9:25 2017/12/7
	 */
	public void setTemplateShowOrHide(List<? extends AutoBaseTemplate> templateList) {
		setTemplateShowOrHide(templateList, false);
	}
	/**
	 * @Author: yangdw
	 * @description AutoBaseTemplate web端获取默认值方法，在attrivateMap中设置标记setDefault
	 * @param templateList 模板list
	 * @Description:处理模板的显示隐藏联动
	 * @Date: 9:25 2017/12/7
	 */
	public void setTemplateShowOrHide(List<? extends AutoBaseTemplate> templateList,boolean isApp){
		try {
			Map<String, Object> templateHideMap = new HashedMap();//空的查询条件,用户填装页面隐藏的表单
			Map<String, SysFormSettings> settingMap = new HashedMap();//设置联动字段map
			List<SysFormSettings> sysFormSettingList = formSettingsDao.queryFormSettingsList(templateHideMap);
			for (SysFormSettings sysFormSetting : sysFormSettingList) {
				String key = sysFormSetting.getControlForm() + "-" + sysFormSetting.getControlItemColumn();
				settingMap.put(key, sysFormSetting);
			}
			for (AutoBaseTemplate autoBaseTemplate : templateList) {
				//获取每个模板
				List<AutoBaseField> fields = autoBaseTemplate.getFields();
				//每个模板中的字段,并判断在web显示,如果不显示不能作为控制值
				for (AutoBaseField autoBaseField : fields) {
					AutoFieldWrapper fieldWrapper = (AutoFieldWrapper)autoBaseField;
					Object[] data = (Object[]) autoBaseTemplate.getData();
					Object value = null;
					if (data != null && data.length > 0)
						value = fieldWrapper.getValue(data[0]);
					String key = autoBaseTemplate.getTableName() + "-" + fieldWrapper.getColumn();
					if (settingMap.containsKey(key)) {
						SysFormSettings formSettings = settingMap.get(key);
						if((isApp && fieldWrapper.getIsAppShow()) || (!isApp && fieldWrapper.getIsWebShow())) {
							if (value != null && StringUtil.isNotEmpty(String.valueOf(value))) {
								String setValue = formSettings.getControlValue();
								String[] setValues = StringUtil.isNotEmpty(setValue) ? setValue.split(",") : new String[0];
								for (int i = 0; i < setValues.length; i++) {
									if (setValues[i].equals(value)) {
										templateHideMap.put(formSettings.getHiddenForm(), "hide");
									}
								}
							}
						}

						if(fieldWrapper.getIsWebShow()) {
							fieldWrapper.setOnChange("hideAllCtrlForm()");
							fieldWrapper.setProperties(getPropertiesString(formSettings));
						}
					}
				}
			}
			for (AutoBaseTemplate autoBaseTemplate : templateList) {
				String hide = (String)templateHideMap.get(autoBaseTemplate.getTableName());
				if (banger.framework.util.StringUtil.isNotEmpty(hide)) {
					autoBaseTemplate.setShowOrHide(hide);
				}
			}
		} catch (Exception e) {
			logger.error("处理模板的显示隐藏联动报错" + e);
			e.printStackTrace();
		}
	}



	private String getPropertiesString(SysFormSettings formSettings){
		return "hideitem='"+formSettings.getHiddenForm()+"' hidevalue='"+formSettings.getControlValue()+"' hideflag='true'";
	}

	/**
	 * @Author: yangdw
	 * @param templateList 模板list,app提交申请校验值
	 * @Description:处理模板的显示隐藏联动
	 * @Date: 9:25 2017/12/7
	 */
	public void setSubmitTemplateShowOrHide(List<LoanTemplateExtend> templateList){
		try {
			Map<String, Object> templateHideMap = new HashedMap();//空的查询条件,用户填装页面隐藏的表单
			Map<String, SysFormSettings> settingMap = new HashedMap();//设置联动字段map
			List<SysFormSettings> sysFormSettingList = formSettingsDao.queryFormSettingsList(templateHideMap);
			for (SysFormSettings sysFormSetting : sysFormSettingList) {
				String key = sysFormSetting.getControlForm() + "-" + sysFormSetting.getControlItemColumn();
				settingMap.put(key, sysFormSetting);
			}
			for (LoanTemplateExtend templateExtend : templateList) {
				//获取每个模板
				List<LoanFieldExtend> fields = templateExtend.getFieldList();
				//每个模板中的字段,并判断在web显示,如果不显示不能作为控制值
				for (LoanFieldExtend fieldExtend : fields) {
					String key = templateExtend.getTableDbName() + "-" + fieldExtend.getFieldColumn();
					if (settingMap.containsKey(key)) {
						SysFormSettings formSettings = settingMap.get(key);
						if(fieldExtend.getFieldAppIsShow() != null && fieldExtend.getFieldAppIsShow().intValue() > 0) {
							Object obj = getValue(templateExtend, fieldExtend);
							String value = (String) TypeUtil.changeType(obj, String.class);
							if (StringUtil.isNotEmpty(value)) {
								String setValue = formSettings.getControlValue();
								String[] setValues = StringUtil.isNotEmpty(setValue) ? setValue.split(",") : new String[0];
								for (int i = 0; i < setValues.length; i++) {
									if (setValues[i].equals(value)) {
										templateHideMap.put(formSettings.getHiddenForm(), "hide");
									}
								}
							}
						}
					}
				}
			}
			for (LoanTemplateExtend templateExtend : templateList) {
				String hide = (String)templateHideMap.get(templateExtend.getTableDbName());
				if (StringUtil.isNotEmpty(hide)) {
					templateExtend.setTableHide(hide);
				}
			}
		} catch (Exception e) {
			logger.error("处理模板的显示隐藏联动报错" + e);
			e.printStackTrace();
		}
	}

	private Object getValue(LoanTemplateExtend templateExtend,LoanFieldExtend fieldExtend){
		DataTable data = (DataTable) templateExtend.getData();
		DataRow[] rows = data.getRows();
		if(rows.length>0) {
			String column = fieldExtend.getFieldColumn();
			Object val = Reader.objectReader().getValue(rows[0], column);
			return val;
		}else{
			return null;
		}
	}

	private Object getValue(AutoBaseTemplate autoBaseTemplate,AutoFieldWrapper fieldWrapper){
		Object data = autoBaseTemplate.getData();
		if(data!=null){
			if(data instanceof DataRow){
				String column = fieldWrapper.getColumn();
				Object val = Reader.objectReader().getValue(data, column);
				return val;
			}else if(data instanceof DataRow[]){
				DataRow[] rows = (DataRow[])data;
				if(rows.length>0) {
					String column = fieldWrapper.getColumn();
					Object val = Reader.objectReader().getValue(rows[0], column);
					return val;
				}else{
					return null;
				}
			}else if(data instanceof Object[]){
				Object[] rows = (Object[])data;
				if(rows.length>0) {
					String field = fieldWrapper.getField();
					Object val = Reader.objectReader().getValue(rows[0], field);
					return val;
				}else{
					return null;
				}
			}else{
				String propertyName = getPropertyNameByColumnName(fieldWrapper.getColumn());
				Object val = Reader.objectReader().getValue(data, propertyName);
				return val;
			}
		}else{
			//
			return fieldWrapper.getDefaultValue();
		}
	}

	/**
	 *
	 * @param columnName
	 * @return
	 */
	private String getPropertyNameByColumnName(String columnName){
		String[] fs = columnName.split("\\_");
		String str="";
		for(int i=0;i<fs.length;i++){
			if(i==0)str+=fs[i].toLowerCase();
			else str+=fs[i].substring(0,1).toUpperCase()+fs[i].substring(1).toLowerCase();
		}
		return str;
	}

	/**
	 * 根据表名修改显示名称
	 * @param displayName
	 * @param tablename
	 */
	@Override
	public void updateDisplayNameByTableName(String displayName, String tablename) {
		formSettingsDao.updateDisplayNameByTableName(displayName,tablename);
	}
}
