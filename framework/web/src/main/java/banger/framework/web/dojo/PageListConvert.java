package banger.framework.web.dojo;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.web.json.JsonArray;
import banger.framework.web.json.JsonElement;
import banger.framework.web.json.JsonObject;
import banger.framework.web.json.JsonValue;

import java.util.List;

public class PageListConvert extends AbstractConvert {

	@Override
	public Object toObject(Class<?> type, JsonElement json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonElement toJson(Object data) {
		if(data instanceof IPageList){
			IPageList<?> ps = (IPageList<?>)data;
			JsonObject jo = new JsonObject();
			jo.put("pagesize", this.getPageSizeJson(ps));
			jo.put("records", this.getListJson(ps));
			return jo;
		}
		return null;
	}
	
	/**
	 * 分页接口转JSON
	 * @param ps
	 * @return
	 */
	private JsonObject getPageSizeJson(IPageSize ps){
		JsonObject jo = new JsonObject();
		jo.put("page", ps.getPageNum());
		jo.put("size", ps.getPageSize());
		jo.put("count", ps.getTotal());
		return jo;
	}
	
	private JsonArray getListJson(List<?> list){
		JsonArray ja = new JsonArray();
		for(Object obj : list){
			IConvert convert = this.selector.getConvert(obj.getClass());
			JsonValue jv = (JsonValue)convert.toJson(obj);
			ja.getValues().add(jv);
		}
		return ja;
	}

	@Override
	public int order() {
		return 0;
	}

	@Override
	public boolean enableConvert(Class<?> type) {
		if(IPageList.class.isAssignableFrom(type)){
			return true;
		}
		return false;
	}

}
