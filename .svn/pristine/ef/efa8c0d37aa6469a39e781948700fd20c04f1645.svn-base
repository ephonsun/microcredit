/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :yuanme
 * Create Date:2012-10-18
 */
package banger.common.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author yuanme
 * @version $Id: ServerRealPathUtil.java,v 0.1 2012-10-18 上午11:53:05 Administrator Exp $
 */
public class ServerRealPathUtil {
    private static Log logger = LogFactory.getLog(ServerRealPathUtil.class);

    public static String getServerRealPath() {
        String result = "";
        String classPath = "";
        try {
            classPath = java.net.URLDecoder.decode(ServerRealPathUtil.class.getResource("")
                .toString(), "utf-8");
        } catch (Exception e) {
            logger.error("getServerRealPath ...", e);
        }
        if (SystemUtil.isWindows()) {
            if (classPath.length() >= 10) {
                result = classPath.substring(10, classPath.lastIndexOf("WEB-INF"));
            }
        } else {
            int first = classPath.indexOf("/");
            if (first > 0) {
                result = classPath.substring(first, classPath.lastIndexOf("WEB-INF"));
            }
        }

        return result;
    }
}
