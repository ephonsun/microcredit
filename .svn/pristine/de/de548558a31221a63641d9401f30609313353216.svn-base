package banger.framework.web.dojo;

import banger.framework.web.json.JsonElement;

public interface IConvert {
	
	/**
	 * json字符串转化为数据对像
	 * @param clazz
	 * @param JsonString
	 * @return
	 */
	Object toObject(Class<?> type, String JsonString);
	
	/**
	 * json字符对像为数据对像
	 * @param clazz
	 * @param json
	 * @return
	 */
	Object toObject(Class<?> type, JsonElement json);
	
	/**
	 * 数据对像转化为json字符串
	 * @param data
	 * @return
	 */
	String toJsonString(Object data);
	
	/**
	 * 数据对像转化为json对像
	 * @param data
	 * @return
	 */
	JsonElement toJson(Object data);
	
}
