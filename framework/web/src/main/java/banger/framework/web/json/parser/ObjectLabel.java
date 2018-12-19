package banger.framework.web.json.parser;

import banger.framework.web.json.JsonElement;
import banger.framework.web.json.JsonObject;
import banger.framework.web.json.JsonPair;

import java.util.ArrayList;
import java.util.List;


public class ObjectLabel extends JsonObject implements IJsonLabel {
	private IJsonLabel parent;
    private int startIndex;
    private int endIndex;
    private JsonLabelRoot root;
    private List<JsonPair> tempPairs;
    
    @Override
	public IJsonLabel getParent() {
		return parent;
	}
	@Override
	public void setParent(IJsonLabel parent) {
		this.parent = parent;
	}
	public ObjectLabel()
    {
        this.startIndex = 0;
        this.endIndex = 0;
    }
	@Override
	public void addChild(IJsonLabel child)
	{
		JsonPair jp=(JsonPair)child.getJson();
        if (jp != null)
        {
            this.tempPairs.add(jp);
            child.setParent(this);
        }
        else throw new RuntimeException("JsonObject只能添加JsonPair 当前Json["+child.getJson()+"]");
	}

	@Override
	public JsonLabelType getCurrentChildType() {
		return JsonLabelType.Pair;
	}

	@Override
	public void close(int endIndex) {
		this.endIndex = endIndex;
		for(int i=0;i<this.tempPairs.size();i++)
		{
			this.getPairs().add(this.tempPairs.get(i));
		}
	}
	
	@Override
	public void open(int startIndex) {
		this.startIndex = startIndex;
		this.tempPairs = new ArrayList<JsonPair>();
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
