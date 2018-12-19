package banger.domain.enumerate;

public enum TableInputType {
	FORMS(1,"Forms","表单输入"),
	LIST(2,"List","列表输入"),
	;

	public final Integer value;
	public final String type;					//输入长度
    public final String typeName;			//数据库类型

    TableInputType(Integer value,String type, String typeName) {
    	this.value = value;
        this.type = type;
        this.typeName = typeName;
    }
	
	public static TableInputType valueOf(int value){
		if(value==1){
			return FORMS;
		}else if(value==2){
			return LIST;
		}
		return null;
	}

	public boolean equalType(String type){
		return this.type.equals(type);
	}

	public boolean equalValue(Integer value){
		return this.value.equals(value);
	}
}
