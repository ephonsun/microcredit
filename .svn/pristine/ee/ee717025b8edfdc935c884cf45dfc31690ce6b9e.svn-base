package banger.domain.layout;

import java.util.ArrayList;
import java.util.List;

public class Page extends Element {
	private String title;
	private List<String> links;
	private List<String> scripts;
	
	public Page(){
		this.links = new ArrayList<String>();
		this.scripts = new ArrayList<String>();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(display!=null && !"".equals(display))this.display=title;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

	public List<String> getScripts() {
		return scripts;
	}

	public void setScripts(List<String> scripts) {
		this.scripts = scripts;
	}
	
	
}
