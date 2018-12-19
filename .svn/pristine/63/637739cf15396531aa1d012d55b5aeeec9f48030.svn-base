package banger.framework.util;

import java.util.HashMap;
import java.util.Map;

import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.reader.Reader;
import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;

public class MenuFilterUtil {
	private static Map<String,String> menuMap;

	public static String getMenuIdByUrl(String url){
		if(menuMap==null){
			menuMap = getAllMenuSet();
		}
		if(menuMap.containsKey(url)){
			return menuMap.get(url);
		}
		return null;
	}
	
	private static Map<String,String> getAllMenuSet(){
		Map<String,String> map = new HashMap<String,String>();
		ISqlEngine ise =  SqlEngine.instance();
        DataTable table = ise.getDataTable("getAllMenu",new Object[0]);
        if(table!=null && table.rowSize()>0){
    		for(DataRow row : table){
    			String menuId = Reader.stringReader().getValue(row, "MENU_ID");
    			String menuUrl = Reader.stringReader().getValue(row, "MENU_URL");
    			if(StringUtil.isNotEmpty(menuId) && StringUtil.isNotEmpty(menuUrl)){
    				String url = menuUrl.replace("..", "");
    				map.put(url, menuId);
    			}
    		}
        }
        return map;
	}
	
}
