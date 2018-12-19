package banger.framework.component.dataimport.context;

import java.util.ArrayList;
import java.util.List;

import banger.framework.component.constraint.IConstraint;
import banger.framework.component.dataimport.IImportContext;

public class DefaultImportContext implements IImportContext {
	private List<ColumnInfo> columns;
	private List<IConstraint> constraints;
	private int maxRow;
	private int batch;
	private String filename;
	
	public DefaultImportContext(String filename){
		this.filename = filename;
		this.maxRow = Integer.MAX_VALUE;
		this.batch = -1;
	}
	
	public List<ColumnInfo> getColumns() {
		return columns;
	}
	
	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}
	
	public int getMaxRow() {
		return maxRow;
	}
	
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	
	public int getBatch() {
		return batch;
	}
	
	public void setBatch(int batch) {
		this.batch = batch;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public List<IConstraint> getConstraints() {
		return constraints;
	}
	
	public void setConstraints(List<IConstraint> constraints) {
		this.constraints = constraints;
	}
	
	public void addConstraint(IConstraint constraint) {
		if(this.constraints==null)this.constraints = new ArrayList<IConstraint>();
		this.constraints.add(constraint);
	}
	
	
}
