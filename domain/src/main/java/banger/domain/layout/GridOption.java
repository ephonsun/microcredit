package banger.domain.layout;

import java.util.ArrayList;
import java.util.List;

public class GridOption {
	private String title;			//标题
	private String html;			//网页内容
	private String script;			//脚本
	private Integer width;			//列宽
	private boolean hide;			//是否隐藏
	private List<GridOptionItem> items;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}
	
	public List<GridOptionItem> getItems() {
		return items;
	}

	public void setItems(List<GridOptionItem> items) {
		this.items = items;
	}

	public GridOption(){
		this.items = new ArrayList<GridOptionItem>();
	}
	
	public void clear(){
		this.items.clear();
	}
	
	public void add(GridOptionItem item){
		this.items.add(item);
	}
	
}
