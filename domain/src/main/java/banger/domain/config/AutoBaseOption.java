package banger.domain.config;

public class AutoBaseOption {
	private String value;		//下拉选项的值
	private String name;		//下拉选项的名称
	
	public AutoBaseOption(String value,String name){
		this.value = value;
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOptionText(){
		return this.name;
	}
	
	public String getOptionValue(){
		return this.value;
	}
	
}
