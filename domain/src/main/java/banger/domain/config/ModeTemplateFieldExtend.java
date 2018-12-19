package banger.domain.config;

/**
 * Created by wanggl on 2017/8/23.
 */
public class ModeTemplateFieldExtend extends ModeTemplateField{

	private String fieldType;
	private String fieldNumberUnit;

	public String getFieldNumberUnit() {
		return fieldNumberUnit;
	}

	public void setFieldNumberUnit(String fieldNumberUnit) {
		this.fieldNumberUnit = fieldNumberUnit;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
