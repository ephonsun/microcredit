/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :wumh
 * Create Date:2013-12-30上午10:02:08
 */
package banger.common.tools;

import banger.framework.pagesize.IPageList;
import banger.framework.reflector.PropertyInfo;
import banger.framework.reflector.PropertyType;
import banger.framework.util.JsonDateValueProcessor;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.XSSFilterUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wumh
 * @version $Id: JsonUtil.java,v 0.1 2013-12-30 上午10:02:08 wumh Exp $
 */
public class JsonUtil {

	private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	public static JSONObject toJson(Object entity,String keyId,Map<String,String> colMap){
		PropertyInfo[] pInfos = ReflectorUtil.getProperties(entity.getClass());
		JSONObject json = new JSONObject();
		JSONObject jCol = new JSONObject();
		for(PropertyInfo pi : pInfos){
			if(!pi.getPropertyType().equals(PropertyType.Value)){
				continue;
			}
			if(pi.getName().equalsIgnoreCase(keyId)){
					json.put("id", ReflectorUtil.getPropertyValue(entity, pi.getGetMethod()));
			}
			if(colMap.containsKey(pi.getName().toLowerCase())){
                if(pi.getType().getName().toString().equalsIgnoreCase("java.util.Date")){
					if(ReflectorUtil.getPropertyValue(entity, pi.getGetMethod()) == null){
						jCol.put(pi.getName(), "");
					}else{
                    	jCol.put(pi.getName(), df.format(ReflectorUtil.getPropertyValue(entity, pi.getGetMethod())));
					}
                }else{
                	Object val = ReflectorUtil.getPropertyValue(entity, pi.getGetMethod()) == null?"":ReflectorUtil.getPropertyValue(entity, pi.getGetMethod());
                	if(val instanceof String){
                		if(!"cardInfo".equals(pi.getName()) && !"customerInfo".equals(pi.getName())){
                			String str = XSSFilterUtil.cleanXSS((String)val);
                    		jCol.put(pi.getName(), str);
                		}else{
                			jCol.put(pi.getName(), val);
                		}
                	}else{
                		jCol.put(pi.getName(), val);
                	}
                }
			}
		}
		json.put("cols", jCol);
		return json;
	}
	
	
	public static JSONObject toJson(List<?> objList,String keyId, String colNames){
		return listToJson(objList, keyId, colNames, DEFAULT_DATE_FORMAT);
	}
	
	public static JSONObject toJson(List<?> objList,String keyId, String colNames, String dateFormat){
		return listToJson(objList, keyId, colNames, dateFormat);
	}

	public static JSONObject jsonArrayToJson(JSONArray rows,int total){
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("page", 1);
		json.put("pageSize", 1);
		json.put("rows", jsonArray2JSONArray(rows,""));
		return json;
	}

	public static JSONArray jsonArray2JSONArray(JSONArray objList,String keyId){
		JSONArray rows=new JSONArray();

		for (int i = 0; i < objList.size(); i++) {
			JSONObject object = objList.getJSONObject(i);
			JSONObject ja = new JSONObject();
			ja.put("id",object.containsKey(keyId)?object.getString(keyId):"");
			ja.put("cols", object);
			rows.add(ja);
		}

		return rows;
	}

	
	private static JSONObject listToJson(List<?> objList, String keyId,String colNames,String dateFormat){
		df =  new SimpleDateFormat(dateFormat);
		JSONObject json = new JSONObject();
		json.put("total", 1);
		json.put("page", 1);
		json.put("pageSize", 1);
		JSONArray rows = list2JSONArray(objList, keyId, colNames);
		json.put("rows", rows);
		return json;
	}
	
	public static JSONObject toJson(IPageList<?> pageList, String keyId,String colNames){
		return pageListToJson(pageList, keyId, colNames,DEFAULT_DATE_FORMAT);
	}

	public static JSONObject toJson(IPageList<?> pageList, String keyId,String colNames,String dateFormat){
		return pageListToJson(pageList, keyId, colNames,dateFormat);
	}

	private static JSONObject pageListToJson(IPageList<?> pageList, String keyId,String colNames,String dateFormat){
		df =  new SimpleDateFormat(dateFormat);
		JSONObject json = new JSONObject();
		json.put("total", pageList.getTotal());
		json.put("page", pageList.getPageNum());
		json.put("pageSize", pageList.getPageSize());
		JSONArray rows = list2JSONArray(pageList, keyId, colNames);
		json.put("rows", rows);
		return json;
	}


	/**
	 * 页卡自动匹配数组组装
	 *
	 * @param dataList
	 * @param keyId
	 * @param colNames
	 * @return
	 */
	public static JSONArray toJsonArray(List<?> dataList,String keyId , String colNames){
	    JSONArray  json = new JSONArray();
	    json = list2JSONArray(dataList, keyId, colNames);
	    return json;
	}



	public static JSONArray list2JSONArray(List<?> objList,String keyId, String colNames){
		JSONArray rows=new JSONArray();
		Map<String,String> colMap = new HashMap<String, String>();
		if(!StringUtil.isNullOrEmpty(colNames) && colNames.contains(",")){
			for(String col : colNames.split(",")){
				colMap.put(col.toLowerCase(), null);
			}
		}else{
			colMap.put(colNames.toLowerCase(), null);
		}
		for (Object entity : objList) {
			JSONObject ja = toJson(entity, keyId,colMap);
			rows.add(ja);
		}
		return rows;
	}
	/**
	 * 格式化webservice jsonArray为页卡JSONArray格式
	 * @return
	 */
	public static String formatJSONArray(String jsonArray){
		if(StringUtil.isNullOrEmpty(jsonArray)||"000000".equals(jsonArray)){
			jsonArray = "[]";
		}
		JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
		JSONArray ja = JSONArray.fromObject(jsonArray,jsonConfig);
		
		JSONObject jo = new JSONObject();
		jo.put("total", ja.size());
		jo.put("page", 1);
		jo.put("pageSize", 1000);
		jo.put("creLineTotal", 0.00);
		
		JSONArray jRows = new JSONArray();
		for(int i=0;i<ja.size();i++){
			JSONObject jRow = new JSONObject();
			JSONObject jCols = ja.getJSONObject(i);
			jRow.put("id", "");
			jRow.put("cols", jCols);
			jRows.add(jRow);
		}
		jo.put("rows", jRows);
		return jo.toString();
	}
	
}
