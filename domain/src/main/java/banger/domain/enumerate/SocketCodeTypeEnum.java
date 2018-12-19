package banger.domain.enumerate;

/**
 * Created by Administrator on 2017/12/26.
 */

/**
 * 接口交易码和接口名不能更改
 */
public enum SocketCodeTypeEnum {
    QRY00400("99QRY00400", "Socket_99QRY00400","根据卡号/存款帐号查询客户信息"),
    QRY00401("99QRY00401", "Socket_99QRY00401","根据客户编码查询客户资料"),
    QRY00402("99QRY00402", "Socket_99QRY00402","根据客户编码查询客户资料"),
    QRY10400("99QRY10400", "Socket_99QRY10400","根据贷款帐号查询账户信息"),
    QRY10401("99QRY10401", "Socket_99QRY10401","根据贷款帐号查询还款记录信息"),
    CHNCMI4050("99CHNCMI4050","Socket_99CHNCMI4050","信贷客户信息查询"),
    CHNCMI2051("99CHNCMI2051","Socket_99CHNCMI2051","客户转正"),
    CMI0230054("99CMI0230054","Socket_99CMI0230054","受托支付信息查询"),
    CHNCMI4036("99CHNCMI4036","Socket_99CHNCMI4036","放款成功、还款、冲正、出入库通知查询"),
    ;

    public final String socketCode;					//交易码
    public final String socketClassName;				//接口名
    public final String func;                      //接口功能描述

    SocketCodeTypeEnum(String socketCode, String socketClassName,String func) {
        this.socketCode = socketCode;
        this.socketClassName = socketClassName;
        this.func = func;
    }

    public static String getTypeName(String name) {
        for(SocketCodeTypeEnum SocketEnum : SocketCodeTypeEnum.values()){
            if(SocketEnum.socketClassName.equalsIgnoreCase(name)){
                return SocketEnum.socketCode;
            }
        }
        return null;
    }

}
