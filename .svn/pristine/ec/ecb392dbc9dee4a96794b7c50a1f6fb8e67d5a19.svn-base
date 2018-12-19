package banger.domain.enumerate;

/**
 * Created by huangfz on 2017/9/15
 */
public enum LoanAssetsColumnEnum {

    现金("ASSETS_CASH_AMOUNT","现金"),
    应收账款("ASSETS_RECEIVABLE_AMOUNT", "应收账款"),
    预付账款("ASSETS_PAYMENT_AMOUNT", "预付账款"),
    应付账款("DEBTS_PAYABLE_AMOUNT", "应付账款"),
    预收账款("DEBTS_RECEIVED_AMOUNT", "预收账款"),
    短期负债("DEBTS_SHORT_AMOUNT", "短期负债"),
    长期负债("DEBTS_LONG_AMOUNT", "长期负债"),
    投资性负债("DEBTS_INVEST_AMOUNT", "投资性负债"),
    自用性负债("DEBTS_SELF_USER_AMOUNT", "自用性负债"),
    消费性负债("DEBTS_CONSUME_AMOUNT", "消费性负债"),
    消费类其他负债("DEBTS_OTHER_AMOUNT", "其他负债"),
    其他经营资产("ASSETS_OPERATING_AMOUNT", "其他经营资产"),
    其他非经营资产("ASSETS_NON_OPERATING_AMOUNT", "其他非经营资产"),
    其他资产("ASSETS_OTHER_AMOUNT", "其他资产"),
    投资性资产("ASSETS_INVEST_AMOUNT", "投资性资产"),
    对外债权("ASSETS_EXTERNAL_CLAIMS", "对外债权"),
    预付款("ASSETS_ADVANCE_PAYMENT_AMOUNT", "预付款"),
    经营类其他负债("DEBTS_BIZ_OTHER_AMOUNT", "其他负债"),
    存货("ASSETS_STOCK_AMOUNT", "存货"),
    固定资产("ASSETS_FIXED_AMOUNT", "固定资产");


    public final String columnName;
    public final String itemName;


    LoanAssetsColumnEnum(String columnName,String itemName){
        this.columnName = columnName;
        this.itemName = itemName;

    }

    public static LoanAssetsColumnEnum itemNameOfColumn(String columnName){
        for(LoanAssetsColumnEnum assetsColumn : LoanAssetsColumnEnum.values()){
            if(assetsColumn.columnName.equals(columnName)){
                return assetsColumn;
            }
        }
        return null;
    }
}
