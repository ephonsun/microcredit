package banger.util.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  对私客户信息同步接口（担保人）
 */
public class Socket_99CHNCMI2039_1 extends BaseXmlBeanEnum {

    public Socket_99CHNCMI2039_1(String elementName, String elementColumnName, String elementLength, String elementNotNull, Map<String, String> elementOptions, String elementDefaultValue) {
        super(elementName, elementColumnName, elementLength, elementNotNull, elementOptions, elementDefaultValue);
    }

    public static List<BaseXmlBeanEnum> getAllElement() {
        List<BaseXmlBeanEnum> list = null;
        try {
            list = BaseXmlBeanEnum.getEnumList(Socket_99CHNCMI2039_1.class);
        }catch (Exception e){
        }
        return list;
    }

    public static String getSocketCode() {
        return Socket_99CHNCMI2039_1.class.getName().split("_")[1];
    }

    public static BaseXmlBeanEnum getElementByName(String elementName) throws Exception{
        BaseXmlBeanEnum baseXmlBeanEnum = (BaseXmlBeanEnum) BaseXmlBeanEnum.getEnum(BaseXmlBeanEnum.class, elementName);
        return  baseXmlBeanEnum;
    }
    //字段，标记，长度，是否必填，select转换，默认值
    public static final BaseXmlBeanEnum opt = new Socket_99CHNCMI2039_1("opt","操作类型","1","1",null,"A");
    public static final BaseXmlBeanEnum inner_cus_id = new Socket_99CHNCMI2039_1("inner_cus_id","担保人.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum cus_id = new Socket_99CHNCMI2039_1("cus_id","担保人.客户编码","17","1",null,"");
    public static final BaseXmlBeanEnum mng_br_id = new Socket_99CHNCMI2039_1("mng_br_id","个人信息.所属法人机构","20","1",null,"06");
    public static final BaseXmlBeanEnum cus_type = new Socket_99CHNCMI2039_1("cus_type","担保人.客户类型","3","1",new HashMap<String, String>(){{put("1","260");put("2","110");put("3","130");}},"110");
    public static final BaseXmlBeanEnum cus_name = new Socket_99CHNCMI2039_1("cus_name","担保人.姓名","80","1",null,"");
    public static final BaseXmlBeanEnum indiv_sex = new Socket_99CHNCMI2039_1("indiv_sex","担保人.性别","1","1",null,"4");
    public static final BaseXmlBeanEnum cert_type = new Socket_99CHNCMI2039_1("cert_type","担保人.证件类型","2","1",
            new HashMap<String, String>(){{
                put("1","10");put("2","31");put("3","11");put("4","12");
                put("5","12");put("6","13");put("7","14");put("8","30");
                put("9","34");put("10","32");put("11","1X");
            }},"");
    public static final BaseXmlBeanEnum cert_code = new Socket_99CHNCMI2039_1("cert_code","担保人.证件号码","20","1",null,"");
    public static final BaseXmlBeanEnum indiv_id_exp_dt = new Socket_99CHNCMI2039_1("indiv_id_exp_dt","证件到期日","10","0",null,"");
    public static final BaseXmlBeanEnum agri_flg = new Socket_99CHNCMI2039_1("agri_flg","是否农户","1","0",null,"");
    public static final BaseXmlBeanEnum cus_bank_rel = new Socket_99CHNCMI2039_1("cus_bank_rel","与我行关联关系","2","0",null,"");
    public static final BaseXmlBeanEnum com_hold_stk_amt = new Socket_99CHNCMI2039_1("com_hold_stk_amt","拥有我行股份金额","18","0",null,"");
    public static final BaseXmlBeanEnum bank_duty = new Socket_99CHNCMI2039_1("bank_duty","在我行职务","1","0",null,"");
    public static final BaseXmlBeanEnum indiv_ntn = new Socket_99CHNCMI2039_1("indiv_ntn","民族","2","0",null,"");
    public static final BaseXmlBeanEnum indiv_brt_place = new Socket_99CHNCMI2039_1("indiv_brt_place","籍贯","60","0",null,"");
    public static final BaseXmlBeanEnum indiv_houh_reg_add = new Socket_99CHNCMI2039_1("indiv_houh_reg_add","担保人.户籍地址","60","0",null,"");
    public static final BaseXmlBeanEnum indiv_dt_of_birth = new Socket_99CHNCMI2039_1("indiv_dt_of_birth","担保人.出生日期","10","0",null,"");
    public static final BaseXmlBeanEnum indiv_pol_st = new Socket_99CHNCMI2039_1("indiv_pol_st","政治面貌","2","0",null,"");
    public static final BaseXmlBeanEnum indiv_edt = new Socket_99CHNCMI2039_1("indiv_edt","担保人.学历","2","0",
            new HashMap<String, String>(){{
                put("1","99");put("2","10");put("3","20");put("4","30");put("5","40");put("6","60");
                put("7","70");put("8","80");put("9","99");put("10","99");put("11","50");put("12","90");
            }},"");
    public static final BaseXmlBeanEnum indiv_dgr = new Socket_99CHNCMI2039_1("indiv_dgr","担保人.学历","1","0",
            new HashMap<String, String>(){{
                put("1","2");put("2","3");put("3","4");put("4","0");put("5","0");put("6","0");
                put("7","0");put("8","0");put("9","0");put("10","0");put("11","0");put("12","0");
            }},"");
    public static final BaseXmlBeanEnum indiv_mar_st = new Socket_99CHNCMI2039_1("indiv_mar_st","担保人.婚姻状况","2","0",null,"");
    public static final BaseXmlBeanEnum indiv_heal_st = new Socket_99CHNCMI2039_1("indiv_heal_st","健康状况","1","0",null,"");
    public static final BaseXmlBeanEnum post_addr = new Socket_99CHNCMI2039_1("post_addr","担保人.居住地址","80","1",null,"");
    public static final BaseXmlBeanEnum post_code = new Socket_99CHNCMI2039_1("post_code","个人信息.居住地址邮编","6","1",null,"");
    public static final BaseXmlBeanEnum area_code = new Socket_99CHNCMI2039_1("area_code","区域编码","12","0",null,"");
    public static final BaseXmlBeanEnum area_name = new Socket_99CHNCMI2039_1("area_name","区域名称","100","0",null,"");
    public static final BaseXmlBeanEnum phone = new Socket_99CHNCMI2039_1("phone","担保人.联系电话","35","0",null,"");
    public static final BaseXmlBeanEnum fphone = new Socket_99CHNCMI2039_1("fphone","担保人.家庭电话","35","0",null,"");
    public static final BaseXmlBeanEnum mobile = new Socket_99CHNCMI2039_1("mobile","担保人.联系电话","35","0",null,"");
    public static final BaseXmlBeanEnum fax_code = new Socket_99CHNCMI2039_1("fax_code","传真","35","0",null,"");
    public static final BaseXmlBeanEnum email = new Socket_99CHNCMI2039_1("email","email地址","80","0",null,"");
    public static final BaseXmlBeanEnum indiv_rsd_addr = new Socket_99CHNCMI2039_1("indiv_rsd_addr","担保人.居住地址","60","0",null,"");
    public static final BaseXmlBeanEnum indiv_zip_code = new Socket_99CHNCMI2039_1("indiv_zip_code","个人信息.居住地址邮编","6","1",null,"");
    public static final BaseXmlBeanEnum indiv_rsd_st = new Socket_99CHNCMI2039_1("indiv_rsd_st","个人信息.居住状况","1","0",null,"");
    public static final BaseXmlBeanEnum indiv_soc_scr = new Socket_99CHNCMI2039_1("indiv_soc_scr","社会保障情况","60","0",null,"");
    public static final BaseXmlBeanEnum indiv_hobby = new Socket_99CHNCMI2039_1("indiv_hobby","爱好","200","0",null,"");
    public static final BaseXmlBeanEnum indiv_occ = new Socket_99CHNCMI2039_1("indiv_occ","从事职业","1","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_name = new Socket_99CHNCMI2039_1("indiv_com_name","个人信息.工作单位","60","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_typ = new Socket_99CHNCMI2039_1("indiv_com_typ","单位性质","3","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_fld = new Socket_99CHNCMI2039_1("indiv_com_fld","所属行业","5","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_phn = new Socket_99CHNCMI2039_1("indiv_com_phn","个人信息.单位电话","25","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_fax = new Socket_99CHNCMI2039_1("indiv_com_fax","单位传真","25","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_addr = new Socket_99CHNCMI2039_1("indiv_com_addr","个人信息.单位地址","80","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_zip_code = new Socket_99CHNCMI2039_1("indiv_com_zip_code","单位邮编","6","0",null,"");
    public static final BaseXmlBeanEnum indiv_com_cnt_name = new Socket_99CHNCMI2039_1("indiv_com_cnt_name","单位联系人","30","0",null,"");
    public static final BaseXmlBeanEnum indiv_work_job_y = new Socket_99CHNCMI2039_1("indiv_work_job_y","个人信息.工作起始年份","10","0",null,"");

    public static final BaseXmlBeanEnum indiv_com_job_ttl = new Socket_99CHNCMI2039_1("indiv_com_job_ttl","个人信息.职务","1","1",
            new HashMap<String, String>(){{
                put("01","9");put("02","2");put("03","2");put("04","2");put("05","2");put("06","3");
                put("07","1");put("8","1");put("9","2");put("10","1");put("11","1");put("12","1");
                put("13","2");put("14","2");put("15","2");put("16","2");put("17","3");put("18","3");
                put("19","1");put("20","1");put("21","1");put("22","2");put("23","2");
            }},"");
    public static final BaseXmlBeanEnum indiv_crtfctn = new Socket_99CHNCMI2039_1("indiv_crtfctn","个人信息.职称","1","1",
            new HashMap<String, String>(){{
                put("1","0");put("2","3");put("3","3");put("4","2");put("5","1");put("6","3");
                put("7","3");put("8","2");put("9","3");put("10","3");put("11","2");put("12","1");
                put("13","3");put("14","2");put("15","1");
            }},"");

    public static final BaseXmlBeanEnum indiv_ann_incm = new Socket_99CHNCMI2039_1("indiv_ann_incm","年收入情况","18","0",null,"");
    public static final BaseXmlBeanEnum indiv_sal_acc_bank = new Socket_99CHNCMI2039_1("indiv_sal_acc_bank","工资账户开户行","80","0",null,"");
    public static final BaseXmlBeanEnum indiv_sal_acc_no = new Socket_99CHNCMI2039_1("indiv_sal_acc_no","工资账号","32","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_name = new Socket_99CHNCMI2039_1("indiv_sps_name","担保人配偶信息.姓名","80","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_id_typ = new Socket_99CHNCMI2039_1("indiv_sps_id_typ","担保人配偶信息.证件类型","2","0",
            new HashMap<String, String>(){{
                put("1","10");put("2","31");put("3","11");put("4","12");
                put("5","12");put("6","13");put("7","14");put("8","30");
                put("9","34");put("10","32");put("11","1X");
            }},"");
    public static final BaseXmlBeanEnum indiv_sps_id_code = new Socket_99CHNCMI2039_1("indiv_sps_id_code","担保人配偶信息.证件号码","20","0",null,"");
    public static final BaseXmlBeanEnum indiv_scom_name = new Socket_99CHNCMI2039_1("indiv_scom_name","担保人配偶信息.工作单位","60","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_occ = new Socket_99CHNCMI2039_1("indiv_sps_occ","担保人配偶信息.职业","1","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_duty = new Socket_99CHNCMI2039_1("indiv_sps_duty","担保人配偶信息.职务","1","0",
            new HashMap<String, String>(){{
                put("01","9");put("02","2");put("03","2");put("04","2");put("05","2");put("06","3");
                put("07","1");put("8","1");put("9","2");put("10","1");put("11","1");put("12","1");
                put("13","2");put("14","2");put("15","2");put("16","2");put("17","3");put("18","3");
                put("19","1");put("20","1");put("21","1");put("22","2");put("23","2");
            }},"");
    public static final BaseXmlBeanEnum indiv_sps_mincm = new Socket_99CHNCMI2039_1("indiv_sps_mincm","配偶年收入","18","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_phn = new Socket_99CHNCMI2039_1("indiv_sps_phn","配偶单位联系电话","35","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_mphn = new Socket_99CHNCMI2039_1("indiv_sps_mphn","担保人担保人.联系电话","35","0",null,"");
    public static final BaseXmlBeanEnum indiv_sps_job_dt = new Socket_99CHNCMI2039_1("indiv_sps_job_dt","配偶参加工作时间","10","0",null,"");
    public static final BaseXmlBeanEnum com_rel_dgr = new Socket_99CHNCMI2039_1("com_rel_dgr","与我行合作关系","2","0",null,"");
    public static final BaseXmlBeanEnum com_init_loan_date = new Socket_99CHNCMI2039_1("com_init_loan_date","建立信贷关系时间","10","0",null,"");
    public static final BaseXmlBeanEnum indiv_hld_acnt = new Socket_99CHNCMI2039_1("indiv_hld_acnt","在我行开立账户情况","1","0",null,"");
    public static final BaseXmlBeanEnum hold_card = new Socket_99CHNCMI2039_1("hold_card","持卡情况","1","0",null,"");
    public static final BaseXmlBeanEnum passport_flg = new Socket_99CHNCMI2039_1("passport_flg","是否拥有外国护照或居住权","1","0",null,"");
    public static final BaseXmlBeanEnum crd_grade = new Socket_99CHNCMI2039_1("crd_grade","信用等级","2","0",null,"");
    public static final BaseXmlBeanEnum crd_date = new Socket_99CHNCMI2039_1("crd_date","信用评定日期","10","0",null,"");
    public static final BaseXmlBeanEnum remark = new Socket_99CHNCMI2039_1("remark","备注","250","0",null,"");
    public static final BaseXmlBeanEnum cust_mgr = new Socket_99CHNCMI2039_1("cust_mgr","个人信息.主管客户经理","20","1",null,"0059408");
    public static final BaseXmlBeanEnum main_br_id = new Socket_99CHNCMI2039_1("main_br_id","个人信息.主管机构","20","1",null,"06044");
    public static final BaseXmlBeanEnum input_id = new Socket_99CHNCMI2039_1("input_id","登记人","20","0",null,"");
    public static final BaseXmlBeanEnum input_br_id = new Socket_99CHNCMI2039_1("input_br_id","登记机构","20","0",null,"");
    public static final BaseXmlBeanEnum input_date = new Socket_99CHNCMI2039_1("input_date","登记日期","10","0",null,"");
    public static final BaseXmlBeanEnum last_upd_id = new Socket_99CHNCMI2039_1("last_upd_id","更新人","20","0",null,"");
    public static final BaseXmlBeanEnum last_upd_date = new Socket_99CHNCMI2039_1("last_upd_date","更新日期","10","0",null,"");
    public static final BaseXmlBeanEnum cus_status = new Socket_99CHNCMI2039_1("cus_status","状态","2","1",null,"03");
    public static final BaseXmlBeanEnum indiv_com_fld_name = new Socket_99CHNCMI2039_1("indiv_com_fld_name","单位所属行业名称","200","0",null,"");
    public static final BaseXmlBeanEnum crd_end_dt = new Socket_99CHNCMI2039_1("crd_end_dt","信用等级到期日期","10","0",null,"");
    public static final BaseXmlBeanEnum indiv_psp_crtfctn = new Socket_99CHNCMI2039_1("indiv_psp_crtfctn","担保人配偶信息.职称","1","0",
            new HashMap<String, String>(){{
                put("1","0");put("2","3");put("3","3");put("4","2");put("5","1");put("6","3");
                put("7","3");put("8","2");put("9","3");put("10","3");put("11","2");put("12","1");
                put("13","3");put("14","2");put("15","1");
            }},"");
    public static final BaseXmlBeanEnum indiv_sps_mar_code = new Socket_99CHNCMI2039_1("indiv_sps_mar_code","结婚证号码","20","0",null,"");
    public static final BaseXmlBeanEnum cus_id_rel = new Socket_99CHNCMI2039_1("cus_id_rel","配偶关联客户码","30","0",null,"");
    public static final BaseXmlBeanEnum work_resume = new Socket_99CHNCMI2039_1("work_resume","个人简历","250","0",null,"");
    public static final BaseXmlBeanEnum accredit_status = new Socket_99CHNCMI2039_1("accredit_status","授权状态","1","0",null,"");
    public static final BaseXmlBeanEnum former_name = new Socket_99CHNCMI2039_1("former_name","姓名","35","0",null,"");
    public static final BaseXmlBeanEnum lay_off_flag = new Socket_99CHNCMI2039_1("lay_off_flag","是否有工作单位","1","0",null,"");
    public static final BaseXmlBeanEnum lay_off_code = new Socket_99CHNCMI2039_1("lay_off_code","下岗证号","20","0",null,"");
    public static final BaseXmlBeanEnum loan_card_flg = new Socket_99CHNCMI2039_1("loan_card_flg","有无贷款卡","1","0",null,"2");
    public static final BaseXmlBeanEnum loan_card_id = new Socket_99CHNCMI2039_1("loan_card_id","贷款卡编号","16","0",null,"");
    public static final BaseXmlBeanEnum loan_card_pwd = new Socket_99CHNCMI2039_1("loan_card_pwd","贷款卡密码","20","0",null,"");
    public static final BaseXmlBeanEnum loan_card_eff_flg = new Socket_99CHNCMI2039_1("loan_card_eff_flg","贷款卡有效标志","2","0",null,"");
    public static final BaseXmlBeanEnum loan_card_audit_dt = new Socket_99CHNCMI2039_1("loan_card_audit_dt","贷款卡最近年审日期","10","0",null,"");
    public static final BaseXmlBeanEnum openday = new Socket_99CHNCMI2039_1("openday","登记日期","10","0",null,"");
    public static final BaseXmlBeanEnum chg_time = new Socket_99CHNCMI2039_1("chg_time","更新时间","26","0",null,"");


}
