package banger.common.tools;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2017/6/27.
 */
public class LoanModuleUtil {



    /**
     * 将阿拉伯数字转换成汉字【1-99】
     * 注意没有零，从1开始【1转换成一，二转换成二，...】
     * @param i
     * @return
     */
    public static String getProcessNameByIndex(int i) {
        String[] chineStrs = new String[]{"一","二","三","四","五","六","七","八","九","十"};
        if (i < 11) {
            return chineStrs[i-1];
        } else if (i < 20) {
            return "十" + chineStrs[i % 10 - 1];
        }  else {
            if (i % 10 == 0) {
                return chineStrs[i / 10-1] + "十";
            } else {
                return chineStrs[i / 10-1] + "十" + chineStrs[i % 10 - 1];
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i=i+7) {
            System.out.println((i+1) +":" +getProcessNameByIndex(i+1));
        }
    }
}
