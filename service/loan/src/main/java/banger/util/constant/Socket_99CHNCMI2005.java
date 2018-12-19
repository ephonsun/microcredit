package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *业务合同与担保关系同步接口
 */
public class Socket_99CHNCMI2005 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2005(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2005.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2005.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2005("opt","操作类型","1","1",null,"A");
    public static final BaseXmlBeanEnum serno = new Socket_99CHNCMI2005("serno","担保合同号","40","1",null,"");//
    public static final BaseXmlBeanEnum cont_no = new Socket_99CHNCMI2005("cont_no","合同号","30","1",null,"");
    public static final BaseXmlBeanEnum guar_cont_no = new Socket_99CHNCMI2005("guar_cont_no","担保合同号","30","1",null,"");
    public static final BaseXmlBeanEnum guar_cont_type = new Socket_99CHNCMI2005("guar_cont_type","担保合同类型","1","0",null,"");
    public static final BaseXmlBeanEnum guar_tee = new Socket_99CHNCMI2005("guar_tee","担保人","60","0",null,"");
    public static final BaseXmlBeanEnum guar_way = new Socket_99CHNCMI2005("guar_way","担保方式","5","0",
            new HashMap<String, String>(){{put("01","00");put("02","30");put("03","10");put("04","20");}},"");
    public static final BaseXmlBeanEnum used_amt = new Socket_99CHNCMI2005("used_amt","审批决议.决议金额","18","0",null,"");
    public static final BaseXmlBeanEnum restored_amt = new Socket_99CHNCMI2005("restored_amt","恢复金额","18","0",null,"");
    public static final BaseXmlBeanEnum ext_rate = new Socket_99CHNCMI2005("ext_rate","汇率","16","0",null,"");
    public static final BaseXmlBeanEnum guar_start_date = new Socket_99CHNCMI2005("guar_start_date","贷款合同.借款起始日期","10","1",null,"");
    public static final BaseXmlBeanEnum guar_end_date = new Socket_99CHNCMI2005("guar_end_date","贷款合同.借款终止日期","10","1",null,"");
    public static final BaseXmlBeanEnum guar_cont_status = new Socket_99CHNCMI2005("guar_cont_status","担保合同状态","3","1",null,"101");
    public static final BaseXmlBeanEnum start_date = new Socket_99CHNCMI2005("start_date","　开始时间","16","0",null,"");
    public static final BaseXmlBeanEnum close_date = new Socket_99CHNCMI2005("close_date","　结束时间","16","0",null,"");
    public static final BaseXmlBeanEnum restored_order = new Socket_99CHNCMI2005("restored_order","释放顺序号","11","0",null,"");


}
