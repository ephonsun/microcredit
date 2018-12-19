package banger.domain.enumerate;

/**
 * Created by Administrator on 2017/12/26.
 */
public enum SexEnum {
    MAN("1", "男"),
    WOMAN("2", "女"),
    ;

    public final String type;					//类型
    public final String typeName;				//类型名称

    SexEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static String getTypeName(String name) {
        for(SexEnum sexEnum : SexEnum.values()){
            if(sexEnum.typeName.equalsIgnoreCase(name)){
                return sexEnum.type;
            }
        }
        return null;
    }

}
