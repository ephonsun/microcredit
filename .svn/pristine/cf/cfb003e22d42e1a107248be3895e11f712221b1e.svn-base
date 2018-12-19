package banger.framework.component.autoimport.convert;

import banger.framework.codetable.ICodeTable;
import banger.framework.component.autoimport.IPropertyInfo;
import banger.framework.reader.Reader;
import banger.framework.util.StringUtil;
import banger.framework.writer.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代码表,数据转换
 * Created by zhusw on 2017/9/25.
 */
public class CodeTableDataConvert extends AbstractDataConvert {
    private ICodeTable codeTable;
    private boolean isCache;        //是否内部缓存
    private Map<String,List<ICodeTable.IItem>> cacheMap;

    public CodeTableDataConvert(ICodeTable codeTable){
        this(codeTable,true);
    }

    public CodeTableDataConvert(ICodeTable codeTable,boolean isCache){
        this.codeTable = codeTable;
        this.isCache = isCache;
        this.cacheMap = new HashMap<String,List<ICodeTable.IItem>>();
    }

    @Override
    public String[] convert(Object data,IPropertyInfo... propertyInfos) {
        List<String> messageList = new ArrayList<String>();
        for(IPropertyInfo propertyInfo : propertyInfos){
            String propertyName = propertyInfo!=null?propertyInfo.getPropertyName():"";
            String propertyDisplay = propertyInfo!=null?propertyInfo.getPropertyDisplay():"";
            Object[] params = propertyInfo.getCodeTableParams();
            if(StringUtil.isNotEmpty(propertyName)){
                String val = Reader.stringReader().getValue(data,propertyName);
                if(StringUtil.isNotEmpty(val)){
                    boolean flag = false;           //导入数据不为空，且没有配置上，则返回false
                    String str = val.trim();
                    List<ICodeTable.IItem> itemList = getItemList(propertyName,params);
                    for(ICodeTable.IItem item : itemList){
                        if(item.getValue().equals(str)){
                            flag = true;
                        }else if(item.getName().equals(str)){
                            Writer.stringWriter().setValue(data,propertyName,item.getValue());
                            flag = true;
                        }
                    }
                    if(!flag){
                        messageList.add(propertyDisplay+" 数据字典项不匹配");
                    }
                }
            }
        }

        if(messageList.size()>0){
            return messageList.toArray(new String[0]);
        }else {
            return pass;
        }
    }

    private List<ICodeTable.IItem> getItemList(String perpertyName,Object[] params){
        List<ICodeTable.IItem> itemList = null;
        if(isCache) {
            if(cacheMap.containsKey(perpertyName)) {
                itemList = cacheMap.get(perpertyName);
            }else{
                if (params != null && params.length > 0) {
                    itemList = codeTable.getItems(params);
                } else {
                    itemList = codeTable.getItems(new Object[0]);
                }
                cacheMap.put(perpertyName,itemList);
            }
        }else{
            if(params!=null && params.length>0) {
                itemList = codeTable.getItems(params);
            }else {
                itemList = codeTable.getItems(new Object[0]);
            }
        }
        return itemList;
    }

}
