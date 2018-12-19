/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :Md5加密工具类
 * Author     :wumh
 * Email      :wumh@baihang-china.com
 * Create Date:2013-10-18
 */
package banger.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * @author wumh
 * @version Md5Encrypt.java,v 0.1 2012-7-19 下午5:43:50
 */
public class Md5Encrypt {
    // MD5加码。32位 
    public static String encrypt(String inStr) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = msgDigest.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
    
}
