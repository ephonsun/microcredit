package banger.framework.web.json.parser;

import banger.framework.util.StringUtil;
import banger.framework.web.json.IJsonParser;
import banger.framework.web.json.JsonElement;

public class RequestJsonParser implements IJsonParser {
	private DefaultJsonParser jsonParser;
	private FormsJsonParser formsParser;
	
	public IJsonParser getJsonParser(){
		if(jsonParser==null)jsonParser=new DefaultJsonParser();
		return jsonParser;
	}
	
	public IJsonParser getFormsParser(){
		if(formsParser==null)formsParser=new FormsJsonParser();
		return formsParser;
	}
	
	@Override
	public JsonElement parser(String jsonString) {
		if(!StringUtil.isNullOrEmpty(jsonString)){
			if(jsonString.charAt(0)=='[' || jsonString.charAt(0)=='{'){
				return this.getJsonParser().parser(jsonString);
			}else{
				return this.getFormsParser().parser(jsonString);
			}
		}
		return null;
	}

}
