package banger.framework.web.layout;

import java.util.ArrayList;
import java.util.List;

import banger.framework.util.StringUtil;

public class FormsCell {
	private int colspan;			//单元格所占的行数
	private int rowspan;			//单元格所点的列数
	private Field field;			//字段
	private String type;
	private double width;
	private List<Field> merges;
	
	public FormsCell(String type,Forms forms,Field field){
		this.type = type;
		double rate = 100 / forms.getColumns();
		if(field!=null){
			this.field = field;
			if("content".equals(type)){
				this.colspan = (field.getCols()>1)?field.getCols()*2-1:1;
				if(StringUtil.isNullOrEmpty(field.getDisplay())){
					this.colspan++;
				}
				this.width = (field.getCols() - forms.getRatio())*rate;
			}else if("head".equals(type)){
				this.colspan = 1;
				this.width = forms.getRatio()*rate;
			}
			this.rowspan = field.getRows();
		}else{
			this.colspan = 1;
			this.rowspan = 1;
			this.width = rate;
		}
		this.merges = new ArrayList<Field>();
		if(type.equals("content"))this.field.setCell(this);
	}
	
	public String getType(){
		return this.type;
	}
	public int getColspan() {
		return this.colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	
	public int getRowspan() {
		return this.rowspan;
	}
	
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	
	public Field getField() {
		return this.field;
	}
	
	public double getWidth(){
		return this.width;
	}
	
	public Field getMergeField(int index){
		if(index < this.merges.size()){
			return this.merges.get(index);
		}
		return null;
	}
	
	public void addMergeField(Field field){
		this.merges.add(field);
	}

	public List<Field> getMerges() {
		return merges;
	}

	public void setMerges(List<Field> merges) {
		this.merges = merges;
	}
	
}
