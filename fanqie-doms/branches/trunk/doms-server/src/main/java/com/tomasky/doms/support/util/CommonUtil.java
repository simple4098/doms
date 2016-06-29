package com.tomasky.doms.support.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);


    /**
     * 获取配置文件的值
     *
     * @param key properties 文件中的key值
     */
    public static String obtProperties(String key) {
        if (StringUtils.isNotEmpty(key)) {
            Properties p = new Properties();
            InputStream in = CommonUtil.class
                    .getResourceAsStream("/sso-info.properties");
            try {
                p.load(in);
            } catch (IOException e) {
                logger.error("读取配置文件异常:" + e);
            }
            return p.getProperty(key);
        }
        return null;
    }

    /**
     * 获取配置文件的值
     *
     * @param key properties 文件中的key值
     */
    public static String obtProperties(String key, String propertiesUrl) {
        if (StringUtils.isNotEmpty(key)) {
            Properties p = new Properties();
            InputStream in = CommonUtil.class.getResourceAsStream(propertiesUrl);
            try {
                p.load(in);
            } catch (IOException e) {
                logger.error("读取配置文件异常:" + e);
            }
            return p.getProperty(key);
        }
        return null;
    }

    /**
     * 如果集合不为空 返回true
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isListNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    /**
     * 根据name[] value[] 封装List<NameValuePair>
     *
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public static List<NameValuePair> initNameValuePairList(String[] name,
                                                            String[] value) throws Exception {
        if (name.length != value.length)
            throw new RuntimeException("NameValuePair中name长度和value长度不一致");
        List<NameValuePair> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            BasicNameValuePair nvp = new BasicNameValuePair(name[i], value[i]);
            list.add(nvp);
        }
        return list;
    }

    /**
     * ? 基本数据类型 List<?>转为已逗号分隔的字符串
     */
    public static String ArrayToStrbyComma(
            @SuppressWarnings("rawtypes") List list) {
        StringBuffer st = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                st.append(list.get(i));
            } else {
                st.append(list.get(i) + ",");
            }
        }
        return st.toString();
    }


    /**
     * 以|分隔的字符串转换为指定类型的集合
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> StrByVerticalLineToArray(String str, Class<T> clazz) {
        return StrByCoustomToArray("\\|", str, clazz);
    }

    /**
     * coustom分隔的字符串转换为指定类型的集合
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> StrByCoustomToArray(String partten, String str, Class<T> clazz) {
        String[] strArray = str.split(partten);
        T[] obj = (T[]) Array.newInstance(clazz, strArray.length);
        Method method = null;
        try {
            if (clazz.getName().equals(String.class.getName())) {
                obj = (T[]) strArray;
            } else {
                method = clazz.getMethod("valueOf", String.class);
                for (int i = 0; i < strArray.length; i++) {
                    obj[i] = (T) method.invoke(null, strArray[i]);
                }
            }
        } catch (Exception e) {
            logger.error("StrByCoustomToArray error", e);
        }
        List accList = Arrays.asList(obj);
        return accList;
    }


    /**
     * 以逗号分隔的字符串转换为指定类型的集合
     *
     * @param <T>
     * @param str
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> StrByCommaToArray(String str, Class<T> clazz) {
        return StrByCoustomToArray(",", str, clazz);
    }

    /**
     * 校验日期格式，yyyy-MM-dd或者yyyy/MM/dd
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

    /**
     * string数组转string字符串，可带分隔符
     *
     * @param param
     * @param patten
     * @return
     */
    public static String arrayConvertToSplitString(String[] param, String patten) {
        StringBuilder result = new StringBuilder();
        if (param.length == 0) {
            return result.toString();
        }
        for (String str : param) {
            if (StringUtils.isNotBlank(str)) {
                result.append(str + patten);
            }
        }
        return result.toString().substring(0, result.toString().length() - 1);
    }

    /**
     * 去掉字符串最后一个符号
     *
     * @param str
     * @param str
     * @return
     */
    public static String substrLastPatten(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return str.substring(0, str.length() - 1);
    }


    public static String convertStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("convertStreamToString " + e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("convertStreamToString exception" + e);
            }
        }
        return sb.toString();
    }


    public static Map<String, Object> getOrderResult(Map<String, Object> result) {
        Map<String, Object> map = new HashMap<>();
        if (null != result) {
            for (Map.Entry entry : result.entrySet()) {
                if (entry.getKey().toString().equals("code")) {
                    map.put(entry.getKey().toString(), entry.getValue());
                } else if (entry.getKey().toString().equals("message")) {
                    map.put(entry.getKey().toString(), entry.getValue());
                }
            }
        }
        return map;
    }
}
