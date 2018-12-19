package banger.framework.util;

import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证信息算法类
 *
 * @author javaweb
 *
 */
public class IdCardUtil {

    /**
     * 正则：身份证号码15位
     */
    public static final String REGEX_ID_CARD15     = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";

    //public static final String REGEX_ID_CARD15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";

    /**
     * 正则：身份证号码18位
     */
    //public static final String REGEX_ID_CARD18     = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    public static final String REGEX_ID_CARD18 = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";


    /**
     * 将身份证转大写，统一入口
     *
     * @param str
     * @return
     */
    public static String toUpperCase(String str) {
        return str.toUpperCase();
    }

    /**
     * 得到身份证的年龄
     *
     * @param idcard
     * @return
     */
    public static Integer getAgeByIdCard(String idcard) {
        if (StringUtil.isNotEmpty(idcard)) {
            String str = idcard.trim();
            if (str.length() == 15) {
                try {
                    Map<String, Object> map = getCarInfo15W(str);
                    Object age = map.get("age");
                    if (age instanceof Integer)
                        return (Integer) age;
                } catch (Exception e) {

                }
            } else if (str.length() == 18) {
                try {
                    Map<String, Object> map = getCarInfo(str);
                    Object age = map.get("age");
                    if (age instanceof Integer)
                        return (Integer) age;
                } catch (Exception e) {

                }
            }

        }
        return null;
    }

    /**
     * 校验证件类型
     *
     * @param idType
     * @param idcard
     * @return
     */
    public static String checkIdCard(String idType, String idcard) {
        if ("1".equals(idType)) {
            return checkIdCard(idcard);
        }
        return "pass";
    }

    /**
     * 校验
     *
     * @param idcard
     * @return
     */
    public static String checkIdCard(String idcard) {
        if (StringUtil.isNotEmpty(idcard)) {
            String str = idcard.trim();
            if (str.length() == 15) {
                Pattern pattern15 = Pattern.compile(REGEX_ID_CARD15);
                Matcher matcher = pattern15.matcher(idcard);
                if (!matcher.find()) {
                    return "身份证格式不正确";
                }
            } else if (str.length() == 18) {
                Pattern pattern18 = Pattern.compile(REGEX_ID_CARD18);
                Matcher matcher = pattern18.matcher(idcard);
                if (!matcher.find()) {
                    return "身份证格式不正确";
                }
            } else {
                return "身份证格式不正确";
            }
        }
        return "pass";
    }

    /**
     * 校验客户内码唯一性
     * @param customerNum
     * @return
     */
    public static boolean isCheckCustomerNum(String customerNum) {
        String regex="^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match=pattern.matcher(customerNum);
        return match.matches();
    }
    /**
     * 得到身份证的姓别
     *
     * @param idcard
     * @return
     */
    public static String getSexByIdCard(String idcard) {
        if (StringUtil.isNotEmpty(idcard)) {
            String str = idcard.trim();
            if (str.length() == 15) {
                try {
                    Map<String, Object> map = getCarInfo15W(str);
                    Object sex = map.get("sex");
                    if (sex instanceof String)
                        return (String) sex;
                } catch (Exception e) {

                }
            } else if (str.length() == 18) {
                try {
                    Map<String, Object> map = getCarInfo(str);
                    Object sex = map.get("sex");
                    if (sex instanceof String)
                        return (String) sex;
                } catch (Exception e) {

                }
            }

        }
        return null;
    }

    /**
     * 得到身份证的生日
     *
     * @param idcard
     * @return
     */
    public static Date getBirthdayByIdCard(String idcard) {
        if (StringUtil.isNotEmpty(idcard)) {
            String str = idcard.trim();
            if (str.length() == 15) {
                try {
                    Map<String, Object> map = getCarInfo15W(str);
                    Date birthday = (Date)map.get("birthday");
                    if (birthday instanceof Date)
                        return birthday;
                } catch (Exception e) {

                }
            } else if (str.length() == 18) {
                try {
                    Map<String, Object> map = getCarInfo(str);
                    Date birthday = (Date)map.get("birthday");
                    if (birthday instanceof Date)
                        return birthday;
                } catch (Exception e) {

                }
            }

        }
        return null;
    }

    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
     *
     * @return
     * @throws Exception
     */
    private static Map<String, Object> getCarInfo(String CardCode)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String year = CardCode.substring(6).substring(0, 4);// 得到年份
        String yue = CardCode.substring(10).substring(0, 2);// 得到月份
        String day = CardCode.substring(12).substring(0, 2);//得到日

