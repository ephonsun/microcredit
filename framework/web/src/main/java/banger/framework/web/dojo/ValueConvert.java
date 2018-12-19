package banger.framework.web.dojo;

import banger.framework.util.TypeUtil;
import banger.framework.web.json.*;

import java.util.Date;

public class ValueConvert extends AbstractConvert {

	public Object toObject(Class<?> type, String JsonString) {
		JsonElement json = this.parser.parser(JsonString);
		return this.toObject(type, json);
	}

	public Object toObject(Class<?> type, JsonElement json) {
		JsonValue jv = (JsonValue)json;
		switch (jv.getType()){
            case Null:
                return null;
            case Date: //JsonValueType
                {
                    JsonDate jd= (JsonDate)jv;
                    return TypeUtil.changeType(jd.getValue(), type);
                }
            case Boolean:	//JsonValueType
                {
                    JsonBoolean jb = (JsonBoolean)jv;
                    return TypeUtil.changeType(jb.getValue(), type);
                }
            case String:	//JsonValueType
                {
                    JsonString js = (JsonString)jv;
                    return TypeUtil.changeType(js.getValue(), type);
                }
            case Number:	//JsonValueType
                {
                    JsonNumber jn = (JsonNumber)jv;
                    return TypeUtil.changeType(jn.getValue(), type);
                }
            default:
            	return null;
        }
	}

	public String toJsonString(Object data) {
		return this.toJson(data).toString();
	}

	public JsonElement toJson(Object data) {
		if (data instanceof JsonValue) return (JsonValue)data;
        JsonValueType type = JsonType.getJsonType(data);
        switch (type)
        {
            case Null:
                return new JsonNull();
            case Date:
                return new JsonDate((Date)data);
            case Boolean:
                return new JsonBoolean((Boolean)data);
            case String:
                return new JsonString((String)data);
            case Number:
                return new JsonNumber(data.toString());
            case Object:
            case Array:
                {
                	String JsonString=(String)data;
					JsonValue v = (JsonValue)this.parser.parser(JsonString);
					return (v != null) ? v : new JsonString((String)data);   
                }
            default:
                return null;
        }
	}

	public int order() {
		return 0;
	}

	public boolean enableConvert(Class<?> type) {
		return TypeUtil.isValueType(type);
	}

}
