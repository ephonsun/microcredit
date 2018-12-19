package banger.service.impl;

import banger.dao.intf.ITableColumnDao;
import banger.dao.intf.ITableInfoDao;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.framework.metadata.IDbMetaStore;
import banger.framework.spring.SpringContext;
import banger.framework.util.DateUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.ITableColumnInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */
@Repository
public class TableColumnInsertService implements ITableColumnInsert {
    @Autowired
    private ITableColumnDao tableColumnDao;
    @Autowired
    private ITableInfoDao tableInfoDao;


    private static final String DbMetaStoreKey = "DbMetaStore";

    protected IDbMetaStore getDbMetaStore(){
        return (IDbMetaStore) SpringContext.instance().get(DbMetaStoreKey);
    }

    /**
     * 修改列务注
     *
     *
     *
     *
     */
    @Override
    public void insertTemplateField(AutoTableColumn autoTableColumn, Integer loginUserId) throws Exception {

        autoTableColumn.setCreateUser(loginUserId);
        autoTableColumn.setCreateDate(DateUtil.getCurrentDate());
        autoTableColumn.setUpdateUser(loginUserId);
        autoTableColumn.setUpdateDate(DateUtil.getCurrentDate());

        String tableName,tableName2="";
        Integer sortnor = 0;
        AutoTableColumn table = tableColumnDao.getTemplateTableByTableId(autoTableColumn.getTableId());//通过表tableId获取自定义表信息
        if(null!=table){
            tableName = table.getTableName();
            sortnor = table.getFieldSortno();//排序
        }else{
            AutoTableInfo tableInfo = tableInfoDao.getTableInfoById(autoTableColumn.getTableId());
            tableName = tableInfo.getTableDbName();
        }

        autoTableColumn.setFieldSortno(sortnor+1);

//        Map<String, Object> condition2 = new HashMap<String, Object>();
//        condition2.put("tableName",tableName);
//        condition2.put("fieldColumn", autoTableColumn.getFieldColumn());
//        Integer number = tableColumnDao.queryTableColumnNum( condition2);

//
//        Map<String, Object> condition = new HashMap<String, Object>();
//        condition.put("tableId",autoTableColumn.getTableId());
//        condition.put("fieldColumn", autoTableColumn.getFieldColumn());
//        List<AutoTableColumn>  list = tableColumnDao.queryTableColumnList(condition);
        boolean isExist = tableColumnDao.checkedFieldIsExist(tableName, autoTableColumn.getFieldColumn());
        if (isExist) {
            throw new Exception("已存在同名自定义字段");
        }else {
            if ("LBIZ_PERSONAL_INFO".equals(tableName)){
                tableName2 ="CUST_BASIC_INFO" ;//  CUST_BASIC_INFO

            }else if ("LBIZ_FAMILY_INFO".equals(tableName)){
                tableName2 ="CUST_FAMILY_INFO";//  CUST_FAMILY_INFO

            } else if ("LBIZ_SPOUSE_INFO".equals(tableName)){
                tableName2 ="CUST_SPOUSE_INFO"; //  CUST_SPOUSE_INFO
            }
            //添加表字段
           tableColumnDao.addTableField(tableName,autoTableColumn.getFieldColumn(),autoTableColumn.getFieldName(),autoTableColumn.getFieldType(),autoTableColumn.getFieldLength());
            //修改列务注
            tableColumnDao.commentTableColumn(tableName,autoTableColumn.getFieldColumn(), autoTableColumn.getFieldName());
            if (!tableName2.equals("")){
                //添加表字段2
                tableColumnDao.addTableField(tableName2,autoTableColumn.getFieldColumn(),autoTableColumn.getFieldName(),autoTableColumn.getFieldType(),autoTableColumn.getFieldLength());
                //修改列务注2
                tableColumnDao.commentTableColumn(tableName2,autoTableColumn.getFieldColumn(), autoTableColumn.getFieldName());
            }

            autoTableColumn.setTableName(tableName);
            //插入自定义字段表
            tableColumnDao.insertTemplateField(autoTableColumn);

            getDbMetaStore().clear(tableName);          //清除元数据缓存

        }
    }

}
