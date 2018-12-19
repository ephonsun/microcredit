package banger.common.tools;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author zhangxiang
 * @version $Id: StringUtil.java,v 0.1 2011-5-24 下午03:27:17 zhangxiang Exp $
 */
public class StringUtil {

    /**
     * 过滤SQL字符。
     *
     * @param Str 要过滤SQL字符的字符串。
     * @return 已过滤掉SQL字符的字符串。
     */
    public static String ReplaceSQLChar(String Str) {
        if (StringUtil.isEmpty(Str))
            return "";
//        Str = Str.replace("'", "‘");
//        Str = Str.replace("\"", "");
//        Str = Str.replace("&", "&amp");
//        Str = Str.replace("<", "&lt");
//        Str = Str.replace(">", "&gt");
//        Str = Str.replace(";", "；");
//        Str = Str.replace(",", ",");
//        Str = Str.replace("?", "?");
//
//        //半角括号替换为全角括号
//        Str = Str.replace("(", "（");
//        Str = Str.replace(")", "）");
//        Str = Str.replace("@", "＠");
//        Str = Str.replace("=", "＝");
//        Str = Str.replace("+", "＋");
//        Str = Str.replace("*", "＊");
//        Str = Str.replace("&", "＆");
//        Str = Str.replace("#", "＃");
//        Str = Str.replace("%", "％");
//        Str = Str.replace("$", "￥");
//        
//        Str = Str.replace("delete", "");
//        Str = Str.replace("update", "");
//        Str = Str.replace("insert", "");
//        Str = Str.replace("select", "");
//        
//        
//        //去除执行存储过程的命令关键字
//        Str = Str.replace("Exec", "");
//        Str = Str.replace("Execute", "");
//
//        //去除系统存储过程或扩展存储过程关键字
//        Str = Str.replace("xp_", "x p_");
//        Str = Str.replace("sp_", "s p_");
//
//        //防止16进制注入
//        Str = Str.replace("0x", "0 x");
        Str = StringEscapeUtils.escapeSql(Str);
        return Str;
    }

    /**
     * 过滤SQL逃逸字符。
     *
     * @param Str 要过滤逃逸SQL字符的字符串。
     * @return 已过滤掉逃逸SQL字符的字符串。
     */
    public static String ReplaceSQLEscapeChar(String Str) {
        if (StringUtil.isNullOrEmpty(Str))
            return "";

        //处理逃逸字符(%, _)
        Str = Str.replace("^", "^^");
        Str = Str.replace("%", "^%");
        Str = Str.replace("_", "^_");
        return Str;
    }

    public static boolean isContains(String[] strs, String s) {
        // 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串

        for (int i = 0; i < strs.length; i++) {
            if (strs[i].indexOf(s) != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
                return true;// 查找到了就返回真，不在继续查询
            }
        }
        return false;// 没找到返回false
    }

    /**
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        if (isEmpty(s)) {
            return false;
        } else {
            for (int i = 0; i < s.length(); i++) {
                if (!((s.charAt(i) >= '0') && (s.charAt(i) <= '9'))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * @param s
     * @return
     */
    public static boolean isIp(String s) {
        String strMatch = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
        Pattern ParsePattern = Pattern.compile(strMatch);
        Matcher ParseMatcher = ParsePattern.matcher(s);
        return ParseMatcher.find();
    }

    /**
     * @param s
     * @return
     */
    public static boolean isDomainName(String s) {
        String strMatch = "[a-zA-Z0-9]+([a-zA-Z0-9\\-\\.]+)?\\.(com|cn|org|net|mil|edu|COM|ORG|NET|MIL|EDU)";
        Pattern ParsePattern = Pattern.compile(strMatch);
        Matcher ParseMatcher = ParsePattern.matcher(s);
        return ParseMatcher.find();
    }

    /**
     * @param s
     * @return
     */
    public static boolean isEmail(String s) {
        String strMatch = "([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})";
        Pattern ParsePattern = Pattern.compile(strMatch);
        Matcher ParseMatcher = ParsePattern.matcher(s);
        return ParseMatcher.find();
    }

