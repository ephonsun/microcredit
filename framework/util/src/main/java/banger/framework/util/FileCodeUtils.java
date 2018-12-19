package banger.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-28
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class FileCodeUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static synchronized String createFileCode() {
        String result = sdf.format(Calendar.getInstance().getTime());
        return result;
    }
}
