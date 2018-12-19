package banger.framework.web.dojo;

import banger.framework.util.StringUtil;
import banger.framework.web.json.JsonElement;

import java.util.*;

/**
 * Json和数据之间的转化
 */
public class DojoConvert implements IConvert,IConvertSelector {
	private Map<Class<?>,IConvert> convertMap;
	private List<AbstractConvert> converts;
	private boolean sorted = false;
	
	public List<AbstractConvert> getConverts() {
		return converts;
	}

	public void setConverts(List<AbstractConvert> converts) {
		this.converts = converts;
	}

	public DojoConvert(){
		this.convertMap = new HashMap<Class<?>,IConvert>();
		this.converts = new ArrayList<AbstractConvert>();
	}
	
	public Object toObject(Class<?> type, String JsonString) {
		if(!StringUtil.isNullOrEmpty(JsonString)){
			return this.getConvert(type).toObject(type, JsonString);
		}
		return null;
	}

	public Object toObject(Class<?> type, JsonElement json) {
		if(json!=null){
			return this.getConvert(type).toObject(type, json);
		}
		return null;
	}

	public String toJsonString(Object data) {
		if(data!=null){
			return this.getConvert(data.getClass()).toJsonString(data);
		}
		return "";
	}

	public JsonElement toJson(Object data) {
		if(data!=null){
			return this.getConvert(data.getClass()).toJson(data);
		}
		return null;
	}
	
	private List<AbstractConvert> getSortConverts(){
		if(!this.sorted){
			this.sorted = true;
			for(AbstractConvert convert : this.converts)convert.setSelector(this);
			Collections.sort(this.converts);
		}
		return this.converts;
	}
	
	public IConvert getConvert(Class<?> type) {
		if(convertMap.containsKey(type)){
			return convertMap.get(type);
		}
		else{
			if(this.converts.size()>0){
				for(AbstractConvert convert : this.getSortConverts()){
					if(convert.enableConvert(type)){
						convertMap.put(type,convert);
						return convert;
					}
				}
				if(!convertMap.containsKey(type))
					throw new DojoConvertException(StringUtil.format("找不到该类型 {0} 的dojo转化器 ", type.getName()));
				else return convertMap.get(type);
			}
			else throw new DojoConvertException("dojo转化器没有配置");
		}
	}

}
