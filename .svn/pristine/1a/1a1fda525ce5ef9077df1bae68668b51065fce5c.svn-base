package banger.domain.layout;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	private String id;
	private String name;
	private List<GridColumn> columns;
	private GridOption option;

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

	public List<GridColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<GridColumn> columns) {
		this.columns = columns;
	}
	
	public Grid(){
		this.columns = new ArrayList<GridColumn>();
	}
	
	public void addColumn(GridColumn column){
		this.columns.add(column);
	}
	
	public GridOption getOption() {
		return option;
	}

	public void setOption(GridOption option) {
		this.option = option;
	}

	public void clear(){
		this.columns.clear();
	}
	
}
