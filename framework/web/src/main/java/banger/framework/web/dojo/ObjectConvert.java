package banger.framework.web.dojo;

import banger.framework.reflector.PropertyInfo;
import banger.framework.reflector.PropertyType;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

import java.lang.reflect.Constructor;

public class ObjectConvert extends AbstractConvert {

	public Object toObject(Class<?> type, String JsonString) {
		JsonElement json = this.parser.parser(JsonString);
		return this.toObject(type, json);
	}

	public Object toObject(Class<?> type, JsonElement json) {
		if(json instanceof JsonObject){
			JsonObject jo = (JsonObject)json;
			Object obj = ReflectorUtil.newInstance(type);
			for(JsonPair jp : jo.getPairs()){
				String propertyName = jp.getPropertyName().getValue();
				PropertyInfo pi = ReflectorUtil.getProperty(type, propertyName);
				if(pi!=null && pi.getSetMethod()!=null){
					JsonValue jv = jo.get(propertyName);
					if(pi.getPropertyType() == PropertyType.Value){
						if(jv.getType()!=JsonValueType.Null){
							Class<?> valType = JsonType.getValueType(jv);
							IConvert convert = this.selector.getConvert(valType);
							Object val = convert.toObject(valType,jv);
							ReflectorUtil.setPropertyValue(obj, pi.getSetMethod(), val);
						}
					}else if(pi.getPropertyType() == PropertyType.Collection || pi.getPropertyType() == PropertyType.Object){
						Class<?> valType = pi.getType();
						IConvert convert = this.selector.getConvert(valType);
						Object val = convert.toObject(valType,jv);
						ReflectorUtil.setPropertyValue(obj, pi.getSetMethod(), val);
					}
				}
			}
			return obj;
		}
		else throw new DojoConvertException(StringUtil.format("Json转化为对像时出错 对像类型{0} Json字符串 {1}", type.getName(), json));
	}

	public String toJsonString(Object data) {
		return this.toJson(data).toString();
	}

	public JsonElement toJson(Object data) {
		PropertyInfo[] pis = ReflectorUtil.getProperties(data.getClass());
		JsonObject jo = new JsonObject();
		for(PropertyInfo pi : pis){
			if(pi.getGetMethod()!=null){
				JsonPair jp = new JsonPair();
				jp.setPropertyName(new JsonString(pi.getName()));
				Object val = ReflectorUtil.getPropertyValue(data, pi.getGetMethod());
				if(val!=null){
					IConvert convert = this.selector.getConvert(val.getClass());
					JsonValue jv = (JsonValue)convert.toJson(val);
					jp.setPropertyValue(jv);
				}else{
					if(pi.getType().equals(String.class)){
						jp.setPropertyValue(new JsonString(""));
					}else{
						jp.setPropertyValue(new JsonNull());
					}
				}
				jo.getPairs().add(jp);
			}
		}
		return jo;
	}

	public int order() {
		return Integer.MAX_VALUE;
	}
	
	public boolean enableConvert(Class<?> type) {
		boolean flag = false;
		if(type.getName().indexOf("banger")>-1){
			@SuppressWarnings("rawtypes")
			Constructor[] ctors = type.getConstructors();
	        for (int i = 0; i < ctors.length; i++){
	        	if(ctors[i].toString().indexOf("()")>-1)flag=true;
	        }
		}

		return flag;
	}
	
}
