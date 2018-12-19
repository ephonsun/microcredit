package banger.domain.layout;

import banger.framework.util.StringUtil;

public class Button {
	private String id;				//ID
	private boolean hide;			//是否显示
	private String title;			//名称
	private String script;			//脚本
	private String function;		//函数名
	private String type;			//操作类型
	private String skin;			//皮肤样式
	private String html;			//网页内容
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean getHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScript() {
		return script;
	}
	public String getScriptString(){
		if(!StringUtil.isNullOrEmpty(this.script)){
			String str = this.script.replaceAll("\\\\n","\n").replaceAll("\\\\r","\r").replaceAll("\\\\t","\t");
			return str;
		}
		return "";
	}
	public void setScript(String script) {
		this.script = script;
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
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getHtml() {
		return html;
	}
	public String getHtmlString(){
		if(!StringUtil.isNullOrEmpty(this.html)){
			String str = this.html.replaceAll("\\\\n","\n").replaceAll("\\\\r","\r").replaceAll("\\\\t","\t");
			return str;
		}
		return "";
	}
	public void setHtml(String html) {
		this.html = html;
	}
}
