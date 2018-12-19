package banger.controller;

import banger.common.BaseController;
import banger.domain.config.AutoImportSetting;
import banger.domain.config.AutoImportSettingItem;
import banger.domain.config.AutoTableColumn;
import banger.framework.sql.meta.Column;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;
import banger.framework.util.AnalogDataUtil;
import banger.service.intf.IImportSettingItemService;
import banger.service.intf.IImportSettingService;
import banger.service.intf.IProcedureService;
import banger.service.intf.ITableColumnService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XUElw on 2017/9/26.
 */
@Controller
@RequestMapping("/custAutoImport")
public class CustAutoImportController extends BaseController{
    private static final long serialVersionUID = -392991562840508329L;

    @Autowired
    private ITableColumnService tableColumnService;
    @Autowired
    private IImportSettingService iImportSettingService;
    @Autowired
    private IImportSettingItemService iImportSettingItemService;
    @Autowired
    private IProcedureService procedureService;

    /**
     * 跳转到客户自动导入页面
     */
    @RequestMapping("/importCustTabPage")
    public String importCustTabPage(){
        Map<String,Object> map = new HashMap<String,Object>();
        List<AutoImportSettingItem> itemLists = iImportSettingItemService.queryImportSettingItemList(map);
        AutoImportSetting autoImportSetting = iImportSettingService.getImportSettingById(1);
        Integer matchMode = autoImportSetting.getMatchMode();
        setAttribute("matchMode",matchMode);
        if(itemLists.size() > 0){
            //导入之前数据表中有数据,则显示到页面上
            setAttribute("isFromTable",true);
            setAttribute("itemLists",itemLists);
        }else{
            //导入之前数据表中没有数据，从导入表中获取
            setAttribute("isFromSource",true);
            createNewImport();
        }
        return "/config/vm/custAutoImport";
    }

    /**
     * 保存用户自动导入
     */
    @RequestMapping("/saveCustAutoImport")
    public void saveCustAutoImport(){
        String jsonArray = getRequest().getParameter("jsonArray");
        Integer id = Integer.parseInt(getRequest().getParameter("id"));
        //保存匹配模式
        String matchMode = getRequest().getParameter("matchMode");
        AutoImportSetting autoImportSetting = new AutoImportSetting();
        autoImportSetting.setMatchMode(Integer.parseInt(matchMode));
        autoImportSetting.setSettingId(id);
        iImportSettingService.updateImportSetting(autoImportSetting,getLoginInfo().getUserId());

        JSONArray jsonObject = JSONArray.fromObject(jsonArray);
        //查询自动导入配置信息表
        autoImportSetting = iImportSettingService.getImportSettingById(id);
        //查询用户自定义字段
        List<AutoTableColumn> atcList  = tableColumnService.queryTableColumnListOfCust();
        //保存之前删除原有数据
        deleteBeforeSave();
        if(jsonObject.size() > 0){
            for(int i = 0;i<jsonObject.size();i++){
                JSONObject job = jsonObject.getJSONObject(i);  //把每一个对象转成 json 对象
                AutoImportSettingItem item = new AutoImportSettingItem();
                item.setSettingId(autoImportSetting.getSettingId());       //配置ID
                item.setImportCode(autoImportSetting.getImportCode());     //配置编码
                item.setSourceTableColumn(job.get("soucolname").toString());//来源数据表列名
                item.setSourceColumnIndex(Integer.parseInt(job.get("ordernum").toString()));//来源数据表列序号
                item.setSourceColumnDisplay(job.get("soucoldisply").toString());  //来源数据表列名描述
                item.setIsCovered(Integer.parseInt(job.get("iscover").toString()));//是否复盖1 复盖0 不复盖
                item.setIsRequired(Integer.parseInt(job.get("isreqwrite").toString()));//是否必填1 必填0 不必填
                item.setTargetColumnId(Integer.parseInt(job.get("selectFieldId").toString()));  //自定义字段列ID
                item.setTargetColumnDisplay(job.get("selectValue").toString());   //数据库自定义字段名称
                for(AutoTableColumn atc : atcList){
                    if(atc.getFieldId() == Integer.parseInt(job.get("selectFieldId").toString())){
                        item.setTargetTableColumn(atc.getFieldColumn());            //目标表列名
                        item.setTargetTableName(atc.getTableName());             //目标表表名
                        item.setTargetColumnType(atc.getFieldType());               //目标表列字段类型
                        item.setTargetColumnDictName(atc.getFieldDictName());          //目标表列代码表
                        break;
                    }
                }
                //保存到自动导入配置项表
                iImportSettingItemService.insertImportSettingItem(item,getLoginInfo().getUserId());
            }
        }
    }

