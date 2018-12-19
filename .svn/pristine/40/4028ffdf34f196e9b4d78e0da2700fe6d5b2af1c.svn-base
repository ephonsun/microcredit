package banger.domain.enumerate;

/**
 * Created by zhusw on 2017/8/22.
 */
public enum LoanClassEnum {

    LOAN_BUSINESS_CLASS("LOAN_BUSINESS_CLASS", "经营类",1),
    LOAN_CONSUMER_CLASS("LOAN_CONSUMER_CLASS", "消费类",2);

    public final Integer value;
    public final String type; // 拒绝类型
    public final String typeName; // 拒绝类型名称

    LoanClassEnum(String type,String typeName,Integer value){
        this.type = type;
        this.typeName = typeName;
        this.value = value;
    }

    public boolean equalValue(Integer value){
        return this.value.equals(value);
    }

}