    /**
     * @param d
     * @param pL
     * @return
     */
    public static String getStandardDouble(double d, int pL) {
        String format = "0.";
        for (int i = 0; i < pL; i++)
            format += "0";
        return ((new DecimalFormat(format)).format(d));
    }

    /**
     * @param n
     * @return
     */
    public static String getRandValue(int n) {
        String sRand = "";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 返回非null的字符串
     *
     * @param s
     * @return
     */
    public static String getNotNullValue(Object s) {
        return s == null ? "" : s.toString();
    }

    /**
     * 将"1,2,3,4"格式的字符串转换成integer数组
     *
     * @param strArray
     * @return
     */
    public static Integer[] StringToIntegerArray(String strArray) {
        Integer ids[] = {};
        do {
            if (strArray == null) {
                break;
            }
            String[] idstrArray = strArray.split(",");
            if (idstrArray.length == 0) {
                break;
            }
            ids = new Integer[idstrArray.length];
            for (int i = 0; i < idstrArray.length; i++) {
                ids[i] = Integer.valueOf(idstrArray[i]);
            }
        } while (false);
        return ids;
    }

    /**
     * 检查字符串是否是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符。
     * <p/>
     * <pre>
     *
     *    StringUtil.isBlank(null)      = true
     *    StringUtil.isBlank(&quot;&quot;)        = true
     *    StringUtil.isBlank(&quot; &quot;)       = true
     *    StringUtil.isBlank(&quot;bob&quot;)     = false
     *    StringUtil.isBlank(&quot;  bob  &quot;) = false
     *
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回 <code>true</code>
     */
    public static boolean isBlank1(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }// add by rolyer 2010.03.16

    /**
     * 过滤sql句的敏感字符（'）
     *
     * @param sql
     * @return
     */
    public static String filterSql(String sql) {
        return sql.replace("'", "''");
    }

    /**
     * 将过滤sql句的敏感字符（'）
     *
     * @param sql
     * @return
     */
    @Deprecated
    public static String getCorrentSql(Object sql) {
        if (sql != null) {
            String s = sql.toString();
            return s.replace("\\", "\\\\").replace("'", "''");
        } else {
            return null;
        }

    }

    /**
     * 将过滤sql句的敏感字符（'）,在调用处不需要加单引号，由这里添加，可以防止将null值做为'null'字符串插入
     *
     * @param sql
     * @return
     */
    public static String getNewCorrentSql(Object sql) {
        if (sql != null) {
            String s = sql.toString();
            return "'" + s.replace("\\", "\\\\").replace("'", "''") + "'";
        } else {
            return null;
        }

    }

    /**
     * 编码URL带的参数
     *
     * @param str 参数
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String decryptUrlParameter(String str) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return new String(str.getBytes("ISO-8859-1"), "UTF-8");
    }

    /**
     * 加密URL带的参数
     *
     * @param str 参数
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String encryptUrlParameter(String str) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return new String(str.getBytes("UTF-8"), "ISO-8859-1");
    }

    /**
     * 控制字符串显示长度，如果过长则按length截取并加"..."
     *
     * @param s      待显示的字符串
     * @param length 能显示的最大长度，英文算1个单位长度，中文字符2单位长度
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String controlLength(String s, Integer length)
            throws UnsupportedEncodingException {
        if (s != null) {
            Integer size = 0;
            for (Integer index = 0; index < s.length(); index++) {
                Integer i = s.substring(index, index + 1).getBytes("UTF-8").length;
                if (size + i > length) {
                    return s.substring(0, index) + "...";
                } else {
                    size += i;
                }
            }
        }
        return s;
    }

    public static String controlLengthAddColon(String s, Integer length)
            throws UnsupportedEncodingException {
        if (s != null) {
            Integer size = 0;
            for (Integer index = 0; index < s.length(); index++) {
                Integer i = s.substring(index, index + 1).getBytes("UTF-8").length;
                if (size + i > length) {
                    return s.substring(0, index);
                } else {
                    size += i;
                }
            }
        }
        return s;
    }

    /**
     * 去除字符串中的HTML标签及一些空白字符
     *
     * @param htmlString
     * @return
     */
    public static String removeHTML(String htmlString) {
        // Remove HTML tag from java String
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");

        // Remove Carriage return from java String
        noHTMLString = noHTMLString.replaceAll("\r", "");

        // Remove New line from java string and replace html break
        noHTMLString = noHTMLString.replaceAll("\n", " ");
        noHTMLString = noHTMLString.replaceAll("\'", "&#39;");
        noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
        return noHTMLString;
    }

    /**
     * 查看一个字符串 是否包含另外一个字符
     *
     * @param a 如果a包含b 则显示a 否则显示a+b
     * @param b
     * @return
     */
    public static String IsIndexOf(String a, String b) {
        if (a != null && b != null) {
            if (a.indexOf(b) != -1) {
                return a;
            } else {
                return a + b;
            }
        } else {
            return a;
        }

    }

    /**
     * 判断字符是否为时间类型
     *
     * @param str
     * @return
     */
    public static boolean isDate(String str) {
        Pattern pattern = Pattern
                .compile("^([0-9]{4})((0([1-9]{1}))|(1[0-2]))(([0-2]([0-9]{1}))|(3[0|1]))(([0-1]([0-9]{1}))|(2[0-4]))([0-5]([0-9]{1}))([0-5]([0-9]{1}))");
        Matcher matcher = pattern.matcher(str);
        boolean bool = matcher.matches();
        return bool;
    }

    /**
     * 返回没有空格的字符串
     *
     * @param s
     * @return
     */
    public static String getNotSpaceValue(String s) {
        return s.split("\\,")[0].trim();

    }

    public static final String allChar = "0123456789";

    /**
     * 随机产生6位密码
     *
     * @param length
     * @return
     */
    public static String generatePassword(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String trim(String str) {
        return trim(str, null, 0);
    }

    /**
     * @param str
     * @param stripChars
     * @param mode
     * @return
     */
    private static String trim(String str, String stripChars, int mode) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        int start = 0;
        int end = length;

        if (mode <= 0) {
            if (stripChars == null) {
                while ((start < end)
                        && (Character.isWhitespace(str.charAt(start)) || (int) str.charAt(start) == 160)) {
                    start++;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
                while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1)) {
                    start++;
                }
            }
        }

        if (mode >= 0) {
            if (stripChars == null) {
                while ((start < end)
                        && (Character.isWhitespace(str.charAt(end - 1)) || (int) str.charAt(start) == 160)) {
                    end--;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
                while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                    end--;
                }
            }
        }

        if ((start > 0) || (end < length)) {
            return str.substring(start, end);
        }

        return str;
    }

