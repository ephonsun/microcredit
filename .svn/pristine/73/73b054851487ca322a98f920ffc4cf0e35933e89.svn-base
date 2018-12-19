package banger.domain.layout;

import banger.framework.util.StringUtil;

public class GridOptionItem {
	private String title;				//标题
	private String id;					//ID
	private String script;				//脚本
	private String hide;				//是否隐藏
	private String disable;				//是否禁用
	private String function;			//脚本函数名
	private String type;				//功能类型
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getScriptString(){
		if(!StringUtil.isNullOrEmpty(this.script)){
			String str = this.script.replaceAll("\\\\n","\n").replaceAll("\\\\r","\r").replaceAll("\\\\t","\t");
			return str;
		}
		return "";
	}
	
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
		this.hide = hide;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
