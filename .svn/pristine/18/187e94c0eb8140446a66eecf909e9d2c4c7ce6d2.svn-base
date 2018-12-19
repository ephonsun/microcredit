package banger.framework.web.dojo;

import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

import java.lang.reflect.Array;

public class ArrayConvert extends AbstractConvert {
	
	@Override
	public Object toObject(Class<?> clazz, JsonElement json) {
		JsonElement je = (json instanceof JsonPair)?((JsonPair)json).getPropertyValue():json;
		if(je instanceof JsonArray)
		{
			JsonArray ja = (JsonArray)je;
			Class<?> cType = clazz.getComponentType();
			if(cType!=null)
			{
				int size = ja.getValues().size();
				Object[] objs = (Object[])Array.newInstance(cType,size);
				IConvert convert = this.selector.getConvert(cType);
				for(int i=0;i<size;i++)
				{
					JsonValue jv = ja.get(i);
					objs[i] = convert.toObject(cType,jv);
				}
				return objs;
			}
			else throw new DojoConvertException(StringUtil.format("找不到该数组的类型 {0}", clazz.getName()));
		}
		else throw new DojoConvertException(StringUtil.format("Json转化为集合对像时出错 对像类型{0} Json字符串 {1}", clazz.getName(), json));
	}

	@Override
	public JsonElement toJson(Object data) {
		JsonArray ja = new JsonArray();
		Object[] objs = (Object[])data;
		for(int i=0;i<objs.length;i++){
			Object obj = objs[i];
			IConvert convert = this.selector.getConvert(obj.getClass());
			JsonValue jv = (JsonValue)convert.toJson(obj);
			ja.getValues().add(jv);
		}
		return ja;
	}

	@Override
	public boolean enableConvert(Class<?> clazz) {
		if(clazz.isArray())
			return true;
		else return false;
	}

	@Override
	public int order() {
		return 0;
	}
}
