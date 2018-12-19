package banger.framework.web.json;

import banger.framework.util.StringUtil;

public class JsonPair extends JsonElement {
	private JsonString propertyName;
    private JsonValue propertyValue;

    public JsonString getPropertyName() {
		return propertyName;
	}


	public void setPropertyName(JsonString propertyName) {
		this.propertyName = propertyName;
	}


	public JsonValue getPropertyValue() {
		return propertyValue;
	}


	public void setPropertyValue(JsonValue propertyValue) {
		this.propertyValue = propertyValue;
	}


	public String getText()
    {
        return (this.getPropertyName() == null) ? "" : this.getPropertyName().getValue();
    }


    public JsonPair()
    {
        this.split = ":";
    }
    
    public JsonPair(String name,JsonValue jv)
    {
        this.split = ":";
        this.propertyName = new JsonString(name);
        this.propertyValue = jv;
    }

    @Override
	public String toString()
    {
    	String str=this.getPropertyName().toString()+this.getSplit()+this.getPropertyValue().toString();
        return this.format(str);
    }
    
    @Override
    public JsonElement contact(JsonElement element)
	{
    	if(element!=null)
    	{
    		if(element instanceof JsonPair)
    		{
    			JsonPair jp = (JsonPair)element;
    			if(this.getPropertyValue() instanceof JsonObject)
    			{
    				JsonObject jo = (JsonObject)this.getPropertyValue();
    				jo.getPairs().add(jp);
    			}
    			else
    			{
    				if(jp.getPropertyValue()!=null)this.setPropertyValue(jp.getPropertyValue());
    			}
    		}
    		else if(element instanceof JsonValue)
	    	{
	    		JsonValue jv = (JsonValue)element;
                this.setPropertyValue(jv);
	    	}
	    	else throw new JsonException(StringUtil.format("JsonPair只能拼接 JsonPair或JsonValue对像 {0}", element));
    	}
    	return this;
	}
}
