package banger.service.impl;

import banger.dao.intf.IImportSettingDao;
import banger.dao.intf.IImportSettingItemDao;
import banger.dao.intf.IProcedureDao;
import banger.domain.config.AutoImportSetting;
import banger.domain.config.AutoImportSettingItem;
import banger.domain.config.AutoTableCountCustid;
import banger.domain.system.SysDataDictCol;
import banger.framework.spring.SpringContext;
import banger.service.intf.IProcedureService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成存储过程
 * Created by XUElw on 2017/10/10.
 */
@Service
public class ProcedureService implements IProcedureService {

    /**
     * 新增的存储过程
     *
     * @return
     */
    @Override
    public StringBuffer generateInsertProcedure() {
        IImportSettingItemDao importSettingItemDao = (IImportSettingItemDao) SpringContext.instance().get("importSettingItemDao");
        IImportSettingDao importSettingDao = (IImportSettingDao) SpringContext.instance().get("importSettingDao");
        AutoImportSetting autoImportSetting = importSettingDao.getImportSettingById(1);
        StringBuffer buffer = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
        List<AutoImportSettingItem> lists = importSettingItemDao.queryImportSettingItemList(map);
        buffer.append(" CREATE OR REPLACE PROCEDURE PRO_INSERT_BASIC()\n" +
                " LANGUAGE SQL\n" +
                " SPECIFIC PRO_INSERT_BASIC\n" +
                " BEGIN\n");
        buffer.append(insertCustProcedure(lists, "CUST_BASIC_INFO", "LBIZ_PERSONAL_INFO", autoImportSetting.getMatchMode()));
        buffer.append(insertProcedure(lists, "CUST_FAMILY_INFO", "LBIZ_FAMILY_INFO", autoImportSetting.getMatchMode()));
        buffer.append(insertProcedure(lists, "CUST_SPOUSE_INFO", "LBIZ_SPOUSE_INFO", autoImportSetting.getMatchMode()));
        buffer.append("END \n");
        return buffer;
    }

    /**
     * 更新的存储过程
     *
     * @return
     */
    @Override
    public StringBuffer generateUpdateProcedure() {
        IImportSettingItemDao importSettingItemDao = (IImportSettingItemDao) SpringContext.instance().get("importSettingItemDao");
        StringBuffer buffer = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
        List<AutoImportSettingItem> lists = importSettingItemDao.queryImportSettingItemList(map);
        buffer.append(" CREATE OR REPLACE PROCEDURE PRO_UPDATE_BASIC() \n" +
                " LANGUAGE SQL\n" +
                " SPECIFIC PRO_UPDATE_BASIC\n" +
                " BEGIN\n");
        buffer.append(updateProcedure(lists, "CUST_BASIC_INFO", "LBIZ_PERSONAL_INFO"));
        buffer.append(updateProcedure(lists, "CUST_FAMILY_INFO", "LBIZ_FAMILY_INFO"));
        buffer.append(updateProcedure(lists, "CUST_SPOUSE_INFO", "LBIZ_SPOUSE_INFO"));
        buffer.append(" END \n");
        return buffer;
    }

