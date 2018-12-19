package banger.test;

import banger.framework.util.VmHelper;

import java.math.BigDecimal;

/**
 * @author zhangfp
 * @version $Id: MyTest v 0.1 ${} 16:50 zhangfp Exp $
 */
public class MyTest {
    public static void main(String[] args){
        String t = "19898100.0119";
        BigDecimal d = new BigDecimal(t);
        d = VmHelper.getDecimalByUnit(d,1,6,0);

        System.out.println(VmHelper.getUnityDecimalMoney(d,6,6));
    }
}
