package banger.framework.web.dojo;

import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

import java.util.HashMap;
import java.util.Map;

public class MapConvert extends AbstractConvert {

	@Override
	public Object toObject(Class<?> clazz, JsonElement json) {
		if(json instanceof JsonObject){
			JsonObject jo = (JsonObject)json;
			Map<Object,Object> map = new HashMap<Object,Object>();
			for(JsonPair jp : jo.getPairs()){
				String propertyName = jp.getPropertyName().getValue();
				JsonValue jv = jo.get(propertyName);
				if(jv.getType()!=JsonValueType.Null){
					Class<?> valType = JsonType.getValueType(jv);
					IConvert convert = this.selector.getConvert(valType);
					Object val = convert.toObject(valType,jv);
					map.put(propertyName,val);
				}
			}
			return map;
		}
		else throw new DojoConvertException(StringUtil.format("Json转化为对像时出错 对像类型{0} Json字符串 {1}", clazz.getName(), json));
	}

	@Override
	public JsonElement toJson(Object data) {
		JsonObject jo = new JsonObject();
		@SuppressWarnings("rawtypes")
		Map map = (Map)data;
		for(Object key : map.keySet()){
			JsonPair jp = new JsonPair();
			jp.setPropertyName(new JsonString(key.toString()));
			Object val = map.get(key);
			if(val!=null){
				IConvert convert = this.selector.getConvert(val.getClass());
				JsonValue jv = (JsonValue)convert.toJson(val);
				jp.setPropertyValue(jv);
				jo.getPairs().add(jp);
			}else{
				jp.setPropertyValue(new JsonNull());
				jo.getPairs().add(jp);
			}
		}
		return jo;
	}

	@Override
	public boolean enableConvert(Class<?> clazz) {
		if(Map.class.isAssignableFrom(clazz))return true;
		else return false;
	}

	@Override
	public int order() {
		return 0;
	}

}