    /**
     * 获取来源数据表信息
     */
    public void getSourceTableInfo(){
        List<Column> columnList = new ArrayList<Column>();
        ISqlHelper sqlHelper = SqlHelper.instance();
        List<Column> lists = sqlHelper.getTableColumns("BANK_CUSTOMER_INFO");
        for(Column column : lists){
            if(column.getComment() == null){
                //如果列注释为空，则保存列名
                column.setComment(column.getName());
            }
            if(!"ID".equals(column.getName())&& !"ERROR_FLAG".equals(column.getName())){
                //排除ID列
                columnList.add(column);
            }
        }
        setAttribute("columnList",columnList);
    }

    /**
     * 导入之前数据表中没有数据
     */
    private void createNewImport(){
        getSourceTableInfo();
        List<AutoTableColumn> atcLists  = tableColumnService.queryTableColumnListOfCust();
        List<AutoTableColumn> atcList = new ArrayList<AutoTableColumn>();
        for(AutoTableColumn atc : atcLists){
            if("LBIZ_FAMILY_INFO".equals(atc.getTableName())){
                //家庭信息
                atc.setFieldName("家庭信息-"+atc.getFieldName());
            }else if("LBIZ_SPOUSE_INFO".equals(atc.getTableName())){
                //配偶信息
                atc.setFieldName("配偶信息-"+atc.getFieldName());
            }else if("LBIZ_PERSONAL_INFO".equals(atc.getTableName())){
                //个人信息
                atc.setFieldName("个人信息-"+atc.getFieldName());
            }
            atcList.add(atc);
        }
        //用户自定义表
        setAttribute("atcList",atcList);
    }

    /**
     *  //保存之前先判断自动导入配置项表是否有数据，如果有则删除后再保存数据
     */
    private void deleteBeforeSave(){
        Map<String,Object> map = new HashMap<String,Object>();
        List<AutoImportSettingItem> itemLists = iImportSettingItemService.queryImportSettingItemList(map);
        if(itemLists.size() > 0){
            for(AutoImportSettingItem aisi : itemLists){
                iImportSettingItemService.deleteImportSettingItemById(aisi.getItemId());
            }
        }
    }

    /**
     * 生成存储过程
     */
    @RequestMapping("/generateProcedure")
    public void generateProcedure(){
        StringBuffer inbuf = procedureService.generateInsertProcedure();
        StringBuffer upbuf = procedureService.generateUpdateProcedure();
        log.info(inbuf.toString());
        log.info(upbuf.toString());
        //procedureService.countAutoTableCustId();
    }

    /**
     * 生成模拟数据
     */
    @RequestMapping("/generateAnalogData")
    public void generateAnalogData(){
        Integer amount = Integer.parseInt(getRequest().getParameter("amount"));
        String filePath = "D:/data.txt";
        FileWriter writer = null;
        BufferedWriter bw = null;
        try {
            writer = new FileWriter(filePath);
            bw = new BufferedWriter(writer);
            for (int i = 0; i < amount; i++) {
                bw.write(AnalogDataUtil.getAnalogData(i+1).toString()+"\r\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
