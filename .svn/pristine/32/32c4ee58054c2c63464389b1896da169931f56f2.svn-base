package banger.util.constant;

import java.util.List;
import java.util.Map;

/**
 *押品出入库、撤销
 */
public class Socket_99CHNCMI1037 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI1037(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI1037.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI1037.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum guaranty_id = new Socket_99CHNCMI1037("guaranty_id","担保编号","32","1",null,"");
    public static final BaseXmlBeanEnum out_fina_br_id = new Socket_99CHNCMI1037("out_fina_br_id","省信贷合同要素.账务机构","20","1",null,"06101");
    public static final BaseXmlBeanEnum guar_way = new Socket_99CHNCMI1037("guar_way","抵押/质押","2","1",null,"");
//    1入库 2出库  3入库撤销
    public static final BaseXmlBeanEnum type = new Socket_99CHNCMI1037("type","类型","1","1",null,"");

}
