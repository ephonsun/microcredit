package banger.common.tools;

import java.io.*;
import java.util.Arrays;

/**
 * 文件加密类,已实现RC4算法
 * Created by huyb on 16-5-9.
 */
public class EncryptUtil {

    public static void main(String[] args) {
        File source = new File("D:\\banger\\record\\avi\\20160517\\000020160517054041153.avi");
        File target = new File("D:\\banger\\record\\avi\\20160517\\000020160517054041153_d.avi");
        encry_RC4_File(source, target, "BangerBanger");
    }

    public static Boolean encry_RC4_File(File source, File target, String key) {
        if (!source.exists()) {
            return false;
        }
        try {
            if (!target.exists()) {
                target.createNewFile();
            }
            FileInputStream sourceFi = null;
            FileOutputStream targetFi = null;
            try {
                sourceFi = new FileInputStream(source);
                targetFi = new FileOutputStream(target);
                byte[] tempbytes = new byte[1024];
                int byteread = 0;
                while ((byteread = sourceFi.read(tempbytes)) != -1) {
                    byte[] out = RC4Base(tempbytes, key);
                    targetFi.write(out);
                }
            } finally {
                if (sourceFi != null) {
                    sourceFi.close();
                }
                if (targetFi != null) {
                    targetFi.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static byte[] encry_RC4_byte(byte[] data, String key) {
        if (data.length == 0 || key == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] tempbytes = new byte[1024];
        int byteread = 0;
        try {
            while ((byteread = bais.read(tempbytes)) != -1) {
                baos.write(RC4Base(tempbytes, key));
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return baos.toByteArray();
        }

    }

    public static byte[] encry_RC4_str(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte b_data[] = data.getBytes();
        return RC4Base(b_data, key);
    }

    private static byte[] initKey(String aKey) {
        byte[] b_key = aKey.getBytes();
        byte state[] = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }


    public static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }

    /**
     * 解析文件流来获取文件加密密钥
     *
     * @param file        已加密文件对象
     * @param encryptName 加密算法, 如RC4,SM4
     * @return 空表示无加密, 非空表示加密密钥
     */
    public static String getEncryptKey(File file, String encryptName) {
        if (!file.exists())
            return "";
        //查询文件是否被加密,读取最后 264 byte
        long at = file.length() - 264;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            while (at > 0) {
                long amt = fileInputStream.skip(at);
                if (amt == -1) {
                    throw new RuntimeException("downloadVideo: unexpected EOF");
                }
                at -= amt;
            }
            byte[] encryptKey = new byte[264];
            int iLen = fileInputStream.read(encryptKey);
            // 0-3 byte表示加密算法
            byte[] encryptNames = new byte[4];
            encryptNames = Arrays.copyOfRange(encryptKey, 0, 3);
            String encryptNameFile = new String(encryptNames);
            // 密钥长度,最长256位, 无符号
            int keyLen = encryptKey[4] & 0xff;
            byte[] encryptKeys = new byte[256];
            if (encryptName.equals(encryptNameFile) && keyLen > 0) {
                // 此文件已加密
                encryptKeys = Arrays.copyOfRange(encryptKey, 8, 8 + keyLen);
                String key = new String(encryptKeys);
                return key;
            } else {
                // 此文件未加密
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
