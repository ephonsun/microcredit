package banger.util.constant;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *根据卡号/存款帐号查询客户信息
 */
public class Socket_99CHNCMI4050 extends BaseXmlBeanEnum{

    public Socket_99CHNCMI4050(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI4050.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI4050.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum mng_br_id = new Socket_99CHNCMI4050("mng_br_id","法人机构号","20","1",null,"06001");
    //输入证件类+证件号码时，可不输入客户号
    public static final BaseXmlBeanEnum cert_type = new Socket_99CHNCMI4050("cert_type","证件类型","2","0",new HashMap<String, String>(){{
        put("1","10");put("2","31");put("3","11");put("4","12");
        put("5","12");put("6","13");put("7","14");put("8","30");
        put("9","34");put("10","32");put("11","1X");
    }},"10");
    //输入证件类+证件号码时，可不输入客户号
    public static final BaseXmlBeanEnum cert_code = new Socket_99CHNCMI4050("cert_code","证件号码","20","0",null,"");
    //输入客户号时，可不输入证件类型+证件号码
    public static final BaseXmlBeanEnum cus_id = new Socket_99CHNCMI4050("cus_id","客户号","30","0",null,"");
    //1-查询
    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI4050("opt","操作类型","1","1",null,"1");
}
