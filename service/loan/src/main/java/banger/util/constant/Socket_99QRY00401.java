package banger.util.constant;

import java.util.List;
import java.util.Map;

/**
 *根据卡号/存款帐号查询客户信息
 */
public class Socket_99QRY00401 extends BaseXmlBeanEnum {

    public Socket_99QRY00401(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99QRY00401.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99QRY00401.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum cus_id = new Socket_99QRY00401("cus_id","客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum cus_type = new Socket_99QRY00401("cus_type","客户类型","1","0",null,"1");
}
