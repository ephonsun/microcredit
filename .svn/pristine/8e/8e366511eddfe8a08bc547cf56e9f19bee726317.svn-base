package banger.common.tools;

import banger.domain.enumerate.*;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.collection.OptionItem;
import banger.framework.spring.SpringContext;
import banger.framework.util.*;
import banger.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 类解释
 *
 * @Version 1.0 2017/5/2
 * @Author Merlin
 */
public class ConstantCodeUtil {

    private static Log log = LogFactory.getLog(ConstantCodeUtil.class);

    public static String cdFiledType(){
        JSONObject json = new JSONObject();
        for (EnumFiledType filedType : EnumFiledType.values()) {
            json.put(filedType.fieldType,filedType.typeName);
        }
        return json.toString();
    }

    public static String cdLoanType(){
        JSONObject json = new JSONObject();
        for (LoanProcessTypeEnum loanProcessType : LoanProcessTypeEnum.values()) {
            json.put(loanProcessType.type,loanProcessType.typeName);
        }
        return json.toString();
    }

    public static String cdFileType(){
        JSONObject json = new JSONObject();
        for (LoanAddedFileType f : LoanAddedFileType.values()) {
            json.put(f.type,f.typeName);
        }
        return json.toString();
    }

    /**
     * 枚举方式获取下拉项
     * @return
     */
    public static List<OptionItem> getOptionItems(String moule){
        if ("loanProcess".equals(moule))
            return LoanProcessTypeEnum.getOptionItems();
        else if ("monitor".equals(moule))
            return LoanMonitorState.getOptionItems();
        else if ("collectionState".equals(moule))
            return LoanCollectionState.getOptionItems();
        else if("loanAddedFile".equals(moule)){
            return LoanAddedFileType.getOptionItems();
        }else {
            return new ArrayList<OptionItem>();
        }
    }

    /**
     * 得到代码表组件
     * @param codetable
     * @param params
     * @return
     */
    public static List<ICodeTable.IItem> getCodeTable(String codetable, String params){
        try{
            if(!StringUtil.isNullOrEmpty(codetable)){
                Object bean = SpringContext.instance().get(codetable);
                if(bean instanceof ICodeTable){
                    Object[] args = (!StringUtil.isNullOrEmpty(params))?params.split(",",-1):new Object[0];
                    return ((ICodeTable)bean).getItems(args);
                }
            }
        }catch(Exception e){
            logError(e);
        }
        return new ArrayList<ICodeTable.IItem>();
    }

    /**
     * 把数据字典的值转化成名称
     * @param codetable
     * @param params
     * @param value
     * @return
     */
    public String getCodeTableDisplay(String codetable, String params, String value){
        if(StringUtil.isNotEmpty(codetable) && StringUtil.isNotEmpty(value)){
            Object bean = SpringContext.instance().get(codetable);
            if(bean instanceof ICodeTable){
                Object[] args = (!StringUtil.isNullOrEmpty(params))?params.split(",",-1):new Object[0];
                List<ICodeTable.IItem> items= ((ICodeTable)bean).getItems(args);
                if(items!=null && items.size()>0){
                    for (ICodeTable.IItem iItem : items) {
                        if(value.equals(iItem.getValue()))
                            return iItem.getName();
                    }
                }
            }
        }
        return "";
    }


    /**
     * 得到字典列表
     * @param codetable
     * @param params
     * @return
     */
    public static String getDataDictList(String codetable, String params){
        try{
            if(!banger.framework.util.StringUtil.isNullOrEmpty(codetable)){
                Object bean = SpringContext.instance().get(codetable);
                if(bean instanceof ICodeTable){
                    Object[] args = (!banger.framework.util.StringUtil.isNullOrEmpty(params))?params.split(",",-1):new Object[0];
                    List<ICodeTable.IItem> items=((ICodeTable)bean).getItems(args);
                    StringBuilder array=new StringBuilder("{");
                    //返回    {0:"禁用",1:"启用"}
                    int i=0;
                    for (ICodeTable.IItem iItem : items) {
                        array.append(iItem.getValue());
                        array.append(":");
                        array.append("\"");
                        array.append(iItem.getName());
                        array.append("\"");
                        i++;
                        if(i<items.size()){
                            array.append(",");
                        }
                    }
                    array.append("}");
                    return array.toString();
                }
            }
        }catch(Exception e){
            logError(e);
        }
        return "";
    }

    /**
     * 监控类型
     * @return
     */
    public static String cdMonitorType(){
        JSONObject json = new JSONObject();
        for (LoanMonitorType l : LoanMonitorType.values()) {
            json.put(l.type,l.typeName);
        }
        return json.toString();
    }

    /**
     * 监控状态
     */
    public static String cdMonitorState(){
        JSONObject json = new JSONObject();
        for (LoanMonitorState l : LoanMonitorState.values()) {
            json.put(l.state,l.stateName);
        }
        return json.toString();
    }

    /**
     * 催收状态
     */
    public static String cdloanCollectionState(){
        JSONObject json = new JSONObject();
        for (LoanCollectionState loanCollectionState : LoanCollectionState.values()) {
            json.put(loanCollectionState.state,loanCollectionState.stateName);
        }
        return json.toString();
    }

    private static void logError(Throwable e){
        log.error("error:"+e.getMessage(),e);
    }

    /**
     * 审计状态
     */
    public static String cdLoanAuditState(){
        JSONObject json = new JSONObject();
        for (LoanAuditResultEnum loanAuditResultEnum : LoanAuditResultEnum.values()) {
            json.put(loanAuditResultEnum.value,loanAuditResultEnum.desc);
        }
        return json.toString();
    }

    public static String cdTypeName(){
        JSONObject json = new JSONObject();
        for(LoanProcessTypeEnum loanProcess : LoanProcessTypeEnum.values()){
           json.put(loanProcess.type,loanProcess.typeName);
        }
        return json.toString();
    }


}

