package banger.domain.config;

/**
 * @Author: yangdw
 * @Description:自定义评分模型字段,用于计算评分得分
 * @Date: Created in 10:09 2017/9/14.
 */
public class ModeScoreCustomField extends ModeTemplateField{

	private Integer optionId;					//主键
	private Integer modeId;					//模型ID
	private String fieldType;					//自定义字段类型Decimal   金额Number   数值Select     单选下拉框

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Integer getModeId() {
		return modeId;
	}

	public void setModeId(Integer modeId) {
		this.modeId = modeId;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
