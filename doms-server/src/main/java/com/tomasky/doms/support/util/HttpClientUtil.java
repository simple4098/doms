package com.tomasky.doms.support.util;


import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.common.Constants;
import com.tomasky.doms.dto.qunar.QunarBase;
import com.tomasky.doms.dto.qunar.QunarBaseBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESC : HttpClientUtil 连接Util工具类
 *
 * @author : 番茄木-ZLin
 * @data : 2015/4/22
 * @version: v1.0.0
 */
public class HttpClientUtil {
    private final static int TIME_OUT = 90000;
    private final static int REQUEST_SOCKET_TIME = 60000;
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private HttpClientUtil() {
    }

    public static HttpClient obtHttpClient(String proxyIp, int proxyPort) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(HttpClientUtil.TIME_OUT)
                .setSocketTimeout(HttpClientUtil.REQUEST_SOCKET_TIME).build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        //设置代理
        if (!StringUtils.isEmpty(proxyIp) && 0 != proxyPort) {
            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            httpClientBuilder.setProxy(proxy);
        }
        CloseableHttpClient httpClient = httpClientBuilder.build();
        return httpClient;
    }

    public static HttpClient obtHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(HttpClientUtil.TIME_OUT)
                .setSocketTimeout(HttpClientUtil.REQUEST_SOCKET_TIME).build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        return httpClientBuilder.build();
    }


    public static String httpJsonPost(String url, String data) throws Exception {
        HttpClient httpClient = obtHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String value = EntityUtils.toString(entity, "utf-8");
        return value;
    }

    /**
     * post http请求
     * @param url 请求url
     * @param data 参数json字符串
     */
    public static  String httpKvPost(String url, String data) throws Exception {
        HttpClient httpClient = obtHttpClient();
        HttpPost httpPost = new HttpPost(url);
        Map<String, String> param = JacksonUtil.json2map(data);
        List<NameValuePair> nameValuePairs = commonParam(param);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String value = EntityUtils.toString(entity, "utf-8");
        return value;
    }

    /**
     * post http请求
     * @param url 请求url
     * @param data 参数对象
     */
    public static  String httpKvPost(String url, Object data) throws Exception {
        HttpClient httpClient = obtHttpClient();
        HttpPost httpPost = new HttpPost(url);
        String json = JacksonUtil.obj2json(data);
        Map<String, String> param = JacksonUtil.json2map(json);
        List<NameValuePair> nameValuePairs = commonParam(param);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String value = EntityUtils.toString(entity, "utf-8");
        return value;
    }

    public static <T extends QunarBaseBean> String httpKvPost(String url, T t) throws Exception {
        HttpClient httpClient = obtHttpClient();
        HttpPost httpPost = new HttpPost(url);
        String obj2json = JacksonUtil.obj2json(t);
        Map<String, String> param = JacksonUtil.json2map(obj2json);
        String hmac = SecurityUtil.buildMyHMAC(param, CommonApi.signkey);
        t.setHmac(hmac);
        List<NameValuePair> nameValuePairs = commonParam(t);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String value = EntityUtils.toString(entity, "utf-8");
        return value;
    }

    public static List<NameValuePair> commonParam(Object o) {
        String json = JacksonUtil.obj2json(o);
        Map<String, String> json2map = JacksonUtil.json2map(json);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : json2map.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

    public static List<NameValuePair> commonParam(Map<String,String> map) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

    //-----------------post方式根据请求类型无代理获取网页信息或者Cookies---------------------
    public static String getResponseInfoByPost(String type, String url, List<NameValuePair> params) {
        return getResponseInfoByPost(type, url, params, null);
    }

    public static String getResponseInfoByPost(String type, String url, List<NameValuePair> params, Map<String, String> requestHeader) {
        return getResponseInfoByPost(type, url, params, requestHeader, null, null);
    }

    public static String getResponseInfoByPost(String type, String url, List<NameValuePair> params, Map<String, String> requestHeader, String beginStr, String endStr) {
        Map<String, String> responseStrAndCookies = getResponseStrAndCookiesByPost(type, url, null, 0, params, null, requestHeader, beginStr, endStr);
        return responseStrAndCookies.get(type);
    }

    public static String getResponseInfoByPost(String type, String url, String stringEntity) {
        return getResponseInfoByPost(type, url, stringEntity, null);
    }

    public static String getResponseInfoByPost(String type, String url, String stringEntity, Map<String, String> requestHeader) {
        return getResponseInfoByPost(type, url, stringEntity, requestHeader, null, null);
    }

    public static String getResponseInfoByPost(String type, String url, String stringEntity, Map<String, String> requestHeader, String beginStr, String endStr) {
        Map<String, String> responseStrAndCookies = getResponseStrAndCookiesByPost(type, url, null, 0, null, stringEntity, requestHeader, beginStr, endStr);
        return responseStrAndCookies.get(type);
    }

    //-----------------post方式根据请求类型通过代理获取网页信息或者Cookies---------------------
    public static String getResponseInfoByPost(String type, String url, String proxyIp, int proxyPort, List<NameValuePair> params) {
        return getResponseInfoByPost(type, url, proxyIp, proxyPort, params, null);
    }

    public static String getResponseInfoByPost(String type, String url, String proxyIp, int proxyPort, List<NameValuePair> params, Map<String, String> requestHeader) {
        return getResponseInfoByPost(type, url, proxyIp, proxyPort, params, requestHeader, null, null);
    }

    public static String getResponseInfoByPost(String type, String url, String proxyIp, int proxyPort, List<NameValuePair> params, Map<String, String> requestHeader, String beginStr, String endStr) {
        Map<String, String> responseStrAndCookies = getResponseStrAndCookiesByPost(type, url, proxyIp, proxyPort, params, null, requestHeader, beginStr, endStr);
        return responseStrAndCookies.get(type);
    }

    public static String getResponseInfoByPost(String type, String url, String proxyIp, int proxyPort, String stringEntity) {
        return getResponseInfoByPost(type, url, proxyIp, proxyPort, stringEntity, null);
    }

    public static String getResponseInfoByPost(String type, String url, String proxyIp, int proxyPort, String stringEntity, Map<String, String> requestHeader) {
        return getResponseInfoByPost(type, url, proxyIp, proxyPort, stringEntity, requestHeader, null, null);
    }

    public static String getResponseInfoByPost(String type, String url, String proxyIp, int proxyPort, String stringEntity, Map<String, String> requestHeader, String beginStr, String endStr) {
        Map<String, String> responseStrAndCookies = getResponseStrAndCookiesByPost(type, url, proxyIp, proxyPort, null, stringEntity, requestHeader, beginStr, endStr);
        return responseStrAndCookies.get(type);
    }

    //-----------------post方式无代理获取网页信息以及Cookies---------------------
    public static Map<String, String> getResponseStrAndCookiesByPost(String url, List<NameValuePair> params) {
        return getResponseStrAndCookiesByPost(url, params, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, List<NameValuePair> params, Map<String, String> requestHeader) {
        return getResponseStrAndCookiesByPost(url, params, requestHeader, null, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, List<NameValuePair> params, Map<String, String> requestHeader, String beginStr, String endStr) {
        return getResponseStrAndCookiesByPost(Constants.HTTP_GET_TYPE_ALL, url, null, 0, params, null, requestHeader, beginStr, endStr);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String stringEntity) {
        return getResponseStrAndCookiesByPost(url, stringEntity, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String stringEntity, Map<String, String> requestHeader) {
        return getResponseStrAndCookiesByPost(url, stringEntity, requestHeader, null, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String stringEntity, Map<String, String> requestHeader, String beginStr, String endStr) {
        return getResponseStrAndCookiesByPost(Constants.HTTP_GET_TYPE_ALL, url, null, 0, null, stringEntity, requestHeader, beginStr, endStr);
    }

    //-----------------post方式通过代理获取网页信息以及Cookies---------------------
    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String proxyIp, int proxyPort, List<NameValuePair> params) {
        return getResponseStrAndCookiesByPost(url, proxyIp, proxyPort, params, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String proxyIp, int proxyPort, List<NameValuePair> params, Map<String, String> requestHeader) {
        return getResponseStrAndCookiesByPost(url, proxyIp, proxyPort, params, requestHeader, null, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String proxyIp, int proxyPort, List<NameValuePair> params, Map<String, String> requestHeader, String beginStr, String endStr) {
        return getResponseStrAndCookiesByPost(Constants.HTTP_GET_TYPE_ALL, url, proxyIp, proxyPort, params, null, requestHeader, beginStr, endStr);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String proxyIp, int proxyPort, String stringEntity) {
        return getResponseStrAndCookiesByPost(url, proxyIp, proxyPort, stringEntity, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String proxyIp, int proxyPort, String stringEntity, Map<String, String> requestHeader) {
        return getResponseStrAndCookiesByPost(url, proxyIp, proxyPort, stringEntity, requestHeader, null, null);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String url, String proxyIp, int proxyPort, String stringEntity, Map<String, String> requestHeader, String beginStr, String endStr) {
        return getResponseStrAndCookiesByPost(Constants.HTTP_GET_TYPE_ALL, url, proxyIp, proxyPort, null, stringEntity, requestHeader, beginStr, endStr);
    }

    public static Map<String, String> getResponseStrAndCookiesByPost(String type, String url, String proxyIp, int proxyPort, List<NameValuePair> params, String stringEntity, Map<String, String> requestHeader, String beginStr, String endStr) {
        Map<String, String> responseStrAndCookies = new HashMap();
        HttpClient httpClient = setHttpClient(proxyIp, proxyPort);
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
            httpPost.setHeader("Accept-Charset", "GB2312,UTF-8;q=0.7,*;q=0.7");
            if (requestHeader != null && requestHeader.size() > 0) {
                for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (params != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } else if (StringUtils.isNotBlank(stringEntity)) {
                httpPost.setEntity(new StringEntity(stringEntity));
            }
            HttpResponse response = httpClient.execute(httpPost);
            responseStrAndCookies = getHttpResponseMap(type, httpClient, response, beginStr, endStr);
        } catch (Exception e) {
            logger.error("post方式获取网页:" + url + "信息失败,代理:" + proxyIp + " " + proxyPort, e);
        } finally {
            httpClient.getConnectionManager().shutdown();   //关闭连接
        }
        return responseStrAndCookies;
    }

    private static HttpClient setHttpClient(String proxyIp, int proxyPort) {
        HttpClient httpClient = new DefaultHttpClient();
        // 设置cookie的兼容性，这一行必须要加，否则服务器无法获取登陆状态
        HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        httpClient.getParams().setParameter(AllClientPNames.HTTP_CONTENT_CHARSET, "UTF-8");//有中文，设置一下请求编码
        httpClient.getParams().setParameter(AllClientPNames.CONNECTION_TIMEOUT, TIME_OUT);
        httpClient.getParams().setParameter(AllClientPNames.SO_TIMEOUT, REQUEST_SOCKET_TIME);
        if (StringUtils.isNotBlank(proxyIp) && 0 != proxyPort) {
            /** 设置代理IP **/
            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
        return httpClient;
    }

    private static Map<String, String> getHttpResponseMap(String type, HttpClient httpClient, HttpResponse response, String beginStr, String endStr) throws ParseException, IOException {
        Map<String, String> responseStrAndCookies = new HashMap();
        if (StringUtils.isNotBlank(type)) {
            if (Constants.HTTP_GET_TYPE_ALL.equals(type) || Constants.HTTP_GET_TYPE_STRING.equals(type)) {
                String content = EntityUtils.toString(response.getEntity());
                if (StringUtils.isNotBlank(beginStr)) {
                    content = StringUtils.substringAfter(content, beginStr);
                }
                if (StringUtils.isNotBlank(endStr)) {
                    content = content.substring(0, content.indexOf(endStr));
                }
                responseStrAndCookies.put(Constants.HTTP_GET_TYPE_STRING, content);
            }
            if (Constants.HTTP_GET_TYPE_ALL.equals(type) || Constants.HTTP_GET_TYPE_COOKIES.equals(type)) {
                //取出服务器返回的cookies信息，里面保存了服务器端给的“临时证”
                List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
                String tmpcookies = "";
                for (Cookie c : cookies) {
                    tmpcookies = tmpcookies + c.getName() + "=" + c.getValue() + "; ";
                }
                responseStrAndCookies.put(Constants.HTTP_GET_TYPE_COOKIES, tmpcookies);
            }
        }
        return responseStrAndCookies;
    }

}
