package banger.util.constant;


import java.util.List;
import java.util.Map;

/**
 * 放款成功、还款、冲正、出入库通知查询
 */
public class Socket_99CHNCMI4036 extends BaseXmlBeanEnum{

    public Socket_99CHNCMI4036(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI4036.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI4036.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum acct_no = new Socket_99CHNCMI4036("acct_no","放款账号","32","0",null,"");
    //600052 入库成功通知 600053 出库成功通知 600010 普通贷款发放通知 600020 普通贷款柜面还款通知 013045 贷款结清通知 013145  贷款结清冲正通知
    //011145 贷款还款冲正通知 011155 贷款放款冲正通知
    public static final BaseXmlBeanEnum trade_code = new Socket_99CHNCMI4036("trade_code","核心查询交易码","32","0",null,"");
    //目前只支持6天内的数据
    public static final BaseXmlBeanEnum trade_date = new Socket_99CHNCMI4036("trade_date","交易日期","10","0",null,"");
    public static final BaseXmlBeanEnum guaranty_id = new Socket_99CHNCMI4036("guaranty_id","押品编号","32","0",null,"");
}




