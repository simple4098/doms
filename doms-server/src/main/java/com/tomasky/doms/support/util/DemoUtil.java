package com.tomasky.doms.support.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/6/7
 * @version: v1.0.0
 */
public class DemoUtil {
    private static String InputCharset = "UTF-8";

    private DemoUtil(){}
    /**
     * 生成签名结果
     * @param paramMap 要签名的参数数组
     * @param signKey 商户密钥
     * @return 签名结果字符串
     */
    public static String buildMyHMAC(Map<String, String> paramMap, String signKey) {
        Map<String, String> sPara = paraFilter(paramMap);
        String prestr = createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String myHMAC = buildHMAC(prestr, signKey, InputCharset);
        return myHMAC.toUpperCase(Locale.ENGLISH);
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("hmac")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 方法说明：签名字符串
     *
     * @param text 需要签名的字符串
     * @param signKey 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    private static String buildHMAC(String text, String signKey, String input_charset) {
        /**
         *  将signKey插入放置在参数的第12位后面
         */
        text = text.substring(0, 12) + signKey + text.substring(12);
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 方法说明：获取指定编码字节流
     *
     * @param content
     *            字符流
     * @param charset
     *            编码
     * @return 字节数组
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"+ charset);
        }
    }

    public static void main(String[] args){
        Map<String, String> paramMap = new HashMap<String, String>();
        /** 注意，hmac的生成使用请求中所有的参数，以下仅为例子，不是最终参数 */
        paramMap.put("channelCode", "QUNAR");
        paramMap.put("pmsId", "去呼呼分配给PMS的唯一标识");
        paramMap.put("version", "1.0");
        String hmac = DemoUtil.buildMyHMAC(paramMap , "去呼呼分配给PMS的唯一签名token");
        System.out.println(hmac);
    }
}