        Date birthday = getBirthdayDateByText(year,yue,day);
        String sex;
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "女";
        } else {
            sex = "男";
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        //String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year);
        }
        map.put("sex", sex);
        map.put("age", age);
        if(birthday!=null)
            map.put("birthday", birthday);
        return map;
    }

    private static Date getBirthdayDateByText(String year,String month,String day){
        String dateStr =  year+"-"+month+"-"+day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 15位身份证的验证
     *
     * @param
     * @throws Exception
     */
    private static Map<String, Object> getCarInfo15W(String card)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String uyear = "19" + card.substring(6, 8);// 年份
        String uyue = card.substring(8, 10);// 月份
        String uday=card.substring(10, 12);//日
        String usex = card.substring(14, 15);// 用户的性别
        Date birthday = getBirthdayDateByText(uyear,uyue,uday);
        String sex;
        if (Integer.parseInt(usex) % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        // String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
        }
        map.put("sex", sex);
        map.put("age", age);
        if(birthday!=null)
            map.put("birthday", birthday);
        return map;
    }

    public static String getFormatMoney(BigDecimal money) {
        if (money == null)
            return "";
        else
            return getFormatMoney(money.toString());
    }
    /**
     * 金钱格式化三位一个逗号，小数点后面不变
     *
     * @param money
     * @return
     */
    public static String getFormatMoney(String money) {
        String fieldNumberMoney = "";
        if (StringUtils.isNotBlank(money)) {
            int limit = money.indexOf(".");
            if (limit < 0)
                limit = money.length();

            for (int i = 0; i < limit; i = i + 3) {
                int end = limit - i;
                if (limit <= i + 3) {
                    fieldNumberMoney = money.substring(0, end) + fieldNumberMoney;
                } else {
                    fieldNumberMoney = "," + money.substring(limit - i - 3, end) + fieldNumberMoney;
                }
            }
            fieldNumberMoney = fieldNumberMoney + money.substring(limit);
        }
        return fieldNumberMoney;
    }


    /**
     * 15位身份证号：脱敏位数为7-12位；18位身份证号：脱敏数位7-14位
     *
     * @return
     */
    private static String getIdentifyNumX(String loanIdentifyNumX, String identifyType) {
        String temp = "";
        if (StringUtils.isNotBlank(loanIdentifyNumX)) {
            if ("1".equals(identifyType)) {
                int len = loanIdentifyNumX.length();
                if (len == 15)
                    temp = SensitiveUtil.getTakeOffSensitiveMiddle(loanIdentifyNumX, 5, 3);
                else if (len > 15)
                    temp = SensitiveUtil.getTakeOffSensitiveMiddle(loanIdentifyNumX, 5, 4);
                else
                    temp = SensitiveUtil.getTakeOffSensitiveMiddle(loanIdentifyNumX, 5, 3);
            } else {
                temp = loanIdentifyNumX;
            }
        }
        return temp;
    }

    /**
     * 15位身份证号：脱敏位数为7-12位；18位身份证号：脱敏数位7-14位
     *
     * @return
     */
    private static String getIdentifyNumX(String loanIdentifyNumX) {
        String temp = "";
        if (StringUtils.isNotBlank(loanIdentifyNumX)) {
            int len = loanIdentifyNumX.length();
            if (len == 15)
                temp = SensitiveUtil.getTakeOffSensitiveMiddle(loanIdentifyNumX, 5, 3);
            else if (len > 15)
                temp = SensitiveUtil.getTakeOffSensitiveMiddle(loanIdentifyNumX, 5, 4);
            else
                temp = SensitiveUtil.getTakeOffSensitiveMiddle(loanIdentifyNumX, 5, 3);
        }
        return temp;
    }

    private static String getTelNumX(String loanTelNumX) {
        String temp = "";
        if (StringUtils.isNotBlank(loanTelNumX)) {
            int len = loanTelNumX.length();
            switch (len) {
                case 1:
                    temp = "*";
                    break;
                case 2:
                    temp = "**";
                    break;
                case 3:
                    temp = "***";
                    break;
                default:
                    StringBuffer sb = new StringBuffer(loanTelNumX);
                    int sLen = len - 4;
                    sb.replace(sLen, len, "****");
                    temp = sb.toString();
                    break;
            }
        }
        return temp;
    }

    /**
     * 15位身份证号：脱敏位数为7-12位；18位身份证号：脱敏数位7-14位
     * flag 1 列表 flag 2 详情
     * @return
     */
    public static String getIdentifyNumX(String loanIdentifyNumX, String identifyType, int flag) {
        if(flag==1 && IdCardConfig.isListSwitch()){         //列表是否要脱敏
            return getIdentifyNumX(loanIdentifyNumX,identifyType);
        }else if(flag==2 && IdCardConfig.isDetailSwitch()){         //详情是否要脱敏
            return getIdentifyNumX(loanIdentifyNumX,identifyType);
        }else{
            return loanIdentifyNumX;
        }
    }

    /**
     * 15位身份证号：脱敏位数为7-12位；18位身份证号：脱敏数位7-14位
     * flag 1 列表 flag 2 详情
     * @return
     */
    public static String getIdentifyNumX(String loanIdentifyNumX, int flag) {
        if(flag==1 && IdCardConfig.isListSwitch()){         //列表是否要脱敏
            return getIdentifyNumX(loanIdentifyNumX);
        }else if(flag==2 && IdCardConfig.isDetailSwitch()){         //详情是否要脱敏
            return getIdentifyNumX(loanIdentifyNumX);
        }else{
            return loanIdentifyNumX;
        }
    }

    /**
     * 电话号话脱敏
     * @param loanTelNumX
     * @param flag 1 列表 2 详情
     * @return
     */
    public static String getTelNumX(String loanTelNumX, int flag) {
        if(flag==1 && IdCardConfig.isListSwitch()){         //列表是否要脱敏
            return getTelNumX(loanTelNumX);
        }else if(flag==2 && IdCardConfig.isDetailSwitch()){         //详情是否要脱敏
            return getTelNumX(loanTelNumX);
        }else{
            return loanTelNumX;
        }
    }

    /**
     * 读取数据库配置
     */
    static class IdCardConfig {

        /**
         * 例表是否要脱敏,默认开启
         * @return
         */
        public static boolean isListSwitch(){
            ISqlEngine ise =  SqlEngine.instance();
            Object value = ise.queryValue("getSysBasicConfigStatusByKey","lbtm");
            if("2".equals(value)){
                return false;
            }
            return true;
        }

        /**
         * 详情是否要脱敏,默认关闭
         * @return
         */
        public static boolean isDetailSwitch(){
            ISqlEngine ise =  SqlEngine.instance();
            Object value = ise.queryValue("getSysBasicConfigStatusByKey","xqtm");
            if("1".equals(value)){
                return true;
            }
            return false;
        }

    }

    public static void main(String[] args){
       String str =  checkIdCard("110111930303312");
       System.out.println(str);
    }


}