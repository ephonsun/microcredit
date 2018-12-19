package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *借款合同主表同步接口
 */
public class Socket_99CHNCMI2003 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2003(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2003.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2003.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }

    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2003("opt","操作类型","1","1",null,"A");
//    public static final BaseXmlBeanEnum cont_no = new Socket_99CHNCMI2003("cont_no","合同号","30","1",null,"");借款合同主表.业务流水号
    public static final BaseXmlBeanEnum cont_no = new Socket_99CHNCMI2003("cont_no","合同号","30","1",null,"");
    public static final BaseXmlBeanEnum serno = new Socket_99CHNCMI2003("serno","借款合同主表.业务流水号","40","1",null,"");
    public static final BaseXmlBeanEnum cont_type = new Socket_99CHNCMI2003("cont_type","省信贷合同要素.合同类型","1","1",null,"");
    public static final BaseXmlBeanEnum prd_pk = new Socket_99CHNCMI2003("prd_pk","调查结论.产品主键","32","0",null,"");

    public static final BaseXmlBeanEnum biz_type = new Socket_99CHNCMI2003("biz_type","调查结论.业务品种","6","0",null,"");
    public static final BaseXmlBeanEnum prd_name = new Socket_99CHNCMI2003("prd_name","调查结论.产品名称","60","0",null,"");
    public static final BaseXmlBeanEnum app_form = new Socket_99CHNCMI2003("app_form","表单类型","60","0",null,"");
