package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *担保合同主表同步接口
 */
public class Socket_99CHNCMI2006 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2006(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2006.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2006.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2006("opt","操作类型","1","1",null,"A");
    public static final BaseXmlBeanEnum guar_cont_no = new Socket_99CHNCMI2006("guar_cont_no","担保合同号","30","1",null,"");
    public static final BaseXmlBeanEnum guar_cont_cn_no = new Socket_99CHNCMI2006("guar_cont_cn_no","担保合同编码","30","0",null,"");
    public static final BaseXmlBeanEnum guar_cont_type = new Socket_99CHNCMI2006("guar_cont_type","合同类型","1","1",
            new HashMap<String, String>(){{put("3","1");put("4","2");}},"");
    public static final BaseXmlBeanEnum guar_way = new Socket_99CHNCMI2006("guar_way","保证担保合同.担保方式","5","1",null,"30003");
//    public static final BaseXmlBeanEnum guar_way = new Socket_99CHNCMI2006("guar_way","担保方式","5","1", null,"30003");
//    public static final BaseXmlBeanEnum borrower_no = new Socket_99CHNCMI2006("borrower_no","个人信息.客户编码","30","1",null,"");
    public static final BaseXmlBeanEnum borrower_no = new Socket_99CHNCMI2006("borrower_no","个人信息.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum borrower_name = new Socket_99CHNCMI2006("borrower_name","个人信息.客户姓名","80","1",null,"");
    public static final BaseXmlBeanEnum borrower_relation = new Socket_99CHNCMI2006("borrower_relation","担保人.与借款人关系","5","1",null,"10013");
    public static final BaseXmlBeanEnum guar_no = new Socket_99CHNCMI2006("guar_no","担保人.客户编码","30","0",null,"");
    public static final BaseXmlBeanEnum guar_name = new Socket_99CHNCMI2006("guar_name","担保人.姓名","80","1",null,"");
    public static final BaseXmlBeanEnum cer_no = new Socket_99CHNCMI2006("cer_no","担保人.证件号码","20","0",null,"");
    public static final BaseXmlBeanEnum cer_type = new Socket_99CHNCMI2006("cer_type","担保人.证件类型","2","0",
            new HashMap<String, String>(){{
                put("1","10");put("2","31");put("3","11");put("4","12");
                put("5","12");put("6","13");put("7","14");put("8","30");
                put("9","34");put("10","32");put("11","1X");
            }},"10");
    public static final BaseXmlBeanEnum cur_type = new Socket_99CHNCMI2006("cur_type","贷款合同.币种","3","1",null,"CNY");
    public static final BaseXmlBeanEnum guar_amt = new Socket_99CHNCMI2006("guar_amt","审批决议.决议金额","18","1",null,"");
    public static final BaseXmlBeanEnum used_amt = new Socket_99CHNCMI2006("used_amt","审批决议.决议金额","18","1",null,"0");
    public static final BaseXmlBeanEnum guar_term = new Socket_99CHNCMI2006("guar_term","审批决议.决议期限","11","1",null,"");
    public static final BaseXmlBeanEnum guar_start_date = new Socket_99CHNCMI2006("guar_start_date","贷款合同.借款起始日期","10","1",null,"");
    public static final BaseXmlBeanEnum guar_end_date = new Socket_99CHNCMI2006("guar_end_date","贷款合同.借款终止日期","10","0",null,"");
    public static final BaseXmlBeanEnum sign_date = new Socket_99CHNCMI2006("sign_date","当前日期","10","0",null,"");
    public static final BaseXmlBeanEnum guar_cont_state = new Socket_99CHNCMI2006("guar_cont_state","担保合同状态","3","1",null,"101");
    public static final BaseXmlBeanEnum notes = new Socket_99CHNCMI2006("notes","备注信息","200","0",null,"");
    public static final BaseXmlBeanEnum regi_date = new Socket_99CHNCMI2006("regi_date","登记日期","10","0",null,"");
    public static final BaseXmlBeanEnum input_id = new Socket_99CHNCMI2006("input_id","申请人","20","0",null,"");
//    public static final BaseXmlBeanEnum customer_mgr = new Socket_99CHNCMI2006("customer_mgr","客户经理","20","1",null,"0602305");
    public static final BaseXmlBeanEnum customer_mgr = new Socket_99CHNCMI2006("customer_mgr","个人信息.主管客户经理","20","1",null,"");
//    public static final BaseXmlBeanEnum input_br_id = new Socket_99CHNCMI2006("input_br_id","登记机构","20","1",null,"06023");
    public static final BaseXmlBeanEnum input_br_id = new Socket_99CHNCMI2006("input_br_id","省信贷合同要素.主管机构","20","1",null,"");
//    public static final BaseXmlBeanEnum main_br_id = new Socket_99CHNCMI2006("main_br_id","主管机构","20","1",null,"06023");
    public static final BaseXmlBeanEnum main_br_id = new Socket_99CHNCMI2006("main_br_id","省信贷合同要素.主管机构","20","1",null,"");
//    public static final BaseXmlBeanEnum fina_br_id = new Socket_99CHNCMI2006("fina_br_id","账务机构","20","1",null,"06288");
    public static final BaseXmlBeanEnum fina_br_id = new Socket_99CHNCMI2006("fina_br_id","省信贷合同要素.账务机构","20","1",null,"06101");
    public static final BaseXmlBeanEnum modi_date = new Socket_99CHNCMI2006("modi_date","修改日期","10","0",null,"");
    public static final BaseXmlBeanEnum grp_no = new Socket_99CHNCMI2006("grp_no","集团编号","30","0",null,"");
    public static final BaseXmlBeanEnum refer_way = new Socket_99CHNCMI2006("refer_way","引用方式","1","1",null,"1");


}
