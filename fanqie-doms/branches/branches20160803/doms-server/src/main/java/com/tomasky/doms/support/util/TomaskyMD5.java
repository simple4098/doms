package com.tomasky.doms.support.util;

import com.fanqie.util.PropertiesUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jame
 * @ClassName: MD5
 * @Description: 番茄PMS调用接口token参数，md5加密
 */
public class TomaskyMD5 {
    private static final String Token = PropertiesUtil.readFile("/config.properties", "doms.token");

    public synchronized static String encrypt(String text) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] buffer = text.getBytes();
            md5.update(buffer);
            byte[] result = md5.digest();
            for (byte b : result) {
                sb.append(String.format("%02X", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getTomaskyPmsToken() {
        return encrypt(Token);
    }
}