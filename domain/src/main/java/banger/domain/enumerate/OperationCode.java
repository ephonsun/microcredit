package banger.domain.enumerate;

/**
 * Created by ygb on 2018/3/18.
 */
public enum OperationCode {
    CODE_100(100, "一般借款合同"),
    CODE_101(101,"担保合同"),
    CODE_110(110,"贴现协议编号"),
    CODE_111(111,"转贴现协议买入编号"),
    CODE_112(112,"转贴现协议卖出编号"),
    CODE_113(113,"保函协议编号"),
    CODE_114(114,"展期协议编号"),
    CODE_115(115,"联保协议编号"),
    CODE_200(200, "借据号"),
    CODE_300(300, "抵质押品号"),
    CODE_401(401,"委托贷款资金协议"),
    CODE_402(402,"银团贷款资金协议"),
    CODE_403(403,"社团贷款资金协议");

    //业务号
    private final Integer  code;

    //业务号描述
    private final String msg;


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private OperationCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static OperationCode valueOf(Integer code){
        for(OperationCode codeEnum : OperationCode.values()){
            if(codeEnum.code.equals(code)){
                return codeEnum;
            }
        }
        return null;
    }
}
