package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *根据客户编码查询客户资料
 */
public class Socket_99QRY00402 extends BaseXmlBeanEnum {

    public Socket_99QRY00402(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99QRY00402.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99QRY00402.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum id = new Socket_99QRY00402("id","证件号码","20","1",null,"");
    public static final BaseXmlBeanEnum id_type = new Socket_99QRY00402("id_type","证件类型","2","0",
            new HashMap<String, String>(){{
                put("1","01");put("2","04");put("3","03");put("4","05");
                put("5","05");put("6","07");put("7","07");put("8","02");
                put("9","09");put("10","06");put("11","11");
            }},"01");
}
