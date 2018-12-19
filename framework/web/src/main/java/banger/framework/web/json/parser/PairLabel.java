package banger.framework.web.json.parser;

import banger.framework.web.json.JsonElement;
import banger.framework.web.json.JsonPair;
import banger.framework.web.json.JsonString;
import banger.framework.web.json.JsonValue;

public class PairLabel extends JsonPair implements IJsonLabel {
	private IJsonLabel parent;
    private int startIndex;
    private int endIndex;
    private JsonLabelRoot root;
    
    @Override
	public IJsonLabel getParent() {
		return parent;
	}
	@Override
	public void setParent(IJsonLabel parent) {
		this.parent = parent;
	}
	public PairLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }
	@Override
	public void addChild(IJsonLabel child)
	{
		if (this.getPropertyName() == null)
        {
            JsonString js = (JsonString)child.getJson();
            if (js != null)
            {
                this.setPropertyName(js);
                child.setParent(this);
            }
            else throw new RuntimeException("JsonPair属性类型为JsonString 当前Json["+child.getJson().toString()+"]");
        }
        else
        {
            JsonValue jv = (JsonValue)child.getJson();
            if (jv != null)
            {
                this.setPropertyValue(jv);
                child.setParent(this);
            }
            else throw new RuntimeException("JsonPair值类型为JsonValue 当前Json["+child.getJson().toString()+"]");
        }
	}

	@Override
	public JsonLabelType getCurrentChildType()
    {
        if (this.getPropertyName() == null) return JsonLabelType.String;
        else if (this.getPropertyValue() == null) return JsonLabelType.Value;
        else return JsonLabelType.Undefine;
    }

	@Override
	public void close(int endIndex) {
		this.endIndex = endIndex;
	}
	
	@Override
	public void open(int startIndex) {
		this.startIndex = startIndex;
	}
	
	@Override
	public int getStartIndex() {
		return this.startIndex;
	}

	@Override
	public int getEndIndex() {
		return this.endIndex;
	}

	@Override
	public JsonElement getJson() {
		return this;
	}

	@Override
	public String getJsonString() {
		if (this.getRoot() != null)
        {
            return this.getRoot().getJsonString().substring(this.getStartIndex(), this.getEndIndex());
        }
        else return "";
	}
	
	@Override
	public void setRoot(JsonLabelRoot root) {
		this.root=root;
	}

	@Override
	public JsonLabelRoot getRoot() {
		return this.root;
	}

	@Override
	public int length() {
		return this.getStartIndex() - this.getEndIndex();
	}
	
	@Override
	public boolean closeEnable(int endIndex) {
		return true;
	}
}
