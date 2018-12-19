package banger.framework.web.json;

import banger.framework.util.StringUtil;

public abstract class JsonElement implements IJsonElement {
	protected String openQuote;
    protected String closeQuote;
    protected String split;
    protected String format;
    
    public JsonElement()
    {
    	this.openQuote = "";
    	this.closeQuote = "";
    	this.split = "";
    	this.format = "";
    }
    
	@Override
	public String getCloseQuote() {
		return this.closeQuote;
	}

	@Override
	public String getOpenQuote() {
		return this.openQuote;
	}

	@Override
	public String getSplit() {
		return this.split;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String format(String str)
	{
		return str;
	}
	
	public JsonElement contact(JsonElement element)
	{
		if(element!=null)throw new JsonException(StringUtil.format("不能拼连该Json对像 {0}", element));
		return this;
	}
}
