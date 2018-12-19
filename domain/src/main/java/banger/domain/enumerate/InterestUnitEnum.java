package banger.domain.enumerate;

/**
 * @Author: yangdw
 * @Description:利息单位枚举类
 * @Date: Created in 10:53 2017/11/10.
 */
public enum InterestUnitEnum {

	INTEREST_UNIT_YEAR("年",1),
	INTEREST_UNIT_MONTH("月",2);


	public final String name; // 利息单位名称
	public final Integer value; // 单位值

	InterestUnitEnum(String name,Integer value){
		this.name = name;
		this.value = value;
	}

	public String getNameByValue(Integer value) {
		for (InterestUnitEnum interstUnit : InterestUnitEnum.values()) {
			if (interstUnit.value.intValue() == value.intValue())
				return interstUnit.name;
		}
		return "";
	}
}
