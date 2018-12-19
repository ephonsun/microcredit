package banger.domain.enumerate;

public enum FieldConstraintRule {

	Tellphone("Tellphone", "手机号"), 
	IdCard("IdCard", "身份证");

	public final String type; // 数据类型
	public final String typeName; // 数据名称
	
	FieldConstraintRule(String type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}
}
