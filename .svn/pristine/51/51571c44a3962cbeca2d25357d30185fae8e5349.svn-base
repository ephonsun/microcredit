package banger.framework.web.json.parser;

import banger.framework.web.json.IJsonParser;
import banger.framework.web.json.JsonElement;
import banger.framework.web.json.JsonParserException;

public class DefaultJsonParser implements IJsonParser {
    
	public JsonElement parser(String JsonString){
		
		try{
	    	LabelBlock block = new LabelBlock();
	        block.clear();
	        block.setJsonString(JsonString);
	
	        for(int i=0;i<JsonString.length();i++){
	        	char c=JsonString.charAt(i);
	            block.read(c);
	        }
	
	        return block.getResult();
		}catch(Exception e){
			throw new JsonParserException("解析json字符串出错 "+JsonString,e);
		}
    }
	
}
