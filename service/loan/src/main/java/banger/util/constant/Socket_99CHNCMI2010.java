package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *抵押品基本信息
 */
public class Socket_99CHNCMI2010 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2010(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2010.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2010.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2010("opt","操作类型","1","1",null,"A");
    public static final BaseXmlBeanEnum guaranty_id = new Socket_99CHNCMI2010("guaranty_id","抵质押物.抵质押物编码","32","1",null,"");
    public static final BaseXmlBeanEnum cus_typ = new Socket_99CHNCMI2010("cus_typ","抵质押物.客户类型","3","1",
            new HashMap<String, String>(){{put("1","260");put("2","110");put("3","130");}},"110");
    public static final BaseXmlBeanEnum cus_id = new Socket_99CHNCMI2010("cus_id","抵质押物.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum cus_name = new Socket_99CHNCMI2010("cus_name","抵质押物.抵押人名称","60","1",null,"");
    public static final BaseXmlBeanEnum cer_type = new Socket_99CHNCMI2010("cer_type","抵质押物.证件类型","2","0",null,"10");
    public static final BaseXmlBeanEnum cer_no = new Socket_99CHNCMI2010("cer_no","抵质押物.身份证号码","20","0",null,"");
    public static final BaseXmlBeanEnum com_loan_card = new Socket_99CHNCMI2010("com_loan_card","抵质押物.贷款卡号","16","1",null,"1");
    public static final BaseXmlBeanEnum gage_type = new Socket_99CHNCMI2010("gage_type","抵质押物.抵质押物类型","5","1",null,"");
    public static final BaseXmlBeanEnum gage_name = new Socket_99CHNCMI2010("gage_name","抵质押物.抵质押品名称","120","0",null,"");
    public static final BaseXmlBeanEnum right_cert_type_code = new Socket_99CHNCMI2010("right_cert_type_code","抵质押物.权属证件类型","5","1",null,"");
    public static final BaseXmlBeanEnum right_cert_no = new Socket_99CHNCMI2010("right_cert_no","抵质押物.权属证件编号","500","1",null,"");
    public static final BaseXmlBeanEnum right_org = new Socket_99CHNCMI2010("right_org","抵质押物.权属登记机关","80","0",null,"");
    public static final BaseXmlBeanEnum currency = new Socket_99CHNCMI2010("currency","抵质押物.币种","3","1",null,"CNY");
    public static final BaseXmlBeanEnum eval_type = new Socket_99CHNCMI2010("eval_type","抵质押物.评估方式","5","0",null,"");
    public static final BaseXmlBeanEnum eval_org = new Socket_99CHNCMI2010("eval_org","抵质押物.评估机构","120","1",null,"");
    public static final BaseXmlBeanEnum eval_amt = new Socket_99CHNCMI2010("eval_amt","抵质押物.抵质押物估价","18","1",null,"");
    public static final BaseXmlBeanEnum eval_date = new Socket_99CHNCMI2010("eval_date","抵质押物.评估日期","10","1",null,"");
    public static final BaseXmlBeanEnum book_amt = new Socket_99CHNCMI2010("book_amt","抵质押物.内部评估金额","18","1",null,"");
    public static final BaseXmlBeanEnum mortagage_max_rate = new Socket_99CHNCMI2010("mortagage_max_rate","抵质押物.抵质押率","18","0",null,"");
    public static final BaseXmlBeanEnum mortagage_rate = new Socket_99CHNCMI2010("mortagage_rate","抵质押物.抵质押率","18","0",null,"");
    public static final BaseXmlBeanEnum max_mortagage_amt = new Socket_99CHNCMI2010("max_mortagage_amt","抵质押物.抵质押金额","18","1",null,"");
    public static final BaseXmlBeanEnum area_location = new Socket_99CHNCMI2010("area_location","抵质押物.存放地点","80","0",null,"");
    public static final BaseXmlBeanEnum if_common_asset = new Socket_99CHNCMI2010("if_common_asset","抵质押物.是否共同产权","1","0",null,"");
    public static final BaseXmlBeanEnum ref_name = new Socket_99CHNCMI2010("ref_name","抵质押物.共有权人名称","250","0",null,"");
    public static final BaseXmlBeanEnum if_disputed = new Socket_99CHNCMI2010("if_disputed","抵质押物.是否权益争议","1","0",null,"");
    public static final BaseXmlBeanEnum if_special_asset = new Socket_99CHNCMI2010("if_special_asset","是否保全资产","1","0",null,"");
    public static final BaseXmlBeanEnum tenancy_circe = new Socket_99CHNCMI2010("tenancy_circe","抵质押物.是否租赁","1","0",null,"");
    public static final BaseXmlBeanEnum tenancy_date = new Socket_99CHNCMI2010("tenancy_date","抵质押物.租赁结束时间","10","0",null,"");
    public static final BaseXmlBeanEnum tenancy_amt = new Socket_99CHNCMI2010("tenancy_amt","年租金（元）","18","0",null,"");
    public static final BaseXmlBeanEnum keep_user = new Socket_99CHNCMI2010("keep_user","保管人","80","0",null,"");
    public static final BaseXmlBeanEnum appl_in_date = new Socket_99CHNCMI2010("appl_in_date","申请入库时间","10","0",null,"");
    public static final BaseXmlBeanEnum in_date = new Socket_99CHNCMI2010("in_date","入库时间","10","0",null,"");
    public static final BaseXmlBeanEnum appl_out_date = new Socket_99CHNCMI2010("appl_out_date","申请出库时间","10","0",null,"");
    public static final BaseXmlBeanEnum out_date = new Socket_99CHNCMI2010("out_date","出库时间","10","0",null,"");
    public static final BaseXmlBeanEnum out_reason = new Socket_99CHNCMI2010("out_reason","出库事由","250","0",null,"");
//    public static final BaseXmlBeanEnum book_no = new Socket_99CHNCMI2010("book_no","抵押登记编号","100","0",null,"");
    public static final BaseXmlBeanEnum book_no = new Socket_99CHNCMI2010("book_no","抵质押物.他项权证号","100","0",null,"");
    public static final BaseXmlBeanEnum book_org = new Socket_99CHNCMI2010("book_org","抵质押物.抵押登记机关","60","1",null,"");
    public static final BaseXmlBeanEnum book_date = new Socket_99CHNCMI2010("book_date","当前日期","10","1",null,"");
    public static final BaseXmlBeanEnum assurance_type = new Socket_99CHNCMI2010("assurance_type","抵质押物.投保险种","60","0",null,"");
    public static final BaseXmlBeanEnum assurance_no = new Socket_99CHNCMI2010("assurance_no","抵质押物.保单编号","100","0",null,"");
    public static final BaseXmlBeanEnum assurance_amt = new Socket_99CHNCMI2010("assurance_amt","抵质押物.保险金额","18","0",null,"");
    public static final BaseXmlBeanEnum assurance_date = new Socket_99CHNCMI2010("assurance_date","抵质押物.投保日期","10","0",null,"");
    public static final BaseXmlBeanEnum end_date = new Socket_99CHNCMI2010("end_date","抵质押物.保险到期日","10","0",null,"");
    public static final BaseXmlBeanEnum co_name = new Socket_99CHNCMI2010("co_name","抵质押物.保险公司名称","120","0",null,"");
//    public static final BaseXmlBeanEnum create_user_no = new Socket_99CHNCMI2010("create_user_no","创建人用户编号","20","0",null,"");
    public static final BaseXmlBeanEnum create_user_no = new Socket_99CHNCMI2010("create_user_no","抵质押物.登记人","20","0",null,"0059408");
//    public static final BaseXmlBeanEnum input_br_id = new Socket_99CHNCMI2010("input_br_id","登记机构","20","0",null,"");
    public static final BaseXmlBeanEnum input_br_id = new Socket_99CHNCMI2010("input_br_id","抵质押物.登记机构","20","0",null,"06044");
    public static final BaseXmlBeanEnum input_date = new Socket_99CHNCMI2010("input_date","抵质押物.登记日期","10","0",null,"");
    public static final BaseXmlBeanEnum status_code = new Socket_99CHNCMI2010("status_code","抵质押物状态","5","0",null,"10002");
    public static final BaseXmlBeanEnum core_guaranty_id = new Socket_99CHNCMI2010("core_guaranty_id","核心担保品编号","32","0",null,"");
    public static final BaseXmlBeanEnum core_guaranty_seq = new Socket_99CHNCMI2010("core_guaranty_seq","核心担保品序号","2","0",null,"");
    public static final BaseXmlBeanEnum used_amt = new Socket_99CHNCMI2010("used_amt","审批决议.决议金额","18","0",null,"");
    public static final BaseXmlBeanEnum depot_status = new Socket_99CHNCMI2010("depot_status","出入库状态","5","0",null,"10000");
//    public static final BaseXmlBeanEnum out_fina_br_id = new Socket_99CHNCMI2010("out_fina_br_id","表外账务机构","20","0",null,"");
    public static final BaseXmlBeanEnum out_fina_br_id = new Socket_99CHNCMI2010("out_fina_br_id","省信贷合同要素.账务机构","20","0",null,"06288");
    public static final BaseXmlBeanEnum core_value = new Socket_99CHNCMI2010("core_value","抵质押物.核心入账金额","18","0",null,"");
    public static final BaseXmlBeanEnum bat_impt_no = new Socket_99CHNCMI2010("bat_impt_no","批量导入编号","30","0",null,"");
    public static final BaseXmlBeanEnum last_eval_date = new Socket_99CHNCMI2010("last_eval_date","last_eval_date","10","0",null,"");
    public static final BaseXmlBeanEnum eval_expire_date = new Socket_99CHNCMI2010("eval_expire_date","eval_expire_date","10","0",null,"");
//    public static final BaseXmlBeanEnum other_cred_no = new Socket_99CHNCMI2010("other_cred_no","抵质押物.他项权证号","50","0",null,"");



}