    /**
     * 新增客户信息存储过程
     *
     * @param list
     * @param custTable
     * @param lbizTable
     * @return
     */
    private StringBuffer insertCustProcedure(List<AutoImportSettingItem> list, String custTable, String lbizTable, Integer matchMode) {
        StringBuffer buffer = new StringBuffer();
        StringBuffer selectBuf = new StringBuffer();
        //buffer.append(" INSERT INTO " + custTable + " \n");
        String insertStr = " INSERT INTO " + custTable + " \n" + "(ID,";
        selectBuf.append(" SELECT \n" +
                " (NEXTVAL FOR SEQ_CUST_BASIC_INFO) AS ID,\n ");
        String column = "";
        List<AutoImportSettingItem> lists = list;
        for (AutoImportSettingItem item : lists) {
            //是否覆盖，1 复盖   0 不复盖，如果覆盖则拼接sql
            if (1 == item.getIsCovered()) {
                if (lbizTable.equals(item.getTargetTableName())) {
                    column = column + item.getTargetTableColumn() + ", ";
                    if ("Select".equals(item.getTargetColumnType())) {
                        selectBuf.append(" (CASE B." + item.getTargetTableColumn() + " \n");
                        queryDictColByDictName(selectBuf, item.getTargetColumnDictName());
                        selectBuf.append(" END) AS " + item.getTargetTableColumn() + ", \n");
                    } else if ("YesNo".equals(item.getTargetColumnType())) {
                        selectBuf.append(" (CASE B." + item.getTargetTableColumn() + " \n");
                        queryDictColByDictName(selectBuf, item.getTargetColumnDictName());
                        selectBuf.append(" END) AS " + item.getTargetTableColumn() + ", \n");
                    } else {
                        selectBuf.append(" B." + item.getTargetTableColumn() + ", \n");
                    }
                }
            }
        }
        if(!"".equals(column)){
            //客户个人信息特殊字段处理
            buffer.append(insertStr);
            column = column + "CUSTOMER_NUM, " + "IS_DEL, " + "CREATE_USER, " + "CREATE_DATE, " + "UPDATE_USER, " + "UPDATE_DATE) \n";
            buffer.append(column);
            buffer.append(selectBuf);
            buffer.append("B.CUSTOMER_NUM AS CUSTOMER_NUM, \n");
            buffer.append(" 0 AS IS_DEL, \n");
            buffer.append(" 1 AS CREATE_USER, \n");
            buffer.append(" SYSDATE AS CREATE_DATE, \n");
            buffer.append(" 1 AS UPDATE_USER, \n");
            buffer.append(" SYSDATE AS UPDATE_DATE \n");
            if (matchMode == 1) {//三要素匹配模式
                buffer.append(" FROM BANK_CUSTOMER_INFO B \n" + " WHERE B.CUST_ID NOT IN(SELECT ID FROM " + custTable + ") AND B.ERROR_FLAG = 0; \n");
                buffer.append(" UPDATE BANK_CUSTOMER_INFO B SET B.CUST_ID = (SELECT CBI.ID FROM CUST_BASIC_INFO CBI WHERE B.CUSTOMER_NAME = CBI.CUSTOMER_NAME AND B.IDENTIFY_NUM = CBI.IDENTIFY_NUM);\n");
            } else {//客户内码匹配模式
                buffer.append(" FROM BANK_CUSTOMER_INFO B \n" + " WHERE B.CUST_ID NOT IN(SELECT ID FROM " + custTable + ") AND B.ERROR_FLAG = 0 AND B.CUSTOMER_NUM IS NOT NULL; \n");
                buffer.append(" UPDATE BANK_CUSTOMER_INFO B SET B.CUST_ID = (SELECT CBI.ID FROM CUST_BASIC_INFO CBI WHERE B.CUSTOMER_NUM = CBI.CUSTOMER_NUM);\n");
            }
        }
        return buffer;
    }

    /**
     * 新增存储过程 家庭信息 配偶信息
     *
     * @param list
     * @param lbizTable 自定义表名
     * @param custTable 客户表名
     * @return
     */
    private StringBuffer insertProcedure(List<AutoImportSettingItem> list, String custTable, String lbizTable, Integer matchMode) {
        StringBuffer buffer = new StringBuffer();
        StringBuffer selectBuf = new StringBuffer();
        //buffer.append(" INSERT INTO "+ custTable +" \n");
        String insertStr = " INSERT INTO " + custTable + " \n" +"(ID,";
        selectBuf.append(" SELECT \n" +
                " B.CUST_ID AS ID,\n");
        String column = "";
        List<AutoImportSettingItem> lists = list;
        for (AutoImportSettingItem item : lists) {
            //是否覆盖，1 复盖   0 不复盖，如果覆盖则拼接sql
            if (1 == item.getIsCovered()) {
                if (lbizTable.equals(item.getTargetTableName())) {
                    column = column + item.getTargetTableColumn() + ", ";
                    if ("Select".equals(item.getTargetColumnType())) {
                        selectBuf.append(" (CASE B." + item.getTargetTableColumn() + " \n");
                        queryDictColByDictName(selectBuf, item.getTargetColumnDictName());
                        selectBuf.append(" END) AS " + item.getTargetTableColumn() + ", \n");
                    } else if ("YesNo".equals(item.getTargetColumnType())) {
                        selectBuf.append(" (CASE B." + item.getTargetTableColumn() + " \n");
                        queryDictColByDictName(selectBuf, item.getTargetColumnDictName());
                        selectBuf.append(" END) AS " + item.getTargetTableColumn() + ", \n");
                    } else {
                        selectBuf.append(" B." + item.getTargetTableColumn() + ", \n");
                    }
                }
            }
        }
        if (!"".equals(column)) {
            buffer.append(insertStr);
            buffer.append(column.substring(0, column.length() - 2) + ")\n");
            buffer.append(selectBuf.toString().substring(0, selectBuf.toString().length() - 3) + "\n");
            if (matchMode == 1) {//三要素匹配模式
                buffer.append(" FROM BANK_CUSTOMER_INFO B \n" + " WHERE B.CUST_ID NOT IN(SELECT ID FROM " + custTable + ") AND B.ERROR_FLAG = 0; \n");
            } else {//客户内码匹配模式
                buffer.append(" FROM BANK_CUSTOMER_INFO B \n" + " WHERE B.CUST_ID NOT IN(SELECT ID FROM " + custTable + ") AND B.ERROR_FLAG = 0 AND B.CUSTOMER_NUM IS NOT NULL; \n");
            }
        }
        return buffer;
    }

