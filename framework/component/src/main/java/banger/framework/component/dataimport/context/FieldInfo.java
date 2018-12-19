package banger.framework.component.dataimport.context;

import java.io.Serializable;

public class FieldInfo implements Serializable {
	private static final long serialVersionUID = 748178739614482817L;

	/**
	 * 字段名
	 */
	protected String fieldName;
	/**
	 * 字段描术
	 */
	protected String fieldDisplay;
	/**
	 * 是否复盖
	 */
	protected boolean covered;
	
	public FieldInfo(){
		this.fieldName = "";
		this.fieldDisplay = "";
		this.covered = false;
	}
	
	public FieldInfo(String name,String display){
		this(name,display,false);
	}
	
	public FieldInfo(String name,String display,boolean covered){
		this.fieldName = name;
		this.fieldDisplay = display;
		this.covered = covered;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldDisplay() {
		return fieldDisplay;
	}
	public void setFieldDisplay(String fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}
	public boolean getCovered() {
		return covered;
	}
	public void setCovered(boolean covered) {
		this.covered = covered;
	}
	
	
}
