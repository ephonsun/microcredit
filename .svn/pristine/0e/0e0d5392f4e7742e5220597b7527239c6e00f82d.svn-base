package banger.util.constant;


import java.util.List;
import java.util.Map;

/**
 *根据卡号/存款帐号查询客户信息
 */
public class Socket_99CHNCMI2051 extends BaseXmlBeanEnum{

    public Socket_99CHNCMI2051(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2051.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2051.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum mng_br_id = new Socket_99CHNCMI2051("mng_br_id","法人机构号","20","1",null,"");
    public static final BaseXmlBeanEnum o_cus_id = new Socket_99CHNCMI2051("o_cus_id","转正前客户号","30","1",null,"");
    public static final BaseXmlBeanEnum cus_id = new Socket_99CHNCMI2051("cus_id","转正后客户号","30","1",null,"");
    //1-保证人转正式客户
    public static final BaseXmlBeanEnum change_opt = new Socket_99CHNCMI2051("change_opt","转正选项","1","1",null,"");
}
