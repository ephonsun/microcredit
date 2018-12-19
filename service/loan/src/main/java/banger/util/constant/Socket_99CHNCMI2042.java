package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *担保合同与押品关系同步接口
 */
public class Socket_99CHNCMI2042 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2042(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2042.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2042.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2042("opt","操作类型","1","1",null,"A");
    public static final BaseXmlBeanEnum guaranty_id = new Socket_99CHNCMI2042("guaranty_id","抵质押物.抵质押物编码","32","1",null,"");
    public static final BaseXmlBeanEnum gage_name = new Socket_99CHNCMI2042("gage_name","抵质押物.抵质押品名称","120","1",null,"");
    public static final BaseXmlBeanEnum ser_no = new Socket_99CHNCMI2042("ser_no","抵质押物.抵质押物编码","40","1",null,"");
    public static final BaseXmlBeanEnum gage_way = new Socket_99CHNCMI2042("gage_way","抵质押担保合同.担保方式","5","0",null,"30003");
    public static final BaseXmlBeanEnum gage_type = new Socket_99CHNCMI2042("gage_type","抵质押物.抵质押物类型","5","1",null,"");
    public static final BaseXmlBeanEnum if_max_guar = new Socket_99CHNCMI2042("if_max_guar","是否最高额担保","1","0",null,"");
    public static final BaseXmlBeanEnum guar_cont_no = new Socket_99CHNCMI2042("guar_cont_no","担保合同号","30","1",null,"");
    public static final BaseXmlBeanEnum guar_cont_state = new Socket_99CHNCMI2042("guar_cont_state","担保合同状态","3","0",null,"");
    public static final BaseXmlBeanEnum status_code = new Socket_99CHNCMI2042("status_code","STATUS_CODE","5","0",null,"");
    public static final BaseXmlBeanEnum currency = new Socket_99CHNCMI2042("currency","币种","3","0",null,"CNY");
    public static final BaseXmlBeanEnum guaranty_amt = new Socket_99CHNCMI2042("guaranty_amt","审批决议.决议金额","18","0",null,"");
    public static final BaseXmlBeanEnum guar_no = new Socket_99CHNCMI2042("guar_no","抵质押物.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum cer_no = new Socket_99CHNCMI2042("cer_no","抵质押物.身份证号码","20","1",null,"");
    public static final BaseXmlBeanEnum cer_type = new Socket_99CHNCMI2042("cer_type","抵质押物.证件类型","2","1", null,"10");
    public static final BaseXmlBeanEnum cus_name = new Socket_99CHNCMI2042("cus_name","抵质押物.抵押人名称","60","1",null,"");
    public static final BaseXmlBeanEnum used_amt = new Socket_99CHNCMI2042("used_amt","审批决议.决议金额","18","0",null,"");



}
