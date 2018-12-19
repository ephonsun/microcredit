package banger.domain.enumerate;

/**
 * 数据字典
 * Created by zhusw on 2017/8/25.
 */
public enum CodeDictEnum {

    资产负债_现金_项目("现金项目","CD_ASSETS_CASH_ITEM","cdDictColByName","itemName","itemNameDisplay","ASSETS_CASH_AMOUNT"),
    资产负债_存货_类型("存货类型","CD_ASSETS_STOCK_TYPE","cdDictColByName","itemType","itemTypeDisplay",""),
    资产情况_其他经营_项目("其他经营项目","CD_OTHER_OPERATING_ITEM","cdDictColByName","itemName","itemNameDisplay","ASSETS_OPERATING_AMOUNT"),
    资产情况_其他非经营_项目("其他非经营项目","CD_NON_OPERATING_ITEM","cdDictColByName","itemName","itemNameDisplay","ASSETS_NON_OPERATING_AMOUNT"),
    资产情况_经营类_固定资产_类别("经营类固定资产类别","CD_ASSETS_BIZ_FIXED_TYPE","cdDictColByName","itemType","itemTypeDisplay","ASSETS_FIXED_AMOUNT1"),
    资产情况_消费类_固定资产_类别("消费类固定资产类别","CD_ASSETS_CONSUM_FIXED_TYPE","cdDictColByName","itemType","itemTypeDisplay","ASSETS_FIXED_AMOUNT2"),
    资产负债_投资性资产_项目("投资性资产项目","CD_ASSETS_INVEST_ITEM","cdDictColByName","itemName","itemNameDisplay","ASSETS_INVEST_AMOUNT"),
    负债情况_消费性负债_来源("消费性负债来源","CD_DEBTS_CONSUME_SOURCE","cdDictColByName","debtsSource","debtsSourceDisplay","DEBTS_CONSUME_AMOUNT"),

    损益情况_其他收入_项目("其他收入项目","CD_PROFIT_OTHER_INCOME","cdDictColByName","itemName","itemNameDisplay",""),
    损益情况_消费类_固定支出_项目("固定支出项目","CD_PROFIT_FIXED_DEFRAY","cdDictColByName","itemName","itemNameDisplay",""),
    损益情况_经营类_固定成本支出_项目("固定成本支出项目","CD_PROFIT_FIXED_COST_DEFRAY","cdDictColByName","itemName","itemNameDisplay",""),
    损益情况_所得税支出_项目("所得税支出项目","CD_PROFIT_TEX_DEFRAY","cdDictColByName","itemName","itemNameDisplay",""),
    损益情况_其他支出_项目("其他支出项目","CD_PROFIT_OTHER_DEFRAY","cdDictColByName","itemName","itemNameDisplay",""),
    损益情况_家庭收入_项目("家庭收入项目","CD_PROFIT_HOME_INCOME","cdDictColByName","itemName","itemNameDisplay",""),

    账款_结算方式("账款结算方式","CD_SETTLEMENT_MODE","cdDictColByName","settlementMode","settlementModeDisplay",""),
    ;

    public final String name;
    public final String params;         //参数
    public final String codeTable;      //代码表
    public final String codeKey;        //值属性名
    public final String nameKey;        //显示属性名
    public final String columnName;     //

    CodeDictEnum(String name,String params,String codeTable,String codeKey,String nameKey,String columnName) {
        this.name = name;
        this.codeTable = codeTable;
        this.params = params;
        this.codeKey = codeKey;
        this.nameKey = nameKey;
        this.columnName = columnName;
    }

    public static CodeDictEnum valueOfColumn(String columnName){
        for (CodeDictEnum codeDictEnum : CodeDictEnum.values()) {
            if (codeDictEnum.columnName.equals(columnName))
                return codeDictEnum;
        }
        return null;
    }

}
