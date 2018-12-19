package banger.framework.web.dojo;

import banger.framework.entity.IPersistEntity;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

public class PersistEntityConvert extends ObjectConvert {

	public Object toObject(Class<?> type, String JsonString) {
		JsonElement json = this.parser.parser(JsonString);
		return this.toObject(type, json);
	}

	public Object toObject(Class<?> type, JsonElement json) {
		if(IPersistEntity.class.isAssignableFrom(type)){
			if(json instanceof JsonObject){
				JsonObject jo = (JsonObject)json;
				IPersistEntity<?> persist = (IPersistEntity<?>) ReflectorUtil.newInstance(type);

				for(JsonPair jp : jo.getPairs()){
					String propertyName = jp.getPropertyName().getValue();
					if(persist.contains(propertyName)){
						JsonValue jv = jo.get(propertyName);
						Class<?> valType = persist.getPropertyType(propertyName);
						IConvert convert = this.selector.getConvert(valType);
						Object val = convert.toObject(valType,jv);
						persist.setPropertyValue(propertyName,val);
					}
				}
				
				return persist;
			}
			else throw new DojoConvertException(StringUtil.format("Json转化为对像时出错 对像类型{0} Json字符串 {1}", type.getName(), json));
		}
		else return super.toObject(type, json);
	}

	public String toJsonString(Object data) {
		return this.toJson(data).toString();
	}

	public JsonElement toJson(Object data) {
		if(data instanceof IPersistEntity){
			IPersistEntity<?> persist = (IPersistEntity<?>)data;
			JsonObject jo = new JsonObject();
			
			//String[] propertyNames = persist.getPropertyNames();
			String[] propertyNames = new String[0];
			for(String propertyName : propertyNames){
				if(!StringUtil.isNullOrEmpty(propertyName)){
					JsonPair jp = new JsonPair();
					jp.setPropertyName(new JsonString(propertyName));
					Object val = persist.getPropertyValue(propertyName);
					if(val!=null){
						IConvert convert = this.getSelector().getConvert(val.getClass());
						JsonValue jv = (JsonValue)convert.toJson(val);
						jp.setPropertyValue(jv);
					}else jp.setPropertyValue(new JsonNull());
					jo.getPairs().add(jp);
				}
			}

			return jo;
		}
		else return super.toJson(data);
	}

	public int order() {
		return 98;
	}

	public boolean enableConvert(Class<?> type) {
		if(IPersistEntity.class.isAssignableFrom(type))return true;
		else return super.enableConvert(type);
	}

}
