package banger.framework.web.dojo;

import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

import java.util.Collection;

public class CollectionConvert extends AbstractConvert {
	
	@Override
	public Object toObject(Class<?> clazz,JsonElement json){
		throw new DojoConvertException(StringUtil.format("目前不支持从Json到非泛型集合的转化  对像 {0} Json {1}", clazz.getName(), json));
	}

	@Override
	public boolean enableConvert(Class<?> clazz) {
		if(Collection.class.isAssignableFrom(clazz)){
			return true;
		}
		return false;
	}

	@Override
	public JsonElement toJson(Object data) {
		JsonArray ja = new JsonArray();
		Collection<?> collection = (Collection<?>)data;
		for(Object obj : collection){
			IConvert convert = this.selector.getConvert(obj.getClass());
			JsonValue jv = (JsonValue)convert.toJson(obj);
			ja.getValues().add(jv);
		}
		return ja;
	}

	@Override
	public int order() {
		return 2;
	}
}
