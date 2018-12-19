package banger.domain.enumerate;

/**
 * Created by XUElw on 2017/10/25.
 */
public enum LoanRepayPlanEnum {

    EQUAL_PRINCIPAL("1","等额本金"),
    EQUAL_PRINCIPAL_ACCRUAL("2","等额本息"),
    MONTHLY_REPAYMENT("3","按月还息到期还本"),
    LUMP_SUM_REPAYMENT("4","一次性还本付息"),
    FLEXIBLE_REPAYMENT("5","灵活还款"),;

    public final String value;
    public final String name;

    LoanRepayPlanEnum(String value, String name){
        this.value = value;
        this.name = name;
    }

    public boolean equalValue(String value){
        return this.value.equals(value);
    }

    public static LoanRepayPlanEnum itemOfValue(String value){
        for(LoanRepayPlanEnum loanRepayPlanEnum : LoanRepayPlanEnum.values()){
            if(loanRepayPlanEnum.value.equals(value)){
                return loanRepayPlanEnum;
            }
        }
        return null;
    }

    public static String getNameByValue(String value){
        for(LoanRepayPlanEnum loanRepayPlanEnum : LoanRepayPlanEnum.values()){
            if(loanRepayPlanEnum.value.equals(value)){
                return loanRepayPlanEnum.name;
            }
        }
        return "";
    }
    public static String getValueByName(String name){
        for(LoanRepayPlanEnum loanRepayPlanEnum : LoanRepayPlanEnum.values()){
            if(loanRepayPlanEnum.name.equals(name)){
                return loanRepayPlanEnum.value;
            }
        }
        return name;
    }
}
