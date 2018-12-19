package banger.domain.enumerate;

public enum InputRuleType {
	NAME("Name", "姓名", 20),
    PHONE_NUM("PhoneNum", "手机号码", 20),
    ;
    
    public final String type;					//类型
    public final String typeName;				//类型名称
    public final Integer length;				//最大长度

    InputRuleType(String type, String typeName,int length) {
        this.type = type;
        this.typeName = typeName;
        this.length = length;
    }
		
}
