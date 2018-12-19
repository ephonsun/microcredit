package banger.domain.layout;

import java.util.ArrayList;
import java.util.List;

public class CodeTable {
	private String id;			//ID
	private String name;		//名称
	private String type;		
	private String sql;			//SQL语句
	private List<Item> items;
	
	public CodeTable(){
		this.id = "";
		this.name = "";
		this.sql = "";
		this.items = new ArrayList<Item>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(String value,String text){
		Item item = new Item();
		item.setValue(value);
		item.setText(text);
		this.items.add(item);
	}

	public static class Item{
		private String value;
		private String text;
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}

}
