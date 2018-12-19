package banger.service.impl;

import banger.dao.intf.ITableColumnDao;
import banger.dao.intf.ITableInfoDao;
import banger.domain.config.*;
import banger.domain.enumerate.TableInputType;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.spring.SpringContext;
import banger.moduleIntf.IConfigService;
import com.hundsun.common.lang.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */
public class ConfigServiceImpl implements IConfigService {


	public ITableInfoDao getTableInfoDao(){
		return (ITableInfoDao) SpringContext.instance().get("tableInfoDao");
	}

	public ITableColumnDao getTableColumnDao(){
		return (ITableColumnDao) SpringContext.instance().get("tableColumnDao");
	}


    @Override
    public List<AutoBaseTemplate> getAllTemplateList() {
        List<AutoTableInfo> tableList = getTableInfoDao().queryAllTableInfoList();
        List<AutoTableColumn> fieldList = getTableColumnDao().getAllActivedTableColumnList();
        return getAutoTemplateList(tableList,fieldList,false);
    }

    @Override
    public AutoBaseTemplate getTemplateListByTable(String tableName) {
        List<AutoTableInfo> tableList = getTableInfoDao().getTableInfoListByTableNames(new String[]{tableName});
        List<AutoTableColumn> fieldList = getTableColumnDao().getFieldSortListByTableNames(new String[]{tableName});
        List<AutoBaseTemplate> autoBaseTemplateList = getAutoTemplateList(tableList,fieldList, false);
        if(CollectionUtils.isNotEmpty(autoBaseTemplateList)){
            return autoBaseTemplateList.get(0);
        }
        return null;
    }


    @Override
    public List<AutoBaseTemplate>  getTemplateListByTables(String[] tableName) {
        List<AutoTableInfo> tableList = getTableInfoDao().getTableInfoListByTableNames(tableName);
        List<AutoTableColumn> fieldList = getTableColumnDao().getFieldSortListByTableNames(tableName);
        List<AutoBaseTemplate> autoBaseTemplateList = getAutoTemplateList(tableList,fieldList, false);
        if(CollectionUtils.isNotEmpty(autoBaseTemplateList)){
            return autoBaseTemplateList;
        }
        return null;
    }


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
                            template.getFields().add(new AutoFieldWrapper(column.getFieldId(),propertyName,column.getFieldColumn(),column.getFieldColumnDisplay(),column.getFieldType(),column.getFieldNumberUnit(),nullable,column.getFieldLength(),getDictOptions(column.getFieldDictName()),isAppShow,isWebShow,column.getFieldConstraintRule(),column.getPopupUrl(),column.getDefaultValue()));
                        }else{
                            template.getFields().add(new AutoFieldWrapper(column.getFieldId(),propertyName,column.getFieldColumn(),column.getFieldColumnDisplay(),column.getFieldType(),column.getFieldNumberUnit(),nullable,column.getFieldLength(),isAppShow,isWebShow,column.getFieldConstraintRule(),column.getPopupUrl(),column.getDefaultValue()));
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
}