    /**
     * 过滤所有特殊符号
     *
     * @param str
     * @return
     * @throws java.util.regex.PatternSyntaxException
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符   
        String regEx = "[`!@#$^={}\\<>?！@#￥{}。？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 加码
     *
     * @param value
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String encode(String value) throws UnsupportedEncodingException {
        if (value == null || value.equals("")) {
            return null;
        }
        return URLEncoder.encode(value, "UTF-8");
    }

    /**
     * 解码
     *
     * @param value
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String decode(String value) throws UnsupportedEncodingException {
        if (value == null || value.equals("")) {
            return null;
        }
        return URLDecoder.decode(value, "UTF-8");
    }

    /**
     * float类型带科学计数法的
     */
    public static String removeFloat(String value) throws UnsupportedEncodingException {
        float a = Float.parseFloat(value);
        DecimalFormat format = new DecimalFormat("###0.0");
        return format.format(a);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.err.println(removeFloat("1.2313231E7" + "测试"));
        String str = "123\n123\n123\n13\n123\n123\n123\n32\nw\nrw\nrw\nr\nwr\n\n\nwer\n\n\n\nwre\n\n\nwr\n\nwr\n\nwr\n\nwr\nwr\n\n\n\n\n\n\n\n\n\n\n\nwr\n\nwr\nw\ner\n\n\nwr\nw\nr\nw\nr\n\n\nwr\n\nr\n\nrr\nr\nrr\n\nr\n\n\nr\n\nr\n\n\nr\n\nr\n\nr\n\nr\nr\nr\n\nr\nr\nr\nw\nr\n\nrw\n\n\n\n\nwr\nw\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nw\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nw\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nw\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ne\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nwwwwwwwwwwwwwwwww\n\n\nw";
        System.err.println(replaceRemark(str));
    }

