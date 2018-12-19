package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *客户信息同步-子表同步 (担保人客户)
 */
public class Socket_99CHNCMI2002_1 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2002_1(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2002_1.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2002_1.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2002_1("opt","操作类型","1","1",null,"A");
    public static final BaseXmlBeanEnum cus_id = new Socket_99CHNCMI2002_1("cus_id","担保人.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum cus_name = new Socket_99CHNCMI2002_1("cus_name","担保人.姓名","80","1",null,"");
    public static final BaseXmlBeanEnum cus_short_name = new Socket_99CHNCMI2002_1("cus_short_name","客户简称","80","0",null,"");

    public static final BaseXmlBeanEnum cus_type = new Socket_99CHNCMI2002_1("cus_type","担保人.客户类型","3","1",
            new HashMap<String, String>(){{put("1","260");put("2","110");put("3","130");}},"110");

    public static final BaseXmlBeanEnum cert_type = new Socket_99CHNCMI2002_1("cert_type","担保人.证件类型","2","1",
            new HashMap<String, String>(){{
                put("1","10");put("2","31");put("3","11");put("4","12");
                put("5","12");put("6","13");put("7","14");put("8","30");
                put("9","34");put("10","32");put("11","1X");
                }},"");
    public static final BaseXmlBeanEnum cert_code = new Socket_99CHNCMI2002_1("cert_code","担保人.证件号码","20","1",null,"");
    public static final BaseXmlBeanEnum contact_name = new Socket_99CHNCMI2002_1("contact_name","联系人","80","0",null,"");
    public static final BaseXmlBeanEnum phone = new Socket_99CHNCMI2002_1("phone","担保人.联系电话","35","0",null,"");
    public static final BaseXmlBeanEnum open_date = new Socket_99CHNCMI2002_1("open_date","最早开户日期","10","0",null,"");
    public static final BaseXmlBeanEnum trans_cus_id = new Socket_99CHNCMI2002_1("trans_cus_id","核心过渡客户码","30","0",null,"");

}
