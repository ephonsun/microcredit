package banger.domain.layout;

import java.util.ArrayList;
import java.util.List;

public class Forms {
	private String id;			
	private String name;		//
	private Integer cols;		//列数
	private List<FormsCell> cells;	//格元格
	
	public Forms(){
		this.cells = new ArrayList<FormsCell>();
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

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public List<FormsCell> getCells() {
		return cells;
	}

	public void setCells(List<FormsCell> cells) {
		this.cells = cells;
	}
	
	public void add(FormsCell cell){
		this.cells.add(cell);
	}
	
	/**
	 * 清除
	 */
	public void clear(){
		this.cells.clear();
	}
	
}
