package banger.framework.web.json;

import banger.framework.util.StringUtil;

import java.util.Map;

public class JsonObject extends JsonValue {
	private JsonPairCollection pairs;

    public JsonValue get(String name){
    	if (!pairs.contains(name)) return null;
        else return pairs.get(name).getPropertyValue();
    }
    
    public void put(String name,JsonValue value){
    	if(!pairs.contains(name)){
            JsonPair pair = pairs.add(name, value);
            pair.setPropertyValue(value);
        }
        pairs.get(name).setPropertyValue(value);
    }
    
    public void put(String name,String value){
    	this.put(name, new JsonString(value));
    }
    
    public void put(String name,Number value){
    	this.put(name, new JsonNumber(value));
    }
    
    public void put(Map<String,Object> map){
    	for(String key : map.keySet()){
    		Object value = map.get(key);
    		if(value instanceof String){
    			this.put(key,(String)value);
    		}else if(value instanceof Number){
    			this.put(key,(Number)value);
    		}
    	}
    }
    
    public JsonPairCollection getPairs()
    {
        return this.pairs;
    }

    public JsonObject()
    {
        this.pairs = new JsonPairCollection();
        this.type = JsonValueType.Object;
        this.openQuote="{";
        this.closeQuote="}";
        this.split = ",";
    }

    @Override
	public String toString()
    {
    	StringBuffer sb=new StringBuffer();
        sb.append(this.getOpenQuote());
        boolean isFirst=true;
        JsonPair[] jps=this.pairs.getPairs();
        for(int i=0;i<jps.length;i++)
        {
        	JsonPair pair=jps[i];
            if (isFirst) sb.append(pair.toString());
            else sb.append(this.getSplit()+pair.toString());
            isFirst=false;
        }
        sb.append(this.getCloseQuote());
        return this.format(sb.toString());
    }
    
    @Override
    public JsonElement contact(JsonElement element)
	{
    	if(element!=null)
    	{
	    	if(element instanceof JsonPair)
	    	{
	    		JsonPair jp = (JsonPair)element;
                this.getPairs().add(jp);
	    	}
	    	else throw new JsonException(StringUtil.format("JsonObject只能拼接 JsonPair对像 {0}", element));
    	}
    	return this;
	}
}
