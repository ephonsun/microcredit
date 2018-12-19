package banger.framework.web.json.parser;

import banger.framework.util.StringUtil;
import banger.framework.web.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 把网页提交上来的表单内容解析成JSON对像
 * @author ZHUSW
 *
 */
public class FormsJsonParser implements IJsonParser {
	
	public JsonElement parser(String jsonString) {
		return new JsonContext(jsonString).getRoot();
	}
	
	static class JsonContext{
		private List<JsonPart> parts;
		private JsonElement root;

		public JsonElement getRoot() {
			return root;
		}

		public void setRoot(JsonElement root) {
			this.root = root;
		}

		public JsonContext(String content){
			if(!StringUtil.isNullOrEmpty(content)){
				String text = content.trim();
				this.root = new JsonObject();				//表单提交最外层必需为对像
				
				String[] strs = text.split("&",-1);
				this.parts = new ArrayList<JsonPart>();
				
				for(String str : strs){
					this.parts.add(new JsonPart(str));
				}
				
				for(JsonPart part : this.parts){
					this.parseJsonByPaths(part);
				}
			}
		}
		
		/**
		 * 根据路径查找并创建JSON对像
		 * @param path
		 * @return
		 */
		private JsonElement parseJsonByPaths(JsonPart part){
			int index = 0;
			JsonObject jo = (JsonObject)this.root;
			JsonValue jv = jo.get(part.getParam());
			if(jv==null){
				jv = createJsonByChild(part,index);
				jo.put(part.getParam(),jv);
			}
			
			JsonElement je = jv;
			while(index<part.getPaths().length){
				je = parseJsonByPathPart(part,index,je);
				index++;
			}
			return je;
		}
		
		/**
		 * 根据
		 * @param pathPart
		 * @param parent
		 * @return
		 */
		private JsonElement parseJsonByPathPart(JsonPart part,int index,JsonElement parent){
			JsonElement je = null;
			String path = part.getPaths()[index];
			boolean isArray = StringUtil.isNumber(path);
			if(parent!=null){
				if(isArray){
					JsonArray ja = (JsonArray)parent;
					int arrayIndex = Integer.parseInt(path);
					if(arrayIndex==ja.size()){
						ja.add(createJsonByChild(part,index+1));
					}
					je = ja.get(arrayIndex);
				}else{
					JsonObject jo = (JsonObject)parent;
					JsonValue jv = jo.get(path);
					if(jv==null){
						jv = createJsonByChild(part,index+1);
						jo.put(path,jv);
					}
					je = jv;
				}
			}else{
				System.out.print(123);
			}
			
			return je;
		}
		
		/**
		 *  创建JSON对像
		 * @param part
		 * @param index
		 * @return
		 */
		private JsonValue createJsonByChild(JsonPart part,int index){
			if(index<part.getPaths().length){
				String path = part.getPaths()[index];
				if(StringUtil.isNumber(path))return new JsonArray();
				else return new JsonObject();
			}else{
				return new JsonString(part.getValue());
			}
		}

	}
	
	static class JsonPart {
		private String[] paths;
		private String param;
		private String value;
		private String part;
		
		public String getValue() {
			return value;
		}

		public String getParam() {
			return param;
		}

		public String[] getPaths(){
			return this.paths;
		}
		
		public JsonPart(String part){
			int n = part.indexOf("=");
			this.part = part.substring(0,n);
			int m = this.part.indexOf("[");
			if(m>0){
				this.param = this.part.substring(0,m);
				String paramStr = this.part.substring(m,n);
				String[] pStrs = paramStr.split("]");
				int len = "".equals(pStrs[pStrs.length-1])?pStrs.length-1:pStrs.length;
				this.paths = new String[len];
				for(int i=0;i<len;i++){
					this.paths[i] = pStrs[i].substring(1);
				}
			}else{
				this.param = this.part.substring(0,n);
				this.paths = new String[0];
			}
			this.value = part.substring(n+1);
		}
	}
}
