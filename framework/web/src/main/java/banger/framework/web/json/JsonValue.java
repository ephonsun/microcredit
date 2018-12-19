package banger.framework.web.json;

public class JsonValue extends JsonElement {
	protected JsonValueType type;
	
	public JsonValueType getType()
	{
		return this.type;
	}
	
	public JsonValue()
	{
		this.type= JsonValueType.Undefine;
	}
}
