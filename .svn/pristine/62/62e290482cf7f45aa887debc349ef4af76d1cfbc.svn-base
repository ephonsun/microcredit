package banger.framework.web.json;

public class JsonBoolean extends JsonValue {
	private boolean b;

    public boolean getValue()
    {
    	return b;
    }
    
    public void setValue(boolean bool)
    {
    	b=bool;
    }

    public JsonBoolean()
    {
        this.type = JsonValueType.Boolean;
    }

    public JsonBoolean(boolean f)
    {
        this.b = f;
        this.type = JsonValueType.Boolean;
    }

    @Override
	public String toString()
    {
    	String str=(b)?"true":"false";
        return this.format(str);
    }
}
