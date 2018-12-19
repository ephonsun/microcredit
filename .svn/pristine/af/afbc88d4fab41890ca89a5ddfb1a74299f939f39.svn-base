package banger.domain.enumerate;


/**
 * 建立/修改/查询协议还款计划-99COR017022
 * 还款计划option选项
 * Created by ygb on 2017/6/12.
 */
public enum RepayPlanOption {

    ADD("C","创建"),
    UPDATE("A","修改"),
    SELECT("E","查询");

    public final String type; // 协议还款计划option选项
    public final String typeName; // option选项名称

    RepayPlanOption(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static String getTypeNameByType(String type){
        for(RepayPlanOption option : RepayPlanOption.values()){
            if(option.type.equalsIgnoreCase(type)){
                return option.typeName;
            }
        }
        return null;
    }

}
