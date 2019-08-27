/**
 * Project Name:snsxiu-moss
 * File Name:HttpUtil.java
 * Package Name:com.snsxiu.moss.util
 * Date:2017年3月17日下午3:24:13
 * Copyright (c) 2017, gww0426@163.com All Rights Reserved.
 *
*/

package com.fx.HttpRequestor.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ClassName:HttpUtil 
 * Date:     2017年3月17日 下午3:24:13 
 * @author   guoweiwei gww0426@163.com
 * @since    JDK 1.7
 */
public class HttpUtil {
	
	private static final Log logger = LogFactory.getLog(HttpUtil.class);
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static String get(String url){
		return get(url, DEFAULT_CHARSET);
	}
	
	public static String get(String url, String charset){
		String result = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
		httpGet.setConfig(requestConfig);
		try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, charset);
		} catch (ClientProtocolException e) {
			logger.error("postResult错误", e);
		} catch (IOException e) {
			logger.error("postResult错误", e);
		}
		
		return result;
		
	}
	
	public static String post(String url,List<NameValuePair> nvps){
		return post(url,nvps,DEFAULT_CHARSET);
	}
	
	public static String post(String url,List<NameValuePair> nvps, String charset){
		String result = null;
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			if(nvps != null && nvps.size()>0){
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			}
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, charset);
		} catch (ClientProtocolException e) {
			logger.error("postResult错误", e);
		} catch (IOException e) {
			logger.error("postResult错误", e);
		}
		
		return result;
	}
	
	public static String post(String url,Map<String, String> params){
		return post(url, params, DEFAULT_CHARSET);
	}
	
	public static String post(String url,Map<String, String> params, String charset){
		
		if(params == null || params.size() <= 0){
			return "empty param";
		}
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		for(Iterator<String> it = params.keySet().iterator();it.hasNext();){
			String key = it.next();
			String value = params.get(key);
			nvps.add(new BasicNameValuePair(key, value));
		}
		
		return post(url, nvps, charset);
	}

}

