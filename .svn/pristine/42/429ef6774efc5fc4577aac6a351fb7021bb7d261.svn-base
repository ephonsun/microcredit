package banger.util.constant;

import java.util.List;
import java.util.Map;

/**
 * Created by kangbiao on 2018/1/25.
 */
public class Socket_Head extends BaseXmlBeanEnum {

    private static final long serialVersionUID = 795381566820679308L;

    public Socket_Head(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_Head.class);
        }catch (Exception e){
        }
        return list;
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    public static final String tx_code = "40220001";

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum tx_code = new Socket_Head("tx_code", "交易码", "12", "1", null, "");
    public static final BaseXmlBeanEnum branch_no = new Socket_Head("branch_no", "机构号", "5", "0", null, "06044");
    public static final BaseXmlBeanEnum teller_no = new Socket_Head("teller_no", "柜员号", "12", "0", null, "0059408");
    public static final BaseXmlBeanEnum channel_flag = new Socket_Head("channel_flag", "渠道标识", "2", "1", null, "02");
    public static final BaseXmlBeanEnum request_date = new Socket_Head("request_date", "请求方日期", "8", "1", null, "");
    public static final BaseXmlBeanEnum request_time = new Socket_Head("request_time", "请求方时间", "6", "1", null, "");
    public static final BaseXmlBeanEnum request_seq_no = new Socket_Head("request_seq_no", "请求方流水号", "20", "1", null, "");
    public static final BaseXmlBeanEnum terminal_info = new Socket_Head("terminal_info", "客户渠道交易终端", "20", "0", null, "");


}
