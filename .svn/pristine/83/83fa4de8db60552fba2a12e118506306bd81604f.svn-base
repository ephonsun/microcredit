package banger.domain.enumerate;

/**
 * 贷款审核权限
 * Created by zhusw on 2017/6/19.
 */
public enum  LoanExamineRight {
    TEAM("TEAM","团内"),
    ALL("ALL","全部"),
    ;

    public final String right; // 选取方式类型
    public final String rightName; // 选取方式类型名称

    LoanExamineRight(String right,String rightName){
        this.right = right;
        this.rightName = rightName;
    }

    public static LoanExamineRight valueOfRight(String right){
        for (LoanExamineRight examineRight : LoanExamineRight.values()) {
            if (examineRight.right.equals(right)) {
                return examineRight;
            }
        }
        return null;
    }

    public static String getNameByRight(String right){
        for (LoanExamineRight examineRight : LoanExamineRight.values()) {
            if (examineRight.right.equals(right)) {
                return examineRight.rightName;
            }
        }
        return "";
    }

}