    /**
     * ID集合转字符串
     *
     * @param ids
     */
    public static String getIdsString(Object[] ids) {
        String str = "";
        for (Object id : ids) {
            str += ("".equals(str)) ? id : "," + id;
        }
        return str;
    }

    /**
     * 字符串左边填补字符
     *
     * @param str
     * @param totalLength 字符串长度
     * @param c           填补的字符
     * @return
     */
    public static String padLeft(String str, int totalLength, char c) {
        String str1 = str;
        int bit = totalLength;
        int n = str1.length();
        if (n < bit) {
            int m = bit - n;
            for (int i = 0; i < m; i++) {
                str1 = String.valueOf(c) + str1;
            }
            return str1;
        } else
            return str;
    }

    /**
     * 字符串右边填补字符
     *
     * @param str
     * @param totalLength 字符串长度
     * @param c           填补的字符
     * @ return
     */
    public static String padRight(String str, int totalLength, char c) {
        String str1 = str;
        int bit = totalLength;
        int n = str1.length();
        if (n < bit) {
            int m = bit - n;
            for (int i = 0; i < m; i++) {
                str1 = str1 + String.valueOf(c);
            }
            return str1;
        } else
            return str;
    }

    public static String format(String str, Object... args) {
        return format(str, Pattern.compile("\\{(\\d+)\\}"), args);
    }

    /**
     * 判断字符串是空或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    /**
     * 字符串参数格式化
     *
     * @param str
     * @param args
     * @return
     */
    public static String format(final String str, Pattern pattern, Object... args) {
        //这里用于验证数据有效性   
        if (str == null || "".equals(str))
            return "";
        if (args.length == 0) {
            return str;
        }

        String result = str;

        //这里的作用是只匹配{}里面是数字的子字符串   
        Pattern p = pattern;
        Matcher m = p.matcher(str);

        while (m.find()) {
            //获取{}里面的数字作为匹配组的下标取值   
            int index = Integer.parseInt(m.group(1));

            //这里得考虑数组越界问题，{1000}也能取到值么？？   
            if (index < args.length) {
                //替换，以{}数字为下标，在参数数组中取值   
                result = result.replace(m.group(), args[index].toString());
            } else {
                result = result.replace(m.group(), "");
            }
        }
        return result;
    }

    public static boolean existChar(String str, String childstr) {
        StringTokenizer tokens = new StringTokenizer(str, ",");
        Map<String, String> parameterMap = new HashMap<String, String>();
        while (tokens.hasMoreElements()) {
            String temp = tokens.nextElement().toString();
            if (!parameterMap.containsKey(temp)) {
                parameterMap.put(temp, temp);
            }
        }
        if (parameterMap.containsKey(childstr)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取MD5加密密码
     *
     * @param pwd 加密前密码
     * @return
     */
    public static String GetMD5(String pwd) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            char[] charArray = pwd.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++)
                byteArray[i] = (byte) charArray[i];
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 替换字符串回车换行符、制表符
     *
     * @param remark
     * @return
     */
    public static String replaceRemark(String remark) {
        String mark = "";
        if (!StringUtil.isBlank(remark)) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(remark);
            mark = m.replaceAll(" ");
        }
        return mark;
    }

    /**
     * 替换双引号为单引号
     *
     * @param remark
     * @return
     */
    public static String replaceQuotation(String remark) {
        String mark = "";
        if (!StringUtil.isBlank(remark)) {
            Pattern p = Pattern.compile("\"");
            Matcher m = p.matcher(remark);
            mark = m.replaceAll("\'");
        }
        return mark;
    }

    /**
     * 例如：HELLO_WORLD->HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */

    public static  String camelName(String name) {
        StringBuilder result =  new  StringBuilder();
        if(!isNotEmpty(name)) {
            // 没必要转换
            return "";
        }else if(!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0,1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for(String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if(camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if(result.length() ==  0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            }else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0,1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 详情换行显示
     *
     * @param remark
     * @return
     */
    public static String blackDisplayForDetail(String remark) {
        if (StringUtil.isNotEmpty(remark)) {
            remark = remark.replace("\r\n","<br>");
            remark = remark.replace("\n","<br>");
        }
        return remark;
    }
}
