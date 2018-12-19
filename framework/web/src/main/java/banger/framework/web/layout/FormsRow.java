package banger.framework.web.layout;

import java.util.ArrayList;
import java.util.List;

public class FormsRow {
	private List<FormsCell> cells;			//单元格集合
	private Forms forms;
	
	public FormsRow(Forms forms){
		this.forms = forms;
		this.cells = new ArrayList<FormsCell>();
	}
	
	public void addCell(FormsCell cell){
		this.cells.add(cell);
	}

	public List<FormsCell> getCells() {
		return cells;
	}

	public void setCells(List<FormsCell> cells) {
		this.cells = cells;
	}
	
	public int getColSpan(){
		int colspan = 0;
		for(FormsCell cell : this.cells){
			colspan += cell.getColspan();
		}
		return colspan;
	}
	
	public void stuffCell(){
		int colspan = this.forms.getColumns()*2-this.getColSpan();
		if(colspan>0){
			FormsCell cell = new FormsCell("place",this.forms,null);
			cell.setColspan(colspan);
			this.cells.add(cell);
		}
	}
}
