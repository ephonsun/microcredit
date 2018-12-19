package banger.domain.config;

import java.util.List;

public class AutoBaseField {
	protected Integer id;		//字段Id
	protected Boolean isAppShow;		//app显示
	protected Boolean isWebShow;		//web显示
	protected String field;		//字段名
	protected String type;		//字段类型
	protected String column;		//数据库字段
	protected String name;		//显示名称
	protected String unit;		//单位
	protected String rule;		//校验
	protected String popupUrl;		//弹出对话框地址
	protected Boolean editable;		//控件是否可编辑
	protected Boolean nullable;		//是否必填
	protected Integer length;		//最大长度
	protected List<AutoBaseOption> options;		//下拉选项
	protected Object value;		//非持久化字段 表字段对应值
	protected String defaultValue;		//默认值
	protected String onChange;			//改变值事件
	protected String ctrlFlag;			//控制联动标记
	protected String properties;			//控制联动数据

	public AutoBaseField(){
		this.editable = true;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Boolean getNullable() {
		return nullable;
	}
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}
	public List<AutoBaseOption> getOptions() {
		return options;
	}
	public void setOptions(List<AutoBaseOption> options) {
		this.options = options;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getInputLength(){
		String type = this.getType();
		if("Address".equalsIgnoreCase(type)){
			return this.getLength()/3;
		}
		if("Text".equalsIgnoreCase(type)){
			return this.getLength()/3;
		}
		if("TextArea".equalsIgnoreCase(type)){
			return this.getLength()/3;
		}
		if("Quest".equalsIgnoreCase(type)){
			return this.getLength()/3;
		}
		if("Ratio".equalsIgnoreCase(type))return 10;
		if("Decimal".equalsIgnoreCase(type))return 20;
		if("Float".equalsIgnoreCase(type))return 20;
		if("Number".equalsIgnoreCase(type))return 9;
		if("Select".equalsIgnoreCase(type))return 30;
		if("MultipleSelect".equalsIgnoreCase(type))return 180;

		return -1;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public Boolean getIsAppShow() {
		return isAppShow;
	}
	public void setIsAppShow(Boolean isAppShow) {
		this.isAppShow = isAppShow;
	}
	public Boolean getIsWebShow() {
		return isWebShow;
	}
	public void setIsWebShow(Boolean isWebShow) {
		this.isWebShow = isWebShow;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getPopupUrl() {
		return popupUrl;
	}

	public void setPopupUrl(String popupUrl) {
		this.popupUrl = popupUrl;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getCtrlFlag() {
		return ctrlFlag;
	}

	public void setCtrlFlag(String ctrlFlag) {
		this.ctrlFlag = ctrlFlag;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
}
