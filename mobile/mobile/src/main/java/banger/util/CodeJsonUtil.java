package banger.util;

import banger.domain.enumerate.CodeDictEnum;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by zhusw on 2017/8/25.
 */
public class CodeJsonUtil {

    /**
     * 把数据字典的值转化成名称
     * @param jo
     * @param code
     */
    public static void setCodeDisplay(JSONObject jo,CodeDictEnum code){
        Object value = jo.containsKey(code.codeKey)?jo.get(code.codeKey):null;
        if(value!=null){
            String text = CodeTableUtil.getCodeTableValue(code.codeTable,code.params,value);
            if(StringUtil.isNotEmpty(text)) {
                jo.put(code.nameKey, text);
            }else{
                jo.put(code.nameKey, value);
            }
        }
    }

    /**
     * 把数据字典的值转化成名称
     * @param jo
     * @param sourceKey
     * @param targetKey
     */
    public static void copyProperty(JSONObject jo,String sourceKey,String targetKey){
        Object value = jo.containsKey(sourceKey)?jo.get(sourceKey):null;
        jo.put(targetKey,value);
    }

    /**
     *
     * @param ja
     * @param code
     */
    public static void setCodeDisplay(JSONArray ja, CodeDictEnum code){
        List<ICodeTable.IItem> itemList = CodeTableUtil.getCodeTable(code.codeTable,code.params);
        for(int i=0;i<ja.size();i++){
            JSONObject jo = ja.getJSONObject(i);
            Object value;
            String nameKey = code.nameKey;
            if(jo.containsKey(code.codeKey)){
                value = jo.get(code.codeKey);
            }else{
                value = jo.containsKey("itemName")?jo.get("itemName"):null;
                nameKey = "itemNameDisplay";
            }

            if(value!=null){
                for(ICodeTable.IItem item : itemList){
                    if(item.getValue().equals(value)){
                        jo.put(nameKey, item.getName());
                    }
                }
            }
        }
    }

    /**
     * 把数据字典的值转化成名称
     * @param ja
     * @param sourceKey
     * @param targetKey
     */
    public static void copyProperty(JSONArray ja,String sourceKey,String targetKey){
        for(int i=0;i<ja.size();i++) {
            JSONObject jo = ja.getJSONObject(i);
            Object value = jo.containsKey(sourceKey) ? jo.get(sourceKey) : null;
            if(value!=null){
                jo.put(targetKey,value);
            }
        }
    }

    /**
     *
     * @param code
     * @param value
     * @return
     */
    public static String getCodeDisplay(CodeDictEnum code,Object value){
        if(code!=null && value!=null){
            String text = CodeTableUtil.getCodeTableValue(code.codeTable,code.params,value);
            return text;
        }
        return "";
    }

    public static void setCodeYNDisplay(JSONObject jo, String code) {

        Object value = jo.containsKey(code)?jo.get(code):null;
        if(value!=null){
            String valueStr = String.valueOf(value);
            if(valueStr.equals("1")||valueStr.equalsIgnoreCase("Y")){
                jo.put(code+"Display", "是");
            }else if(valueStr.equals("0")||valueStr.equalsIgnoreCase("N")){
                jo.put(code+"Display", "否");
            }
        }
    }


}
