package com.tomasky.doms.support.util;


import com.tomasky.doms.common.CommonApi;
import com.tomasky.doms.dto.qunar.QunarBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DESC : HttpClientUtil 连接Util工具类
 * @author : 番茄木-ZLin
 * @data : 2015/4/22
 * @version: v1.0.0
 */
public class HttpClientUtil {
	private final  static  int TIME_OUT = 90000;
	private final  static  int REQUEST_SOCKET_TIME = 60000;
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private HttpClientUtil(){}

	public static HttpClient obtHttpClient(String proxyIp,int proxyPort){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(HttpClientUtil.TIME_OUT)
				.setSocketTimeout(HttpClientUtil.REQUEST_SOCKET_TIME).build();
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
		//设置代理
		if(!StringUtils.isEmpty(proxyIp) && 0!=proxyPort){
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);
			httpClientBuilder.setProxy(proxy);
		}
		CloseableHttpClient httpClient = httpClientBuilder.build();
		return httpClient;
	}

	public static HttpClient obtHttpClient(){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(HttpClientUtil.TIME_OUT)
				.setSocketTimeout(HttpClientUtil.REQUEST_SOCKET_TIME).build();
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
		return httpClientBuilder.build();
	}



	public static String httpJsonPost(String url,String data) throws Exception {
		HttpClient httpClient = obtHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String value = EntityUtils.toString(entity, "utf-8");
		return value;
	}

	public static <T extends QunarBase> String httpKvPost(String url, T t) throws Exception {
		HttpClient httpClient = obtHttpClient();
		HttpPost httpPost = new HttpPost(url);
		String obj2json = JacksonUtil.obj2json(t);
		Map<String,String> param = JacksonUtil.json2map(obj2json);
		String hmac = SecurityUtil.buildMyHMAC(param, CommonApi.signkey);
		t.setHmac(hmac);
		List<NameValuePair> nameValuePairs = commonParam(t);
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String value = EntityUtils.toString(entity, "utf-8");
		return value;
	}

	public static List<NameValuePair> commonParam(Object o){
		String json = JacksonUtil.obj2json(o);
		Map<String, String> json2map = JacksonUtil.json2map(json);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry : json2map.entrySet()){
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return  nameValuePairs;

	}

	public static String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error("convertStreamToString "+e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("convertStreamToString exception"+e);
			}
		}
		return sb.toString();
	}


}
