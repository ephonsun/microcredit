package banger.domain.enumerate;

/**
 * 审核人，选取方式
 * Created by zhusw on 2017/6/19.
 */
public enum LoanExamineType {
    SELECT_USER("SELECT_USER", "指定"),
    RANDOM_USER("RANDOM_USER", "随机"),
    ;

    public final String type; // 选取方式类型
    public final String typeName; // 选取方式类型名称

    LoanExamineType(String type,String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    public static LoanExamineType valueOfType(String type){
        for (LoanExamineType examineType : LoanExamineType.values()) {
            if (examineType.type.equals(type)) {
                return examineType;
            }
        }
        return null;
    }

    public static String getNameByType(String type){
        for (LoanExamineType examineType : LoanExamineType.values()) {
            if (examineType.type.equals(type)) {
                return examineType.typeName;
            }
        }
        return "";
    }

}
