package banger.framework.web.json;

import banger.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonValue {
	private List<JsonValue> values;
	
	public int size(){
		return this.values.size();
	}
	
	public List<JsonValue> getValues() {
		return values;
	}

	public JsonValue get(int index){
		if(index>-1 && index<this.values.size()){
			return this.values.get(index);
		}
		return null;
	}
	
	public JsonArray()
    {
		this.type = JsonValueType.Array;
        this.values = new ArrayList<JsonValue>();
        this.openQuote = "[";
        this.closeQuote = "]";
        this.split = ",";
    }
	
	public JsonArray(JsonValue jv)
	{
		this(new JsonValue[]{jv});
	}
	
	public JsonArray(JsonValue[] jvs){
		this();
		if(jvs!=null)
		{
			for(JsonValue jv : jvs)
			{
				if(jv!=null)this.getValues().add(jv);
			}
		}
	}
	
	public void add(JsonValue value){
		this.values.add(value);
	}
	
	public void add(String value){
		this.add(new JsonString(value));
	}
	
	public void add(Number value){
		this.add(new JsonNumber(value));
	}
	
	public JsonArray contact(JsonArray ja){
		for(JsonValue jv : ja.getValues()){
			this.getValues().add(jv);
		}
		return this;
	}
	
	@Override
	public String toString()
    {
		StringBuffer sb = new StringBuffer();
        sb.append(this.getOpenQuote());
        boolean isFirst = true;
        for(int i=0;i<this.values.size();i++)
        {
        	JsonValue val = this.get(i);
            if (isFirst) sb.append(val.toString());
            else sb.append(this.getSplit()+val.toString());
            isFirst = false;
        }
        sb.append(this.getCloseQuote());
        return this.format(sb.toString());
    }
	
	@Override
    public JsonElement contact(JsonElement element)
	{
    	if(element!=null)
    	{
	    	if(element instanceof JsonValue)
	    	{
	    		JsonValue jv = (JsonValue)element;
                this.getValues().add(jv);
	    	}
	    	else throw new JsonException(StringUtil.format("JsonObject只能拼接 JsonValue对像 {0}", element));
    	}
    	return this;
	}
}
