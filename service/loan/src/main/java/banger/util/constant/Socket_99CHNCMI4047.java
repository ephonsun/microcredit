package banger.util.constant;

import java.util.List;
import java.util.Map;

/**
 * 根据贷款账号查询还款记录信息
 */
public class Socket_99CHNCMI4047 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI4047(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI4047.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI4047.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

//    字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum loan_nature = new Socket_99CHNCMI4047("loan_nature","调查结论.贷款性质","2","1",null,"");
        public static final BaseXmlBeanEnum prd_type = new Socket_99CHNCMI4047("prd_type","调查结论.产品类型","2","1",null,"");
        public static final BaseXmlBeanEnum loan_start_date = new Socket_99CHNCMI4047("loan_start_date","贷款起始日","10","1",null,"");
        public static final BaseXmlBeanEnum loan_end_date = new Socket_99CHNCMI4047("loan_end_date","贷款终止日","10","1",null,"");
        public static final BaseXmlBeanEnum int_rate_type = new Socket_99CHNCMI4047("int_rate_type","利率类型","1","1",null,"");
        public static final BaseXmlBeanEnum cur_type = new Socket_99CHNCMI4047("cur_type","币种","3","1",null,"");
        public static final BaseXmlBeanEnum biz_type = new Socket_99CHNCMI4047("biz_type","调查结论.业务品种","6","1",null,"");
        public static final BaseXmlBeanEnum ispaf = new Socket_99CHNCMI4047("ispaf","是否住房公积金贷款","1","1",null,"2");
        public static final BaseXmlBeanEnum biz_type_detail = new Socket_99CHNCMI4047("biz_type_detail","业务品种分类名称","100","1",null,"");

    }
