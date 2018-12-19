package banger.framework.util;

import sun.misc.BASE64Decoder;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class Base64Util {
    // 解密
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
