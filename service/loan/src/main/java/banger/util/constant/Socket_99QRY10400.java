package banger.util.constant;

import java.util.List;
import java.util.Map;

/**
 *9.4.3. 根据贷款帐号查询账户信息
 */
public class Socket_99QRY10400 extends BaseXmlBeanEnum {

    public Socket_99QRY10400(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99QRY10400.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99QRY10400.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum opt = new Socket_99QRY10400("loan_acc","贷款帐号","32","1",null,"");


}
