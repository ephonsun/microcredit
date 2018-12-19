package banger.service.impl;

import banger.dao.intf.ITableColumnDao;
import banger.dao.intf.ITableInfoDao;
import banger.domain.config.*;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.LoanAuditTableFieldExtend;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IAutoTemplateProvider;
import banger.service.intf.IAutoFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义字段模板
 * @author zhusw
 *
 */
@Repository
public class AutoFieldService implements IAutoFieldService,IAutoTemplateProvider {

	@Autowired
	private ITableColumnDao tableColumnDao;
	
	@Autowired
	private ITableInfoDao tableInfoDao;

	/**
	 * 得到模板集合
	 * tableIds
	 * @return
	 */
	@Override
	public List<AutoBaseTemplate> getTemplateListByIds(Integer[] tableIds) {
		List<AutoTableInfo> tableList = tableInfoDao.getTableInfoListByTableIds(tableIds);
		List<AutoTableColumn> fieldList = tableColumnDao.getFieldSortListByTableIds(tableIds);
		return getAutoTemplateList(tableList,fieldList);
	}

	@Override
	public List<AutoBaseTemplate> getApprovalTemplateListByIds(Integer[] tableIds, List<LoanAuditTableFieldExtend> loanAuditTableFieldExtends) {
		List<AutoTableInfo> tableList = tableInfoDao.getTableInfoListByTableIds(tableIds);
		List<AutoTableColumn> fieldList = new ArrayList<AutoTableColumn>();
		for (LoanAuditTableFieldExtend loanAuditTableFieldExtend : loanAuditTableFieldExtends) {
			fieldList.add(loanAuditTableFieldExtend);
		}
		return getAutoTemplateList(tableList, fieldList);
	}

	/**
	 * 得到模板集合
	 * @return
	 */
	@Override
	public List<AutoBaseTemplate> getTemplateListByTables(String[] tableNames) {
		return getTemplateListByTables(tableNames, false);
	}

	@Override
	public List<AutoBaseTemplate> getTemplateListByTables(String[] tableNames, boolean isShow) {
		List<AutoTableInfo> tableList = tableInfoDao.getTableInfoListByTableNames(tableNames);
		List<AutoTableColumn> fieldList = tableColumnDao.getFieldSortListByTableNames(tableNames);
		return getAutoTemplateList(tableList,fieldList, isShow);
	}

	@Override
	public List<AutoBaseTemplate> getAllTemplateList() {
		List<AutoTableInfo> tableList = tableInfoDao.queryAllTableInfoList();
		List<AutoTableColumn> fieldList = tableColumnDao.getAllActivedTableColumnList();
		return getAutoTemplateList(tableList,fieldList);
	}


	/**
	 * 得到贷款环节模板集合
	 * @return
	 */
	@Override
	public List<AutoBaseTemplate> getTemplateListByLoanType(String loanTypeId, String precType, Integer tableId) {
		List<AutoTableInfo> tableList = tableInfoDao.getTableInfoListByLoanType(loanTypeId,precType,tableId);
		List<AutoTableColumn> fieldList = tableColumnDao.getFieldSortListByLoanType(loanTypeId,precType,tableId);
		return getAutoTemplateList(tableList,fieldList);
	}


	private List<AutoBaseTemplate> getAutoTemplateList(List<AutoTableInfo> tableList,List<AutoTableColumn> fieldList){
		return getAutoTemplateList(tableList, fieldList, false);
	}
	/**
	 *
	 * @param tableList
	 * @param fieldList
	 * @param isShow [是否显示所有，跳过是否显示]
	 * @return
	 */
	private List<AutoBaseTemplate> getAutoTemplateList(List<AutoTableInfo> tableList,List<AutoTableColumn> fieldList, boolean isShow){
		List<AutoBaseTemplate> templateList = new ArrayList<AutoBaseTemplate>();
		for(AutoTableInfo table : tableList){
			AutoBaseTemplate template = new AutoBaseTemplate();
			String lowerTableName = getLowerName(table.getTableDbName());
			template.setName(table.getTableDisplayName());
			template.setId(table.getTableId());
			template.setTableName(table.getTableDbName());
			template.setLowerTableName(lowerTableName);
			template.setType(TableInputType.valueOf(table.getTableType()).type);
			template.setModule(table.getTableModule());
			templateList.add(template);
			for(AutoTableColumn column : fieldList){
				if(column.getTableName().equals(table.getTableDbName())){
					String propertyName = getLowerName(column.getFieldColumn());
					Boolean nullable = column.getFieldIsRequired()!=null && column.getFieldIsRequired().equals(1);
					boolean isAppShow = column.getFieldAppIsShow()!=null && column.getFieldAppIsShow().intValue()>0;
					boolean isWebShow = column.getFieldWebIsShow()!=null && column.getFieldWebIsShow().intValue()>0;
					if(isShow || isAppShow || isWebShow){
						if(StringUtil.isNotEmpty(column.getFieldDictName())){
							AutoFieldWrapper fieldWrapper = new AutoFieldWrapper(column.getFieldId(),propertyName,column.getFieldColumn(),column.getFieldColumnDisplay(),column.getFieldType(),column.getFieldNumberUnit(),nullable,column.getFieldLength(),getDictOptions(column.getFieldDictName()),isAppShow,isWebShow,column.getFieldConstraintRule(),column.getPopupUrl(),column.getDefaultValue());
							fieldWrapper.setEditable(false);
							template.getFields().add(fieldWrapper);
						}else{
							AutoFieldWrapper fieldWrapper = new AutoFieldWrapper(column.getFieldId(),propertyName,column.getFieldColumn(),column.getFieldColumnDisplay(),column.getFieldType(),column.getFieldNumberUnit(),nullable,column.getFieldLength(),isAppShow,isWebShow,column.getFieldConstraintRule(),column.getPopupUrl(),column.getDefaultValue());
							fieldWrapper.setEditable(false);
							template.getFields().add(fieldWrapper);
						}
					}
				}
			}
		}
		return templateList;
	}

	/**
	 * 推得到贷款业务类型
	 * @return
	 */
	private List<AutoBaseOption> getDictOptions(String dictName){
		List<AutoBaseOption> list = new ArrayList<AutoBaseOption>();
		List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", dictName);
		for(ICodeTable.IItem item : items){
			list.add(new AutoBaseOption(item.getValue(),item.getName()));
		}
		return list;
	}
	
	/**
	 * 列名转字段名
	 * @param columnName
	 * @return
	 */
	private String getLowerName(String columnName){
		String[] parts = columnName.split("_");
		String lowerName = "";
		for(int i=0;i<parts.length;i++){
			if(i==0){
				lowerName+=parts[i].toLowerCase();
			}else{
				lowerName+=parts[i].substring(0,1)+parts[i].substring(1).toLowerCase();
			}
		}
		return lowerName;
	}
	
	/**
	 * 通过Id得到tableInfo对像
	 * @param tableId
	 * @return
	 */
	public AutoTableInfo getTableInfoById(Integer tableId){
		return tableInfoDao.getTableInfoById(tableId);
	}

	@Override
	public List<AutoTableInfo> getAllTableInfoList() {
		return tableInfoDao.queryAllTableInfoList();
	}



}
