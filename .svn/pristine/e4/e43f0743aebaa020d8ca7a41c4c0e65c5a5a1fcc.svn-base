package banger.framework.web.json.parser;

import banger.framework.web.json.JsonElement;
import banger.framework.web.json.JsonNull;

public class NullLabel extends JsonNull implements IJsonLabel{

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
	public NullLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }
	
	@Override
	public void addChild(IJsonLabel child) throws RuntimeException {
		throw new RuntimeException("JsonNull不支持添加子对像 当前Json["+child.getJson().toString()+"]");
	}

	@Override
	public JsonLabelType getCurrentChildType() {
		return JsonLabelType.Undefine;
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
