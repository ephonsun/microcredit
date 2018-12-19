package banger.framework.codetable;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import banger.framework.codetable.ICodeTable.IItem;
import banger.framework.spring.SpringContext;
import banger.framework.util.StringUtil;

/**
 * 类解释
 *
 * @Version 1.0 2017/3/30
 * @Author Merlin
 */
public class CodeTableUtil {

    private static Log log = LogFactory.getLog(CodeTableUtil.class);

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
     * 得到代码表的值
     * @param codeTable
     * @param params
     * @param value
     * @return
     */
    public static String getCodeTableValue(String codeTable,String params,Object value){
        try{
            List<ICodeTable.IItem> items = getCodeTable(codeTable, params);
            if(items.size()>0){
                for(ICodeTable.IItem item : items){
                    if(value!=null && value.toString().equals(item.getValue())){
                        return item.getName();
                    }
                }
            }
        }catch(Exception e){
            logError(e);
        }
        return "";
    }

    /**
     * 得到代码表json数据
     * @param codetable
     * @param params
     * @return
     */
    public static String getCodeTableJson(String codetable,String params){
        JSONArray ja = new JSONArray();
        try{
            if(!StringUtil.isNullOrEmpty(codetable)){
                Object bean = SpringContext.instance().get(codetable);
                if(bean instanceof ICodeTable){
                    Object[] args = (!StringUtil.isNullOrEmpty(params))?params.split(",",-1):new Object[0];
                    List<ICodeTable.IItem> items = ((ICodeTable)bean).getItems(args);
                    for(ICodeTable.IItem item : items){
                        JSONObject jo = new JSONObject();
                        jo.put("value", item.getValue());
                        jo.put("text", item.getName());
                        jo.put("checked",item.checked());
                        ja.add(jo);
                    }
                }
            }
        }catch(Exception e){
            logError(e);
        }
        return ja.toString();
    }

    public static String test(String codetable){
        return "test" + codetable;
    }

    private static void logError(Throwable e){
        log.error("error:"+e.getMessage(),e);
    }


}