//    public static final BaseXmlBeanEnum cust_no = new Socket_99CHNCMI2003("cust_no","个人信息.客户编码","30","1",null,"");
    public static final BaseXmlBeanEnum cust_no = new Socket_99CHNCMI2003("cust_no","个人信息.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum cust_name = new Socket_99CHNCMI2003("cust_name","个人信息.客户姓名","60","1",null,"");
    public static final BaseXmlBeanEnum biz_type_sub = new Socket_99CHNCMI2003("biz_type_sub","业务品种细分","8","0",null,"");
    public static final BaseXmlBeanEnum biz_type_detail = new Socket_99CHNCMI2003("biz_type_detail","调查结论.业务品种分类名称","100","0",null,"");
    public static final BaseXmlBeanEnum loan_form = new Socket_99CHNCMI2003("loan_form","调查结论.贷款形式","1","0",null,"");
    public static final BaseXmlBeanEnum loan_nature = new Socket_99CHNCMI2003("loan_nature","调查结论.贷款性质","2","0",null,"");
    public static final BaseXmlBeanEnum loan_kind_ca = new Socket_99CHNCMI2003("loan_kind_ca","企业贷款种类","2","0",null,"");
    public static final BaseXmlBeanEnum loan_type_ext = new Socket_99CHNCMI2003("loan_type_ext","关联交易类型","2","0",null,"");
    public static final BaseXmlBeanEnum loan_type_ext2 = new Socket_99CHNCMI2003("loan_type_ext2","关联交易描述","100","0",null,"");
    public static final BaseXmlBeanEnum currency_type = new Socket_99CHNCMI2003("currency_type","贷款合同.币种","3","1",null,"CNY");
    public static final BaseXmlBeanEnum approval_amt = new Socket_99CHNCMI2003("approval_amt","审批决议.决议金额","18","0",null,"");//审批金额
    public static final BaseXmlBeanEnum cont_amt = new Socket_99CHNCMI2003("cont_amt","审批决议.决议金额","18","1",null,"");//审批金额
    public static final BaseXmlBeanEnum total_issue_amt = new Socket_99CHNCMI2003("total_issue_amt","累计发放金额","18","0",null,"0");//累计发放金额
    public static final BaseXmlBeanEnum total_recyle_amt = new Socket_99CHNCMI2003("total_recyle_amt","累计回收金额","18","0",null,"0");//累计回收金额
    public static final BaseXmlBeanEnum avail_amt = new Socket_99CHNCMI2003("avail_amt","审批决议.决议金额","18","1",null,"0");//合同可用余额
    public static final BaseXmlBeanEnum term_time_type = new Socket_99CHNCMI2003("term_time_type","贷款合同.期限时间类型","3","0",null," 001");
    public static final BaseXmlBeanEnum loan_term = new Socket_99CHNCMI2003("loan_term","审批决议.决议期限","11","0",null,"");
    public static final BaseXmlBeanEnum loan_start_date = new Socket_99CHNCMI2003("loan_start_date","贷款合同.借款起始日期","10","1",null,"");
    public static final BaseXmlBeanEnum loan_end_date = new Socket_99CHNCMI2003("loan_end_date","贷款合同.借款终止日期","10","1",null,"");
    public static final BaseXmlBeanEnum ruling_ir = new Socket_99CHNCMI2003("ruling_ir","省信贷合同要素.基准利率","18","0",null,"");
    public static final BaseXmlBeanEnum cal_floating_rate = new Socket_99CHNCMI2003("cal_floating_rate","省信贷合同要素.利率浮动比","16","0",null,"0");
    public static final BaseXmlBeanEnum reality_ir_y = new Socket_99CHNCMI2003("reality_ir_y","省信贷合同要素.执行利率","25","0",null,"0");
    public static final BaseXmlBeanEnum overdue_rate = new Socket_99CHNCMI2003("overdue_rate","省信贷合同要素.逾期加罚率","14","0",null,"");
    public static final BaseXmlBeanEnum overdue_ir = new Socket_99CHNCMI2003("overdue_ir","省信贷合同要素.逾期月利率","25","0",null,"");
    public static final BaseXmlBeanEnum default_rate = new Socket_99CHNCMI2003("default_rate","省信贷合同要素.违约加罚率","14","0",null,"");
    public static final BaseXmlBeanEnum default_ir = new Socket_99CHNCMI2003("default_ir","省信贷合同要素.违约月利率","25","0",null,"");
    public static final BaseXmlBeanEnum ci_rate = new Socket_99CHNCMI2003("ci_rate","省信贷合同要素.复利加罚率","14","0",null,"");
    public static final BaseXmlBeanEnum ci_ir = new Socket_99CHNCMI2003("ci_ir","省信贷合同要素.复利月利率","25","0",null,"");
    public static final BaseXmlBeanEnum ir_adjust_mode = new Socket_99CHNCMI2003("ir_adjust_mode","省信贷合同要素.利率调整方式","1","0",null,"");
    public static final BaseXmlBeanEnum interest_acc_mode = new Socket_99CHNCMI2003("interest_acc_mode","省信贷合同要素.结息周期","2","0",null,"01");
    public static final BaseXmlBeanEnum chargeoff_ind = new Socket_99CHNCMI2003("chargeoff_ind","省信贷合同要素.出帐方式","1","0",null,"");
    public static final BaseXmlBeanEnum discont_ind = new Socket_99CHNCMI2003("discont_ind","省信贷合同要素.是否贴息","1","0",null,"");
    public static final BaseXmlBeanEnum discount_id = new Socket_99CHNCMI2003("discount_id","省信贷合同要素.贴息方编号","25","0",null,"");
    public static final BaseXmlBeanEnum mortgage_flg = new Socket_99CHNCMI2003("mortgage_flg","省信贷合同要素.按揭标识","1","0",null,"");

    public static final BaseXmlBeanEnum repayment_mode = new Socket_99CHNCMI2003("repayment_mode","审批决议.还款方式","3","0",
            new HashMap<String, String>(){{put("1","201");put("2","102");put("3","101");put("4","202");put("5","202");}},"");
    public static final BaseXmlBeanEnum repayment_period = new Socket_99CHNCMI2003("repayment_period","还款频率","2","0",null,"01");

    public static final BaseXmlBeanEnum assure_means_main = new Socket_99CHNCMI2003("assure_means_main","审批决议.担保方式","2","1",
            new HashMap<String, String>(){{put("01","00");put("02","30");put("03","10");put("04","20");}},"");
    public static final BaseXmlBeanEnum assure_means2 = new Socket_99CHNCMI2003("assure_means2","调查结论.担保方式2","2","0",
            new HashMap<String, String>(){{put("01","00");put("02","30");put("03","10");put("04","20");}},"");
    public static final BaseXmlBeanEnum assure_means3 = new Socket_99CHNCMI2003("assure_means3","调查结论.担保方式3","2","0",
            new HashMap<String, String>(){{put("01","00");put("02","30");put("03","10");put("04","20");}},"");
    public static final BaseXmlBeanEnum loan_direction = new Socket_99CHNCMI2003("loan_direction","调查结论.贷款投向","4","1",null,"xfd");
    public static final BaseXmlBeanEnum direction_name = new Socket_99CHNCMI2003("direction_name","调查结论.投向名称","200","1",null,"消费贷");
    public static final BaseXmlBeanEnum agriculture_type = new Socket_99CHNCMI2003("agriculture_type","调查结论.是否涉农","2","0",null,"");
    public static final BaseXmlBeanEnum agriculture_use = new Socket_99CHNCMI2003("agriculture_use","涉农用途","4","0",null,"");
    public static final BaseXmlBeanEnum minor_entrp_range = new Socket_99CHNCMI2003("minor_entrp_range","小企业统计口径(银监)","1","0",null,"");
    public static final BaseXmlBeanEnum loan_use_type = new Socket_99CHNCMI2003("loan_use_type","借款用途类型","3","0",null,"900");
    //01	个人经营 02	个人消费 03	其他  申请信息.使用明细
    public static final BaseXmlBeanEnum use_dec = new Socket_99CHNCMI2003("use_dec","申请信息.贷款用途","200","0",
            new HashMap<String, String>(){{put("01","个人经营");put("02","个人消费");put("03","其他");}},"");
    public static final BaseXmlBeanEnum repayment_src_dec = new Socket_99CHNCMI2003("repayment_src_dec","申请信息.还款来源","200","1",null,"");
    public static final BaseXmlBeanEnum limit_ind = new Socket_99CHNCMI2003("limit_ind","授信额度使用标志","1","0",null,"");
    public static final BaseXmlBeanEnum guarantora_grp_ind = new Socket_99CHNCMI2003("guarantora_grp_ind","省信贷合同要素.联保贷款标志","1","1",null,"2");
    public static final BaseXmlBeanEnum copartner_ind = new Socket_99CHNCMI2003("copartner_ind","市场合作方标志","1","0",null,"");
    public static final BaseXmlBeanEnum loan_card_ind = new Socket_99CHNCMI2003("loan_card_ind","使用农户贷款证标志","1","0",null,"");
    public static final BaseXmlBeanEnum entrust_ind = new Socket_99CHNCMI2003("entrust_ind","委托方贷款标识","1","0",null,"");
    public static final BaseXmlBeanEnum loan_type1 = new Socket_99CHNCMI2003("loan_type1","借款分类1","6","0",null,"");
    public static final BaseXmlBeanEnum loan_type2 = new Socket_99CHNCMI2003("loan_type2","借款分类2","6","0",null,"");
    public static final BaseXmlBeanEnum loan_type3 = new Socket_99CHNCMI2003("loan_type3","借款分类3","6","0",null,"");
    public static final BaseXmlBeanEnum loan_type4 = new Socket_99CHNCMI2003("loan_type4","借款分类4","6","0",null,"");
    public static final BaseXmlBeanEnum loan_type5 = new Socket_99CHNCMI2003("loan_type5","借款分类5","6","0",null,"");
    public static final BaseXmlBeanEnum loan_type6 = new Socket_99CHNCMI2003("loan_type6","借款分类6","6","0",null,"");
    public static final BaseXmlBeanEnum final_endorse_br_id = new Socket_99CHNCMI2003("final_endorse_br_id","省信贷合同要素.主管机构","20","0",null,"");//最终审批机构
    public static final BaseXmlBeanEnum final_endorse_date = new Socket_99CHNCMI2003("final_endorse_date","贷款审批时间","20","0",null,"");
    public static final BaseXmlBeanEnum final_endorse_id = new Socket_99CHNCMI2003("final_endorse_id","最终审批人","20","0",null,"");
    public static final BaseXmlBeanEnum sign_date = new Socket_99CHNCMI2003("sign_date","合同签订日期","10","0",null,"");
    public static final BaseXmlBeanEnum change_date = new Socket_99CHNCMI2003("change_date","当前日期","10","0",null,"");
    public static final BaseXmlBeanEnum cont_state = new Socket_99CHNCMI2003("cont_state","合同状态","3","1",null,"200");
    public static final BaseXmlBeanEnum input_id = new Socket_99CHNCMI2003("input_id","个人信息.主管客户经理","20","0",null,"0059408");
    public static final BaseXmlBeanEnum cust_mgr = new Socket_99CHNCMI2003("cust_mgr","个人信息.主管客户经理","20","1",null,"0059408");
    public static final BaseXmlBeanEnum input_br_id = new Socket_99CHNCMI2003("input_br_id","省信贷合同要素.主管机构","20","0",null,"06044");
    public static final BaseXmlBeanEnum main_br_id = new Socket_99CHNCMI2003("main_br_id","省信贷合同要素.主管机构","20","1",null,"06044");
    public static final BaseXmlBeanEnum fina_br_id = new Socket_99CHNCMI2003("fina_br_id","省信贷合同要素.账务机构","20","1",null,"06288");
    public static final BaseXmlBeanEnum old_bill_no = new Socket_99CHNCMI2003("old_bill_no","原借据号","30","0",null,"");
    public static final BaseXmlBeanEnum cla_result = new Socket_99CHNCMI2003("cla_result","风险分类初分结果","2","0",null,"");
    public static final BaseXmlBeanEnum cla_suggestion = new Socket_99CHNCMI2003("cla_suggestion","风险分类初分意见","500","0",null,"");
    public static final BaseXmlBeanEnum ir_exe_type = new Socket_99CHNCMI2003("ir_exe_type","省信贷合同要素.利率执行方式","1","0",null,"3");
    public static final BaseXmlBeanEnum responsible_man = new Socket_99CHNCMI2003("responsible_man","责任人","80","0",null,"");
    public static final BaseXmlBeanEnum government_ind = new Socket_99CHNCMI2003("government_ind","调查结论.是否涉政","1","0",null,"");
    public static final BaseXmlBeanEnum government_org_attribute = new Socket_99CHNCMI2003("government_org_attribute","涉政机构属性","4","0",null,"");
    public static final BaseXmlBeanEnum government_platform = new Socket_99CHNCMI2003("government_platform","是否为涉政平台","4","0",null,"");
    public static final BaseXmlBeanEnum int_rate_type = new Socket_99CHNCMI2003("int_rate_type","省信贷合同要素.利率类型","1","0",null,"");
    public static final BaseXmlBeanEnum int_rate_inc_opt = new Socket_99CHNCMI2003("int_rate_inc_opt","省信贷合同要素.利率增量选项","1","0",null,"");
    public static final BaseXmlBeanEnum int_rate_inc = new Socket_99CHNCMI2003("int_rate_inc","省信贷合同要素.利率增量","25","0",null,"0");
    public static final BaseXmlBeanEnum fixed_rate = new Socket_99CHNCMI2003("fixed_rate","省信贷合同要素.固定利率","25","0",null,"0");
    public static final BaseXmlBeanEnum prin_int_rate_inc_opt = new Socket_99CHNCMI2003("prin_int_rate_inc_opt","省信贷合同要素.本金罚息利率增量选项","1","0",null,"2");
    public static final BaseXmlBeanEnum prin_int_rate_inc = new Socket_99CHNCMI2003("prin_int_rate_inc","省信贷合同要素.本金罚息利率增量","25","0",null,"0.5");
    public static final BaseXmlBeanEnum prin_fixed_rate = new Socket_99CHNCMI2003("prin_fixed_rate","省信贷合同要素.本金固定罚息率","25","0",null,"0");
    public static final BaseXmlBeanEnum unpd_int_rate_inc_opt = new Socket_99CHNCMI2003("unpd_int_rate_inc_opt","省信贷合同要素.利息罚息利率增量选项","1","0",null,"2");
    public static final BaseXmlBeanEnum unpd_int_rate_inc = new Socket_99CHNCMI2003("unpd_int_rate_inc","省信贷合同要素.利息罚息利率增量","25","0",null,"0.5");
    public static final BaseXmlBeanEnum unpd_fixed_rate = new Socket_99CHNCMI2003("unpd_fixed_rate","省信贷合同要素.利息固定罚息率","25","0",null,"0");
    public static final BaseXmlBeanEnum comp_int_rate_inc_opt = new Socket_99CHNCMI2003("comp_int_rate_inc_opt","省信贷合同要素.复利罚息利率增量选项","1","0",null,"2");
    public static final BaseXmlBeanEnum comp_int_rate_inc = new Socket_99CHNCMI2003("comp_int_rate_inc","省信贷合同要素.复利罚息利率增量","25","0",null,"0.5");
    public static final BaseXmlBeanEnum comp_fixed_rate = new Socket_99CHNCMI2003("comp_fixed_rate","省信贷合同要素.复利固定罚息率","25","0",null,"0");
    public static final BaseXmlBeanEnum appr_int_rate_inc = new Socket_99CHNCMI2003("appr_int_rate_inc","省信贷合同要素.挪用利率罚息利率增量","25","0",null,"1");
    public static final BaseXmlBeanEnum prd_userdf_type = new Socket_99CHNCMI2003("prd_userdf_type","调查结论.产品自定义名称","60","0",null,"");//产品自定义类型
    public static final BaseXmlBeanEnum prd_userdf_name = new Socket_99CHNCMI2003("prd_userdf_name","调查结论.产品自定义名称","60","0",null,"");
    public static final BaseXmlBeanEnum consign_loan_bgl_acct_no = new Socket_99CHNCMI2003("consign_loan_bgl_acct_no","委托贷款bgl帐号","16","0",null,"");
    public static final BaseXmlBeanEnum funds_src = new Socket_99CHNCMI2003("funds_src","资金来源","2","0",null,"");
    public static final BaseXmlBeanEnum chargeoff_manage = new Socket_99CHNCMI2003("chargeoff_manage","省信贷合同要素.出帐负责人","20","1",null,"");
    public static final BaseXmlBeanEnum enter_account = new Socket_99CHNCMI2003("enter_account","贷款合同.入账账号","32","0",null,"");
    public static final BaseXmlBeanEnum enter_account_name = new Socket_99CHNCMI2003("enter_account_name","贷款合同.还款账户名称","80","0",null,"");
    public static final BaseXmlBeanEnum repayment_account = new Socket_99CHNCMI2003("repayment_account","贷款合同.还款帐号","32","0",null,"");
    public static final BaseXmlBeanEnum repayment_acc_name = new Socket_99CHNCMI2003("repayment_acc_name","贷款合同.还款账户名称","80","0",null,"");
    public static final BaseXmlBeanEnum prd_type = new Socket_99CHNCMI2003("prd_type","prd_type","2","0",null,"");
    public static final BaseXmlBeanEnum Ispaf = new Socket_99CHNCMI2003("Ispaf","是否住房公积金贷款","1","0",null,"");
    public static final BaseXmlBeanEnum cosurety_cont_no = new Socket_99CHNCMI2003("cosurety_cont_no","联保协议编号","30","0",null,"");
    public static final BaseXmlBeanEnum lead_arranger_ind = new Socket_99CHNCMI2003("lead_arranger_ind","牵头行标志","1","0",null,"");
    public static final BaseXmlBeanEnum cn_cont_no = new Socket_99CHNCMI2003("cn_cont_no","合同编码","60","0",null,"");
    public static final BaseXmlBeanEnum fldvalue01 = new Socket_99CHNCMI2003("fldvalue01","省信贷合同要素.汇率","250","1",null,"1");
    public static final BaseXmlBeanEnum fldvalue02 = new Socket_99CHNCMI2003("fldvalue02","fldvalue02","250","0",null,"");
    public static final BaseXmlBeanEnum Seot = new Socket_99CHNCMI2003("Seot","省信贷合同要素.专营中心业务类型","1","0",null,"");
    public static final BaseXmlBeanEnum Fhf = new Socket_99CHNCMI2003("Fhf","首套房标识","1","0",null,"");
    public static final BaseXmlBeanEnum ori_loan_bal = new Socket_99CHNCMI2003("ori_loan_bal","原贷款余额","18","0",null,"");
    public static final BaseXmlBeanEnum indus_adj_type = new Socket_99CHNCMI2003("indus_adj_type","产业结构调整类型","1","0",null,"");
    public static final BaseXmlBeanEnum indus_upd_flag = new Socket_99CHNCMI2003("indus_upd_flag","省信贷合同要素.工业转型升级标识","1","0",null,"");
    public static final BaseXmlBeanEnum stra_indus_type = new Socket_99CHNCMI2003("stra_indus_type","战略新兴产业类型","1","0",null,"");
    public static final BaseXmlBeanEnum portfolio_ind = new Socket_99CHNCMI2003("portfolio_ind","是否组合贷款","1","0",null,"");
    public static final BaseXmlBeanEnum business_line = new Socket_99CHNCMI2003("business_line","调查结论.业务条线","4","0",null,"");
    public static final BaseXmlBeanEnum business_line_name = new Socket_99CHNCMI2003("business_line_name","调查结论.业务条线名称","50","0",null,"");
    public static final BaseXmlBeanEnum main_pro_type = new Socket_99CHNCMI2003("main_pro_type","调查结论.主要产品分类","4","0",null,"");
    public static final BaseXmlBeanEnum main_pro_type_name = new Socket_99CHNCMI2003("main_pro_type_name","调查结论.主要产品分类名称","50","0",null,"");
    public static final BaseXmlBeanEnum sub_pro_type = new Socket_99CHNCMI2003("sub_pro_type","次要产品分类","50","0",null,"");
    public static final BaseXmlBeanEnum sub_pro_type_name = new Socket_99CHNCMI2003("sub_pro_type_name","次要产品分类名称","400","0",null,"");

    public static final BaseXmlBeanEnum main_flag = new Socket_99CHNCMI2003("main_flag","经营主体.投向背景主体","1","0",null,"");
    public static final BaseXmlBeanEnum register_no = new Socket_99CHNCMI2003("register_no","经营主体.社会代码","30","0",null,"");
    public static final BaseXmlBeanEnum register_name = new Socket_99CHNCMI2003("register_name","经营主体.企业名称","80","0",null,"");





}