    /**
     * 更新存储过程
     *
     * @param list      设置表集合
     * @param custTable 客户表
     * @param lbizTable 自定义表
     * @return
     */
    private StringBuffer updateProcedure(List<AutoImportSettingItem> list, String custTable, String lbizTable) {
        StringBuffer buffer = new StringBuffer();
        StringBuffer selectBuf = new StringBuffer();
        //buffer.append(" UPDATE "+ custTable +" AS C SET (\n ");
        String updateStr = " UPDATE " + custTable + " AS C SET (\n ";
        String column = "";
        selectBuf.append(" = ( SELECT ");
        List<AutoImportSettingItem> lists = list;
        for (AutoImportSettingItem item : lists) {
            //是否覆盖，1 复盖   0 不复盖，如果覆盖则拼接sql
            if (1 == item.getIsCovered()) {
                if (lbizTable.equals(item.getTargetTableName())) {
                    column = column + item.getTargetTableColumn() + ", ";
                    if ("Select".equals(item.getTargetColumnType())) {
                        selectBuf.append(" (CASE B." + item.getTargetTableColumn() + "\n ");
                        queryDictColByDictName(selectBuf, item.getTargetColumnDictName());
                        selectBuf.append(" END) AS " + item.getTargetTableColumn() + ", \n");
                    } else {
                        selectBuf.append(" B." + item.getTargetTableColumn() + ", \n");
                    }
                }
            }
        }
        if (!"".equals(column)) {
            buffer.append(updateStr);
            buffer.append(column.substring(0, column.length() - 2) + ")\n");
            buffer.append(selectBuf.toString().substring(0, selectBuf.toString().length() - 3) + "\n");
            buffer.append(" FROM BANK_CUSTOMER_INFO B WHERE B.CUST_ID = C.ID AND B.ERROR_FLAG = 0) \n" +
                    " WHERE EXISTS (SELECT 1 FROM BANK_CUSTOMER_INFO BCI WHERE BCI.CUST_ID = C.ID);\n ");
        }
        return buffer;
    }

    /**
     * 由字典表英文名称获得字典表对象
     *
     * @param buffer
     */
    private StringBuffer queryDictColByDictName(StringBuffer buffer, String dictname) {
        IProcedureDao procedureDao = (IProcedureDao) SpringContext.instance().get("procedureDao");
        List<SysDataDictCol> colList = procedureDao.queryDictColByDictName(dictname);
        Integer index = 0;
        for (SysDataDictCol col : colList) {
            buffer.append("  WHEN '" + col.getDictName() + "' THEN  '" + col.getDictValue() + "'\n");
        }
        for (SysDataDictCol col : colList) {
            buffer.append("  WHEN '" + col.getDictValue() + "' THEN  '" + col.getDictValue() + "'\n");
        }
        buffer.append("  ELSE '' \n");
        return buffer;
    }

    /**
     * 统计导入的客户表中客户id是否有重复,过滤重复记录
     *
     * @return
     */
    @Override
    public void countAutoTableCustId() {
        IProcedureDao procedureDao = (IProcedureDao) SpringContext.instance().get("procedureDao");
        procedureDao.updateCustId();
        List<Integer> arr = new ArrayList<Integer>();
        List<AutoTableCountCustid> lists = procedureDao.countAutoTableCustId();
        for (AutoTableCountCustid list : lists) {
            if (list.getCount() > 1) {
                arr.add(list.getCustId());
            }
        }
        //修改错误标志
        if (arr != null && arr.size() > 0) {
            procedureDao.updateErrorFlag(arr);
        }
    }
}
