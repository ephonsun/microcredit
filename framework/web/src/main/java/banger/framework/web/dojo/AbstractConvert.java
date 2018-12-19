package banger.framework.web.dojo;

import banger.framework.web.json.IJsonParser;
import banger.framework.web.json.JsonElement;

public abstract class AbstractConvert implements IConvert,Comparable<AbstractConvert> {
	protected IJsonParser parser;
	protected IConvertSelector selector;
	
	public IJsonParser getParser() {
		return parser;
	}

	public void setParser(IJsonParser parser) {
		this.parser = parser;
	}
	
	public IConvertSelector getSelector() {
		return selector;
	}

	public void setSelector(IConvertSelector selector) {
		this.selector = selector;
	}
	
	public Object toObject(Class<?> type, String JsonString) {
		JsonElement json = this.parser.parser(JsonString);
		return this.toObject(type, json);
	}
	
	public String toJsonString(Object data) {
		return this.toJson(data).toString();
	}

	public abstract int order();
	
	public abstract boolean enableConvert(Class<?> type);
	
	public int compareTo(AbstractConvert o) {
		if(this.order()>o.order())
			return 1;
		else if(this.order() == o.order())
			return 0;
		else return -1;
	}
}
